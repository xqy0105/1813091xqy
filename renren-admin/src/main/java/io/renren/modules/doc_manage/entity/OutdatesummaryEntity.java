package io.renren.modules.doc_manage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-13 21:15:09
 */
@Data
@TableName("fms_outdatesummary")
public class OutdatesummaryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String summaryname;
	/**
	 * 
	 */
	private Integer totalSize;
	/**
	 * 
	 */
	private BigDecimal percent;
	/**
	 * 
	 */
	private Integer totalUnitsSold;

}
