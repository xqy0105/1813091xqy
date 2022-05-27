package io.renren.modules.doc_manage.controller;

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

import io.renren.modules.doc_manage.entity.VDocumentEntity;
import io.renren.modules.doc_manage.service.VDocumentService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * VIEW
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
@RestController
@RequestMapping("doc_manage/vdocument")
public class VDocumentController {
    @Autowired
    private VDocumentService vDocumentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = vDocumentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
    @RequiresPermissions("doc_manage:vdocument:info")
    public R info(@PathVariable("docId") Long docId){
        VDocumentEntity vDocument = vDocumentService.getById(docId);

        return R.ok().put("vDocument", vDocument);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:vdocument:save")
    public R save(@RequestBody VDocumentEntity vDocument){
        vDocumentService.save(vDocument);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:vdocument:update")
    public R update(@RequestBody VDocumentEntity vDocument){
        ValidatorUtils.validateEntity(vDocument);
        vDocumentService.updateById(vDocument);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:vdocument:delete")
    public R delete(@RequestBody Long[] docIds){
        vDocumentService.removeByIds(Arrays.asList(docIds));

        return R.ok();
    }

}
