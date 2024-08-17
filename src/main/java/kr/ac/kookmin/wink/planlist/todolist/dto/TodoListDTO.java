package kr.ac.kookmin.wink.planlist.todolist.dto;

public class TodoListDTO {

    private Long id;
    private String task;
    private boolean completed;

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
