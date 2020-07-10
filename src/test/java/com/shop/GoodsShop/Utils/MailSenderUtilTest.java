package com.shop.GoodsShop.Utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
public class MailSenderUtilTest {
    @Autowired
    @SpyBean
    private MailSenderUtil mailSenderUtil;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    public void shouldSendMessage() {
        mailSenderUtil.sendMessage("To", "Subject", "Message text");

        Mockito
                .verify(mailSender, Mockito.times(1))
                .send(any(SimpleMailMessage.class));
    }

    @Test
    public void shouldSendTemplateMessage() {
        mailSenderUtil.sendTemplateMessage("To", "LoginName", "code:123");

        Mockito
                .verify(mailSenderUtil, Mockito.times(1))
                .sendMessage(eq("To"),
                        eq("Активация электронной почты"),
                        contains("LoginName\nПожалуйста пройдите по данной ссылке" +
                                ", чтобы активировать почту:\ncode:123"));
    }
}
