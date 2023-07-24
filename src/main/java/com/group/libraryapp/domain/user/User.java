package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanHistory.UserLoanHistory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//저장되고 관리되어야 하는 데이터를 의미
@Entity  //스프링이 user객체와 user테이블을 같은 것으로 봄
public class User {
    @Id //primary key로 간주한다
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id = null;
    @Column(nullable = false, length = 20)
    private String name;
    private Integer age;

    //cascade:User가 삭제될 때 User와 연결된 UserLoanHistory도 삭제
    //orphanRemoval:객체간의 관계가 끊어진 데이터를 자동으로 제거
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)   //연관관계의 주인이 아닌 쪽에 mappedBy
    private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

    //JPA를 사용하기 위한 기본생성자
    protected User(){}

    public User(String name, Integer age) {
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
        }
        this.name = name;
        this.age = age;
    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void loanBook(String bookName) {
        this.userLoanHistories.add(new UserLoanHistory(this, bookName));
    }

    public void returnBook(String bookName){
        UserLoanHistory targetHistory = this.userLoanHistories.stream()
                .filter(history -> history.getBookName().equals(bookName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        targetHistory.doReturn();   //반납처리
    }
}
