package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocModifyLogDao;
import io.renren.modules.sys.entity.FmsDocModifyLogEntity;
import io.renren.modules.sys.service.FmsDocModifyLogService;


@Service("fmsDocModifyLogService")
public class FmsDocModifyLogServiceImpl extends ServiceImpl<FmsDocModifyLogDao, FmsDocModifyLogEntity> implements FmsDocModifyLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocModifyLogEntity> page = this.page(
                new Query<FmsDocModifyLogEntity>().getPage(params),
                new QueryWrapper<FmsDocModifyLogEntity>()
        );

        return new PageUtils(page);
    }

}
