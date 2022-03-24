package com.example.security1.controller;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //View를 리턴하겠다!
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index(){
        //머스테치 기본폴더 src/main/resources/
        //뷰리졸버 설정: templates (prefix), mustache (suffix) 생략가능!! dependancy에 넣어둬서

        return "index"; //src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return"user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return"admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return"manager";
    }

    //스프링 시큐리티 해당주소를 낚아챔!! - SecurityConfig파일 생성 후 작동안함.
    //로그인
    @GetMapping("/loginForm")
    public String login(){
        return"loginForm";
    }

    //회원가입
    @GetMapping("/joinForm")
    public String joinForm(){
        return"joinForm";
    }

    //회원가입
    @PostMapping("/join")
    public @ResponseBody String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        //id, createDate 자동으로 만들어진다.
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user); //회원가입 잘됨. 비밀번호 :1234 => 시큐리티로 로그인 할 수 없음.
        // 이유는 패스워드가 암호화가 안되있기 때뮨!

        return"redirect:/loginForm";
    }

}
