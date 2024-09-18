package kr.ac.kookmin.wink.planlist.todolist.dto;

import kr.ac.kookmin.wink.planlist.todolist.domain.Todolist;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodolistDTO {
    private Long id;       // 투두 리스트 ID
    private String content;       // 투두 리스트 내용
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime date;
    private Long userId; //사용자 ID
    private boolean checked;

    public static TodolistDTO create(Todolist todolist) {
        return TodolistDTO
                .builder()
                .id(todolist.getId())
                .content(todolist.getContent())
                .createdAt(todolist.getCreatedAt())
                .date(todolist.getDate())
                .userId(todolist.getUser().getId())
                .checked(todolist.isChecked())
                .build();
    }
}
