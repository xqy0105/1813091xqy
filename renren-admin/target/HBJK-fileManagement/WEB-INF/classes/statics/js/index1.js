
$(function() {
    $(window).on('resize', function () {
        var $content = $('#larry-tab .layui-tab-content');
        $content.height($(this).height() - 140);
        $content.find('iframe').each(function () {
            $(this).height($content.height());
        });
    }).resize();
})

//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {item: {}},
    template: [
        '<li class="layui-nav-item" >',
        '<a v-if="item.type === 0" href="javascript:;">',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '<em class="layui-nav-more"></em>',
        '</a>',
        '<dl v-if="item.type === 0" class="layui-nav-child">',
        '<dd v-for="item in item.list" >',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</dd>',
        '</dl>',
        '<a v-if="item.type === 1" href="javascript:;" :data-url="item.url"><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></a>',
        '</li>'
    ].join('')
});

//注册菜单组件
Vue.component('menuItem', menuItem);
isquery=true;
var vm = new Vue({
    el: '#layui_layout',
    data: {
        user: {},
        menuList: {},
        password: '',
        newPassword: '',
        navTitle: "首页",
    },
    methods: {

        getMenuList: function () {
            $.getJSON("sys/menu/nav", function (r) {
                vm.menuList = r.menuList;
            });
        },
        getUser: function () {
            $.getJSON("sys/user/info?_" + $.now(), function (r) {
                vm.user = r.user;
                console.log(r.user);
                $.getJSON("sys/user/info/" + r.user.userId, function (r) {
                    vm.user = r.user;


                });

            });
        },
        passnamechange:function(e){
            if(e.id=="upassword"){
                vm.password=e.value;

            }
            else {
                vm.newPassword=e.value;
            }
        },
        updatePassword: function () {

            layer.open({
                type: 1,
                skin: 'layui-layer-molv',
                title: "修改密码",
                area: ['550px', '270px'],
                shadeClose: false,
                content: jQuery("#passwordLayer").html(),
                btn: ['修改', '取消'],
                btn1: function (index) {
                    // layer.alert($('#unewPassword').value);
                    // vm.password=$('#upassword').val();
                    // vm.newPassword=$('#unewPassword').val();
                    var data = "password=" + vm.password + "&newPassword=" + vm.newPassword;
                    $.ajax({
                        type: "POST",
                        url: "sys/user/password",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            // var mask = $(".layui-layer-shade");
                            // mask.appenTo(result.parent());
                            if (result.code == 0) {
                                layer.close(index);
                                layer.alert('修改成功', function (index) {
                                    location.reload();
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });


                }
            });
        },
        openTime: function (cond) {
            //cond:0 7天内超期；1 二周；2 30天内；3 超过30天
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
                // if (typeof(cond)!='string') cond="";
                var data={href: 'modules/doc_manage/vdocinfodetail.html?cond='+cond, icon: 'fa fa-industry', title: '档案统计'};
                navtab.tabAdd(data);
            });

        },
        borrowapply: function (cond) {
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
                var data={href: 'modules/doc_manage/docborrowapply.html?cond='+cond, icon: "fa fa-search", title: "档案申请审批"};
                navtab.tabAdd(data);
            });

        },
        borrowexp: function (cond) {
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
                var data={href: 'modules/doc_manage/docborrowlist.html?cond='+cond, icon: "fa fa-search", title: "档案借阅记录"};
                navtab.tabAdd(data);
            });

        }


    },
    created: function () {
        this.getMenuList();
        this.getUser();
    },
    updated:function(){
        // console.log($("#larry-side .layui-nav-item>a"));
        if($("#larry-side .layui-nav-item>a").length===0 || !isquery){
            return;
        }
        // console.log("执行")
        isquery=false;
        layui.config({
            base: 'statics/js/',
        }).use(['navtab','layer'], function(){
            window.jQuery = window.$ = layui.jquery;
            window.layer = layui.layer;
            var element = layui.element();
            var  navtab = layui.navtab({
                elem: '.larry-tab-box',
                closed:false
            });
            // console.log(navtab);
            $('#larry-nav-side').children('ul').find('li').each(function () {
                var $this = $(this);
                if ($this.find('dl').length > 0) {

                    var $dd = $this.find('dd').each(function () {
                        $(this).on('click', function () {
                            var $a = $(this).children('a');
                            var href = $a.data('url');
                            var icon = $a.children('i:first').data('icon');
                            var title = $a.children('span').text();
                            var data = {
                                href: href,
                                icon: icon,
                                title: title
                            }
                            navtab.tabAdd(data);
                        });
                    });
                } else {

                    $this.on('click', function () {
                        var $a = $(this).children('a');
                        var href = $a.data('url');
                        var icon = $a.children('i:first').data('icon');
                        var title = $a.children('span').text();
                        var data = {
                            href: href,
                            icon: icon,
                            title: title
                        }
                        navtab.tabAdd(data);
                    });
                }
            });
            $('.larry-side-menu').click(function () {
                var sideWidth = $('#larry-side').width();
                if (sideWidth === 200) {
                    $('#larry-body').animate({
                        left: '0'
                    });
                    $('#larry-footer').animate({
                        left: '0'
                    });
                    $('#larry-side').animate({
                        width: '0'
                    });
                } else {
                    $('#larry-body').animate({
                        left: '200px'
                    });
                    $('#larry-footer').animate({
                        left: '200px'
                    });
                    $('#larry-side').animate({
                        width: '200px'
                    });
                }
            });

        });
    },
    mounted() {
        window.passnamechange = this.passnamechange;
        // window.delSingle=this.del;
        // window.borrowSingle=this.clickBorrow;

    }
    // mounted:function (){
    //     // window.newTabs=this.NewTabs;
    //     var url = location.search; //获取url中"?"符后的字串 ('?modFlag=business&role=1')
    //     console.log(url);
    //     if (url.indexOf('?') !== -1) {
    //         // var str = url.substr(1) //substr()方法返回从参数值开始到结束的字符串；
    //         var data={href: "modules/doc_manage/document.html", icon: "fa fa-search", title: "档案查询"};
    //         // window.newTabs(data);
    //         this.NewTabs(data);
    //     }
    //
    // }
});


