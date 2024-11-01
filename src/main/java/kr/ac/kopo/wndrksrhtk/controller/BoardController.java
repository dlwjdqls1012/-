package kr.ac.kopo.wndrksrhtk.controller;

import kr.ac.kopo.wndrksrhtk.entity.Notice;
import kr.ac.kopo.wndrksrhtk.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class BoardController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/board/write")
    public String showWriteForm(Model model) {
        model.addAttribute("board", new Notice());
        return "write"; // write.html 템플릿을 반환
    }

    @PostMapping("/board/write")
    public String submitWriteForm(Notice board) {
        // 생성 날짜 및 초기 조회수 설정
        board.setCreatedDate(LocalDateTime.now());
        board.setViewCount(0); // 초기 조회수 설정

        // 데이터베이스에 게시글 저장
        noticeRepository.save(board);

        return "redirect:/board"; // 게시글 목록 페이지로 리다이렉트
    }
}
