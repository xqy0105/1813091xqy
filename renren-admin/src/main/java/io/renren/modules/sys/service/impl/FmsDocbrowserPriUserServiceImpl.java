package io.renren.modules.sys.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocbrowserPriUserDao;
import io.renren.modules.sys.entity.FmsDocbrowserPriUserEntity;
import io.renren.modules.sys.service.FmsDocbrowserPriUserService;


@Service("fmsDocbrowserPriUserService")
public class FmsDocbrowserPriUserServiceImpl extends ServiceImpl<FmsDocbrowserPriUserDao, FmsDocbrowserPriUserEntity> implements FmsDocbrowserPriUserService {
    @Autowired
    private FmsDocbrowserPriUserDao fmsDocbrowserPriUserDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocbrowserPriUserEntity> page = this.page(
                new Query<FmsDocbrowserPriUserEntity>().getPage(params),
                new QueryWrapper<FmsDocbrowserPriUserEntity>()
        );

        return new PageUtils(page);
    }
    @Override
     public List<FmsDocbrowserPriUserEntity> queryList(Map<String, Object> params){
        return fmsDocbrowserPriUserDao.queryList(params);
        }
    public void deletepriuser(@Param("docId")Long docId){
        fmsDocbrowserPriUserDao.delete(docId);
    }





}
