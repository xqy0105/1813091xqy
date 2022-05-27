package io.renren.modules.sys.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocumentFileDao;
import io.renren.modules.sys.entity.FmsDocumentFileEntity;
import io.renren.modules.sys.service.FmsDocumentFileService;


@Service("fmsDocumentFileService")
public class FmsDocumentFileServiceImpl extends ServiceImpl<FmsDocumentFileDao, FmsDocumentFileEntity> implements FmsDocumentFileService {
    @Autowired
    private FmsDocumentFileDao fmsDocumentFileDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocumentFileEntity> page = this.page(
                new Query<FmsDocumentFileEntity>().getPage(params),
                new QueryWrapper<FmsDocumentFileEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public List<FmsDocumentFileEntity> queryList(Map<String, Object> map){
        return fmsDocumentFileDao.queryList(map);
    }
    @Override
    public FmsDocumentFileDao queryById(Long docfileId){
        return this.fmsDocumentFileDao.queryById(docfileId);
    }
    public void deldocfile(Long docfileId,Long userId){
        fmsDocumentFileDao.delete(docfileId,userId);
    }


    @Override
    public boolean canView(Long userId,Long docId) {
        return fmsDocumentFileDao.canView(userId,docId)>0;
    }

}
