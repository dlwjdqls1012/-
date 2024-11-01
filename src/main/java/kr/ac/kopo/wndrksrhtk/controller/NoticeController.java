package kr.ac.kopo.wndrksrhtk.controller;

import kr.ac.kopo.wndrksrhtk.entity.Notice;
import kr.ac.kopo.wndrksrhtk.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // 공지사항 목록을 JSON으로 반환하는 엔드포인트
    @GetMapping("/notices/all")
    @ResponseBody
    public List<Notice> getAllNotices() {
        return noticeService.findAll();
    }

    // 새로운 공지사항을 JSON으로 생성하는 엔드포인트
    @PostMapping("/notices/create")
    @ResponseBody
    public Notice createNotice(@RequestParam String title, @RequestParam String content) {
        return noticeService.saveNotice(title, content);
    }

    // 특정 ID로 공지사항을 JSON으로 반환하는 엔드포인트
    @GetMapping("/notices/{id}")
    @ResponseBody
    public Notice getNoticeById(@PathVariable Long id) {
        return noticeService.findById(id);
    }

    // 특정 ID의 공지사항을 삭제하는 JSON 엔드포인트
    @DeleteMapping("/notices/{id}")
    @ResponseBody
    public void deleteNotice(@PathVariable Long id) {
        noticeService.delete(id);
    }

    // 공지사항 작성 페이지를 반환하는 엔드포인트
    @GetMapping("/menu_announcement/write")
    public String showNoticeWritePage() {
        return "noticeWrite"; // 템플릿 파일명
    }

    // NoticeController에 공지사항 저장을 처리하는 엔드포인트
    @PostMapping("/menu_announcement/save")
    public String saveNotice(@RequestParam String title, @RequestParam String content) {
        Notice newNotice = new Notice();
        newNotice.setTitle(title);
        newNotice.setContent(content);
        newNotice.setCreatedDate(LocalDateTime.now()); // 현재 시간 설정
        noticeService.save(newNotice); // 서비스로 저장 요청

        return "redirect:/menu_announcement"; // 저장 후 공지사항 목록으로 리다이렉트
    }

    // 공지사항 목록을 표시하는 HTML 페이지를 반환하는 엔드포인트
    @GetMapping("/menu_announcement")
    public String getAnnouncements(Model model) {
        List<Notice> notices = noticeService.findAll();
        logger.debug("공지사항 목록: {}", notices);
        model.addAttribute("notices", notices);
        return "menu_announcement"; // 템플릿 파일명
    }

    @GetMapping("/menu_announcement/view/{id}")
    public String viewNotice(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "noticeDetail";  // noticeDetail.html 파일을 반환해야 합니다.
    }

    @GetMapping("/menu_announcement/edit/{id}")
    public String editNotice(@PathVariable Long id, Model model) {
        Notice notice = noticeService.findById(id);
        model.addAttribute("notice", notice);
        return "noticeEdit"; // 수정 페이지 템플릿 이름
    }

    // 공지사항 수정 엔드포인트
    @PostMapping("/menu_announcement/update")
    public String updateNotice(@RequestParam Long id, @RequestParam String title, @RequestParam String content) {
        noticeService.updateNotice(id, title, content);
        return "redirect:/menu_announcement"; // 수정 후 목록 페이지로 이동
    }
}