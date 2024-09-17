package kr.ac.kookmin.wink.planlist.notification.aop;

import kr.ac.kookmin.wink.planlist.notification.domain.NotificationMessage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Notify {
    NotificationMessage value() default NotificationMessage.WELCOME;
}
