package io.renren.modules.doc_manage.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.doc_manage.service.DocBorrowApplyService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



/**
 * 档案借阅申请
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 16:16:53
 */
@RestController
@RequestMapping("doc_manage/docborrowapply")
public class DocBorrowApplyController {
    @Autowired
    private DocBorrowApplyService docBorrowApplyService;

    /**
     * overtimetype更新查询条件语句
     * @param params
     */
    void buildparams(Map<String, Object> params) throws UnsupportedEncodingException {
        String condition= URLDecoder.decode((String)params.get("overtimetype"),"UTF8");
        if(condition!=null&&!"".equals(condition)){
            String[] conditions =condition.split(":");
            if (conditions.length==2){
                params.remove("overtimetype");
                params.put("condition",conditions[1]);
            }
        }
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("doc_manage:docborrowapply:list")
    public R list(@RequestParam Map<String, Object> params) throws UnsupportedEncodingException {
        buildparams(params);
        PageUtils page = docBorrowApplyService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/borrowlist")
    public R borrowlist(@RequestParam Map<String, Object> params) throws UnsupportedEncodingException {
        if(params==null){
            params=new HashMap<>();
        }
        SysUserEntity user = ShiroUtils.getUserEntity();
        params.put("userId",user.getUserId());
        buildparams(params);
        PageUtils page = docBorrowApplyService.queryBorrwlistPage(params);


        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docBorrowApplyId}")
    @RequiresPermissions("doc_manage:docborrowapply:info")
    public R info(@PathVariable("docBorrowApplyId") Integer docBorrowApplyId){
        DocBorrowApplyEntity docBorrowApply = docBorrowApplyService.getById(docBorrowApplyId);

        return R.ok().put("docBorrowApply", docBorrowApply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:docborrowapply:save")
    public R save(@RequestBody DocBorrowApplyEntity docBorrowApply){
        docBorrowApplyService.save(docBorrowApply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:docborrowapply:update")
    public R update(@RequestBody DocBorrowApplyEntity docBorrowApply){
        ValidatorUtils.validateEntity(docBorrowApply);
        docBorrowApplyService.updateById(docBorrowApply);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:docborrowapply:delete")
    public R delete(@RequestBody Integer[] docBorrowApplyIds){
        docBorrowApplyService.removeByIds(Arrays.asList(docBorrowApplyIds));

        return R.ok();
    }

    /**
     * 申请借阅
     */
    @RequestMapping("/apply")
    public R apply(@RequestBody DocBorrowApplyEntity docBorrowApplyEntity){
        System.out.println("params from front page: "+docBorrowApplyEntity.toString());
        if(docBorrowApplyService.apply(docBorrowApplyEntity)) return R.ok();
        else return R.error("数量不足或者其他问题,请联系管理员");
    }
    /**
     * 通过借阅审核
     */
    @RequestMapping("approve_or_reject_borrow")
    public R approveBorrow(@RequestBody String str){
        Map<String, Object> params = (Map)JSON.parse(str);
        System.out.println(params.toString());
        if(docBorrowApplyService.handleApply(params)) return R.ok();
        else return R.error("数量不足或者其他问题,请联系管理员");
    }

}


