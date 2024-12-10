package com.example.kafka;

import com.example.domain.TaskEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationHandleService {
    private final ObjectMapper objectMapper;

    private final JavaMailSender javaMailSender;
    @KafkaListener(topics = "task", groupId = "group_id")
    @Transactional
    public void consume(String message) throws JsonProcessingException {
        TaskEventDTO dto = objectMapper.readValue(message, TaskEventDTO.class);
        log.info("CONSUMED: {}", dto.toString());
        emailMessageSender(dto);
    }

    @Transactional
    public void emailMessageSender(TaskEventDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sergeilisovoi22@gmail.com");
        message.setTo("sergeilisovoi22@gmail.com");
        message.setSubject(dto.getStatus().toString());
        message.setText(dto.toString());
        javaMailSender.send(message);
    }
}
