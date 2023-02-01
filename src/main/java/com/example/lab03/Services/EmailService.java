package com.example.lab03.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendSimpleMessage(String to, String subject, String text)
            throws SendFailedException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@uph.edu.pl");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    public void sendMimeMessage(String to, String subject, String header, String tittle, String description)
            throws MessagingException, javax.mail.MessagingException {
        var mimeMessage = emailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom("noreply@uph.edu.pl");
        helper.setTo(to);
        helper.setSubject(subject);
        var thymeleafCtx = new Context();
        thymeleafCtx.setVariable("header", header);
        thymeleafCtx.setVariable("title", tittle);
        thymeleafCtx.setVariable("description", description);
        String html = templateEngine.process("mail.html", thymeleafCtx);
        helper.setText(html, true); //czy HTML
        emailSender.send(mimeMessage);
    }

}
