$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fmsdoctranslog/list',
        datatype: "json",
        colModel: [			
			{ label: '档案Id', name: 'docId', index: 'doc_id', width: 50, key: true },
            { label: '档案名称', name: 'docName', index: 'doc_name', width: 50, key: true },
			{ label: '交出者', name: 'fromUserName', index: 'from_user_name', width: 80 },
			{ label: '接收者', name: 'toUserName', index: 'to_user_name', width: 80 },
			{ label: '状态', name: 'state', index: 'state', width: 80 }, 			
			{ label: '交出时间', name: 'outTime', index: 'out_time', width: 80 },
            { label: '接收时间', name: 'inTime', index: 'in_time', width: 80 },
             { label: '备注', name: 'comm', index: 'comm', width: 80 }
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

var vm = new Vue({
	el:'#rrapp',
	data:{
	    q:{
            fromUserName: null,
            toUserName: null,
            docName: null,
            state: null,
        },
        stateList:[{text:'已交出',value:1},{text:'已完成',value:0},{text:'全部',value:null}],
        showList: true,
		title: null,
		fmsDocTransLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.fmsDocTransLog = {};
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
                var url = vm.fmsDocTransLog.docId == null ? "sys/fmsdoctranslog/save" : "sys/fmsdoctranslog/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.fmsDocTransLog),
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
                        url: baseURL + "sys/fmsdoctranslog/delete",
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
		getInfo: function(docId){
			$.get(baseURL + "sys/fmsdoctranslog/info/"+docId, function(r){
                vm.fmsDocTransLog = r.fmsDocTransLog;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'fromUserName': vm.q.fromUserName,
                    'toUserName': vm.q.toUserName,
                    'docName': vm.q.docName,
                    'state': vm.q.state
                },
                page:1
            }).trigger("reloadGrid");
		}
	}
});