package io.renren.modules.sys.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocbrowserPriDeptDao;
import io.renren.modules.sys.entity.FmsDocbrowserPriDeptEntity;
import io.renren.modules.sys.service.FmsDocbrowserPriDeptService;


@Service("fmsDocbrowserPriDeptService")
public class FmsDocbrowserPriDeptServiceImpl extends ServiceImpl<FmsDocbrowserPriDeptDao, FmsDocbrowserPriDeptEntity> implements FmsDocbrowserPriDeptService {
    @Autowired
    private FmsDocbrowserPriDeptDao fmsDocbrowserPriDeptDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocbrowserPriDeptEntity> page = this.page(
                new Query<FmsDocbrowserPriDeptEntity>().getPage(params),
                new QueryWrapper<FmsDocbrowserPriDeptEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public List<FmsDocbrowserPriDeptEntity> queryList(Map<String, Object> params){
        return fmsDocbrowserPriDeptDao.queryList(params);
    }
    public void deletepridept(@Param("docId")Long docId){
    fmsDocbrowserPriDeptDao.delete(docId);
    }

}
