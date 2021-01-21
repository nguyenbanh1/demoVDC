package com.nguyen.demo.request;

import org.springframework.data.domain.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * PageResponseBuilder.
 *
 * @param <T> the given entity
 * @param <R> the given Object's mapping.
 */
public class PageResponseBuilder<T, R> {

    /**
     * Build {@link PageResponseBuilder<T, R>}.
     *
     * @param page the given pageRequest
     * @param mapFunction the given mapFunction
     * @param <T> the given Object which want to map
     * @param <R> the given Object response.
     * @return {@link PageResponse<R>}
     */
    public static <T, R> PageResponse<R> build(Page<T> page, Function<? super T, ? extends R> mapFunction) {
        return new PageResponse<>(
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.stream().map(mapFunction).collect(Collectors.toList()));
    }

    /**
     * Build {@link PageResponseBuilder<T>}.
     *
     * @param page the given pageRequest
     * @param <T> the given Object which want to map
     * @return {@link PageResponse<T>}
     */
    public static <T> PageResponse<T> build(Page<T> page) {
        return new PageResponse<>(
                page.getTotalElements(),
                page.getNumber(),
                page.getSize(),
                page.stream().collect(Collectors.toList()));
    }
}
