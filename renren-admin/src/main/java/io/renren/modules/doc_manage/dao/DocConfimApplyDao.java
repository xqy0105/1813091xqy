package io.renren.modules.doc_manage.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.doc_manage.entity.DocBorrowApplyEntity;
import io.renren.modules.doc_manage.entity.DocConfimApplyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.sys.entity.FmsDocumentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-25 11:00:16
 */
@Mapper
public interface DocConfimApplyDao extends BaseMapper<DocConfimApplyEntity> {

    //void docConfirm(FmsDocumentEntity fmsDocumentEntity);
    //void reCallConfirm(Long docId);
    void handle(Map<String, Object> params);
    IPage<DocConfimApplyEntity> docConfirmApplyList(IPage<DocConfimApplyEntity> page);
    IPage<DocConfimApplyEntity> docConfirmList(IPage<DocConfimApplyEntity> page);
    DocConfimApplyEntity docApplyList(Map<String, Object> params);

	
}
