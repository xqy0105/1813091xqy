package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocfileViewLogEntity;

import java.util.Map;

/**
 * 文件浏览日志
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
public interface FmsDocfileViewLogService extends IService<FmsDocfileViewLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

