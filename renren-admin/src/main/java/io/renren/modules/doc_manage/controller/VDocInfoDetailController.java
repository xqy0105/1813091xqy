package io.renren.modules.doc_manage.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.doc_manage.entity.DocTypeEntity;
import io.renren.modules.doc_manage.entity.VDocInfoEntity;
import io.renren.modules.doc_manage.util.SomeTools;
import io.renren.modules.sys.entity.FmsDocbrowserPriDeptEntity;
import io.renren.modules.sys.entity.FmsDocbrowserPriUserEntity;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.FmsDocbrowserPriDeptService;
import io.renren.modules.sys.service.FmsDocbrowserPriUserService;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.service.SysUserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.doc_manage.entity.VDocInfoDetailEntity;
import io.renren.modules.doc_manage.service.VDocInfoDetailService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


/**
 * VIEW
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
@RestController
@RequestMapping("doc_manage/vdocinfodetail")
public class VDocInfoDetailController {
    @Autowired
    private VDocInfoDetailService vDocInfoDetailService;
    @Autowired
    private FmsDocbrowserPriDeptService fmsDocbrowserPriDeptService;
    @Autowired
    private FmsDocbrowserPriUserService fmsDocbrowserPriUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("doc_manage:vdocinfodetail:list")
    public R list(@RequestParam Map<String, Object> params){
        Object con=params.get("info");
        if(con!=null && !con.toString().trim().equals("")){
            String[] cons  =con.toString().split(" ");
            params.put("conditionList",Arrays.asList(cons));
        }
        SomeTools.delnullcondtion(params);


        PageUtils page = vDocInfoDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
   // @RequiresPermissions("doc_manage:vdocinfodetail:info")
    public R info(@PathVariable("docId") Long docId){
        VDocInfoEntity vDocInfoDetail = vDocInfoDetailService.selectDocInfo(docId);
        Map<String,Object> params=new HashMap<>();
        params.put("docId",docId);
        String viewPri = vDocInfoDetail.getViewPri();
        if(viewPri.equals("指定部门可见")){
            List<FmsDocbrowserPriDeptEntity> priDeptList = fmsDocbrowserPriDeptService.queryList(params);
            List<Long> deptList = new ArrayList<>();
            List<String> deptsList = new ArrayList<>();
            for (FmsDocbrowserPriDeptEntity fmsDocbrowserPriDeptEntity : priDeptList) {
                deptList.add(fmsDocbrowserPriDeptEntity.getDeptId());
            }
            Long[] deptString = new Long[deptList.size()];
            deptList.toArray(deptString);
            for(int i =0;i<deptString.length;i++){
                if(deptString[i]!=null){
                    SysDeptEntity sysDeptEntity = sysDeptService.getById(deptString[i]);
                    deptsList.add(sysDeptEntity.getName());
                }
            }
            String[] deptsString = new String[deptsList.size()];
            deptsList.toArray(deptsString);
      //      for(int i =0;i<deptString.length;i++) {
      //          System.out.println(deptString[i]);
      //      }
      //      for(int i =0;i<deptsString.length;i++) {
      //          System.out.println(deptsString[i]);
      //      }
            vDocInfoDetail.setDeptsString(deptsString);
        }
        if(viewPri.equals("指定人可见")){
            List<FmsDocbrowserPriUserEntity> priUserList = fmsDocbrowserPriUserService.queryList(params);
            List<Long> userList = new ArrayList<>();
            List<String> usersList = new ArrayList<>();
            for(FmsDocbrowserPriUserEntity fmsDocbrowserPriUserEntity : priUserList){
                userList.add(fmsDocbrowserPriUserEntity.getUserId());
            }
            Long[] userString = new Long[userList.size()];
            userList.toArray(userString);
            for(int i =0;i<userString.length;i++){
                if(userString[i]!=null){
                    SysUserEntity sysUserEntity = sysUserService.getById(userString[i]);
                    usersList.add(sysUserEntity.getUsername());
                }
            }
            String[] usersString = new String[usersList.size()];
            usersList.toArray(usersString);
            vDocInfoDetail.setUsersString(usersString);
        }
        return R.ok().put("vDocInfoDetail", vDocInfoDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:vdocinfodetail:save")
    public R save(@RequestBody VDocInfoDetailEntity vDocInfoDetail){
        vDocInfoDetailService.save(vDocInfoDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:vdocinfodetail:update")
    public R update(@RequestBody VDocInfoDetailEntity vDocInfoDetail){
        ValidatorUtils.validateEntity(vDocInfoDetail);
        vDocInfoDetailService.updateById(vDocInfoDetail);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:vdocinfodetail:delete")
    public R delete(@RequestBody Long[] docIds){
        vDocInfoDetailService.removeByIds(Arrays.asList(docIds));

        return R.ok();
    }
    /**
     * 导出明细
     */
    @RequestMapping("/exportdetail")
    public void exportExceldetail(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException {
        params.put("limit","-1"); //不限制长度，全部导出
        Object con=params.get("info");
        if(con!=null && !con.toString().trim().equals("")){
            String[] cons  =con.toString().split(" ");
            params.put("conditionList",Arrays.asList(cons));
        }
        SomeTools.delnullcondtion(params);
        PageUtils page = vDocInfoDetailService.queryDetailPage(params);


        // return R.ok().put("page", page);
        // DocTypeEntity
        //先查询到要导出的excel数据
        List<VDocInfoDetailEntity> dataList = (List<VDocInfoDetailEntity>) page.getList();


        ExportParams exportParams = new ExportParams();  //导出的excel相关配置
        exportParams.setTitle("档案明细");  //导出excel的第一行标题名字
        exportParams.setSheetName("档案明细sheet");  //导出的excel的sheet名称
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, VDocInfoDetailEntity.class, dataList);
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("档案明细.xlsx", "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        //关闭流
        outputStream.close();
        workbook.close();
    }
    /**
     * 导出明细
     */
    @RequestMapping("/exportdoccheck")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletResponse response) throws IOException {
        Object con=params.get("info");
        if(con!=null && !con.toString().trim().equals("")){
            String[] cons  =con.toString().split(" ");
            params.put("conditionList",Arrays.asList(cons));
        }
      //  PageUtils page =


        // return R.ok().put("page", page);
        // DocTypeEntity
        //先查询到要导出的excel数据
        SomeTools.delnullcondtion(params);
        List<VDocInfoEntity> dataList = vDocInfoDetailService.selectDocInfo(params);


        ExportParams exportParams = new ExportParams();  //导出的excel相关配置
        exportParams.setTitle("档案信息");  //导出excel的第一行标题名字
        exportParams.setSheetName("档案信息sheet");  //导出的excel的sheet名称
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, VDocInfoEntity.class, dataList);
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("档案信息.xlsx", "utf-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        //关闭流
        outputStream.close();
        workbook.close();
    }
}
