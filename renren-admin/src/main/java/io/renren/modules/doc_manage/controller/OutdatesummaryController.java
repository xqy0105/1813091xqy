package io.renren.modules.doc_manage.controller;

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

import io.renren.modules.doc_manage.entity.OutdatesummaryEntity;
import io.renren.modules.doc_manage.service.OutdatesummaryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-13 21:15:09
 */
@RestController
@RequestMapping("doc_manage/outdatesummary")
public class OutdatesummaryController {
    @Autowired
    private OutdatesummaryService outdatesummaryService;

    /**
     * 列表
     */
    @RequestMapping("/summary")
    public R list(@RequestParam Map<String, Object> params){
        List<OutdatesummaryEntity> result= outdatesummaryService.queryList(params);

        return R.ok().put("page", result);
    }
    /**
     * 列表
     */
    @RequestMapping("/commonsummary")
    public R listcommon(@RequestParam Map<String, Object> params){
        List<OutdatesummaryEntity> result= outdatesummaryService.querycommonsummaryList(params);

        return R.ok().put("page", result);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{summaryname}")
    @RequiresPermissions("doc_manage:outdatesummary:info")
    public R info(@PathVariable("summaryname") String summaryname){
        OutdatesummaryEntity outdatesummary = outdatesummaryService.getById(summaryname);

        return R.ok().put("outdatesummary", outdatesummary);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:outdatesummary:save")
    public R save(@RequestBody OutdatesummaryEntity outdatesummary){
        outdatesummaryService.save(outdatesummary);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:outdatesummary:update")
    public R update(@RequestBody OutdatesummaryEntity outdatesummary){
        ValidatorUtils.validateEntity(outdatesummary);
        outdatesummaryService.updateById(outdatesummary);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:outdatesummary:delete")
    public R delete(@RequestBody String[] summarynames){
        outdatesummaryService.removeByIds(Arrays.asList(summarynames));

        return R.ok();
    }

}
