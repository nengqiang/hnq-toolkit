package com.hnq.toolkit.game;

import java.util.Random;

/**
 * 赛马游戏
 * 🏇🎮
 *
 * @author henengqiang
 * @date 2019/03/27
 */
public class RaceGame {

    private static String[] serial = new String[] {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    public static void main(String[] args) {
        int lastOneIndex = 3;
        int participantNums = 5;
        gameStart(participantNums);
        gameOn(lastOneIndex, participantNums);
        itAccelerate(lastOneIndex, participantNums);
        gameOver(lastOneIndex, participantNums);
    }

    private static void gameStart(int participantNums) {
        String prompt = "赛马游戏现在开始，";
        System.out.println(prompt);
        System.out.println("请下注：");
        for (int i = 0; i < participantNums; i++) {
            System.out.print((i + 1) + "🏁");
            for (int j = 0; j < prompt.length(); j++) {
                System.out.print("   ");
            }
            System.out.println("|🏇");
        }
    }

    private static void gameOn(int lastOneIndex, int participantNums) {
        String prompt = "赛马比赛现在开始:";
        System.out.println(prompt);
        Random r = new Random();
        // 路程 - 存放跑得最快的马的位移
        int maxBlankNums = 0;
        // 存放 路程 - 每匹马跑的位移
        int[] blankNums = new int[participantNums];
        for (int i = 0; i < participantNums; i++) {
            int blankNum = prompt.length() / 2 + r.nextInt(prompt.length() / 2 - 1);
            blankNums[i] = blankNum;
            if (maxBlankNums < blankNum) {
                maxBlankNums = blankNum;
            }
        }
        for (int i = 0; i < participantNums; i++) {
            System.out.print((i + 1) + "🏁");
            // 把指定马放在最后
            if (i == lastOneIndex - 1) {
                for (int j = 0; j < maxBlankNums + 1; j++) {
                    System.out.print("   ");
                }
            } else {
                for (int j = 0; j < blankNums[i]; j++) {
                    System.out.print("   ");
                }
            }
            System.out.println("🏇");
        }
//        System.out.println(serial[lastOneIndex - 1] + "号很落后。充钱吗？");
        System.out.println(serial[lastOneIndex - 1] + "号很落后。需要打开外挂吗？");
    }

    private static void itAccelerate(int theLastOneIndex, int participantNums) {
        String prompt = "赛马比赛激烈进行:";
        System.out.println(prompt);
        Random r = new Random();
        // 路程 - 存放跑得最快的马的位移
        int maxBlankNums = 0;
        // 存放 路程 - 每匹马跑的位移
        int[] blankNums = new int[participantNums];
        for (int i = 0; i < participantNums; i++) {
            int blankNum = prompt.length() / 2 - 1 + r.nextInt(prompt.length() / 2 - 1);
            blankNums[i] = blankNum;
            if (maxBlankNums < blankNum) {
                maxBlankNums = blankNum;
            }
        }
        for (int i = 0; i < participantNums; i++) {
            System.out.print((i + 1) + "🏁");
            // 把指定马加速到终点
            if (i == theLastOneIndex - 1) {
                System.out.println("🏇🌬🌬🌬");
                continue;
            } else {
                for (int j = 0; j < blankNums[i]; j++) {
                    System.out.print("   ");
                }
            }
            System.out.println("🏇");
        }
        System.out.println(serial[theLastOneIndex - 1] + "号开始加速了！");
    }

    private static void gameOver(int theLastOneIndex, int participantNums) {
        String prompt = "赛马比赛接近尾声:";
        System.out.println(prompt);
        Random r = new Random();
        // 路程 - 存放跑得最快的马的位移
        int maxBlankNums = 0;
        // 存放 路程 - 每匹马跑的位移
        int[] blankNums = new int[participantNums];
        for (int i = 0; i < participantNums; i++) {
            int blankNum = r.nextInt(prompt.length() / 2) + 1;
            blankNums[i] = blankNum;
            if (maxBlankNums < blankNum) {
                maxBlankNums = blankNum;
            }
        }
        for (int i = 0; i < participantNums; i++) {
            System.out.print((i + 1) + "🏁");
            // 指定马dead
            if (i == theLastOneIndex - 1) {
                System.out.println("☠️");
                continue;
            } else {
                for (int j = 0; j < blankNums[i]; j++) {
                    System.out.print("   ");
                }
            }
            System.out.println("🏇");
        }
        System.out.println("因非法外挂，你下注的马儿挂了！");
    }

}
