package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.docCheckUtil;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.FmsDocumentFileEntity;
import io.renren.modules.sys.service.FmsDocumentFileService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案电子文件
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@RestController
@RequestMapping("sys/fmsdocumentfile")
public class FmsDocumentFileController {
    @Autowired
    private FmsDocumentFileService fmsDocumentFileService;
    @Autowired
    private DocCatalogService docCatalogService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocumentfile:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fmsDocumentFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docfileId}")
    @RequiresPermissions("sys:fmsdocumentfile:info")
    public R info(@PathVariable("docfileId") Long docfileId){
        FmsDocumentFileEntity fmsDocumentFile = fmsDocumentFileService.getById(docfileId);

        return R.ok().put("fmsDocumentFile", fmsDocumentFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocumentfile:save")
    public R save(@RequestBody FmsDocumentFileEntity fmsDocumentFile){
        fmsDocumentFileService.save(fmsDocumentFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocumentfile:update")
    public R update(@RequestBody FmsDocumentFileEntity fmsDocumentFile){
        ValidatorUtils.validateEntity(fmsDocumentFile);
        fmsDocumentFileService.updateById(fmsDocumentFile);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocumentfile:delete")
    public R delete(@RequestBody Long[] docfileIds){
        fmsDocumentFileService.removeByIds(Arrays.asList(docfileIds));

        return R.ok();
    }
    @RequestMapping("/getFileList")
   // @RequiresPermissions("sys:fmsdocumentfile:getFileList")
    public R getfileList(@RequestParam Map<String, Object> params){

        List<FmsDocumentFileEntity> result= (List<FmsDocumentFileEntity>) fmsDocumentFileService.queryList(params);
        SysUserEntity user = ShiroUtils.getUserEntity();
        boolean canView=false;
        if(!docCatalogService.isAdmin(user)) {
         canView=fmsDocumentFileService.canView(user.getUserId(), Long.parseLong(params.get("docId").toString()));}
        else
            canView=true;//系统管理员可以看所有文件
        HashMap<String,Object> re=new HashMap();
        re.put("page",result);
        re.put("canView",canView);

         return R.ok(re);
    }

}
