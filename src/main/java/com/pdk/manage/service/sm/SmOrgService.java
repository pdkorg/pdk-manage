package com.pdk.manage.service.sm;

import com.pdk.manage.common.tree.TreeNode;
import com.pdk.manage.dao.sm.EmployeeDao;
import com.pdk.manage.dao.sm.OrgDao;
import com.pdk.manage.dto.sm.OrgJson;
import com.pdk.manage.model.sm.Org;
import com.pdk.manage.service.BaseService;
import com.pdk.manage.util.IdGenerator;
import com.pdk.manage.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liuhaiming on 2015/8/9.
 */
@Service
public class SmOrgService extends BaseService<Org> {
    org.slf4j.Logger log = LoggerFactory.getLogger(SmOrgService.class);

    @Autowired
    private EmployeeDao employeeDao;

    private OrgDao getDao() {
        return (OrgDao)this.dao;
    }

    @Override
    public String getModuleCode() {
        return IdGenerator.SM_MODULE_CODE;
    }

    public List<OrgJson> getOrgTreeRoot(boolean isEnableOnly) {

        List<Org> orgList = null;
        try {
            if ( isEnableOnly ) {
                orgList = getDao().selectEnableData();
            } else {
                orgList = selectAll();
            }
        } catch (Exception e) {
            orgList = new ArrayList<>();
            log.error("查询异常!", e);
        }

        List<OrgJson> result = new ArrayList<>();

        TreeNode<Org> rootOrg = TreeUtil.buildTree(orgList);

        for (TreeNode<Org> node : rootOrg.getChilds()) {
            result.add( chgNodeToRoleJson(node) );
        }

        return result;
    }

    private OrgJson chgNodeToRoleJson(TreeNode<Org> node) {
        Org org = node.getValue();
        OrgJson orgJson = new OrgJson(org);
        if (node.getChilds() != null) {

            List<OrgJson> children = new ArrayList<>();
            for (TreeNode<Org> chld : node.getChilds()) {
                children.add( chgNodeToRoleJson(chld) );
            }

            if (children.size() > 0) {
                orgJson.setChildren( children );
            } else {
                orgJson.setIcon( OrgJson.ICON_LEAF );
            }
        }

        return orgJson;
    }

    public String createInncode(String parentId) {
        String incode = null;

        if ( !StringUtils.isEmpty(parentId) ) {
            Org parentOrg = get(parentId);
            incode = parentOrg.getIncode() + createThislvl();
            int incodeCount = getDao().selectByInnerCode(incode);

            while ( incodeCount > 0 ) {
                incode = parentOrg.getIncode() + createThislvl();
                incodeCount = getDao().selectByInnerCode(incode);
            }
        } else {
            incode = createThislvl();
        }

        return incode;
    }

    private String createThislvl() {
        String codeLib = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random ran = new Random();

        String inncode = "";
        int LENGTH = 2;
        int tmpIdx = 0;
        for ( int idx = 0; idx < LENGTH; idx++ ) {
            tmpIdx = ran.nextInt(26);
            inncode += codeLib.substring( tmpIdx, tmpIdx+1 );
        }

        return inncode;
    }

    public boolean isCodeRepeat(Org org) {
        int count = getDao().repeatCountSelect(org);
        return count > 0;
    }

    public boolean isRefrence(String id) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        return employeeDao.refrencedCountSelect(ids, Org.class.getName()) > 0;
    }
}