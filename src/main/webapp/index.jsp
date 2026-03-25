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
    <!-- Add icons for footer -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header class="site-header">
    <div class="header-left">
        <%@ include file="/includes/logo.jsp" %>
    </div>
        <span class="site-title">ChattTech HAIR SALON</span>
</header>

<main class = "flex-container">
    <section class="hero" style="background-image: url('images/BW_hair style.png');">
        <div class="hero-overlay">

            <!-- icons-links for services and artists -->
            <div class="hero-icons">
                <a href="<%= request.getContextPath() %>/services.jsp" class="hero-icon">
                    <img src="<%= request.getContextPath() %>/images/services-nobg.png" alt="Services">
                    <span>SERVICES</span>
                </a>

                <a href="<%= request.getContextPath() %>/artists.jsp" class="hero-icon">
                    <img src="<%= request.getContextPath() %>/images/artists-nobg.png" alt="Artists">
                    <span>ARTISTS</span>
                </a>
            </div>

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

    <!-- WELCOME SECTION -->
    <section class="welcome-section">
        <h2>WELCOME</h2>
        <div class="welcome-text">
            <p>Our mission from day one has been to offer accessible hair care for students and instructors that elevates their style.
                We serve Marietta, North Metro, Woodstock, Canton, Mountain View Campuses, and the rest of the Metro-Atlanta area.
            </p>
            <br>
            <p>We are known for our expert color and cutting techniques.
                ChattTech Hair Salon provides customized treatments from Kérastase and L’Oreal Professionnel
                that keep your needs and the health of your hair at the forefront. We have several Hair Extension artists
                on staff.
            </p>
            <br>
            <p>We love what we do, and the ChattTech Hair Salon team continues to fine-tune our abilities
                to ensure you enjoy an exceptional salon experience.
                Time at ChattTech Hair Salon is a creative collaboration between guests and our skilled stylists and make-up artists.
                We look forward to serving you.
            </p>
        </div>
    </section>

</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>

