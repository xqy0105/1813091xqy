$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/doctype/list',
        datatype: "json",
        colModel: [
            {label: '操作', name: '', index: 'action', width: 50, sortable:false,formatter: cmgStateFormat},
            { label: '档案类型ID', name: 'docTypeId', index: 'doc_type_id', width: 50, key: true },
            { label: '部门名称', name: 'deptName', index: 'deptName', width: 80 },
            { label: '档案大类名称', name: 'catalogName', index: 'catalogName', width: 80 },
            { label: '档案类型名称', name: 'docTypeName', index: 'doc_type_name', width: 80 }
            /*,
			{ label: '', name: 'delFlag', index: 'del_flag', width: 80 }	*/
        ],
        viewrecords: true,
        height: 'auto',
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        // multiselect: true,
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
    vm.getCatalogList();
});
function cmgStateFormat(cellvalue, options, rowObject) {
    var html="";
    //if (rowObject.hasSub==0){
    html=
        // "<button type=\"button\" class='btn btn-default'  onclick=\"updateSingle(" + options.rowId + ")\">修改</button>" +
        // "<button type=\"button\" class='btn btn-default' onclick=\"delSingle(" + options.rowId + ")\">删除</button>" +

        "<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"update(" + rowObject.docTypeId + ")\">修改</button>  "+
        "<button type=\"button\" class='btn btn-danger' style='line-height: 0.6' onclick=\"delSingle(" + rowObject.docTypeId + ")\">删除</button>";
    // }
    // else html="";
    // "<button type=\"button\" class='btn btn-default'>借阅</button>";
    return html;
    // return "<button class='btn btn-primary ' οnclick= \" change (" + rows. cmgId+ ","+cellValue + ") \" >上架</button>" ;
}

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        docType: {},
        canModify:true,
        deptList:{},
        selected:'',
        catalogList:{},
        cataselected:''
    },
    methods: {
        query: function () {
            vm.refresh();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.docType = {};
        },
        update: function (docTypeId) {
            //var docTypeId = getSelectedRow();
            if(docTypeId == null){
                return ;
            }
            vm.getCanModify(docTypeId);

        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.docType.docTypeId == null ? "doc_manage/doctype/save" : "doc_manage/doctype/update";
                vm.docType.deptId=vm.selected;
                vm.docType.catalogId=vm.cataselected;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.docType),
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
            var docTypeIds = getSelectedRows();
            if(docTypeIds == null){
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
                        url: baseURL + "doc_manage/doctype/delete",
                        contentType: "application/json",
                        data: JSON.stringify(docTypeIds),
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
        getInfo: function(docTypeId){
            $.get(baseURL + "doc_manage/doctype/info/"+docTypeId, function(r){
                vm.docType = r.docType;
                vm.selected=vm.docType.deptId;
                vm.getCatalogList(vm.selected);

            });


        },
        getDeptList: function(){
            $.get(baseURL + "sys/dept/selectuserdep",function(res){
                vm.deptList = res.deptList;
                // console.log(vm.deptList);
            });
        },
        onSelectedDept(event,item) {
//打印出绑定的对象
 //           console.log(this.selected);
            vm.getCatalogList(this.selected);
            vm.cataselected=vm.docType.catalogId;
        },

        getCatalogList: function(deptId){
            var res={deptId:deptId};
            var code;
            $.get(baseURL + "doc_manage/doccatalog/select",res,function(res,code){
                vm.catalogList = res.catalogList;
                vm.cataselected=vm.docType.catalogId;
            });
        },
        getCanModify: function(docTypeId){
            var para={docTypeId:docTypeId};
            var code;
            $.get(baseURL + "doc_manage/doctype/canModify",para, function(r,code){
                vm.canModify = r.canModify;
                if(!vm.canModify){
                    layer.msg("当前类型存在未删除文件，不可修改", {icon: 1});
                    return;
                }
                vm.showList = false;
                vm.title = "修改";
                vm.getInfo(docTypeId)
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        },
        refresh: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:1
            }).trigger("reloadGrid");
        },
        exporttoexcel: function () {
            layer.confirm('是否确认导出所有数据项?', {
                btn: ['确定','取消'] //按钮
            }, function(){ window.location.href=baseURL + "doc_manage/doctype/export";
                layer.close(layer.index);
            }, function(){
            });


        }
        ,
        delSingle: function (catalogId) {

            if(catalogId == null){
                return ;
            }
            var lock = false;
            layer.confirm('确定要删除记录？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                if(!lock) {
                    lock = true;
                    $.ajax({
                        type: "GET",
                        url: baseURL + "doc_manage/doctype/deleteSingle/"+catalogId,
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
        }

    }
    ,

    mounted() {
        window.update = this.update;
        window.delSingle=this.delSingle;
    }
});