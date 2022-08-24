package com.example.testcodeex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

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

    @Test
    @DisplayName("조건에 맞춰 실행 - 함수")
    void assume_ex() {
        Lunch lunch = new Lunch();
        String test_env = "LOCAL"; // 이 변수를 환경변수를 통해 값을 가져와서 활용할 수 있다.
        assumeTrue("LOCAL".equals(test_env));
        assertTrue(lunch.getLunch("test").equals("Complete"), () -> "실패시 메시지");

        assumingThat("LOCAL".equals(test_env), () -> {
            System.out.println("LOCAL 환경에서 실행될 테스트 코드");
        });

        assumingThat("DEV".equals(test_env), () -> {
            System.out.println("DEV 환경에서 실행될 테스트");
        });

        assumingThat("LIVE".equals(test_env), () -> {
            System.out.println("LIVE 환경에서 실행될 테스트");
        });
    }

    @Test
    @DisplayName("조건에 맞춰 실행 - 어노테이션 Enabled")
    @EnabledOnOs(OS.MAC)
    void enabled_ex() {
        Lunch lunch = new Lunch();
        assertTrue(lunch.getLunch("test").equals("Complete"));
    }

    @Test
    @DisplayName("조건에 맞춰 실행 - 어노테이션 Disabled")
    @DisabledOnOs(OS.MAC)
    void disabled_ex() {
        Lunch lunch = new Lunch();
        assertTrue(lunch.getLunch("test").equals("Complete"));
    }

    @Test
    @DisplayName("조건에 맞춰 실행 - 어노테이션 Variable")
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "LOCAL")
    void enabled_environment_variable_ex() {
        Lunch lunch = new Lunch();
        assertTrue(lunch.getLunch("test").equals("Complete"));
    }

    @Test
    @DisplayName("태깅 그룹 local")
    @Tag("local")
    void tag_local_ex() {
        Lunch lunch = new Lunch();
        assertTrue(lunch.getLunch("test").equals("Complete"));
    }

    @Test
    @DisplayName("태깅 그룹 dev")
    @Tag("dev")
    void tag_dev_ex() {
        Lunch lunch = new Lunch();
        assertTrue(lunch.getLunch("test").equals("Complete"));
    }

    @custom // @Test , Tag("local") 어노테이션 생략가능
    @DisplayName("커스텀 태그")
    void custom_tag_ex() {
        Lunch lunch = new Lunch();
        assertTrue(lunch.getLunch("test").equals("Complete"));
    }

}