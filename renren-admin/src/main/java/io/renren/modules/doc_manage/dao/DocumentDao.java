package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.DocumentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 档案
 *
 */
@Mapper
public interface DocumentDao extends BaseMapper<DocumentEntity> {
	IPage<DocumentEntity> search(IPage<DocumentEntity> page);
	IPage<DocumentEntity> searchTransable(IPage<DocumentEntity> page);
	IPage<DocumentEntity> searchTransIn(IPage<DocumentEntity> page);
	void transOut(Map<String, Object> params);
	void transReject(Map<String, Object> params);
	void transIn(Map<String, Object> params);
	void transInUpdateOwner(Map<String, Object> params);
	IPage<DocumentEntity> docTimelist(IPage<DocumentEntity> page);
}
