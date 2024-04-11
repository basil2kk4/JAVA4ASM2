<%@page import="entity.UserAccount" %>
<%@page import= "java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Form</title>
  <!-- Bootstrap CSS -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">Your Logo</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/ASM2/user/hienthi">Thêm Khách Hàng</a>
            </li>
            <!-- Add more menu items as needed -->
        </ul>
        <!-- You can place additional content here, such as search bar or buttons -->
    </div>
</nav>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <h2 class="mb-4">Quản Lý ADMIN</h2>
        
        <form id="userForm" action="/ASM2/user/add" method="POST">
          <div class="form-group">
              <input type="hidden" class="form-control" name="ip_userId" value="${data.getId()}">
          </div>
          <div class="form-group">
            <label for="Name">Name</label>
            <input type="text" class="form-control" name="ip_name" value="${data.getName()}">
          </div>
          <div class="form-group">
            <label for="userName">User Name</label>
            <input type="text" class="form-control" name="ip_userName" value="${data.getUsername()}">
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" name="ip_PassWord" value="${data.getPassword()}">
          </div>
          <button type="submit" class="btn btn-primary" name="action" value="add">Add</button>
          <button type="submit" class="btn btn-primary" name="action" value="update">Update</button>
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
    </div>

    <!-- Table to display data -->
    <div class="row mt-5">
      <div class="col-md-8 offset-md-2">
        <h2 class="mb-4">QUẢN LÝ ADMIN</h2>
        <table class="table">
          <thead class="thead-light">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>User Name</th>
              <th>Function</th>
            </tr>
          </thead>
          <tbody>
            <% 
            List<UserAccount> userAccounts = (List<UserAccount>) request.getAttribute("userAccounts");
            for(UserAccount userAccount : userAccounts){
            %>
            <tr>
              <td><%=userAccount.getId()%></td>
              <td><%=userAccount.getName()%></td>
              <td><%=userAccount.getUsername()%></td>
              <td>
                <a href="/ASM2/user/update?id=<%=userAccount.getId()%>" class="btn btn-primary btn-sm">View</a>
                <button onclick="check(<%=userAccount.getId()%>)" class="btn btn-danger btn-sm">Delete</button>
              </td>
            </tr>
            <% } %>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <!-- Footer -->
  <footer class="footer mt-auto py-3 bg-light">
    <div class="container">
      <span class="text-muted">Place footer content here.</span>
    </div>
  </footer>
  <!-- End Footer -->

  <!-- Bootstrap JS (optional) -->
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

  <!-- jQuery (required for Bootstrap) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</body>
</html>

<script>
    function check(id) {
        if(confirm("Are you sure you want to delete?")){
            window.location.href="/ASM2/user/delete?id="+id;
        }
    }
</script>
