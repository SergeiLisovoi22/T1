package com.example.kafka;

import com.example.domain.TaskEventDTO;
import com.example.service.EmailMessageSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationHandleService {
    private final EmailMessageSenderService emailMessageSender;

    @KafkaListener(id = "task",
            topics = "task",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<TaskEventDTO> messageList,
                         Acknowledgment ack) {
        log.debug("Client consumer: Обработка новых сообщений");
        try {
            messageList.forEach(emailMessageSender::emailMessageSender);
        } finally {
            ack.acknowledge();
        }
        log.debug("Client consumer: записи обработаны");
    }
}
