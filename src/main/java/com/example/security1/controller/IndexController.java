package com.example.security1.controller;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, //DI(의존성 주입)
                                          @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("test/login=================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); //authentication 안에 .getPrincipal()있음
        System.out.println("authentication: "+principalDetails.getUser());

        System.out.println("userDetails: "+userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oAuth){//DI(의존성 주입)
        System.out.println("test/oauth/login=================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); //authentication 안에 .getPrincipal()있음
        System.out.println("authentication: "+oAuth2User.getAttributes());
        System.out.println("oAuth2User: "+oAuth.getAttributes());
        return "OAuth세션 정보 확인하기";
    }
    //localhost:8080/
    //localhost:8080
    @GetMapping({"","/"})
    public String index(){
        //머스테치 기본폴더 src/main/resources/
        //뷰리졸버 설정: templates (prefix), mustache (suffix) 생략가능!! dependancy에 넣어둬서

        return "index"; //src/main/resources/templates/index.mustache
    }

    //OAuth 로그인을 해도 PrincipalDetails
    //일반 로그인을 해도 PrincipalDetails
    //return되는 principalDetails이 AuthenticationPrincipal에 저장됨
   @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("PrincipalDetails>>"+principalDetails.getUser());
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

    @Secured("ROLE_ADMIN") //특정 메서드에 권한을 줄경우 간단하게 설정
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //data메서드가 실행되기 직전에 실행됨! 여러 권한 등록하고싶을때
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "개인정보";
    }

}
