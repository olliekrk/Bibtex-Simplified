package parser;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BibtexConstraints {

    boolean required() default false;

    boolean multiple() default false;

}