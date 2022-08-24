package com.example.testcodeex;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 메서드에 사용이 가능하다.
@Retention(RetentionPolicy.RUNTIME) // 애노테이션 정보를 런타임 까지 유지되도록
@Tag("local") // 태그가 local 환경이다.
@Test
public @interface custom {
}
