package com.itheima;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SortResources {
    protected LinkedList<DataVo> originalList = new LinkedList();
    //剩余文件数，由于要在类的外面调用，所以这里是静态的，并且是原子类。
    public static AtomicInteger restFileCount;

    public SortResources(int fileSize){
        restFileCount = new AtomicInteger(fileSize);
    }

    public synchronized void produceData(DataVo vo){
        while(originalList.size() > 3){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        originalList.push(vo);
        //System.out.println(Thread.currentThread().getName()+"线程生产了--"+vo);
        notifyAll();
    }

    public synchronized void consumeData() {
        while(true){
            while (originalList.size() == 0) {
                if(restFileCount.get() == 0){
                    //System.out.println(Thread.currentThread().getName()+"消费线程离开");
                    notifyAll();//离开之前唤醒其他线程
                    return;
                }
                try {
                    //System.out.println(Thread.currentThread().getName()+"---------"+originalList.size()+"  "+restFileCount.get() +"----"+getSortResult());
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DataVo vo = originalList.poll();
            //System.out.println(Thread.currentThread().getName()+"线程消费了--"+vo);
            //由子类实现排序。
            sort(vo);
            notifyAll();//结束一次循环唤醒其他线程
        }
    }

    protected abstract void sort(DataVo vo);

    public abstract Collection<DataVo> getSortResult();


}
