package kr.ac.kookmin.wink.planlist.todolist.dto;

public class TodoListDTO {

    private Long id;
    private String task;
    private boolean completed;

    // 기본 생성자
    public TodoListDTO() {}

    // 모든 필드를 초기화하는 생성자
    public TodoListDTO(Long id, String task, boolean completed) {
        this.id = id;
        this.task = task;
        this.completed = completed;
    }

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for task
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    // Getter and Setter for completed
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "TodoListDTO{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", completed=" + completed +
                '}';
    }
}
