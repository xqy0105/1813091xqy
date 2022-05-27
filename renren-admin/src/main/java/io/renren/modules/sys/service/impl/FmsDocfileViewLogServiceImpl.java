package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocfileViewLogDao;
import io.renren.modules.sys.entity.FmsDocfileViewLogEntity;
import io.renren.modules.sys.service.FmsDocfileViewLogService;


@Service("fmsDocfileViewLogService")
public class FmsDocfileViewLogServiceImpl extends ServiceImpl<FmsDocfileViewLogDao, FmsDocfileViewLogEntity> implements FmsDocfileViewLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocfileViewLogEntity> page = this.page(
                new Query<FmsDocfileViewLogEntity>().getPage(params),
                new QueryWrapper<FmsDocfileViewLogEntity>()
        );

        return new PageUtils(page);
    }

}
