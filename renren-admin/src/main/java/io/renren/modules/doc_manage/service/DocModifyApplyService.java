package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocConfimApplyEntity;
import io.renren.modules.doc_manage.entity.DocModifyApplyEntity;

import java.util.Map;

/**
 * 
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-11-20 09:43:28
 */
public interface DocModifyApplyService extends IService<DocModifyApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void docModify(Long docId);
    String reCallModify(Long docId);
    boolean handleApply(Map<String, Object> params);
    DocModifyApplyEntity docApplyList(Map<String, Object> params);
}

