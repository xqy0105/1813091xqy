package io.renren.modules.doc_manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateException;
import io.renren.common.utils.FreeMarkerTemplateUtil;
import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysDeptService;
import io.renren.modules.sys.shiro.ShiroUtils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.SysTemplateDao;
import io.renren.modules.doc_manage.entity.SysTemplateEntity;
import io.renren.modules.doc_manage.service.SysTemplateService;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("sysTemplateService")
public class SysTemplateServiceImpl extends ServiceImpl<SysTemplateDao, SysTemplateEntity> implements SysTemplateService {

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private SysUserRoleDao userRoleDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysTemplateEntity> page = this.page(
                new Query<SysTemplateEntity>().getPage(params),
                new QueryWrapper<SysTemplateEntity>()
        );
        for(SysTemplateEntity sysTemplateEntity : page.getRecords()){
            SysDeptEntity sysDeptEntity = sysDeptService.getById(sysTemplateEntity.getOwnDeptId());
            if(sysDeptEntity != null){
                sysTemplateEntity.setDeptName(sysDeptEntity.getName());
            }
        }
        return new PageUtils(page);
    }

    @Override
    public List<SysTemplateEntity> getList(Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        QueryWrapper queryWrapper = new QueryWrapper();
        if(userId != 1){
            List<Long> roleIdList = userRoleDao.queryRoleIdList(userId);
            queryWrapper.in("user_identity",roleIdList);
        }
        return this.baseMapper.selectList(queryWrapper);
    }
    @Override
    public Map<String, Object> yearCreate(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>>  list = new ArrayList<>();
        String year = params.get("year").toString();
        for(int i=0;i<12;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("month",(i+1)+"月");
            String month =year+"-"+((i+1)<10?"0"+(i+1):(i+1));
            List<String> numList = new ArrayList<>();
            List<Map<String, Object>> yearCreateList = baseMapper.yearCreate(month);
            for(Map<String,Object> map1:yearCreateList){
                numList.add(map1.get("num").toString());
            }
            map.put("data",numList);
            list.add(map);
        }
        resultMap.put("data",list);
        return resultMap;
    }

    @Override
    public PageUtils queryYearPage(Map<String, Object> params) {
        String year = params.get("year").toString();
        Integer page = Integer.valueOf(params.get("page").toString());
        Integer limit = Integer.valueOf(params.get("limit").toString());
        Integer startSize = (page-1)*limit;
        List<Map<String,Object>> list = baseMapper.queryYearPage(year,startSize,limit);
        Integer totalCount = baseMapper.queryYearPageCount(year);
        return new PageUtils(list,totalCount,limit,page);
    }

    @Override
    public Map<String, Object> echartHandler(Map<String, Object> params) {
        Map<String,Object> returnMap = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        List<String> yearList = new ArrayList<>();
        List<String> numsList = new ArrayList<>();
        for(int i=0;i<5;i++){
            String yearStr = String.valueOf(year-i);
            yearList.add(yearStr);
            int nums = baseMapper.echartHandler(yearStr);
            numsList.add(String.valueOf(nums));
        }
        returnMap.put("yearList",yearList);
        returnMap.put("numsList",numsList);
        return returnMap;
    }

    @Override
    public Map<String, Object> echartHandler1(Map<String, Object> params) {
        Map<String,Object> returnMap = new HashMap<>();
        Integer year = Integer.valueOf(params.get("year").toString());
        List<Map<String,Object>> list = baseMapper.echartHandler1(year);
        returnMap.put("list",list);
        return returnMap;
    }

    @Override
    public Map<String, Object> echartHandler2(Map<String, Object> params) {
        Map<String,Object> returnMap = new HashMap<>();
        String year = params.get("year").toString();
        List<String> borrowApplyList = new ArrayList<>();
        List<String> borrowNumList = new ArrayList<>();
        for(int i=0;i<12;i++){
            String month =year+"-"+((i+1)<10?"0"+(i+1):(i+1));
            int num1 = baseMapper.getBorrowApplyNumNew(month);
            int num2 = baseMapper.getBorrowNumNew(month);
            borrowApplyList.add(String.valueOf(num1));
            borrowNumList.add(String.valueOf(num2));
        }
        //借阅
        returnMap.put("borrowApplyList",borrowApplyList);
        //归还
        returnMap.put("borrowNumList",borrowNumList);
        return returnMap;
    }

    @Override
    public Map<String, Object> echartHandler3(Map<String, Object> params) {
        Map<String,Object> returnMap = new HashMap<>();
        String year = params.get("year").toString();
        List<String> transableList = new ArrayList<>();
        List<String> transInList = new ArrayList<>();
        for(int i=0;i<12;i++){
            String month =year+"-"+((i+1)<10?"0"+(i+1):(i+1));
            int num1 = baseMapper.getTransableNumNew(month);
            int num2 = baseMapper.getTransInNumNew(month);
            transableList.add(String.valueOf(num1));
            transInList.add(String.valueOf(num2));
        }
        //转出
        returnMap.put("transableList",transableList);
        //转入
        returnMap.put("transInList",transInList);
        return returnMap;
    }

    @Override
    public Map<String, Object> echartHandler4(Map<String, Object> params) {
        Map<String,Object> returnMap = new HashMap<>();
        String year = params.get("year").toString();
        List<String> confimApplyList = new ArrayList<>();
        for(int i=0;i<12;i++){
            String month =year+"-"+((i+1)<10?"0"+(i+1):(i+1));
            int num1 = baseMapper.getConfimApplyNumNew(month);
            confimApplyList.add(String.valueOf(num1));
        }
        //交付
        returnMap.put("confimApplyList",confimApplyList);
        return returnMap;
    }

    @Override
    public Map<String, Object> echartHandler5(Map<String, Object> params) {
        Map<String,Object> returnMap = new HashMap<>();
        String year = params.get("year").toString();
        List<String> modifyList = new ArrayList<>();
        for(int i=0;i<12;i++){
            String month =year+"-"+((i+1)<10?"0"+(i+1):(i+1));
            int num1 = baseMapper.getModifyApplyNumNew(month);
            modifyList.add(String.valueOf(num1));
        }
        //修改
        returnMap.put("modifyList",modifyList);
        return returnMap;
    }

    @Override
    public void exportYearList(Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {

        String year = params.get("year").toString();

        List<Map<String,Object>> list = baseMapper.exportYearList(year);

        String fileName =  year +"各部门数据统计";

        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        try {
            excelUtil(request,response,map,"exportFtl.ftl",fileName);
        }catch (Exception e){
            log.error("",e);
        }

    }

    /** 数据类型为{@value} .*/
    public static final String UTF = "UTF-8";


    /**
     * 调用创建excel帮助类，来创建excel.
     * @param request 请求
     * @param response 响应
     * @param dataMap 传过来的map
     * @param valueName 存储名
     * @param fileName 文件名
     */
    public void excelUtil(final HttpServletRequest request,
                          final HttpServletResponse response,
                          final Map<?, ?> dataMap,
                          final String valueName,
                          final String fileName) throws IOException, TemplateException {
        byte[] file = FreeMarkerTemplateUtil.createExcel(dataMap, valueName);
        try (OutputStream out = response.getOutputStream();) {
            request.setCharacterEncoding(UTF);
            //调用创建excel帮助类，其中LeaderHomepage.ftl为模板名称  路径export-model/LeaderHomepage.ftl
            response.setCharacterEncoding(UTF);
            response.setContentType("application/msexcel");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", UTF));
            out.write(file);
            out.flush();
        }
    }


    @Override
    public Map<String, Object> create(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>>  list = new ArrayList<>();
        String startTime = params.get("startTime").toString();
        String endTime = params.get("endTime").toString();
        String templateId = params.get("templateId").toString();
        SysTemplateEntity sysTemplateEntity = baseMapper.selectById(templateId);
        String roleId = "";
        //用户
        /*if(sysTemplateEntity.getUserIdentity() == 0){
            roleId = "3";
        }else if(sysTemplateEntity.getUserIdentity() == 1){
            //档案管理员
            roleId = "2";
        }*/
        List<StateCycle> stateCycleList = getCycle(startTime,endTime,sysTemplateEntity.getStateCycle());

        //录著数
        if(sysTemplateEntity.getQueryContent() == 1){
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getDocumentNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }

        }else if(sysTemplateEntity.getQueryContent() == 2){
            //借阅数
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getBorrowApplyNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }
        }else if(sysTemplateEntity.getQueryContent() == 3){
            //归还数
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getBorrowNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }
        }else if(sysTemplateEntity.getQueryContent() == 4){
            //转出数
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getTransableNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }
        }else if(sysTemplateEntity.getQueryContent() == 5){
            //转入数
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getTransInNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }
        }else if(sysTemplateEntity.getQueryContent() == 6){
            //交付数
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getConfimApplyNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }
        }else if(sysTemplateEntity.getQueryContent() == 7){
            //修改数
            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getModifyApplyNum(stateCycle.getStartTime(),stateCycle.getEndTime(),sysTemplateEntity);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }
        }
        //借阅
        /*if(sysTemplateEntity.getQueryType() ==1){

            int i=0;
            for(StateCycle stateCycle:stateCycleList){
                Map<String,Object> map = new HashMap<>();
                Integer num = baseMapper.getCustomReport(stateCycle.getStartTime(),stateCycle.getEndTime(),roleId);
                map.put("value",num);
                if(sysTemplateEntity.getStateCycle() == 1){
                    map.put("name","第"+(i+1)+"日");
                }else if(sysTemplateEntity.getStateCycle() == 2){
                    map.put("name","第"+(i+1)+"周");
                }else if(sysTemplateEntity.getStateCycle() == 3){
                    map.put("name","第"+(i+1)+"月");
                }else if(sysTemplateEntity.getStateCycle() == 4){
                    map.put("name","第"+(i+1)+"年");
                }
                list.add(map);
                i++;
            }

        }else if(sysTemplateEntity.getQueryType() ==2){
            //录入

        }*/
        resultMap.put("data",list);
        resultMap.put("chartType",sysTemplateEntity.getChartType());
        return resultMap;
    }

    private List<StateCycle> getCycle(String startTime,String endTime,Integer stateCycle){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try {
            start.setTime(sf.parse(startTime));
            end.setTime(sf.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<StateCycle> list = new ArrayList<>();
        if(stateCycle == 1){//日
            while(end.compareTo(start)>=0){
                int month = start.get(Calendar.MONTH)+1;
                int day = start.get(Calendar.DAY_OF_MONTH);
                StateCycle stateCycle1 = new StateCycle();
                String time = start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day);
                stateCycle1.setStartTime(time);
                stateCycle1.setEndTime(time);
                list.add(stateCycle1);
                start.add(Calendar.DAY_OF_MONTH,1);
            }
        }else if(stateCycle == 2){//周
            while(end.compareTo(start)>=0){
                StateCycle stateCycle1 = new StateCycle();
                int month = start.get(Calendar.MONTH)+1;
                int day = start.get(Calendar.DAY_OF_MONTH);
                start.setFirstDayOfWeek(Calendar.MONDAY); //以周一为首日
                stateCycle1.setStartTime(start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                start.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //周日
                if(end.after(start)){
                    month = start.get(Calendar.MONTH)+1;
                    day = start.get(Calendar.DAY_OF_MONTH);
                    stateCycle1.setEndTime(start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                    start.add(Calendar.DAY_OF_MONTH,1);
                }else{
                    month = end.get(Calendar.MONTH)+1;
                    day = end.get(Calendar.DAY_OF_MONTH);
                    stateCycle1.setEndTime(end.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                }
                list.add(stateCycle1);
            }
        }else if(stateCycle == 3){//月
            while(end.compareTo(start)>=0){
                StateCycle stateCycle1 = new StateCycle();
                int month = start.get(Calendar.MONTH)+1;
                int day = start.get(Calendar.DAY_OF_MONTH);
                stateCycle1.setStartTime(start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                start.set(Calendar.DAY_OF_MONTH,start.getActualMaximum(Calendar.DAY_OF_MONTH)); //月末
                if(end.after(start)){
                    month = start.get(Calendar.MONTH)+1;
                    day = start.get(Calendar.DAY_OF_MONTH);
                    stateCycle1.setEndTime(start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                    start.add(Calendar.DAY_OF_MONTH,1);
                }else{
                    month = end.get(Calendar.MONTH)+1;
                    day = end.get(Calendar.DAY_OF_MONTH);
                    stateCycle1.setEndTime(end.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                }
                list.add(stateCycle1);
            }
        }else if(stateCycle == 4){//年
            while(end.compareTo(start)>=0){
                StateCycle stateCycle1 = new StateCycle();
                int month = start.get(Calendar.MONTH)+1;
                int day = start.get(Calendar.DAY_OF_MONTH);
                stateCycle1.setStartTime(start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                start.set(Calendar.DAY_OF_YEAR,start.getActualMaximum(Calendar.DAY_OF_YEAR)); //月末
                if(end.after(start)){
                    month = start.get(Calendar.MONTH)+1;
                    day = start.get(Calendar.DAY_OF_MONTH);
                    stateCycle1.setEndTime(start.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                    start.add(Calendar.DAY_OF_MONTH,1);
                }else{
                    month = end.get(Calendar.MONTH)+1;
                    day = end.get(Calendar.DAY_OF_MONTH);
                    stateCycle1.setEndTime(end.get(Calendar.YEAR)+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day));
                }
                list.add(stateCycle1);
            }
        }
        return list;
    }



}
class StateCycle{
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
