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
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<main>
    <section class="hero">
        <div class="hero-overlay">
            <div class="login-container">
                <h2>Login</h2>

                <!-- Login form posts to LoginServlet -->
                <form id="loginForm" method="post" action="login">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" placeholder="you@example.com" required>
                    </div>

                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Enter password" required>
                    </div>

                    <button type="submit" class="btn btn-full">Login</button>
                </form>

            </div>
        </div>
    </section>
</main>

</body>
</html>