$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fmsdocborrow/list',
        datatype: "json",
        colModel: [
            {label: '', name: '', index: 'action', width: 80, sortable:false,formatter: cmgStateFormat},
			{ label: '申请人', name: 'approverName', index: 'approver_name', width: 50 },
			{ label: '借阅人', name: 'borrowerName', index: 'borrower_name', width: 80 },
			{ label: '文档ID', name: 'docId', index: 'doc_id', width: 80 },
            { label: '文档名称', name: 'docName', index: 'doc_name', width: 80 },
            // { label: '申请借阅时间', name: 'applyTime', index: 'applytime', width: 80 },
			{ label: '借出时间', name: 'borrowtime', index: 'borrowtime', width: 80 }, 			
			{ label: '归还截止时间', name: 'expirationDate', index: 'expiration_date', width: 80 }, 			
			{ label: '实际归还时间', name: 'returnTime', index: 'return_time', width: 80 },
			{ label: '借阅数量', name: 'borrownum', index: 'borrownum', width: 80 }
            // ,
            // { label: '归还结果', name: 'returnResult', index: 'returnResult', width: 80 },
            // { label: '备注', name: 'comm', index: 'comm', width: 80 }
        ],
		viewrecords: true,
        height: 'auto',
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
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

function cmgStateFormat(cellvalue, options, rowObject) {
    var output = "";
    for(var i in rowObject){
        var property=rowObject[i];
        output+=i+" = "+property+"\n";
    }
    console.log("row Object: "+output);
     html = "";
    if (rowObject.returnResult === 0){
        html =
            // "<button type=\"button\" class='btn btn-default' onclick=\"RejectSingle(" + rowObject.docBorrowApplyId + ")\">驳回</button>" +
            "<button type=\"button\" class='btn btn-warning' onclick=\"ReturnSingle(" + rowObject.docBorrowId+ ")\">点击归还</button>";
    } else{
        html =
            // "<button type=\"button\" class='btn btn-default' onclick=\"RejectSingle(" + rowObject.docBorrowApplyId + ")\">驳回</button>" +
            "<button type=\"button\"  disabled=\"disabled\"  class='btn btn-success' onclick=\"ReturnSingle(" + rowObject.docBorrowId+ ")\">已经归还</button>";
    }

    return html;

}

var vm = new Vue({
	el:'#rrapp',
	data:{
	    q: {
            borrowerName: null,
            returnResult: null,
        },
        returnresultList:[{text:'已归还',value:1},{text:'未归还',value:0},{text:'全部',value:null}],
		showList: true,
		title: null,
		fmsDocBorrow: {}
	},
	methods: {
		query: function () {
			vm.refresh();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.fmsDocBorrow = {};
		},
		update: function (event) {
			var approverId = getSelectedRow();
			if(approverId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(approverId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.fmsDocBorrow.approverId == null ? "sys/fmsdocborrow/save" : "sys/fmsdocborrow/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.fmsDocBorrow),
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
			var approverIds = getSelectedRows();
			if(approverIds == null){
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
                        url: baseURL + "sys/fmsdocborrow/delete",
                        contentType: "application/json",
                        data: JSON.stringify(approverIds),
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
		getInfo: function(approverId){
			$.get(baseURL + "sys/fmsdocborrow/info/"+approverId, function(r){
                vm.fmsDocBorrow = r.fmsDocBorrow;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'borrowerName': vm.q.borrowerName,
                    'returnResult': vm.q.returnResult
                },
                page:page
            }).trigger("reloadGrid");
		},
        refresh: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'borrowerName': vm.q.borrowerName,
                    'returnResult': vm.q.returnResult
                },
                page:1
            }).trigger("reloadGrid");
        },
        clickReturn: function (docBorrowId){
		    console.log("click return!"+docBorrowId);
            params = {
                "docBorrowId": docBorrowId,
            }
            var lock = false;
            layer.confirm('确认归还？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                if(!lock) {
                    lock = true;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "sys/fmsdocborrow/return",
                        contentType: "application/json",
                        data: JSON.stringify(params),
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
	},
    mounted(){
        window.ReturnSingle = this.clickReturn;
    }
});