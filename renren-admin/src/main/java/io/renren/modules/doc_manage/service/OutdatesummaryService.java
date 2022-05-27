package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocumentEntity;
import io.renren.modules.doc_manage.entity.OutdatesummaryEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-13 21:15:09
 */
public interface OutdatesummaryService extends IService<OutdatesummaryEntity> {

    List<OutdatesummaryEntity> queryList(Map<String, Object> params);
    List<OutdatesummaryEntity> querycommonsummaryList(Map<String, Object> params);

}

