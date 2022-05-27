package io.renren.modules.doc_manage.dao;

import io.renren.modules.doc_manage.entity.SysTemplateEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 模板表
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2022-01-22 10:34:13
 */
@Mapper
public interface SysTemplateDao extends BaseMapper<SysTemplateEntity> {

    Integer getCustomReport(@Param("startTime") String startTime, @Param("endTime")String endTime, @Param("roleId")String roleId);

    List<Map<String, Object>> yearCreate(@Param("month")String month);

    List<Map<String, Object>> queryYearPage(@Param("year")String year, @Param("startSize")Integer startSize,@Param("limit") Integer limit);

    Integer queryYearPageCount(@Param("year")String year);

    Integer getDocumentNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    Integer getBorrowApplyNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    Integer getBorrowNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    Integer getTransableNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    Integer getTransInNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    Integer getConfimApplyNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    Integer getModifyApplyNum(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("sysTemplateEntity")SysTemplateEntity sysTemplateEntity);

    int echartHandler(@Param("year") String year);

    List<Map<String, Object>> echartHandler1(@Param("year")Integer year);

    int getBorrowApplyNumNew(@Param("month")String month);

    int getBorrowNumNew(@Param("month")String month);

    int getTransableNumNew(@Param("month")String month);

    int getTransInNumNew(@Param("month")String month);

    int getConfimApplyNumNew(@Param("month")String month);

    int getModifyApplyNumNew(@Param("month")String month);

    List<Map<String, Object>> exportYearList(@Param("year")String year);
}
