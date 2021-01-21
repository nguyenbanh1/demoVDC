package com.nguyen.demo.rsql;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

/**
 * Custom Rsql Visitor.
 *
 * @param <T> the given object class
 */
public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<T>, Void> {

    private final GenericRsqlSpecBuilder<T> builder;
    private final boolean lenient;

    /**
     * Custom Rsql Visitor Constructor.
     */
    public CustomRsqlVisitor() {
        this(false);
    }

    public CustomRsqlVisitor(boolean lenient) {
        builder = new GenericRsqlSpecBuilder<T>();
        this.lenient = lenient;
    }

    /**
     * Build specification with {@link AndNode}.
     *
     * @param node the given {@link AndNode}
     * @param param the given param.
     * @return {@link Specification <T>}
     */
    @Override
    public Specification<T> visit(AndNode node, Void param) {
        return builder.createSpecification(node, lenient);
    }

    /**
     * Build specification with {@link OrNode}.
     *
     * @param node the given {@link OrNode}
     * @param param the given param
     * @return {@link Specification <T>}
     */
    @Override
    public Specification<T> visit(OrNode node, Void param) {
        return builder.createSpecification(node, lenient);
    }

    /**
     * Build specification with {@link ComparisonNode}.
     * @param node the given {@link ComparisonNode}
     * @param params the given params
     * @return {@link Specification <T>}
     */
    @Override
    public Specification<T> visit(ComparisonNode node, Void params) {
        return builder.createSpecification(node, lenient);
    }
}
