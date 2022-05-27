package io.renren.modules.doc_manage.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.doc_manage.entity.DocModifyApplyEntity;
import io.renren.modules.doc_manage.service.DocModifyApplyService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-11-20 09:43:28
 */
@RestController
@RequestMapping("doc_manage/docmodifyapply")
public class DocModifyApplyController {
    @Autowired
    private DocModifyApplyService docModifyApplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = docModifyApplyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docModifyApplyId}")
    @RequiresPermissions("doc_manage:docmodifyapply:info")
    public R info(@PathVariable("docModifyApplyId") Long docModifyApplyId){
        DocModifyApplyEntity docModifyApply = docModifyApplyService.getById(docModifyApplyId);

        return R.ok().put("docModifyApply", docModifyApply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:docmodifyapply:save")
    public R save(@RequestBody DocModifyApplyEntity docModifyApply){
        docModifyApplyService.save(docModifyApply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:docmodifyapply:update")
    public R update(@RequestBody DocModifyApplyEntity docModifyApply){
        ValidatorUtils.validateEntity(docModifyApply);
        docModifyApplyService.updateById(docModifyApply);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:docmodifyapply:delete")
    public R delete(@RequestBody Long[] docModifyApplyIds){
        docModifyApplyService.removeByIds(Arrays.asList(docModifyApplyIds));

        return R.ok();
    }
    /**
     * 处理修改申请
     */
    @RequestMapping("/approve_or_reject_modify")
    public R approveModify(@RequestBody String str){
        Map<String, Object> params = (Map) JSON.parse(str);
        System.out.println(params.toString());
        docModifyApplyService.handleApply(params);
        return R.ok();

    }


}
