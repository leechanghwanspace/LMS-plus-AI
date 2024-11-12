package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.CommentDto;
import com.project.LMS_plus.entity.Comment;
import com.project.LMS_plus.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Tag(name = "Comment Management", description = "댓글 관리 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "특정 게시글(boardId)에 대한 댓글을 작성합니다.")
    @PostMapping("/{boardId}")
    public ResponseEntity<Comment> createComment(
            @Parameter(description = "댓글을 작성할 게시글의 ID", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "댓글의 내용을 담고 있는 객체", required = true)
            @RequestBody CommentDto commentDto,
            @Parameter(description = "현재 로그인한 사용자의 인증 정보", hidden = true)
            Authentication authentication) {

        String studentId = authentication.getName();  // 현재 로그인한 사용자 ID
        Comment comment = commentService.createComment(boardId, commentDto, studentId);
        return ResponseEntity.ok(comment);
    }

    @Operation(summary = "게시글의 모든 댓글 조회", description = "특정 게시글(boardId)에 작성된 모든 댓글을 조회합니다.")
    @GetMapping("/{boardId}")
    public ResponseEntity<List<Comment>> getCommentsByBoard(
            @Parameter(description = "댓글을 조회할 게시글의 ID", required = true)
            @PathVariable Long boardId) {

        List<Comment> comments = commentService.getCommentsByBoard(boardId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "댓글 수정", description = "특정 게시글(boardId)의 특정 댓글(commentId)을 수정합니다.")
    @PutMapping("/{boardId}/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @Parameter(description = "댓글을 수정할 게시글의 ID", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "수정할 댓글의 ID", required = true)
            @PathVariable Long commentId,
            @Parameter(description = "수정할 댓글의 내용을 담고 있는 객체", required = true)
            @RequestBody CommentDto commentDto,
            @Parameter(description = "현재 로그인한 사용자의 인증 정보", hidden = true)
            Authentication authentication) {

        String studentId = authentication.getName();  // 현재 로그인한 사용자 ID
        Comment updatedComment = commentService.updateComment(boardId, commentId, commentDto, studentId);
        return ResponseEntity.ok(updatedComment);
    }

    @Operation(summary = "댓글 삭제", description = "특정 게시글(boardId)의 특정 댓글(commentId)을 삭제합니다.")
    @DeleteMapping("/{boardId}/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글을 삭제할 게시글의 ID", required = true)
            @PathVariable Long boardId,
            @Parameter(description = "삭제할 댓글의 ID", required = true)
            @PathVariable Long commentId,
            @Parameter(description = "현재 로그인한 사용자의 인증 정보", hidden = true)
            Authentication authentication) {

        String studentId = authentication.getName();  // 현재 로그인한 사용자 ID
        commentService.deleteComment(boardId, commentId, studentId);
        return ResponseEntity.noContent().build();
    }
}
