<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:51 PM
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
    Salon Appointment System
</header>

<main>
    <div class="login-container">
        <h2>Login</h2>
        <form id="loginForm">
            <div>
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="you@example.com" required>
            </div>

            <div>
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter password" required>
            </div>

            <button type="submit">Login</button>
            <div class="error" id="errorMessage"></div>
        </form>
    </div>
</main>

<script>
    document.getElementById("loginForm").addEventListener("submit", function(e) {
        e.preventDefault();

        const email = document.getElementById("email").value.toLowerCase();
        const password = document.getElementById("password").value;

        let role = "";

        // Mock login logic based on email
        if(email.includes("customer")) {
            role = "CUSTOMER";
        } else if(email.includes("hairdresser")) {
            role = "HAIRDRESSER";
        } else if(email.includes("admin")) {
            role = "ADMIN";
        }

        // Redirect based on role
        switch(role) {
            case "CUSTOMER":
                window.location.href = "customerDashboard.jsp";
                break;
            case "HAIRDRESSER":
                window.location.href = "stylistDashboard.jsp";
                break;
            case "ADMIN":
                window.location.href = "adminSchedule.jsp";
                break;
            default:
                document.getElementById("errorMessage").innerText =
                    "Invalid login. Use a mock email like customer@example.com";
        }
    });
</script>

</body>
</html>
