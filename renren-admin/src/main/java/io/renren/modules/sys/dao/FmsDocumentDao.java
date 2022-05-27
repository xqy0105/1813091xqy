package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.FmsDocumentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 档案
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:15
 */
@Mapper
public interface FmsDocumentDao extends BaseMapper<FmsDocumentEntity> {
    public List<FmsDocumentEntity> queryList(Map<String, Object> map);
    void delete(Long docId,Long userId);

}
