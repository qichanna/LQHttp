package com.liqi.http;

/**
 * Created by liqi on 2018/4/15.
 */

public class VolatileTest {
    int m = 0;
    int n = 1;

    public void set(){
        m = 6;
        n = m;
    }

    public void print(){
        System.out.println("m:"+m+",n:"+n);
    }

    public static void main(String[] args) {
        while(true){
            final VolatileTest v = new VolatileTest();
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    v.set();
                }

            }).start();

            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    v.print();
                }

            }).start();
        }
    }
}
