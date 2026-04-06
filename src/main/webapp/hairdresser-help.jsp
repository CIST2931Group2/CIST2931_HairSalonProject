<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hairdresser Help</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
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
        <p>Learn how to manage your appointments and view customer details.</p>
    </div>
</section>

<section class="help-section container-width">

    <div class="help-card">
        <h2>Hairdresser Features</h2>
        <ul>
            <li>View daily appointments</li>
            <li>View assigned customers</li>
            <li>View customer profiles and appointment history</li>
            <li>Update your profile information</li>
        </ul>
    </div>

    <div class="help-card">
        <h2>View Daily Appointments</h2>
        <ol>
            <li>Open the <strong>Dashboard</strong>.</li>
            <li>Select a date using the date picker.</li>
            <li>Click <strong>View</strong>.</li>
            <li>Your appointments for that day will display in the table.</li>
        </ol>
    </div>

    <div class="help-card">
        <h2>View Customer Details</h2>
        <ol>
            <li>From the Dashboard, locate an appointment.</li>
            <li>Click on the <strong>customer name</strong>.</li>
            <li>You will be taken to the <strong>Customer View</strong> page.</li>
            <li>View the customer’s profile and full appointment history.</li>
        </ol>
    </div>

    <div class="help-card">
        <h2>View My Customers</h2>
        <ol>
            <li>Navigate to <strong>My Customers</strong>.</li>
            <li>View all customers assigned to you.</li>
            <li>Click <strong>View Profile</strong> to see detailed information.</li>
        </ol>
    </div>

    <div class="help-card">
        <h2>Update Your Profile</h2>
        <ol>
            <li>Go to <strong>My Profile</strong>.</li>
            <li>Edit your information (name, phone, specialties, bio).</li>
            <li>Click <strong>Save Profile</strong>.</li>
        </ol>
    </div>

    <div class="help-card">
        <h2>Common Issues</h2>
        <ul>
            <li><strong>No appointments showing:</strong> There may be no bookings for the selected date.</li>
            <li><strong>Incorrect date:</strong> Make sure a valid date is selected.</li>
        </ul>
    </div>

    <div class="help-card">
        <h2>Navigation Tips</h2>
        <ul>
            <li><strong>Dashboard:</strong> View daily appointments</li>
            <li><strong>My Customers:</strong> View assigned customers</li>
            <li><strong>My Profile:</strong> Update your information</li>
            <li><strong>Logout:</strong> End your session</li>
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
