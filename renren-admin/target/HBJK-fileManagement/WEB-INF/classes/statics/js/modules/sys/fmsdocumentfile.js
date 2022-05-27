$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/fmsdocumentfile/list',
        datatype: "json",
        colModel: [			
			{ label: 'docfileId', name: 'docfileId', index: 'docfile_id', width: 50, key: true },
			{ label: 'doc_id', name: 'docId', index: 'doc_id', width: 80 }, 			
			{ label: '保存位置', name: 'fileLocation', index: 'file_location', width: 80 }, 			
			{ label: '文件大小', name: 'fileSize', index: 'file__size', width: 80 }, 			
			{ label: '浏览次数', name: 'browseTimes', index: 'browse_times', width: 80 }, 			
			{ label: '备注', name: 'comm', index: 'comm', width: 80 },
			{ label: '删除标记', name: 'delFlag', index: 'del_flag', width: 80 }
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
		fmsDocumentFile: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.fmsDocumentFile = {};
		},
		update: function (event) {
			var docfileId = getSelectedRow();
			if(docfileId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(docfileId)
		},
		saveOrUpdate: function (event) {
		    $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.fmsDocumentFile.docfileId == null ? "sys/fmsdocumentfile/save" : "sys/fmsdocumentfile/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.fmsDocumentFile),
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
			var docfileIds = getSelectedRows();
			if(docfileIds == null){
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
                        url: baseURL + "sys/fmsdocumentfile/delete",
                        contentType: "application/json",
                        data: JSON.stringify(docfileIds),
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
		getInfo: function(docfileId){
			$.get(baseURL + "sys/fmsdocumentfile/info/"+docfileId, function(r){
                vm.fmsDocumentFile = r.fmsDocumentFile;
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