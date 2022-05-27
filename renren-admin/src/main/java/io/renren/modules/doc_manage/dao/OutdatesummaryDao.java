package io.renren.modules.doc_manage.dao;

import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.entity.OutdatesummaryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-13 21:15:09
 */
@Mapper
public interface OutdatesummaryDao extends BaseMapper<OutdatesummaryEntity> {
    public List<OutdatesummaryEntity> queryList(Map<String, Object> map);
    public List<OutdatesummaryEntity> queryCommonList(Map<String, Object> map);

}
