$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fmsdocmodifylog/list',
        datatype: "json",
        colModel: [			
			{ label: '档案ID', name: 'docId', index: 'doc_id', width: 50, key: true },
			{ label: '修改人', name: 'modifyUserId', index: 'modify_user_id', width: 80 }, 			
			{ label: '字段名称', name: 'fieldName', index: 'field_name', width: 80 }, 			
			{ label: 'old_value', name: 'oldValue', index: 'old_value', width: 80 }, 			
			{ label: 'new_value', name: 'newValue', index: 'new_value', width: 80 }, 			
			{ label: 'modify_time', name: 'modifyTime', index: 'modify_time', width: 80 }			
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

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		fmsDocModifyLog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.fmsDocModifyLog = {};
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
                var url = vm.fmsDocModifyLog.docId == null ? "sys/fmsdocmodifylog/save" : "sys/fmsdocmodifylog/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.fmsDocModifyLog),
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
                        url: baseURL + "sys/fmsdocmodifylog/delete",
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
			$.get(baseURL + "sys/fmsdocmodifylog/info/"+docId, function(r){
                vm.fmsDocModifyLog = r.fmsDocModifyLog;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});