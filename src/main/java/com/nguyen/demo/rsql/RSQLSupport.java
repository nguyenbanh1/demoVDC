package com.nguyen.demo.rsql;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * RSQL support.
 */
public class RSQLSupport {

    /**
     * Parse query and create specification.
     *
     * @param search the given search
     * @param <T>    the given Object
     * @return {@link Specification <T>}
     */
    public static <T> Specification<T> rsql(String search) {
        return rsql(search, false);
    }

    public static <T> Specification<T> rsql(String search, boolean lenient) {
        if (StringUtils.isBlank(search)) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.and();
        }
        Node rootNode = new RSQLParser().parse(search);
        return rootNode.accept(new CustomRsqlVisitor<>(lenient));
    }

}
