package com.sparta.myblog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.myblog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // 응답할 때 NULL 이 아닌 값들만 포함하겠다 ! -> NULL 인 값들은 제외
public class PostResponseDto {
    private Boolean success;
    private Long id;
    private String title;
    private String name;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    // password 는 노출이 되면 안되기 때문에 password 를 제외한 데이터를 반환 해준다
    // Dto 를 사용하는 이유 !

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.name = post.getName();
        this.contents = post.getContent();
        this.createdAt = post.getCreateAt();
        this.modifiedAt = post.getModifiedAt();
    }

    public PostResponseDto(Boolean success) {
        this.success = success;
    }
}
