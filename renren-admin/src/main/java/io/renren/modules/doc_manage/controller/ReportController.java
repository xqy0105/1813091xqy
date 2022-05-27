package io.renren.modules.doc_manage.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.doc_manage.entity.SysTemplateEntity;
import io.renren.modules.doc_manage.service.SysTemplateService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 模板表
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2022-01-22 10:34:13
 */
@RestController
@RequestMapping("doc_manage/report")
public class ReportController {
    @Autowired
    private SysTemplateService sysTemplateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        List<SysTemplateEntity> list = sysTemplateService.getList(params);

        return R.ok().put("data", list);
    }

    /**
     * 生成
     */
    @RequestMapping("/create")
    public R create(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.create(params);

        return R.ok().put("data", map);
    }

    /**
     * 列表
     */
    @RequestMapping("/yearCreate")
    public R yearCreate(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.yearCreate(params);

        return R.ok().put("data", map);
    }

    /**
     * 列表
     */
    @RequestMapping("/yearList")
    public R yearList(@RequestParam Map<String, Object> params){
        PageUtils page = sysTemplateService.queryYearPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/exportYearList")
    public R exportYearList(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response){

        sysTemplateService.exportYearList(params,request,response);

        return R.ok();
    }


    /**
     * （1）近五年档案总数变化
     */
    @RequestMapping("/echartHandler")
    public R echartHandler(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.echartHandler(params);

        return R.ok().put("data", map);
    }

    /**
     * （2）各个部门档案数的饼状图
     */
    @RequestMapping("/echartHandler1")
    public R echartHandler1(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.echartHandler1(params);

        return R.ok().put("data", map);
    }

    /**
     * 借阅归还数
     */
    @RequestMapping("/echartHandler2")
    public R echartHandler2(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.echartHandler2(params);

        return R.ok().put("data", map);
    }

    /**
     * 转出转入数
     */
    @RequestMapping("/echartHandler3")
    public R echartHandler3(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.echartHandler3(params);

        return R.ok().put("data", map);
    }

    /**
     * 交付数
     */
    @RequestMapping("/echartHandler4")
    public R echartHandler4(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.echartHandler4(params);

        return R.ok().put("data", map);
    }

    /**
     * 修改数
     */
    @RequestMapping("/echartHandler5")
    public R echartHandler5(@RequestParam Map<String, Object> params){
        Map<String, Object> map = sysTemplateService.echartHandler5(params);

        return R.ok().put("data", map);
    }

}
