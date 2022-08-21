package com.example.testcodeex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LunchTest {

    @Test
    @DisplayName("점심추천 만들기")
    void create_lunch() {
        Lunch lunch = new Lunch();

        assertAll( // 한 개의 테스트 케이스가 실패해도 두개 모두 동작하도록 해준다.
                () -> assertEquals("Complete", lunch.getLunch("test"),
                        () -> "점심 만들기 성공시 Complete 여야한다."), // getLunch라는 메서드 실행시 Complete 가 반환되는지 체크
                () -> assertNotNull(lunch) // lunch 가 null이 아닌지 체크
        );

        assertTrue(lunch.getLunch("test").equals("Complete"), () -> "실패시 메시지");

        // 예외를 던져주는지 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,() -> lunch.getLunch("throw"));
        // 받아온 예외메시지가 원하는 예외 메세지와 동일한지까지 체크
        assertEquals("예외처리 확인", exception.getMessage());

        // 설정된 시간안에 동작하는지 확인
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            lunch.getLunch("test");
            Thread.sleep(300); // 300 밀리초 이상의 시간이 걸려서 실패한다.
        });
    }

}