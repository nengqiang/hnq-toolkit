package com.hnq.toolkit.util;

import com.google.common.collect.Maps;
import com.hnq.toolkit.util.cache.GuavaCache;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author henengqiang
 * @date 2019-09-18
 */
public class RegexUtils {

    private static GuavaCache<String, Pattern> CACHE = GuavaCache.getInstance(10, TimeUnit.MINUTES, 100);

    private RegexUtils() {}

    public static Pattern compile(@Nonnull String regex) {
        return compile(regex, 0);
    }

    /**
     * Compiles the given regular expression into a pattern with the given
     * flags.
     *
     * @param regex The expression to be compiled
     * @param flags Match flags, a bit mask that may include
     * {@link Pattern#CASE_INSENSITIVE}, {@link Pattern#MULTILINE}, {@link Pattern#DOTALL},
     * {@link Pattern#UNICODE_CASE}, {@link Pattern#CANON_EQ}, {@link Pattern#UNIX_LINES},
     * {@link Pattern#LITERAL}, {@link Pattern#UNICODE_CHARACTER_CLASS}
     * and {@link Pattern#COMMENTS}
     * @return the given regular expression compiled into a pattern with the given flags
     * @throws IllegalArgumentException If bit values other than those corresponding to the defined
     * match flags are set in <tt>flags</tt>
     */
    public static Pattern compile(@Nonnull String regex, int flags) {
        return CACHE.getUnchecked(regex + "_" + flags, () -> Pattern.compile(regex, flags));
    }

    public static Matcher getMatcher(@Nonnull String regex, @Nonnull String str) {
        return compile(regex).matcher(str);
    }

    public static Matcher getMatcher(@Nonnull String regex, int flags, @Nonnull String str) {
        return compile(regex, flags).matcher(str);
    }

    public static boolean matches(@Nonnull String str, @Nonnull Pattern pattern) {
        return pattern.matcher(str).matches();
    }

    public static boolean matches(@Nonnull String str, @Nonnull String regex) {
        Pattern pattern = compile(regex);
        return matches(str, pattern);
    }

    public static boolean matches(@Nonnull String str, @Nonnull String regex, int flags) {
        Pattern pattern = compile(regex, flags);
        return matches(str, pattern);
    }

    public static boolean find(@Nonnull String str, @Nonnull Pattern pattern) {
        return pattern.matcher(str).find();
    }

    public static boolean find(@Nonnull String str, @Nonnull String regex) {
        Pattern pattern = compile(regex);
        return find(str, pattern);
    }

    public static boolean find(@Nonnull String str, @Nonnull String regex, int flags) {
        Pattern pattern = compile(regex, flags);
        return find(str, pattern);
    }

    public static String group(@Nullable String str, @Nonnull Pattern pattern, int index, String defaultValue) {
        if (str != null) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return matcher.group(index);
            }
        }

        return defaultValue;
    }

    public static String group(@Nullable String str, @Nonnull Pattern pattern, int index) {
        return group(str, pattern, index, StringUtils.EMPTY);
    }

    public static String group(@Nullable String str, @Nonnull String regex, int flags, int index, String defaultValue) {
        Pattern pattern = compile(regex, flags);
        return group(str, pattern, index, defaultValue);
    }

    public static String group(@Nullable String str, @Nonnull String regex, int index, String defaultValue) {
        Pattern pattern = compile(regex);
        return group(str, pattern, index, defaultValue);
    }

    public static String group(@Nullable String str, @Nonnull String regex, int flags, int index) {
        return group(str, regex, flags, index, StringUtils.EMPTY);
    }

    public static String group(@Nullable String str, @Nonnull String regex, int index) {
        return group(str, regex, index, StringUtils.EMPTY);
    }

    public static String group(@Nullable String str, @Nonnull String regex) {
        return group(str, regex, 1, StringUtils.EMPTY);
    }

    public static Map<Integer, String> groupAsMap(@Nullable String str, @Nonnull String regex) {
        if (str == null) {
            return Collections.emptyMap();
        }

        Map<Integer, String> groupMap = Maps.newHashMap();
        Matcher m = getMatcher(regex, str);
        if (m.find()) {
            int groupCount = m.groupCount();
            for (int i = 0; i <= groupCount; i++) {
                groupMap.put(i, m.group(i));
            }
        }
        return groupMap;
    }

    public static List<String> findAll(@Nullable String str, @Nonnull Pattern pattern, int index) {
        if (str != null) {
            List<String> list = new ArrayList<>();
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                list.add(matcher.group(index));
            }
            return list;
        }

        return Collections.emptyList();
    }

    public static List<String> findAll(@Nullable String str, @Nonnull String regex, int flags,
                                       int index) {
        Pattern pattern = compile(regex, flags);
        return findAll(str, pattern, index);
    }

    public static List<String> findAll(@Nullable String str, @Nonnull String regex, int index) {
        Pattern pattern = compile(regex);
        return findAll(str, pattern, index);
    }


    /**
     * replace substring by {@code pattern} and {@code replaceFunction}.
     *
     * @param str the string to replace
     * @param pattern the substring's pattern
     * @param replaceFunction the replace function
     * @return a replaced string
     */
    public static String replaceByPattern(@CheckForNull final String str,
                                          @Nonnull final Pattern pattern, @Nonnull final Function<Matcher, String> replaceFunction) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        return replace(str, pattern, replaceFunction);
    }

    public static String replaceByRegex(@CheckForNull final String str,
                                        @Nonnull final String regex, @Nonnull final Function<Matcher, String> replaceFunction) {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(regex)) {
            return str;
        }

        Pattern pattern = Pattern.compile(regex);
        return replace(str, pattern, replaceFunction);
    }

    private static String replace(@Nonnull String str, @Nonnull Pattern pattern,
                                  @Nonnull Function<Matcher, String> replaceFunction) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            StringBuffer result = new StringBuffer();

            do {
                String replacement = replaceFunction.apply(matcher);
                matcher.appendReplacement(result, Matcher.quoteReplacement(replacement));
            } while (matcher.find());

            matcher.appendTail(result);

            return result.toString();
        }

        return str;
    }

}
