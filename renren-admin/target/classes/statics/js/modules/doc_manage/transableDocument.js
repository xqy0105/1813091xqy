$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/document/transable_list',
        datatype: "json",
        colModel: [
            // {label: '', name: '', index: 'action', width: 50, sortable:false,formatter: cmgStateFormat},
			{ label: '档案ID', name: 'docId', index: 'doc_id', width: 50, key: true },
			{ label: '档案大类', name: 'catalogName', index: 'catalog_name', width: 80},
			{ label: '档案类型', name: 'docTypeName', index: 'doc_type_name', width: 80},
			// { label: 'doc_type_id', name: 'docTypeId', index: 'doc_type_id', width: 80 },
			// { label: '所属部门', name: 'ownDeptId', index: 'own_dept_id', width: 80 },
			// { label: '负责人', name: 'ownUserId', index: 'own_user_id', width: 80 },
			// { label: '创建人', name: 'createUserId', index: 'create_user_id', width: 80 },
			// { label: '创建时间', name: 'createTime', index: 'create_time', width: 80 },
			// { label: '档案形式', name: 'docStyle', index: 'doc_style', width: 80 },
			// { label: '存放地址', name: 'storePlace', index: 'store_place', width: 80 },
			// { label: '浏览权限类型', name: 'viewPri', index: 'view_pri', width: 80 },
			{ label: '档案名称', name: 'docName', index: 'doc_name', width: 80 }, 			
			// { label: '档案编号/文号', name: 'docNum', index: 'doc_num', width: 80 },
			// { label: '是否有金额', name: 'isAmount', index: 'is_amount', width: 80 },
			// { label: '金额', name: 'amount', index: 'amount', width: 80 },
			// { label: '定期归档模式', name: 'archiveMode', index: 'archive_mode', width: 80 },
			// { label: '档案数量', name: 'docCount', index: 'doc_count', width: 80 },
			// { label: '档案页数', name: 'docPages', index: 'doc_pages', width: 80 },
			// { label: '密级/标签', name: 'secretDegree', index: 'secret_degree', width: 80 },
			{ label: '起始日期', name: 'beginDate', index: 'begin_date', width: 80 }, 			
			{ label: '终止日期', name: 'endDate', index: 'end_date', width: 80 }, 			
			// { label: '档案保管期限', name: 'storagPeriod', index: 'storag_period', width: 80 },
			{ label: '合同甲方', name: 'contractA', index: 'contract_a', width: 80 },
			// { label: '合同乙方', name: 'contractB', index: 'contract_b', width: 80 },
			// { label: '合同丙方', name: 'contractC', index: 'contract_c', width: 80 },
			// { label: '合同丁方', name: 'contractD', index: 'contract_d', width: 80 },
			// { label: '合同戊方', name: 'contractE', index: 'contract_e', width: 80 },
			// { label: '查询标签定义，分号分隔', name: 'labels', index: 'labels', width: 80 }
        ],
		viewrecords: true,
        height: 385,
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
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "visible" });
        },
    });
});

// function cmgStateFormat(cellvalue, options, rowObject) {
//     const html =
//
//         "<button type=\"button\" class='btn btn-default' onclick=\"transSingle(" + rowObject + ")\">交接</button>";
//
//     return html;
// }


var vm = new Vue({
	el:'#rrapp',
	data:{
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
		title: "newTitle",
		document: {},
        transDoc: {},
        colleagues: [],
        selectedColleagues: null,
        depts: [],
        selectedDeptId: null,
        catalogList: [],
        selectedCatalogId: null,
        doctypeList: [],
        selectedDoctypeId: null,
        selectedDocIds: []
	},
	methods: {
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

        clickTransOut: function (){
            vm.showList = false;
            vm.title = "交出";
            //init options:

            $.get(baseURL+"sys/dept/listDepts", function (res){
                //console.log("dept list res is :"+res)
                vm.depts = res.depts;
                //console.log("depts: "+JSON.stringify(vm.depts))
            });
            vm.selectedDocIds = getSelectedRows();
            if(vm.selectedDocIds == null){
                return ;
            }
        },
        //点击确定后；
        transOut: function(event){
           // console.log("selected:")
            //console.log("deptID "+vm.selectedDeptId+"\ncatalogId: "
             //   +vm.selectedCatalogId+"\ndocTypeId: "+vm.selectedDoctypeId+"\nuserId: "+vm.selectedColleagues)
            var params = {
                "docIds": vm.selectedDocIds,
                "deptId": vm.selectedDeptId,
                "catalogId": vm.selectedCatalogId,
                "docTypeId": vm.selectedDoctypeId,
                "toUserId": vm.selectedColleagues
            }
            $('#btnTransOut').button('loading').delay(1000).queue(function() {
                var url = "doc_manage/document/transOut";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(params),
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnTransOut').button('reset');
                            $('#btnTransOut').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnTransOut').button('reset');
                            $('#btnTransOut').dequeue();
                        }
                    }
                });
            });


        },
        onSelectedDept(event, selectedDeptId){
            console.log("Dept is seleted!")
            var res={deptId:selectedDeptId};
            var code;
            $.get(baseURL + "doc_manage/doccatalog/select",res,function(res,code){
                vm.catalogList = res.catalogList;
                vm.selectedCatalogId=vm.catalogList.catalogId;
              //  console.log("Catalog: "+JSON.stringify(vm.catalogList) )
            });

            res={deptId:selectedDeptId};
            $.get(baseURL+"sys/user/selectdeptuser", res,function (res){
                vm.colleagues = res.colleagues;
               // console.log("colleagues: "+JSON.stringify(vm.colleagues))
                // var docIds = getSelectedRows();
                // if(docIds == null){
                //     return ;
                // }
            });
        },
        onSelectedCatalog(event, selectedCatalogId){
		    console.log("Catalog is seleted!")
            var res={catalogId:selectedCatalogId};
            var code;
            $.get(baseURL + "doc_manage/doctype/select",res,function(res,code){
                vm.doctypeList = res.doctypeList;
                vm.selectedDoctypeId = vm.doctypeList.docTypeId;
               // console.log("Doctype: "+JSON.stringify(vm.doctypeList) )
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
		reload: function () {
			vm.showList = true;
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

    },
    created(){


    }
});