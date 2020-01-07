package com.hnq.toolkit.util;

/**
 * @author henengqiang
 * @date 2019/09/06
 */
public class MathUtils {

    /**
     * 高效率的幂运算
     *
     * @param x 底数
     * @param n 指数
     * @return  运算结果
     * @see     native method {@link Math#pow(double, double)}
     */
    public static long pow(long x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        if (isEven(n)) {
            return pow(x * x, n / 2);
        } else {
            return pow(x * x, n / 2) * x;
        }
    }

    /**
     * 十进制转其他进制
     * @param num   十进制数
     * @param radix 其他进制
     * @return      其他进制数
     * @see         Integer#toBinaryString(int),Integer#toOctalString(int),Integer#toHexString(int)
     */
    public static int tenDecimalToOther(int num, int radix) {
        //使用StringBuilder的reverse方法
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            sb.append(num % radix);
            num /= radix;
        }

        return Integer.parseInt(sb.reverse().toString());
    }

    private static boolean isEven(int x) {
        return x % 2 == 0;
    }

}
