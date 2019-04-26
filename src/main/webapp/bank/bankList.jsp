<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>table模块快速使用</title>
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
    <a class="layui-btn layui-btn-xs" lay-event="save">增加</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>

    {{#  if(d.bankType ==1){ }}
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="cun">存款</a>
    {{#  } }}
    {{#  if(d.bankType ==2){ }}
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="huan">还款</a>
    {{#  } }}

    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="qu">取款</a>
    <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="zhuan">转账</a>
</script>

<script>

    layui.use('table', function(){
        var table = layui.table;
        //第一个实例
        table.render({
            elem: '#demo'
            ,url: '<%=request.getContextPath()%>/bank/queryBankList.do' //数据接口
            ,page: true, //开启分页
            toolbar: '#toolbarDemo'//开启工具栏
            ,even:true
            ,style:'background-color: #5FB878; color: #fff;'
            ,loading:true
            ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
            ,toolbar: 'default'
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'},
                {field: 'bankId', title: '银行卡编号',  sort: true, fixed: 'center'},
                {field: 'bankNo', title: '卡号',edit: 'text', sort: true, fixed: 'center'},
                {field: 'bankType', title: '卡种',edit: 'text', sort: true, fixed: 'center'},
                {field: 'bankBelong', title: '所属银行',edit: 'text', sort: true, fixed: 'center'},
                {field: 'bankName', title: '开户人',edit: 'text', sort: true, fixed: 'center'},
                {field: 'bankMoney', title: '账户余额',edit: 'text', sort: true, fixed: 'center'},
                {field: 'bankDateStr', title: '开户时间',edit: 'text', sort: true, fixed: 'center'},
                {field: '', title: '操作',edit: 'text',  fixed: 'center',toolbar: '#barDemo'}

            ]]
            ,done:function(res, curr, count) {
                //res 接口返回的信息
                $("[data-field='bankType']").children().each(function () {
                    if ($(this).text() === '1') {
                        $(this).text("储蓄卡")
                    } else if ($(this).text() === '2') {
                        $(this).text("信用卡")
                    }
                });
                $("[data-field='bankBelong']").children().each(function () {
                    if ($(this).text() === '1') {
                        $(this).text("中国银行")
                    } else if ($(this).text() === '2') {
                        $(this).text("建设银行")
                    }else if ($(this).text() === '3') {
                        $(this).text("民生银行")
                    }else if ($(this).text() === '4') {
                        $(this).text("招商银行")
                    }
                });
            }
        });

        //监听头工具栏事件
        table.on('toolbar(test)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    layer.open({
                        type: 2,
                        //skin: 'layui-layer-rim', //加上边框
                        area: ['590', '420px'], //宽高
                        content:'<%=request.getContextPath()%>/bank/openAddPage.do',
                        btn:['确定','取消'],
                        yes:function(index,layero){
                            var formSubmit = layer.getChildFrame('form',index);
                            var submited = formSubmit.find('button')[0];
                            submited.click();
                        }
                    });
                    break;
                case 'update':
                    if(data.length === 0){
                        layer.msg('请选择一行');
                    } else if(data.length > 1){
                        layer.msg('只能同时编辑一个');
                    } else {
                        layer.alert('编辑 [id]：'+ checkStatus.data[0].bankId);

                    }
                    break;
                case 'delete':
                    if(data.length === 0){
                        layer.msg('请选择一行');
                    } else {
                        layer.msg('删除');
                        layer.confirm('真的删除[id为'+data[0].bankId+']行么', function(index){

                            $.ajax({
                                type:'post',
                                url:'<%=request.getContextPath()%>/bank/deleteBank.do',
                                data:{'bankId':data[0].bankId},
                                dataType:'json',
                                success:function(data){
                                    if(data.code==0){
                                        layer.msg(data.msg,{icon:1,time:1000})
                                        window.location.reload();//刷新页面
                                    }else{
                                        layer.msg('操作失败',{icon:2,time:1000})
                                    }
                                }
                            })
                        });
                    }
                    break;
            };
        });

        //监听单元格编辑
        table.on('edit(test)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值
                ,field = obj.field; //得到字段
            //layer.msg('[ID: '+ data.stuId +'] ' + field + ' 字段更改为：'+ value);
            $.ajax({
                type:'get',
                url:'<%=request.getContextPath()%>/bank/addOrUpdate.do',
                data:{'bankId':data.bankId,'bankNo':data.bankNo,'bankType':data.bankType,'bankBelong':data.bankBelong,'bankName':data.bankName,'bankMoney':data.bankMoney,'bankDate':data.bankDateStr},
                dataType:'json',
                success:function(data){
                    if(data.code==0){
                        layer.msg(data.msg,{icon:1,time:1000})
                    }else{
                        layer.msg(data.msg,{icon:2,time:1000})
                    }
                    window.location.reload();//刷新页面
                }
            })
        });

        //监听行工具事件
        table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'save'){
                layer.open({
                    type: 2,
                    //skin: 'layui-layer-rim', //加上边框
                    area: ['590', '420px'], //宽高
                    content:'<%=request.getContextPath()%>/bank/openAddPage.do',
                    btn:['确定','取消'],
                    yes:function(index,layero){
                        var formSubmit = layer.getChildFrame('form',index);
                        var submited = formSubmit.find('button')[0];
                        submited.click();
                    }
                });
            } else if(layEvent === 'del'){
                layer.confirm('真的删除[id为'+data.bankId+']行么', function(index){

                    $.ajax({
                        type:'post',
                        url:'<%=request.getContextPath()%>/bank/deleteBank.do',
                        data:{'bankId':data.bankId},
                        dataType:'json',
                        success:function(data){
                            if(data.code==0){
                                layer.msg(data.msg,{icon:1,time:1000})
                                window.location.reload();//刷新页面
                            }else{
                                layer.msg('操作失败',{icon:2,time:1000})
                            }
                        }
                    })
                });
            } else if(layEvent === 'cun'){
                layer.msg('存款');
                layer.open({
                    type: 2,
                    //skin: 'layui-layer-rim', //加上边框
                    area: ['390', '220px'], //宽高
                    content: '<%=request.getContextPath()%>/order/openCunOrUpdate.do?bankNo='+data.bankNo+'&bankState=1',
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                        var formSubmit = layer.getChildFrame('form', index);
                        var submited = formSubmit.find('button')[0];
                        submited.click();
                    }
                });
            } else if(layEvent === 'huan'){
                layer.msg('还款');
                layer.open({
                    type: 2,
                    //skin: 'layui-layer-rim', //加上边框
                    area: ['390', '220px'], //宽高
                    content: '<%=request.getContextPath()%>/order/openCunOrUpdate.do?bankNo='+data.bankNo+'&bankState=2',
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                        var formSubmit = layer.getChildFrame('form', index);
                        var submited = formSubmit.find('button')[0];
                        submited.click();
                    }
                });
            } else if(layEvent === 'qu'){
                layer.msg('取款');
                layer.open({
                    type: 2,
                    //skin: 'layui-layer-rim', //加上边框
                    area: ['390', '220px'], //宽高
                    content: '<%=request.getContextPath()%>/bank/openQu.do?bankId='+data.bankId+'&bankMoney='+data.bankMoney,
                    btn: ['确定', '取消'],
                    yes: function (index, layero) {
                        var formSubmit = layer.getChildFrame('form', index);
                        var submited = formSubmit.find('button')[0];
                        submited.click();
                    }
                });
            } else if(layEvent === 'zhuan'){
                layer.msg('转账');
            }
        });
    });

</script>
</body>
</html>
