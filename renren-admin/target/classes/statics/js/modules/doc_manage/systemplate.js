$(function () {
    vm.getDeptList();
    vm.getCatalogList();
    vm.getUserList();
    //vm.getDocType();
    //vm.getFmsDocumentList(docId);
    vm.getDoctypeList();
    vm.getRoleList()
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/systemplate/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', index: 'id', width: 50, key: true },
            { label: '模板名称', name: 'name', index: 'name', width: 80 },
            { label: '查询内容', name: 'queryContent', index: 'query_content', width: 80 , formatter: function(value, options, row){
                    if(value == ""){
                        return "所有"
                    }else if(value == 1){
                        return "录著数"
                    }else if(value == 2){
                        return "借阅数"
                    }else if(value == 3){
                        return "归还数"
                    }else if(value == 4){
                        return "转出数"
                    }else if(value == 5){
                        return "转入数"
                    }else if(value == 6){
                        return "交付数"
                    }else if(value == 7){
                        return "修改数"
                    }
                }},
            { label: '档案形式', name: 'docStyle', index: 'doc_style', width: 80 , formatter: function(value, options, row){
                    if(value == "-1"){
                        return "全部"
                    }else if(value  == null){
                        return "";
                    }else{
                        return value;
                    }
                }},
            { label: '密级/标签', name: 'secretDegree', index: 'secret_degree', width: 80 , formatter: function(value, options, row){
                    if(value == "-1"){
                        return "全部"
                    }else if(value  == null){
                        return "";
                    }else{
                        return value;
                    }
                }},
            { label: '统计周期', name: 'stateCycle', index: 'state_cycle', width: 80 , formatter: function(value, options, row){
                    return value === 1 ? '日' : value === 2 ? '周' : value === 3 ? '月' : value === 4 ? '年' : '';
                }},
            { label: '图表类型', name: 'chartType', index: 'chart_type', width: 80 , formatter: function(value, options, row){
                    return value === 1 ? '饼状图' : value === 2 ? '折线图' : value === 3 ? '柱状图' : '';
                }},
            { label: '创建人', name: 'createUserName', index: 'create_user_name', width: 80 },
            { label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }
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
        sysTemplate: {

        },
        userIdentityList:[],
        queryTypeList:[{text:'请选择',value:''},{text:'所属部门',value:'1'},{text:'档案大类',value:'2'},{text:'档案类型',value:'3'},{text:'交出人',value:'4'},{text:'档案形式',value:'5'},{text:'密级/标签',value:'6'}],
        queryContentList:[{text:'录著数',value:'1'},{text:'借阅数',value:'2'},{text:'归还数',value:'3'},{text:'转出数',value:'4'},{text:'转入数',value:'5'},{text:'交付数',value:'6'},{text:'修改数',value:'7'}],
        stateCycleList:[{text:'日',value:'1'},{text:'周',value:'2'},{text:'月',value:'3'},{text:'年',value:'4'}],
        chartTypeList:[{text:'饼状图',value:'1'},{text:'折线图',value:'2'},{text:'柱状图',value:'3'}],
        secretDegreeList:[{text:'全选',value:'-1'},{text:'A',value:'A'},{text:'B',value:'B'},{text:'C',value:'C'},{text:'D',value:'D'}],
        deptList:[],
        catalogList:[],
        doctypeList:[],
        userList:[],
        docStyleList:[{text:'全选',value:'-1'},{text:'电子版',value:'电子版'},{text:'纸质',value:'纸质'},{text:'电子和纸质版',value:'电子和纸质版'}],

    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.sysTemplate = {deptName:null, deptId:null};
        },
        update: function (event) {
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        export: function (event) {

            debugger
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.sysTemplate),
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

        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.sysTemplate.id == null ? "doc_manage/systemplate/save" : "doc_manage/systemplate/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.sysTemplate),
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
            var ids = getSelectedRows();
            if(ids == null){
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
                        url: baseURL + "doc_manage/systemplate/delete",
                        contentType: "application/json",
                        data: JSON.stringify(ids),
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
        getInfo: function(id){
            $.get(baseURL + "doc_manage/systemplate/info/"+id, function(r){
                vm.sysTemplate = r.sysTemplate;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                page:page
            }).trigger("reloadGrid");
        },
        getDataTree: function(roleId) {
            //加载菜单树
            $.get(baseURL + "sys/dept/list", function(r){
                data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
                //展开所有节点
                data_ztree.expandAll(true);
            });
        },
        getDeptList: function(){
            $.get(baseURL + "sys/dept/selectuserdep",function(res){

                vm.deptList = [{'deptId':'-1','name':'全选'}].concat(res.deptList);
            });
        },
        getCatalogList: function(deptId){
            var res={deptId:deptId};
            $.get(baseURL + "doc_manage/doccatalog/select",res,function(res,code){
                vm.catalogList = [{'catalogId':'-1','catalogName':'全选'}].concat(res.catalogList);
            });
        },
        onSelectedDept(event) {
            vm.getCatalogList(this.sysTemplate.ownDeptId);
        },
        onSelectedCatalog(event,item) {
            //打印出绑定的对象
            vm.getDoctypeList(this.sysTemplate.categoryId);
        },
        getDoctypeList: function(catalogId){
            var res={catalogId:catalogId};
            var code;
            $.get(baseURL + "doc_manage/doctype/select",res,function(res,code){
                vm.doctypeList = [{'docTypeId':'-1','docTypeName':'全选'}].concat(res.doctypeList);
            });
        },
        getUserList: function(){
            $.get(baseURL + "sys/user/select",function(res){
                vm.userList = [{'userId':'-1','username':'全选'}].concat(res.userList);
            });
        },
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.userIdentityList = r.list;
            });
        },
    }
});
