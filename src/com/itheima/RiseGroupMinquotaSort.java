package com.itheima;

import java.util.*;

public class RiseGroupMinquotaSort implements SortInterface,Comparator<DataVo> {
    private Set<DataVo> resultSet = new TreeSet<>(this);
    @Override
    public void sort(DataVo vo) {
        if(!resultSet.contains(vo)){
            resultSet.add(vo);//会自动排序
        }else{
            for(DataVo currentVo : resultSet){
                if(currentVo.equals(vo) && vo.getQuota() < currentVo.getQuota()){
                    currentVo.setId(vo.getId());
                    currentVo.setQuota(vo.getQuota());
                }
            }
        }
        //！！！！！讲解：有的同学会问，这也没排序啊。注意resultSet.add(vo)会自动排序，原理就是他会调用该类中的compare方法
    }

    @Override
    public Collection<DataVo> getResult() {
        return resultSet;
    }

    @Override
    public int compare(DataVo o1, DataVo o2) {
        return Integer.parseInt(o1.getGroupId()) - Integer.parseInt(o2.getGroupId());
    }
}
