package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocModifyLogEntity;

import java.util.Map;

/**
 * 档案修改日志
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
public interface FmsDocModifyLogService extends IService<FmsDocModifyLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

