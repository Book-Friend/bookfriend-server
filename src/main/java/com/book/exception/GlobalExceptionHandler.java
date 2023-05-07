package com.book.exception;

import com.book.exception.book.BookNotFoundException;
import com.book.exception.book.DuplicateBookException;
import com.book.exception.book.TagNotFoundException;
import com.book.exception.book.UnAuthorizedAccess;
import com.book.exception.user.DuplicateEmailException;
import com.book.exception.user.PasswordException;
import com.book.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            DuplicateEmailException.class,
            DuplicateBookException.class,
            PasswordException.class})
    public ResponseEntity<?> handleDuplicateException(final CustomRuntimeException e){
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMsg(e.getName(), e.getMessage()));
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            BookNotFoundException.class,
            TagNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(final CustomRuntimeException e){
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg(e.getName(), e.getMessage()));
    }

    @ExceptionHandler({
            UnAuthorizedAccess.class})
    public ResponseEntity<?> handleAuthorizationException(final CustomRuntimeException e){
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMsg(e.getName(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));
        log.info(e.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleReqParamExceptions(MissingServletRequestParameterException e) {
        log.info(e.getMessage());
        return ResponseEntity.badRequest().body(errorMsg(e.getParameterName(), e.getMessage()));
    }

    private Map<String, String> errorMsg(String name, String msg){
        Map<String, String> error = new HashMap<>();
        error.put(name, msg);
        return error;
    }
}
