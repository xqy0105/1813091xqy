package io.renren.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.common.utils.R;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.sys.entity.FmsDocBorrowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * 档案借阅
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@Mapper
public interface FmsDocBorrowDao extends BaseMapper<FmsDocBorrowEntity> {
	void safeSave(Map<String, Object> params);
	void returnById(Map<String, Object> params);
	IPage<FmsDocBorrowEntity> docBorrowList(IPage<FmsDocBorrowEntity> page);
	int isAdmin(@Param("userId")Long userid);
}
