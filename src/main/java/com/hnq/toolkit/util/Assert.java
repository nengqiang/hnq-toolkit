package com.hnq.toolkit.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Static utilities relating to condition's asserting
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class Assert {

    private Assert() {
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
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
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
        isTrue(condition, "The condition must be true");
    }

    public static void isFalse(final boolean condition, final String message) {
        isTrue(!condition, message);
    }

    public static void isFalse(final boolean condition) {
        isFalse(condition, "This condition must be false");
    }

    /**
     * Assert that an object is {@code null} .
     *
     * <pre class="code">
     * Assert.isNull(value, &quot;The value must be null&quot;);
     * </pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is not {@code null}
     */
    public static void isNull(final Object object, final String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is {@code null} .
     *
     * <pre class="code">
     * Assert.isNull(value);
     * </pre>
     *
     * @param object the object to check
     * @throws IllegalArgumentException if the object is not {@code null}
     */
    public static void isNull(final Object object) {
        isNull(object, "The object argument must be null");
    }

    /**
     * Assert that an object is not {@code null} .
     *
     * <pre class="code">
     * Assert.notNull(clazz, &quot;The class must not be null&quot;);
     * </pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(final Object object, final String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an object is not {@code null} .
     *
     * <pre class="code">
     * Assert.notNull(clazz);
     * </pre>
     *
     * @param object the object to check
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static void notNull(final Object object) {
        notNull(object, "The argument must not be null");
    }

    /**
     * Assert that the given String is empty; that is, it must be {@code null} or the empty String.
     *
     * <pre class="code">
     * Assert.isEmpty(name, &quot;Name must be empty&quot;);
     * </pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the text is empty
     */
    public static void isEmpty(final String text, final String message) {
        if (StringUtils.isNotEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String is empty; that is, it must be {@code null} or the empty String.
     *
     * <pre class="code">
     * Assert.isEmpty(name);
     * </pre>
     *
     * @param text the String to check
     * @throws IllegalArgumentException if the text is empty
     */
    public static void isEmpty(final String text) {
        isEmpty(text, "The String argument must be null or empty");
    }

    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the
     * empty String.
     *
     * <pre class="code">
     * Assert.notEmpty(name, &quot;Name must not be empty&quot;);
     * </pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the text is empty
     */
    public static void notEmpty(final String text, final String message) {
        if (StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String is not empty; that is, it must not be {@code null} and not the
     * empty String.
     *
     * <pre class="code">
     * Assert.notEmpty(name);
     * </pre>
     *
     * @param text the String to check
     * @throws IllegalArgumentException if the text is empty
     */
    public static void notEmpty(final String text) {
        notEmpty(text, "The String argument must not be null or empty");
    }

    /**
     * Assert that an array has elements; that is, it must not be {@code null} and must have at
     * least one element.
     *
     * <pre class="code"> Assert.notEmpty(array, &quot;The array must have elements&quot;); </pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(final Object[] array, final String message) {
        if (ArrayUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that an array has elements; that is, it must not be {@code null} and must have at
     * least one element.
     *
     * <pre class="code"> Assert.notEmpty(array); </pre>
     *
     * @param array the array to check
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static void notEmpty(final Object... array) {
        notEmpty(array, "The array must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a collection has elements; that is, it must not be {@code null} and must have at
     * least one element.
     *
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(final Collection<?> collection, final String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a collection has elements; that is, it must not be {@code null} and must have at
     * least one element.
     *
     * <pre class="code">
     * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
     * </pre>
     *
     * @param collection the collection to check
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static void notEmpty(final Collection<?> collection) {
        notEmpty(collection, "The collection must not be empty: it must contain at least 1 element");
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and must have at least
     * one entry.
     *
     * <pre class="code"> Assert.notEmpty(map, &quot;Map must have entries&quot;); </pre>
     *
     * @param map     the map to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(final Map<?, ?> map, final String message) {
        if (MapUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that a Map has entries; that is, it must not be {@code null} and must have at least
     * one entry.
     *
     * <pre class="code"> Assert.notEmpty(map); </pre>
     *
     * @param map the map to check
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static void notEmpty(final Map<?, ?> map) {
        notEmpty(map, "The map must not be empty; it must contain at least one entry");
    }

    /**
     * Assert that the given String doesn't has valid text content; that is, it must be {@code null}
     * or only contain whitespace character.
     *
     * <pre class="code">
     * Assert.isBlank(name, &quot;'name' must be blank&quot;);
     * </pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the text does not contain valid text content
     */
    public static void isBlank(final String text, final String message) {
        if (StringUtils.isNotBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String doesn't has valid text content; that is, it must be {@code null}
     * or only contain whitespace character.
     *
     * <pre class="code">
     * Assert.isBlank(name, &quot;'name' must be blank&quot;);
     * </pre>
     *
     * @param text the String to check
     * @throws IllegalArgumentException if the text does not contain valid text content
     */
    public static void isBlank(final String text) {
        isBlank(text, "The String argument must be null, empty or blank");
    }

    /**
     * Assert that the given String has valid text content; that is, it must not be {@code null} and
     * must contain at least one non-whitespace character.
     *
     * <pre class="code">
     * Assert.notBlank(name, &quot;'name' must not be empty&quot;);
     * </pre>
     *
     * @param text    the String to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the text does not contain valid text content
     */
    public static void notBlank(final String text, final String message) {
        if (StringUtils.isBlank(text)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given String has valid text content; that is, it must not be {@code null} and
     * must contain at least one non-whitespace character.
     *
     * <pre class="code">
     * Assert.notBlank(name, &quot;'name' must not be empty&quot;);
     * </pre>
     *
     * @param text the String to check
     * @throws IllegalArgumentException if the text does not contain valid text content
     */
    public static void notBlank(final String text) {
        notBlank(text, "The String argument must not be null, empty or blank");
    }

    /**
     * Assert that the given text does not contain the given substring.
     *
     * <pre class="code">
     * Assert.doesNotContain(name, &quot;rod&quot;, &quot;Name must not contain 'rod'&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @param message      the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the text contains the substring
     */
    public static void doesNotContain(final String textToSearch, final String substring,
                                      final String message) {
        if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring) && textToSearch
                .contains(substring)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Assert that the given text does not contain the given substring.
     *
     * <pre class="code">
     * Assert.doesNotContain(name, &quot;rod&quot;);
     * </pre>
     *
     * @param textToSearch the text to search
     * @param substring    the substring to find within the text
     * @throws IllegalArgumentException if the text contains the substring
     */
    public static void doesNotContain(final String textToSearch, final String substring) {
        doesNotContain(textToSearch, substring,
                "The String argument must not contain the substring [" + substring + "]");
    }


    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     *
     * <pre class="code">
     * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
     * </pre>
     *
     * @param array   the array to check
     * @param message the exception message to use if the assertion fails
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(final Object[] array, final String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new IllegalArgumentException(message);
                }
            }
        }
    }

    /**
     * Assert that an array has no null elements. Note: Does not complain if the array is empty!
     *
     * <pre class="code">
     * Assert.noNullElements(array);
     * </pre>
     *
     * @param array the array to check
     * @throws IllegalArgumentException if the object array contains a {@code null} element
     */
    public static void noNullElements(final Object... array) {
        noNullElements(array, "The this array must not contain any null elements");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     *
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param clazz the required class
     * @param obj   the object to check
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(final Class<?> clazz, final Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     * Assert that the provided object is an instance of the provided class.
     *
     * <pre class="code">
     * Assert.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param type    the type to check against
     * @param obj     the object to check
     * @param message a message which will be prepended to the message produced by the function
     *                itself, and which may be used to provide context. It should normally end in ":"
     *                or "." so that the generated message looks OK when appended to it.
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance
     */
    public static void isInstanceOf(final Class<?> type, Object obj, final String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(
                    (StringUtils.isNoneBlank(message) ? message + " " : "") + "Object of class [" + (
                            obj != null ? obj.getClass().getName() : "null") + "] must be an instance of "
                            + type);
        }
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     *
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType the super type to check
     * @param subType   the sub type to check
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(final Class<?> superType, final Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    /**
     * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
     *
     * <pre class="code">
     * Assert.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType the super type to check against
     * @param subType   the sub type to check
     * @param message   a message which will be prepended to the message produced by the function
     *                  itself, and which may be used to provide context. It should normally end in
     *                  ":" or "." so that the generated message looks OK when appended to it.
     * @throws IllegalArgumentException if the classes are not assignable
     */
    public static void isAssignable(final Class<?> superType, final Class<?> subType,
                                    final String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(
                    (StringUtils.isNotBlank(message) ? message + " " : "") + subType
                            + " is not assignable to " + superType);
        }
    }

    /**
     * Assert a boolean expression, throwing {@code IllegalStateException} if the test result is
     * {@code false}. Call isTrue if you wish to throw IllegalArgumentException on an assertion
     * failure.
     *
     * <pre class="code">
     * Assert.state(id == null, &quot;The id property must not already be initialized&quot;);
     * </pre>
     *
     * @param expression a boolean expression
     * @param message    the exception message to use if the assertion fails
     * @throws IllegalStateException if expression is {@code false}
     */
    public static void state(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Assert a boolean expression, throwing {@link IllegalStateException} if the test result is
     * {@code false}.
     * <p/>
     * Call {@link #isTrue(boolean)} if you wish to throw {@link IllegalArgumentException} on an
     * assertion failure.
     *
     * <pre class="code">
     * Assert.state(id == null);
     * </pre>
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if the supplied expression is {@code false}
     */
    public static void state(final boolean expression) {
        state(expression, "The state invariant must be true");
    }

}
