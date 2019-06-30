package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,
        ElementType.CONSTRUCTOR,
        ElementType.TYPE,
        ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Status {
    /**
     * @return - The author.
     */
    String author() default "";

    /**
     * @return - The status.
     */
    boolean finished() default false;

    /**
     * @return - The status.
     */
    int progression() default 0;

    /**
     * @return - Version control.
     */
    double version() default 1;
}
