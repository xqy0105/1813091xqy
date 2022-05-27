$(function () {


});
var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        sysTemplate: {
            deptId:null,
            deptName:null
        },
        q:{
            year: null
        },
        value3:''
    },
    methods: {
        tableData(){
            $("#jqGrid").jqGrid({
                url: baseURL + 'doc_manage/report/yearList?year='+this.q.year,
                datatype: "json",
                colModel: [
                    { label: '部门名称', name: 'name', index: 'name', width: 80 },
                    { label: '录著数', name: 'num1', index: 'num1', width: 80 },
                    { label: '借阅数', name: 'num2', index: 'num2', width: 80 , formatter: function(value, options, row){
                            return value == null?0:value;
                        }},
                    { label: '归还数', name: 'num3', index: 'num3', width: 80 , formatter: function(value, options, row){
                            return value == null?0:value;
                        }},
                    { label: '转出数', name: 'num4', index: 'num4', width: 80 , formatter: function(value, options, row){
                            return value == null?0:value;
                        } },
                    { label: '转入数', name: 'num5', index: 'num4', width: 80 , formatter: function(value, options, row){
                            return value == null?0:value;
                        } },
                    { label: '交付数', name: 'num6', index: 'num4', width: 80 , formatter: function(value, options, row){
                            return value == null?0:value;
                        } },
                    { label: '修改数', name: 'num7', index: 'num4', width: 80 , formatter: function(value, options, row){
                            return value == null?0:value;
                        } },
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
        },
        create:function(){
            if(!this.value3){
                this.$message({
                    message: '请选择年份',
                    type: 'warning'
                });
                return
            }
            this.q.year = this.value3
            this.tableData()
            this.echartHandler();
            this.echartHandler1()
            this.echartHandler2()
            this.echartHandler3()
            this.echartHandler4()
            this.echartHandler5()
        },
        echartHandler(){
            $.get(baseURL + "doc_manage/report/echartHandler", function(r){
                // $("#echartDiv").html("")
                $("#echartDiv").html("<div id=\"main\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main'));
                var option;
                option = {
                    title:{
                        text: '近五年档案总数变化',
                        x:'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: r.data.yearList
                    },
                    tooltip: {
                        trigger: 'axis'
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            data: r.data.numsList,
                            type: 'line'
                        }
                    ]
                };
                myChart.setOption(option);
            })
        },
        echartHandler1(){
            $.get(baseURL + "doc_manage/report/echartHandler1", this.q,function(r){
                // $("#echartDiv").html("")
                $("#echartDiv1").html("<div id=\"main1\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main1'));
                var option;
                option = {
                    title: {
                        text: '各个部门档案数',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left'
                    },
                    series: [
                        {
                            name: 'Access From',
                            type: 'pie',
                            radius: '50%',
                            data: r.data.list,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                myChart.setOption(option);
            })
        },
        echartHandler2(){
            $.get(baseURL + "doc_manage/report/echartHandler2", this.q,function(r){
                // $("#echartDiv").html("")
                $("#echartDiv2").html("<div id=\"main2\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main2'));
                var option;
                //柱状图
                option = {
                    title: {
                        text: '本年度档案借阅归还数',
                        x:'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '借阅',
                            data: r.data.borrowApplyList,
                            type: 'bar'
                        },
                        {
                            name: '归还',
                            data: r.data.borrowNumList,
                            type: 'bar'
                        }
                    ]
                };
                myChart.setOption(option);
            })
        },
        echartHandler3(){
            $.get(baseURL + "doc_manage/report/echartHandler3", this.q,function(r){
                // $("#echartDiv").html("")
                $("#echartDiv3").html("<div id=\"main3\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main3'));
                var option;
                //柱状图
                option = {
                    title: {
                        text: '本年度档案转出转入数',
                        x:'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '转入',
                            data: r.data.transableList,
                            type: 'bar'
                        },
                        {
                            name: '转出',
                            data: r.data.transInList,
                            type: 'bar'
                        }
                    ]
                };
                myChart.setOption(option);
            })
        },
        echartHandler4(){
            $.get(baseURL + "doc_manage/report/echartHandler4", this.q,function(r){
                // $("#echartDiv").html("")
                $("#echartDiv4").html("<div id=\"main4\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main4'));
                var option;
                //柱状图
                option = {
                    title: {
                        text: '本年度档案交付数',
                        x:'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '交付',
                            data: r.data.confimApplyList,
                            type: 'bar'
                        }
                    ]
                };
                myChart.setOption(option);
            })
        },
        echartHandler5(){
            $.get(baseURL + "doc_manage/report/echartHandler5", this.q,function(r){
                // $("#echartDiv").html("")
                $("#echartDiv5").html("<div id=\"main5\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main5'));
                var option;
                //柱状图
                option = {
                    title: {
                        text: '本年度档案修改数',
                        x:'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    xAxis: {
                        type: 'category',
                        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        {
                            name: '修改',
                            data: r.data.modifyList,
                            type: 'bar'
                        }
                    ]
                };
                myChart.setOption(option);
            })
        },
    }
});
