package com.itheima;

import java.util.concurrent.CountDownLatch;

public class SortThread implements Runnable {
    private SortResources sortResources;
    private CountDownLatch countDownLatch;
    public SortThread(SortResources sortResources,CountDownLatch countDownLatch){
        this.sortResources = sortResources;
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        sortResources.consumeData();
        countDownLatch.countDown();
    }
}
