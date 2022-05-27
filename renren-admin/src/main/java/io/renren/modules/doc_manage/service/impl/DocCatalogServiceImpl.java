package io.renren.modules.doc_manage.service.impl;

import io.renren.common.utils.*;
import io.renren.modules.doc_manage.entity.DocTypeEntity;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.FmsDocBorrowEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.modules.doc_manage.dao.DocCatalogDao;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;


@Service("docCatalogService")
public class DocCatalogServiceImpl extends ServiceImpl<DocCatalogDao, DocCatalogEntity> implements DocCatalogService {

    @Autowired
private DocCatalogDao docCatalogDao;


    @Override
    public boolean isAdmin(SysUserEntity user){
        if (user.getUserId() == Constant.SUPER_ADMIN)
            return true;
        return (docCatalogDao.isAdmin(user.getUserId())>0);


    }

    @Override
    public boolean isAdmin() {
        SysUserEntity user=ShiroUtils.getUserEntity();
        return isAdmin(user);
    }

    @Override
    public boolean removeById(Long catalogId) {
        Map<String,Object> params=new HashedMap();
        params.put("catalogId",catalogId);
        SysUserEntity user = ShiroUtils.getUserEntity();
        params.put("userId",user.getUserId());
        params.put("isDone",0);

         docCatalogDao.delByCatalogId(params);
         try {

             return (Integer.parseInt(params.get("isDone").toString()) == 1) ;
         }
         catch(Exception e){
             log.error("delete catalogid",e);
             return false;


         }
        //调用删除过程
    }
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        System.out.println("大类 queryPage params： "+params.toString());
        //this.docCatalogDao.selectCatalogList(params);
        SysUserEntity user = ShiroUtils.getUserEntity();
        docCheckUtil check=new docCheckUtil();
       // List<?> result;
        IPage<DocCatalogEntity> result=null;
        DocCatalogEntity par=new DocCatalogEntity();
        if(isAdmin(user)) {
//            par.setDeptId(Long.valueOf(2));

           // result= this.docCatalogDao.selectCatalogList(new DocQuery<DocCatalogEntity>().getPage(params),params);
        }
        else{
            params.put("deptId",user.getDeptId());
        }
        IPage<DocCatalogEntity> page=new DocQuery<DocCatalogEntity>().getPage(params);
       // System.out.println(page.condition().get("deptId"));
            result= this.docCatalogDao.selectCatalogList(page);

        return new PageUtils(result);
    }
    @Override
    public List<DocCatalogEntity> queryList(Map<String, Object> map) {
        return docCatalogDao.queryList(map);
    }

    @Override
    public List<String> selectDocCatalogNameList(Map<String, Object> params) {
        return docCatalogDao.selectDocCatalogNameList(params);
    }
}
