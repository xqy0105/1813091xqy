package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.VDocInfoEntity;
import io.renren.modules.doc_manage.entity.VDocumentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * VIEW
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
@Mapper
public interface VDocumentDao extends BaseMapper<VDocumentEntity> {

    IPage<VDocumentEntity> selectDocInfoPage(IPage<VDocumentEntity> page);
}
