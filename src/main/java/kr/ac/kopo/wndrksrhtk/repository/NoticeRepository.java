package kr.ac.kopo.wndrksrhtk.repository;

import kr.ac.kopo.wndrksrhtk.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}