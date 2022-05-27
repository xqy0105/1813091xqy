package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocCatalogDao;
import io.renren.modules.sys.entity.FmsDocCatalogEntity;
import io.renren.modules.sys.service.FmsDocCatalogService;


@Service("fmsDocCatalogService")
public class FmsDocCatalogServiceImpl extends ServiceImpl<FmsDocCatalogDao, FmsDocCatalogEntity> implements FmsDocCatalogService {

    @Autowired
    FmsDocCatalogDao fmsDocCatalogDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocCatalogEntity> page = this.page(
                new Query<FmsDocCatalogEntity>().getPage(params),
                new QueryWrapper<FmsDocCatalogEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<FmsDocCatalogEntity> queryByDeptId(Long deptId) {
        return fmsDocCatalogDao.queryByDeptId(deptId);
    }

}
