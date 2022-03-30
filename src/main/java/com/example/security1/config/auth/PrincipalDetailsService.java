package com.example.security1.config.auth;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC되어있는 loadUserByUsername 함수가 실행 -> 그냥 규칙임
// -- 이타입으로 만들어줘야함..
@Service
public class PrincipalDetailsService implements UserDetailsService {

    //view에서 /login이 호출되면 spring 은 IoC컨테이너에서 UserDetailsService 로 등록된 타입을 찾음->PrincipalDetailsService
    //찾으면 바로 loadUserByUsername 호출. 이때 넘어온 파라미터 username을 가져옴!

    @Autowired
    private UserRepository userRepository;

    //시큐리티 session = Authentication = UserDetails ->
    //시큐리티 session = Authentication(내부 UserDetails) ->
    //시큐리티 session(내부 Authentication(내부 UserDetails))
    //loadUserByUsername 요놈이 알아서 쏙 넣어줌

    //함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //String username 해당 username 과 view에 <input> name="username" 이 같아야함!!
        //같지 않으면 view에서 동작은 하지만 여기서 매칭이 안됨
        System.out.println("username: "+username);
        User userEntity = userRepository.findByUsername(username); //원래는 기본적인 CRUD만 들고있기때문에 findByUserName() 만들자
        if(userEntity!=null){
            return new PrincipalDetails(userEntity); //꼭 user 를 넣어야지 활용하기 편함
        }
        return null;
    }
}
