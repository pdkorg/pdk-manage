package com.pdk.manage.service.coupon;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.coupon.CouponActivityDao;
import com.pdk.manage.dto.coupon.CouponJson;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.model.coupon.*;
import com.pdk.manage.model.sm.User;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.service.coupon.rule.factory.ActRuleFactory;
import com.pdk.manage.service.coupon.runner.CouponSender;
import com.pdk.manage.service.order.OrderService;
import com.pdk.manage.service.sm.SmUserService;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by liuhaiming on 2015/9/11
 */
@Service
public class CouponActivityService extends BaseService<CouponActivity> {
    private static final Logger log = LoggerFactory.getLogger(CouponActivityService.class);

    private static final int THREAD_SIZE = 8;

    private CouponActivityDao getDao() {
        return (CouponActivityDao) this.dao;
    }

    @Autowired
    private CouponActivityTemplateBService couponActivityTemplateBService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUserSendedService couponUserSendedService;

    @Autowired
    private CouponActivityRuleService couponActivityRuleService;

    @Autowired
    private CouponUserRelationService couponUserRelationService;

    @Autowired
    private ActRuleFactory ruleFactory;

    @Override
    public String getModuleCode() {
        return IdGenerator.CP_MODULE_CODE;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAll(CouponActivity activity) throws BusinessException {
        activity.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
        save(activity);

        List<Coupon> coupons = new ArrayList<>();
        IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON);
        for (Coupon coupon : activity.getCoupons()) {
            coupon.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
            coupon.setActivityId(activity.getId());
            coupons.add(coupon);
        }
        couponService.save(coupons);

        List<CouponActivityRule> couponActivityRules = new ArrayList<>();

        if (activity.getRules() != null && activity.getRules().size() > 0) {
            for (CouponActivityRule rule : activity.getRules()) {
                rule.setActivityId(activity.getId());
                couponActivityRules.add(rule);
            }
            couponActivityRuleService.save(couponActivityRules);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAll(CouponActivity activity) throws BusinessException {
        update(activity);

        couponService.updateCoupons(activity.getCoupons(), activity.getId());

        couponActivityRuleService.updateCouponActivityRules(activity.getRules(), activity.getId());
    }

    public void autoSendCoupon4Activity() throws BusinessException, InterruptedException {
        Date currDate = Calendar.getInstance().getTime();
        List<CouponActivity> activitys = getDao().findAutoSendActivity(currDate);
        for (CouponActivity activity : activitys) {
            sendCoupon(activity.getId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteByBill(List<CouponActivity> activitys) throws BusinessException {
        delete(activitys);

        couponService.deleteByActivity(activitys);

        couponActivityRuleService.deleteByActivity(activitys);
    }

    public PageInfo<CouponActivity> selectLikePageEnable(String searchText, int pageNum, int pageSize, String orderBy) {
            PageHelper.startPage(pageNum, pageSize, orderBy);
        PageInfo<CouponActivity> pageInfo = null;
        if (StringUtils.isEmpty(searchText)) {
            pageInfo = new PageInfo<>(getDao().selectEnable());
        } else {
            pageInfo = new PageInfo<>(getDao().selectLikeEnable(searchText));
        }

            return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean sendCoupon(String id) throws BusinessException, InterruptedException {
        boolean rst = true;
        CouponActivity couponActivity = this.get(id);
        List<CouponActivityRule> rules = couponActivityRuleService.getByActivityId(id);

        log.info("==================== Begin 1、开始派送优惠券 =========================");
        log.info("==================== 2、优惠券派送中 =========================");

        log.info("==================== 2.1、开始过滤用户 =========================");
        List<CouponUserSended> users  = getFilteredUser(rules, null);
        if ( users == null || users.size() == 0 ) {
            return false;
        }

        log.info("==================== 2.2、开始整理优惠券包 =========================");
        List<Coupon> coupons = couponService.getByActivityId(id);

        log.info("==================== 2.3、开始派送 =========================");
        rst = createSendCoupon(coupons, users, id);

        log.info("==================== 3、派送完成，将派送单设置为已派送 =========================");
        couponActivity.setSendUserCnt(users.size());
        BigDecimal mny = BigDecimal.ZERO;
        for ( Coupon coupon : coupons ) {
            mny = mny.add(coupon.getSendMny());
        }

        mny = mny.multiply(new BigDecimal(users.size()));
        couponActivity.setSendTotalMny(mny);
        couponActivity.setStatus(DBConst.COUPON_ACTIVITY_STATUS_SENDED);
        this.update(couponActivity);
        log.info("==================== End   1、结束派送优惠券 =========================");

        return rst;
    }

    public int getSendUserCount(List<CouponActivityRule> rules) throws BusinessException {
        CouponActivityRuleModel model = transRule2Condition(rules);
        return couponUserSendedService.getSendUserCount(model.getSqlWhere(), model.getSqlHaving(), model.isLinkOrder());
    }

    public List<CouponUserSended> getFilteredUser(List<CouponActivityRule> rules, String searchText) throws BusinessException {
        CouponActivityRuleModel model = transRule2Condition(rules);
        List<CouponUserSended> users = couponUserSendedService.getUserListForCoupon(model, searchText);
        return users;
    }

    private boolean createSendCoupon(List<Coupon> couponLst, List<CouponUserSended> userList, String activityId) throws BusinessException, InterruptedException {
        System.out.println("============优惠券发送时间计算开始================================");
        long t1 = Calendar.getInstance().getTimeInMillis();
        List<CouponUserSended> sendLst = new ArrayList<>();
        List<CouponUserRelation> couponList = new ArrayList<>();
        for ( CouponUserSended user : userList ) {
            couponList.addAll(createSendCouponByUser(user, couponLst));
            sendLst.add(createSnapshot(user, activityId));
        }

        boolean retFlg = threadSendCoupon(couponList, sendLst);
        System.out.println("============优惠券发送时间计算结束================================");
        long t2 = Calendar.getInstance().getTimeInMillis();
        System.out.println("============优惠券发送耗时：" + (t2 - t1) / 1000 + "================================");

        return retFlg;
    }



    private boolean threadSendCoupon(List<CouponUserRelation> couponList, List<CouponUserSended> sendLst) throws BusinessException, InterruptedException {
        boolean retFlg = true;

        int threadSize = THREAD_SIZE;
        if ( couponList.size() <= threadSize ) {
            threadSize = 1;
        }

        ExecutorService service = Executors.newFixedThreadPool(threadSize);
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);

        List< List<CouponUserRelation> > threadSendCouponLst = new ArrayList<>();
        List< List<CouponUserSended> > threadSendSnapLst = new ArrayList<>();
        for ( int tCnt = 0; tCnt < threadSize; tCnt++ ) {
            threadSendCouponLst.add(new ArrayList<CouponUserRelation>());
            threadSendSnapLst.add(new ArrayList<CouponUserSended>());
        }

        int index = 0;
        for ( CouponUserRelation couponUserRelation : couponList ) {
            threadSendCouponLst.get(index).add(couponUserRelation);
            index++;
            if ( index >= threadSize ) {
                index = 0;
            }
        }

        index = 0;
        for ( CouponUserSended sendSnap : sendLst ) {
            threadSendSnapLst.get(index).add(sendSnap);
            index++;
            if ( index >= threadSize ) {
                index = 0;
            }
        }

        List<Future<Boolean>> tasks = new ArrayList<>();

        for ( int tCnt = 0; tCnt < threadSize; tCnt++ ) {
            tasks.add(service.submit(new CouponSender(tCnt
                    , couponUserSendedService
                    , couponUserRelationService
                    , threadSendCouponLst.get(tCnt)
                    , threadSendSnapLst.get(tCnt)
                    , countDownLatch)));
        }

        countDownLatch.await();
        try {
            for (Future<Boolean> task : tasks) {
                if(!task.get()) {
                    String templateId = couponList.get(0).getTemplateId();
                    rollBackSendCoupon(templateId);
                    retFlg = false;
                    break;
                }
            }
        } catch (ExecutionException e) {
            log.error("多线程发送优惠券异常!", e);
            retFlg = false;
        }
        log.info("=================总优惠券发送量：" + couponList.size() + "===============================");
        service.shutdown();

        return retFlg;
    }

    private void rollBackSendCoupon(String templateId) {
        couponUserRelationService.deleteUserCouponByTemplateId(templateId);
    }

    private CouponUserSended createSnapshot(CouponUserSended user, String activityId) {
        CouponUserSended sended;
        sended = new CouponUserSended();
        sended.setUserId(user.getUserId());
        sended.setSourceId(user.getSourceId());
        sended.setActivityId(activityId);
        sended.setName(user.getName());
        sended.setRealName(user.getRealName());
        sended.setTimes(user.getTimes());
        sended.setPayMny(user.getPayMny());
        sended.setRegisterDate(user.getRegisterDate());

        return sended;
    }

    public CouponActivityRuleModel transRule2Condition(List<CouponActivityRule> rules) {
        CouponActivityRuleModel model = new CouponActivityRuleModel();
        if ( rules == null ) {
            return model;
        }

        Map<Short, List<CouponActivityRule>> ruleMap = new HashMap<>();
        for (CouponActivityRule rule : rules) {
            if ( !ruleMap.containsKey( rule.getRuleType() ) ) {
                ruleMap.put( rule.getRuleType(), new ArrayList<CouponActivityRule>() );
            }

            ruleMap.get(rule.getRuleType()).add(rule);
        }

        for ( Short ruleType : ruleMap.keySet() ) {
            fillSqlCondition(ruleMap.get(ruleType), model);
        }

        return model;
    }

    private void fillSqlCondition(List<CouponActivityRule> rules, CouponActivityRuleModel model) {
        StringBuffer sql = new StringBuffer();
        String fstConn = "";
        boolean isWhere = true;
        for (CouponActivityRule rule : rules) {
            if ( StringUtils.isEmpty(rule.getChkVal()) ) {
                continue;
            }

            String optType = getOptByType(rule.getOptType());
            String connType = getConnByType(rule.getConnectType());
            String field = "";
            boolean isString = false;
            if (StringUtils.isEmpty(fstConn)) {
                fstConn = connType;
                connType = "";
            }

            if (rule.getRuleType() ==  DBConst.RULE_TYPE_TIMES ) {
                field = " count(a.user_id) ";
                isWhere = false;
                model.setIsLinkOrder(true);
            } else if (rule.getRuleType() ==  DBConst.RULE_TYPE_MNY ) {
                field = " sum(a.mny) ";
                isWhere = false;
                model.setIsLinkOrder(true);
            } else if (rule.getRuleType() ==  DBConst.RULE_TYPE_REGISTER_TIME ) {
                isString = true;
                field = " u.register_time ";
            } else if (rule.getRuleType() ==  DBConst.RULE_TYPE_ORDER_BEGIN_DATE ) {
                isString = true;
                field = " a.start_time ";
                model.setIsLinkOrder(true);
            } else if (rule.getRuleType() ==  DBConst.RULE_TYPE_FLOW_TYPE ) {
                isString = true;
                field = " a.flow_type_id ";
                model.setIsLinkOrder(true);
            } else if (rule.getRuleType() == DBConst.RULE_TYPE_REF_USER) {
                isString = true;
                field = " u.id ";
            }

            if (isString) {
                sql.append(connType).append(field).append(optType).append("'").append(rule.getChkVal()).append("'");
            } else {
                sql.append(connType).append(field).append(optType).append(rule.getChkVal());
            }
        }

        String cond = StringUtils.isEmpty(sql.toString()) ? "" : fstConn + " ( " + sql.toString() + " ) ";
        if ( isWhere ) {
            model.setSqlWhere( (model.getSqlWhere() == null ? "" : model.getSqlWhere()) + cond );
        } else {
            model.setSqlHaving( (model.getSqlHaving() == null ? "" : model.getSqlHaving()) + cond );
        }
    }

    private String getOptByType(Short optType) {
        String type = "";
        if ( optType == DBConst.RULE_OPT_TYPE_GREATER_THEN ) {
            type = " > ";
        } else if ( optType == DBConst.RULE_OPT_TYPE_GREATER_THEN_EQUAL ) {
            type = " >= ";
        } else if ( optType == DBConst.RULE_OPT_TYPE_EQUAL_TO ) {
            type = " = ";
        } else if ( optType == DBConst.RULE_OPT_TYPE_LESS_THEN ) {
            type = " < ";
        } else if ( optType == DBConst.RULE_OPT_TYPE_LESS_THEN_EQUAL ) {
            type = " <= ";
        }

        return type;
    }

    private String getConnByType(Short connectType) {
        String type = "";
        if ( connectType == DBConst.RULE_CONN_TYPE_AND ) {
            type = " and ";
        } else if ( connectType == DBConst.RULE_CONN_TYPE_OR ) {
            type = " or ";
        }

        return type;
    }

    public CouponJson sendCouponByRule(String actCode, User user) throws BusinessException {
        List<CouponJson> couponList = sendCouponsByRule(actCode, user);

        return couponList == null || couponList.size() == 0 ? null : couponList.get(0);
    }

    private List<CouponJson> getCouponJson(List<CouponUserRelation> couponLst) {
        if ( couponLst == null ) {
            return null;
        }

        List<CouponJson> json = new ArrayList<>();

        int index = 1;
        for (CouponUserRelation relation : couponLst) {
            json.add( new CouponJson(index, relation) );
            index++;
        }

        return json;
    }

    public List<CouponJson> sendCouponsByRule(String actCode, User user) throws BusinessException {
        List<Coupon> couponLst =  findCouponByTemplate(actCode);
        if ( couponLst == null|| couponLst.size() == 0 ) {
            return null;
        }

        couponLst = ruleFactory.getRule(actCode).filterCoupons(couponLst, user);
        if ( couponLst == null || couponLst.size() == 0 ) {
            return null;
        }

        List<CouponUserRelation> relations = sendCouponByActivity(user, couponLst);

        List<String> ids = new ArrayList<>();
        for (CouponUserRelation relation : relations) {
            ids.add(relation.getId());
        }

        return getCouponJson( couponUserRelationService.selectByIds(ids));
    }

    public List<Coupon> findCouponByTemplate(String actCode) throws BusinessException {
        log.info("==================== 查询模板:" + actCode + " =========================");
        List<CouponActivityTemplateB> templates = couponActivityTemplateBService.selectTemplateByActCode(actCode);
        log.info("==================== 模板数量:" + templates.size() + " =========================");

        // 未查到可用模板认为无此活动
        if ( templates == null || templates.size() == 0 ) {
            return null;
        }

        log.info("==================== 根据模板生成待发优惠券实体 =========================");
        List<Coupon> couponLst = createCouponByTemplate(templates);

        return couponLst;
    }

    public List<CouponUserRelation> sendCouponByActivity(User user, List<Coupon> couponLst) throws BusinessException {
        log.info("==================== 根据优惠券 =========================");
        List<CouponUserRelation> couponUserRelations = createSendCouponByUser(user, couponLst);
        couponUserRelationService.save(couponUserRelations);

        return couponUserRelations;
    }

    private List<Coupon> createCouponByTemplate(List<CouponActivityTemplateB> templates) throws BusinessException {
        List<Coupon> coupons = new ArrayList<>();
        for ( CouponActivityTemplateB template : templates ) {
            coupons.add( createCoupon(template) );
        }

        return coupons;
    }

    private Coupon createCoupon(CouponActivityTemplateB template) {
        fillTemplate(template);
        
        Coupon coupon = new Coupon();
        coupon.setCode(IdGenerator.generateCode(IdGenerator.CODE_PER_MARK_COUPON));
        coupon.setName(template.getName());
        coupon.setFlowTypeId(template.getFlowTypeId());
        coupon.setSendMny(template.getSendMny());
        coupon.setMinPayMny(template.getMinPayMny());
        coupon.setActBeginDate(template.getBeginDate());
        coupon.setActEndDate(template.getEndDate());
        coupon.setTemplateId(template.getTemplateId());
        coupon.setId(template.getId());

        return coupon;
    }

    /**
     * 对优惠券模板进行填充（优先进行延迟生效方式，如果信息不全则进行日期匹配模式）
     * @param template
     */
    private void fillTemplate(CouponActivityTemplateB template) {
        if ( template.getDelayDays() != null || template.getActDays() != null ) {
            Calendar currCal = Calendar.getInstance();
            currCal.add(Calendar.DAY_OF_MONTH, template.getDelayDays() == null ? 0 : template.getDelayDays());
            template.setBeginDate(currCal.getTime());

            currCal.add(Calendar.DAY_OF_MONTH, (template.getActDays() == null || template.getActDays() <= 0 ) ? 0 : template.getActDays() - 1);
            template.setEndDate(currCal.getTime());
        }
    }


    private List<CouponUserRelation> createSendCouponByUser(User user, List<Coupon> couponLst) {
        List<CouponUserRelation> relations = new ArrayList<>();
        for ( Coupon coupon : couponLst ) {
            relations.add( createSendCouponByUser(user, coupon) );
        }

        return relations;
    }

    private List<CouponUserRelation> createSendCouponByUser(CouponUserSended user, List<Coupon> couponLst) {
        List<CouponUserRelation> relations = new ArrayList<>();
        CouponUserRelation relation;
        for ( Coupon coupon : couponLst ) {
            relation = new CouponUserRelation();
            relation.setUserId(user.getUserId());
            relation.setSourceId(user.getSourceId());
            relation.setStatus(DBConst.STATUS_UNUSE);

            relation.setCouponId(coupon.getId());
            relation.setCode(coupon.getCode());
            relation.setName(coupon.getName());
            relation.setFlowTypeId(coupon.getFlowTypeId());
            relation.setSendMny(coupon.getSendMny());
            relation.setMinPayMny(coupon.getMinPayMny());
            relation.setBeginDate(coupon.getActBeginDate());
            relation.setEndDate(coupon.getActEndDate());

            relation.setTemplateId( coupon.getActivityId() );

            relations.add(relation);
        }

        return relations;
    }

    private CouponUserRelation createSendCouponByUser(User user, Coupon coupon) {
        CouponUserRelation relation = new CouponUserRelation();
        relation.setUserId(user.getId());
        relation.setSourceId(user.getSourceId());
        relation.setCouponId(coupon.getId());
        relation.setTemplateId(coupon.getTemplateId());
        relation.setStatus(DBConst.STATUS_UNUSE);

        relation.setCode(coupon.getCode());
        relation.setName(coupon.getName());
        relation.setFlowTypeId(coupon.getFlowTypeId());
        relation.setSendMny(coupon.getSendMny());
        relation.setMinPayMny(coupon.getMinPayMny());
        relation.setBeginDate(coupon.getActBeginDate());
        relation.setEndDate(coupon.getActEndDate());

        return relation;
    }
}

