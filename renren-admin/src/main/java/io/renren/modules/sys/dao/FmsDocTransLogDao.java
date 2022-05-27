package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.sys.entity.FmsDocTransLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 档案流转日志
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@Mapper
public interface FmsDocTransLogDao extends BaseMapper<FmsDocTransLogEntity> {
    IPage<FmsDocTransLogEntity> transLogList(IPage<FmsDocTransLogEntity> page);
}
