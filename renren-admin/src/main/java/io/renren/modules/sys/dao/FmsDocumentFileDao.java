package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.FmsDocumentFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 档案电子文件
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@Mapper
public interface FmsDocumentFileDao extends BaseMapper<FmsDocumentFileEntity> {
    List<FmsDocumentFileEntity> queryList(Map<String, Object> params);
    FmsDocumentFileDao queryById(Long docfileId);
    void delete(Long docfileId,Long userId);
    int canView(@Param("userId")Long userId, @Param("docId")Long docId);


}
