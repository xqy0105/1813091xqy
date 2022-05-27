package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.FmsDocCatalogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 档案大类
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@Mapper
public interface FmsDocCatalogDao extends BaseMapper<FmsDocCatalogEntity> {
    List<FmsDocCatalogEntity> queryByDeptId(Long deptId);
}
