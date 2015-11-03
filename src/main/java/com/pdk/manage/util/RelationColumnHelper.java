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
public class RelationColumnHelper {

    private static volatile Map<Class<?>, List<RelationColumn>> relationColumnMap = new HashMap<>();

    public static List<RelationColumn> getRelationColumns(Class<?> entityClass) {

        if(!relationColumnMap.containsKey(entityClass)) {

            synchronized (relationColumnMap) {

                if(!relationColumnMap.containsKey(entityClass)) {

                    relationColumnMap.put(entityClass, new ArrayList<RelationColumn>());

                    List<Field> fieldList = getAllField(entityClass, null);

                    for (Field field : fieldList) {
                        String columnName = null;
                        String relationCol = null;
                        if (field.isAnnotationPresent(com.pdk.manage.annotation.RelationColumn.class)) {
                            com.pdk.manage.annotation.RelationColumn column = field.getAnnotation(com.pdk.manage.annotation.RelationColumn.class);
                            columnName = column.column();
                            relationCol = column.relationKey();
                            if (StringUtils.isEmpty(StringUtils.trim(columnName))) {
                                columnName = EntityHelper.camelhumpToUnderline(field.getName());
                            }
                            if (StringUtils.isEmpty(StringUtils.trim(relationCol))) {
                                relationCol = EntityHelper.camelhumpToUnderline(field.getName());
                            }

                            RelationColumn c = new RelationColumn();
                            c.setColumn(columnName);
                            c.setRelationColKey(relationCol);
                            relationColumnMap.get(entityClass).add(c);
                        }

                    }
                }
            }
        }

        return relationColumnMap.get(entityClass);
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


    public static class RelationColumn {

        private String column;

        private String relationColKey;

        private String value;

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getRelationColKey() {
            return relationColKey;
        }

        public void setRelationColKey(String relationColKey) {
            this.relationColKey = relationColKey;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
