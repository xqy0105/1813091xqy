package io.renren.modules.sys.controller;

import java.util.*;

import io.renren.common.annotation.SysLog;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.doc_manage.service.DocConfimApplyService;
import io.renren.modules.doc_manage.service.DocModifyApplyService;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.*;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:15
 */
@RestController
@RequestMapping("sys/fmsdocument")
public class FmsDocumentController {
    @Autowired
    private FmsDocumentService fmsDocumentService;
    @Autowired
    private FmsDocumentFileService fmsDocumentFileService;
@Autowired
private FmsDocbrowserPriDeptService fmsDocbrowserPriDeptService;
@Autowired
private FmsDocbrowserPriUserService fmsDocbrowserPriUserService;
@Autowired
private DocConfimApplyService docConfimApplyService;
@Autowired
private FmsDocTypeService fmsDocTypeService;
@Autowired
private DocModifyApplyService docModifyApplyService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:fmsdocument:list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = fmsDocumentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
    @RequiresPermissions("sys:fmsdocument:info")
    public R info(@PathVariable("docId") Long docId){
        FmsDocumentEntity fmsDocumentList = fmsDocumentService.getById(docId);
        Map<String,Object> params=new HashMap<>();
        params.put("docId",docId);
        Long docTypeId = fmsDocumentList.getDocTypeId();
        FmsDocTypeEntity fmsDocTypeEntity = fmsDocTypeService.getById(docTypeId);
        fmsDocumentList.setCatalogId(fmsDocTypeEntity.getCatalogId());
        fmsDocumentList.setDocFileList(fmsDocumentFileService.queryList(params));
        String viewPri = fmsDocumentList.getViewPri();
        if(viewPri.equals("指定部门可见")) {
            List<FmsDocbrowserPriDeptEntity> priDeptList = fmsDocbrowserPriDeptService.queryList(params);
            List<Long> deptList = new ArrayList<>();
            for (FmsDocbrowserPriDeptEntity fmsDocbrowserPriDeptEntity : priDeptList) {
                deptList.add(fmsDocbrowserPriDeptEntity.getDeptId());
            }
            Long[] deptString = new Long[deptList.size()];
            deptList.toArray(deptString);
            fmsDocumentList.setDeptsString(deptString);
        }
        if(viewPri.equals("指定人可见")){
            List<FmsDocbrowserPriUserEntity> priUserList = fmsDocbrowserPriUserService.queryList(params);
            List<Long> userList = new ArrayList<>();
            for(FmsDocbrowserPriUserEntity fmsDocbrowserPriUserEntity : priUserList){
                userList.add(fmsDocbrowserPriUserEntity.getUserId());
            }
            Long[] userString = new Long[userList.size()];
            userList.toArray(userString);
            fmsDocumentList.setUsersString(userString);
        }
        return R.ok().put("fmsDocumentList", fmsDocumentList);
    }

    @RequestMapping("/select")
    @RequiresPermissions("sys:fmsdocument:select")
    public R select(@RequestParam Map<String, Object> params){
        List<FmsDocumentEntity> fmsDocumentList = fmsDocumentService.queryList(params);
        return R.ok().put("fmsDocumentList", fmsDocumentList);
    }

    /**
     * 保存
     */
    @SysLog("新增档案")
    @RequestMapping("/save")
    @RequiresPermissions("sys:fmsdocument:save")
    public R save(@RequestBody FmsDocumentEntity fmsDocument){
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
        if(userId.equals(fmsDocument.getCreateUserId())){
            return (R.error("交出人与录入人不能是同一人"));
        }else {
            fmsDocument.setCreateTime(new Date());
            //fmsDocument.setCreateUserId(userId);
            fmsDocument.setOwnUserId(userId);
            //fmsDocument.setOwnDeptId(user.getDeptId());
            fmsDocument.setStatus(-1);
            fmsDocumentService.save(fmsDocument);
            updatepri(fmsDocument);
            return R.ok().put("fmsDocument", fmsDocument);
        }
    }

    /**
     * 修改
     */
    @SysLog("修改档案")
    @RequestMapping("/update")
    @RequiresPermissions("sys:fmsdocument:update")
    public R update(@RequestBody FmsDocumentEntity fmsDocument){
        ValidatorUtils.validateEntity(fmsDocument);


        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
        if(userId.equals(fmsDocument.getCreateUserId())){
            return (R.error("交出人与录入人不能是同一人"));
        }
        fmsDocument.setUpdateUser(userId);
        if(fmsDocument.getStatus()==-2){
            fmsDocument.setStatus(0);
        }
        fmsDocumentService.updateById(fmsDocument);
        updatepri(fmsDocument);
        return R.ok().put("fmsDocument",fmsDocument);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:fmsdocument:delete")
    public R delete(@RequestBody Long[] docIds){
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();

           if(docIds!=null||docIds.length!=0) {
               for (int i = 0; i < docIds.length; i++) {
                   fmsDocumentService.deletedoc(docIds[i], userId);
                   return R.ok("档案删除成功");
               }
           }else{
                   return R.ok("数组为空！");
               }

      return R.ok();
    }

    public boolean updatepri( FmsDocumentEntity fmsDocument){
        fmsDocbrowserPriDeptService.deletepridept(fmsDocument.getDocId());
        if(fmsDocument.getDeptsString()!=null&&fmsDocument.getDeptsString().length!=0){
           Long deptsString[]=fmsDocument.getDeptsString();
            for(int i=0;i<deptsString.length;i++){
                if(deptsString[i]!=null) {
                    FmsDocbrowserPriDeptEntity de = new FmsDocbrowserPriDeptEntity();
                    de.setDocId(fmsDocument.getDocId());
                    de.setDeptId((deptsString[i]));
                    de.setPrivilegeType("浏览");
                    fmsDocbrowserPriDeptService.save(de);
                }
            }
        }
       //
        fmsDocbrowserPriUserService.deletepriuser(fmsDocument.getDocId());
        if (fmsDocument.getUsersString()!=null&&fmsDocument.getUsersString().length!=0){
            Long usersString[]= fmsDocument.getUsersString();
            for (int i=0;i<usersString.length;i++){
                if(usersString[i]!=null){
                    FmsDocbrowserPriUserEntity user = new FmsDocbrowserPriUserEntity();
                    user.setDocId(fmsDocument.getDocId());
                    user.setUserId((usersString[i]));
                    user.setPrivilegeType("浏览");
                    fmsDocbrowserPriUserService.save(user);
                }
            }
        }
       return true;
    }

    @RequestMapping("/docConfirmApply/{docId}")
    public R docConfirmApply(@PathVariable("docId") Long docId){
        docConfimApplyService.docConfirm(docId);
        return R.ok("确认申请已提交");
    }
    @RequestMapping("/reCall/{docId}")
    public R reCall(@PathVariable("docId") Long docId){
        String message=docConfimApplyService.reCallConfirm(docId);
        return R.ok(message);
    }
    @RequestMapping("/docModifyApply/{docId}")
    public R docModifyApply(@PathVariable("docId") Long docId){
        docModifyApplyService.docModify(docId);
        return R.ok("修改申请已提交");
    }
    @RequestMapping("/reCalls/{docId}")
    public R reCalls(@PathVariable("docId") Long docId){
        String message=docModifyApplyService.reCallModify(docId);
        return R.ok(message);
    }

}
