package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件浏览日志
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@Data
@TableName("fms_docfile_view_log")
public class FmsDocfileViewLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * docfile_id
	 */
	@TableId
	private Long docfileId;
	/**
	 * user_id
	 */
	private Long userId;
	/**
	 * 查看时间
	 */
	private Date browseTime;
	/**
	 * 备注
	 */
	private String comm;

}
