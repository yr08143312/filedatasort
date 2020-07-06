package com.itheima;

import java.util.LinkedList;

public class SortResources {
    protected LinkedList<DataVo> originalList = new LinkedList();
    //剩余文件数，由于要在类的外面调用，所以这里是静态的，并且是原子类。
    public int restFileCount;

    private SortInterface sort;

    public SortResources(int fileSize,SortInterface sort){
        restFileCount = fileSize;
        this.sort = sort;
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
                if(restFileCount == 0){
                    //System.out.println(Thread.currentThread().getName()+"消费线程离开");
                    return;
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DataVo vo = originalList.poll();
            //System.out.println(Thread.currentThread().getName()+"线程消费了--"+vo);
            //由子类实现排序。
            sort.sort(vo);
            notifyAll();
        }
    }


    public synchronized void fileSizeDecrement() {
        --restFileCount;
        //这里为什么要notifyAll()??相当于生产线程通知消费线程结束了
        notifyAll();

    }
}
