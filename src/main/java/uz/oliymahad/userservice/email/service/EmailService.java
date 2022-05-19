package uz.oliymahad.userservice.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by abburi on 6/10/17.
 */

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.from.address}")
    private String fromAddress;

    public void sendMessage(String toEmail, String subject, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(fromAddress);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(message);
        javaMailSender.send(mimeMessage);
    }


}
