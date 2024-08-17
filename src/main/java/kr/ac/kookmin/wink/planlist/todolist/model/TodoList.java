package kr.ac.kookmin.wink.planlist.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String task; // 할 일의 내용을 저장하는 필드
    private boolean completed; // 할 일의 완료 여부를 저장하는 필드

    // 기본 생성자 (필수)
    public TodoList() {}

    // 생성자: 필드를 초기화할 때 사용할 수 있습니다.
    public TodoList(String task, boolean completed) {
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

    // toString 메서드 (선택 사항)
    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", completed=" + completed +
                '}';
    }
}
