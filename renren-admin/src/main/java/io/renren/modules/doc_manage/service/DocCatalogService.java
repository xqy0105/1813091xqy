package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 档案大类
 *
 * @author liumingming
 * @email liumingming@nankai.edu.cm
 * @date 2021-08-07 10:20:20
 */
public interface DocCatalogService extends IService<DocCatalogEntity> {
    public boolean removeById(Long catalogId) ;
    PageUtils queryPage(Map<String, Object> params);
    public boolean isAdmin(SysUserEntity user);
    public boolean isAdmin();
    public List<DocCatalogEntity> queryList(Map<String, Object> map);
    List<String> selectDocCatalogNameList (Map<String, Object> params);
}

