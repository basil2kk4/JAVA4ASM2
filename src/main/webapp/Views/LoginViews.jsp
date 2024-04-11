<%-- 
    Document   : LoginViews
    Created on : Apr 8, 2024, 9:41:38 AM
    Author     : Admin
--%>
<%@page import= "java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
    <!-- Link CSS của Bootstrap -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Định dạng cho phần form */
        .login-form {
            width: 300px;
            margin: auto;
            margin-top: 100px;
        }
    </style>
</head>
<body>

<div class="login-form">
    <h2 class="text-center">Login</h2>
    <!-- Form đăng nhập -->
    <form action="/ASM2/user/login" method="POST">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" name="ip_usernamelogin">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" name="ip_passwordlogin">
        </div>
        <button type="submit" class="btn btn-primary btn-block">Login</button>
         <% List<String> errors = (List<String>) request.getAttribute("errors");
       if (errors != null && !errors.isEmpty()) {
           for (String error : errors) {
               %>
               <div class="error"><%= error %></div>
               <%
           }
       }
    %>
    </form>
</div>

<!-- Link JS của Bootstrap (tùy chọn nếu cần sử dụng các tính năng JS của Bootstrap) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
