package io.renren.modules.doc_manage.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
//import io.renren.modules.doc_manage.entity.FmsDocTransLogEntity;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.FmsDocumentService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.DocumentDao;
import io.renren.modules.doc_manage.entity.DocumentEntity;
import io.renren.modules.doc_manage.service.DocumentService;


@Service("documentService")
public class DocumentServiceImpl extends ServiceImpl<DocumentDao, DocumentEntity> implements DocumentService {

    @Autowired
    DocumentDao documentDao;
    @Autowired
    private FmsDocumentService fmsDocumentService;
    public boolean isAdmin(SysUserEntity user){
        if (user.getUserId() == Constant.SUPER_ADMIN)
            return true;
        return false;
    }

    @Override
    public boolean transInByIds(FmsDocumentEntity document) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long to_user_id = user.getUserId();
        HashMap<String, Object> params = new HashMap<>();
        params.put("docId", document.getDocId());
        if(!isAdmin(user))//如果为系统管理员可以接受非本人档案
        params.put("toUserId", to_user_id);
        params.put("comm","由"+user.getUsername()+"接收");
        params.put("state", "已完成");
        documentDao.transIn(params);
        fmsDocumentService.updateById(document);//更新档案类型
        documentDao.transInUpdateOwner(params);//只更新交接状态
        return true;
    }

    @Override
    public boolean transOutByIds(Map<String, Object> params,StringBuilder message) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long from_user_id = user.getUserId();
        params.put("fromUserId", from_user_id);
        params.put("state", "已交出");
        String arrayStr = "";
        for(String docId : (List<String>)params.get("docIds")){
            System.out.println("docId "+docId);
            arrayStr = arrayStr + docId + ",";
        }
        params.put("arrayStr", arrayStr);
        params.put("sSplit", ",");
        params.put("result", -1);
//        System.out.println("Serviec Level transOut Param: "+params.toString());
        documentDao.transOut(params);
        int result=(Integer) params.get("result");


        if(result==1)
            return true;
        else {

            message=message.append("共有"+Math.abs(result)+"个档案接收人和档案交出人为同一人，不能转出。需要交接给其他人。");
            return false;
        }


    }

    @Override
    public boolean transRejectById(Map<String, Object> params) {

        params.put("result", -1);
//        System.out.println("Serviec Level transOut Param: "+params.toString());
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long from_user_id = user.getUserId();
        String pcomm=(String)params.get("p_comm");
        params.put("p_comm","由"+user.getUsername()+"退回，退回原因："+pcomm);

        documentDao.transReject(params);

        return (Integer) params.get("result") == 1;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String catalogName = params.get("catalogName") == null?"%%":"%"+params.get("catalogName")+"%";
        String docTypeName = params.get("docTypeName") == null?"%%":"%"+params.get("docTypeName")+"%";
        String docName = params.get("docName") == null?"%%":"%"+params.get("docName")+"%";
        String beginDatefrom = params.get("beginDatefrom") == null?"%%":"%"+params.get("beginDatefrom")+"%";
        String beginDateto = params.get("beginDateto") == null?"%%":"%"+params.get("beginDateto")+"%";
        String endDatefrom = params.get("endDatefrom") == null?"%%":"%"+params.get("endDatefrom")+"%";
        String endDateto = params.get("endDateto") == null?"%%":"%"+params.get("endDateto")+"%";
        String contractA = params.get("contractA") == null?"%%":"%"+params.get("contractA")+"%";

//        System.out.println("Pararararara: "+docTypeName+"  "+catalogName+"  "+docName+ " "+beginDate+" "+endDate+" "+contractA);
        IPage<DocumentEntity> result;
//        HashMap<String, Object> map = new HashMap<>();
        params.put("catalogName", catalogName);
        params.put("docTypeName", docTypeName);
        params.put("docName", docName);
        params.put("beginDatefrom", beginDatefrom);
        params.put("beginDateto", beginDateto);
        params.put("endDatefrom", endDatefrom);
        params.put("endDateto", endDateto);
        params.put("contractA", contractA);
        SysUserEntity user = ShiroUtils.getUserEntity();
        if(!isAdmin(user)){
            Long userId = user.getUserId();
            params.put("userId", userId);
            System.out.println("USER ID: "+userId);
        } else params.put("userId", -1);
        IPage<DocumentEntity> page = new DocQuery<DocumentEntity>().getPage(params);

        Object re =  params.get("transable");
        if(re != null){
            result = this.documentDao.searchTransable(page);
        }
        else if( params.get("trans_in") != null){
            //System.out.println("TRANS IN SERVICEIMPL");
//            IPage<FmsDocTransLogEntity> resultlog;
//            IPage<FmsDocTransLogEntity> pagelog = new DocQuery<FmsDocTransLogEntity>().getPage(params);
            result = this.documentDao.searchTransIn(page);
           // return new PageUtils(resultlog);
        }
        else result = this.documentDao.search(page);

//       for(DocumentEntity doc : result.getRecords()){
//           System.out.println(doc.getDocId()+" "+doc.getDocName()+" "+doc.getDocTypeName());
//       }

        return new PageUtils(result);
    }
    @Override
    public PageUtils querydocTimelistPage(Map<String,Object> params){
        IPage<DocumentEntity> result = null;
        SysUserEntity user = ShiroUtils.getUserEntity();
        if(!isAdmin(user)){
            Long userId = user.getUserId();
            params.put("userId", userId);
            System.out.println("USER ID: "+userId);
        } else params.put("userId", -1);
        IPage<DocumentEntity> page = new DocQuery<DocumentEntity>().getPage(params);
        result = this.documentDao.docTimelist(page);
        return new PageUtils(result);
    }

}
