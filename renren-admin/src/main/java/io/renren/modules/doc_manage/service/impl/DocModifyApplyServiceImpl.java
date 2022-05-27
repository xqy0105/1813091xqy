package io.renren.modules.doc_manage.service.impl;

import io.renren.modules.doc_manage.entity.DocConfimApplyEntity;
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
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.DocModifyApplyDao;
import io.renren.modules.doc_manage.entity.DocModifyApplyEntity;
import io.renren.modules.doc_manage.service.DocModifyApplyService;
import org.springframework.transaction.annotation.Transactional;


@Service("docModifyApplyService")
public class DocModifyApplyServiceImpl extends ServiceImpl<DocModifyApplyDao, DocModifyApplyEntity> implements DocModifyApplyService {
    @Autowired
    FmsDocumentService fmsDocumentService;
    @Autowired
    DocCatalogService docCatalogService;
    @Autowired
    DocModifyApplyDao docModifyApplyDao;
    @Autowired
    DocModifyApplyService docModifyApplyService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        IPage<DocModifyApplyEntity> result=null;
        if(!docCatalogService.isAdmin(user)) {
            params.put("userId",user.getUserId());
        }
        IPage<DocModifyApplyEntity> page=new DocQuery<DocModifyApplyEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.docModifyApplyDao.docModifyApplyList(page);


        return new PageUtils(page);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void docModify(Long docId){
        DocModifyApplyEntity docModifyApplyEntity = new DocModifyApplyEntity();
        docModifyApplyEntity.setApplyTime(new Date());
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
        docModifyApplyEntity.setOwnUserId(userId);
        FmsDocumentEntity fmsDocumentEntity = fmsDocumentService.getById(docId);
        if(fmsDocumentEntity==null){
            throw new RuntimeException("档案不存在");
        }
        docModifyApplyEntity.setCreateUserId(fmsDocumentEntity.getCreateUserId());
        docModifyApplyEntity.setDocId(fmsDocumentEntity.getDocId());
        docModifyApplyEntity.setApproveStatus(-1);
        this.save(docModifyApplyEntity);
        fmsDocumentEntity.setStatus(2);
        fmsDocumentService.updateById(fmsDocumentEntity);

    }
    @Override
    public DocModifyApplyEntity docApplyList(Map<String, Object> params){
        return this.docModifyApplyDao.docApplyList(params);
    }
    @Override
    public boolean handleApply(Map<String, Object> params){
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long approver_id = user.getUserId();
        params.put("approverId", approver_id);
        params.put("approveStatus", -1);
        docModifyApplyDao.handle(params);
        return false;
    }
    @Override
    public String reCallModify(Long docId){
        Map<String, Object> params=new HashMap<>();
        params.put("docId",docId);
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
        params.put("userId",userId);
        DocModifyApplyEntity docModifyApplyEntity= docModifyApplyService.docApplyList(params);
        if(docModifyApplyEntity == null){
            return ("非本人申请，不可撤回");
        }
        docModifyApplyEntity.setApproverId(userId);
        docModifyApplyEntity.setApproveTime(new Date());
        docModifyApplyEntity.setApproveStatus(1);
        docModifyApplyEntity.setApproveInfo("已撤回");
        docModifyApplyService.updateById(docModifyApplyEntity);
        FmsDocumentEntity fmsDocumentEntity = fmsDocumentService.getById(docId);
        if(fmsDocumentEntity==null){
            return ("档案不存在");
        }
        fmsDocumentEntity.setStatus(0);
        fmsDocumentService.updateById(fmsDocumentEntity);

        return ("撤回修改申请成功");

    }


}
