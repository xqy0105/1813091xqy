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

                        //console.log(vm.q.overtimetype);
                        break;
                    }
                }


            }
        }
    $("#jqGrid").jqGrid({
        url: baseURL + 'doc_manage/docborrowapply/list',
          postData:{
            'overtimetype':encodeURI(vm.q.overtimetype),
            'borrowerName': vm.q.borrowerName,
            'applyResult': vm.q.applyResult
        },
        datatype: "json",
        colModel: [
            {label: '', name: '', index: 'action', width: 120, sortable:false,formatter: cmgStateFormat},
            // { label: 'docBorrowApplyId', name: 'docBorrowApplyId', index: 'doc_borrow_apply_id', width: 50, key: true },
            { label: '审批人', name: 'approverName', index: 'approver_name', width: 80 },
            { label: '借阅人', name: 'borrowerName', index: 'borrower_name', width: 80 },
            { label: '文件id', name: 'docId', index: 'doc_id', width: 60 },
            {label: '文件名', name: 'docName', index: 'doc_name', width: 80},
            { label: '申请借阅时间', name: 'applytime', index: 'applytime', width: 80 },
            { label: '截止归还时间', name: 'expirationDate', index: 'expirationDate', width: 80},
            // { label: '实际归还时间', name: 'returnTime', index: 'return_time', width: 80 },
            { label: '借阅数量', name: 'borrownum', index: 'borrownum', width: 80 },
            { label: '审批时间', name: 'approveTime', index: 'approve_time', width: 80 },
            { label: '审批意见', name: 'approveInfo', index: 'approve_info', width: 80 },
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


}

);

function cmgStateFormat(cellvalue, options, rowObject) {
    // var output = "";
    // for(var i in rowObject){
    //     var property=rowObject[i];
    //     output+=i+" = "+property+"\n";
    // }
    // console.log("row Object: "+output);
    html = "";
    if(rowObject.applyResult === 1){
        html = "<button type=\"button\" disabled=\"disabled\" class='btn btn-success' \">申请已通过</button>";
    }else if(rowObject.applyResult === 0){
        html =
            // "<button type=\"button\" class='btn btn-default'  onclick=\"updateSingle(" + options.rowId + ")\">修改</button>" +
            "<button type=\"button\" class='btn btn-danger' onclick=\"RejectSingle(" + rowObject.docBorrowApplyId + ")\">驳回</button>" +
            "<button type=\"button\" class='btn btn-primary' onclick=\"ApproveSingle(" + rowObject.docBorrowApplyId + ")\">通过</button>";
    }else if(rowObject.applyResult === -1){
        html = "<button type=\"button\" disabled=\"disabled\" class='btn btn-warning' \">申请被驳回</button>";
    }

    return html;
}

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        docBorrowApply: {},
        approveMsg: {
            isApproved: null,
            docBorrowApplyId: null,
            approverId: null,
            approveInfo: null,
            approveTime: null,
        },
        q:{
            applyResult:null,
            borrowerName:null,
            overtimetype:null
        },
        approveresultList:[{text:'未审批',value:0},{text:'通过审批',value:1},{text:'未通过审批',value:-1},{text:'全部',value:null}],
        ottypelist:{}

    },
    methods: {
        query: function () {
            vm.refresh();
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.docBorrowApply = {};
        },
        update: function (event) {
            var docBorrowApplyId = getSelectedRow();
            if(docBorrowApplyId == null){
                return ;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(docBorrowApplyId)
        },
        saveOrUpdate: function (event) {
            $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
                var url = vm.docBorrowApply.docBorrowApplyId == null ? "doc_manage/docborrowapply/save" : "doc_manage/docborrowapply/update";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.docBorrowApply),
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
            var docBorrowApplyIds = getSelectedRows();
            if(docBorrowApplyIds == null){
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
                        url: baseURL + "doc_manage/docborrowapply/delete",
                        contentType: "application/json",
                        data: JSON.stringify(docBorrowApplyIds),
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

        //获取超期类型
        getottypelist: function(){
            $.ajaxSettings.async = false;
            $.get(baseURL + "sys/config/listbykey?params=borrowapply",function(res){
                vm.ottypelist = res.configs;
            });
            $.ajaxSettings.async = true;

        },
        getInfo: function(docBorrowApplyId){
            $.get(baseURL + "doc_manage/docborrowapply/info/"+docBorrowApplyId, function(r){
                vm.docBorrowApply = r.docBorrowApply;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'overtimetype':encodeURI(vm.q.overtimetype),
                    'borrowerName': vm.q.borrowerName,
                    'applyResult': vm.q.applyResult
                },
                page:page,
                sidx:'applytime',
                order:'desc'
            }).trigger("reloadGrid");
        },
        refresh: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam','page');
            $("#jqGrid").jqGrid('setGridParam',{
                postData:{
                    'overtimetype':encodeURI(vm.q.overtimetype),
                    'borrowerName': vm.q.borrowerName,
                    'applyResult': vm.q.applyResult
                },
                page:1,
                sidx:'applytime',
                order:'desc'
            }).trigger("reloadGrid");
        },
        clickReject: function (docBorrowApplyId){
            vm.showList = false;
            vm.approveMsg.isApproved = false;
            vm.title = "驳回理由";
            vm.approveMsg.docBorrowApplyId = docBorrowApplyId;
            vm.approveMsg.approveTime= new Date().toISOString().slice(0, 19).replace('T', ' ');
        },


        clickApprove: function (docBorrowApplyId){
            vm.showList = false;
            vm.approveMsg.isApproved = true;
            vm.title = "通过理由";
            vm.approveMsg.docBorrowApplyId = docBorrowApplyId;
            vm.approveMsg.approveTime= new Date().toISOString().slice(0, 19).replace('T', ' ');
        },

        handleApproveOrReject: function (event){
            console.log("Handle ! TABLE ID: "+ vm.approveMsg.docBorrowApplyId);
            $('#btnApproveOrRejectBorrow').button('loading').delay(1000).queue(function() {
                var url = "doc_manage/docborrowapply/approve_or_reject_borrow";
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(vm.approveMsg),
                    success: function(r){
                        if(r.code === 0){
                            layer.msg("操作成功", {icon: 1});
                            vm.reload();
                            $('#btnApproveOrRejectBorrow').button('reset');
                            $('#btnApproveOrRejectBorrow').dequeue();
                        }else{
                            layer.alert(r.msg);
                            $('#btnApproveOrRejectBorrow').button('reset');
                            $('#btnApproveOrRejectBorrow').dequeue();
                        }
                    }
                });
            });

        },
        // reject_borrow: function (event){
        //     console.log("REJECT !"+ vm.approveMsg.docBorrowApplyId);
        //     $('#btnSaveOrUpdate').button('loading').delay(1000).queue(function() {
        //         var url = vm.docBorrowApply.docBorrowApplyId == null ? "doc_manage/docborrowapply/save" : "doc_manage/docborrowapply/update";
        //         $.ajax({
        //             type: "POST",
        //             url: baseURL + url,
        //             contentType: "application/json",
        //             data: JSON.stringify(vm.docBorrowApply),
        //             success: function(r){
        //                 if(r.code === 0){
        //                     layer.msg("操作成功", {icon: 1});
        //                     vm.reload();
        //                     $('#btnSaveOrUpdate').button('reset');
        //                     $('#btnSaveOrUpdate').dequeue();
        //                 }else{
        //                     layer.alert(r.msg);
        //                     $('#btnSaveOrUpdate').button('reset');
        //                     $('#btnSaveOrUpdate').dequeue();
        //                 }
        //             }
        //         });
        //     });
        //
        // },

    },

    mounted(){
        window.RejectSingle = this.clickReject;
        window.ApproveSingle = this.clickApprove;
    }
});