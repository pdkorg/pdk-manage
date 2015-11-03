package com.pdk.manage.service.coupon;

import com.pdk.manage.dao.coupon.CouponActivityTemplateDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.CouponActivityTemplate;
import com.pdk.manage.model.coupon.CouponActivityTemplateB;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaiming on 2015/9/11
 */
@Service
public class CouponActivityTemplateService extends BaseService<CouponActivityTemplate> {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityTemplateService.class);

    @Override
    public String getModuleCode() {
        return IdGenerator.CP_MODULE_CODE;
    }

    private CouponActivityTemplateDao getDao() {
        return (CouponActivityTemplateDao) this.dao;
    }

    @Autowired
    private CouponActivityTemplateBService couponActivityTemplateBService;

    @Transactional(rollbackFor = Exception.class)
    public void saveAll(CouponActivityTemplate couponActivityTemplate) throws BusinessException {
//        couponActivityTemplate.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
        save(couponActivityTemplate);

        List<CouponActivityTemplateB> templateBs = new ArrayList<>();
        IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON);
        for (CouponActivityTemplateB templateB : couponActivityTemplate.getBodys()) {
            templateB.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
            templateB.setTemplateId(couponActivityTemplate.getId());
            templateBs.add(templateB);
        }
        couponActivityTemplateBService.save(templateBs);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAll(CouponActivityTemplate couponActivityTemplate) throws BusinessException {
        update(couponActivityTemplate);

        couponActivityTemplateBService.updateCouponTemplate(couponActivityTemplate.getBodys(), couponActivityTemplate.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByBill(List<CouponActivityTemplate> templates) throws BusinessException {
        delete(templates);
        couponActivityTemplateBService.deleteByTemplate(templates);
    }

}

