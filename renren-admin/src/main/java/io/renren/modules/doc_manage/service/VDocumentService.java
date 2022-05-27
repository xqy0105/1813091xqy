package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.VDocumentEntity;

import java.util.Map;

/**
 * VIEW
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
public interface VDocumentService extends IService<VDocumentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

