package com.itheima;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class SortResources {
    protected LinkedList<DataVo> originalList = new LinkedList();
    //剩余文件数
    public static AtomicInteger restFileCount;

    public synchronized void produceData(DataVo vo){
        originalList.push(vo);
        notifyAll();
    }

    public synchronized void consumeData(){
        while (originalList.size() == 0 && restFileCount.get() > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (originalList.size() > 0) {
            DataVo vo = originalList.poll();
            //由子类实现排序。
            sort(vo);
        }
        notifyAll();
    }

    protected abstract void sort(DataVo vo);

    public abstract Collection<DataVo> getSortResult();

    public synchronized boolean isEmpty(){
        return originalList.size() == 0 && restFileCount.get() == 0;
    }



}
