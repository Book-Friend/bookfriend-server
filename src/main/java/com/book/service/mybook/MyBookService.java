package com.book.service.mybook;

import com.book.domain.MyBook.*;
import com.book.service.mybook.dto.request.MyBookCreateDto;
import com.book.service.mybook.dto.request.MyBookUpdateDto;
import com.book.domain.book.Book;
import com.book.domain.user.User;
import com.book.exception.InvalidReqBodyException;
import com.book.exception.book.UnAuthorizedAccess;
import com.book.exception.user.UserNotFoundException;
import com.book.exception.book.BookNotFoundException;
import com.book.exception.book.DuplicateBookException;
import com.book.repository.mybook.MyBookRepository;
import com.book.repository.user.UserRepository;
import com.book.service.book.BookService;
import com.book.service.mybook.dto.response.MyBookResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyBookService {

    private final MyBookRepository myBookRepository;
    private final UserRepository userRepository;
    private final BookService bookService;


    @Transactional
    public MyBook createMyBook(Long id, MyBookCreateDto myBookDto){
        User user = userRepository.findById(id).get();
        checkDuplicateBook(user, myBookDto.getIsbn());
        validateDate(myBookDto.getStartDate(), myBookDto.getEndDate());

        Book book = bookService.getBook(myBookDto.getIsbn());
        MyBook myBook = myBookDto.create(book, user);
        user.addMyBook(myBook);
        book.addMyBook(myBook);

        myBookRepository.save(myBook);

        return myBook;
    }

    @Transactional(readOnly = true)
    public MyBook getMyBook(Long id){
        return myBookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("책을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Slice<MyBookResDto> getMyBookList(Long userId, Pageable pageable){
        return myBookRepository.findMyBookList(userId, pageable);
    }

    @Transactional
    public void updateMyBook(Long userId, MyBookUpdateDto myBookDto){
        MyBook myBook = myBookRepository.findById(userId).orElseThrow(() -> new BookNotFoundException("책을 찾을 수 없습니다."));
        myBook.update(myBookDto);
    }

    @Transactional
    public void deleteMyBooks(Long id){
        MyBook myBook = getMyBook(id);
        myBook.deleteBook();
        myBookRepository.deleteById(id);
    }

    public void checkAccessPermission(Long myBookId, Long userId){
        MyBook myBook = myBookRepository.findById(myBookId).orElseThrow(() -> new BookNotFoundException("책을 찾을 수 없습니다."));
        if(!myBook.isOpen()){
            User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
            if(myBook.getUser() != user){
                throw new UnAuthorizedAccess("접근 권한이 없습니다.");
            }
        }
    }

    private void checkDuplicateBook(User user, String isbn) {
        if(myBookRepository.existsByUserAndIsbn(user, isbn)){
            throw new DuplicateBookException("이미 등록된 책입니다.");
        }
    }

    private void validateDate(LocalDate start, LocalDate end) {
        if (end.isBefore(start))
            throw new InvalidReqBodyException("date = " + start + " < " + end);
    }
}
