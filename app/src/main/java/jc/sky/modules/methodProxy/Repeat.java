package jc.sky.modules.methodProxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @创建人 sky
 * @创建时间 15/4/15 下午9:31
 * @类描述 方法是否重复 - 默认不可重复
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Repeat {

	boolean value() default false;
}
