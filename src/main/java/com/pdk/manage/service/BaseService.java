package com.pdk.manage.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pdk.manage.dao.common.BaseDao;
import com.pdk.manage.exception.BusinessException;
import com.pdk.manage.exception.MessageBusinessException;
import com.pdk.manage.model.IBaseVO;
import com.pdk.manage.util.DBConst;
import com.pdk.manage.util.IdGenerator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hubo on 15/8/12
 */
public class BaseService<T extends IBaseVO> {

    @Autowired
    protected BaseDao<T> dao;

    @Transactional(rollbackFor = Exception.class)
    public int save(T entity) throws BusinessException{
        int result;
        try {
            entity.setId(IdGenerator.generateId(getModuleCode()));
            entity.setDr(DBConst.DR_NORMAL);
            setNewTs(entity);
            result = dao.insert(entity);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
        return result;
    }

    public Date createTs() {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date ts;

        try {
            ts = format.parse(format.format(new Date()));
        } catch (ParseException e) {
            ts = new Date();
        }

        return ts;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<T> arr) throws BusinessException {

        Date ts = createTs();

        for (T t : arr) {
            t.setId(IdGenerator.generateId(getModuleCode()));
            t.setDr(DBConst.DR_NORMAL);
            t.setTs(ts);
        }

        int result;

        try {
            result = dao.insertList(arr);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @SuppressWarnings("unchecked")
    public int delete(T entity) throws BusinessException{

        int result;

        T newEntity;

        try {

            validateTs(entity);

            newEntity = (T)entity.getClass().newInstance();
            newEntity.setId(entity.getId());
            newEntity.setDr(DBConst.DR_DEL);
            setNewTs(newEntity);

            result = dao.updateByPrimaryKeySelective(newEntity);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public int delete(List<T> entitys) throws BusinessException{
        int result = 0;
        result = dao.batchDelete(entitys);
        return result;
    }

    /**
     *
     * @param ids 主键集合
     * @param cls 主要为了跟上面的方法区分开来
     * @return 更新个数
     * @throws BusinessException
     */
    @Transactional(rollbackFor = Exception.class)
    public int delete(List<String> ids, Class<T> cls) throws BusinessException{
        int result;
        
        List<T> list = new ArrayList<>();
        try {
            for (String id : ids) {
                T newEntity = cls.newInstance();
                newEntity.setId(id);
                list.add(newEntity);
            }
            result = delete(list);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(T entity) throws BusinessException{

        int result;

        try {
            validateTs(entity);
            setNewTs(entity);
            result = dao.updateByPrimaryKey(entity);
        } catch (MessageBusinessException e) {
            throw e;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
        return result;
    }

    private void validateTs(T entity) throws MessageBusinessException{

        if(entity == null || entity.getTs() == null) {
            return;
        }

        T origEntity = dao.selectByPrimaryKey(entity.getId());

        if(entity.getTs().compareTo(origEntity.getTs()) != 0) {
            throw new MessageBusinessException("当前数据已被他人修改，请刷新！");
        }

    }

    protected void setNewTs(T entity) throws BusinessException {
        //由于mysql不支持毫秒级,所以设置TS到秒
        entity.setTs(createTs());
    }

    public T get(String id) {
        return dao.selectByPrimaryKey(id);
    }

    /**
     * 单表分页查询
     *
     * @param pageNum 页数
     * @param pageSize 每页个数
     * @return 结果集
     */
    public PageInfo<T> selectPage(int pageNum, int pageSize, String orderBy) throws Exception{

        PageHelper.startPage(pageNum, pageSize, orderBy);

        T t = getGenericType().newInstance();

        t.setDr(DBConst.DR_NORMAL);

        return new PageInfo<>(dao.select(t));
    }


    public PageInfo<T> selectLikePage(String searchText, int pageNum, int pageSize, String orderBy) throws Exception {

        if(StringUtils.isNotEmpty(StringUtils.trim(searchText))) {

            PageHelper.startPage(pageNum, pageSize, orderBy);

            return new PageInfo<>(dao.selectLike(searchText));

        }else {
            return selectPage(pageNum, pageSize, orderBy);
        }

    }

    /**
     * 单表分页查询
     *
     * @param pageNum 页数
     * @param pageSize 每页个数
     * @return 结果集
     */
    public PageInfo<T> selectPage(int pageNum, int pageSize, String orderBy, T t) throws Exception{

        PageHelper.startPage(pageNum, pageSize, orderBy);

        return new PageInfo<>(dao.select(t));
    }

    public List<T> selectAll() throws Exception{
        T t = getGenericType().newInstance();

        t.setDr(DBConst.DR_NORMAL);

       return dao.select(t);
    }

    public List<T> get(List<String> ids) {
        return dao.selectByIdList(ids);
    }

    /**
     * 获取泛型类型
     * @return 泛型类型
     */
    private Class<T> getGenericType() throws BusinessException{

        /*
         * 通过dao找到泛型类的类型
         */
        Class<T> cls = getGenericType(dao.getClass());

        if(cls == null) {
            throw new BusinessException("无法获取Mapper<T>泛型类型:" + dao.getClass().getName());
        }

        return cls;

    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericType(Class cls) {
        Type[] types = cls.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                return (Class<T>) t.getActualTypeArguments()[0];
            } else if(type instanceof Class) {
                return getGenericType((Class<T>)type);
            }
        }
        return null;
    }


    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    @SuppressWarnings("unchecked")
    public int updateStatus(T entity, short status) throws BusinessException{

        int result;

        T newEntity;

        try {
            validateTs(entity);
            newEntity = (T)entity.getClass().newInstance();
            newEntity.setId(entity.getId());
            PropertyUtils.setSimpleProperty(newEntity, DBConst.STATUS, status);
            setNewTs(newEntity);
            result = dao.updateByPrimaryKeySelective(newEntity);

        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }

        return result;
    }

    public int enable(T entity) throws BusinessException{
        return updateStatus(entity, DBConst.STATUS_ENABLE);
    }

    public int enable(List<T> entitys) throws BusinessException{
        int result = 0;
        for (T entity : entitys) {
            result += enable(entity);
        }
        return result;
    }

    public int disable(T entity) throws BusinessException{
        return updateStatus(entity, DBConst.STATUS_DISABLE);
    }

    public int disable(List<T> entitys) throws BusinessException {
        int result = 0;
        for (T entity : entitys) {
            result += disable(entity);
        }
        return result;
    }

    /**
     * 新增编码唯一性校验
     * @param entity
     * @return
     */
    public boolean isCodeRepeat(T entity) {
        entity.setDr(DBConst.DR_NORMAL);
        List<T> list = dao.select(entity);
        return (list != null && list.size() > 0);
    }

    /**
     * 修改编码唯一性校验
     * @param entity
     * @param id
     * @return
     */
    public boolean isCodeRepeat(T entity,String id) {
        entity.setDr(DBConst.DR_NORMAL);
        List<T> list = dao.select(entity);
        boolean result = false;
        for (T t : list) {
            if(!id.equals(t.getId())) {
                result = true;
                break;
            }
        }
        return result;
    }

}
