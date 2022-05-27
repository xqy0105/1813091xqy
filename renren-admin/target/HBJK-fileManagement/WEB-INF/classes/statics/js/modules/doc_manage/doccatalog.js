$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/doccatalog/list',
        datatype: "json",
        colModel: [
            {label: '操作', name: '', index: 'action', width: 50, sortable:false,formatter: cmgStateFormat},
            { label: '档案大类Id', name: 'catalogId', index: 'catalog_id', width: 50, key: true },
            { label: '部门id', name: 'deptId', index: 'dept_id', width: 80 },
            { label: '部门名称', name: 'deptName', index: 'dept_name', width: 80 },
            { label: '档案大类名称', name: 'catalogName', index: 'catalog_name', width: 80 },
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
    vm.getDeptList();

});
function cmgStateFormat(cellvalue, options, rowObject) {
    var html="";
    if (rowObject.hasSub==0){
        html=
            // "<button type=\"button\" class='btn btn-default'  onclick=\"updateSingle(" + options.rowId + ")\">修改</button>" +
            // "<button type=\"button\" class='btn btn-default' onclick=\"delSingle(" + options.rowId + ")\">删除</button>" +

            "<button type=\"button\" class='btn btn-info' style='line-height: 0.6' onclick=\"update(" + rowObject.catalogId + ")\">修改</button>&nbsp;"+
            "<button type=\"button\" class='btn btn-danger' style='line-height: 0.6' onclick=\"delSingle(" + rowObject.catalogId + ")\">删除</button>";
    }
    else html="";
    // "<button type=\"button\" class='btn btn-default'>借阅</button>";
    return html;
    // return "<button class='btn btn-primary ' οnclick= \" change (" + rows. cmgId+ ","+cellValue + ") \" >上架</button>" ;
}
var vm = new Vue({
    el:'#rrapp',
    data:{
        showBatch:false,
        showList: true,
        title: null,
        docCatalog: {},
        deptList:{},
        deptsString:[],
        selected:''
    },
    methods: {
        query: function () {
            vm.refresh();

        },
        add: function(){
            vm.showList = false;
            vm.showBatch=false;
            vm.title = "新增";
            vm.docCatalog = {};
        },
        addbatch: function(){
            vm.showList = false;
            vm.showBatch=true;
            vm.title = "批量新增";
            vm.docCatalog = {};
            vm.deptsString="";
        },
        update: function (catalogId) {
            //var catalogId = getSelectedRow();
            if(catalogId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(catalogId)
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.docCatalog.catalogId == null ? "doc_manage/doccatalog/save" : "doc_manage/doccatalog/update";
                vm.docCatalog.deptId=vm.selected;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.docCatalog),
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
        batchAdd: function (event) {
            $('#btnBatchAdd').button('loading').delay(1000).queue(function() {
                var url =  "doc_manage/doccatalog/batchadd" ;
                vm.docCatalog.deptsString=vm.deptsString;
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.docCatalog),
                    success: function(r){
                        vm.deptsString="";
                        if(r.code === 0){
                            layer.msg("操作成功", {icon: 1});

                            vm.reload();
                            $('#btnBatchAdd').button('reset');
                            $('#btnBatchAdd').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnBatchAdd').button('reset');
                            $('#btnBatchAdd').dequeue();
                        }
                    }
                });
            });
        },
        del: function (event) {
            var catalogIds = getSelectedRows();
            if(catalogIds == null){
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
                        url: baseURL + "doc_manage/doccatalog/delete",
                        contentType: "application/json",
                        data: JSON.stringify(catalogIds),
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
        delSingle: function (catalogId) {
            // var catalogId = getSelectedRow();
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
                        url: baseURL + "doc_manage/doccatalog/deleteSingle/"+catalogId,
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
        getInfo: function(catalogId){

            $.get(baseURL + "doc_manage/doccatalog/info/"+catalogId, function(r){
                vm.docCatalog = r.docCatalog;
                vm.selected=vm.docCatalog.deptId;

            });


        },
        getDeptList: function(){
            $.get(baseURL + "sys/dept/selectuserdep",function(res){
                vm.deptList = res.deptList;
                // console.log(vm.deptList);
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
    ,
    mounted() {
        window.update = this.update;
        window.delSingle=this.delSingle;
    }
});