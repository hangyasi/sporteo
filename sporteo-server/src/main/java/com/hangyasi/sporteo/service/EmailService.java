package com.hangyasi.sporteo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;
  private static final String URL = "http://localhost:8080/";

  @Async
  private void sendMail(SimpleMailMessage email) {
    mailSender.send(email);
  }

  public void sendConfirmationMail(String email, String confirmationToken) {
    SimpleMailMessage confirmationMail = new SimpleMailMessage();
    confirmationMail.setTo(email);
    confirmationMail.setSubject("Registration confirmation");
    confirmationMail.setText("To confirm your e-mail address, please click the link below:" + System.lineSeparator() +
        URL + "confirm?token=" + confirmationToken);
    confirmationMail.setFrom("noreply@sporteo.com");
    sendMail(confirmationMail);
  }
}
