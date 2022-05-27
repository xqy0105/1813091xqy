package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.DocModifyApplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-11-20 09:43:28
 */
@Mapper
public interface DocModifyApplyDao extends BaseMapper<DocModifyApplyEntity> {
    void handle(Map<String, Object> params);

    IPage<DocModifyApplyEntity> docModifyApplyList(IPage<DocModifyApplyEntity> page);
    IPage<DocModifyApplyEntity> docModifyList(IPage<DocModifyApplyEntity> page);
    DocModifyApplyEntity docApplyList(Map<String, Object> params);
}
