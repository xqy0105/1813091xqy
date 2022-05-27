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

import io.renren.modules.sys.entity.FmsDocbrowserPriUserEntity;
import io.renren.modules.sys.service.FmsDocbrowserPriUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户档案浏览权限表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@RestController
@RequestMapping("sys/fmsdocbrowserpriuser")
public class FmsDocbrowserPriUserController {
    @Autowired
    private FmsDocbrowserPriUserService fmsDocbrowserPriUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocbrowserpriuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocbrowserPriUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:fmsdocbrowserpriuser:info")
    public R info(@PathVariable("userId") Long userId){
        FmsDocbrowserPriUserEntity fmsDocbrowserPriUser = fmsDocbrowserPriUserService.getById(userId);

        return R.ok().put("fmsDocbrowserPriUser", fmsDocbrowserPriUser);
    }
    /**
     * 选择列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:fmsdocbrowserpriUser:select")
    public R select(){
        Map<String,Object> params=new HashMap<>();
        List<FmsDocbrowserPriUserEntity> priDeptList =fmsDocbrowserPriUserService.queryList(params);
        return R.ok().put("priDeptList", priDeptList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocbrowserpriuser:save")
    public R save(@RequestBody FmsDocbrowserPriUserEntity fmsDocbrowserPriUser){
        fmsDocbrowserPriUserService.save(fmsDocbrowserPriUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocbrowserpriuser:update")
    public R update(@RequestBody FmsDocbrowserPriUserEntity fmsDocbrowserPriUser){
        ValidatorUtils.validateEntity(fmsDocbrowserPriUser);
        fmsDocbrowserPriUserService.updateById(fmsDocbrowserPriUser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocbrowserpriuser:delete")
    public R delete(@RequestBody Long[] userIds){
        fmsDocbrowserPriUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
