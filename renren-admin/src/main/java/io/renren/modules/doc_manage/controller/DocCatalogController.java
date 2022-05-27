package io.renren.modules.doc_manage.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.renren.common.utils.Constant;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案大类
 *
 * @author liumingming
 * @email liumingming@nankai.edu.cm
 * @date 2021-08-07 10:20:20
 */
@RestController
@RequestMapping("doc_manage/doccatalog")
public class DocCatalogController {
    @Autowired
    private DocCatalogService docCatalogService;
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("doc_manage:doccatalog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = docCatalogService.queryPage(params);

        return R.ok().put("page", page);
    }
    @RequestMapping("/select")
    //@RequiresPermissions("doc_manage:doccatalog:select")
    public R select(@RequestParam Map<String, Object> params){
        System.out.println("select catalog params control:"+params.toString());
        List<DocCatalogEntity> catalogList = docCatalogService.queryList(params);
        return R.ok().put("catalogList", catalogList);
    }
    @RequestMapping("/selectByName")
    public R selectByName(@RequestParam Map<String, Object> map){
        List<String> catalogList = docCatalogService.selectDocCatalogNameList(map);

        return R.ok().put("catalogList", catalogList);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{catalogId}")
    @RequiresPermissions("doc_manage:doccatalog:info")
    public R info(@PathVariable("catalogId") Long catalogId){
        DocCatalogEntity docCatalog = docCatalogService.getById(catalogId);

        return R.ok().put("docCatalog", docCatalog);
    }

    public boolean hasSameName(DocCatalogEntity docCatalog){
        HashMap<String,Object> params=new HashMap<>();
        params.put("deptId",docCatalog.getDeptId());
        params.put("catalogName",docCatalog.getCatalogName());
        // params.put("delFlag",0);

        List result= docCatalogService.queryList(params);
        if(result.size()>=1){
            return true;
        }
        else
            return false;
    }
    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:doccatalog:save")
    public R save(@RequestBody DocCatalogEntity docCatalog){



       if(hasSameName(docCatalog)){
           return R.error("已存在同名档案大类");
       }
        docCatalogService.save(docCatalog);

        return R.ok();
    }
    /**
     * 保存
     */
    @RequestMapping("/batchadd")
    @RequiresPermissions("doc_manage:doccatalog:batchadd")
    public R batchAdd(@RequestBody DocCatalogEntity docCatalog){


      StringBuilder sbn=new StringBuilder();
        StringBuilder sbs=new StringBuilder();

        String depts[]=docCatalog.getDeptsString();
        for(int i=0;i<depts.length;i++){
            DocCatalogEntity temp=new DocCatalogEntity();
            temp.setDeptId(Long.parseLong(depts[i]));
            temp.setCatalogName(docCatalog.getCatalogName());
            SysDeptEntity sysDept=sysDeptService.getById(temp.getDeptId());
            if(hasSameName(temp)){

                if (sbn.length()>0){
                    sbn.append("、");
                }
                    sbn.append(sysDept.getName());

            }
            else{
                if (sbs.length()>0){
                    sbs.append("、");
                }
                sbs.append(sysDept.getName());
            }
            docCatalogService.save(temp);
        }
        if(sbn.length()>0){
            sbn.append("已存在同名档案大类。");
            if(sbs.length()>0)
            sbn.append(sbs.append("成功添加档案大类。"));
            return R.error(sbn.toString());}
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:doccatalog:update")
    public R update(@RequestBody DocCatalogEntity docCatalog){
        ValidatorUtils.validateEntity(docCatalog);
        if(hasSameName(docCatalog)){
            return R.error("已存在同名档案大类");
        }
        docCatalogService.updateById(docCatalog);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:doccatalog:delete")
    public R delete(@RequestBody Long[] catalogIds){
        docCatalogService.removeByIds(Arrays.asList(catalogIds));

        return R.ok();
    }
    /**
     * 单条删除，加删除标记
     */
    @RequestMapping("/deleteSingle/{catalogId}")
   // @RequiresPermissions("doc_manage:doccatalog:deleteSingle")
    public R delete(@PathVariable("catalogId") Long catalogId){
        docCatalogService.removeById(catalogId);

        if ( docCatalogService.removeById(catalogId)) return R.ok();
        else return R.error("删除失败，请查看当前档案大类是否有存在的档案类型");
    }
}
