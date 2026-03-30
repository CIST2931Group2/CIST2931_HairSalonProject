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
    <!-- Add icons for footer -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<jsp:include page="/includes/admin-nav.jsp" />

<main>
    <div class="dashboard-container">
    <h2 style="text-align:center;">Admin - Manage Weekly Schedule and Blocks</h2>

    <% if (error != null) { %>
    <p style="text-align: center; color:red;background:#FFE6E6; padding:10px; border-radius:5px;"><%= error %></p>
    <% } %>

    <% if (success != null) { %>
    <p style="text-align: center; color:green; background:#e6ffe6; padding:10px; border-radius:5px;"><%= success %></p>
    <% } %>

        <div class="dashboard-cards">
            <!-- LEFT COLUMN -->
            <div class="dashboard-appointments-column">

                <!-- Load existing schedule -->
                <div class="dashboard-card appointments-card">

                    <h3 style="text-align:center;">Load Weekly Schedule with Blocks</h3>
                    <!--removed class="form-date" -->
                    <form method="get" action="<%= request.getContextPath() %>/adminSchedule">

                        <label for="hairdresserId">Hairdresser:</label>
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

                        <label for="weekStartDate">Week Start Date:</label>
                        <input type="date"
                            name="weekStartDate"
                            id="weekStartDate"
                            value="<%= selectedWeekStartDate != null ? selectedWeekStartDate.toString() : "" %>"
                            required>

                        <button type="submit">Load Schedule</button>

                    </form>
                </div>

                <!-- Create new weekly schedule. Week Start Date should be Monday -->
                <div class="dashboard-card appointments-card">

                    <h3 style="text-align:center;">Create New Weekly Schedule</h3>
                        <!--removed class="form-date" -->
                    <form method="post" action="<%= request.getContextPath() %>/adminSchedule">
                        <input type="hidden" name="action" value="createSchedule">

                        <label for="createHairdresserId">Hairdresser:</label>
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

                        <label for="createWeekStartDate">Week Start Date:</label>
                        <input type="date"
                            name="weekStartDate"
                            id="createWeekStartDate"
                            value="<%= selectedWeekStartDate != null ? selectedWeekStartDate.toString() : "" %>"
                            required>

                        <button type="submit">Create Weekly Schedule</button>

                    </form>
                </div>
            </div>

            <!-- RIGHT COLUMN -->
            <div class="dashboard-appointments-column">

            <% if (schedule != null) { %>

                <!-- Existing schedule details: Schedule ID, Hairdresser ID, Week Start -->
                <div class="dashboard-card appointments-card">
                <h3 style="text-align:center;">Schedule Details</h3>

                    <p>
                        <strong>Schedule ID:</strong> <%= schedule.getScheduleId() %><br>
                        <strong>Hairdresser ID:</strong> <%= schedule.getHairdresserId() %><br>
                        <strong>Week Start:</strong> <%= schedule.getWeekStartDate() %>
                    </p>

                <!-- Add new schedule block inside existing schedule week. Choose: Day, Start time, End time -->
                <h3 style="text-align:center;">Add New Schedule Block</h3>

                    <form method="post" action="<%= request.getContextPath() %>/adminSchedule">
                        <input type="hidden" name="action" value="addBlock">
                        <input type="hidden" name="scheduleId" value="<%= schedule.getScheduleId() %>">
                        <input type="hidden" name="hairdresserId" value="<%= schedule.getHairdresserId() %>">
                        <input type="hidden" name="weekStartDate" value="<%= schedule.getWeekStartDate() %>">

                        <label for="dayOfWeek">Day:</label>
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

                        <label for="startTime">Start Time:</label>
                        <input type="time" name="startTime" id="startTime" required>

                        <label for="endTime">End Time:</label>
                        <input type="time" name="endTime" id="endTime" required>

                        <button type="submit">Add Block</button>

                    </form>
                </div>

                <!-- Schedule Blocks inside existing schedule -->
                <div class="dashboard-card appointments-card">

                <h3 style="text-align:center;">Existing Schedule Blocks</h3>

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
                </div>
            </div>

</div>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>

