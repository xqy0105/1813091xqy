package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.VDocInfoDetailEntity;
import io.renren.modules.doc_manage.entity.VDocInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * VIEW
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
public interface VDocInfoDetailService extends IService<VDocInfoDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryAllPage(Map<String, Object> params);
    List<VDocInfoEntity> selectDocInfo(Map<String,Object> params);
    VDocInfoEntity selectDocInfo(Long docID);
    public PageUtils queryDetailPage(Map<String, Object> params);
}

