package io.renren.modules.doc_manage.service.impl;

import io.renren.modules.doc_manage.entity.VDocInfoEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.VDocumentDao;
import io.renren.modules.doc_manage.entity.VDocumentEntity;
import io.renren.modules.doc_manage.service.VDocumentService;


@Service("vDocumentService")
public class VDocumentServiceImpl extends ServiceImpl<VDocumentDao, VDocumentEntity> implements VDocumentService {
    @Autowired
    VDocumentDao vDocumentDao;
    @Autowired
    private DocCatalogService docCatalogService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VDocumentEntity> result=null;
        VDocInfoDetailServiceImpl temp=new VDocInfoDetailServiceImpl();

        temp.rebuildOverParams(params);
        SysUserEntity user = ShiroUtils.getUserEntity();
        if(!docCatalogService.isAdmin(user)) {
            params.put("deptId",user.getDeptId());
        }
        IPage<VDocumentEntity> page=new DocQuery<VDocumentEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.vDocumentDao.selectDocInfoPage(page);

        return new PageUtils(result);
    }

}
