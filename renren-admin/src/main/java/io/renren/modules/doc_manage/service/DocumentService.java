package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocumentEntity;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * 档案
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-08 08:25:15
 */
public interface DocumentService extends IService<DocumentEntity> {
    boolean transInByIds(FmsDocumentEntity fmsDocument);
    boolean transOutByIds(Map<String, Object> params,StringBuilder message);
    boolean transRejectById(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params);
    PageUtils querydocTimelistPage(Map<String,Object> params);
}

