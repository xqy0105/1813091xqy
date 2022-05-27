package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.sys.entity.FmsDocBorrowEntity;
import io.renren.modules.sys.service.FmsDocBorrowService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案借阅
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@RestController
@RequestMapping("sys/fmsdocborrow")
public class FmsDocBorrowController {
    @Autowired
    private FmsDocBorrowService fmsDocBorrowService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocborrow:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocBorrowService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{approverId}")
    @RequiresPermissions("sys:fmsdocborrow:info")
    public R info(@PathVariable("approverId") Long approverId){
        FmsDocBorrowEntity fmsDocBorrow = fmsDocBorrowService.getById(approverId);

        return R.ok().put("fmsDocBorrow", fmsDocBorrow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocborrow:save")
    public R save(@RequestBody FmsDocBorrowEntity fmsDocBorrow){

//        fmsDocBorrowService.save(fmsDocBorrow);
//        System.out.println("Input Information: "+fmsDocBorrow.getDocId() + fmsDocBorrow.getBorrowerId()+ fmsDocBorrow.getApproverId());

        if (fmsDocBorrowService.safeSave(fmsDocBorrow)) return R.ok();
        else return R.error("Not Enough Remain");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocborrow:update")
    public R update(@RequestBody FmsDocBorrowEntity fmsDocBorrow){
        ValidatorUtils.validateEntity(fmsDocBorrow);
        fmsDocBorrowService.updateById(fmsDocBorrow);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocborrow:delete")
    public R delete(@RequestBody Long[] approverIds){
        fmsDocBorrowService.removeByIds(Arrays.asList(approverIds));

        return R.ok();
    }

    @RequestMapping(value = "/return")
    @ResponseBody()
    public R returnBorrow(@RequestBody String str){
        Map<String, Object> params = (Map) JSON.parse(str);
        System.out.println("Return params: "+params.toString());
        fmsDocBorrowService.returnById(params);
       return R.ok();
    }

}
