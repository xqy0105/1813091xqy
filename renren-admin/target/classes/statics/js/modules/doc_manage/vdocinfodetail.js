
$(function () {

	vm.getottypelist();
	vm.getCatalist();
	var info=location.search;
	if (info.length>0) {
		item = info.split("=");
		var itemId = item[1];
		console.log(itemId);
		if(itemId!="") {
			//从ottotype获取参数值
			//console.log(vm.ottypelist[itemId].paramValue);
			vm.q.overtimetype = vm.ottypelist[itemId].paramValue;
			vm.reload();
		}
	}

	$("#jqGrid").jqGrid({
		url: baseURL + 'doc_manage/vdocinfodetail/list?valid=true',
		postData:{
			overtimetype:vm.q.overtimetype,
		},
		datatype: "json",
		colModel: [
			{label: '', name: '', index: 'action', width: 80, sortable:false,formatter: cmgStateFormat},
			{ label: '档案ID', name: 'docId', index: 'doc_id', width: 50, key: true },
			{ label: '合同甲方', name: 'contractA', index: 'contract_a', width: 80 },
			{ label: '档案大类名称', name: 'catalogName', index: 'catalog_name', width: 80 },
			{ label: '档案类型名称', name: 'docTypeName', index: 'doc_type_name', width: 80 },
			{ label: '起始日期', name: 'beginDate', index: 'begin_date', width: 80 },
			{ label: '终止日期', name: 'endDate', index: 'end_date', width: 80 },
			{ label: '档案名称', name: 'docName', index: 'doc_name', width: 80 },
			{ label: '档案在库数量', name: 'docCurrCount', index: 'doc_curr_count', width: 80 },
			{ label: '档案编号/文号', name: 'docNum', index: 'doc_num', width: 80 },
			// { label: '档案页数', name: 'docPages', index: 'doc_pages', width: 80 },
			{ label: '档案形式', name: 'docStyle', index: 'doc_style', width: 80 },
			// { label: 'doc_type_id', name: 'docTypeId', index: 'doc_type_id', width: 80 },
			// { label: '是否有金额', name: 'isAmount', index: 'is_amount', width: 80 },
			{ label: '金额', name: 'amountInfo', index: 'amountinfo', width: 80 },
			// { label: '密级/标签', name: 'secretDegree', index: 'secret_degree', width: 80 },
			// { label: '档案保管期限', name: 'storagPeriod', index: 'storag_period', width: 80 },
			{ label: '合同乙方', name: 'contractB', index: 'contract_b', width: 80 },
			{ label: '合同丙方', name: 'contractC', index: 'contract_c', width: 80 }
			// ,
			// { label: '合同丁方', name: 'contractD', index: 'contract_d', width: 80 },
			// { label: '合同戊方', name: 'contractE', index: 'contract_e', width: 80 }
		],
		viewrecords: true,
		height: 'auto',
		rowNum: 10,
		rowList : [10,30,50],
		rownumbers: true,
		rownumWidth: 25,
		autowidth:true,
		multiselect: false,
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



});

function cmgStateFormat(cellvalue, options, rowObject) {
	const html =
		"<button type=\"button\" class='btn btn-warning'  onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>" ;
	// "<button type=\"button\" class='btn btn-default' onclick=\"delSingle(" + rowObject.docId + ")\">删除</button>" +
	// "<button type=\"button\" class='btn btn-warning' style='line-height: 0.6' onclick=\"borrowSingle(" + rowObject.docId + ")\">借阅</button>";
	// "<button type=\"button\" class='btn btn-default'>借阅</button>";
	return html;
	// return "<button class='btn btn-primary ' οnclick= \" change (" + rows. cmgId+ ","+cellValue + ") \" >上架</button>" ;
}
var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		showBorrow:false,
		showDetail:false,
        deptpriShow:false,
        userpriShow:false,
		title: null,
		vDocInfoDetail: {},
		queryparmstr:'',
		q:{
			overtimetype:null,
			info: null,
			beginDatefrom: null,
			beginDateto: null,
			endDatefrom: null,
			endDateto: null,
			createDatefrom: null,
			createDateto: null,
			amountto:null,
			amountfrom:null,
			docTypeName: null,
			catalogName: null,
			beginDate: null,
			endDate: null,
			docName: null,
			contractA: null,

			// 申请的数量
			borrownum: null,
		},title: null,
		document: {},
		docBorrowApply: {},
		canView:false,//是否可以浏览文件
		fileList:{},
		ottypelist:{},
		catalogList:{},
		doctypeList:{},
		uploadpath:''
	},
	methods: {
		created:function(){
			$.get(baseURL + "sys/file/getuploadPath", function(r){
				vm.uploadpath = r.uploadpath;
			});
		},
		query: function () {
			vm.refresh();
		},
		cleardata: function () {

		},
		update: function (event) {
			var docId = event;
			if(docId == null){
				return ;
			}
			vm.showList = false;
			vm.showBorrow=false;
			vm.showDetail=true;
			vm.title = "详情";

			vm.getInfo(docId)
		},
		saveOrUpdate: function (event) {
			$('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
				var url = vm.vDocInfoDetail.docId == null ? "doc_manage/vdocinfodetail/save" : "doc_manage/vdocinfodetail/update";
				$.ajax({
					type: "POST",
					url: baseURL + url,
					contentType: "application/json",
					data: JSON.stringify(vm.vDocInfoDetail),
					success: function(r){
						if(r.code === 0){
							layer.msg("操作成功", {icon: 1});
							vm.reload();
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
		}
		,
		//获取超期类型
		getottypelist: function(){
			$.ajaxSettings.async = false;
			$.get(baseURL + "sys/config/listbykey?params=outdate",function(res){
				vm.ottypelist = res.configs;
			});
			$.ajaxSettings.async = true;

		},
		//获取档案大类
		getCatalist: function(){
			$.ajaxSettings.async = false;
			$.get(baseURL + "doc_manage/doccatalog/selectByName",function(res){
				vm.catalogList = res.catalogList;
			});
			$.ajaxSettings.async = true;

		},
		//doc_manage/doctype/selectByName
		//获取档案类型
		getDoctypeList: function(){
			$.ajaxSettings.async = false;
			$.get(baseURL + "doc_manage/doctype/selectByName?catalogName="+vm.q.catalogName,function(res){
				vm.doctypeList = res.doctypeList;
			});
			$.ajaxSettings.async = true;

		},
		// 查询栏中的 借阅按钮
		clickBorrow: function (docId) {

			vm.showList=false;
			vm.showBorrow=true;
			vm.showDetail=false;
			vm.title = "借阅";
			console.log("docID: ", docId);
			vm.docBorrowApply['docId'] = docId;
			vm.docBorrowApply['applytime'] = new Date().toISOString().slice(0, 19).replace('T', ' ');
			// vm.docBorrowApply['borrowerId'] =
			// vm.do
		},
		//借阅确定按钮，传入docIds参数
		borrowApply: function(docId){
			console.log("docBorrowApply Data: ", JSON.stringify(vm.docBorrowApply));
			$('#btnBorrowApply').button('loading').delay(1000).queue(function() {
				var url = "doc_manage/docborrowapply/apply" ;
				$.ajax({
					type: "POST",
					url: baseURL + url,
					contentType: "application/json",
					data: JSON.stringify(vm.docBorrowApply),
					success: function(r){
						if(r.code === 0){
							layer.msg("操作成功", {icon: 1});
							vm.reload();
							$('#btnBorrowApply').button('reset');
							$('#btnBorrowApply').dequeue();
						}else{
							layer.alert(r.msg);
							$('#btnBorrowApply').button('reset');
							$('#btnBorrowApply').dequeue();
						}
					}
				});
			});
			// console.log("docNum: "+vm.docBorrowApply.borrownum);
		},

		getInfo: function(docId){
			vm.created();
			$.get(baseURL + "doc_manage/vdocinfodetail/info/"+docId, function(r){
				vm.vDocInfoDetail = r.vDocInfoDetail;
				vm.deptpriShow = (vm.vDocInfoDetail.viewPri === "指定部门可见");
				vm.userpriShow = (vm.vDocInfoDetail.viewPri === "指定人可见");
				vm.getFmsDocumentList(docId);
			});
		},

		getFmsDocumentList: function(docId){
			$.get(baseURL + "sys/fmsdocumentfile/getFileList?docId=" +docId, function(r){

				vm.fileList = r.page;
				vm.canView=r.canView;
			});

		},
		exporttoexcel: function () {
			layer.confirm('是否确认导出查询数据项?', {
				btn: ['确定','取消'] //按钮
			}, function(){
				var url=baseURL + "doc_manage/vdocinfodetail/exportdoccheck";
				vm.getQueryPara();
				url=url+"?"+vm.queryparmstr;
				window.location.href=url;
				layer.close(layer.index);
			}, function(){
			});


		},
		getQueryPara:function (){
			vm.queryparmstr='&testp=testp';
			if(vm.q.info!=null &&vm.q.info!='')
				vm.queryparmstr=vm.queryparmstr+'&info='+vm.q.info;
			if(vm.q.beginDatefrom!=null &&vm.q.beginDatefrom!='')
				vm.queryparmstr=vm.queryparmstr+'&beginDatefrom='+vm.q.beginDatefrom;
			if(vm.q.beginDateto!=null &&vm.q.beginDateto!='')
				vm.queryparmstr=vm.queryparmstr+'&beginDateto='+vm.q.beginDateto;
			if(vm.q.endDatefrom!=null &&vm.q.endDatefrom!='')
				vm.queryparmstr=vm.queryparmstr+'&endDatefrom='+vm.q.endDatefrom;
			if(vm.q.endDateto!=null &&vm.q.endDateto!='')
				vm.queryparmstr=vm.queryparmstr+'&endDateto='+vm.q.endDateto;
			if(vm.q.createDatefrom!=null &&vm.q.createDatefrom!='')
				vm.queryparmstr=vm.queryparmstr+'&createDatefrom='+vm.q.createDatefrom;
			if(vm.q.createDateto!=null &&vm.q.createDateto!='')
				vm.queryparmstr=vm.queryparmstr+'&createDateto='+vm.q.createDateto;
			if(vm.q.overtimetype!=null &&vm.q.overtimetype!='')
				vm.queryparmstr=vm.queryparmstr+'&overtimetype='+vm.q.overtimetype;
		}
		,
		exporttoexceldetail: function () {
			layer.confirm('是否确认导出查询明细数据项?', {
				btn: ['确定','取消'] //按钮
			}, function(){
				var url=baseURL + "doc_manage/vdocinfodetail/exportdetail";
				vm.getQueryPara();
				url=url+"?"+vm.queryparmstr;
				window.location.href=url;
				layer.close(layer.index);
			}, function(){
			});


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

		}
		,
		refresh: function (event) {
			vm.showList = true;
			vm.showBorrow=false;
			vm.showDetail=false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{
					'overtimetype':vm.q.overtimetype,
					'info': vm.q.info,
					'beginDatefrom': vm.q.beginDatefrom,
					'beginDateto': vm.q.beginDateto,
					'endDatefrom': vm.q.endDatefrom,
					'endDateto': vm.q.endDateto,
					'createDatefrom': vm.q.createDatefrom,
					'createDateto': vm.q.createDateto
				},
				page:1
			}).trigger("reloadGrid");
		},
		reload: function (event) {
			vm.showList = true;
			vm.showBorrow=false;
			vm.showDetail=false;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
				postData:{
					'overtimetype':vm.q.overtimetype,
					'info': vm.q.info,
					'beginDatefrom': vm.q.beginDatefrom,
					'beginDateto': vm.q.beginDateto,
					'endDatefrom': vm.q.endDatefrom,
					'endDateto': vm.q.endDateto,
					'createDatefrom': vm.q.createDatefrom,
					'createDateto': vm.q.createDateto,
					'amountto':vm.q.amountto,
					'amountfrom':vm.q.amountfrom,
					'docTypeName': vm.q.docTypeName,
					'catalogName': vm.q.catalogName,
					'docName': vm.q.docName
				},
				page:page
			}).trigger("reloadGrid")
		},
		selectcatalog:function(value){
			vm.q.catalogName=value;
			vm.getDoctypeList();

		}
	},//外部调用
	mounted() {
		window.updateSingle = this.update;
		// window.delSingle=this.del;
		window.borrowSingle=this.clickBorrow;

	}
});

layui.use('laydate', function(){
	var laydate = layui.laydate;
	//执行一个laydate实例
	laydate.render({
		elem: '#beginDatefrom', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.beginDatefrom=value;
			//console.log(date);

		}

	});
	laydate.render({
		elem: '#beginDateto', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.beginDateto=value;
			//console.log(date);

		}
	});
	laydate.render({
		elem: '#endDateto', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.endDateto=value;
			//console.log(date);

		}
	});
	laydate.render({
		elem: '#endDatefrom', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.q.endDatefrom=value;
			//console.log(date);

		}
	});

	laydate.render({
		elem: '#expirationDate', //指定元素
		done:function(value,date){
			//value, date, endDate点击日期、清空、现在、确定均会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
			vm.docBorrowApply.expirationDate=value;
			//console.log(date);

		}
	});
});

