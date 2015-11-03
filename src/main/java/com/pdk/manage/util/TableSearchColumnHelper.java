package com.pdk.manage.util;

import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.persistence.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by hubo on 2015/8/27
 */
public class TableSearchColumnHelper {

    private final static Map<Class<?>, List<TableSearchColumn>> searchColumnMap = new HashMap<>();

    public static List<TableSearchColumn> getTableSearchColumns(Class<?> entityClass) {

        if(!searchColumnMap.containsKey(entityClass)) {

            synchronized (searchColumnMap) {

                if(!searchColumnMap.containsKey(entityClass)) {

                    searchColumnMap.put(entityClass, new ArrayList<TableSearchColumn>());

                    List<Field> fieldList = getAllField(entityClass, null);

                    for (Field field : fieldList) {
                        String columnName;
                        if (field.isAnnotationPresent(com.pdk.manage.annotation.TableSearchColumn.class)) {
                            com.pdk.manage.annotation.TableSearchColumn column = field.getAnnotation(com.pdk.manage.annotation.TableSearchColumn.class);
                            columnName = column.column();
                            if (StringUtils.isEmpty(StringUtils.trim(columnName))) {
                                columnName = EntityHelper.camelhumpToUnderline(field.getName());
                            }
                            TableSearchColumn c = new TableSearchColumn();
                            c.setColumn(columnName);
                            if(StringUtils.isNotEmpty(column.joinTable())) {
                                c.setJoinTable(column.joinTable());
                                c.setJoinId(EntityHelper.camelhumpToUnderline(field.getName()));
                            }
                            searchColumnMap.get(entityClass).add(c);
                        }

                    }
                }
            }
        }

        return searchColumnMap.get(entityClass);
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
            fieldList = new LinkedList<Field>();
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


    public static class TableSearchColumn {

        private String column;

        private String joinTable;

        private String prefix;

        private String joinId;

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getJoinTable() {
            return joinTable;
        }

        public void setJoinTable(String joinTable) {
            this.joinTable = joinTable;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getJoinId() {
            return joinId;
        }

        public void setJoinId(String joinId) {
            this.joinId = joinId;
        }
    }

}
