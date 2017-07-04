package com.bobo.something.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by huangbo on 2017/7/4.
 */

public class LayoutResUtil {
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LayoutId {
        int value();
    }

    public static int getLayoutRes(Class<?> cls) {
        boolean isExist = cls.isAnnotationPresent(LayoutId.class);
        if (!isExist) return 0;
        LayoutId layoutRes = cls.getAnnotation(LayoutId.class);
        return layoutRes.value();
    }
}
