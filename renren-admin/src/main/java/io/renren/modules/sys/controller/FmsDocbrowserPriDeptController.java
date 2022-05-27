package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
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

import io.renren.modules.sys.entity.FmsDocbrowserPriDeptEntity;
import io.renren.modules.sys.service.FmsDocbrowserPriDeptService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 部门档案浏览权限表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@RestController
@RequestMapping("sys/fmsdocbrowserpridept")
public class FmsDocbrowserPriDeptController {
    @Autowired
    private FmsDocbrowserPriDeptService fmsDocbrowserPriDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocbrowserpridept:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocbrowserPriDeptService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:fmsdocbrowserpridept:info")
    public R info(@PathVariable("deptId") Long deptId){
        FmsDocbrowserPriDeptEntity fmsDocbrowserPriDept = fmsDocbrowserPriDeptService.getById(deptId);

        return R.ok().put("fmsDocbrowserPriDept", fmsDocbrowserPriDept);
    }
    /**
     * 选择列表
     */
     @RequestMapping("/select")
     @RequiresPermissions("sys:fmsdocbrowserpridept:select")
     public R select(){
         Map<String,Object> params=new HashMap<>();
         List<FmsDocbrowserPriDeptEntity> priDeptList =fmsDocbrowserPriDeptService.queryList(params);
         System.out.println(priDeptList);
         return R.ok().put("priDeptList",priDeptList);
     }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocbrowserpridept:save")
    public R save(@RequestBody FmsDocbrowserPriDeptEntity fmsDocbrowserPriDept){
        fmsDocbrowserPriDeptService.save(fmsDocbrowserPriDept);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocbrowserpridept:update")
    public R update(@RequestBody FmsDocbrowserPriDeptEntity fmsDocbrowserPriDept){
        ValidatorUtils.validateEntity(fmsDocbrowserPriDept);
        fmsDocbrowserPriDeptService.updateById(fmsDocbrowserPriDept);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocbrowserpridept:delete")
    public R delete(@RequestBody Long[] deptIds){
        fmsDocbrowserPriDeptService.removeByIds(Arrays.asList(deptIds));

        return R.ok();
    }

}
