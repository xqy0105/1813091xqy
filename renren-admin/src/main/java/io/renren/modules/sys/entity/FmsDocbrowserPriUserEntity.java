package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户档案浏览权限表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:17
 */
@Data
@TableName("fms_docbrowser_pri_user")
public class FmsDocbrowserPriUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * user_id
	 */
	//、、@TableId
	private Long userId;
	/**
	 * doc_id
	 */
	private Long docId;
	/**
	 * 权限类型;浏览
	 */
	private String privilegeType;

}
