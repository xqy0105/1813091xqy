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
@TableName("v_doc_info_detail")
public class VDocInfoDetailInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_id
	 */
	@TableId
	private Long docId;
	/**
	 * 入库时间
	 */
	@Excel(name="入库时间",exportFormat = "yyyy-MM-dd hh:mm:ss")
	private Date inTime;
	/**
	 * 出库时间
	 */
	@Excel(name="出库时间",exportFormat = "yyyy-MM-dd hh:mm:ss")
	private Date outTime;

	/**
	 * from用户
	 */
	@Excel(name="from用户")
	private String fromuser;
	/**
	 * to用户
	 */
	@Excel(name="to用户")
	private String touser;
	/**
	 * 数量
	 */
	@Excel(name="数量")
	private Integer borrownum;
	


}
