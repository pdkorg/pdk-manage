package com.pdk.manage.model.common;

import java.util.List;

/**
 * Created by liuhaiming on 2015/8/23.
 */
public class Reference {
    private String referencedId;

    /** 批量校验的时候使用该参数，判定时优先校验批量处理 */
    private List<String> referencedIds;

    private String referenceKey;
    private String referenceTableKey;
//    private String referencedKey;
//    private String referencedTableKey;

    public String getReferencedId() {
        return referencedId;
    }

    public void setReferencedId(String referencedId) {
        this.referencedId = referencedId;
    }

    public List<String> getReferencedIds() {
        return referencedIds;
    }

    public void setReferencedIds(List<String> referencedIds) {
        this.referencedIds = referencedIds;
    }

    public String getReferenceKey() {
        return referenceKey;
    }

    public void setReferenceKey(String referenceKey) {
        this.referenceKey = referenceKey;
    }

    public String getReferenceTableKey() {
        return referenceTableKey;
    }

    public void setReferenceTableKey(String referenceTableKey) {
        this.referenceTableKey = referenceTableKey;
    }

//    public String getReferencedKey() {
//        return referencedKey;
//    }
//
//    public void setReferencedKey(String referencedKey) {
//        this.referencedKey = referencedKey;
//    }
//
//    public String getReferencedTableKey() {
//        return referencedTableKey;
//    }
//
//    public void setReferencedTableKey(String referencedTableKey) {
//        this.referencedTableKey = referencedTableKey;
//    }
}
