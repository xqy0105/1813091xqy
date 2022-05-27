package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;

import java.util.Map;

/**
 * 档案借阅申请
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 16:16:53
 */
public interface DocBorrowApplyService extends IService<DocBorrowApplyEntity> {
    boolean apply(DocBorrowApplyEntity docBorrowApplyEntity);
    boolean handleApply(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryBorrwlistPage(Map<String, Object> params);
}

