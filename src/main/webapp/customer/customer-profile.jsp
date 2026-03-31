<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/25/2026
  Time: 5:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Customer" %>

<%
    // Ensure user is logged in
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Retrieve customer object from request
    Customer customer = (Customer) request.getAttribute("customer");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Customer Profile</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
</head>
<body>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/customer-nav.jsp" />

<main>
    <div class="dashboard-container">
        <div class="dashboard-card">
            <h2>Edit Your Profile</h2>

            <form action="<%= request.getContextPath() %>/customerProfile" method="post">
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" name="firstName" id="firstName"
                           value="<%= customer != null ? customer.getFirstName() : "" %>"
                           required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" name="lastName" id="lastName"
                           value="<%= customer != null ? customer.getLastName() : "" %>"
                           required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="text" name="phone" id="phone"
                           value="<%= customer != null ? customer.getPhone() : "" %>">
                </div>

                <button type="submit" class="btn-dashboard">Update Profile</button>
            </form>
        </div>
    </div>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>