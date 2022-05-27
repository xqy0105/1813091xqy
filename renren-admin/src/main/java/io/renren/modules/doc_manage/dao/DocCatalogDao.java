package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 档案大类
 * 
 * @author liumingming
 * @email liumingming@nankai.edu.cm
 * @date 2021-08-07 10:20:20
 */
@Mapper
public interface DocCatalogDao extends BaseMapper<DocCatalogEntity> {
    /**
     * 查询具有传入参数的查询结果
     * @return
     */
	IPage<DocCatalogEntity> selectCatalogList(IPage<DocCatalogEntity> page);

	int isAdmin(@Param("userId")Long userid);
	List<Long> deptList(@Param("userId")Long userid);
	void delByCatalogId(Map<String,Object> params);
	List<DocCatalogEntity> queryList(Map<String, Object> params);
	List<String> selectDocCatalogNameList (Map<String, Object> params);
}
