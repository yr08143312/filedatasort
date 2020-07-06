package com.itheima;

public class SortThread implements Runnable {
    private SortResources sortResources;
    public SortThread(SortResources sortResources){
        this.sortResources = sortResources;
    }
    @Override
    public void run() {
        sortResources.consumeData();
        //System.out.println("消费线程---"+Thread.currentThread().getName()+"结束");
    }
}
