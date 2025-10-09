package com.example.userauthservice_june2025.services;

import com.example.userauthservice_june2025.exceptions.PasswordMismatchException;
import com.example.userauthservice_june2025.exceptions.UserAlreadyExistsException;
import com.example.userauthservice_june2025.exceptions.UserNotSignedUpException;
import com.example.userauthservice_june2025.models.Session;
import com.example.userauthservice_june2025.models.SessionState;
import com.example.userauthservice_june2025.models.Status;
import com.example.userauthservice_june2025.models.User;
import com.example.userauthservice_june2025.repos.SessionRepo;
import com.example.userauthservice_june2025.repos.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    SecretKey secretKey;

    @Override
    public User signup(String name, String email, String password, String phoneNumber) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("Please login directly");
        }

        User user = new  User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        return userRepo.save(user);
    }

    @Override
    public Pair<User, String> login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotSignedUpException("Please create your account first");
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new PasswordMismatchException("Password didn't match");
        }

        // Generate JWT

//        String message = "{\n"+
//                " \"email\": \"pruthvirajgaikwad1717@gmail.com\",\n"+
//                " \"roles\": [\n" +
//                "    \"instructor\",\n" +
//                "    \"buddy\"\n" +
//                " ], \n" +
//                " \"expirationDate\": \"2ndApril2026\"\n" +
//                "}";
//        byte[] content = message.getBytes(StandardCharsets.UTF_8);


        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("iss", "scaler");
        Long nowInMillis = System.currentTimeMillis();
        claims.put("gen", nowInMillis);
        claims.put("exp", nowInMillis + (1000*60*60*24));
        claims.put("scope", user.getRoles());
        // claims are used to make the payload
        // And we are using hashmap which can be used to pass to claims

//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = algorithm.key().build();
        String token = Jwts.builder().claims(claims).signWith(secretKey).compact(); // Secret key and we are passing claims payload

        Session session =  new Session();
        session.setUser(user);
        session.setToken(token);
        session.setState(SessionState.ACTIVE);
        sessionRepo.save(session);

        return new Pair<User, String>(user,token);
    }

    @Override
    public Boolean validateToken(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepo.findByTokenAndUser_Id(token, userId);
        if(sessionOptional.isEmpty()){
            return false;
        }

        Session session = sessionOptional.get();
//        MacAlgorithm algorithm = Jwts.SIG.HS256;
//        SecretKey secretKey = algorithm.key().build();
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build(); // parser is required because we need to reverse then it and find the data in jwt
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long exp = (Long) claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        System.out.println("Token Expiry ="+exp);
        System.out.println("Current Time ="+currentTime);

        if(currentTime > exp){
            session.setState(SessionState.EXPIRED);
            sessionRepo.save(session);
            return false;
        }
        return true;
    }
}
