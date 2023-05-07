package com.book.config.auth;

import com.book.domain.user.User;
import com.book.repository.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username = " + username);
		User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("아이디 또는 비밀번호가 틀렸습니다."));
		return new PrincipalDetails(user);
	}

}
