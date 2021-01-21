package com.nguyen.demo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

/**
 * PagingAndSortingRequest.
 *
 * @author An Tran <an.tran@zamo.io>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingAndSortingRequest {

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("page_num")
    private Integer pageNum;

    @JsonProperty("order_by")
    private String orderBy;

    private Sort.Direction direction;
}
