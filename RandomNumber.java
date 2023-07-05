package com.example.freshly;

import java.util.Random;

public class RandomNumber {
    private static Random random = new Random();
    public static int rand ;
    public static int getRand(){
        return rand = random.nextInt(13)+100;
    }
    public static int getRand2(){
        return rand=100000 + random.nextInt(999999-100000+1);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println(getRand2());
        }
    }
}
