$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/vdocument/list',
        datatype: "json",
        colModel: [			
			{ label: '档案ID', name: 'docId', index: 'doc_id', width: 50, key: true },
			{ label: '合同甲方', name: 'contractA', index: 'contract_a', width: 80 }, 			
			{ label: '档案大类名称', name: 'catalogName', index: 'catalog_name', width: 80 }, 			
			{ label: '档案类型名称', name: 'docTypeName', index: 'doc_type_name', width: 80 }, 			
			{ label: '起始日期', name: 'beginDate', index: 'begin_date', width: 80 }, 			
			{ label: '终止日期', name: 'endDate', index: 'end_date', width: 80 }, 			
			{ label: '档案名称', name: 'docName', index: 'doc_name', width: 80 }, 			
			{ label: '档案在库数量', name: 'docCurrCount', index: 'doc_curr_count', width: 80 }, 			
			{ label: '档案编号/文号', name: 'docNum', index: 'doc_num', width: 80 }, 			
			{ label: '档案页数', name: 'docPages', index: 'doc_pages', width: 80 }, 			
			{ label: '档案形式', name: 'docStyle', index: 'doc_style', width: 80 }, 			
			{ label: 'doc_type_id', name: 'docTypeId', index: 'doc_type_id', width: 80 }, 			
			{ label: '是否有金额', name: 'isAmount', index: 'is_amount', width: 80 }, 			
			{ label: '金额', name: 'amount', index: 'amount', width: 80 }, 			
			{ label: '密级/标签', name: 'secretDegree', index: 'secret_degree', width: 80 }, 			
			{ label: '档案保管期限', name: 'storagPeriod', index: 'storag_period', width: 80 }, 			
			{ label: '合同乙方', name: 'contractB', index: 'contract_b', width: 80 }, 			
			{ label: '合同丙方', name: 'contractC', index: 'contract_c', width: 80 }, 			
			{ label: '合同丁方', name: 'contractD', index: 'contract_d', width: 80 }, 			
			{ label: '合同戊方', name: 'contractE', index: 'contract_e', width: 80 }, 			
			{ label: '查询标签定义，分号分隔', name: 'labels', index: 'labels', width: 80 }			
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
		vDocument: {}
	},
	methods: {
		query: function () {
			vm.refresh();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.vDocument = {};
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
                var url = vm.vDocument.docId == null ? "doc_manage/vdocument/save" : "doc_manage/vdocument/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.vDocument),
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
                        url: baseURL + "doc_manage/vdocument/delete",
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
			$.get(baseURL + "doc_manage/vdocument/info/"+docId, function(r){
                vm.vDocument = r.vDocument;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
        ,
        refresh: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:1
            }).trigger("reloadGrid");
        }
	}
});