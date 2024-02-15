package com.fastcampus.boardserver.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // Runtime 시 동작하기위해 lifeCycle 등록
@Target(ElementType.METHOD)
public @interface LoginCheck {

    public static enum UserType {
        USER, ADMIN
    }

    UserType type();
}
