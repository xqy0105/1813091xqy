package io.renren.modules.doc_manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 档案大类
 * 
 * @author liumingming
 * @email liumingming@nankai.edu.cm
 * @date 2021-08-07 10:20:20
 */
@Data
@TableName("fms_doc_catalog")
public class DocCatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * catalog_id
	 */
	@TableId
	private Long catalogId;
	/**
	 * dept_id
	 */
	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;
	/**
	 * dept_name
	 */
	@TableField(exist=false)
	private String deptName;
	/**
	 * 档案大类名称
	 */
	@NotNull(message="档案大类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String catalogName;
	/**
	 * 备注
	 */
	private String comm;
	/**
	 * 具有子类型
	 */
	@TableField(exist=false)
	private int hasSub;
	/**
	 * 用于批量增加
	 */
	@TableField(exist=false)
	private String[] deptsString;
}
