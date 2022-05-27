package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocumentEntity;

import java.util.List;
import java.util.Map;

/**
 * 档案
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:15
 */
public interface FmsDocumentService extends IService<FmsDocumentEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public List<FmsDocumentEntity> queryList(Map<String, Object> map);
    void deletedoc(Long docId,Long userId);
}

