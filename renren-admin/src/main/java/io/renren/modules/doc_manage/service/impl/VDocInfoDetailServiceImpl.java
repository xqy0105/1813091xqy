package io.renren.modules.doc_manage.service.impl;

import io.renren.common.utils.docCheckUtil;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.entity.DocTypeEntity;
import io.renren.modules.doc_manage.entity.VDocInfoEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysConfigService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.VDocInfoDetailDao;
import io.renren.modules.doc_manage.entity.VDocInfoDetailEntity;
import io.renren.modules.doc_manage.service.VDocInfoDetailService;


@Service("vDocInfoDetailService")
public class VDocInfoDetailServiceImpl extends ServiceImpl<VDocInfoDetailDao, VDocInfoDetailEntity> implements VDocInfoDetailService {
    @Autowired
    private DocCatalogService docCatalogService;
    @Autowired
    VDocInfoDetailDao vDocInfoDetailDao;
    @Autowired
    SysConfigService sysConfigService;

    Date addDate(int days){
        Date now=new Date();
        Calendar c=Calendar.getInstance();
        //c.
        c.add(Calendar.DATE,days);
        return c.getTime();
    }
    /**
     * 如果查询条件中有过期类型，需要重新解析
     * @param params
     */
    void rebuildOverParams(Map<String, Object> params){
        if(params.containsKey("overtimetype")){
            Map<String,Integer> overtime=
            sysConfigService.getValuepair("outdate",params.get("overtimetype").toString());
            if(overtime!=null&&!overtime.isEmpty()){
                Date endDateFrom=addDate(overtime.get("min"));
                Date endDateTo=addDate(overtime.get("max")-1);
                //如果params中有endDatefrom,则替换为范围更小的
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date endDatefrom=null;
                try{
                    endDatefrom=sdf.parse(params.get("endDatefrom").toString());
                }
                catch(Exception e){

                }

                if(endDatefrom==null || (endDatefrom.getTime()<endDateFrom.getTime())){
                    params.put("endDatefrom",sdf.format(endDateFrom));
                }
                Date endDateto=null;
                try{
                    endDateto=sdf.parse(params.get("endDateto").toString());
                }
                catch(Exception e){

                }
                if(endDateto==null || (endDateto.getTime()>endDateTo.getTime())){
                    params.put("endDateto",sdf.format(endDateTo));
                }




            }
        }
    }
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        SysUserEntity user = ShiroUtils.getUserEntity();
        if(!docCatalogService.isAdmin(user)) {
            params.put("deptId",user.getDeptId());
        }


        return queryAllPage(params);
    }

    @Override
    public PageUtils queryAllPage(Map<String, Object> params) {


        IPage<VDocInfoEntity> result=null;


        rebuildOverParams(params);
        IPage<VDocInfoEntity> page=new DocQuery<VDocInfoEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.vDocInfoDetailDao.selectDocInfoPage(page);

        return new PageUtils(result);
    }

    @Override
    public PageUtils queryDetailPage(Map<String, Object> params) {

        SysUserEntity user = ShiroUtils.getUserEntity();

        IPage<VDocInfoDetailEntity> result=null;

        if(!docCatalogService.isAdmin(user)) {
            params.put("deptId",user.getDeptId());
        }
        rebuildOverParams(params);
        IPage<VDocInfoDetailEntity> page=new DocQuery<VDocInfoDetailEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.vDocInfoDetailDao.selectDocInfoDetail(page);

        return new PageUtils(result);
    }
    @Override
    public List<VDocInfoEntity> selectDocInfo(Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        if(!docCatalogService.isAdmin(user)) {
            params.put("deptId",user.getDeptId());
        }
        return vDocInfoDetailDao.selectDocInfo(params);
    }

    @Override
    public VDocInfoEntity selectDocInfo(Long docID) {

        return vDocInfoDetailDao.selectDocInfoDetailById(docID);
    }

}
