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

import io.renren.modules.sys.entity.FmsDocModifyLogEntity;
import io.renren.modules.sys.service.FmsDocModifyLogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案修改日志
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@RestController
@RequestMapping("sys/fmsdocmodifylog")
public class FmsDocModifyLogController {
    @Autowired
    private FmsDocModifyLogService fmsDocModifyLogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocmodifylog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocModifyLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
    @RequiresPermissions("sys:fmsdocmodifylog:info")
    public R info(@PathVariable("docId") Long docId){
        FmsDocModifyLogEntity fmsDocModifyLog = fmsDocModifyLogService.getById(docId);

        return R.ok().put("fmsDocModifyLog", fmsDocModifyLog);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocmodifylog:save")
    public R save(@RequestBody FmsDocModifyLogEntity fmsDocModifyLog){
        fmsDocModifyLogService.save(fmsDocModifyLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocmodifylog:update")
    public R update(@RequestBody FmsDocModifyLogEntity fmsDocModifyLog){
        ValidatorUtils.validateEntity(fmsDocModifyLog);
        fmsDocModifyLogService.updateById(fmsDocModifyLog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocmodifylog:delete")
    public R delete(@RequestBody Long[] docIds){
        fmsDocModifyLogService.removeByIds(Arrays.asList(docIds));

        return R.ok();
    }

}
