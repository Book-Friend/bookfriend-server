package com.book.service;

import com.book.domain.recommend.Recommend;
import com.book.domain.user.*;
import com.book.domain.user.dto.request.PasswordChangeDto;
import com.book.domain.user.dto.request.UserCreateDto;
import com.book.domain.user.dto.request.UserUpdateDto;
import com.book.domain.user.dto.response.ProfileResDto;
import com.book.exception.user.DuplicateEmailException;
import com.book.exception.user.PasswordException;
import com.book.exception.user.UserNotFoundException;
import com.book.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TagService tagService;
    private final RecommendService recommendService;


    @Transactional
    public User signUp(UserCreateDto userCreateDto){
        checkDuplicateUser(userCreateDto.getEmail());
        userCreateDto.setPassword(encoder.encode(userCreateDto.getPassword()));
        User user = userCreateDto.toEntity();
        userRepository.save(user);
        return user;
    }

    public void checkDuplicateUser(String email){
        userRepository.findByEmail(email).ifPresent(param -> {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        });
    }

    public User getUserInfo(String email){
        return userRepository.findByEmail(email).get();
    }

    public ProfileResDto getUserProfile(Long id){
        User user = findUser(id);
        ProfileResDto profileResDto = user.toProfile();
        profileResDto.setMyBooks(user.getMyBooks());

        profileResDto.setRecommends(user.getRecommends().stream()
                .map(Recommend::toResDto).collect(Collectors.toList()));
        return profileResDto;
    }

    public ProfileResDto getProfile(Long id){
        User user = findUser(id);
        ProfileResDto profileResDto = user.toProfile();
        profileResDto.setMyBooks(user.getMyBooks());

        profileResDto.setRecommends(user.getRecommends().stream()
                .map(Recommend::toResDto).collect(Collectors.toList()));

        return profileResDto;
    }


    @Transactional
    public void deleteUser(User user){
        List<Recommend> recommendList = recommendService.getRecommendList(user.getId());
        for(Recommend recommend : recommendList){
            tagService.deleteTag(recommend);
        }
        userRepository.deleteById(user.getId());
    }

    @Transactional
    public void updatePassword(Long id, PasswordChangeDto password) {
        User user = findUser(id);
        String encPassword = encoder.encode(password.getCurPassword());
        if(user.getPassword().equals(encPassword)){
            user.updatePassword(password.getNewPassword());
        } else {
            throw new PasswordException("패스워드가 일치하지 않습니다.");
        }
    }

    @Transactional
    public void updateProfile(Long id, UserUpdateDto updateDto) {
        User user = findUser(id);
        user.updateProfile(updateDto);
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


}