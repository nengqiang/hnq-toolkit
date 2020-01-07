package com.hnq.toolkit.util;

import java.util.Collection;
import java.util.Map;

/**
 * Static utilities relating to any condition's checking.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class Preconditions {

    private Preconditions() {
    }

    /**
     * Assert a boolean expression, throwing {@code IllegalArgumentException} if the test result is
     * {@code false}.
     *
     * <pre class="code">
     * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
     * </pre>
     *
     * @param condition a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(final boolean condition, final String message) {
        Assert.isTrue(condition, message);
    }

    /**
     * Assert a boolean expression, throwing {@code IllegalArgumentException} if the test result is
     * {@code false}.
     *
     * <pre class="code">
     * Assert.isTrue(i &gt; 0);
     * </pre>
     *
     * @param condition a boolean expression
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(final boolean condition) {
        Assert.isTrue(condition);
    }

    public static void isFalse(final boolean condition, final String message) {
        Assert.isFalse(condition, message);
    }

    public static void isFalse(final boolean condition) {
        Assert.isFalse(condition);
    }

    /**
     * check if {@code object} is null.
     *
     * @see Assert#isNull(Object, String)
     */
    public static void isNull(final String name, final Object object) {
        Assert.isNull(object, "Parameter named '" + name + "' must be null");
    }

    /**
     * check if {@code object} is not null.
     *
     * @see Assert#notNull(Object, String)
     */
    public static void notNull(final String name, final Object object) {
        Assert.notNull(object, "Parameter named '" + name + "' must not be null");
    }

    /**
     * check if {@code text} is null or empty.
     *
     * @see Assert#isEmpty(String, String)
     */
    public static void isEmpty(final String name, final String text) {
        Assert.isEmpty(text, "Parameter named '" + name + "' must be null or empty");
    }

    /**
     * check if {@code text} is not null or not empty.
     *
     * @see Assert#notEmpty(String, String)
     */
    public static void notEmpty(final String name, final String text) {
        Assert.notEmpty(text, "Parameter named '" + name + "' must not be null or empty");
    }

    /**
     * check if {@code array} is not empty.
     *
     * @see Assert#notEmpty(Object[], String)
     */
    public static void notEmpty(final String name, final Object[] array) {
        Assert.notEmpty(array,
                "Array named '" + name + "' must not be empty: it must contain at least 1 element");
    }

    /**
     * check if {@code collection} is not empty.
     *
     * @see Assert#notEmpty(Collection, String)
     */
    public static void notEmpty(final String name, final Collection<?> collection) {
        Assert.notEmpty(collection,
                "Collection named '" + name + "' must not be empty: it must contain at least 1 element");
    }

    /**
     * check if {@code map} is not empty.
     *
     * @see Assert#notEmpty(Map, String)
     */
    public static void notEmpty(final String name, final Map<?, ?> map) {
        Assert.notEmpty(map,
                "Map named '" + name + "' must not be empty; it must contain at least one entry");
    }

    /**
     * check if {@code text} is blank.
     *
     * @see Assert#isBlank(String, String)
     */
    public static void isBlank(final String name, final String text) {
        Assert.isBlank(text, "Parameter named '" + name + "' must be null, empty or blank");
    }

    /**
     * check if {@code text} is not blank.
     *
     * @see Assert#notBlank(String, String)
     */
    public static void notBlank(final String name, final String text) {
        Assert.notBlank(text, "Parameter named '" + name + "' must not be null, empty or blank");
    }

    /**
     * check if {@code array} does not have null element.
     *
     * @see Assert#noNullElements(Object[], String)
     */
    public static void noNullElements(final String name, final Object[] array) {
        Assert.noNullElements(array, "Array named '" + name + "' must not contain any null elements");
    }

}
