package com.itheima;

import java.util.concurrent.atomic.AtomicInteger;

public class DataVo {

    public DataVo() {
    }

    public DataVo(String id, String groupId, float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    private String id;
    private String groupId;
    private float quota;

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
