package com.project.LMS_plus.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    private String comment;
    private String writer;

    public static class CreateCommentDto {
        private String comment;
        private String writer;
    }

    public static class UpdateCommentDto {
        private String comment;
    }
}