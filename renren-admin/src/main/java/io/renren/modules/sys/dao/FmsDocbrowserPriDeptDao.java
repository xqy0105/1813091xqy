package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.FmsDocbrowserPriDeptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * 部门档案浏览权限表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@Mapper
public interface FmsDocbrowserPriDeptDao extends BaseMapper<FmsDocbrowserPriDeptEntity> {
    List<FmsDocbrowserPriDeptEntity> queryList(Map<String, Object> params);
    void delete(@Param("docId")Long docId);
}
