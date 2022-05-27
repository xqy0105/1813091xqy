$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/docconfimapply/list',
        datatype: "json",
        colModel: [
            {label: '', name: '', index: 'action', width: 120, sortable:false,formatter: cmgStateFormat},
			{ label: '档案Id', name: 'docId', index: 'doc_id', width: 50, key: true },
			{label: '档案名', name: 'docName', index: 'doc_name', width: 80},
			//{ label: '确认申请人ID', name: 'ownUserId', index: 'own_user_id', width: 80 },
            { label: '审批人', name: 'approverName', index: 'approver_name', width: 80 },
            { label: '确认申请人', name: 'ownUserName', index: 'ownUser_name', width: 80 },
			//{ label: '交付人ID', name: 'createUserId', index: 'create_user_id', width: 80 },
			{ label: '申请确认时间', name: 'applyTime', index: 'apply_time', width: 80 }, 			
			{ label: '交付人审批时间', name: 'approveTime', index: 'approve_time', width: 80 },
			//{ label: '审批状态，-1待审批，0退回，1通过', name: 'approveStatus', index: 'approve_status', width: 80 },
			{ label: '审批意见', name: 'approveInfo', index: 'approve_info', width: 80 },
			//{ label: '备注', name: 'comm', index: 'comm', width: 80 }
        ],
		viewrecords: true,
        height: 'auto',
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
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
    // var output = "";
    // for(var i in rowObject){
    //     var property=rowObject[i];
    //     output+=i+" = "+property+"\n";
    // }
    // console.log("row Object: "+output);
    html = "";
    if(rowObject.approveStatus === 1){
        html = "<button type=\"button\" disabled=\"disabled\" class='btn btn-success' \">申请已确认</button>";
    }else if(rowObject.approveStatus === -1){
        html =
            // "<button type=\"button\" class='btn btn-default'  onclick=\"updateSingle(" + options.rowId + ")\">修改</button>" +
        "<button type=\"button\" class='btn btn-warning' style='line-height: 0.6' onclick=\"RejectSingle(" + rowObject.docConfirmApplyId + ")\">驳回</button>" +
            "<button type=\"button\" class='btn btn-primary' style='line-height: 0.6' onclick=\"ApproveSingle(" + rowObject.docConfirmApplyId + ")\">确认申请</button>"+
            "<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"updateSingle(" + rowObject.docId + ")\">详情</button>";
             }else if(rowObject.approveStatus === 0){
        html = "<button type=\"button\" disabled=\"disabled\" class='btn btn-danger' \">申请被驳回</button>";
    }

    return html;
}

var vm = new Vue({
	el:'#rrapp',
	data:{
	    canView:false,
		showList: true,
		showDetail: false,
		showInfo:false,
        deptpriShow:false,
        userpriShow:false,
		title: null,
		docConfimApply: {},
        approveMsg: {
            isApproved: null,
            docConfirmApplyId: null,
            approverId: null,
            approveInfo: null,
            approveTime: null,
        },
        q:{
            approveStatus:null,
            ownUserName:null
        },
        vDocInfoDetail:{},
        fileList:{},
        uploadpath:'',
        docName:'',
        approveresultList:[{text:'未审批',value:-1},{text:'通过审批',value:1},{text:'未通过审批',value:0},{text:'全部',value:null}],

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
			vm.showList = false;
			vm.title = "新增";
			vm.docConfimApply = {};
		},
		update: function (event) {
			var docId = getSelectedRow();
			if(docId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(docId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.docConfimApply.docId == null ? "doc_manage/docconfimapply/save" : "doc_manage/docconfimapply/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.docConfimApply),
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
		del: function (event) {
			var docIds = getSelectedRows();
			if(docIds == null){
				return ;
			}
			var lock = false;
            layer.confirm('确定要删除选中的记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
               if(!lock) {
                    lock = true;
		            $.ajax({
                        type: "POST",
                        url: baseURL + "doc_manage/docconfimapply/delete",
                        contentType: "application/json",
                        data: JSON.stringify(docIds),
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
             }, function(){
             });
		},
		updateSingle: function(docId){
            var docId = docId;
            if(docId == null){
                return ;
                }
            vm.showList = false;
            vm.showDetail=true;
            vm.title = "详情";

            vm.getInfos(docId);

		},
		getInfo: function(docId){
			$.get(baseURL + "doc_manage/docconfimapply/info/"+docId, function(r){
                vm.docConfimApply = r.docConfimApply;
            });
		},
		getInfos: function(docId){
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
                console.log(vm.fileList);
            });

        },
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
        reload: function (event) {
            vm.showList = true;
            vm.showInfo = false;
            vm.showDetail=false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'ownUserName': vm.q.ownUserName,
                    'approveStatus': vm.q.approveStatus
                },
                page:page,
                sidx:'applytime',
                order:'desc'
            }).trigger("reloadGrid");
        },
        refresh: function (event) {
            vm.showList = true;
            vm.showInfo = false;
            vm.showDetail=false;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'ownUserName': vm.q.ownUserName,
                    'approveStatus': vm.q.approveStatus
                },
                page:1,
                sidx:'applytime',
                order:'desc'
            }).trigger("reloadGrid");
        },

       clickReject: function (docConfirmApplyId){
            vm.showList = false;
            vm.showInfo = true;
            vm.approveMsg.isApproved = 0;
            vm.title = "驳回理由";
            vm.approveMsg.docConfirmApplyId = docConfirmApplyId;
            vm.approveMsg.approveTime= new Date().toISOString().slice(0, 19).replace('T', ' ');
        },


        clickApprove: function (docConfirmApplyId){
            vm.showList = false;
            vm.showInfo = true;
            vm.approveMsg.isApproved = 1;
            vm.title = "通过理由";
            vm.approveMsg.docConfirmApplyId  = docConfirmApplyId ;
            vm.approveMsg.approveTime= new Date().toISOString().slice(0, 19).replace('T', ' ');
        },

        handleApproveOrReject: function (event){
            console.log("Handle ! TABLE ID: "+ vm.approveMsg.docConfirmApplyId );
            $('#btnApproveOrRejectConfirm').button('loading').delay(1000).queue(function() {
                var url = "doc_manage/docconfimapply/approve_or_reject_confirm";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.approveMsg),
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();

                            $('#btnApproveOrRejectConfirm').button('reset');
                            $('#btnApproveOrRejectConfirm').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnApproveOrRejectConfirm').button('reset');
                            $('#btnApproveOrRejectConfirm').dequeue();
                        }
                        vm.approveMsg.approveInfo='';
                    }
                });
            });

        },
        },
    mounted(){
        window.RejectSingle = this.clickReject;
        window.ApproveSingle = this.clickApprove;
        window.updateSingle = this.updateSingle;
    }


 });