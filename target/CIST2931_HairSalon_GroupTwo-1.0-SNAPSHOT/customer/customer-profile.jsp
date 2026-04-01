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

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>
<!-- Navigation -->
<jsp:include page="/includes/customer-nav.jsp" />

<%
    String success = request.getParameter("success");
    if ("profileUpdated".equals(success)) {
%>
<div id="successPopup" style="
    position: fixed;
    top: 20%;
    left: 50%;
    transform: translateX(-50%);
    background-color: #e6ffe6;
    border: 1px solid green;
    padding: 20px;
    border-radius: 10px;
    text-align: center;
    z-index: 9999;
    box-shadow: 0 0 10px rgba(0,0,0,0.3);
">
    <p style="color: green; font-weight: bold;">
        Profile Updated Successfully! 🎉
    </p>
    <div style="margin-top: 10px;">
        <button onclick="document.getElementById('successPopup').style.display='none'"
                style="padding: 10px 20px; margin-right: 10px; cursor: pointer;">
            Stay Here
        </button>

        <button onclick="window.location.href='<%= request.getContextPath() %>/customerDashboard'"
                style="padding: 10px 20px; cursor: pointer;">
            Go to Dashboard
        </button>
    </div>
</div>

<script>
    setTimeout(() => {
        const popup = document.getElementById("successPopup");
        if (popup) popup.style.display = "none";
    }, 5000); // auto-hide after 5 seconds
</script>

<script>
    if (window.location.search.includes("success=profileUpdated")) {
        window.history.replaceState({}, document.title, window.location.pathname);
    }
</script>
<% } %>

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