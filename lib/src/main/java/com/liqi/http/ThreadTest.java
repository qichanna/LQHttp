package com.liqi.http;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liqi on 2018/4/15.
 */

public class ThreadTest {

    static class MyRunnable implements Runnable{
        public volatile boolean flag = true;

        @Override
        public void run() {
            while (flag && !Thread.interrupted()){
//            while (!Thread.interrupted()){
                try {
//            while (flag){
                    System.out.println("running " );
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

        }
    }

    public static void main(String args[]) throws InterruptedException{
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(1000);
        runnable.flag = false;
        thread.interrupt();
    }
}
