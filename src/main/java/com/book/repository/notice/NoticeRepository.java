package com.book.repository.notice;

import com.book.domain.alarm.Notice;
import com.book.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByUserAndChecked(User user, boolean checked);
}
