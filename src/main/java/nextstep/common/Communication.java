package nextstep.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Communication("도메인 커뮤니케이션 용어를 정리하는 어노테이션 입니다")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Communication {
    String value();
}
