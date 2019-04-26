<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/4/12
  Time: 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加、修改</title>
    <script src="<%=request.getContextPath()%>/js/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="../layui/css/layui.css" media="all">
    <script src="../layui/layui.js"></script>
</head>
<body>
<form class="layui-form" action="/bank/addOrUpdate.do" lay-filter="example">
    <div class="layui-form-item layui-form-pane">
        <label class="layui-form-label">卡号:</label>
        <div class="layui-input-block">
            <input name="bankNo" class="layui-input" type="text" placeholder="" value="${bankNo}" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item layui-form-pane">
        <label class="layui-form-label">开户人:</label>
        <div class="layui-input-block">
            <input name="bankName" class="layui-input" type="text" placeholder=""  autocomplete="off" lay-verify="bankName">
        </div>
    </div>

    <div class="layui-form-item layui-form-pane">
        <label class="layui-form-label">卡种:</label>
        <div class="layui-input-block">
            <label onclick="danjiFun(1)" id="chu"><input name="bankType" title="储蓄卡"  type="radio" checked="" value="1" ></label>
            <label onclick="danjiFun(2)" id="xin"><input name="bankType" title="信用卡"  type="radio" value="2"></label>
        </div>
    </div>

    <div class="layui-form-item layui-form-pane">
        <label class="layui-form-label">账户余额:</label>
        <div class="layui-input-inline">
            <input name="bankMoney" class="layui-input" type="text" value="0" id="bankMoney" autocomplete="off" lay-verify="required" readonly="true">
        </div>
    </div>

    <div class="layui-form-item layui-form-pane">
        <label class="layui-form-label">所属银行:</label>
        <div class="layui-input-block">
            <select name="bankBelong" lay-filter="aihao">
                <option value="1">中国银行</option>
                <option value="2">建设银行</option>
                <option value="3">民生银行</option>
                <option value="4">招商银行</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <!-- 隐藏提交按钮，在父层中调用 -->
            <button id="addDepartment" class="layui-btn" lay-filter="formVerify" lay-submit style="display: none">添加</button>
        </div>
    </div>
    </div>
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
    function danjiFun(obj) {
        if(obj == 1){
            $("#bankMoney").val(0);
            //document.getElementById("bankMoney").value = 0;
        }else{
            $("#bankMoney").val(50000);
            //document.getElementById("bankMoney").value = 50000;
        }
    }
</script>
</form>
</body>
</html>
