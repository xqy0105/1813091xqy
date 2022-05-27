$(function () {
	$("#jqGrid").jqGrid({
		url: baseURL + 'doc_manage/vdocument/list',
		datatype: "json",
		colModel: [
			{label: '操作', name: '', index: 'action', width: 220, sortable:false,formatter: cmgStateFormat},
			{ label: '档案ID', name: 'docId', index: 'doc_id', width: 50, key: true },
			//{ label: 'doc_type_id', name: 'docTypeId', index: 'doc_type_id', width: 80 },
			//{ label: '所属部门', name: 'ownDeptId', index: 'own_dept_id', width: 80 },
			//{ label: '负责人', name: 'ownUserId', index: 'own_user_id', width: 80 },
			//{ label: '创建人', name: 'createUserId', index: 'create_user_id', width: 80 },
			//{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			{ label: '档案形式', name: 'docStyle', index: 'doc_style', width: 80 },
			//{ label: '存放地址', name: 'storePlace', index: 'store_place', width: 80 },
			//{ label: '浏览权限类型', name: 'viewPri', index: 'view_pri', width: 80 },
			{ label: '档案名称', name: 'docName', index: 'doc_name', width: 80 },
			{ label: '档案编号/文号', name: 'docNum', index: 'doc_num', width: 80 },
			//{ label: '是否有金额', name: 'isAmount', index: 'is_amount', width: 80 },
			{ label: '金额', name: 'amount', index: 'amount', width: 80 },
			//{ label: '定期归档模式', name: 'archiveMode', index: 'archive_mode', width: 80 },
			//{ label: '档案数量', name: 'docCount', index: 'doc_count', width: 80 },
			{ label: '档案页数', name: 'docPages', index: 'doc_pages', width: 80 },
			{ label: '密级/标签', name: 'secretDegree', index: 'secret_degree', width: 80 },
			{ label: '起始日期', name: 'beginDate', index: 'begin_date', width: 80 },
			{ label: '终止日期', name: 'endDate', index: 'end_date', width: 80 },
			//{ label: '档案保管期限', name: 'storagPeriod', index: 'storag_period', width: 80 },
			{ label: '合同甲方', name: 'contractA', index: 'contract_a', width: 80 },
			{ label: '合同乙方', name: 'contractB', index: 'contract_b', width: 80 },
			{ label: '合同丙方', name: 'contractC', index: 'contract_c', width: 80 },
			//{ label: '合同丁方', name: 'contractD', index: 'contract_d', width: 80 },
			//{ label: '合同戊方', name: 'contractE', index: 'contract_e', width: 80 },
			//{ label: '查询标签定义，分号分隔', name: 'labels', index: 'labels', width: 80 },
			{ label: '档案数量', name: 'docCurrCount', index: 'doc_curr_count', width: 80 },
			//{ label: '档案状态', name: 'status', index: 'status', width: 80 }
		],
		viewrecords: true,
		height: 'auto',
		rowNum: 10,
		rowList : [10,30,50],
		rownumbers: true,
		rownumWidth: 25,
		autowidth:true,
		multiselect: true,
		sortname: "doc_id",
		sortorder: "desc",
		pager: "#jqGridPager",
		jsonReader : {
			root: "page.list",
			page: "page.currPage",
			total: "page.totalPage",
			records: "page.totalCount"
		},
		prmNames : {
			page:"page",
			rows:"limit",
			order: "order"
		},
		gridComplete:function(){
			//隐藏grid底部滚动条
			$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
		}
	});
	vm.getDeptList();
	vm.getUser();
	vm.getCatalogList();
	vm.getUserList();
	//vm.getDocType();
	//vm.getFmsDocumentList(docId);
	vm.getDoctypeList();
	vm.getDeptsList();

	//vm.getsecretDegreeList();
	//vm.getviewPriList();
	//vm.getarchiveModeList();
	//vm.getisAmountList();
	//vm.getfmsDocumentList();
	//vm.getfmsDocumentList();
});

function cmgStateFormat(cellvalue, options, rowObject) {
	var html="";
	if (rowObject.status==-1){
		html=
			// "<button type=\"button\" class='btn btn-default'  onclick=\"updateSingle(" + options.rowId + ")\">修改</button>" +
			// "<button type=\"button\" class='btn btn-default' onclick=\"delSingle(" + options.rowId + ")\">删除</button>" +

			"<button type=\"button\" class='btn btn-success' style='line-height: 0.6' onclick=\"update(" + rowObject.docId + ")\">修改</button>&nbsp"+
			"<button type=\"button\" class='btn btn-primary' style='line-height: 0.6' onclick=\"docConfirmApply(" + rowObject.docId + ")\">提交确认申请</button>"+
			"<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>";
		// }
		// else html="";
		// "<button type=\"button\" class='btn btn-default'>借阅</button>";
		return html;
		// return "<button class='btn btn-primary ' onclick= \" change (" + rows. cmgId+ ","+cellValue + ") \" >上架</button>" ;
	}
	else if(rowObject.status==0){
		html=
			"<button type=\"button\" class='btn btn-warning' style='line-height: 0.6' onclick=\"docModifyApply(" + rowObject.docId + ")\">提交修改申请</button>"+
			"<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>";
		return html;
	}
	else if(rowObject.status==1){
		html=
			"<button type=\"button\" class='btn btn-danger' style='line-height: 0.6' onclick=\"reCall(" + rowObject.docId + ")\">撤回确认申请</button>"+
			"<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>";
		return html;
	}
	else if(rowObject.status==2){
		html=
			"<button type=\"button\" class='btn btn-danger' style='line-height: 0.6' onclick=\"reCalls(" + rowObject.docId + ")\">撤回修改申请</button>"+
			"<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>";
		return html;
	}
	else{
		html=
			"<button type=\"button\" class='btn btn-success' style='line-height: 0.6' onclick=\"update(" + rowObject.docId + ")\">修改</button>"+
			"<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>";
		return html;
	}

}
var vm = new Vue({
	el:'#rrapp',
	data:{
	    canView:false,
		showLists: false,
		showDetail: false,
		showInfo:false,
        deptspriShow:false,
        userspriShow:false,
		showList: true,
		title: null,
		fmsDocument: {},
		deptList:{},
		deptsList:{},
		user: {},
		fmsDocumentList:{},
		catalogList:{},
		cataselected:'',
		doctypeList:{},
		doctypeselected:'',
		//上传文件
		files: [],
		fmsDocBorrowApply: {},
		docStyleList:[{text:'电子版',value:'电子版'},{text:'纸质',value:'纸质'},{text:'电子和纸质版',value:'电子和纸质版'}],
		docselected:'',
		secretDegreeList:[{text:'A',value:'A'},{text:'B',value:'B'},{text:'C',value:'C'},{text:'D',value:'D'}],
		secretselected:'',
		viewPriList:[{text:'仅本部门可见',value:'仅本部门可见'},{text:'指定部门可见',value:'指定部门可见'},
			{text:'指定人可见',value:'指定人可见'},{text:'所有人可见',value:'所有人可见'}],
		viewselected:'',
		archiveModeList:[{text:'定期归档',value:'定期归档'},{text:'不定期归档',value:'不定期归档'}],
		archiveselected:'',
		isAmountList:[{text:'是',value:'1'},{text:'否',value:'0'}],
		isselected:'',
		selected:'',
		userList:{},
		amountShow:false,
		deptpriShow:false,
		userpriShow:false,
		docShow:false,
		ownuserShow:false,
		createuserShow:false,
		createTimeShow:false,
		deptsString:[],
		usersString:[],
		createselected:'',
        vDocInfoDetail:{},
        fileList:{},
        docName:'',
		//createTime:'',
		//beginDate:'',
		//endDate:''
		q:{
			info: null,
			createTime: null,
			beginDate: null,
			endDate: null
		},
		//即getInfo的参数docId
		curDocId:"",
		uploadpath:'',
		whenUpdate:false,
		//判断是“新增”的话就隐藏文件
		whenAddNew:false
	},
	methods: {
		created:function(){
			$.get(baseURL + "sys/file/getuploadPath", function(r){
				vm.uploadpath = r.uploadpath;
			});
		},
		query: function () {
			vm.reload();
		},
		add: function(){
			this.whenAddNew=true;
			this.whenUpdate=false;
			vm.showList = false;
			vm.showLists = true;
			vm.title = "新增";
			vm.fmsDocument = {};
			vm.selected ='';
			vm.isselected='';
			vm.cataselected='';
			vm.doctypeselected='';
			vm.docselected='';
			vm.secretselected='';
			vm.viewselected='';
			vm.archiveselected='';
			vm.createselected='';
			vm.q.beginDate=null;
			vm.q.endDate=null;
			vm.deptsString=[];
			vm.usersString=[];

		},
		update: function (docId) {
			this.whenUpdate = true;
			//var docId = getSelectedRow();
			if(docId == null){
				return ;
			}
			vm.showList = false;
			vm.showLists = true;
			vm.title = "修改";

			vm.getInfo(docId)
		},
		saveOrUpdate: function (event) {
			var that=this;
			$('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
				var url = vm.fmsDocument.docId == null ? "sys/fmsdocument/save" : "sys/fmsdocument/update";
				//that.whenAddNew=false;
				console.log(that.whenAddNew);
				vm.fmsDocument.ownDeptId=vm.selected;
				vm.fmsDocument.docStyle=vm.docselected;
				vm.fmsDocument.secretDegree=vm.secretselected;
				vm.fmsDocument.docTypeId=vm.doctypeselected;
				vm.fmsDocument.viewPri=vm.viewselected;
				vm.fmsDocument.archiveMode=vm.archiveselected;
				vm.fmsDocument.isAmount=vm.isselected;
				vm.fmsDocument.createTime=vm.q.createTime;
				vm.fmsDocument.beginDate=vm.q.beginDate;
				vm.fmsDocument.endDate=vm.q.endDate;
				//if(vm.fmsDocument.ownUserId==vm.fmsDocument.createUserId  || vm.fmsDocument.createUserId != 1){
				//vm.fmsDocument.ownUserId=vm.user.userId;
				//vm.fmsDocument.createUserId=vm.user.userId;
				//}
				//vm.fmsDocument.docId=vm.fmsDocumentList.docId;
				vm.fmsDocument.usersString=vm.usersString;
				vm.fmsDocument.deptsString=vm.deptsString;
				vm.fmsDocument.catalogId=vm.cataselected;
				vm.fmsDocument.docTypeId=vm.doctypeselected;
				vm.fmsDocument.createUserId=vm.createselected;
				//vm.fmsDocument.endDate=$('#endDate').val();
				if(vm.selected==''){      //数据校验
					layer.msg("所属部门不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.cataselected==''){
					layer.msg("档案大类不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.doctypeselected==''){
					layer.msg("档案类型不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.createselected==''){
					layer.msg("交出人不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.docselected==''){
					layer.msg("档案形式不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.viewselected==''){
					layer.msg("浏览权限不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.viewselected=='指定部门可见'&&vm.deptsString==''){
					layer.msg("请指定浏览权限部门", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.viewselected=='指定人可见'&&vm.usersString==''){
					layer.msg("请指定浏览权限用户", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.fmsDocument.docName==null ||vm.fmsDocument.docName==""){
					layer.msg("档案名称不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				/*if(vm.fmsDocument.docNum==null ||vm.fmsDocument.docNum==''){
					layer.msg("档案编号不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}*/
				if(vm.fmsDocument.docCurrCount==null ||vm.fmsDocument.docCurrCount==''){
					layer.msg("档案当前数量不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.fmsDocument.docCurrCount==null ||vm.fmsDocument.docCurrCount==''){
					layer.msg("档案当前数量不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.isselected.value==''){
					layer.msg("金额不能为空", {icon:0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.isselected=='1'&&(vm.fmsDocument.amount==null ||vm.fmsDocument.amount=='')){
					layer.msg("请填写档案金额", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				// if(vm.archiveselected==''){
				//  layer.msg("请选择归档模式", {icon: 1});
				//  $('#btnSaveOrUpdate').button('reset');
				//  return ;
				// }
				if(vm.fmsDocument.docCount==null ||vm.fmsDocument.docCount==''){
					layer.msg("档案数量不能为空", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				if(vm.secretselected==''||vm.secretselected==null){
					layer.msg("请指定档案密级", {icon: 0});
					$('#btnSaveOrUpdate').button('reset');
					$('#btnSaveOrUpdate').dequeue();
					return ;
				}
				// if(vm.q.beginDate==''){
				//  layer.msg("请选择起始日期", {icon: 1});
				//  $('#btnSaveOrUpdate').button('reset');
				//  return ;
				// }
				// if(vm.q.endDate==''){
				//  layer.msg("请选择终止日期", {icon: 1});
				//  $('#btnSaveOrUpdate').button('reset');
				//  return ;
				// }
				// if(vm.fmsDocument.storagPeriod==''){
				//  layer.msg("档案保管期限不能为空", {icon: 1});
				//  $('#btnSaveOrUpdate').button('reset');
				//  return ;
				// }
				// if(vm.fmsDocument.docCurrCount==''){
				//  layer.msg("档案当前数量不能为空", {icon: 1});
				//  $('#btnSaveOrUpdate').button('reset');
				//  return ;
				// }
				$.ajax({
					type: "POST",
					url: baseURL + url,
					contentType: "application/json",
					data: JSON.stringify(vm.fmsDocument),
					success: function(r){
						if(r.code === 0){
							that.whenAddNew=false;
							layer.msg("操作成功", {icon: 1});
							//vm.reload();
							vm.fmsDocument=r.fmsDocument;
							vm.curDocId=r.fmsDocument.docId;
							//console.log(vm.fmsDocument);
							$('#btnSaveOrUpdate').button('reset');
							$('#btnSaveOrUpdate').dequeue();
						}else{
							layer.alert(r.msg);
							$('#btnSaveOrUpdate').button('reset');
							$('#btnSaveOrUpdate').dequeue();
						}
					}
				});
			});
		},
		apply: function(event) {

		},

		del: function (event) {
			var docIds = getSelectedRows();
			//var userId = vm.user.userId;
			//var data = "docIds"+getSelectedRows()+"&userId"+vm.user.userId;
			if(docIds == null){
				return ;
			}

			var lock = false;
			layer.confirm('确定要删除选中的记录？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				//if(vm.fmsDocument.status==-1){
				if(!lock) {
					lock = true;
					$.ajax({
						type: "POST",
						url: baseURL + "sys/fmsdocument/delete",
						contentType: "application/json",
						datatype: 'json',
						data: JSON.stringify(docIds),// (docIds),
						success: function(r){
							if(r.code == 0){
								layer.msg("已删除可修改的档案", {icon: 1});
								$("#jqGrid").trigger("reloadGrid");
							}else{
								layer.alert(r.msg);
							}
						}
					});

				}
				// }else{
				// layer.msg("该档案不可删除", {icon: 1});
				// $("#jqGrid").trigger("reloadGrid");
				//}
			}, function(){
			});
			//}else{
			// layer.msg("该档案不可删除", {icon: 1});
			// $("#jqGrid").trigger("reloadGrid");

		},
		updateSingle: function(docId){
            var docId = docId;
            if(docId == null){
                return ;
                }
            vm.showList = false;
            vm.showLists = false;
            vm.showDetail=true;
            vm.title = "详情";
			vm.fmsDocument = {};
			vm.selected ='';
			vm.isselected='';
			vm.cataselected='';
			vm.doctypeselected='';
			vm.docselected='';
			vm.secretselected='';
			vm.viewselected='';
			vm.archiveselected='';
			vm.createselected='';
			vm.q.beginDate=null;
			vm.q.endDate=null;
			vm.deptsString=[];
			vm.usersString=[];
            vm.getInfos(docId);

		},
		docConfirmApply:function(docId){
			if(docId== null){
				return ;
			}
			var lock = false;
			layer.confirm('确定要提交申请？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				if(!lock){
					lock = true;
					$.ajax({
						type: "GET",
						url: baseURL + "sys/fmsdocument/docConfirmApply/"+docId,
						success: function(r){
							if(r.code == 0){
								layer.msg("操作成功", {icon: 1});
								$("#jqGrid").trigger("reloadGrid");
							}else{
								layer.alert(r.msg);
							}
						}
					});
				}
			});

		},
		docModifyApply:function(docId){
			if(docId== null){
				return ;
			}
			var lock = false;
			layer.confirm('确定要提交申请？', {
				btn: ['确定','取消'] //按钮
			}, function() {
				if(!lock){
					lock = true;
					$.ajax({
						type: "GET",
						url: baseURL + "sys/fmsdocument/docModifyApply/"+docId,
						success: function(r){
							if(r.code == 0){
								layer.msg("操作成功", {icon: 1});
								$("#jqGrid").trigger("reloadGrid");
							}else{
								layer.alert(r.msg);
							}
						}
					});
				}
			});

		},
		reCall: function(docId){
			if(docId== null){
				return ;
			}
			var lock = false;
			layer.confirm('确定要撤回申请？', {
				btn: ['确定','取消'] //按钮
			}, function() {
				if(!lock){
					lock = true;
					$.ajax({
						type: "GET",
						url: baseURL + "sys/fmsdocument/reCall/"+docId,
						success: function(r){
							if(r.code == 0){
								layer.msg("操作成功", {icon: 1});
								$("#jqGrid").trigger("reloadGrid");
								vm.reload();
							}else{
								layer.alert(r.msg);
							}
						}
					});
				}
			});
		},
		reCalls: function(docId){
			if(docId== null){
				return ;
			}
			var lock = false;
			layer.confirm('确定要撤回申请？', {
				btn: ['确定','取消'] //按钮
			}, function() {
				if(!lock){
					lock = true;
					$.ajax({
						type: "GET",
						url: baseURL + "sys/fmsdocument/reCalls/"+docId,
						success: function(r){
							if(r.code == 0){
								layer.msg("操作成功", {icon: 1});
								$("#jqGrid").trigger("reloadGrid");
								vm.reload();
							}else{
								layer.alert(r.msg);
							}
						}
					});
				}
			});
		},
		getInfo: function(docId){
			var that=this;
			vm.curDocId=docId;
			vm.created();
			$.get(baseURL + "sys/fmsdocument/info/"+docId, function(r){
				that.fmsDocument = r.fmsDocumentList;
				vm.selected=vm.fmsDocument.ownDeptId;
				vm.getCatalogList(vm.fmsDocument.ownDeptId);

				vm.cataselected= vm.fmsDocument.catalogId;
				vm.getDoctypeList(vm.fmsDocument.catalogId);
				vm.doctypeselected=	vm.fmsDocument.docTypeId;
				//that.cataloglist.deptId=that.fmsDocument.ownDeptId;
				//that.doctyoelist.catalogId=that.fmsDocument.catalogId;

				vm.docselected=vm.fmsDocument.docStyle;
				vm.secretselected=vm.fmsDocument.secretDegree;
				vm.viewselected=vm.fmsDocument.viewPri;
				vm.archiveselected=vm.fmsDocument.archiveMode;
				vm.isselected=vm.fmsDocument.isAmount;
				if(vm.fmsDocument.isAmount === 1){
					that.amountShow = true;
				}
				else{that.amountShow = false;}
				 vm.deptsString = vm.fmsDocument.deptsString;
				 that.deptpriShow = (vm.fmsDocument.viewPri === "指定部门可见");
//				if(vm.fmsDocument.viewPri === "指定部门可见"){
//
//
//				}
                 vm.usersString = vm.fmsDocument.usersString;
                 that.userpriShow = (vm.fmsDocument.viewPri === "指定人可见");
				//if(vm.fmsDocument.viewPri === "指定人可见"){
				//  that.userpriShow = true;
				//  vm.usersString = vm.fmsDocument.usersString;
				//}
				vm.q.createTime=vm.fmsDocument.createTime;
				vm.q.beginDate=vm.fmsDocument.beginDate;
				vm.q.endDate=vm.fmsDocument.endDate;
				vm.createselected=vm.fmsDocument.createUserId;
				//vm.user.userId=vm.fmsDocument.ownUserId;
				//vm.user.userId=vm.fmsDocument.createUserId;
				that.fmsDocumentList.docId=that.fmsDocument.docId;
				vm.getFmsDocumentList(docId);
				/**vm.fmsDocument.docFileList 文件列表：
				 下载链接地址：baseURL+sys/file/fileDownload/{docfileId}
				 文件名称：fileName
				 */
				//vm.fmsDocumentList.docId=vm.fmsDocument.docId;

			});
		},
		getInfos: function(docId){
            vm.created();
            $.get(baseURL + "doc_manage/vdocinfodetail/info/"+docId, function(r){
                vm.vDocInfoDetail = r.vDocInfoDetail;
				vm.deptspriShow = (vm.vDocInfoDetail.viewPri === "指定部门可见");
				vm.userspriShow = (vm.vDocInfoDetail.viewPri === "指定人可见");
                vm.getFmsDocumentLists(docId);
            });
		},
        getFmsDocumentLists: function(docId){
            $.get(baseURL + "sys/fmsdocumentfile/getFileList?docId=" +docId, function(r){

                vm.fileList = r.page;
                vm.canView=r.canView;
                console.log(vm.fileList);
            });

        },
		reload: function (event) {
			vm.showList = true;
            vm.showLists = false;
            vm.showInfo = false;
            vm.showDetail=false;
			vm.files=[];
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				/*   postData:{
                   'info': vm.q.info,
                   'createTime': vm.q.createTime,
                   'beginDate': vm.q.beginDate,
                   'endDate': vm.q.endDate
                   },*/
				page:page
			}).trigger("reloadGrid");
			$('#btnSaveOrUpdate').button('reset');
			$('#btnSaveOrUpdate').dequeue();
		},
		getDeptList: function(){
			$.get(baseURL + "sys/dept/selectuserdep",function(res){
				vm.deptList = res.deptList;
				//console.log(vm.deptList);
			});
		},
		onSelectedDept(event,item) {
//打印出绑定的对象
			//           console.log(this.selected);
			vm.getCatalogList(this.selected);
			vm.cataselected=vm.catalogList.catalogId;
		},
		getUser: function () {
			$.get(baseURL +"sys/user/info?_" + $.now(),function(r){
				// $.getJSON("sys/user/info?_" + $.now(), function (r) {
				vm.user = r.user;
			});
		},
		getDeptsList: function(){
			$.get(baseURL + "sys/dept/selectuseralldep",function(res){
				vm.deptsList = res.deptsList;
				//console.log(vm.deptList);
			});
		},
		getCatalogList: function(deptId){
			var res={deptId:deptId};
			var code;
			$.get(baseURL + "doc_manage/doccatalog/select",res,function(res,code){
				vm.catalogList = res.catalogList;
				//vm.cataselected=vm.catalogList.catalogId;
			});
		},
		getUserList: function(){
			$.get(baseURL + "sys/user/select",function(res){
				vm.userList = res.userList;
				// console.log(vm.deptList);
			});
		},
		onSelectedCatalog(event,item) {
//打印出绑定的对象
			//           console.log(this.selected);
			vm.getDoctypeList(this.cataselected);
			//vm.doctypeselected=vm.doctypeList.docTypeId;
		},
		getDoctypeList: function(catalogId){
			var res={catalogId:catalogId};
			var code;
			$.get(baseURL + "doc_manage/doctype/select",res,function(res,code){
				vm.doctypeList = res.doctypeList;
				//vm.doctypeselected=vm.doctypeList.docTypeId;
			});
		},
		/* onSelectedCatalog(event,item) {
 //打印出绑定的对象
  //           console.log(this.selected);
             vm.getDocType(this.cataselected);
             vm.doctypeselected=vm.fmsDocument.docTypeId;
         },

         getDocType: function (catalogId) {
              var res={catalogId:catalogId};
              var code;
              $.get(baseURL + "doc_manage/doctype/info/"+docTypeId,res,function(res,code){
                  vm.catalogList = res.catalogList;
                  vm.cataselected=vm.docType.catalogId;
              });

         }*/
		/* getfmsDocumentList: function(){
           $.get(baseURL + "sys/fmsDocument/select",function(res){
           vm.fmsDocumentList = res.fmsDocumentList;
           });
         },
        */
		getFmsDocumentList: function(docId){
			var that=this;
			$.get(baseURL + "sys/fmsdocument/info/" +docId, function(r){

				that.fmsDocumentList = r.fmsDocumentList;
				that.fmsDocument.docFileList=r.fmsDocumentList.docFileList;
				console.log(vm.fmsDocumentList);
				// console.log(vm.fmsDocument.docFileList);
			});

		},
		File(){
			// var fileList=[];
			var that=this;
			for(var ff=0;ff<$("#file")[0].files.length;ff++){
				if($("#file")[0].files[ff].size/(1024*1024)>100){
					alert("太大");
					return;
				}
				that.uploadFile($("#file")[0].files[ff]);
			}
			//console.log($("#file")[0].files)
		},

		//上传
		//只有类型为File的才可以，文件夹不行
		uploadFile: function (file) {
			var that=this;
			console.log(file);
			var item = {
				name: file.name,
				uploadPercentage: 0
			};
			this.files.push(item);
			var fd = new FormData();
			fd.append('file', file);
			fd.append('document', that.fmsDocument.docId);
			var xhr = new XMLHttpRequest();
			xhr.open('POST', baseURL +"sys/file/fileUpload", true);
			xhr.upload.addEventListener('progress', function (e) {
				item.uploadPercentage = Math.round((e.loaded * 100) / e.total);
				// console.log(item.uploadPercentage);
				if(item.uploadPercentage===100) {
					window.setTimeout(function () {
						that.files.pop();
						vm.getInfo(vm.curDocId);
						layer.msg('文件《'+item.name+'》已上传',{shift:6,offset: 'rb'});

					}, 1000);

				}

			}, false);

			xhr.send(fd);
			//调用getInfo以更新fmsDocument.docFileList，但有延迟，在有些情况下上传1、2个文件没反应，但3个在上面的文件列表里就能显示
			//可以在这个函数里获得上传的文件（类似docFileList中的一个）的对象吗？

			// vm.getFmsDocumentList(vm.curDocId);
			//删除






			// that.getFmsDocumentList(vm.curDocId);
		},
		onDrag: function (e) {
			console.log(e);
			e.stopPropagation();
			e.preventDefault();
		},
		onDrop: function (e) {
			console.log(e);
			e.stopPropagation();
			e.preventDefault();
			var dt = e.dataTransfer;
			for (var i = 0; i !== dt.files.length; i++) {
				this.uploadFile(dt.files[i]);
			}
		},
		//是否有金额的动态设置
		changeisamount:function (value){

			vm.amountShow=(value.target.value=="1");
		},
		changepri: function (value){
			vm.deptpriShow=(value.target.value=="指定部门可见");
			vm.userpriShow=(value.target.value=="指定人可见");
		},

		//点击下载文件
		//传入fileList中的一个
		clickToDownload:function (file){
			var path=file.fileLocation;
			// var projectname=getRootPath();
			path=path.replace(vm.uploadpath,projectName+"/files/");
			console.log(path);
			var name=file.fileName;
			console.log(name);
			if(name.endsWith(".pdf")||name.endsWith(".PDF")){
				//pdf预览
				//跳转
				// var url="pdfPreview.html?name="+name+"&url="+path;
				// window.open(url);
				window.open(projectName+"/statics/plugins/pdfjs/web/viewer.html?file="+path);
				//window.open("/docmanagement/statics/plugins/pdfjs/web/viewer.html?name="+name+"&url="+path);
			}
			else if(name.endsWith(".png")||name.endsWith(".jpg")){
				window.open(path);
			}
			else {
				const xhr = new XMLHttpRequest();
				xhr.open('get', path);
				xhr.responseType = 'blob';
				xhr.send();
				xhr.onload = function () {
					if (this.status === 200 || this.status === 304) {
						// 如果是IE10及以上，不支持download属性，采用msSaveOrOpenBlob方法，但是IE10以下也不支持msSaveOrOpenBlob
						if ('msSaveOrOpenBlob' in navigator) {
							navigator.msSaveOrOpenBlob(this.response, name);
							return;
						}
						// const blob = new Blob([this.response], { type: xhr.getResponseHeader('Content-Type') });
						// const url = URL.createObjectURL(blob);
						const url = URL.createObjectURL(this.response);
						const a = document.createElement('a');
						a.style.display = 'none';
						a.href = url;
						a.download = name;
						document.body.appendChild(a);
						a.click();
						document.body.removeChild(a);
						URL.revokeObjectURL(url);
					}
				};
			}
		},
		//删除图标
		//传入参数是index
		deleteOrNot:function (fileIndex) {
			var fileName=vm.fmsDocument.docFileList[fileIndex].fileName;
			//vm.docfileId = vm.fmsDocument.docFileList[fileIndex].docfileId;
			//vm.userId = vm.user.userId;
			console.log(fileName);
			//console.log(docfileId);
			//console.log(userId);
			this.curFileName=fileName;
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "",
				// area: 'auto',
				shadeClose: false,
				content: jQuery("#deleteFileOrNot"),
				btn: ['是', '否'],
				btnAlign: 'c',
				btn1: function (data,index) {
					//仅临时删除用于效果展示


					// var data = {
					//  docfileId:vm.fmsDocument.docFileList[fileIndex].docfileId,
					//  userId:vm.user.userId
					// }
					var data = "docfileId=" +vm.fmsDocument.docFileList[fileIndex].docfileId + "&userId=" + vm.user.userId;
					$.ajax({
						type: "POST",
						url: baseURL+"sys/file/deleteSingle",
						data: data,
						success: function (result) {
							if (result.code == 0) {
								layer.close(layer.index);
								vm.fmsDocument.docFileList.splice(fileIndex,1);

								layer.alert('修改成功');
							} else {
								layer.alert(result.msg);
							}
						},
						error:function(event){
							layer.close(layer.index);
							console.log(event);
						}
					});
				}
			});
		}

	},

	mounted: function () {
		var dropbox = document.querySelector('.dropbox');
		dropbox.addEventListener('dragenter', this.onDrag, false);
		dropbox.addEventListener('dragover', this.onDrag, false);
		dropbox.addEventListener('drop', this.onDrop, false);
		window.docConfirmApply=this.docConfirmApply;
		window.docModifyApply=this.docModifyApply;
		window.reCall=this.reCall;
		window.reCalls=this.reCalls;
		window.update=this.update;
        window.updateSingle = this.updateSingle;
	}
});
vm.created();
layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
		elem: '#createTime', //指定元素
		type: 'datetime',
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.createTime=value;
			//console.log(date);

		}

	});
	laydate.render({
		elem: '#beginDate', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.beginDate=value;
			//console.log(date);

		}
	});
	laydate.render({
		elem: '#endDate', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.endDate=value;
			//console.log(date);

		}
	});
});
