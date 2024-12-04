import com.example.domain.Task;
import com.example.exception.TaskNotFoundException;
import com.example.mapper.TaskMapper;
import com.example.repository.TaskRepository;
import com.example.service.TaskService;
import com.example.web.controller.model.RequestTaskDTO;
import com.example.web.controller.model.ResponseTaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTask_Success() {
        Long taskId = 1L;
        Task task = new Task();
        ResponseTaskDTO taskDTO = new ResponseTaskDTO();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        ResponseTaskDTO result = taskService.getTask(taskId);

        assertNotNull(result);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).toDTO(task);
    }

    @Test
    void testGetTask_NotFound() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
        verify(taskRepository).findById(taskId);
    }

    @Test
    void testGetAllTasks() {
        Task task = new Task();
        ResponseTaskDTO taskDTO = new ResponseTaskDTO();

        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        when(taskMapper.toListDTO(anyList())).thenReturn(Collections.singletonList(taskDTO));

        List<ResponseTaskDTO> result = taskService.getAllTasks();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskRepository).findAll();
        verify(taskMapper).toListDTO(anyList());
    }

    @Test
    void testCreateTask() {
        RequestTaskDTO requestTaskDTO = new RequestTaskDTO();
        Task task = new Task();
        ResponseTaskDTO taskDTO = new ResponseTaskDTO();

        when(taskMapper.toEntity(requestTaskDTO)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDTO(task)).thenReturn(taskDTO);

        ResponseTaskDTO result = taskService.createTask(requestTaskDTO);

        assertNotNull(result);
        verify(taskMapper).toEntity(requestTaskDTO);
        verify(taskRepository).save(task);
        verify(taskMapper).toDTO(task);
    }

    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        RequestTaskDTO requestTaskDTO = new RequestTaskDTO();
        Task existingTask = new Task();
        Task updatedTask = new Task();
        ResponseTaskDTO taskDTO = new ResponseTaskDTO();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskMapper.toEntity(requestTaskDTO)).thenReturn(updatedTask);
        when(taskMapper.updateEntityFromRequest(updatedTask, existingTask)).thenReturn(existingTask);
        when(taskRepository.save(existingTask)).thenReturn(existingTask);
        when(taskMapper.toDTO(existingTask)).thenReturn(taskDTO);

        ResponseTaskDTO result = taskService.updateTask(taskId, requestTaskDTO);

        assertNotNull(result);
        verify(taskRepository).findById(taskId);
        verify(taskMapper).updateEntityFromRequest(updatedTask, existingTask);
        verify(taskRepository).save(existingTask);
        verify(taskMapper).toDTO(existingTask);
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }
}
