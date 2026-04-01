<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Help</title>
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

<jsp:include page="/includes/customer-nav.jsp" />

<main class="help-page">

    <section class="help-hero container-width">
        <div class="help-hero-overlay">
            <h1>Customer Help</h1>
            <p>
                This page explains how customers can book appointments,
                manage their profile, and view scheduled services.
            </p>
        </div>
    </section>

    <section class="help-section container-width">

        <div class="help-card">
            <h2>Customer Features</h2>
            <ul>
                <li>Create and manage appointments</li>
                <li>View upcoming appointments</li>
                <li>Update personal profile information</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>How to Book an Appointment</h2>
            <ol>
                <li>Navigate to <strong>Book Appointment</strong>.</li>
                <li>Select a <strong>Hairdresser</strong>.</li>
                <li>Choose a <strong>Date</strong>.</li>
                <li>Select a <strong>Start Time</strong> and <strong>End Time</strong>.</li>
                <li>Click <strong>Schedule Appointment</strong>.</li>
            </ol>
            <p>
                After booking, you will be redirected to your dashboard where the appointment will appear.
            </p>
        </div>

        <div class="help-card">
            <h2>View Your Appointments</h2>
            <ul>
                <li>Open the <strong>Dashboard</strong>.</li>
                <li>View all scheduled appointments in the list.</li>
                <li>Appointments include date, time, and assigned hairdresser.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Update Your Profile</h2>
            <ol>
                <li>Navigate to your <strong>Profile</strong> page.</li>
                <li>Edit your personal information (name, phone, etc.).</li>
                <li>Click <strong>Save</strong> to update your profile.</li>
            </ol>
        </div>

        <div class="help-card">
            <h2>Common Errors</h2>
            <ul>
                <li><strong>Missing fields:</strong> Ensure all required fields are filled out.</li>
                <li><strong>Invalid time:</strong> Start time must be before end time.</li>
                <li><strong>Unavailable slot:</strong> Selected time may already be booked.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Navigation Tips</h2>
            <ul>
                <li>Use <strong>Dashboard</strong> to view appointments.</li>
                <li>Use <strong>Book Appointment</strong> to schedule services.</li>
                <li>Use <strong>Logout</strong> when finished.</li>
            </ul>
        </div>

        <div class="help-back-link">
            <a href="<%= request.getContextPath() %>/customerDashboard" class="btn">Back to Dashboard</a>
        </div>

    </section>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>
