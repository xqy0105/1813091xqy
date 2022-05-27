package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 档案电子文件
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2021-08-06 16:16:16
 */
@Data
@TableName("fms_document_file")
public class FmsDocumentFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * docfile_id
	 */
	@TableId
	private Long docfileId;
	/**
	 * doc_id
	 */
	private Long docId;
	/**
	 * 保存位置
	 */
	private String fileLocation;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private Float fileSize;
	/**
	 * 浏览次数
	 */
	private Long browseTimes;
	/**
	 * 备注
	 */
	private String comm;
	/**
	 * 上传时间
	 */
	private Date upload_time;
	/**
	 * 删除标记
	 */
	private Short delFlag;


}
