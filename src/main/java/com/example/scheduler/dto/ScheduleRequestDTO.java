package com.example.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleRequestDTO {

    @NotBlank(message = "할 일은 필수입니다.")
    @Size(max = 200, message = "할 일은 최대 200자까지 입력할 수 있습니다.")
    private String task;

    @NotBlank(message = "작성자는 필수입니다.")
    private String author;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}