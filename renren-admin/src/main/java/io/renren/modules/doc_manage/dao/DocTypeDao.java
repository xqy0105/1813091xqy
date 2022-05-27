package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.entity.DocTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 档案类型
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 21:03:22
 */
@Mapper
public interface DocTypeDao extends BaseMapper<DocTypeEntity> {
    IPage<DocTypeEntity> selectDocTypeList(IPage<DocTypeEntity> page);
    void delByDocTypeId(Map<String,Object> params);
    int selectCountByDocTypeID(@Param("docTypeId")Long docTypeID);
    List<DocTypeEntity> queryList(Map<String, Object> params);

    List<DocTypeEntity> selectDocType(Map<String, Object> params);
    List<String> selectDocTypeNameList (Map<String, Object> params);

}
