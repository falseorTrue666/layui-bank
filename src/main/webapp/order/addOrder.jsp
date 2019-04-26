<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/4/13
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>金额</title>
    <script src="<%=request.getContextPath()%>/js/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <script src="../layui/layui.js"></script>
</head>
<body>
<form class="layui-form" action="/order/addOrder.do" lay-filter="example">
    <input type="hidden" name="bankNo" value="${bankNo}">
    <input type="hidden" name="bankState" value="${bankState}">
    <div class="layui-form-item layui-form-pane">
        <label class="layui-form-label">账户余额:</label>
        <div class="layui-input-inline">
            <input name="orderMoney" class="layui-input" type="text" autocomplete="off" lay-verify="required">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <!-- 隐藏提交按钮，在父层中调用 -->
            <button id="addDepartment" class="layui-btn" lay-filter="formVerify" lay-submit style="display: none">添加</button>
        </div>
    </div>
</form>
<script>
    layui.use(['form'],function () {
        var form = layui.form;
        form.on('submit(formVerify)',function (data) {
            // 提交成功后返回信息，关闭弹出层
            parent.layer.msg('操作成功', {
                icon: 1,
                time: 2000
            });

            //当你在iframe页面关闭自身时
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index);
            $(".layui-laypage-btn")[0].click()
            parent.window.location.reload();
        });
    });
</script>
</body>
</html>
