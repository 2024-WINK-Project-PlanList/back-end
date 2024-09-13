package kr.ac.kookmin.wink.planlist.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CreateTodolistRequestDTO {
    private String content;
}
