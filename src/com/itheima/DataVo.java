package com.itheima;

public class DataVo {

    private String id;
    private String groupId;
    private float quota;

    public DataVo() {
    }

    public DataVo(String id, String groupId, float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public float getQuota() {
        return quota;
    }

    public void setQuota(float quota) {
        this.quota = quota;
    }

    @Override
    public String toString() {
        return groupId + "，" + id + "，" + quota;
    } 

    //！！！！！讲解：为什么需要equals方法和hashCode方法，因为我们的数据最终需要排序，排序的话就需要放在容器中，而容器中要匹配某个元素必须要有这两个方法。如果没有这两个方法，就必须自己实现匹配。
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataVo dataVo = (DataVo) o;

        return groupId.equals(dataVo.groupId);
    }

    @Override
    public int hashCode() {
        return groupId.hashCode();
    }

}
