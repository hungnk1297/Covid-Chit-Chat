package com.covid.chitchat.util;

import com.covid.chitchat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
@Slf4j
@Component
public class CommonUtil {

    private final UserRepository userRepository;

    @Autowired
    ServletContext servletContext;

    //  Validate duplicate username when creating
    public boolean isDuplicateUsername(String username) {
        return userRepository.isDuplicateUsername(username);
    }

    //  Generate hash password
    public String generateHashPassword(String username, String password) {
        String appendedString = password.concat(username);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digestBytes = md.digest(appendedString.getBytes());
            BigInteger no = new BigInteger(digestBytes);
            return no.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //  Get current Session
    public static HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true);
    }
}
