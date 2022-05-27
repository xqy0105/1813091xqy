package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.FmsDocTypeEntity;
import io.renren.modules.sys.service.FmsDocTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案类型
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@RestController
@RequestMapping("sys/fmsdoctype")
public class FmsDocTypeController {
    @Autowired
    private FmsDocTypeService fmsDocTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdoctype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docTypeId}")
    @RequiresPermissions("sys:fmsdoctype:info")
    public R info(@PathVariable("docTypeId") Long docTypeId){
        FmsDocTypeEntity fmsDocType = fmsDocTypeService.getById(docTypeId);

        return R.ok().put("fmsDocType", fmsDocType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdoctype:save")
    public R save(@RequestBody FmsDocTypeEntity fmsDocType){
        fmsDocTypeService.save(fmsDocType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdoctype:update")
    public R update(@RequestBody FmsDocTypeEntity fmsDocType){
        ValidatorUtils.validateEntity(fmsDocType);
        fmsDocTypeService.updateById(fmsDocType);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdoctype:delete")
    public R delete(@RequestBody Long[] docTypeIds){
        fmsDocTypeService.removeByIds(Arrays.asList(docTypeIds));

        return R.ok();
    }

}
