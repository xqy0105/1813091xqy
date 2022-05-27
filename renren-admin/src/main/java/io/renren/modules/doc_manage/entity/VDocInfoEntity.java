package io.renren.modules.doc_manage.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * VIEW
 * 
 * @author mingming
 * @email liumingming@nankai.edu.cn
 * @date 2021-08-15 09:42:33
 */
@Data
@TableName("v_doc_info_detail")
public class VDocInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * doc_id
	 */
	@TableId
	private Long docId;
	/**
	 * 合同甲方
	 */
	@Excel(name="合同甲方"  )
	private String contractA;
	/**
	 * 档案大类名称
	 */
	@Excel(name="档案大类名称"  )
	private String catalogName;
	/**
	 * 档案类型名称
	 */
	@Excel(name="档案类型名称"  )
	private String docTypeName;
	/**
	 * 起始日期
	 */
	@Excel(name="起始日期" ,exportFormat = "yyyy-MM-dd" )
	private Date beginDate;
	/**
	 * 终止日期
	 */
	@Excel(name="终止日期" ,exportFormat = "yyyy-MM-dd"  )
	private Date endDate;
	/**
	 * 档案名称
	 */
	@Excel(name="档案名称"  )
	private String docName;
	/**
	 * 档案在库数量
	 */
	@Excel(name="档案在库数量"  )
	private Integer docCurrCount;
	/**
	 * 档案数量
	 */
	@Excel(name="档案数量"  )
	private Integer docCount;
	/**
	 * 所属部门
	 */
	@Excel(name="所属部门",needMerge =true)
	private String ownDeptName;
	/**
	 * 浏览权限类型
	 */
	@Excel(name="浏览权限类型",needMerge =true)
	private String viewPri;
	/**
	 * 交接状态
	 */
	//@Excel(name="浏览权限类型",needMerge =true)
	private String tranStatus;
	/**
	 * 定期归档模式
	 */
	@Excel(name="定期归档模式",needMerge =true)
	private String archiveMode;
	/**
	 * 存放地址
	 */
	@Excel(name="存放地址",needMerge =true)
	private String storePlace;
	/**
	 * 交出人
	 */
	@Excel(name="交出人",needMerge =true)
	private String createUsername;
	/**
	 * 起始日期
	 */
	/**
	 * 档案编号/文号
	 */
	@Excel(name="档案编号/文号"  )
	private String docNum;
	/**
	 * 档案页数
	 */
	@Excel(name="档案页数"  )
	private Integer docPages;
	/**
	 * 档案形式
	 */
	@Excel(name="档案形式"  )
	private String docStyle;
	/**
	 * doc_type_id
	 */
	//@Excel(name="档案编号/文号"  )
	private Long docTypeId;
	/**
	 * 是否有金额
	 */
	//@Excel(name="档案编号/文号"  )
	private Integer isAmount;
	/**
	 * 金额
	 */
	//@Excel(name="档案编号/文号"  )
	private Float amount;
	/**
	 * 档案金额
	 */
	@TableField(exist=false)
	@Excel(name="档案金额"  )
	private String amountInfo;

	public String getAmountInfo() {
		if(isAmount!=null &&isAmount!=0){
			//			return "是("+formatamount(amount)+")";
			return ""+formatamount(amount);
		}
		else
			return "否";
	}
public String formatamount(float amount){
	BigDecimal conv= new BigDecimal(amount);
	return conv.toString();
//		if(amount<10000){
//			return amount+"";
//		}
//		else if(amount<100000000){
//			return amount/10000+"万";
//		}
//		else{
//			return amount/100000000+"亿";
//		}
}


	/**
	 * 密级/标签
	 */
	@Excel(name="密级/标签"  )
	private String secretDegree;
	/**
	 * 档案保管期限
	 */
	@Excel(name="档案保管期限"  )
	private String storagPeriod;
	/**
	 * 合同乙方
	 */
	@Excel(name="合同乙方"  )
	private String contractB;
	/**
	 * 合同丙方
	 */
	@Excel(name="合同丙方"  )
	private String contractC;
	/**
	 * 合同丁方
	 */
	@Excel(name="合同丁方"  )
	private String contractD;
	/**
	 * 合同戊方
	 */
	@Excel(name="合同戊方"  )
	private String contractE;
	/**
	 * 查询标签定义，分号分隔
	 */
	//@Excel(name="档案编号/文号")
	private String labels;
//	/**
//	 *
//	 */
//	@TableField(exist=false)
//	private List<VDocInfoDetailInfoEntity> detail;
//	/**
//	 *
//	 */
//	private Date inTime;
//	/**
//	 *
//	 */
//	private String fromuser;
//	/**
//	 *
//	 */
//	private String touser;
//	/**
//	 *
//	 */
//	private Integer borrownum;
	/**
	 * 档案状态-1：新录入/修改申请已通过 可修改 1：录入待确认，不可修改，可取消 0：交付人已确认，不可修改，可申请修改
	 */
	private int status;

	@TableField(exist=false)
	private String[] deptsString;

	@TableField(exist=false)
	private String[] usersString;
}
