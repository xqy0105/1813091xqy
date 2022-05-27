package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocCatalogEntity;

import java.util.List;
import java.util.Map;

/**
 * 档案大类
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
public interface FmsDocCatalogService extends IService<FmsDocCatalogEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<FmsDocCatalogEntity> queryByDeptId(Long deptId);
}

