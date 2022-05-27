package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.FmsDocCatalogEntity;
import io.renren.modules.sys.service.FmsDocCatalogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案大类
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@RestController
@RequestMapping("sys/fmsdoccatalog")
public class FmsDocCatalogController {
    @Autowired
    private FmsDocCatalogService fmsDocCatalogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdoccatalog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocCatalogService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listByDeptId")
    public R listByDeptId(@RequestParam Long deptId){
        List<FmsDocCatalogEntity> catalogList = fmsDocCatalogService.queryByDeptId(deptId);
        return R.ok().put("catalogList", catalogList);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
    @RequiresPermissions("sys:fmsdoccatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        FmsDocCatalogEntity fmsDocCatalog = fmsDocCatalogService.getById(catalogId);

        return R.ok().put("fmsDocCatalog", fmsDocCatalog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdoccatalog:save")
    public R save(@RequestBody FmsDocCatalogEntity fmsDocCatalog){
        fmsDocCatalogService.save(fmsDocCatalog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdoccatalog:update")
    public R update(@RequestBody FmsDocCatalogEntity fmsDocCatalog){
        ValidatorUtils.validateEntity(fmsDocCatalog);
        fmsDocCatalogService.updateById(fmsDocCatalog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdoccatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
        fmsDocCatalogService.removeByIds(Arrays.asList(catalogIds));

        return R.ok();
    }

}
