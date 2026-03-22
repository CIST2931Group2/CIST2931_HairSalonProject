<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<header class="site-header">
  <div class="header-container">

    <!-- Left side: Logo -->
    <div class="header-left">
      <img src="<%= request.getContextPath() %>/images/CIST2931_HairSalonLogo.png"
           alt="Salon Logo"
           class="header-logo">
    </div>

    <div class="header-center">
      <div class="header-text">
        <h1 class = "header-text">Salon Appointment System</h1>
        <p class="tagline">Book appointments, manage schedules, and edit your profile</p>
      </div>
    </div>

    <!-- Right side: Buttons -->
    <div class="header-right">
      <a href="<%= request.getContextPath() %>/index.jsp" class="btn-header">Home</a>
      <% if (session.getAttribute("userId") == null) { %>
      <a href="<%= request.getContextPath() %>/login" class="btn-header">Login</a>
      <% } else { %>
      <a href="<%= request.getContextPath() %>/logout" class="btn-header logout-btn">Logout</a>
      <% } %>
    </div>

  </div>
</header>