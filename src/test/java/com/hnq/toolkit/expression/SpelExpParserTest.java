package com.hnq.toolkit.expression;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.hnq.toolkit.expression.SpelExpParser.parse;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author henengqiang
 * @date 2019/10/16
 */
class SpelExpParserTest {

    @Test
    void stringEvalTest() {
        String res = parse("'Hello World'", String.class);
        assertEquals("Hello World", res);
    }

    @Test
    void stringEvalTest2() {
        String res = parse("'Hello World'.concat('!')", String.class);
        assertEquals("Hello World!", res);
    }

    @Test
    void bytesEvalTest() {
        byte[] res = parse("'Hello World'.bytes", byte[].class);
        assertEquals("[72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100]", Arrays.toString(res));
    }

    @Test
    void bytesLengthEvalTest() {
        int len = parse("'Hello World'.bytes.length", Integer.class);
        assertEquals(11, len);
    }

    @Test
    void methodEvalTest() {
        String res = parse("new String('Hello World').toUpperCase()", String.class);
        assertEquals("HELLO WORLD", res);

        char c = parse("'abc'.substring(2, 3)", char.class);
        assertEquals('c', c);
    }

    @Test
    void literalEvalTest() {
        String helloWorld = parse("'Hello World'", String.class);
        assertEquals("Hello World", helloWorld);

        double d = parse("6.022E+23", Double.class);
        assertEquals(6.022E23D, d, 0.05D);

        int maxVal = parse("0x7FFFFFFF", Integer.class);
        assertEquals(Integer.MAX_VALUE, maxVal);

        boolean trueVal = parse("true", boolean.class);
        assertTrue(trueVal);

        Object nullVal = parse("null", Object.class);
        assertNull(nullVal);
    }

    @Test
    void listEvalTest() {
        List nums = parse("{1, 2, 3, 4}", List.class);
        assertEquals(Lists.newArrayList(1, 2, 3, 4), nums);

        List listOfLists = parse("{{'a', 'b'}, {'x', 'y'}, {1, 2}}", List.class);
        List expected = Lists.newArrayList(
                Lists.newArrayList("a", "b"),
                Lists.newArrayList("x", "y"),
                Lists.newArrayList(1, 2)
        );
        assertEquals(expected, listOfLists);

        int[] num1 = parse("new int[4]", int[].class);
        assertArrayEquals(new int[4], num1);

        int[] num2 = parse("new int[]{1, 2, 3}", int[].class);
        assertArrayEquals(new int[]{1, 2, 3}, num2);

        int[][] num3 = parse("new int[4][5]", int[][].class);
        assertArrayEquals(new int[4][5], num3);
    }

    @Test
    void operatorsEvalTest() {
        boolean trueVal = parse("4 == 4", boolean.class);
        assertTrue(trueVal);

        boolean falseVal = parse(" 2 < -5.0", boolean.class);
        assertFalse(falseVal);

        boolean res = parse("'black' < 'block'", boolean.class);
        assertTrue(res);

        boolean re1 = parse("'xyz' instanceof T(int)", boolean.class);
        assertFalse(re1);

        boolean re2 = parse("'5.00' matches '^-?\\d+(\\.\\d{2})?$'", boolean.class);
        assertTrue(re2);

        boolean re3 = parse("'5.0067' matches '^-?\\d+(\\.\\d{2})?$'", boolean.class);
        assertFalse(re3);
    }

    @Test
    void logicEvalTest() {
        boolean re1 = parse("true and false", boolean.class);
        assertFalse(re1);

        boolean re2 = parse("true or false", boolean.class);
        assertTrue(re2);

        boolean re3 = parse("!true", boolean.class);
        assertFalse(re3);
    }

    @Test
    void mathematicalOperatorsEvalTest() {
        int two = parse("1 + 1", int.class);
        assertEquals(2, two);

        String str = parse("'test' + ' ' + 'string'", String.class);
        assertEquals("test string", str);

        int four = parse("1 - -3", int.class);
        assertEquals(4, four);

        double d = parse("1000.00 - 1e4", double.class);
        assertEquals(-9000, d, 0);

        int six = parse("-2 * -3", int.class);
        assertEquals(6, six);

        double tf = parse("2.0 * 3e0 * 4", double.class);
        assertEquals(24, tf, 0);

        int minusTwo = parse("6 / -3", int.class);
        assertEquals(-2, minusTwo);

        double one = parse("8.0 / 4e0 / 2", double.class);
        assertEquals(1.0, one, 0);

        int three = parse("7 % 4", int.class);
        assertEquals(3, three);

        int one2 = parse("8 / 5 % 2", int.class);
        assertEquals(1, one2);

        int minusTwentyOne = parse("1 + 2 - 3 * 8", int.class);
        assertEquals(-21, minusTwentyOne);
    }

    @Test
    void typesEvalTest() {
        Class dateClass = parse("T(java.util.Date)", Class.class);
        assertEquals(Date.class, dateClass);

        Class stringClass = parse("T(String)", Class.class);
        assertEquals(String.class, stringClass);

        boolean res = parse("T(java.math.RoundingMode).CEILING < T(java.math.RoundingMode).FLOOR", boolean.class);
        assertTrue(res);
    }

    @Test
    void functionEvalTest() throws NoSuchMethodException {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("reverseString", this.getClass().getDeclaredMethod("reverseString", String.class));

        String res = parse("#reverseString('hello')", context, String.class);
        assertEquals("olleh", res);
    }

    /**
     *  Only static methods can be called via function references.
     */
    private static String reverseString(String input) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            builder.append(input.charAt(input.length() - 1 - i));
        }
        return builder.toString();
    }

    // ---- Expression templating ----//

    @Test
    void expressionTemplatingEvalTest() {
        String randomPhrase = parse("random number is #{T(java.lang.Math).random()}", new TemplateParserContext(), String.class);
        System.out.println(randomPhrase);
    }

    private static class TemplateParserContext implements ParserContext {

        @Override
        public boolean isTemplate() {
            return true;
        }

        @Override
        public String getExpressionPrefix() {
            return "#{";
        }

        @Override
        public String getExpressionSuffix() {
            return "}";
        }
    }

}