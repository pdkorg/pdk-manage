package com.pdk.manage.service.coupon;

import com.pdk.manage.dao.coupon.CouponActivityTemplateBDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponActivityTemplate;
import com.pdk.manage.model.coupon.CouponActivityTemplateB;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11
 */
@Service
public class CouponActivityTemplateBService extends BaseService<CouponActivityTemplateB> {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityTemplateBService.class);

    @Override
    public String getModuleCode() {
        return IdGenerator.CP_MODULE_CODE;
    }

    public CouponActivityTemplateBDao getDao() {
        return (CouponActivityTemplateBDao) this.dao;
    }

    public List<CouponActivityTemplateB> selectTemplateByActCode(String actCode) {
        return getDao().selectTemplateByActCode(actCode);
    }

    public void updateCouponTemplate(List<CouponActivityTemplateB> templates, String templateId) throws BusinessException {
        List<CouponActivityTemplateB> insTemplate = new ArrayList<>();
        List<CouponActivityTemplateB> updTemplate = new ArrayList<>();
        List<CouponActivityTemplateB> delTemplate = new ArrayList<>();
        for (CouponActivityTemplateB template : templates) {
            template.setTemplateId(templateId);

            if (StringUtils.isEmpty(template.getId())) {
                template.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
                insTemplate.add(template);
            } else if (template.getDr() == DBConst.DR_DEL) {
                delTemplate.add(template);
            } else {
                template.setDr(DBConst.DR_NORMAL);
                updTemplate.add(template);
            }
        }

        if ( insTemplate.size() > 0 ) {
            save(insTemplate);
        }

        if ( updTemplate.size() > 0 ) {
            for (CouponActivityTemplateB template : updTemplate) {
                update(template);
            }
        }

        if ( delTemplate.size() > 0 ) {
            delete(delTemplate);
        }
    }

    public void deleteByTemplate(List<CouponActivityTemplate> templates) {
        List<String> actIds = new ArrayList<>();
        for ( CouponActivityTemplate template : templates ) {
            actIds.add(template.getId());
        }

        getDao().deleteCouponByTemplateId(actIds);
    }

    public List<CouponActivityTemplateB> selectListByTemplateId(String templateId) {
        return getDao().selectListByTemplateId(templateId);
    }
}

