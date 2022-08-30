package com.example.testcodeex;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@ExtendWith(FindSlowTestExtension.class) // 선언적인 등록방법 [확장모델]
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 클래스당 인스턴스를 하나만 만들어 사용한다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LunchTest {

//    프로그래밍적으로 등록하여 사용하는 방법 -> 커스터마이징이 가능하다.
//    @RegisterExtension
//    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

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
//            Thread.sleep(300); // 300 밀리초 이상의 시간이 걸려서 실패한다.
        });
    }

    @Test
    @DisplayName("조건에 맞춰 실행 - 함수")
    void assume_ex() throws InterruptedException {
        Thread.sleep(1005L); // public static native void sleep(long millis) throws InterruptedException; 문구를 출력하게됨
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

    @DisplayName("테스트 코드 반복하기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}") // 10번의 테스트 반복
    void repeat_ex(RepetitionInfo repetitionInfo) { // 매개변수 통하여 현재 테스트 정보를 얻어옴
        System.out.println("현재 " + repetitionInfo.getTotalRepetitions() + " 개의 테스트중 " + // 총 테스트 수
                repetitionInfo.getCurrentRepetition() + " 번 실행중"); // 현재 진행중인 테스트 번호
    }

    @DisplayName("테스트 코드 반복하기")
    @ParameterizedTest(name = "{index} {displayName} param={0}")
    @ValueSource(strings = {"테스트", "코드", "매개", "변수"})
    @NullAndEmptySource
    void parameterized_ex(String param) {
        System.out.println(param);
    }

    int value = 1;

    @custom
    @DisplayName("테스트 인스턴스")
    void instance_ex() {
        System.out.println("value test1 = " + value++);
        System.out.println("instance test1 = " + this);
    }

    @custom
    @DisplayName("테스트 인스턴스2")
    void instance2_ex() {
        System.out.println("value test2 = " + value++);
        System.out.println("instance test1 = " + this);
    }

    @Order(1)
    @DisplayName("테스트 순서 1")
    void order1_ex() {
        System.out.println("첫번째 테스트");
    }

    @Order(3)
    @DisplayName("테스트 순서 3")
    void order3_ex() {
        System.out.println("세번째 테스트");
    }

    @Order(2)
    @DisplayName("테스트 순서 2")
    void order2_ex() {
        System.out.println("두번째 테스트");
    }


}