package com.nguyen.demo.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * PageRequestBuilder.
 *
 */
public final class PageRequestBuilder {

    /**
     * Default page size.
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Default page num.
     */
    public static final int DEFAULT_PAGE_NUM = 0;


    private Integer pageSize;

    private Integer pageNum;

    private String orderBy;

    private Sort.Direction direction;

    /**
     * Private constructor for {@link PageRequestBuilder}.
     */
    private PageRequestBuilder() {
        this.pageSize = DEFAULT_PAGE_SIZE;
        this.pageNum = DEFAULT_PAGE_NUM;
        this.orderBy = "id";
        this.direction = Sort.Direction.ASC;
    }

    /**
     * Construct an instance of {@link PageRequestBuilder}.
     *
     * @return an instance of {@link PageRequestBuilder}.
     */
    public static PageRequestBuilder builder() {
        return new PageRequestBuilder();
    }

    /**
     * Set page size.
     *
     * @param number the given number.
     * @return an instance of {@link PageRequestBuilder}.
     */
    public PageRequestBuilder pageSize(Integer number) {
        if (number == null) {
            return this;
        }
        this.pageSize = number;
        return this;
    }

    /**
     * Set page number.
     *
     * @param input the given number.
     * @return an instance of {@link PageRequestBuilder}.
     */
    public PageRequestBuilder pageNum(Integer input) {
        if (input == null) {
            return this;
        }
        this.pageNum = input;
        return this;
    }

    /**
     * Set order by.
     *
     * @param input the given input.
     * @return an instance of {@link PageRequestBuilder}.
     */
    public PageRequestBuilder orderBy(String input) {
        if (input == null) {
            return this;
        }
        this.orderBy = input;
        return this;
    }

    /**
     * Set direction.
     *
     * @param input the given direction. Default is {@code ASC}.
     * @return an instance of {@link PageRequestBuilder}.
     */
    public PageRequestBuilder direction(Sort.Direction input) {
        if (input == null) {
            return this;
        }
        this.direction = input;
        return this;
    }

    /**
     * Build an instance of {@link PageRequest}.
     *
     * @return an instance of {@link PageRequest}.
     */
    public PageRequest build() {
        return PageRequest.of(pageNum, pageSize, direction, orderBy);
    }

}
