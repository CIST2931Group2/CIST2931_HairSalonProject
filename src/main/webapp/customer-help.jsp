<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Customer Help</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
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
        <p>Learn how to book appointments, manage your profile, and view your schedule.</p>
    </div>
</section>

<section class="help-section container-width">

    <div class="help-card">
        <h2>Customer Features</h2>
        <ul>
            <li>Book new appointments</li>
            <li>View upcoming and past appointments</li>
            <li>Update your profile information</li>
        </ul>
    </div>

    <div class="help-card">
        <h2>How to Book an Appointment</h2>
        <ol>
            <li>Go to <strong>Book Appointments</strong> from the Dashboard.</li>
            <li>Select a <strong>Hairdresser</strong>.</li>
            <li>Select a <strong>Date</strong>.</li>
            <li>Select a <strong>Service Type</strong>.</li>
            <li>Click <strong>Search Availability</strong>.</li>
            <li>Choose an available time slot.</li>
            <li>Click <strong>Confirm Appointment</strong>.</li>
        </ol>

        <p><strong>Tip:</strong> Available time slots will appear after clicking "Search Availability".</p>
    </div>

    <div class="help-card">
        <h2>View Your Appointments</h2>
        <ul>
            <li>Open the <strong>Dashboard</strong>.</li>
            <li>View <strong>Upcoming Appointments</strong> and <strong>Past Appointments</strong>.</li>
            <li>Each appointment shows date, time, hairdresser, and service type.</li>
        </ul>
    </div>

    <div class="help-card">
        <h2>Update Your Profile</h2>
        <ol>
            <li>Click <strong>Profile Settings</strong> from the Dashboard.</li>
            <li>Edit your name or phone number.</li>
            <li>Click <strong>Update Profile</strong>.</li>
        </ol>
    </div>

    <div class="help-card">
        <h2>Common Errors</h2>
        <ul>
            <li><strong>No available slots:</strong> Try a different date or hairdresser.</li>
            <li><strong>Missing fields:</strong> All selections are required before searching.</li>
        </ul>
    </div>

    <div class="help-card">
        <h2>Navigation Tips</h2>
        <ul>
            <li><strong>Dashboard:</strong> View appointments and access features</li>
            <li><strong>Book Appointments:</strong> Schedule new services</li>
            <li><strong>Profile Settings:</strong> Update your personal info</li>
            <li><strong>Logout:</strong> End your session securely</li>
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
