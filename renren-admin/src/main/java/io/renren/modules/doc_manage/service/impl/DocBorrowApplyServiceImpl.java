package io.renren.modules.doc_manage.service.impl;

import com.alibaba.druid.sql.visitor.functions.Now;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.dao.FmsDocBorrowDao;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import io.renren.modules.sys.entity.SysLogEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.DocBorrowApplyDao;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.doc_manage.service.DocBorrowApplyService;


@Service("docBorrowApplyService")
public class DocBorrowApplyServiceImpl extends ServiceImpl<DocBorrowApplyDao, DocBorrowApplyEntity> implements DocBorrowApplyService {
    @Autowired
    DocBorrowApplyDao docBorrowApplyDao;

    @Autowired
    FmsDocBorrowDao fmsDocBorrowDao;
    @Override
    public boolean apply(DocBorrowApplyEntity docBorrowApplyEntity) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long borrower_id = user.getUserId();

        docBorrowApplyEntity.setBorrowerId(borrower_id);
        docBorrowApplyEntity.setApplyResult(0); //申请中

        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("approverId", null);
//        params.put("returnTime", null);
        params.put("isApplied", 0);
        params.put("borrowerId", docBorrowApplyEntity.getBorrowerId());
        params.put("docId", docBorrowApplyEntity.getDocId());
//        params.put("applyTime", docBorrowApplyEntity.getApplytime());
        params.put("borrowNum", docBorrowApplyEntity.getBorrownum());
        params.put("applyResult", docBorrowApplyEntity.getApplyResult());
        params.put("comm", docBorrowApplyEntity.getComm());
        params.put("expirationDate", docBorrowApplyEntity.getExpirationDate());
        System.out.println("DocBorrowApplyServiceImpl map: "+ params.toString());
        docBorrowApplyDao.apply(params);
        int is_applied = (Integer)params.get("isApplied");
        return is_applied != 0;
    }

    /**
     * 通过或者驳回 借阅申请
     * @param params
     * @return
     */
    @Override
    public boolean handleApply(Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long approver_id = user.getUserId();
        params.put("approverId", approver_id);
        params.put("applyResult", 0);
        docBorrowApplyDao.handle(params);
        int is_applied = (Integer)params.get("applyResult");
        return is_applied==1;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<DocBorrowApplyEntity> page = this.page(
//                new Query<DocBorrowApplyEntity>().getPage(params),
//                new QueryWrapper<DocBorrowApplyEntity>()
//        );
        IPage<DocBorrowApplyEntity> result=null;
        IPage<DocBorrowApplyEntity> page=new DocQuery<DocBorrowApplyEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.docBorrowApplyDao.docBorrowApplyList(page);

        return new PageUtils(result);
    }

    @Override
    public PageUtils queryBorrwlistPage(Map<String, Object> params) {
        IPage<DocBorrowApplyEntity> result=null;
        IPage<DocBorrowApplyEntity> page=new DocQuery<DocBorrowApplyEntity>().getPage(params);
        // System.out.println(page.condition().get("deptId"));
        result= this.docBorrowApplyDao.docBorrowList(page);

        return new PageUtils(result);
    }

}
