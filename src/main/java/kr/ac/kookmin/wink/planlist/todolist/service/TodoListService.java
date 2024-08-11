package kr.ac.kookmin.wink.planlist.todolist.service;

import kr.ac.kookmin.wink.planlist.todolist.model.TodoList;
import kr.ac.kookmin.wink.planlist.todolist.repository.TodoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

    private final TodoListRepository todoListRepository;

    @Autowired
    public TodoListService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    // Create a new task
    public TodoList createTask(TodoList todoList) {
        return todoListRepository.save(todoList);
    }

    // Get all tasks
    public List<TodoList> getAllTasks() {
        return todoListRepository.findAll();
    }

    // Get a task by ID
    public Optional<TodoList> getTaskById(Long id) {
        return todoListRepository.findById(id);
    }

    // Update a task by ID
    public Optional<TodoList> updateTask(Long id, TodoList updatedTodoList) {
        return todoListRepository.findById(id).map(existingTask -> {
            existingTask.setTask(updatedTodoList.getTask());
            existingTask.setCompleted(updatedTodoList.isCompleted());
            return todoListRepository.save(existingTask);
        });
    }

    // Delete a task by ID
    public boolean deleteTask(Long id) {
        return todoListRepository.findById(id).map(existingTask -> {
            todoListRepository.delete(existingTask);
            return true;
        }).orElse(false);
    }
}
