package com.nguyen.demo.rsql;

import com.nguyen.demo.error.RuleViolationException;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.nguyen.demo.rsql.RsqlSearchOperation.EQUAL;
import static com.nguyen.demo.rsql.RsqlSearchOperation.GREATER_THAN;
import static com.nguyen.demo.rsql.RsqlSearchOperation.GREATER_THAN_OR_EQUAL;
import static com.nguyen.demo.rsql.RsqlSearchOperation.IN;
import static com.nguyen.demo.rsql.RsqlSearchOperation.LESS_THAN;
import static com.nguyen.demo.rsql.RsqlSearchOperation.LESS_THAN_OR_EQUAL;
import static com.nguyen.demo.rsql.RsqlSearchOperation.NOT_EQUAL;
import static com.nguyen.demo.rsql.RsqlSearchOperation.NOT_IN;

/**
 * Generic RSQL Specification.
 *
 * @param <T> the given Object
 */
@Slf4j
@AllArgsConstructor
public class GenericRsqlSpecification<T> implements Specification<T> {

    private static final long serialVersionUID = -5300138753428531067L;

    private String property;

    private transient ComparisonOperator operator;

    private List<String> arguments;

    private boolean lenient;

    @Override
    public Predicate toPredicate(@NonNull Root<T> root,
                                 @NonNull CriteriaQuery<?> query,
                                 @NonNull CriteriaBuilder builder) {
        List<?> args = castArguments(root);
        Object argument = args.get(0);

        RsqlSearchOperation rsqlOperator = RsqlSearchOperation.getSimpleOperator(this.operator);
        if (rsqlOperator == EQUAL) {
            if (argument == null) {
                return builder.isNull(parseProperty(root, property));
            }
            if (argument instanceof String) {
                return builder.like(builder.lower(parseProperty(root, property)),
                    argument.toString().replace('*', '%').toLowerCase());
            }
            if (argument instanceof Instant) {
                return builder.greaterThanOrEqualTo(parseProperty(root, property).as(Instant.class), (Instant) argument);
            }
            return builder.equal(parseProperty(root, property), argument);
        }
        if (rsqlOperator == NOT_EQUAL) {
            if (argument == null) {
                return builder.isNotNull(parseProperty(root, property));
            }
            if (argument instanceof String) {
                return builder.notLike(parseProperty(root, property), argument.toString().replace('*', '%'));
            }
            if (argument instanceof Instant) {
                return builder.greaterThanOrEqualTo(parseProperty(root, property).as(Instant.class), (Instant) argument);
            }
            return builder.notEqual(parseProperty(root, property), argument);
        }
        if (rsqlOperator == GREATER_THAN) {
            if (argument instanceof LocalDate) {
                return builder.greaterThan(parseProperty(root, property).as(LocalDate.class), (LocalDate) argument);
            }
            if (argument instanceof Instant) {
                return builder.greaterThan(parseProperty(root, property).as(Instant.class), (Instant) argument);
            }
            return builder.greaterThan(parseProperty(root, property), argument != null ? argument.toString() : null);
        }
        if (rsqlOperator == GREATER_THAN_OR_EQUAL) {
            if (argument instanceof LocalDate) {
                return builder.greaterThanOrEqualTo(
                        parseProperty(root, property).as(LocalDate.class), (LocalDate) argument);
            }
            if (argument instanceof Instant) {
                return builder.greaterThanOrEqualTo(parseProperty(root, property).as(Instant.class), (Instant) argument);
            }
            return builder.greaterThanOrEqualTo(parseProperty(root, property),
                    argument != null ? argument.toString() : null);
        }
        if (rsqlOperator == LESS_THAN) {
            if (argument instanceof LocalDate) {
                return builder.lessThan(parseProperty(root, property).as(LocalDate.class), (LocalDate) argument);
            }
            if (argument instanceof Instant) {
                return builder.lessThan(parseProperty(root, property).as(Instant.class), (Instant) argument);
            }
            return builder.lessThan(parseProperty(root, property), argument != null ? argument.toString() : null);
        }
        if (rsqlOperator == LESS_THAN_OR_EQUAL) {
            if (argument instanceof LocalDate) {
                return builder.lessThanOrEqualTo(
                        parseProperty(root, property).as(LocalDate.class), (LocalDate) argument);
            }
            if (argument instanceof Instant) {
                return builder.lessThanOrEqualTo(
                        parseProperty(root, property).as(Instant.class), (Instant) argument);
            }
            return builder.lessThanOrEqualTo(
                    parseProperty(root, property), argument != null ? argument.toString() : null);
        }
        if (rsqlOperator == IN) {
            return parseProperty(root, property).in(args);
        }
        if (rsqlOperator == NOT_IN) {
            return builder.not(parseProperty(root, property).in(args));
        }
        return null;
    }

    /**
     * Cast arguments to Object.
     *
     * @param root the given {@link Root}
     * @return A list of {@link Object}
     */
    @SuppressWarnings("unchecked")
    private List<Object> castArguments(final Root<T> root) {
        List<Object> castedArgs = new ArrayList<>();
        Class<?> type = getJavaType(root, property);
        for (String arg : arguments) {
            if (type.equals(Integer.class)) {
                castedArgs.add(!arg.equals("null") ? parseArgument(() -> Integer.parseInt(arg)) : null);
            } else if (type.equals(Long.class)) {
                castedArgs.add(!arg.equals("null") ? parseArgument(() -> Long.parseLong(arg)) : null);
            } else if (type.isEnum()) {
                castedArgs.add(!arg.equals("null") ? parseArgument(() ->Enum.valueOf((Class<Enum>) type, arg)) : null);
            } else if (type.equals(LocalDate.class)) {
                castedArgs.add(!arg.equals("null") ? parseArgument(() -> LocalDate.parse(arg)) : null);
            } else if (type.equals(Boolean.class)) {
                castedArgs.add(!arg.equals("null") ? parseArgument(() -> parseBoolean(arg)) : null);
            } else if (type.equals(Instant.class)) {
                castedArgs.add(!arg.equals("null") ? parseArgument(() -> Instant.parse(arg)) : null);
            } else {
                castedArgs.add(arg);
            }
        }
        return castedArgs;
    }

    private <E> Object parseArgument(Supplier<E> parser) {
        try {
            return parser.get();
        } catch (Exception e) {
            if (!lenient) {
                throw e;
            } else {
                log.warn("Invalid data type", e);
            }
        }
        return null;
    }

    /**
     * Get java type with attribute path.
     *
     * @param root      the given {@link Root}
     * @param attribute the given attribute path
     * @return {@link Class}
     */
    private Class<?> getJavaType(final Root<T> root, String attribute) {
        return parseProperty(root, attribute).getJavaType();
    }

    /**
     * Parse attribute path.
     *
     * @param root      the given {@link Root}
     * @param attribute the given attribute path
     * @return {@link Path}
     */
    private Path<String> parseProperty(final Root<T> root, String attribute) {
        List<String> pros = Arrays.asList(attribute.split("\\."));
        Deque<String> prosQueue = new ArrayDeque<>(pros);

        String propertyFirst = prosQueue.pop();
        Path<String> path = getPath(root, propertyFirst);

        if (Set.class.isAssignableFrom(path.getJavaType())) {
            if (prosQueue.isEmpty()) {
                throw new RuleViolationException(String.format("Not support query with %s", propertyFirst));
            }
            String prosNext = prosQueue.pop();
            path = root.join(propertyFirst).get(prosNext);
        } else {
            path = getPath(root, propertyFirst);
        }
        while (!prosQueue.isEmpty()) {
            String property = prosQueue.pop();
            path = getPath(path, property);
            if (Set.class.isAssignableFrom(path.getJavaType())) {
                throw new RuleViolationException(String.format("Not support nested Join level 2 for property %s", property));
            }
        }
        return path;
    }

    private Path<String> getPath(Root<T> root, String property) {
        try {
            return root.get(property);
        } catch (Exception e) {
            throw new RuleViolationException(String.format("%s property haven't been supported", property));
        }
    }

    private Path<String> getPath(Path<String> path, String property) {
        try {
            return path.get(property);
        } catch (Exception e) {
            throw new RuleViolationException(String.format("%s property haven't been supported", property));
        }
    }

    /**
     * Parse boolean.
     *
     * @param value the given value
     * @return true/false
     */
    public static boolean parseBoolean(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }
        throw new RuleViolationException(String.format("Not parse %s to Boolean type", value));
    }
}
