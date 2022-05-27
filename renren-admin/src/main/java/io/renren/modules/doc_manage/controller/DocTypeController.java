package io.renren.modules.doc_manage.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.renren.common.annotation.SysLog;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import io.renren.modules.doc_manage.service.DocCatalogService;
import io.renren.modules.sys.entity.FmsDocumentFileEntity;
import io.renren.modules.sys.service.FmsDocumentFileService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.doc_manage.entity.DocTypeEntity;
import io.renren.modules.doc_manage.service.DocTypeService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * 档案类型
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 21:03:22
 */
@RestController
@RequestMapping("doc_manage/doctype")
public class DocTypeController {
    @Autowired
    private DocTypeService docTypeService;
    @Autowired
    private FmsDocumentFileService fmsDocumentFileService;
//    @Autowired
//    private DocCatalogService docCatalogService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("doc_manage:doctype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = docTypeService.queryPage(params);

        return R.ok().put("page", page);
    }

    public boolean hasSameName(DocTypeEntity docType){
        HashMap<String,Object> params=new HashMap<>();
        params.put("deptId",docType.getDeptId());
        params.put("catalogId",docType.getCatalogId());
        params.put("docTypeName",docType.getDocTypeName());
        // params.put("delFlag",0);

        List result= docTypeService.queryList(params);
        if(result.size()>=1){
            return true;
        }
        else
            return false;
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{docTypeId}")
    @RequiresPermissions("doc_manage:doctype:info")
    public R info(@PathVariable("docTypeId") Long docTypeId){
        DocTypeEntity docType = docTypeService.getdocTypeById(docTypeId);

        return R.ok().put("docType", docType);
    }

    @RequestMapping("/select")
    //@RequiresPermissions("doc_manage:doctype:select")
    public R select(@RequestParam Map<String, Object> map){
        List<DocTypeEntity> doctypeList = docTypeService.queryList(map);

        return R.ok().put("doctypeList", doctypeList);
    }
    @RequestMapping("/selectByName")
    public R selectByName(@RequestParam Map<String, Object> map){
        List<String> doctypeList = docTypeService.selectDocTypeNameList(map);

        return R.ok().put("doctypeList", doctypeList);
    }
    @RequestMapping("/selectByCatalogId")
    //@RequiresPermissions("doc_manage:doctype:select")
    public R selectByCatalogId(@RequestParam Map<String, Object> map){
        List<DocTypeEntity> doctypeList = docTypeService.queryListByCatalogId(map);

        return R.ok().put("doctypeList", doctypeList);
    }

    /**
     * 保存
     */
    @SysLog("新增档案类型")
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:doctype:save")
    public R save(@RequestBody DocTypeEntity docType){
        if(hasSameName(docType)){
            return R.error("同一个档案大类已存在同名档案类型");
        }
        docTypeService.save(docType);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改档案类型")
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:doctype:update")
    public R update(@RequestBody DocTypeEntity docType){
        ValidatorUtils.validateEntity(docType);
        if(hasSameName(docType)){
            return R.error("同一个档案大类已存在同名档案类型");
        }
        if(docTypeService.getDocCountByDocTypeId(docType.getDocTypeId())>0){
            return R.error("更新失败，请查看当前档案类型是否" +
                    "存在的未删除的档案");
        }
        docTypeService.updateById(docType);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:doctype:delete")
    public R delete(@RequestBody Long[] docTypeIds){
        docTypeService.removeByIds(Arrays.asList(docTypeIds));

        return R.ok();
    }
    /**
     * 单条删除，加删除标记
     */
    @RequestMapping("/deleteSingle/{docTypeId}")
    // @RequiresPermissions("doc_manage:doccatalog:deleteSingle")
    public R delete(@PathVariable("docTypeId") Long docTypeId){
        docTypeService.removeById(docTypeId);
        if ( docTypeService.removeById(docTypeId)) return R.ok();
        else return R.error("删除失败，请查看当前档案大类是否有存在的档案类型");
    }
    /**
     * 单条删除，加删除标记
     */
    @RequestMapping("/canModify")
    // @RequiresPermissions("doc_manage:doccatalog:deleteSingle")
    public R canModify(@RequestParam Map<String, Object> params){
        int docnum=docTypeService.getDocCountByDocTypeId(Long.parseLong(params.get("docTypeId").toString()));
         return R.ok().put("canModify",docnum<=0);
       // else return R.error("删除失败，请查看当前档案大类是否有存在的档案类型");
    }
    @RequestMapping("/export")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException {
        params.put("limit","-1"); //不限制长度，全部导出
        PageUtils page = docTypeService.queryPage(params);


       // return R.ok().put("page", page);
       // DocTypeEntity
        //先查询到要导出的excel数据
        List<DocTypeEntity> userList = (List<DocTypeEntity>) page.getList();
        //数据库保存的图片名,并不是图片路径,需要获取路径凭借图片名

        ExportParams exportParams = new ExportParams();  //导出的excel相关配置
        exportParams.setTitle("档案类型");  //导出excel的第一行标题名字
        exportParams.setSheetName("档案类型sheet");  //导出的excel的sheet名称
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, DocTypeEntity.class, userList);
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("档案类型.xlsx", "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        //关闭流
        outputStream.close();
        workbook.close();
    }
/**
 *
 */
@RequestMapping("/getFileList")
// @RequiresPermissions("doc_manage:doccatalog:deleteSingle")
public R getfileList(@RequestParam Map<String, Object> params){

    List<FmsDocumentFileEntity> result= (List<FmsDocumentFileEntity>) fmsDocumentFileService.listByMap(params);

    return R.ok().put("page",result);
}
}
