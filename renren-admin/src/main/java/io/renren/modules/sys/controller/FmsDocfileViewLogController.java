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

import io.renren.modules.sys.entity.FmsDocfileViewLogEntity;
import io.renren.modules.sys.service.FmsDocfileViewLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 文件浏览日志
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@RestController
@RequestMapping("sys/fmsdocfileviewlog")
public class FmsDocfileViewLogController {
    @Autowired
    private FmsDocfileViewLogService fmsDocfileViewLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocfileviewlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocfileViewLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docfileId}")
    @RequiresPermissions("sys:fmsdocfileviewlog:info")
    public R info(@PathVariable("docfileId") Long docfileId){
        FmsDocfileViewLogEntity fmsDocfileViewLog = fmsDocfileViewLogService.getById(docfileId);

        return R.ok().put("fmsDocfileViewLog", fmsDocfileViewLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocfileviewlog:save")
    public R save(@RequestBody FmsDocfileViewLogEntity fmsDocfileViewLog){
        fmsDocfileViewLogService.save(fmsDocfileViewLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocfileviewlog:update")
    public R update(@RequestBody FmsDocfileViewLogEntity fmsDocfileViewLog){
        ValidatorUtils.validateEntity(fmsDocfileViewLog);
        fmsDocfileViewLogService.updateById(fmsDocfileViewLog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocfileviewlog:delete")
    public R delete(@RequestBody Long[] docfileIds){
        fmsDocfileViewLogService.removeByIds(Arrays.asList(docfileIds));

        return R.ok();
    }

}
