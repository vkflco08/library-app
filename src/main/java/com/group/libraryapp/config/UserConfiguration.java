package com.group.libraryapp.config;

import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/*
@Configuration, @Bean : 외부 라이브러리나 프레임워크에서 만든 클래스를 빈으로 등록할 때
 */
@Configuration
public class UserConfiguration {
    @Bean
    public UserJdbcRepository userRepository(JdbcTemplate jdbcTemplate){
        return new UserJdbcRepository(jdbcTemplate);
    }
}

/*

사용자가 클래스를 직접 만든 경우 @Service @Repository 를 사용

@Component : 스프링 서버가 뜰 때 자동으로 감지.
컨트롤러, 서비스, 리포지토리가 아닌데 개발자가 직접 작성한 클래스의 경우 사용

 */