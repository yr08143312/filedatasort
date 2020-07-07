package com.itheima;

import java.util.LinkedList;

public class SortResources {
    /**
     * 原始数据，我们可以把它看成一个流水线
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
        while(originalList.size() > 5){
            try {
                wait();//！！！！！讲解：大家都是干活的，凭什么我一直干，你一直休息啊，那我觉得不公平啊
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        originalList.push(vo);
        //System.out.println(Thread.currentThread().getName()+"线程生产了--"+vo);
        notifyAll();//！！！！！讲解：把大家叫醒，活儿来啦，别睡了。
    }

    public synchronized void consumeData() {
        //！！！！！讲解：while(true)，这里写成死循环是因为，消费线程不能提前终止，只要生产线程一直在生产，消费线程就不能结束。
        // ！！！！！！就像流水线一样，流水线上游的工人把零件放在流水线上，然后流水线都满了，放不下了，这个时候，你说你走了，那怎么办呢，上游的员工就一直等着你，他哪知道，你都辞职不干了。
        //所以啊，消费线程不能说走就走，需要站好最后一班岗
        //那何时终止呢？接下来再看
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
            notifyAll();//！！！！！讲解：我都在生产了，你们也别睡觉了。
            //！！！！！讲解：但是你发现没有？while没有跳出的条件。
        }
    }


    public synchronized void fileSizeDecrement() {
        --restFileSize;
        //！！！！！讲解：这里为什么要notifyAll()??相当于生产线程通知消费线程结束了
        //！！！！！讲解：produceData 和 fileSizeDecrement方法肯定是生产线程分开调用的，那么在这两个方法调用之间肯定存在一个状态：：就是所有的数据已经被生产线程生产完毕，但是生产线程没有将剩余文件数改成0.所以消费线程会进入wait方法等待。那么它将一直等待下去。解决的方法就是这个notifyAll。
        //！！！！！重点！！！！
        notifyAll();

    }
}
