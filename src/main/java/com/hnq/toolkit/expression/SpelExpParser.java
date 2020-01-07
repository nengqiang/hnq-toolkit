package com.hnq.toolkit.expression;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * Spring Expression Language
 *
 * @author henengqiang
 * @date 2019/10/16
 * @see <a href="https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/expressions.html">SpEL doc</a>
 */
@Slf4j
public class SpelExpParser {

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    private SpelExpParser() {}

    public static <T> T parse(String expression, Class<T> clazz) {
        return PARSER.parseExpression(expression).getValue(clazz);
    }

    public static <T> T parse(String expression, ParserContext context, Class<T> clazz) {
        return PARSER.parseExpression(expression, context).getValue(clazz);
    }

    public static <T> T parse(String expression, StandardEvaluationContext context, Class<T> clazz) {
        return PARSER.parseExpression(expression).getValue(context, clazz);
    }

    public static <T> T parse(String expression, T defaultValue, Class<T> clazz) {
        if (StringUtils.isBlank(expression)) {
            log.warn("Blank expression and return default value: {}", defaultValue);
            return defaultValue;
        }
        return parse(expression, clazz);
    }

}
