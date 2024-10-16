package com.project.LMS_plus.controller;

import com.project.LMS_plus.dto.BoardDto;
import com.project.LMS_plus.entity.Board;
import com.project.LMS_plus.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 목록 조회
    @GetMapping
    public ResponseEntity<Page<Board>> getAllBoards(Pageable pageable) {
        Page<Board> boards = boardService.readAllBoard(pageable);
        return ResponseEntity.ok(boards);
    }

    // 게시판 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 게시판 생성
    @PostMapping
    public ResponseEntity<Board> createBoard(@RequestBody BoardDto.CreateBoardDto boardDto, Authentication authentication) {
        // Spring Security 컨텍스트에서 현재 로그인된 사용자 정보 가져오기
        String userId = authentication.getName();

        Board board = boardService.createBoard(boardDto, userId);
        return ResponseEntity.ok(board);
    }

    // 게시판 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(@PathVariable Long id, @RequestBody BoardDto.UpdateBoardDto boardDto, Authentication authentication) {
        // Spring Security 컨텍스트에서 현재 로그인된 사용자 정보 가져오기
        String userId = authentication.getName();

        boardService.updateBoard(userId, id, boardDto);
        return ResponseEntity.ok().build();
    }

    // 게시판 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, Authentication authentication) {
        // Spring Security 컨텍스트에서 현재 로그인된 사용자 정보 가져오기
        String userId = authentication.getName();

        // 삭제 로직 실행 (필요시 권한 확인 추가 가능)
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
