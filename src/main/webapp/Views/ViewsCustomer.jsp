<%-- 
    Document   : ViewsCustomer
    Created on : Apr 8, 2024, 2:25:09 PM
    Author     : Admin
--%>
<%@page import="entity.Customer" %>
<%@page import="entity.UserAccount" %>
<%@page import="java.util.List" %>

<%@page import="entity.UserAccount"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Link to Bootstrap library -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <style>
            /* Add your custom styles here */
            body {
                padding-top: 60px;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top">
            <div class="container">
                <a class="navbar-brand" href="#">Your Logo</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/ASM2/user/list">Thêm Quản lý</a>
                        </li>
                        <!-- Add more menu items as needed -->
                    </ul>
                    <!-- You can place additional content here, such as search bar or buttons -->
                </div>
            </div>
        </nav>

        <div class="container mt-5">
            <h1>Quản Lý Khách Hàng</h1>
            <form action="/ASM2/user/them" method="post">

                <div class="form-group">
                    <input type="hidden" class="form-control" name="ip_id" value="${customers.getId()}">
                    <label for="name">Tên:</label>
                    <input type="text" class="form-control" name="ip_nameeee" value="${customers.getName()}">

                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="text" class="form-control" id="email" name="ip_email" value="${customers.getEmail()}">
                </div>
                <div class="form-group">
                    <label for="selectbox">Người Tạo:</label>
                    <select class="form-control" id="selectbox" name="ip_selectbox">
                        <%
                            List<UserAccount> lstuser = (List<UserAccount>) request.getAttribute("userAccounts");
                            Customer c = (Customer) request.getAttribute("customers");
                            for (UserAccount u : lstuser) {
                        %>
                        <option value="<%=u.getId() %>"<% if(c != null){ if(c.getUser_id() == u.getId()){%>selected<% }}%>><%= u.getName()%></option>
                        <%
                            }
                        %>
                    </select>

                </div>
                <button type="submit" name="action" value="them" class="btn btn-primary">Thêm</button>
                <button type="submit" name="action" value="capnhap" class="btn btn-primary">Cập nhật</button>
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
            <br><br>
            <table class="table table-bordered">
                <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Tên</th>
                        <th>Email</th>
                        <th>Người Tạo</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Customer> lstcus = (List<Customer>) request.getAttribute("Customers");
                        for (Customer s : lstcus) {
                            UserAccount user = (UserAccount) request.getAttribute("user_" + s.getUser_id());
                    %>
                    <tr>
                        <td><%= s.getId()%></td>
                        <td><%= s.getName()%></td>
                        <td><%= s.getEmail()%></td>
                        <td><%= user.getUsername()%></td>
                        <td>
                            <a href="/ASM2/user/capnhap?id=<%= s.getId()%>" class="btn btn-sm btn-primary">Xem</a>
                            <button onclick="check(<%= s.getId()%>)" class="btn btn-sm btn-danger">Xóa</button>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>

        <footer class="footer mt-auto py-3">
            <div class="container">
                <span class="text-muted">Place footer content here.</span>
            </div>
        </footer>

        <!-- Load Bootstrap JavaScript -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
                                function check(id) {
                                    if (confirm("Bạn có chắc chắn muốn xóa?")) {
                                        window.location.href = "/ASM2/user/xoa?id=" + id;
                                    }
                                }
        </script>
    </body>
</html>
