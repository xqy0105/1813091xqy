$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/document/trans_in_list',
        datatype: "json",
        colModel: [
            {label: '', name: '操作', index: 'action', width: 50, sortable:false,formatter: cmgStateFormat},
            { label: '档案交接ID', name: 'docTransLogId', index: 'doc_trans_log_id', width: 50, key: true },
            { label: '档案ID', name: 'docId', index: 'doc_id', width: 50, key: false },
			{ label: '档案大类', name: 'catalogName', index: 'catalog_name', width: 80},
			{ label: '档案类型', name: 'docTypeName', index: 'doc_type_name', width: 80},
			{ label: '档案名称', name: 'docName', index: 'doc_name', width: 80 },
			{ label: '起始日期', name: 'beginDate', index: 'begin_date', width: 80 },
			{ label: '终止日期', name: 'endDate', index: 'end_date', width: 80 },
			// { label: '档案保管期限', name: 'storagPeriod', index: 'storag_period', width: 80 },
			{ label: '合同甲方', name: 'contractA', index: 'contract_a', width: 80 },
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: false,
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
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "visible" });
        },
    });
    vm.getDeptList();
    vm.getUser();
    vm.getCatalogList();
    vm.getUserList();
    //vm.getDocType();
    //vm.getFmsDocumentList(docId);
    vm.getDoctypeList();
    vm.getDeptsList();
});

 function cmgStateFormat(cellvalue, options, rowObject) {
     const html =

         "<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docTransLogId + ","+ rowObject.docId + ")\">详情</button>";

     return html;
 }


var vm = new Vue({
	el:'#rrapp',
	data:{
	    canView:false,
		showDetail: false,
        deptpriShow:false,
        userpriShow:false,
	    q:{
	        docTypeName: null,
            catalogName: null,
            beginDate: null,
            endDate: null,
            docName: null,
            contractA: null,

            // 申请的数量
            borrownum: null,
        },
		showList: true,
		title: null,
		document: {},

        vDocInfoDetail:{},
        fileList:{},
        uploadpath:'',
        docName:'',
        docTranLogId:null,
        p_comm:null,//退回意见
        transDoc: {},
        deptList:{},
        selected:'',
        catalogList:{},
        cataselected:'',
        doctypeList:{},
        doctypeselected:''
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
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.document = {};
		},
		update: function (docId) {
			// var docId = getSelectedRow();
			if(docId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            vm.getInfo(docId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.document.docId == null ? "doc_manage/document/save" : "doc_manage/document/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.document),
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
		},
		updateSingle: function(docTranLogId,docId){
            vm.docTranLogId=docTranLogId
            var docId = docId;
            if(docId == null){
                return ;
                }
            vm.getInfos(docId);
            // if(vm.vDocInfoDetail!=null){
            // vm.showList = false;
            // vm.showDetail=true;
            // vm.title = "详情";}
            // else
            // {
            //     layer.msg("档案不存在！", {icon: 2});
            // }



		},
        acceptTransIn: function (event){
            	if(vm.vDocInfoDetail == null){
            		return ;
            	}
            if(vm.selected==null ||vm.selected==''){      //数据校验
                layer.msg("请选择所属部门", {icon: 0});
                $('#btnSaveOrUpdate').button('reset');
                $('#btnSaveOrUpdate').dequeue();
                return ;
            }
            if(vm.cataselected==null ||vm.cataselected==''){
                layer.msg("请选择转入的档案大类", {icon: 0});
                $('#btnSaveOrUpdate').button('reset');
                $('#btnSaveOrUpdate').dequeue();
                return ;
            }
            if(vm.doctypeselected==null ||vm.doctypeselected==''){
                layer.msg("请选择转入的档案类型", {icon: 0});
                $('#btnSaveOrUpdate').button('reset');
                $('#btnSaveOrUpdate').dequeue();
                return ;
            }
            vm.vDocInfoDetail.catalogId=vm.cataselected;
            vm.vDocInfoDetail.docTypeId=vm.doctypeselected;
            vm.vDocInfoDetail.ownDeptId=vm.selected;
            	//console.log("selected rows: "+docIds)
            	var lock = false;
                layer.confirm('确定要接受该记录？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                   if(!lock) {
                        lock = true;
                        $.ajax({
                            type: "POST",
                            url: baseURL + "doc_manage/document/transIn",
                            contentType: "application/json",
                            data: JSON.stringify(vm.vDocInfoDetail),
                            success: function(r){
                                if(r.code == 0){
                                    vm.showList = true;
                                    vm.showDetail=false;
                                    vm.cataselected="";
                                    vm.doctypeselected="";
                                    vm.selected="";
                                    layer.msg("转入操作成功", {icon: 1});
                                    $("#jqGrid").trigger("reloadGrid");
                                }else{
                                    layer.alert(r.msg);
                                }
                            }
            		    });
            	    }
                 }, function(){
                 });
        },
        rejectTransIn: function (event){
            if(vm.docTranLogId == null){
                return ;
            }
            if(vm.p_comm==null ||vm.p_comm==''){      //数据校验
                layer.msg("请输入退回意见", {icon: 0});
                $('#btnReject').button('reset');
                $('#btnReject').dequeue();
                return ;
            }

            //console.log("selected rows: "+docIds)
            var lock = false;
            layer.confirm('确定要退回该记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                var urlr=baseURL + "doc_manage/document/transReject?p_comm="+vm.p_comm+"&docTranLogId="+vm.docTranLogId;
                if(!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: urlr,
                        contentType: "application/json",
                        data: {
                            p_comm:vm.p_comm,
                            docTranLogId:vm.docTranLogId
                        },
                        success: function(r){
                            if(r.code == 0){
                                vm.showList = true;
                                vm.showDetail=false;
                                vm.p_comm="";

                                layer.msg("退回操作成功", {icon: 1});
                                $("#jqGrid").trigger("reloadGrid");
                            }else{
                                layer.alert(r.msg);
                            }
                        }
                    });
                }
            }, function(){
            });
        },
        moreclick: function (event){

		  document.getElementById("querydiv").style.height='100px';
        },
		getInfo: function(docId){
			$.get(baseURL + "doc_manage/document/info/"+docId, function(r){
                vm.document = r.document;
            });
		},
		getInfos: function(docId){
            vm.created();
            $.get(baseURL + "doc_manage/vdocinfodetail/info/"+docId, function(r){
                if(r.vDocInfoDetail!=null){
                vm.vDocInfoDetail = r.vDocInfoDetail;
				vm.deptpriShow = (vm.vDocInfoDetail.viewPri === "指定部门可见");
				vm.userpriShow = (vm.vDocInfoDetail.viewPri === "指定人可见");
                vm.getFmsDocumentList(docId);
                    vm.showList = false;
                    vm.showDetail=true;
                    vm.title = "详情";
                }
                else{
                    layer.msg("档案不存在！", {icon: 2});
                    // layer.confirm('当前档案不存在。', {
                    //     btn: ['确定'] //按钮
                    // }, function() {
                    //     vm.vDocInfoDetail=null;
                    //     vm.showList = true;
                    //     vm.showDetail=false;
                    //     $("#jqGrid").trigger("reloadGrid");
                    // }, function(){
                    // });



                }
            });
		},
        getFmsDocumentList: function(docId){
            $.get(baseURL + "sys/fmsdocumentfile/getFileList?docId=" +docId, function(r){

                vm.fileList = r.page;
                vm.canView=r.canView;
                console.log(vm.fileList);
            });

        },
        clickToDownload:function (file){
            var path=file.fileLocation;
            // var projectname=getRootPath();
            path=path.replace(vm.uploadpath,projectName+"/files/");
            //console.log(path);
            var name=file.fileName;
           // console.log(name);
            if(name.endsWith(".pdf")||name.endsWith(".PDF")){
                //pdf预览
                //跳转
                // var url="pdfPreview.html?name="+name+"&url="+path;
                // window.open(url);
                window.open(projectName+"/statics/plugins/pdfjs/web/viewer.html?file="+path);

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
                vm.cataselected=vm.catalogList.catalogId;
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
            vm.doctypeselected=vm.doctypeList.docTypeId;
        },
        getDoctypeList: function(catalogId){
            var res={catalogId:catalogId};
            var code;
            $.get(baseURL + "doc_manage/doctype/select",res,function(res,code){
                vm.doctypeList = res.doctypeList;
                vm.doctypeselected=vm.doctypeList.docTypeId;
            });
        },
		reload: function () {
			vm.showList = true;
            vm.showDetail=false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'docTypeName': vm.q.docTypeName,
                    'catalogName': vm.q.catalogName,
                    'beginDate': vm.q.beginDate,
                    'endDate': vm.q.endDate,
                    'docName': vm.q.docName,
                    'contractA': vm.q.contractA
                },
                page:page
            }).trigger("reloadGrid");
		},
        refresh: function () {
            vm.showList = true;
            vm.showDetail=false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'docTypeName': vm.q.docTypeName,
                    'catalogName': vm.q.catalogName,
                    'beginDate': vm.q.beginDate,
                    'endDate': vm.q.endDate,
                    'docName': vm.q.docName,
                    'contractA': vm.q.contractA
                },
                page:1
            }).trigger("reloadGrid");
        }

	},
    //外部调用
    mounted() {
         window.updateSingle = this.updateSingle;
        // window.delSingle=this.del;
        // window.transSingle=this.clickTrans;
    }
});