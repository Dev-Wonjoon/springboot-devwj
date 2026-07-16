package net.likelion.bebc25.board3.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PostDto {
    private int id;

    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(max = 100, message = "제목은 최대 100자 이하로 입력해야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    private String content;

    @NotBlank(message = "작성자는 필수 입력 항목입니다.")
    @Size
    private String author;

    private boolean secret;
    private LocalDateTime createdAt;

}
