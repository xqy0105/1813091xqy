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

import io.renren.modules.sys.entity.FmsDocTransLogEntity;
import io.renren.modules.sys.service.FmsDocTransLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案流转日志
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@RestController
@RequestMapping("sys/fmsdoctranslog")
public class FmsDocTransLogController {
    @Autowired
    private FmsDocTransLogService fmsDocTransLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdoctranslog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocTransLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
    @RequiresPermissions("sys:fmsdoctranslog:info")
    public R info(@PathVariable("docId") Long docId){
        FmsDocTransLogEntity fmsDocTransLog = fmsDocTransLogService.getById(docId);

        return R.ok().put("fmsDocTransLog", fmsDocTransLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdoctranslog:save")
    public R save(@RequestBody FmsDocTransLogEntity fmsDocTransLog){
        fmsDocTransLogService.save(fmsDocTransLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdoctranslog:update")
    public R update(@RequestBody FmsDocTransLogEntity fmsDocTransLog){
        ValidatorUtils.validateEntity(fmsDocTransLog);
        fmsDocTransLogService.updateById(fmsDocTransLog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdoctranslog:delete")
    public R delete(@RequestBody Long[] docIds){
        fmsDocTransLogService.removeByIds(Arrays.asList(docIds));

        return R.ok();
    }

}
