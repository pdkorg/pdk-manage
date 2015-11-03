package com.pdk.manage.dao.common.provider;

import com.pdk.manage.util.RelationColumnHelper;
import com.pdk.manage.util.RepeatColumnHelper;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.*;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by liuhaiming on 2015/8/27.
*/
public class BusinessLogicMapperProvider extends MapperTemplate {

    public BusinessLogicMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询参照引用数据
     * @param ms
     * @return
     */
    public SqlNode refrencedCountSelect(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);

        //修改返回值类型为实体类型
        setResultType(ms, Integer.class);

        List<RelationColumnHelper.RelationColumn> refColumns = RelationColumnHelper.getRelationColumns(entityClass);

        List<SqlNode> sqlNodes = new LinkedList<>();

        String selectColumns = EntityHelper.getSelectColumns(entityClass);

        String tableName = tableName(entityClass);

        sqlNodes.add( new StaticTextSqlNode("SELECT COUNT(id) FROM " + tableName) );
        WhereSqlNode whereSqlNode = new WhereSqlNode(ms.getConfiguration(), new StaticTextSqlNode(" dr = 0 "));
        sqlNodes.add(whereSqlNode);

        for (RelationColumnHelper.RelationColumn refColumn : refColumns) {
            ForEachSqlNode forEachSqlNode = new ForEachSqlNode(ms.getConfiguration(),
                    new StaticTextSqlNode("#{item}"), "refIds", "index", "item", " and " + refColumn.getColumn() + " in (", ")", ",");
            IfSqlNode ifSqlNode = new IfSqlNode(forEachSqlNode, " refCol == '" + refColumn.getRelationColKey() + "'");

            sqlNodes.add(ifSqlNode);
        }

        return new MixedSqlNode(sqlNodes);
    }

    /**
     * 查询重复数据
     * @param ms
     * @return
     */
    public SqlNode repeatCountSelect(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);

        //修改返回值类型为实体类型
        setResultType(ms, Integer.class);

        List<RepeatColumnHelper.RepeatColumn> repeatColumns = RepeatColumnHelper.getRepeatColumns(entityClass);
        RepeatColumnHelper.RepeatColumnGroup repeatColumnGroup = RepeatColumnHelper.getRepeatColumnsGroup(entityClass);
        List<SqlNode> sqlNodes = new LinkedList<>();
        String tableName = tableName(entityClass);

        sqlNodes.add( new StaticTextSqlNode("SELECT COUNT(id) FROM " + tableName) );
        WhereSqlNode whereSqlNode = new WhereSqlNode(ms.getConfiguration(), new StaticTextSqlNode(" dr = 0 "));
        sqlNodes.add(whereSqlNode);

        StaticTextSqlNode idNode = new StaticTextSqlNode( " and id != #{vo.id, jdbcType=VARCHAR} " );
        IfSqlNode idIfSqlNode = new IfSqlNode(idNode, " vo.id != null and vo.id != '' ");
        sqlNodes.add(idIfSqlNode);

        if ( repeatColumnGroup != null ) { // 如果设置了组合校验，则不再进行单个字段的唯一性校验规则
            StringBuffer sbFiled = new StringBuffer();
            StringBuffer sbValue = new StringBuffer();
            boolean isFirst = true;
            for (String field : repeatColumnGroup.getColumns()) {
                sbFiled.append(",").append(field);
                sbValue.append(",").append("#{vo." + field + ", jdbcType=VARCHAR}");
            }

            StaticTextSqlNode sqlNode = new StaticTextSqlNode( " and CONCAT(" + sbFiled.substring(1) + ") = CONCAT(" + sbValue.substring(1) + ")" );
            sqlNodes.add(sqlNode);
        } else { // 单个字段的唯一性校验规则
            sqlNodes.add(new StaticTextSqlNode(" and ( "));
            boolean isFirst = true;
            for (RepeatColumnHelper.RepeatColumn repeatColumn : repeatColumns) {
                StaticTextSqlNode sqlNode = new StaticTextSqlNode( repeatColumn.getColumn() + " = #{vo." + repeatColumn.getColumn() + ", jdbcType=VARCHAR} " );

                if ( !isFirst ) {
                    sqlNodes.add(new StaticTextSqlNode(" or "));
                } else {
                    isFirst = false;
                }

                sqlNodes.add(sqlNode);

            }
            sqlNodes.add(new StaticTextSqlNode(" ) "));
        }


        return new MixedSqlNode(sqlNodes);
    }
}
