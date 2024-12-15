package com.example.aspect;

import com.example.domain.StatusType;
import com.example.domain.TaskEventDTO;
import com.example.web.controller.model.ResponseTaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerAspect {
    private final static String TOPIC = "task";
    private final KafkaTemplate<String, TaskEventDTO> kafkaEventTemplate;

    @Before("@annotation(com.example.aspect.annotation.KafkaProducerMessage) && args(.., taskId)")
    public void sendBefore(JoinPoint joinPoint, Long taskId) {
        log.info("Send kafka calling method {}", joinPoint.getSignature().getName());
        kafkaEventTemplate.send(TOPIC, createTaskEvent(joinPoint, taskId));
    }

    @AfterReturning(
            pointcut = "@annotation(com.example.aspect.annotation.HandlingResult)",
            returning = "result"
    )
    public void sendAfterReturning(JoinPoint joinPoint, ResponseTaskDTO result) {
        log.info("Send kafka calling method {}", joinPoint.getSignature().getName());
        kafkaEventTemplate.send(TOPIC, createTaskEvent(joinPoint, result.getId()));
    }

    private TaskEventDTO createTaskEvent(JoinPoint joinPoint, Long taskId) {
        TaskEventDTO eventDTO = new TaskEventDTO();
        eventDTO.setStatus(StatusType.fromValue(joinPoint.getSignature().getName()));
        eventDTO.setId(taskId);
        return eventDTO;
    }
}
