package com.itheima;

import java.util.LinkedList;

public class SortResources {
    /**
     * 原始数据
     */
    private LinkedList<DataVo> originalList = new LinkedList();

    //(后面再来写)，剩余文件数，由于要在类的外面调用，所以这里是静态的，并且是原子类。
    public int restFileSize;

    private SortInterface sort;

    public SortResources(int fileSize,SortInterface sort){
        restFileSize = fileSize;
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
                //(此处的if写完这个方法再写)
                if(restFileSize == 0){
                    //System.out.println(Thread.currentThread().getName()+"消费线程离开");
                    return;
                }
                try {
                    wait();//！！！！！讲解：为什么要把wait包在while循环中，而不是if中。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DataVo vo = originalList.poll();
            //System.out.println(Thread.currentThread().getName()+"线程消费了--"+vo);
            //由子类实现排序。
            sort.sort(vo);
            notifyAll();
            //！！！！！讲解：但是你发现没有？while没有跳出的条件。
        }
    }


    public synchronized void fileSizeDecrement() {
        --restFileSize;
        //！！！！！讲解：这里为什么要notifyAll()??相当于生产线程通知消费线程结束了
        notifyAll();

    }
}
