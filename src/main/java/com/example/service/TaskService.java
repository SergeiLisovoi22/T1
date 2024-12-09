package com.example.service;

import com.example.aspect.MainAspect;
import com.example.aspect.annotation.HandlingResult;
import com.example.aspect.annotation.LogException;
import com.example.aspect.annotation.LogExecution;
import com.example.aspect.annotation.LogTracking;
import com.example.domain.Task;
import com.example.exception.TaskNotFoundException;
import com.example.mapper.TaskMapper;
import com.example.repository.TaskRepository;
import com.example.web.controller.model.RequestTaskDTO;
import com.example.web.controller.model.ResponseTaskDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(MainAspect.class.getName());
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    @LogException
    public ResponseTaskDTO getTask(Long taskId) {
        logger.info("Get task by id");

        return taskMapper.toDTO(getEntity(taskId));
    }

    @Transactional(readOnly = true)
    @LogExecution
    public List<ResponseTaskDTO> getAllTasks() {
        logger.info("Get all task");
        return taskMapper.toListDTO(taskRepository.findAll());
    }

    @Transactional
    @HandlingResult
    public ResponseTaskDTO createTask(RequestTaskDTO taskDTO) {
        logger.info("Create task");
        Task task = taskMapper.toEntity(taskDTO);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Transactional
    @HandlingResult
    public ResponseTaskDTO updateTask(Long taskId, RequestTaskDTO taskDTO) {
        logger.info("Create task");
        Task targetEntity = getEntity(taskId);
        Task task = taskMapper.toEntity(taskDTO);
        return taskMapper.toDTO(taskRepository.save(taskMapper.updateEntityFromRequest(task, targetEntity)));
    }

    @Transactional
    @LogTracking
    public void deleteTask(Long taskId) {
        logger.info("Delete task");
        taskRepository.deleteById(taskId);
    }

    private Task getEntity(Long taskId) {
        Optional<Task> taskEntity = taskRepository.findById(taskId);
        return taskEntity.orElseThrow(() -> new TaskNotFoundException(String.valueOf(taskId)));
    }
}
