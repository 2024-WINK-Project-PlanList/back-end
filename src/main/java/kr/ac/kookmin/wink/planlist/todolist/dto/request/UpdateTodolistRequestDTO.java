package kr.ac.kookmin.wink.planlist.todolist.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTodolistRequestDTO {
    private Long id;
    private String content;
    private boolean checked;
}
