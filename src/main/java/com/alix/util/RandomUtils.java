package com.alix.util;

import java.util.Random;

/**
 * 随机数获取
 *
 * @author shadowyy
 * @version 2017/11/4 21:55
 */
public class RandomUtils {
    public static int getRandomNum(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            int randomNum = getRandomNum(20);
            System.out.println(randomNum);

            for (int i = 0; i < randomNum; i++) {
                System.out.println("random:" + getRandomNum(randomNum * 10));
            }

            Thread.sleep(30000);
        }
    }
}
