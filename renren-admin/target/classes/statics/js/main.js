

var vm2 = new Vue({
    el:'#echart',
    data:{
        logList:[],
        applyList:[]

    },
    methods: {
        query: function () {
            vm2.reload();
        },
        openTab: function (data) {
            layui.config({
                base: 'statics/js/',
            }).use(['navtab', 'layer'], function () {
                window.jQuery = window.$ = layui.jquery;
                window.layer = layui.layer;
                var element = layui.element();
                var navtab = layui.navtab({
                    elem: '.larry-tab-box',
                    closed: false
                });
                navtab.tabAdd(data);
            });

        },

        pieChart:function (){
            // 基于准备好的dom，初始化echarts实例
            // console.log("hhhh");
            //e.log(this.logList);
            var myChart = echarts.init(document.getElementById('main1'));
            // 指定图表的配置项和数据
            var data=JSON.parse(JSON.stringify(this.logList));
            // console.log(data);
            option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '超期',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: data
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
            myChart.on("click",function (e){
                //e.dataIndex对应
                //0 7天内超期；1 二周；2 30天内；3 超过30天
                vm.openTime(e.dataIndex);

            });
        },
        pieChartapply:function (){
            // 基于准备好的dom，初始化echarts实例
            // console.log("hhhh");
            // console.log(this.logList);
            var myChart = echarts.init(document.getElementById('mainapply'));
            // 指定图表的配置项和数据
            console.log(JSON.stringify(this.applyList)) ;
            var data=JSON.parse(JSON.stringify(this.applyList));
            // console.log(data);
            option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '借阅申请',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: data
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
            myChart.on("click",function (e){
                vm.borrowapply(e.name);

            });
        },
        pieChartborrow:function (){
            // 基于准备好的dom，初始化echarts实例
            // console.log("hhhh");
            // console.log(this.logList);
            var myChart = echarts.init(document.getElementById('mainborrow'));
            // 指定图表的配置项和数据
            console.log(JSON.stringify(this.borrowList)) ;
            var data=(this.borrowList);
            // console.log(data);
            option = {
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: '借阅情况',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: data
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表
            myChart.setOption(option);
            myChart.on("click",function (e){
                vm.borrowexp(e.name);
            });

        },
        getData:function (){
            // this.logList=[];
            var that=this;
            //http://127.0.0.1:8080/docmanagement/doc_manage/outdatesummary/summary
            var url="doc_manage/outdatesummary/summary";
            $.ajax({
                type: "GET",
                url:  url,
                contentType: "application/json",
                success: function(res){
                    var datas=res.page;
                    var dataList=[];
                    for(var i=0;i<datas.length;i++){
                        var data={};
                        data.value=datas[i].totalSize;
                        data.name=datas[i].summaryname;
                        dataList.push(data);
                    }
                    that.logList=dataList;
                    vm2.pieChart();

                }
            });


        },
        getApplyData:function (){
            // this.logList=[];
            var that=this;
            //http://127.0.0.1:8080/docmanagement/doc_manage/outdatesummary/summary
            var url="doc_manage/outdatesummary/commonsummary";
            $.ajax({
                type: "GET",
                data:{
                    querypara:'borrowapply'
                },
                url:  url,
                contentType: "application/json",
                success: function(res){
                    var datas=res.page;
                    var dataList=[];
                    for(var i=0;i<datas.length;i++){
                        var data={};
                        data.value=datas[i].totalSize;
                        data.name=datas[i].summaryname;
                        dataList.push(data);
                    }
                    that.applyList=dataList;
                    vm2.pieChartapply();

                }
            });


        },
        getBorrowData:function (){
            // this.logList=[];
            var that=this;
            //http://127.0.0.1:8080/docmanagement/doc_manage/outdatesummary/summary
            var url="doc_manage/outdatesummary/commonsummary";
            $.ajax({
                type: "GET",
                data:{
                    querypara:'borrowexp'
                },
                url:  url,
                contentType: "application/json",
                success: function(res){
                    var datas=res.page;
                    var dataList=[];
                    for(var i=0;i<datas.length;i++){
                        var data={};
                        data.value=datas[i].totalSize;
                        data.name=datas[i].summaryname;
                        dataList.push(data);
                    }
                    that.borrowList=dataList;
                    vm2.pieChartborrow();

                }
            });


        }

    },

    mounted() {
        this.getData();
        this.getApplyData();
        this.getBorrowData();
        // window.newTabs=this.NewTabs;


    }
});



