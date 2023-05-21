package com.book.service.notice;

import com.book.domain.alarm.Notice;
import com.book.domain.user.User;
import com.book.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void addNotice(User user, String content){
        Notice notice = Notice.builder()
                .content(content)
                .user(user)
                .build();
        noticeRepository.save(notice);
    }

    public List<Notice> getNotice(User user, boolean old){
        return noticeRepository.findByUserAndChecked(user, old);
    }

    public void markAsRead(List<Notice> notices){
        notices.forEach(Notice::read);
    }

    public void deleteNotice(User user, Long id){
        noticeRepository.deleteById(id);
    }

//    public void sendToClient(SseEmitter sseEmitter, String id, Object data){
//        try {
//            sseEmitter.send(SseEmitter.event()
//                    .id(id)
//                    .name("sse")
//                    .data(data));
//        } catch (IOException exception) {
//            emitterRepository.deleteById(id);
//            throw new RuntimeException("연결 오류");
//        }
//    }
}
