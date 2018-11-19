package utilities;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BibtexFieldConstraints {

    boolean required() default false;

    boolean multiple() default false;

}