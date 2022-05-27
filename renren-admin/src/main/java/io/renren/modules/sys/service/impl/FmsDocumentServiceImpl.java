package io.renren.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.FmsDocumentDao;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import io.renren.modules.sys.service.FmsDocumentService;


@Service("fmsDocumentService")
public class FmsDocumentServiceImpl extends ServiceImpl<FmsDocumentDao, FmsDocumentEntity> implements FmsDocumentService {
    @Autowired
    private FmsDocumentDao fmsDocumentDao;
    //@Override
    public int insert(FmsDocumentEntity entity) {
        return baseMapper.insert(entity);
    }
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FmsDocumentEntity> page = this.page(
                new Query<FmsDocumentEntity>().getPage(params),
                new QueryWrapper<FmsDocumentEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public List<FmsDocumentEntity> queryList(Map<String, Object> map){
        return fmsDocumentDao.queryList(map);
    }
    @Override
    public void deletedoc(Long docId,Long userId){
        fmsDocumentDao.delete(docId,userId);
    }
}
