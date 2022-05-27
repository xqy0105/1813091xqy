package io.renren.modules.doc_manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案借阅申请
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 16:16:53
 */
@Data
@TableName("fms_doc_borrow_apply")
public class DocBorrowApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 借阅记录的id
	 */
	@TableId
	private Integer docBorrowApplyId;
	/**
	 * 审批人
	 */
	private Long approverId;

	/**
	 * 审批人name
	 */

	@TableField(exist=false)
	private String approverName;
	/**
	 * 借阅人
	 */
	private Long borrowerId;
	/**
	 * 借阅人name
	 */

	@TableField(exist=false)
	private String borrowerName;
	/**
	 * doc_id
	 */
	private Long docId;
	/**
	 * doc_name
	 */
	@TableField(exist=false)
	private String docName;
	/**
	 * 申请借阅时间
	 */
	private Date applytime;
	/**
	 * 实际归还时间
	 */
	private Date returnTime;
	/**
	 * 借阅数量;默认1
	 */
	private Integer borrownum;
	/**
	 * 审批时间
	 */
	private Date approveTime;
	/**
	 * 审批意见
	 */
	private String approveInfo;
	/**
	 * 审批结果
            -1，驳回
            0，申请中
            1，同意
	 */
	private Integer applyResult;
	/**
	 * 审批结果
	 -1，驳回
	 0，申请中
	 1，同意
	 */
	private String applyResultInfo;

	public String getApplyResultInfo() {
		if(applyResult == null)return "";
		switch (applyResult){
			case 1:return "通过";
			case 0:return "未审批";
			case -1:return "驳回";
			default:
				return "";
		}
	}

	/**
	 * 备注
	 */
	private String comm;

	/**
	 * 截止时间
	 */

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")

	private Date expirationDate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@TableField(exist=false)
	private Date return_time;

	@TableField(exist = false)
	private Short del_flag;

}
