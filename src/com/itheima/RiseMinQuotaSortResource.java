package com.itheima;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RiseMinQuotaSortResource extends SortResources {
    private List<DataVo> sortList = new ArrayList<DataVo>();

    public RiseMinQuotaSortResource(int fileSize) {
        super(fileSize);
    }

    @Override
    protected void sort(final DataVo vo) {
        //如果vo在list中存在的情况
        int index = sortList.indexOf(vo);
        DataVo currentVo = null;
        if(index > -1){
            if((currentVo = sortList.get(index)).getQuota() > vo.getQuota()){
                currentVo.setId(vo.getId());
                currentVo.setQuota(vo.getQuota());
            }
        }else{
            int voGroupId = Integer.parseInt(vo.getGroupId());
            //如果vo在list中不存在
            switch(sortList.size()){
                case 0:
                    sortList.add(vo);
                    break;
                case 1:
                    DataVo onlyVo = sortList.get(0);
                    if(Integer.parseInt(onlyVo.getGroupId()) < voGroupId){
                        sortList.add(vo);
                    }else{
                        sortList.add(0,vo);
                    }
                    break;
                default://集合中有2个以上的数据时
                    //讲解:此处需要讲解为何sortList.size() - 1
                    List<DataVo> tempList = new ArrayList<>();
                    tempList.addAll(sortList);
                    for(int i = 0 ; i < tempList.size() - 1;i ++){
                        int firstGroupId = Integer.parseInt(sortList.get(i).getGroupId());
                        int secondGroupId = Integer.parseInt(sortList.get(i+1).getGroupId());
                        if(voGroupId < firstGroupId){
                            sortList.add(i,vo);
                        }else if(firstGroupId < voGroupId && voGroupId < secondGroupId){
                            sortList.add(i+1,vo);
                        }else if((i+2) == sortList.size()){
                            sortList.add(vo);
                        }
                        //讲解：大于secondGroupId的情况下，当然是下一个循环再判断拉
                    }

            }

        }
    }

    @Override
    public Collection<DataVo> getSortResult() {
        return sortList;
    }
}
