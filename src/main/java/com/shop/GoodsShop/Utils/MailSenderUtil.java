package com.shop.GoodsShop.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailSenderUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        logger.debug("Setting mailSender");
        this.mailSender = mailSender;
    }

    public void sendMessage(String to, String subject, String text) {
        logger.info("Called sendMessage method");
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendTemplateMessage(String to, String name, String uri) {
        sendMessage(
                to,
                "Активация электронной почты",
                "Здравствуйте, " + name + "\nПожалуйста пройдите по данной ссылке," +
                "чтобы активировать почту:\n" + uri);
    }
}
