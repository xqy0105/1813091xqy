$(function () {
    vm.getottypelist();
    var info=location.search;
    if (info.length>0) {
        item = info.split("=");
        var itemId = decodeURI(item[1]);
        console.log(itemId);
        if(itemId!="") {
            //从ottotype获取参数值
            //console.log(vm.ottypelist[itemId].paramValue);
            for ( overtype of vm.ottypelist) {
                //console.log(overtype.remark);
                if(overtype.remark==itemId){
                    vm.q.overtimetype = overtype.paramKey;

                    console.log(vm.q.overtimetype);
                    break;
                }
            }


        }
    }
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/docborrowapply/borrowlist',
        datatype: "json",

        postData:{
            'overtimetype':encodeURI(vm.q.overtimetype),
            'applyResult': vm.q.applyResult,
            'overtime':vm.q.overtime
        },
        colModel: [

            // { label: '借阅人', name: 'borrowerName', index: 'borrower_name', width: 80 },
            { label: '文件id', name: 'docId', index: 'doc_id', width: 60 },
            {label: '文件名', name: 'docName', index: 'doc_name', width: 80},
            { label: '申请借阅时间', name: 'applytime', index: 'applytime', width: 80 },
            { label: '审批结果', name: 'applyResultInfo', index: 'applyResultInfo', width: 80 },
            { label: '审批意见', name: 'approveInfo', index: 'approve_info', width: 80 },
            { label: '审批时间', name: 'approveTime', index: 'approve_time', width: 80},
            { label: '借阅截止时间', name: 'expirationDate', index: 'expirationDate', width: 80},
            { label: '实际归还时间', name: 'returnTime', index: 'return_time', width: 80 },
            { label: '借阅数量', name: 'borrownum', index: 'borrownum', width: 80 }

            // { label: '审批结果',
            //     // -1，驳回
            //     // 0，申请中
            //     // 1，同意',
            //     name: 'applyResult', index: 'apply_result', width: 80 },
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


var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        q:{
            applyResult:null,
            overtime:null,
            overtimetype:null
        },

        approveresultList:[{text:'未审批',value:0},{text:'通过审批',value:1},{text:'未通过审批',value:-1},{text:'全部',value:null}],
        overtimeList:[{text:'已逾期',value:-1},{text:'3天内到期',value:3},{text:'全部',value:null}],
        ottypelist:{}
    },
    methods: {
        query: function () {
            vm.refresh();
        },
        //获取超期类型
        getottypelist: function(){
            $.ajaxSettings.async = false;
            $.get(baseURL + "sys/config/listbykey?params=borrowexp",function(res){
                vm.ottypelist = res.configs;
            });
            $.ajaxSettings.async = true;

        },
        refresh: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'overtimetype':encodeURI(vm.q.overtimetype),
                    'applyResult': vm.q.applyResult,
                    'overtime':vm.q.overtime
                },
                page:1,
                sidx:'applytime',
                order:'desc'
            }).trigger("reloadGrid");
        }
    },

    mounted(){
    }
});