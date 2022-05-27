package io.renren.modules.doc_manage.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * VIEW
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
@Data
@TableName("v_fms_document")
public class VDocumentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_id
	 */
	@TableId
	private Long docId;
	/**
	 * 合同甲方
	 */
	private String contractA;
	/**
	 * 档案大类名称
	 */
	private String catalogName;
	/**
	 * 档案类型名称
	 */
	private String docTypeName;
	/**
	 * 起始日期
	 */
	private Date beginDate;
	/**
	 * 终止日期
	 */
	private Date endDate;
	/**
	 * 档案名称
	 */
	private String docName;
	/**
	 * 档案在库数量
	 */
	private Integer docCurrCount;
	/**
	 * 档案编号/文号
	 */
	private String docNum;
	/**
	 * 档案页数
	 */
	private Integer docPages;
	/**
	 * 档案形式
	 */
	private String docStyle;
	/**
	 * doc_type_id
	 */
	private Long docTypeId;
	/**
	 * 是否有金额
	 */
	private Integer isAmount;
	/**
	 * 金额
	 */
	private Float amount;
	/**
	 * 密级/标签
	 */
	private String secretDegree;
	/**
	 * 档案保管期限
	 */
	private String storagPeriod;
	/**
	 * 合同乙方
	 */
	private String contractB;
	/**
	 * 合同丙方
	 */
	private String contractC;
	/**
	 * 合同丁方
	 */
	private String contractD;
	/**
	 * 合同戊方
	 */
	private String contractE;
	/**
	 * 查询标签定义，分号分隔
	 */
	private String labels;
	private String ownDeptName;
	/**
	 * 浏览权限类型
	 */

	private String viewPri;
	/**
	 * 定期归档模式
	 */

	private String archiveMode;
	/**
	 * 存放地址
	 */

	private String storePlace;
	/**
	 * 交出人
	 */
	private String createUsername;
	/**
	 * 档案状态-1：新录入/修改申请已通过 可修改 1：录入待确认，不可修改，可取消 0：交付人已确认，不可修改，可申请修改
	 */
	private int status;
	private int tranStatus;
}
