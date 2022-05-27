package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocConfimApplyEntity;
import io.renren.modules.sys.entity.FmsDocumentEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-25 11:00:16
 */
public interface DocConfimApplyService extends IService<DocConfimApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void docConfirm(Long docId);
    String reCallConfirm(Long docId);
    boolean handleApply(Map<String, Object> params);
    PageUtils queryConfirmlistPage(Map<String, Object> params);
    DocConfimApplyEntity docApplyList(Map<String, Object> params);

}

