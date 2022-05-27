package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案大类
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:18
 */
@Data
@TableName("fms_doc_catalog")
public class FmsDocCatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * catalog_id
	 */
	@TableId
	private Long catalogId;
	/**
	 * dept_id
	 */
	private Long deptId;
	/**
	 * 档案大类名称
	 */
	private String datalogName;

}
