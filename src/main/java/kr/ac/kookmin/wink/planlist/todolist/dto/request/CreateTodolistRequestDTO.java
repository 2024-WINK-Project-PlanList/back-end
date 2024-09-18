package kr.ac.kookmin.wink.planlist.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Setter
public class CreateTodolistRequestDTO {
    private String content;
    private LocalDateTime date;
}
