package com.nguyen.demo.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.AllArgsConstructor;

/**
 * Enum that present map Operations of Rsql.
 */
@AllArgsConstructor
public enum RsqlSearchOperation {

    /**
     * Equal operation.
     */
    EQUAL(RSQLOperators.EQUAL),

    /**
     * Not_Equal operation.
     */
    NOT_EQUAL(RSQLOperators.NOT_EQUAL),

    /**
     * Greater_Than operation.
     */
    GREATER_THAN(RSQLOperators.GREATER_THAN),

    /**
     * Greater_Than_Or_Equal operation.
     */
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),

    /**
     * Less_Than operation.
     */
    LESS_THAN(RSQLOperators.LESS_THAN),

    /**
     * Less_Than_Or_Equal operation.
     */
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),

    /**
     * In operation.
     */
    IN(RSQLOperators.IN),

    /**
     * Not_In operation.
     */
    NOT_IN(RSQLOperators.NOT_IN);

    private final transient ComparisonOperator operator;

    /**
     * Get simple operator by {@link ComparisonOperator}.
     *
     * @param operator the given {@link ComparisonOperator}
     * @return {@link RsqlSearchOperation}
     */
    public static RsqlSearchOperation getSimpleOperator(ComparisonOperator operator) {
        for (RsqlSearchOperation item : values()) {
            if (item.operator == operator) {
                return item;
            }
        }
        throw new RsqlNotSupportOperationException();
    }
}

