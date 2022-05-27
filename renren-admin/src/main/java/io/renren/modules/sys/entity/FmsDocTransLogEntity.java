package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案流转日志
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@Data
@TableName("fms_doc_trans_log")
public class FmsDocTransLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_id
	 */
	@TableId
	private Long docId;
	private String docName;
	/**
	 * from_user_id
	 */
	private Long fromUserId;
	private String fromUserName;
	/**
	 * to_user_id
	 */
	private Long toUserId;
	private String toUserName;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 交出时间
	 */
	private Date outTime;
	/**
	 * 接收时间
	 */
	private Date inTime;
	/**
	 * 备注
	 */
	private String comm;

}
