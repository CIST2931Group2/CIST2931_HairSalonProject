<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/27/2026
  Time: 7:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="site-header">
    <div class="header-left">
        <a href="<%= request.getContextPath() %>/index.jsp">
            <img src="<%= request.getContextPath() %>/images/CIST2931_HairSalonLogo.png"
                 alt="Hair Salon Logo" class="header-logo">
        </a>
    </div>
    <span class="site-title">ChattTech HAIR SALON</span>

    <div class="nav-right">
        <!-- Home -->
        <a href="<%= request.getContextPath() %>/index.jsp" class = "btn">Home</a>

        <!-- Services -->
        <a href="<%= request.getContextPath() %>/services.jsp" class = "btn">Services</a>

        <!-- Artists -->
        <a href="<%= request.getContextPath() %>/artists.jsp" class = "btn">Artists</a>

        <!-- Help -->
        <a href="<%= request.getContextPath() %>/customer-help.jsp" class = "btn">Help</a>
    </div>

</header>
