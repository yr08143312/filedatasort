package com.itheima;

import java.util.*;

public class RiseGroupMinquotaSort implements SortInterface {
    private Set<DataVo> resultSet = new TreeSet<>(new RiseGroupComparator());
    @Override
    public void sort(DataVo vo) {
        if(!resultSet.contains(vo)){
            resultSet.add(vo);
        }else{
            for(DataVo currentVo : resultSet){
                if(currentVo.equals(vo) && vo.getQuota() < currentVo.getQuota()){
                    currentVo.setId(vo.getId());
                    currentVo.setQuota(vo.getQuota());
                }
            }
        }
    }

    @Override
    public Collection<DataVo> getResult() {
        return resultSet;
    }

    class RiseGroupComparator implements Comparator<DataVo>{

        @Override
        public int compare(DataVo o1, DataVo o2) {
            return Integer.parseInt(o1.getGroupId()) - Integer.parseInt(o2.getGroupId());
        }
    }
}
