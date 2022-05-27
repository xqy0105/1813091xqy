package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 档案
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:15
 */
@Data
@TableName("fms_document")
public class FmsDocumentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_id
	 */
	@TableId
	private Long docId;
	/**
	 * doc_type_id
	 */
	@NotNull(message="档案类型不能为空")
	private Long docTypeId;
	/**
	 * 所属部门
	 */
	private Long ownDeptId;
	/**
	 * 负责人
	 */
	private Long ownUserId;
	/**
	 * 创建人
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createTime;
	/**
	 * 档案形式
	 */
	private String docStyle;
	/**
	 * 存放地址
	 */
	private String storePlace;
	/**
	 * 浏览权限类型
	 */
	private String viewPri;
	/**
	 * 档案名称
	 */
	private String docName;
	/**
	 * 档案编号/文号
	 */
	private String docNum;
	/**
	 * 是否有金额
	 */
	private Integer isAmount;
	/**
	 * 金额
	 */
	private Float amount;
	/**
	 * 定期归档模式
	 */
	private String archiveMode;
	/**
	 * 档案数量
	 */
	private Integer docCount;
	/**
	 * 档案页数
	 */
	private Integer docPages;
	/**
	 * 密级/标签
	 */
	private String secretDegree;
	/**
	 * 起始日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date beginDate;
	/**
	 * 终止日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	/**
	 * 档案保管期限
	 */
	private String storagPeriod;
	/**
	 * 合同甲方
	 */
	private String contractA;
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

	private  Integer docCurrCount;
	/**
	 * 文件file列表
	 */
	@TableField(exist=false)
	private List<FmsDocumentFileEntity> docFileList;
	/**
	 *
	 */
	@TableField(exist=false)
	private Long[] deptsString;


	@TableField(exist=false)
	private Long[] usersString;
	/**
	 *修改人
	 */
    private Long updateUser;
	/**
	 *修改人
	 */
	private Integer status;
	/**
	 * 档案大类
	 */
	@TableField(exist = false)
	private Long catalogId;
}
