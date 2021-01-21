package com.nguyen.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ReflectionUtils {
    /**
     * Map the given object from the given select fields with token ","  to new DTO.
     *
     * @param clazz  the given class to be transform.
     * @param select the given string of fields
     * @param object the given data from the given class
     * @return new transform
     */
    public static Map<String, Object> map(Class<?> clazz, String select, Object object) {
        Map<String, Object> map = new HashMap<>();
        String[] selectFields = select.split(",");
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            if (f.getAnnotation(javax.persistence.Column.class) == null) {
                continue;
            }
            for (String field : selectFields) {
                String name = f.getAnnotation(javax.persistence.Column.class).name();
                if (!name.equals(field)) {
                    continue;
                }
                f.setAccessible(true);
                try {
                    map.put(field, f.get(object));
                } catch (IllegalAccessException e) {
                    log.debug("Exception occur when process mapping ", e);
                }
            }
        }
        return map;
    }

    /***
     * Find the given annotationClass from the given methodParameter.
     *
     * @param annotationClass the given annotationClass to find.
     * @param parameter the given {@link MethodParameter}
     * @param <T> the given generic {@link Annotation}
     * @return Annotation
     */
    public static <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass,
                                                                MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(),
                    annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }
}
