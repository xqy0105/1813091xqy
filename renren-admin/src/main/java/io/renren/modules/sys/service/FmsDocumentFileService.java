package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.dao.FmsDocumentFileDao;
import io.renren.modules.sys.entity.FmsDocumentFileEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 档案电子文件
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
public interface FmsDocumentFileService extends IService<FmsDocumentFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
    List<FmsDocumentFileEntity> queryList(Map<String, Object> map);
    FmsDocumentFileDao queryById(Long docfileId);

    void deldocfile(Long docfileId,Long userId);

    boolean canView(@Param("userId")Long userId,@Param("docId")Long docId);

}

