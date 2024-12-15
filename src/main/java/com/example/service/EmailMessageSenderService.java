package com.example.service;

import com.example.config.properties.EmailProperties;
import com.example.domain.TaskEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailMessageSenderService {

    private final JavaMailSender javaMailSender;

    private final EmailProperties emailProperties;

    @Transactional
    public void emailMessageSender(TaskEventDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailProperties.getEmailFrom());
        message.setTo(emailProperties.getEmailTo());
        message.setSubject(dto.getStatus().toString());
        message.setText(dto.toString());
        javaMailSender.send(message);
        log.info("Message send: {}", message);
    }
}
