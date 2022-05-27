package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocTypeEntity;

import java.util.Map;

/**
 * 档案类型
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
public interface FmsDocTypeService extends IService<FmsDocTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

