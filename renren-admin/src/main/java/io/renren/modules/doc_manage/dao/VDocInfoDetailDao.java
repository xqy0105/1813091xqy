package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.entity.VDocInfoDetailEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.doc_manage.entity.VDocInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * VIEW
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
@Mapper
public interface VDocInfoDetailDao extends BaseMapper<VDocInfoDetailEntity> {
    IPage<VDocInfoDetailEntity> selectDocInfoDetail(IPage<VDocInfoDetailEntity> page);
    List<VDocInfoEntity> selectDocInfo(Map<String,Object> params);
    IPage<VDocInfoEntity> selectDocInfoPage(IPage<VDocInfoEntity> page);
    VDocInfoEntity selectDocInfoDetailById(@Param("docId")Long docId);
}
