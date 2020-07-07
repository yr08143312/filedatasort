package com.itheima;

import java.util.Collection;

public interface SortInterface {

    void sort(DataVo vo);

    //！！！！！讲解为什么需要Collection？因为具体的排序方法需要具体的数据结构，
    Collection<DataVo> getResult();
}
