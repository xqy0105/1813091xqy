package io.renren.modules.doc_manage.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.doc_manage.service.DocBorrowApplyService;
import io.renren.modules.doc_manage.service.VDocInfoDetailService;
import io.renren.modules.doc_manage.util.SomeTools;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.doc_manage.entity.DocumentEntity;
import io.renren.modules.doc_manage.service.DocumentService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 档案
 *
 */
@RestController
@RequestMapping("doc_manage/document")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private VDocInfoDetailService vDocInfoDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("doc_manage:document:list")
    public R list(@RequestParam Map<String, Object> params){
        Object con=params.get("info");
        if(con!=null && !con.toString().trim().equals("")){
            String[] cons  =con.toString().split(" ");
            params.put("conditionList",Arrays.asList(cons));
        }
        SomeTools.delnullcondtion(params);
        PageUtils page = vDocInfoDetailService.queryAllPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/trans_in_list")
    @RequiresPermissions("doc_manage:document:list")
    public R transInList(@RequestParam Map<String, Object> params){
        params.put("trans_in", true);
        PageUtils page = documentService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/transable_list")
    @RequiresPermissions("doc_manage:document:list")
    public R transableList(@RequestParam Map<String, Object> params){
        params.put("transable", true);
        PageUtils page = documentService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{docId}")
    @RequiresPermissions("doc_manage:document:info")
    public R info(@PathVariable("docId") Long docId){
        DocumentEntity document = documentService.getById(docId);

        return R.ok().put("document", document);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("doc_manage:document:save")
    public R save(@RequestBody DocumentEntity document){
        documentService.save(document);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("doc_manage:document:update")
    public R update(@RequestBody DocumentEntity document){
        ValidatorUtils.validateEntity(document);
        documentService.updateById(document);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doc_manage:document:delete")
    public R delete(@RequestBody Long[] docIds){
        documentService.removeByIds(Arrays.asList(docIds));

        return R.ok();
    }
    /**
     * 交接出
     */
    @RequestMapping("/transOut")
    public R transOut(@RequestBody String str){
        Map<String, Object> params = (Map) JSON.parse(str);
        //System.out.println("transOut Params: "+params.toString());
        StringBuilder message= new StringBuilder();
        if(documentService.transOutByIds(params,message)) return R.ok();
        else return R.error(message.toString());
//        return R.ok();
    }

    /**
     * 接收文档
     *
     */
    @RequestMapping("/transIn")
    public R transIn(@RequestBody FmsDocumentEntity fmsDocument){
        SysUserEntity user = ShiroUtils.getUserEntity();
        Long userId=user.getUserId();
        if(userId.equals(fmsDocument.getCreateUserId())){
            return (R.error("交出人与录入人不能是同一人，请退回！"));
        }
        documentService.transInByIds(fmsDocument);
        return R.ok();
    }
    /**
     * 退回文档
     *
     */
    @RequestMapping("/transReject")
    public R transIn(@RequestParam Map<String, Object> params){
        if(documentService.transRejectById(params)) return R.ok();
        else return R.error("退回失败，请联系管理员查看交接记录是否存在。");

    }
    @RequestMapping("/docTimelist")
    public R docTimelist(@RequestParam Map<String, Object> params){
        if(params==null){
            params=new HashMap<>();
        }

        PageUtils page = documentService.querydocTimelistPage(params);


        return R.ok().put("page", page);
    }

}
