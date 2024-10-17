package com.project.LMS_plus.service;

import com.project.LMS_plus.entity.Board;
import com.project.LMS_plus.entity.Comment;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.BoardRepository;
import com.project.LMS_plus.repository.CommentRepository;
import com.project.LMS_plus.repository.UserRepository;
import com.project.LMS_plus.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public Comment createComment(Long boardId, CommentDto commentDto, String userId) {
        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 사용자 확인
        User user = userRepository.findByStudentId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // 댓글 생성
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setBoard(board);
        comment.setUser(user); // User 객체를 설정
        comment.setWriter(userService.modifyName(userId));

        // 댓글 저장
        return commentRepository.save(comment);
    }

    @Transactional
    public List<Comment> getCommentsByBoard(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }


    @Transactional
    public Comment updateComment(Long boardId, Long commentId, CommentDto commentDto, String userId) {
        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 댓글 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // 게시글의 댓글인지 확인
        if (!comment.getBoard().getId().equals(boardId)) {
            throw new IllegalArgumentException("Comment does not belong to the specified board");
        }

        // 댓글 작성자 확인
        if (!comment.getUser().getStudentId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized to update this comment");
        }

        // 댓글 내용 업데이트
        comment.update(commentDto.getComment());

        // writer를 userService.modifyName(userId)를 통해 설정
        String modifiedWriterName = userService.modifyName(userId);
        comment.setWriter(modifiedWriterName);

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long boardId, Long commentId, String userId) {
        // 게시글 확인
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));

        // 댓글 확인
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // 게시글의 댓글인지 확인
        if (!comment.getBoard().getId().equals(boardId)) {
            throw new IllegalArgumentException("Comment does not belong to the specified board");
        }

        // 댓글 작성자 확인
        if (!comment.getUser().getStudentId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized to delete this comment");
        }

        commentRepository.delete(comment);
    }
}
