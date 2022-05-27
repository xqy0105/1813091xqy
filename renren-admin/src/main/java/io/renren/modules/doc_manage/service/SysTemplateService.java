package io.renren.modules.doc_manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.doc_manage.entity.SysTemplateEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 模板表
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2022-01-22 10:34:13
 */
public interface SysTemplateService extends IService<SysTemplateEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SysTemplateEntity> getList(Map<String, Object> params);

    Map<String, Object> create(Map<String, Object> params);

    Map<String, Object> yearCreate(Map<String, Object> params);

    PageUtils queryYearPage(Map<String, Object> params);

    Map<String, Object> echartHandler(Map<String, Object> params);

    Map<String, Object> echartHandler1(Map<String, Object> params);

    Map<String, Object> echartHandler2(Map<String, Object> params);

    Map<String, Object> echartHandler3(Map<String, Object> params);

    Map<String, Object> echartHandler4(Map<String, Object> params);

    Map<String, Object> echartHandler5(Map<String, Object> params);

    void exportYearList(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response);
}

