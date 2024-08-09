//package com.jm.board_back.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry corsRegistry) {
//        // 이 설정은 컨트롤러에서 어노테이션으로 설정할 수 있으며 메소드의 매개변수에서도 설정할 수 있다.
//        // 하지만 하나하나 모두 설정하기 번거롭기 때문에 설정 클래스에서 한번에 적용하였다.
//        /**
//         * addMapping()
//         * CORS를 적용할 URL 패턴을 정의한다.
//         * /** 와일드카드
//         * ex) /somePath/**
//         */
//        /**
//         * allowedOrigins()
//         * 자원을 공유를 허락할 Origin을 지정할 수 있다.
//         * 여러개 지정 allowedOrigins("http://localhost:3000", "https://test.com" , "https://www.test.com")
//         * 오류날 시 - allowedOriginPatterns("*")로 수정하여 실행해보기
//         */
//
//        /**
//         * allowedMethods()
//         * 허용할 HTTP 메소드를 지정할 수 있다.
//         * 메소드 : GET, POST, PUT, PATCH, DELETE, OPTIONS
//         */
//        /**
//         * maxAge()
//         * 원하는 시간만큼 pre-flight 리퀘스트를 캐싱할 수 있다.
//         * maxAge(3000)
//         */
//        corsRegistry
//            .addMapping("/**")
//            .allowedMethods("*")
//            .allowedOrigins("*");
//    }
//
//}
