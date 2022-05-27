package io.renren.modules.doc_manage.service.impl;

import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.FmsDocumentService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.DocConfimApplyDao;
import io.renren.modules.doc_manage.entity.DocConfimApplyEntity;
import io.renren.modules.doc_manage.service.DocConfimApplyService;
import org.springframework.transaction.annotation.Transactional;


@Service("docConfimApplyService")
public class DocConfimApplyServiceImpl extends ServiceImpl<DocConfimApplyDao, DocConfimApplyEntity> implements DocConfimApplyService {
    @Autowired
    DocConfimApplyDao docConfimApplyDao;
    @Autowired
    FmsDocumentService fmsDocumentService;
    @Autowired
    DocCatalogService docCatalogService;
    @Autowired
    DocConfimApplyService docConfimApplyService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        IPage<DocConfimApplyEntity> result=null;
        if(!docCatalogService.isAdmin(user)) {
            params.put("userId",user.getUserId());
        }
        IPage<DocConfimApplyEntity> page=new DocQuery<DocConfimApplyEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.docConfimApplyDao.docConfirmApplyList(page);

        return new PageUtils(result);


    }
    /*
       档案修改完成时发送确认申请
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void docConfirm(Long docId){

          DocConfimApplyEntity docConfimApplyEntity = new DocConfimApplyEntity();
          docConfimApplyEntity.setApplyTime(new Date());
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
          docConfimApplyEntity.setOwnUserId(userId);
         FmsDocumentEntity fmsDocumentEntity = fmsDocumentService.getById(docId);
         if(fmsDocumentEntity==null){
             throw new RuntimeException("档案不存在");
         }
          docConfimApplyEntity.setCreateUserId(fmsDocumentEntity.getCreateUserId());
         docConfimApplyEntity.setDocId(fmsDocumentEntity.getDocId());
          docConfimApplyEntity.setApproveStatus(-1);//审核中
          this.save(docConfimApplyEntity);
          fmsDocumentEntity.setStatus(1);
          fmsDocumentService.updateById(fmsDocumentEntity);

    }
    @Override
    public PageUtils queryConfirmlistPage(Map<String, Object> params){
        IPage<DocConfimApplyEntity> result=null;
        IPage<DocConfimApplyEntity> page=new DocQuery<DocConfimApplyEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.docConfimApplyDao.docConfirmList(page);

        return new PageUtils(result);
    }

    @Override
    public DocConfimApplyEntity docApplyList(Map<String, Object> params) {
        return this.docConfimApplyDao.docApplyList(params);
    }

    @Override
    public boolean handleApply(Map<String, Object> params){
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long approver_id = user.getUserId();
        params.put("approverId", approver_id);
        params.put("approveStatus", -1);
        docConfimApplyDao.handle(params);
        return false;
    }
    /*
      处理档案确认申请
     */
    @Override
    public String reCallConfirm(Long docId){
        Map<String, Object> params=new HashMap<>();
        params.put("docId",docId);
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
        params.put("userId",userId);
        DocConfimApplyEntity docConfimApplyEntity = docConfimApplyService.docApplyList(params);
        if(docConfimApplyEntity==null){
            return ("非本人申请，不可撤回");
        }
       docConfimApplyEntity.setApproverId(userId);
       docConfimApplyEntity.setApproveTime(new Date());
       docConfimApplyEntity.setApproveStatus(1);
       docConfimApplyEntity.setApproveInfo("已撤回");
       docConfimApplyService.updateById(docConfimApplyEntity);
       FmsDocumentEntity fmsDocumentEntity = fmsDocumentService.getById(docId);
        if(fmsDocumentEntity==null){
            return ("档案不存在");
        }
       fmsDocumentEntity.setStatus(-1);
       fmsDocumentService.updateById(fmsDocumentEntity);

       return ("撤回确认申请成功");
    }


}
