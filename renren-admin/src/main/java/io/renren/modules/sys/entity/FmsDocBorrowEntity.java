package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * 档案借阅
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@Data
@TableName("fms_doc_borrow")
public class FmsDocBorrowEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Integer docBorrowId;

	/**
	 * 审批人
	 */

	private Long approverId;

	@TableField(exist=false)
	private String approverName;
	/**
	 * 借阅人
	 */
	private Long borrowerId;

	@TableField(exist=false)
	private String borrowerName;
	/**
	 * doc_id
	 */
	private Long docId;

	@TableField(exist=false)
	private String docName;
	/**
	 * 申请借阅时间
	 */
	private Date applytime;
	/**
	 * 借出时间
	 */
	private Date borrowtime;
	/**
	 * 归还截止时间
	 */
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd" , timezone = "GMT+8")
	private Date expirationDate;
	/**
	 * 实际归还时间
	 */
	private Date returnTime;
	/**
	 * 借阅数量;默认1
	 */
	private Integer borrownum;
	/**
	 * 备注
	 */
	private String comm;

	/**
	 * 归还结果
	 */
	private Integer returnResult;


}
