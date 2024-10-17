package com.project.LMS_plus.service;

import com.project.LMS_plus.dto.BoardDto;
import com.project.LMS_plus.entity.Board;
import com.project.LMS_plus.entity.User;
import com.project.LMS_plus.repository.BoardRepository;
import com.project.LMS_plus.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @Transactional
    public Page<Board> readAllBoard(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
    @Transactional
    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional
    public Board createBoard(BoardDto.CreateBoardDto boardDto, String userId) {
        // 사용자 정보를 조회합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // 보드를 생성합니다.
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setPostedTime(LocalDateTime.now());
        board.setWriter(userService.modifyName(userId));

        // 보드를 사용자에게 추가합니다.
        user.addBoard(board);

        // 보드를 저장합니다.
        return boardRepository.save(board);
    }

    @Transactional
    public void updateBoard(String userId, Long boardId, BoardDto.UpdateBoardDto boardDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found with id: " + boardId));

        if (!board.getUser().getStudentId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Board does not belong to user with id: " + userId);
        }

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUpdatedTime(LocalDateTime.now());

        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}

