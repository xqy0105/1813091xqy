package io.renren.modules.doc_manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-25 11:00:16
 */
@Data
@TableName("fms_doc_confim_apply")
public class DocConfimApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 确认申请ID
	 */
	@TableId
	private Long docConfirmApplyId;
	/**
	 * 档案ID
	 */
	private Long docId;
	/**
	 * doc_name
	 */
	@TableField(exist=false)
	private String docName;
	/**
	 * 审批人ID
	 */
	private Long approverId;
	/**
	 * 审批人name
	 */

	@TableField(exist=false)
	private String approverName;
	/**
	 * 档案管理员ID
	 */
	private Long ownUserId;
	/**
	 * 档案管理员name
	 */

	@TableField(exist=false)
	private String ownUserName;
	/**
	 * 交付人ID
	 */
	private Long createUserId;
	/**
	 * 申请确认时间
	 */
	private Date applyTime;
	/**
	 * 交付人审批时间
	 */
	private Date approveTime;
	/**
	 * 审批状态，-1待审批，0退回，1通过
	 */
	private Integer approveStatus;
	/**
	 * 审批意见
	 */
	private String approveInfo;

	/*public String getApproveInfo() {
		if(approveStatus == null)return "";
		switch (approveStatus){
			case 1:return "通过";
			case -1:return "未审批";
			case 0:return "驳回";
			default:
				return "";
		}
	}*/
	/**
	 * 备注
	 */
	private String comm;

}
