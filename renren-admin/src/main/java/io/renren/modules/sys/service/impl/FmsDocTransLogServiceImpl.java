package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.doc_manage.entity.DocumentEntity;
import io.renren.modules.doc_manage.util.DocQuery;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocTransLogDao;
import io.renren.modules.sys.entity.FmsDocTransLogEntity;
import io.renren.modules.sys.service.FmsDocTransLogService;


@Service("fmsDocTransLogService")
public class FmsDocTransLogServiceImpl extends ServiceImpl<FmsDocTransLogDao, FmsDocTransLogEntity> implements FmsDocTransLogService {
    @Autowired
    FmsDocTransLogDao fmsDocTransLogDao;

    public boolean isAdmin(SysUserEntity user){
        if (user.getUserId() == Constant.SUPER_ADMIN)
            return true;
//        return (fmsDocTransLogDao.isAdmin(user.getUserId())>0);
        return false;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        if(isAdmin(user)){
            params.put("userId", -1);
        }else params.put("userId", user.getUserId());
        System.out.println("TransLog params: "+params.toString());
        IPage<FmsDocTransLogEntity> page = new DocQuery<FmsDocTransLogEntity>().getPage(params);
        IPage<FmsDocTransLogEntity> result = fmsDocTransLogDao.transLogList(page);
        return new PageUtils(result);
    }

}
