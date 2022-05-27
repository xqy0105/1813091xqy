package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocTypeDao;
import io.renren.modules.sys.entity.FmsDocTypeEntity;
import io.renren.modules.sys.service.FmsDocTypeService;


@Service("fmsDocTypeService")
public class FmsDocTypeServiceImpl extends ServiceImpl<FmsDocTypeDao, FmsDocTypeEntity> implements FmsDocTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocTypeEntity> page = this.page(
                new Query<FmsDocTypeEntity>().getPage(params),
                new QueryWrapper<FmsDocTypeEntity>()
        );

        return new PageUtils(page);
    }

}
