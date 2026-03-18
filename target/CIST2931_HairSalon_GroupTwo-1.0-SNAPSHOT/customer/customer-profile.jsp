<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/25/2026
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Ensure user is logged in
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Retrieve user object
    Object user = session.getAttribute("user");
%>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="/includes/logo.jsp" %>
    <title>Customer Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<div class="form-container">
    <h2>Edit Your Profile</h2>

    <form action="<%= request.getContextPath() %>/customerProfile" method="post">
        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" id="firstName"
               value="<%= user != null ? ((com.example.cist2931_hairsalon_grouptwo.model.User)user).getFirstName() : "" %>"
               required>

        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" id="lastName"
               value="<%= user != null ? ((com.example.cist2931_hairsalon_grouptwo.model.User)user).getLastName() : "" %>"
               required>

        <label for="email">Email:</label>
        <input type="email" name="email" id="email"
               value="<%= user != null ? ((com.example.cist2931_hairsalon_grouptwo.model.User)user).getEmail() : "" %>"
               required>

        <button type="submit">Update Profile</button>
    </form>
</div>

</body>
</html>