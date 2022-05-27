package io.renren.common.utils;

import io.renren.modules.doc_manage.dao.DocCatalogDao;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class docCheckUtil {
    @Autowired
    private DocCatalogService docCatalogService;
    /**
    判断当前用户
     */
    public boolean isAdmin(){
        SysUserEntity user=ShiroUtils.getUserEntity();
        return isAdmin(user);

    }

    /**
     * 判断某个用户是否系统管理员
     * @param user
     * @return
     */
    public boolean isAdmin(SysUserEntity user){
        if (user.getUserId() == Constant.SUPER_ADMIN)
            return true;

        return docCatalogService.isAdmin(user);
    }
}
