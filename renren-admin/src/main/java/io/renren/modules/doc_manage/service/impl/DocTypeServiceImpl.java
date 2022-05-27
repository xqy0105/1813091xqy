package io.renren.modules.doc_manage.service.impl;

import io.renren.common.utils.docCheckUtil;
import io.renren.modules.doc_manage.dao.DocCatalogDao;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.doc_manage.util.DocQuery;
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
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.DocTypeDao;
import io.renren.modules.doc_manage.entity.DocTypeEntity;
import io.renren.modules.doc_manage.service.DocTypeService;


@Service("docTypeService")
public class DocTypeServiceImpl extends ServiceImpl<DocTypeDao, DocTypeEntity> implements DocTypeService {
    @Autowired
    private DocCatalogService docCatalogService;
    @Autowired
    private DocTypeDao docTypeDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //this.docCatalogDao.selectCatalogList(params);
        SysUserEntity user = ShiroUtils.getUserEntity();
        docCheckUtil check=new docCheckUtil();
        // List<?> result;
        IPage<DocTypeEntity> result=null;
        DocCatalogEntity par=new DocCatalogEntity();
        if(!docCatalogService.isAdmin(user)) {
            params.put("deptId",user.getDeptId());
        }
        IPage<DocTypeEntity> page=new DocQuery<DocTypeEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.docTypeDao.selectDocTypeList(page);

        return new PageUtils(result);
    }
    @Override
    public boolean removeById(Long docTypeId) {
        Map<String,Object> params=new HashedMap();
        params.put("docTypeId",docTypeId);
        SysUserEntity user = ShiroUtils.getUserEntity();
        params.put("userId",user.getUserId());
        params.put("isDone",0);

        docTypeDao.delByDocTypeId(params);
        try {

            return (Integer.parseInt(params.get("isDone").toString()) == 1) ;
        }
        catch(Exception e){
            log.error("delete doctype",e);
            return false;


        }
        //调用删除过程
    }

    @Override
    public boolean updateBySingleId(DocTypeEntity docTypeEntity) {
        return false;

    }

    @Override
    public int getDocCountByDocTypeId(Long docTypeId) {

        return docTypeDao.selectCountByDocTypeID(docTypeId);
    }

    @Override
    public DocTypeEntity getdocTypeById(Long docTypeId) {
        Map <String,Object> para=new HashedMap();
        para.put("docTypeId",docTypeId);
        List <DocTypeEntity>result= docTypeDao.selectDocType(para);
        return result.size()>0?result.get(0):null;
    }

    @Override
    public DocTypeEntity getdocType(Map<String, Object> map) {
        List <DocTypeEntity>result= docTypeDao.selectDocType(map);
        return result.size()>0?result.get(0):null;
    }

    @Override
    public List<DocTypeEntity> queryList(Map<String, Object> map) {
        return docTypeDao.queryList(map);
    }

    @Override
    public List<DocTypeEntity> queryListByCatalogId(Map<String, Object> map) {
       // System.out.println("query docType list map: "+map.toString());
        return docTypeDao.queryList(map);
    }

    @Override
    public List<String> selectDocTypeNameList(Map<String, Object> params) {
        return docTypeDao.selectDocTypeNameList(params);
    }


}
