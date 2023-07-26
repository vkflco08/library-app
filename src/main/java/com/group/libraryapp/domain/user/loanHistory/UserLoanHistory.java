package com.group.libraryapp.domain.user.loanHistory;


import com.group.libraryapp.domain.user.User;

import jakarta.persistence.*;

@Entity
public class UserLoanHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    @ManyToOne
    @JoinColumn //주도권을 가진 쪽에서 활용할 수 있는 어노테이션
    private User user;
    private String bookName;
    private boolean isReturn;

    protected UserLoanHistory(){}

    //table 관계의 주도권을 가지고 있음
    public UserLoanHistory(User user, String bookName) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = false;
    }
    public void doReturn(){
        isReturn = true;
    }

    public String getBookName(){
        return this.bookName;
    }
}
