package com.nguyen.demo.request;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.nguyen.demo.request.RequestConstants.*;
import static com.nguyen.demo.util.ReflectionUtils.findMethodAnnotation;

/**
 * Class used to resolve annotation {@link PagingAndSorting}.
 */
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PagingAndSortingResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(@Nullable MethodParameter parameter) {
        if (parameter == null) {
            return false;
        }
        return findMethodAnnotation(PagingAndSorting.class, parameter) != null;
    }

    @Override
    public PageRequest resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer,
                                       @Nullable NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (webRequest == null) {
            return null;
        }
        String pageNumParam = webRequest.getParameter(PAGE_NUMBER);
        Integer pageNum = pageNumParam != null ? Integer.parseInt(pageNumParam) : null;

        String pageSizeParam = webRequest.getParameter(PAGE_SIZE);
        Integer pageSize = pageSizeParam != null ? Integer.parseInt(pageSizeParam) : null;

        String directionParam = webRequest.getParameter(DIRECTION);
        Sort.Direction direction = directionParam != null ? Sort.Direction.fromString(directionParam) : null;

        return PageRequestBuilder.builder()
                .orderBy(webRequest.getParameter(ORDER_BY))
                .pageNum(pageNum)
                .pageSize(pageSize)
                .direction(direction).build();
    }
}
