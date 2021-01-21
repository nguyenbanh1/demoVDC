package com.nguyen.demo.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Generic RSQL specification builder.
 *
 * @param <T> the given Object.
 */
public class GenericRsqlSpecBuilder<T> {

    /**
     * Create specification by {@link Node}.
     *
     * @param node the given {@link Node}
     * @return {@link Specification <T>}
     */
    public Specification<T> createSpecification(Node node, boolean lenient) {
        if (node instanceof LogicalNode) {
            return createSpecification((LogicalNode) node, lenient);
        }
        if (node instanceof ComparisonNode) {
            return createSpecification((ComparisonNode) node, lenient);
        }
        return null;
    }

    /**
     * Create specification by {@link LogicalNode}.
     *
     * @param logicalNode the given {@link LogicalNode}
     * @return {@link Specification <T>}
     */
    public Specification<T> createSpecification(LogicalNode logicalNode, boolean lenient) {
        List<Specification<T>> specs = logicalNode.getChildren()
                .stream()
                .map(n -> createSpecification(n, lenient))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Specification<T> result = Specification.where(specs.get(0));
        if (logicalNode.getOperator() == LogicalOperator.AND) {
            result = specs.subList(1, specs.size()).stream().reduce(result, Specification::and);
        } else if (logicalNode.getOperator() == LogicalOperator.OR) {
            result = specs.subList(1, specs.size()).stream().reduce(result, Specification::or);
        }
        return result;
    }

    /**
     * Create specification by {@link ComparisonNode}.
     *
     * @param comparisonNode the given {@link ComparisonNode}
     * @return {@link Specification <T>}
     */
    public Specification<T> createSpecification(ComparisonNode comparisonNode, boolean lenient) {
        return Specification.where(
                new GenericRsqlSpecification<T>(
                        comparisonNode.getSelector(),
                        comparisonNode.getOperator(),
                        comparisonNode.getArguments(),
                        lenient
                )
        );
    }
}
