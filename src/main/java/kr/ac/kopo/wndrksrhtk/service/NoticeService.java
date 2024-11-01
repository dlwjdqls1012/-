package kr.ac.kopo.wndrksrhtk.service;

import kr.ac.kopo.wndrksrhtk.entity.Notice;
import kr.ac.kopo.wndrksrhtk.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notices")
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll(); // 공지사항 전체 목록 가져오기
    }

    public Notice findById(Long id) {
        return noticeRepository.findById(id).orElse(null); // 특정 공지사항 가져오기
    }

    public Notice saveNotice(String title, String content) {
        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setContent(content);
        notice.setViewCount(0); // 초기 조회수 0으로 설정
        notice.setCreatedDate(LocalDateTime.now()); // 현재 시간으로 생성일 설정
        return noticeRepository.save(notice); // 공지사항 저장
    }

    public void delete(Long id) {
        noticeRepository.deleteById(id); // 공지사항 삭제
    }
    // NoticeService 클래스에 추가
    public void save(Notice notice) {
        noticeRepository.save(notice); // 공지사항 저장
    }
    public void updateNotice(Long id, String title, String content) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid notice ID"));
        notice.setTitle(title);
        notice.setContent(content);
        noticeRepository.save(notice);
    }

}