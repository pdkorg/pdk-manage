package com.pdk.manage.util;

import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by liuhaiming on 2015/8/28
 */
public class RepeatColumnHelper {

    private static volatile Map<Class<?>, List<RepeatColumn>> repeatColumnMap = new HashMap<>();
    private static volatile RepeatColumnGroup repeatColumnGroup = null;

    public static List<RepeatColumn> getRepeatColumns(Class<?> entityClass) {

        if(!repeatColumnMap.containsKey(entityClass)) {

            synchronized (repeatColumnMap) {

                if(!repeatColumnMap.containsKey(entityClass)) {

                    repeatColumnMap.put(entityClass, new ArrayList<RepeatColumn>());

                    List<Field> fieldList = getAllField(entityClass, null);

                    for (Field field : fieldList) {
                        String columnName = null;
                        if (field.isAnnotationPresent(com.pdk.manage.annotation.RepeatColumn.class)) {
                            com.pdk.manage.annotation.RepeatColumn column = field.getAnnotation(com.pdk.manage.annotation.RepeatColumn.class);
                            columnName = column.column();
                            if (StringUtils.isEmpty(StringUtils.trim(columnName))) {
                                columnName = EntityHelper.camelhumpToUnderline(field.getName());
                            }

                            RepeatColumn c = new RepeatColumn();
                            c.setColumn(columnName);
                            repeatColumnMap.get(entityClass).add(c);
                        }

                    }
                }
            }
        }

        return repeatColumnMap.get(entityClass);
    }

    public static RepeatColumnGroup getRepeatColumnsGroup(Class<?> entityClass) {

        if(repeatColumnGroup == null) {
            synchronized (RepeatColumnHelper.class) {
                if(repeatColumnGroup == null) {
                    if (entityClass.isAnnotationPresent(com.pdk.manage.annotation.RepeatColumnGroup.class)) {
                        com.pdk.manage.annotation.RepeatColumnGroup group = entityClass.getAnnotation(com.pdk.manage.annotation.RepeatColumnGroup.class);
                        String strColumns = group.columns();
                        if (StringUtils.isEmpty(StringUtils.trim(strColumns))) {
                            return repeatColumnGroup;
                        }

                        repeatColumnGroup = new RepeatColumnGroup();
                        List<String> columns = new ArrayList<>();
                        for ( String col : strColumns.split(",") ) {
                            columns.add(col);
                        }
                        repeatColumnGroup.setColumns(columns);
                    }
                }
            }
        }

        return repeatColumnGroup;
    }

    /**
     * 获取全部的Field
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    private static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new LinkedList<>();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null
                && !superClass.equals(Object.class)
                && (superClass.isAnnotationPresent(Entity.class)
                || (!Map.class.isAssignableFrom(superClass)
                && !Collection.class.isAssignableFrom(superClass)))) {
            return getAllField(entityClass.getSuperclass(), fieldList);
        }
        return fieldList;
    }


    public static class RepeatColumn {

        private String column;

        private String value;

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class RepeatColumnGroup {

        private List<String> columns;

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }
    }

}
