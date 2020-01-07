package com.hnq.toolkit.game;

/**
 * @author henengqiang
 * @date 2019/03/28
 */
public class PrintShapes {

    public static void main(String[] args) {
        printCocaCola();
    }

    /**
     * 失败的作品
     */
    private static void printCocaCola() {
        int capAndBottleNeckLong = 4;
        int capAndBottleNeckHeight = 2;
        int capAndBottleNeckBlankNum = 3;
        // 瓶盖
        for (int i = 0; i < capAndBottleNeckHeight; i++) {
            for (int j = 0; j < capAndBottleNeckBlankNum; j++) {
                System.out.print("  ");
            }
            // 微调
            System.out.print(" ");
            for (int j = 0; j < capAndBottleNeckLong; j++) {
                System.out.print("📕");
            }
            System.out.println();
        }
        // 瓶颈
        for (int i = 0; i < capAndBottleNeckHeight; i++) {
            for (int j = 0; j < capAndBottleNeckBlankNum; j++) {
                System.out.print("  ");
            }
            // 微调
            System.out.print(" ");
            for (int j = 0; j < capAndBottleNeckLong; j++) {
                System.out.print("⚫");
            }
            System.out.println();
        }
        // 瓶颈到瓶身的斜坡
        int slopStdNum = capAndBottleNeckLong + 1;
        int slopStdBlank = capAndBottleNeckBlankNum * 2 - 1;
        for (int i = 0; i < capAndBottleNeckHeight * 2; i++) {
            for (int j = 0; j < slopStdBlank; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < slopStdNum; j++) {
                System.out.print("⚫");
            }
            System.out.println();
            slopStdNum++;
            slopStdBlank--;
        }
        // 瓶身
        int bottleBodyHeight = capAndBottleNeckHeight * 4;
        int bottleBodyLong = slopStdNum;
        for (int i = 0; i < bottleBodyHeight; i++) {
            for (int j = 0; j < slopStdBlank; j++) {
                System.out.print(" ");
            }
            if (i == 3) {
                for (int j = 0; j < (bottleBodyLong - 3) / 2; j++) {
                    System.out.print("🔴");
                }
                System.out.print("可口可乐");
                for (int j = 0; j < (bottleBodyLong - 3) / 2; j++) {
                    System.out.print("🔴");
                }
            } else {
                for (int j = 0; j < bottleBodyLong; j++) {
                    System.out.print("🔴");
                }
            }
            System.out.println();
        }
        // 瓶椎 + 瓶腰
        int bottleVertebraHeight = bottleBodyHeight / 2;
        int bottleVertebraStdLong = slopStdNum;
        for (int i = 0; i < bottleVertebraHeight / 2; i++) {
            for (int j = 0; j < slopStdBlank; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < bottleVertebraStdLong; j++) {
                System.out.print("⚫");
            }
            System.out.println();
        }
        for (int i = 0; i < bottleVertebraHeight / 2; i++) {
            for (int j = 0; j < slopStdBlank + 1; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < bottleVertebraStdLong - 1; j++) {
                System.out.print("⚫");
            }
            System.out.println();
        }
        // 瓶腰
        for (int i = 0; i < bottleVertebraHeight / 2; i++) {
            for (int j = 0; j < slopStdBlank + 2; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < bottleVertebraStdLong - 2; j++) {
                System.out.print("⚫");
            }
            System.out.println();
        }
        for (int i = 0; i < bottleVertebraHeight / 2; i++) {
            for (int j = 0; j < slopStdBlank + 1; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < bottleVertebraStdLong - 1; j++) {
                System.out.print("⚫");
            }
            System.out.println();
        }
        // 瓶股
        for (int i = 0; i < bottleVertebraHeight / 2 + 1; i++) {
            for (int j = 0; j < slopStdBlank; j++) {
                System.out.print(" ");
            }
            for (int j = 0; j < bottleVertebraStdLong; j++) {
                System.out.print("⚫");
            }
            System.out.println();
        }
        // 瓶脚
        // 大腿
        for (int j = 0; j < slopStdBlank + 1; j++) {
            System.out.print(" ");
        }
        for (int j = 0; j < bottleVertebraStdLong; j++) {
            if (j == 3 || j == 7) {
                System.out.print(" ");
            } else {
                System.out.print("⚫");
            }
        }
        System.out.println();
        // 小脚
        for (int j = 0; j < slopStdBlank + 2; j++) {
            System.out.print(" ");
        }
        for (int j = 0; j < bottleVertebraStdLong; j++) {
            if (j == 0 || j== 1 || j == 4 || j == 5 || j == 8) {
                System.out.print("⚫");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println();

    }

}
