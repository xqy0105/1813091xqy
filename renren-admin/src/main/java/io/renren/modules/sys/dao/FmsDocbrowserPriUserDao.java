package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.FmsDocbrowserPriDeptEntity;
import io.renren.modules.sys.entity.FmsDocbrowserPriUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户档案浏览权限表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@Mapper
public interface FmsDocbrowserPriUserDao extends BaseMapper<FmsDocbrowserPriUserEntity> {
    List<FmsDocbrowserPriUserEntity> queryList(Map<String, Object> params);
    void delete(@Param("docId")Long docId);
	
}
