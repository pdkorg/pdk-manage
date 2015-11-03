package com.pdk.manage.model;

import java.util.Date;

/**
 * Created by hubo on 2015/8/14.
 */
public interface IBaseVO {
    String getId();
    void setId(String id);
    Date getTs();
    void setTs(Date ts);
    Short getDr();
    void setDr(Short dr);
}
