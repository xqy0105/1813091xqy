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

import io.renren.modules.doc_manage.entity.DocConfimApplyEntity;
import io.renren.modules.doc_manage.service.DocConfimApplyService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-25 11:00:16
 */
@RestController
@RequestMapping("doc_manage/docconfimapply")
public class DocConfimApplyController {
    @Autowired
    private DocConfimApplyService docConfimApplyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("doc_manage:docconfimapply:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = docConfimApplyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
    @RequiresPermissions("doc_manage:docconfimapply:info")
    public R info(@PathVariable("docId") Long docId){
        DocConfimApplyEntity docConfimApply = docConfimApplyService.getById(docId);

        return R.ok().put("docConfimApply", docConfimApply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:docconfimapply:save")
    public R save(@RequestBody DocConfimApplyEntity docConfimApply){
        docConfimApplyService.save(docConfimApply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:docconfimapply:update")
    public R update(@RequestBody DocConfimApplyEntity docConfimApply){
        ValidatorUtils.validateEntity(docConfimApply);
        docConfimApplyService.updateById(docConfimApply);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:docconfimapply:delete")
    public R delete(@RequestBody Long[] docIds){
        docConfimApplyService.removeByIds(Arrays.asList(docIds));

        return R.ok();
    }
    /**
     * 处理确认申请
     */
    @RequestMapping("/approve_or_reject_confirm")
    public R approveConfirm(@RequestBody String str){
        Map<String, Object> params = (Map) JSON.parse(str);
        System.out.println(params.toString());
        docConfimApplyService.handleApply(params);
        return R.ok();

    }

}
