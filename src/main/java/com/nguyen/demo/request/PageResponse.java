package com.nguyen.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Collection;

/**
 * Page Response.
 *
 * @param <T> the given Object
 */
@Getter
public class PageResponse<T> {

    @JsonProperty("total")
    private final long total;

    @JsonProperty("page_num")
    private final int pageNum;

    @JsonProperty("page_size")
    private final int pageSize;

    @JsonProperty("items")
    private Collection<T> items;

    /**
     * All Args constructor for Pagination.
     *
     * @param total the given total items from super class.
     * @param pageNum the given page number.
     * @param pageSize the given page size.
     * @param items the given items returned.
     */
    public PageResponse(long total, int pageNum, int pageSize, Collection<T> items) {
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.items = items;
    }
}
