package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocBorrowDao;
import io.renren.modules.sys.entity.FmsDocBorrowEntity;
import io.renren.modules.sys.service.FmsDocBorrowService;


@Service("fmsDocBorrowService")
public class FmsDocBorrowServiceImpl extends ServiceImpl<FmsDocBorrowDao, FmsDocBorrowEntity> implements FmsDocBorrowService {

    @Autowired
    FmsDocBorrowDao fmsDocBorrowDao;

    public boolean isAdmin(SysUserEntity user){
        if (user.getUserId() == Constant.SUPER_ADMIN)
            return true;
        return (fmsDocBorrowDao.isAdmin(user.getUserId())>0);
    }

    @Override
    public boolean safeSave(FmsDocBorrowEntity fmsDocBorrowEntity) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println("doc_borrow_id: "+fmsDocBorrowEntity.getDocBorrowId());
        map.put("approverId", fmsDocBorrowEntity.getApproverId());
        map.put("borrowerId", fmsDocBorrowEntity.getBorrowerId());
        map.put("docId", fmsDocBorrowEntity.getDocId());
        map.put("applytime", fmsDocBorrowEntity.getApplytime());
        map.put("borrowtime", fmsDocBorrowEntity.getBorrowtime());
        map.put("expirationDate", fmsDocBorrowEntity.getExpirationDate());
        map.put("returnTime", fmsDocBorrowEntity.getReturnTime());
        map.put("borrownum", fmsDocBorrowEntity.getBorrownum());
        map.put("comm", fmsDocBorrowEntity.getComm());
        map.put("isDone", -1);// 是否完成;
        System.out.println("The Map: "+map.toString());
        System.out.println("The entity: "+fmsDocBorrowEntity.toString());
        fmsDocBorrowDao.safeSave(map);
        return (Integer) map.get("isDone") == 1;
    }

    /**
     * 按归还表id 归还
     * @param params
     */
    @Override
    public void returnById(Map<String, Object> params) {
        fmsDocBorrowDao.returnById(params);
//        return true;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage<FmsDocBorrowEntity> result=null;
        IPage<FmsDocBorrowEntity> page=new DocQuery<FmsDocBorrowEntity>().getPage(params);
         System.out.println("FmsDocBorrow params: "+page.toString());
        result= this.fmsDocBorrowDao.docBorrowList(page);

        return new PageUtils(result);
    }


}
