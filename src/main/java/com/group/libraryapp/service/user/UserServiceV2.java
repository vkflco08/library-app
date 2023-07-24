package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래 있는 함수가 시작될 때 start transaction;을 한다.
    //예외 없이 잘 끝났다면 commit
    //예외 발생할 경우 rollback
    //영속성 컨텍스트로 인해 쿼리는 한번에 실행됨. [쓰기지연]
    //id를 기준으로 entity를 기억함. [1차 캐싱]
    @Transactional
    public void saveUser(UserCreateRequest request){
        userRepository.save(new User(request.getName(), request.getAge()));
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
        return userRepository.findAll().stream()
            .map(UserResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserUpdateRequest request){
        //select * from user where id = ?;
        //Optional<User>
        User user = userRepository.findById(request.getId())
            .orElseThrow(IllegalArgumentException::new);

        //객체 update, save 메소드 호출
        user.updateName(request.getName());

        //트렌젝션으로 인해 사용된 '영속성 컨텍스트'로 인해 변경 상태를 자동으로 저장. [dirty save]
        //userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String name){
        // select * from user where name = ?
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
