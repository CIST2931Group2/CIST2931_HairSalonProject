<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<nav class="nav">
    <!-- LEFT SIDE -->
    <div class="nav-left">
        <!-- Dashboard -->
        <a href="<%= request.getContextPath() %>/adminManageHairdressers" class = "btn btn-small">Dashboard</a>

        <!-- Create Weekly schedules and blocks (link to JSP page that will submit to servlet) -->
        <a href="<%= request.getContextPath() %>/adminSchedule" class = "btn btn-small">Weekly Schedules</a>

        <!-- Help -->
        <a href="<%= request.getContextPath() %>/admin-help.jsp" class = "btn btn-small">HELP</a>
    </div>

    <!-- RIGHT SIDE -->
    <div class="nav-right">
      <!-- Logout -->
      <form action="<%= request.getContextPath() %>/logout" method="post">
        <button type="submit" class="btn btn-small">Logout</button>
      </form>
    </div>
</nav>

