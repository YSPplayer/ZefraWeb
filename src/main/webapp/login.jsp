<%--
  Created by IntelliJ IDEA.
  User: YSP
  Date: 2023/3/31
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html><!--使用html5版本，不加的话效果不一样-->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <!--导入login的css-->
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
  <!--登录主界面-->
    <div class="login_main">
       <!--设置登录界面的左边框图片-->
        <img src="pics/login/01.jpg"/>
        <!--登录副页面-->
         <div class="login_sub">
         <!--登录提示-->
         <h2>登录</h2>
        <form action="demo.php" method="POST" name="name1"> 
         <!--登录副页面 -->
          <span class="login_characters_text"><h4>账号:</h4></span><input type="text"  maxlength="10"  id="login_sub_input">
          <span class="login_characters_password"><h4>密码:</h4></span><input type="password" id="login_sub_input">
          <input type="button" id="login_sub_button_sign" value="登录">
          <input type="button" id="login_sub_button_logon" value="注册">
          <span id="login_sub_link"><a href="#">忘记密码?</a></span>
        </form>
         <div>

    </div>
</body>
</html>
