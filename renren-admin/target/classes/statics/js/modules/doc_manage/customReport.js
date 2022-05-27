$(function () {
    function getTemplateList() {
        $.get(baseURL + "doc_manage/report/list", function(r){
            vm.templateList = r.data
        })
    }
    getTemplateList()

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
            startTime: null,
            endTime: null,
            templateId:''
        },
        datetimepicker:'',
        templateList:[]
    },
    methods: {
        create:function(){
            if(!this.datetimepicker){
                this.$message({
                    message: '请选择日期区间',
                    type: 'warning'
                });
                return
            }
            if(!this.q.templateId){
                this.$message({
                    message: '请选择模板',
                    type: 'warning'
                });
                return
            }
            this.q.startTime = this.datetimepicker[0]
            this.q.endTime = this.datetimepicker[1]
            $.get(baseURL + "doc_manage/report/create", this.q,function(r){
                // $("#echartDiv").html("")
                $("#echartDiv").html("<div id=\"main\" style=\"width: 100%;height:500px;\" ></div>")
                var myChart = echarts.init(document.getElementById('main'));
                var option;
                if(r.data.chartType == 1){
                    //饼状图
                    option = {
                        toolbox:{
                            feature:{
                                saveAsImage:{}
                            }
                        },
                        title: {
                            text: '',
                            subtext: '',
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
                                name: '数量',
                                type: 'pie',
                                radius: '50%',
                                data: r.data.data,
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
                }else if(r.data.chartType == 2){
                    var list = r.data.data
                    var xData = []
                    var seriesData = []
                    for(var i=0;i<list.length;i++){
                        xData.push(list[i].name)
                        seriesData.push(list[i].value)
                    }
                    //折线图
                    option = {
                        toolbox:{
                            feature:{
                                saveAsImage:{}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            data: xData
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                data: seriesData,
                                type: 'line'
                            }
                        ],
                        tooltip: {
                            trigger: 'axis',
                        },
                    };
                }else if(r.data.chartType == 3){
                    //柱状图
                    var list = r.data.data
                    var xData = []
                    var seriesData = []
                    for(var i=0;i<list.length;i++){
                        xData.push(list[i].name)
                        seriesData.push(list[i].value)
                    }
                    option = {
                        toolbox:{
                            feature:{
                                saveAsImage:{}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            data: xData
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                data: seriesData,
                                type: 'bar'
                            }
                        ],
                        tooltip: {
                            trigger: 'axis',
                        },
                    };
                }
                myChart.setOption(option);
            })
        },
    }
});
