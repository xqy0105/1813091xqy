package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.doc_manage.entity.DocCatalogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 档案借阅申请
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 16:16:53
 */
@Mapper
public interface DocBorrowApplyDao extends BaseMapper<DocBorrowApplyEntity> {
    void apply(Map<String, Object> params);
    void handle(Map<String, Object> params);
    IPage<DocBorrowApplyEntity> docBorrowApplyList(IPage<DocBorrowApplyEntity> page);
    IPage<DocBorrowApplyEntity> docBorrowList(IPage<DocBorrowApplyEntity> page);
//    IPage<DocBorrowApplyEntity> docBorrowApplyList(IPage<DocBorrowApplyEntity> page,@Param(Constants.WRAPPER) Wrapper<DocBorrowApplyEntity> queryWrapper);
}
