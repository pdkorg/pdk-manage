package com.pdk.manage.model.app.response;

import java.io.Serializable;

/**
 * Created by chengxiang on 15/8/8.
 * Function：响应的公用字段
 */
public class BaseResponseModel implements Serializable {
    protected String retcode;
    protected String retdesc;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetdesc() {
        return retdesc;
    }

    public void setRetdesc(String retdesc) {
        this.retdesc = retdesc;
    }
}
