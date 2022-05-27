package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocBorrowEntity;
import io.renren.modules.sys.entity.FmsDocumentEntity;

import java.util.Map;

/**
 * 档案借阅
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
public interface FmsDocBorrowService extends IService<FmsDocBorrowEntity> {
    boolean safeSave(FmsDocBorrowEntity fmsDocBorrowEntity);
    void returnById(Map<String, Object> params );
    PageUtils queryPage(Map<String, Object> params);
}

