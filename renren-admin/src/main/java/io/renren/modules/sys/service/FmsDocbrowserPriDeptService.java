package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.FmsDocbrowserPriDeptEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门档案浏览权限表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
public interface FmsDocbrowserPriDeptService extends IService<FmsDocbrowserPriDeptEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<FmsDocbrowserPriDeptEntity> queryList(Map<String, Object> params);
    public void deletepridept(@Param("docId")Long docId);
}

