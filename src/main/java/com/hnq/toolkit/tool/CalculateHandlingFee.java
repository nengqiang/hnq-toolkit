package com.hnq.toolkit.tool;

import org.apache.commons.lang3.time.StopWatch;

import java.util.Scanner;

/**
 * Calculate the handling fee for buying and selling a stock
 *
 * @author henengqiang
 * @date 2019/02/28
 */
public class CalculateHandlingFee {

    private static final Scanner SCAN = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            StopWatch watch = new StopWatch();
            watch.start();
            // ----------
//        calculateFeeByAmount();
            calculateFeeByPrice();
            // ----------
            watch.stop();
            System.out.println("总共耗时：" + watch.getTime() / 1000 + "s");
        }
    }

    private static void calculateFeeByAmount() {
        System.out.print("输入买入金额（元）：");
        double buyAmount = SCAN.nextDouble();
        System.out.print("输入买入股票数（股）：");
        int portionNum = SCAN.nextInt();
        System.out.print("输入卖出金额（元）：");
        double sellAmount = SCAN.nextDouble();
        System.out.println();

        calculateFeeLogic(buyAmount, portionNum, sellAmount);
    }

    private static void calculateFeeByPrice() {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.print("输入买入单价（元）：");
        double buyPrice = SCAN.nextDouble();
        System.out.print("输入买入股票数（股）：");
        int portionNum = SCAN.nextInt();
        System.out.print("输入卖出单价（元）：");
        double sellPrice = SCAN.nextDouble();
        watch.split();
        long inputTime = watch.getSplitTime();
        System.out.println("输入耗时：" + inputTime / 1000 + "s");

        double buyAmount = buyPrice * portionNum;
        double sellAmount = sellPrice * portionNum;
        calculateFeeLogic(buyAmount, portionNum, sellAmount);
        watch.stop();
        System.out.println("计算耗时：" + (watch.getTime() - inputTime) + "ms");
    }

    /**
     * 计算手续费及盈利
     * @param buyAmount     买入金额（元）
     * @param portionNum    买入股数（股）
     * @param sellAmount    卖出金额（元）
     */
    private static void calculateFeeLogic(double buyAmount, int portionNum, double sellAmount) {
        double buyTransferFee = calculateTransferFee(portionNum);
        double buyCommission = calculateCommission(buyAmount);

        double sellStampDuty = calculateStampDuty(sellAmount);
        double sellTransferFee = calculateTransferFee(portionNum);
        double sellCommission = calculateCommission(sellAmount);

        double buyFee = buyTransferFee + buyCommission;
        double sellFee = sellStampDuty + sellTransferFee + sellCommission;
        double totalFee = buyFee + sellFee;
        System.out.println(String.format("买入花费-->\n\t过户费：%.4f\n\t佣金：%.4f\n\t买入总共花费手续费：%.4f",
                buyTransferFee, buyCommission, buyFee));
        System.out.println(String.format("卖出花费-->\n\t印花税：%.4f\n\t过户费：%.4f\n\t佣金：%.4f\n\t卖出总共花费手续费：%.4f",
                sellStampDuty, sellTransferFee, sellCommission, sellFee));
        System.out.println(String.format("总手续费：%.2f，盈利：%.2f", totalFee, sellAmount - buyAmount - totalFee));
        System.out.println("单位：元");
    }

    /**
     * 计算印花税 成交金额的千分之一 只有卖方交
     */
    private static double calculateStampDuty(double sellAmount) {
        return sellAmount / 1000.0;
    }

    /**
     * 计算过户费 上海股票（60XXXX）收取，1元/1000股，不足1000股按1元收取
     */
    private static double calculateTransferFee(int portionNum) {
        int stdNum = 1000;
        if (portionNum < stdNum) {
            return 1.0D;
        }
        return portionNum % stdNum == 0 ? portionNum / (stdNum * 1.0) : portionNum / (stdNum * 1.0) + 1;
    }

    /**
     * 计算佣金 最高为成交金额的千分之三 最低5元/笔
     */
    private static double calculateCommission(double turnover) {
        double fee = turnover * 3.0 / 1000.0;
        return fee >= 5 ? fee : 5;
    }

}
