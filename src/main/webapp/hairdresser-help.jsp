<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hairdresser Help</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<header class="site-header">
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Salon Appointment System</span>
</header>

<jsp:include page="/includes/hairdresser-nav.jsp" />

<main class="help-page">

    <section class="help-hero container-width">
        <div class="help-hero-overlay">
            <h1>Hairdresser Help</h1>
            <p>
                This page explains how hairdressers can view their schedules
                and manage daily appointments.
            </p>
        </div>
    </section>

    <section class="help-section container-width">

        <div class="help-card">
            <h2>Hairdresser Responsibilities</h2>
            <ul>
                <li>View daily appointment schedules</li>
                <li>Check assigned customers</li>
                <li>Track working hours based on schedule blocks</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>View Daily Schedule</h2>
            <ol>
                <li>Go to the <strong>Dashboard</strong>.</li>
                <li>Your appointments for the selected day will display.</li>
                <li>If available, select a different date to view appointments.</li>
            </ol>
        </div>

        <div class="help-card">
            <h2>Understanding Your Schedule</h2>
            <ul>
                <li><strong>Date:</strong> The day of the appointment.</li>
                <li><strong>Time:</strong> Start and end time of each appointment.</li>
                <li><strong>Customer:</strong> The client assigned to the appointment.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Common Errors</h2>
            <ul>
                <li><strong>No appointments showing:</strong> There may be no bookings for that day.</li>
                <li><strong>Invalid date:</strong> Ensure correct date format when filtering.</li>
                <li><strong>Inactive account:</strong> Contact admin if you cannot access schedule.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Navigation Tips</h2>
            <ul>
                <li>Use <strong>Dashboard</strong> to view appointments.</li>
                <li>Check different dates to plan your schedule.</li>
                <li>Logout after completing your session.</li>
            </ul>
        </div>

        <div class="help-back-link">
            <a href="<%= request.getContextPath() %>/hairdresserDashboard" class="btn">Back to Dashboard</a>
        </div>

    </section>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
