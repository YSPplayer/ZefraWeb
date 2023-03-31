<%--
  Created by IntelliJ IDEA.
  User: YSP
  Date: 2023/3/31
  Time: 21:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
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
        <form action="demo.php" method="POST" name="name1"> 
        账号:<input type="text" maxlength="10" id="login_sub_input login_sub_text"> <br/>
        密码:<input type="password" id="login_sub_input login_sub_password"> <br/>
        </form>
         <div>

    </div>
</body>
</html>
