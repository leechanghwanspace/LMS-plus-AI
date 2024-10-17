package com.project.LMS_plus.controller;


import com.project.LMS_plus.dto.CommentDto;
import com.project.LMS_plus.entity.Comment;
import com.project.LMS_plus.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{boardId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long boardId, @RequestBody CommentDto commentDto, Authentication authentication) {
        String userId = authentication.getName();  // 현재 로그인한 사용자 ID
        Comment comment = commentService.createComment(boardId, commentDto, userId);
        return ResponseEntity.ok(comment);
    }

    // 특정 게시글의 댓글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<List<Comment>> getCommentsByBoard(@PathVariable Long boardId) {
        List<Comment> comments = commentService.getCommentsByBoard(boardId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 수정
    @PutMapping("/{boardId}/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long boardId,
                                                 @PathVariable Long commentId,
                                                 @RequestBody CommentDto commentDto,
                                                 Authentication authentication) {
        String userId = authentication.getName();  // 현재 로그인한 사용자 ID
        Comment updatedComment = commentService.updateComment(boardId, commentId, commentDto, userId);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId,
                                              @PathVariable Long commentId,
                                              Authentication authentication) {
        String userId = authentication.getName();  // 현재 로그인한 사용자 ID
        commentService.deleteComment(boardId, commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
