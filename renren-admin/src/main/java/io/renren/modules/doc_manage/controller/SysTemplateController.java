package io.renren.modules.doc_manage.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.doc_manage.entity.SysTemplateEntity;
import io.renren.modules.doc_manage.service.SysTemplateService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 模板表
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2022-01-22 10:34:13
 */
@RestController
@RequestMapping("doc_manage/systemplate")
public class SysTemplateController {
    @Autowired
    private SysTemplateService sysTemplateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("doc_manage:systemplate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysTemplateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("doc_manage:systemplate:info")
    public R info(@PathVariable("id") Long id){
        SysTemplateEntity sysTemplate = sysTemplateService.getById(id);

        return R.ok().put("sysTemplate", sysTemplate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:systemplate:save")
    public R save(@RequestBody SysTemplateEntity sysTemplate){
        sysTemplate.setCreateTime(new Date());
        sysTemplate.setCreateUserId(ShiroUtils.getUserEntity().getUserId());
        sysTemplate.setCreateUserName(ShiroUtils.getUserEntity().getUsername());
        sysTemplateService.save(sysTemplate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:systemplate:update")
    public R update(@RequestBody SysTemplateEntity sysTemplate){
        ValidatorUtils.validateEntity(sysTemplate);
        sysTemplateService.updateById(sysTemplate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:systemplate:delete")
    public R delete(@RequestBody Long[] ids){
        sysTemplateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
