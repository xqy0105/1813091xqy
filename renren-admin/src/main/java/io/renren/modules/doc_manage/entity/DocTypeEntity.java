package io.renren.modules.doc_manage.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 档案类型
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-11 21:03:22
 */
@Data
@TableName("fms_doc_type")
public class DocTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_type_id
	 */
	@TableId
	private Long docTypeId;
	/**
	 * catalog
	 */

	@NotNull(message="档案大类", groups = {AddGroup.class, UpdateGroup.class})
	private Long catalogId;
	/**
	 * 档案类型名称
	 */
	@Excel(name="档案类型名称")
	@NotNull(message="档案类型名称", groups = {AddGroup.class, UpdateGroup.class})
	private String docTypeName;
	/**
	 * 
	 */
	private Integer delFlag;
	/**
	 * catalog_name
	 */
	@TableField(exist=false)
	@Excel(name="档案大类")
	private String catalogName;
	/**
	 * 档案类型名称
	 */
	@TableField(exist=false)
	@Excel(name="部门名称")
	private String deptName;
	/**
	 *
	 */
	@TableField(exist=false)
	private Integer deptId;

}
