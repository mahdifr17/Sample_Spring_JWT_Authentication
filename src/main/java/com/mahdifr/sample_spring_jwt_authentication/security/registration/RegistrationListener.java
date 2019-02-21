package com.mahdifr.sample_spring_jwt_authentication.security.registration;

import com.mahdifr.sample_spring_jwt_authentication.model.UserModel;
import com.mahdifr.sample_spring_jwt_authentication.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * RegistrationListener
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        UserModel user = event.getUser();
        String verificationToken = tokenProvider.generateVerificationToken(user.getId());

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/api/auth/signup/confirm?token=" + verificationToken;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Confirmation URL " + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);
    }
}