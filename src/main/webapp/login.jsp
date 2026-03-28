<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/8/2026
  Time: 4:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Salon Appointment System - Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
    <!-- Add icons for footer -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<jsp:include page="/includes/home-header.jsp" />

<main class="login-page">
    <section class="hero">
        <div class="hero-overlay">

            <div class="login-page-layout">
                <!-- Left side: Login / Register -->
                <div class="login-container">
                    <h2>Login / Register to Book</h2>
                    <p> Please login or create an account to book an appointment. </p>

                    <%
                        String error = request.getParameter("error");
                        if ("authFailed".equals(error)) {
                    %>
                    <p style="color:red;">Invalid email or password.</p>
                    <%
                    } else if ("invalidRole".equals(error)) {
                    %>
                    <p style="color:red;">Invalid role assigned. Please contact admin.</p>
                    <%
                        }
                    %>

                    <!-- Login form posts to LoginServlet -->
                    <form id="loginForm" method="post" action="login">
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" placeholder="you@example.com" required>
                        </div>

                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" placeholder="Enter password" required>
                        </div>

                        <button type="submit" class="btn btn-full">Login</button>
                    </form>

                    <!-- added new button for creating customer account  -->
                    <div>
                        <a href="<%= request.getContextPath() %>/register.jsp">
                            <button type="button" class="btn btn-full">Don't have an account?<br>
                                Create one!</button>
                        </a>
                    </div>
                </div>
                <!-- Right side: Business Hours -->
                <div class="business-hours-container">
                    <h2>Business Hours</h2>

                    <table class="hours-table">
                        <tr><td><strong>Sunday:</strong></td><td>Closed</td></tr>
                        <tr><td><strong>Monday:</strong></td><td>9:00 AM to 7:00 PM</td></tr>
                        <tr><td><strong>Tuesday:</strong></td><td>9:00 AM to 7:00 PM</td></tr>
                        <tr><td><strong>Wednesday:</strong></td><td>9:00 AM to 5:00 PM</td></tr>
                        <tr><td><strong>Thursday:</strong></td><td>9:00 AM to 7:00 PM</td></tr>
                        <tr><td><strong>Friday:</strong></td><td>9:00 AM to 7:00 PM</td></tr>
                        <tr><td><strong>Saturday:</strong></td><td>9:00 AM to 6:00 PM</td></tr>
                    </table>

                    <div style="margin-top: 20px;">
                        <a href="tel:7701234567"><button type="button" class="btn btn-full">Call Us</button></a>
                    </div>

                    <div style="margin-top: 15px;">
                        <a href="mailto:ChattechHairsalon@example.com"><button type="button" class="btn btn-full">E-mail Us</button></a>
                    </div>
                </div>

            </div>

        </div>
    </section>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>