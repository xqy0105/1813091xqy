package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.DocTypeEntity;
import io.renren.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 档案类型
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 21:03:22
 */
public interface DocTypeService extends IService<DocTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    public boolean removeById(Long docTypeId);
    public boolean updateBySingleId(DocTypeEntity docTypeEntity);
    public int getDocCountByDocTypeId(Long docTypeId);
    public DocTypeEntity getdocTypeById(Long docTypeId);
    public DocTypeEntity getdocType(Map<String, Object> map);
    List<DocTypeEntity> queryList(Map<String, Object> map);
    List<DocTypeEntity> queryListByCatalogId(Map<String, Object> map);
    List<String> selectDocTypeNameList (Map<String, Object> params);
}

