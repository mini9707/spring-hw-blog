package com.sparta.myblog.service;

import com.sparta.myblog.dto.AuthRequestDto;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.entity.UserRoleEnum;
import com.sparta.myblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder; // WebSecurityConfig 에서 Bean 으로 등록 후 사용

    private final UserRepository userRepository;

    //회원가입
    public void signup(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = requestDto.getRole();

        if (userRepository.findByUsername(username).isPresent()) {  // isPresent -> Optional 객체가 가진 함수 -> 그래서 Repository 에서 Optional 타입으로 반환해준다..?
            // isPresent() -> 값이 존재 -> true 반환 -> 중복값이 있기 때문에 if문 실행 !!
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    //로그인
    public void login(AuthRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 확인 (username 이 없는 경우)
        User user = userRepository.findByUsername(username).orElseThrow( // orElseThrow -> 값이 있으면 값을 반환, 없으면 예외 처리
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //비밀번호 확인 (password 가 다른 경우)
        if(!passwordEncoder.matches(password, user.getPassword())) { // matches -> 사용자가 입력한 password 를 자동으로 인코딩해서 db의 인코딩된 password와 비교 !
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}