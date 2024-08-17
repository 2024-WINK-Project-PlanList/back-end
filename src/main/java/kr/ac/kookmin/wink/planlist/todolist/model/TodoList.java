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


    /*
    쓰이지 않을까..?
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "TodoList{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", completed=" + completed +
                '}';
    }
}
