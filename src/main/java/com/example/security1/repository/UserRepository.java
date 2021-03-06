package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD 함수를 JpaRepository가 들고있음
//@Repository라는 어노테이션이 없어도 IoC되요. 이유는 JpaRepository를 상속했기때문에
public interface UserRepository extends JpaRepository<User, Integer> {

    //findBy규칙 -> Username문법
    //select * from user where username =?
    public User findByUsername(String username); //JPA query methods 를 찾아 공부해볼것..

    //select * from user where email =?
    //public User findByEmail();
}
