<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Hairdresser" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Schedule" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.ScheduleBlock" %>

<%
    List<Hairdresser> hairdressers =
            (List<Hairdresser>) request.getAttribute("hairdressers");

    Schedule schedule = (Schedule) request.getAttribute("schedule");

    List<ScheduleBlock> blocks =
            (List<ScheduleBlock>) request.getAttribute("blocks");

    Integer selectedHairdresserId =
            (Integer) request.getAttribute("selectedHairdresserId");

    LocalDate selectedWeekStartDate =
            (LocalDate) request.getAttribute("selectedWeekStartDate");

    String error = (String) request.getAttribute("error");
    String success = (String) request.getAttribute("success");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Hairdressers</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<nav class="nav">
    <a href="<%= request.getContextPath() %>/adminManageHairdressers">My Dashboard</a>
    |
    <a href="<%= request.getContextPath() %>/adminSchedule">Manage Weekly Schedules</a>
    |
    <a href="<%= request.getContextPath() %>/logout">Logout</a>
</nav>

<h1>Admin - Weekly Schedule</h1>

<% if (error != null) { %>
<p style="color:red;"><%= error %></p>
<% } %>

<% if (success != null) { %>
<p style="color:green;"><%= success %></p>
<% } %>

<h2>Create / Load Weekly Schedule</h2>

<form method="get" action="<%= request.getContextPath() %>/adminSchedule">
    <table>
        <tr>
            <td><label for="hairdresserId">Hairdresser:</label></td>
            <td>
                <select name="hairdresserId" id="hairdresserId" required>
                    <option value="">-- Select Hairdresser --</option>

                    <%
                        if (hairdressers != null) {
                            for (Hairdresser h : hairdressers) {
                                boolean isSelected =
                                        selectedHairdresserId != null &&
                                                selectedHairdresserId == h.getHairdresserId();
                    %>
                    <option value="<%= h.getHairdresserId() %>"
                            <%= isSelected ? "selected" : "" %>>
                        <%= h.getFirstName() %> <%= h.getLastName() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </td>
        </tr>

        <tr>
            <td><label for="weekStartDate">Week Start Date:</label></td>
            <td>
                <input type="date"
                       name="weekStartDate"
                       id="weekStartDate"
                       value="<%= selectedWeekStartDate != null ? selectedWeekStartDate.toString() : "" %>"
                       required>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <button type="submit">Load Schedule</button>
            </td>
        </tr>
    </table>
</form>

<br>

<form method="post" action="<%= request.getContextPath() %>/adminSchedule">
    <input type="hidden" name="action" value="createSchedule">

    <table>
        <tr>
            <td><label for="createHairdresserId">Hairdresser:</label></td>
            <td>
                <select name="hairdresserId" id="createHairdresserId" required>
                    <option value="">-- Select Hairdresser --</option>

                    <%
                        if (hairdressers != null) {
                            for (Hairdresser h : hairdressers) {
                                boolean isSelected =
                                        selectedHairdresserId != null &&
                                                selectedHairdresserId == h.getHairdresserId();
                    %>
                    <option value="<%= h.getHairdresserId() %>"
                            <%= isSelected ? "selected" : "" %>>
                        <%= h.getFirstName() %> <%= h.getLastName() %>
                    </option>
                    <%
                            }
                        }
                    %>
                </select>
            </td>
        </tr>

        <tr>
            <td><label for="createWeekStartDate">Week Start Date:</label></td>
            <td>
                <input type="date"
                       name="weekStartDate"
                       id="createWeekStartDate"
                       value="<%= selectedWeekStartDate != null ? selectedWeekStartDate.toString() : "" %>"
                       required>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
                <button type="submit">Create Weekly Schedule</button>
            </td>
        </tr>
    </table>
</form>

<hr>

<% if (schedule != null) { %>

<h2>Schedule Details</h2>

<p>
    <strong>Schedule ID:</strong> <%= schedule.getScheduleId() %><br>
    <strong>Hairdresser ID:</strong> <%= schedule.getHairdresserId() %><br>
    <strong>Week Start:</strong> <%= schedule.getWeekStartDate() %>
</p>

<h3>Add Schedule Block</h3>

<form method="post" action="<%= request.getContextPath() %>/adminSchedule">
    <input type="hidden" name="action" value="addBlock">
    <input type="hidden" name="scheduleId" value="<%= schedule.getScheduleId() %>">
    <input type="hidden" name="hairdresserId" value="<%= schedule.getHairdresserId() %>">
    <input type="hidden" name="weekStartDate" value="<%= schedule.getWeekStartDate() %>">

    <table>
        <tr>
            <td><label for="dayOfWeek">Day:</label></td>
            <td>
                <select name="dayOfWeek" id="dayOfWeek" required>
                    <option value="">-- Select Day --</option>
                    <option value="MON">MON</option>
                    <option value="TUE">TUE</option>
                    <option value="WED">WED</option>
                    <option value="THU">THU</option>
                    <option value="FRI">FRI</option>
                    <option value="SAT">SAT</option>
                    <option value="SUN">SUN</option>
                </select>
            </td>
        </tr>

        <tr>
            <td><label for="startTime">Start Time:</label></td>
            <td><input type="time" name="startTime" id="startTime" required></td>
        </tr>

        <tr>
            <td><label for="endTime">End Time:</label></td>
            <td><input type="time" name="endTime" id="endTime" required></td>
        </tr>

        <tr>
            <td></td>
            <td><button type="submit">Add Block</button></td>
        </tr>
    </table>
</form>

<h3>Existing Schedule Blocks</h3>

<%
    if (blocks != null && !blocks.isEmpty()) {
%>
<table class="schedule-blocks-table">
    <tr>
        <th>Block ID</th>
        <th>Day</th>
        <th>Start Time</th>
        <th>End Time</th>
    </tr>

    <%
        for (ScheduleBlock block : blocks) {
    %>
    <tr>
        <td><%= block.getScheduleBlockId() %></td>
        <td><%= block.getDayOfWeek() %></td>
        <td><%= block.getStartTime() %></td>
        <td><%= block.getEndTime() %></td>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<p>No schedule blocks added yet.</p>
<%
    }
%>

<% } %>

</body>
</html>

