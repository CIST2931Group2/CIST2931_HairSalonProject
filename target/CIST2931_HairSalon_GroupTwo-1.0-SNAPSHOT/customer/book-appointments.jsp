<%--
  Created by IntelliJ IDEA.
  User: Michael
  Date: 2/6/2026
  Time: 9:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.example.cist2931_hairsalon_grouptwo.model.Hairdresser" %>

<%
    // Data sent by SearchAvailabilityServlet
    List<Hairdresser> hairdressers =
            (List<Hairdresser>) request.getAttribute("hairdressers");

    List<LocalDateTime> availableSlots =
            (List<LocalDateTime>) request.getAttribute("availableSlots");

    String error = (String) request.getAttribute("error");

    Integer selectedHairdresserId =
            (Integer) request.getAttribute("selectedHairdresserId");

    String selectedDate =
            (String) request.getAttribute("selectedDate");

    String selectedServiceType =
            (String) request.getAttribute("selectedServiceType");

    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
%>

<!DOCTYPE html>
<html>
<head>
    <title>Book Appointment</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
    <!-- <link rel="stylesheet" href="/css/styles.css"> -->
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<jsp:include page="/includes/customer-nav.jsp" />

    <main class = "flex-container">
        <div class="dashboard-container">
        <h2 style="text-align: center;">Book an Appointment</h2>

            <% if (error != null) { %>
                <p style="text-align: center; color:red;background:#FFE6E6; padding:10px; border-radius:5px;"><%= error %></p>
            <% } %>

    <!-- =========================
        SEARCH AVAILABILITY FORM
     ========================= -->
        <form class="form-container" method="post" action="<%= request.getContextPath() %>/searchAvailability">

            <div>
                <label for="hairdresserId">Hairdresser</label>
                <select name="hairdresserId" id="hairdresserId" required>
                    <option value="">-- Select Hairdresser --</option>

                    <%
                        if (hairdressers != null) {
                            for (Hairdresser h : hairdressers) {
                                boolean isSelected =
                                        (selectedHairdresserId != null &&
                                                selectedHairdresserId == h.getHairdresserId());
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
            </div>

            <br>

            <div>
                <label for="date">Date</label>
                <input type="date"
                    name="date"
                    id="date"
                    value="<%= selectedDate != null ? selectedDate : "" %>"
                    required>
            </div>

            <br>

            <div>
                <label for="serviceType">Service Type</label>
                <select name="serviceType" id="serviceType" required>
                    <option value="">-- Select Service --</option>
                    <option value="Haircut"
                            <%= "Haircut".equals(selectedServiceType) ? "selected" : "" %>>
                        Haircut
                    </option>
                    <option value="Hair Styling"
                            <%= "Hair Styling".equals(selectedServiceType) ? "selected" : "" %>>
                        Hair Styling
                    </option>
                    <option value="Hair Dying"
                            <%= "Hair Dying".equals(selectedServiceType) ? "selected" : "" %>>
                        Hair Dying
                    </option>
                    <option value="Spa Service"
                            <%= "Spa Service".equals(selectedServiceType) ? "selected" : "" %>>
                        Spa Service
                    </option>
                    <option value="Nail Coloring"
                            <%= "Nail Coloring".equals(selectedServiceType) ? "selected" : "" %>>
                        Nail Coloring
                    </option>
                </select>
            </div>

            <br>

            <button type="submit">Search Availability</button>
        </form>

        <br>
        <hr>

        <!-- SUCCESS MESSAGE added-->
        <%
            String success = request.getParameter("success");
            if ("bookingConfirmed".equals(success)) {
        %>
        <p style="text-align: center; color:green; background:#e6ffe6; padding:10px; border-radius:5px;">
            Appointment booked successfully! Check your Dashboard for upcoming appointments.
        </p>
        <%
            }
        %>

        <!-- =========================
            AVAILABLE SLOTS / BOOKING
            ========================= -->

        <%
            if (availableSlots != null && availableSlots.isEmpty()) {
        %>
        <p style="color:red;">No available slots for the selected date.</p>
        <%
            }
        %>

        <%
            if (availableSlots != null && !availableSlots.isEmpty()) {
        %>

        <form class="form-container" method="post" action="<%= request.getContextPath() %>/bookAppointment">

            <!-- Keep selected search values for booking -->
            <input type="hidden" name="hairdresserId" value="<%= selectedHairdresserId %>">
            <input type="hidden" name="serviceType" value="<%= selectedServiceType %>">
            <input type="hidden" name="date" value="<%= selectedDate %>">

            <h3>Available Slots</h3>

            <%
                for (LocalDateTime slot : availableSlots) {
            %>
            <div>
                <label>
                    <input type="radio" name="slotStart" value="<%= slot %>" required>
                    <%= slot.format(fmt) %>
                </label>
            </div>
            <%
                }
            %>

            <br>
            <button type="submit">Confirm Appointment</button>
        </form>

        <%
            }
        %>

        <br>
    </div>
    </main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>

