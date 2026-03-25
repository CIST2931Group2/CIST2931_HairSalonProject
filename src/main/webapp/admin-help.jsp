<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/09/2026
  Time: 7:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
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

<main class="help-page">

    <section class="help-hero container-width">
        <div class="help-hero-overlay">
            <h1>Admin Help</h1>
            <p>
                This page explains how Admin users manage hairdressers and weekly schedules
                in the ChattTech Hair Salon system.
            </p>
        </div>
    </section>

    <section class="help-section container-width">

        <div class="help-card">
            <h2>Admin Responsibilities</h2>
            <p>As an Admin, you can perform the following tasks:</p>
            <ul>
                <li>Add new hairdressers to the system</li>
                <li>Deactivate hairdressers who are no longer current</li>
                <li>Create weekly schedules for active hairdressers</li>
                <li>Add schedule blocks for each hairdresser’s working days and hours</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Manage Hairdressers</h2>
            <h3>How to add a new hairdresser</h3>
            <ol>
                <li>Open the <strong>Dashboard</strong> page.</li>
                <li>Find the <strong>Add New Hairdresser</strong> form.</li>
                <li>Enter the required information:
                    <ul>
                        <li>First Name</li>
                        <li>Last Name</li>
                        <li>Email</li>
                        <li>Password</li>
                    </ul>
                </li>
                <li>Optional fields such as phone, specialties, and bio may also be entered.</li>
                <li>Click <strong>Add Hairdresser</strong>.</li>
                <li>If successful, the new hairdresser will appear in the active hairdresser list.</li>
            </ol>

            <h3>How to deactivate a hairdresser</h3>
            <ol>
                <li>Open the <strong>Dashboard</strong> page.</li>
                <li>Locate the hairdresser in the current hairdresser table.</li>
                <li>Click the <strong>Deactivate</strong> button in that row.</li>
                <li>A confirmation message should appear after the action is completed.</li>
                <li>Deactivated hairdressers are no longer shown in active lists used by scheduling pages.</li>
            </ol>
        </div>

        <div class="help-card">
            <h2>Create Weekly Schedule</h2>
            <ol>
                <li>Open the <strong>Weekly Schedule</strong> page.</li>
                <li>Select a hairdresser from the dropdown list.</li>
                <li>Choose a <strong>Week Start Date (week starts on Monday)</strong>.</li>
                <li>Click <strong>Create Weekly Schedule</strong>.</li>
                <li>The page will reload and display the created schedule details.</li>
            </ol>

            <p><strong>Important:</strong> Only active hairdressers should appear in the weekly schedule dropdown.</p>
        </div>

        <div class="help-card">
            <h2>Add Schedule Blocks</h2>
            <p>
                Schedule blocks define the working days and time ranges available for a hairdresser during a specific week.
            </p>

            <ol>
                <li>Load or create a weekly schedule first.</li>
                <li>Under <strong>Add Schedule Block</strong>, select a day of the week.</li>
                <li>Enter a start time.</li>
                <li>Enter an end time.</li>
                <li>Click <strong>Add Block</strong>.</li>
            </ol>

            <p>Example schedule block:</p>
            <ul>
                <li>Day: MON</li>
                <li>Start Time: 09:00 AM</li>
                <li>End Time: 05:00 PM</li>
            </ul>

            <p>
                Once added, the block will appear in the <strong>Existing Schedule Blocks</strong> table below the form.
            </p>
        </div>

        <div class="help-card">
            <h2>Understanding the Weekly Schedule Page</h2>
            <ul>
                <li><strong>Load Schedule:</strong> Opens an existing schedule for a selected hairdresser and week.</li>
                <li><strong>Create Weekly Schedule:</strong> Creates a new weekly schedule if one does not already exist.</li>
                <li><strong>Add Block:</strong> Adds working-hour blocks to the loaded schedule.</li>
                <li><strong>Existing Schedule Blocks:</strong> Displays all blocks already added for that schedule.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Common Error Situations</h2>
            <ul>
                <li><strong>Missing required fields:</strong> Make sure all required inputs are entered before submitting.</li>
                <li><strong>Schedule already exists:</strong> A weekly schedule for that hairdresser and week has already been created.</li>
                <li><strong>Invalid time range:</strong> The start time must be earlier than the end time.</li>
                <li><strong>Inactive hairdresser:</strong> Inactive hairdressers should not be selected for new schedules.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Navigation Tips</h2>
            <ul>
                <li>Use <strong>Dashboard</strong> to add hairdresser and maintain the current list of active hairdressers.</li>
                <li>Use <strong>Weekly Schedule</strong> to build and view weekly availability.</li>
                <li>Use <strong>Logout</strong> when you are finished working in the system.</li>
            </ul>
        </div>

        <div class="help-card">
            <h2>Need More Help?</h2>
            <p>
                If a page does not behave as expected, verify that:
            </p>
            <ul>
                <li>You are logged in as an Admin</li>
                <li>The required fields are filled in correctly</li>
                <li>The selected hairdresser is still active</li>
                <li>The selected week and schedule information are valid</li>
            </ul>
        </div>

        <div class="help-back-link">
            <a href="<%= request.getContextPath() %>/adminManageHairdressers" class="btn">Back to Admin Page</a>
        </div>

    </section>
</main>

<jsp:include page="/includes/footer.jsp" />

</body>
</html>