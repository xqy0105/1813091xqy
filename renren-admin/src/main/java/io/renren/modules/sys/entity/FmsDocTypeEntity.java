package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案类型
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@Data
@TableName("fms_doc_type")
public class FmsDocTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_type_id
	 */
	@TableId
	private Long docTypeId;
	/**
	 * catalog
	 */
	private Long catalogId;
	/**
	 * 档案类型名称
	 */
	private String docTypeName;
	/**
	 * 
	 */
	private Integer delFlag;

}
