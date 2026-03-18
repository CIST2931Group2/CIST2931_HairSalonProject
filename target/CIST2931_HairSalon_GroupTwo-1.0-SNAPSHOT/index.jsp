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
    <title>Salon Appointment System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">ChattTech HAIR SALON</span>
</header>

<main>
    <section class="hero" style="background-image: url('images/BW_hair style.png');">
        <div class="hero-overlay">
            <div class="login-container landing-container">
                <h2>Welcome to Our Salon</h2>
                <p>
                    Book appointments, <br>
                    manage schedules, <br>
                    and connect with your stylist easily.
                </p>
                <a class="btn btn-full" href="login.jsp">LOGIN <br> BOOKING ONLINE</a>
            </div>
        </div>
    </section>
</main>

</body>
</html>

