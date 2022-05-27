package io.renren.modules.doc_manage.service.impl;

import io.renren.modules.doc_manage.entity.DocumentEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.doc_manage.dao.OutdatesummaryDao;
import io.renren.modules.doc_manage.entity.OutdatesummaryEntity;
import io.renren.modules.doc_manage.service.OutdatesummaryService;


@Service("outdatesummaryService")
public class OutdatesummaryServiceImpl extends ServiceImpl<OutdatesummaryDao, OutdatesummaryEntity> implements OutdatesummaryService {
@Autowired
    OutdatesummaryDao outdatesummaryDao;
    @Override
    public List<OutdatesummaryEntity> queryList(Map<String, Object> params) {

        SysUserEntity user = ShiroUtils.getUserEntity();

        params.put("userId",user.getUserId());


        //调用删除过程
        return outdatesummaryDao.queryList(params);
    }

    @Override
    public List<OutdatesummaryEntity> querycommonsummaryList(Map<String, Object> params) {
        SysUserEntity user = ShiroUtils.getUserEntity();
        params.put("userId",user.getUserId());
        return outdatesummaryDao.queryCommonList(params);
    }
}
