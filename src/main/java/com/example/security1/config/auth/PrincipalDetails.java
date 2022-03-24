package com.example.security1.config.auth;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
//로그인 진행이 완료가 되면 시큐리티 (가 가지고있는) session을 만들어줍니다. (Security ContextHolder)
// -- 세션공간은 똑같은데 시큐리티 자신만의 세션공간을 가짐! 키값으로 구분함.
//오브젝트 =>Authentication 타입 객체
//Authentication 안에 User 정보가 있어야 됨.

//--클래스가 정의되어져있음
//User 오브젝트타입 => UserDetails 타입 객체

//Security Session 여기에 세션정보를 저장해줌 (여기들어갈 수 있는 객체가) => Authentication 객체 => (user 정보는)UserDetails 타입이여야함
//--꺼내쓸때는 Security Session ->Authentication ->UserDetails(PrincipalDetails) 객체 접근

import com.example.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user; //콤포지션

    public PrincipalDetails(User user){
        this.user =user;
    }

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //user.getRole(); 리턴타입이 String 타입이라 return 불가.. 그래서 정해진 타입으로 만들자.

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                //여기서 String return 타입으로 넣어줌!
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정 만료
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정 기간 지났나
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //활성화됐니
    @Override
    public boolean isEnabled() {

        //우리 사이트!! 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        //user.getLoginDate(); 현재시간 -로긴시간 => 1년 초과하면 return false;
        return true;
    }
}
