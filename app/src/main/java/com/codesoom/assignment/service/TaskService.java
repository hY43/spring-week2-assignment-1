package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TaskService {

    private List<Task> tasks = new ArrayList<>();
    private long maxId = 0L;

    public List<Task> findAll() {
        return tasks;
    }

    public Task findOne(Long id) {
        return findTaskById(id);
    }

    public Task create(Task task) {
        Task newTask = Task.createNewTaskWithTask(generateId(), task);
        tasks.add(newTask);
        return newTask;
    }

    public Task update(Long id, Task task) {
        Task source = findTaskById(id);
        source.changeTitle(task);
        return task;
    }

    public void delete(Long id) {
        Task target = findTaskById(id);
        tasks.remove(target);
    }

    /**
     * Task id 생성
     * @return id
     */
    private Long generateId() {
        this.maxId += 1;
        return this.maxId;
    }

    /**
     * Task id로 해당되는 task를 조회함
     * @param id
     * @return 존재 시, 해당 Task 객체 반환
     *         존재하지 않을시, 빈 Task 객체 반환
     */
    private Task findTaskById(Long id) {
        return tasks.stream()
                .filter(source -> source.checkMyId(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException());
    }
}
