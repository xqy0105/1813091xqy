package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案修改日志
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@Data
@TableName("fms_doc_modify_log")
public class FmsDocModifyLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_id
	 */
	@TableId
	private Long docId;
	/**
	 * 修改人
	 */
	private Long modifyUserId;
	/**
	 * 字段名称
	 */
	private String fieldName;
	/**
	 * old_value
	 */
	private String oldValue;
	/**
	 * new_value
	 */
	private String newValue;
	/**
	 * modify_time
	 */
	private Date modifyTime;

}
