package com.pdk.manage.action.report;

import com.pdk.manage.common.wapper.report.ReportOrderDataTableQueryArgWapper;
import com.pdk.manage.dto.report.ReportOrderJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.order.YwyMnyInfoModel;
import com.pdk.manage.service.report.ReportOrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuhaiming on 2015/10/27
 */
@Controller
@RequestMapping(value = "/report")
public class ReportOrderAction {
    private static final Logger log = LoggerFactory.getLogger(ReportOrderAction.class);

    @Autowired
    private ReportOrderService reportOrderService;

    @RequestMapping("/order")
    public String index() {
        return "report/report_order";
    }

    @RequestMapping("/order/table_data")
    public @ResponseBody Map<String, Object> list( ReportOrderDataTableQueryArgWapper arg ) {
        Map<String, Object> result = new HashMap<>();
        List<ReportOrderJson> data = reportOrderService.getOrderInfo(arg.getFromDate(), arg.getToDate());

        int index = 1;
        for ( ReportOrderJson json : data ) {
            json.setIndex(index);
            index++;
        }

        result.put("recordsTotal", data.size());
        result.put("recordsFiltered", data.size());
        result.put("data", data);

        return result;
    }

    @RequestMapping("/ywymny")
    public String ywymny() {
        return "report/report_ywy";
    }

    @RequestMapping(value = "/ywymny/table_data")
    @ResponseBody
    public Map<String,Object> ywyMnyTableData(String ywyId,String queryDate){

        Map<String, Object> result = new HashMap<>();
        List<YwyMnyInfoModel> resultList = new ArrayList<>();

        result.put("recordsTotal", resultList.size());
        result.put("recordsFiltered", resultList.size());
        result.put("data", resultList);


        if(StringUtils.isEmpty(queryDate)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            queryDate =format.format(new Date());
        }

        List<YwyMnyInfoModel> list = null;

        try {
            list = reportOrderService.getYwyMnyDatas(ywyId,queryDate);
        } catch (BusinessException e) {
            log.error(e.getMessage(),e);
            return result;
        }

        if(list==null||list.size()==0){
            return result;
        }

        BigDecimal countMny = new BigDecimal(0.00);
        BigDecimal tipCountMny = BigDecimal.ZERO;
        String tempName = "";

        for (YwyMnyInfoModel temp:list){
            if(!(tempName.equals(temp.getYwyName()))&& !(tempName.equals(""))){
                YwyMnyInfoModel countModel = new YwyMnyInfoModel("",countMny,tempName+"总金额：",tipCountMny);
                resultList.add(countModel);
                countMny = new BigDecimal(0.00);
                tipCountMny = BigDecimal.ZERO;
            }
            resultList.add(temp);
            tempName = temp.getYwyName();
            countMny = countMny.add(temp.getMny());
            tipCountMny = tipCountMny.add(temp.getTipMny());
        }
        //最后一个循环没有添加汇总值
        YwyMnyInfoModel countModel = new YwyMnyInfoModel("",countMny,tempName+"总金额：",BigDecimal.ZERO);
        resultList.add(countModel);

        result.put("recordsTotal", resultList.size());
        result.put("recordsFiltered", resultList.size());
        result.put("data", resultList);

        return result;
    }
}
