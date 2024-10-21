package com.project.LMS_plus.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class BoardDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateBoardDto{
        private String title;
        private String content;



    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateBoardDto{
        private String title;
        private String content;
        private String writer;

    }

    @Getter
    @NoArgsConstructor
    public static class PostDetailsDTO {
        private Long id;
        private String title;
        private String content;
        private String writer;
        private Long userId; // 작성자의 userId 필드 추가
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public PostDetailsDTO(Long id, String title, String content, String writer, Long userId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.writer = writer;
            this.userId = userId; // 생성자에 userId 추가
            this.createdDate = createdDate;
            this.modifiedDate = modifiedDate;
        }

    }
}