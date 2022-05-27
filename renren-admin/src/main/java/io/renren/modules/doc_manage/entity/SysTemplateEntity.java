package io.renren.modules.doc_manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模板表
 *
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2022-01-22 10:34:13
 */
@Data
@TableName("sys_template")
public class SysTemplateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 模板名称
	 */
	private String name;
	/**
	 * 所属部门
	 */
	private Long ownDeptId;

	/**
	 * 档案大类
	 */
	private Long categoryId;

	/**
	 * 档案类型
	 */
	private Long docTypeId;

	/**
	 * 交出人
	 */
	private Long handleUserId;

	/**
	 * 档案形式
	 */
	private String docStyle;

	/**
	 * 密级/标签
	 */
	private String secretDegree;

	/**
	 * 部门名称
	 */
	@TableField(exist=false)
	private String deptName;
	/**
	 * 用户身份 0：用户   1：管理员
	 */
	private Integer userIdentity;

	/**
	 * 查询类型 1 录著数、2 借阅数、3 归还数、4 转出数、5 转入数、6 交付数、7 修改数
	 */
	private Integer queryContent;
	/**
	 * 统计周期  1 日 2 周 3 月 4 年
	 */
	private Integer stateCycle;
	/**
	 * 图表类型 1 饼状图 2 折线图 3 柱状图
	 */
	private Integer chartType;
	/**
	 * 创建时间
	 */
	private Date createTime;

	private Long createUserId;

	private String createUserName;

}
