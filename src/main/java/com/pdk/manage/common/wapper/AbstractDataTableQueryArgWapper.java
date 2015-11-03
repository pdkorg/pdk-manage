package com.pdk.manage.common.wapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hubo on 2015/8/12.
 */
public abstract class AbstractDataTableQueryArgWapper implements Serializable{



    private int draw;
    private int start;
    private int length;
    private String searchText;
    private int orderIndex;
    private String orderType;

    public AbstractDataTableQueryArgWapper(int draw, int start, int length, String searchText, int orderIndex, String orderType) {
        this.draw = draw;
        this.start = start;
        this.length = length;
        this.searchText = searchText;
        this.orderIndex = orderIndex;
        this.orderType = orderType;
    }

    public AbstractDataTableQueryArgWapper() {
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String search) {
        this.searchText = search;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * 返回表格列对应的名称
     * <p>放入顺序根据界面显示顺序一致，第0列统一为"checked"</p>
     * @return 返回表格列对应的名称
     */
    public abstract List<String> getColNameList();

    public String getOrderStr() {
        return getColNameList().get(orderIndex) + " " + orderType;
    }


    public int getPageNum() {
        int pageNum;
        if(getStart() < getLength()) {
            pageNum = 1;
        }else {
            pageNum = getStart() % getLength() == 0 ? getStart() / getLength() + 1 : getStart() / getLength() + 2;
        }
        return pageNum;
    }

}
