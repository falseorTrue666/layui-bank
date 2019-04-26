<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/4/12
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="<%=request.getContextPath()%>/js/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <script src="../layui/layui.js"></script>
</head>
<body>

<table id="demo" lay-filter="test"></table>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="fuKuan">付款</a>
</script>
<script>

    layui.use('table', function() {
        var table = layui.table;
        //第一个实例
        table.render({
            elem: '#demo'
            , url: '<%=request.getContextPath()%>/order/orderList.do' //数据接口
            , page: true, //开启分页
            toolbar: '#toolbarDemo'//开启工具栏
            , even: true
            , style: 'background-color: #5FB878; color: #fff;'
            , loading: true
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            , toolbar: 'default'
            , cols: [[ //表头
                {type: 'checkbox', fixed: 'left'},
                {field: 'orderId', title: '订单编号', fixed: 'center'},
                {field: 'bankNo', title: '卡号', sort: true, fixed: 'center'},
                {field: 'bankState', title: '卡状态', sort: true, fixed: 'center'},
                {field: 'orderMoney', title: '订单金额', sort: true, fixed: 'center'},
                {field: 'orderState', title: '订单状态', sort: true, fixed: 'center'/*, templet: '#sexTpl'*/},
                {field: 'orderDateStr', title: '订单创建时间', sort: true, fixed: 'center'},
                {field: '', title: '操作', edit: 'text', fixed: 'center', toolbar: '#barDemo'}

            ]]
            , done: function (res, curr, count) {
                //res 接口返回的信息
                $("[data-field='bankState']").children().each(function () {
                    if ($(this).text() === '1') {
                        $(this).text("(储蓄卡)存款")
                    } else if ($(this).text() === '2') {
                        $(this).text("(信用卡)还款")
                    }
                });
                $("[data-field='orderState']").children().each(function () {
                    if ($(this).text() === "1") {
                        $(this).text("已付款")
                    } else if ($(this).text() === "2") {
                        $(this).text("待付款")
                    } else if ($(this).text() === "3") {
                        $(this).text("该订单已失效")
                    }
                });
            }
        });

        //监听行工具事件
        table.on('tool(test)', function(obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                , layEvent = obj.event; //获得 lay-event 对应的值
            if (layEvent === 'fuKuan') {
                layer.open({
                    type: 2,
                    //skin: 'layui-layer-rim', //加上边框
                    area: ['590', '420px'], //宽高
                    content: '<%=request.getContextPath()%>/alipay.do?orderId='+data.orderId,
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                        var formSubmit = layer.getChildFrame('form', index);
                        var submited = formSubmit.find('button')[0];
                        submited.click();
                    }
                });
            }
        });
    });
</script>
<%--<script type="text/html" id="sexTpl">
    {{#  if(d.orderState === 1){ }}
    <span style="color: #45ff4f;">{{ d.orderState }}</span>
    {{#  } else
    if(d.orderState === 2){ }}
    <span style="color: #FF0000;">{{ d.orderState }}</span>
    {{#  }else
    if(d.orderState === 3){ }}
    <span style="color: #FFC125;">{{ d.orderState }}</span>
    {{#  } }}
</script>--%>
</body>
</html>
