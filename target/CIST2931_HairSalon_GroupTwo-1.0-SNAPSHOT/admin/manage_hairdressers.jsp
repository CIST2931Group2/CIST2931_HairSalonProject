<!DOCTYPE html>
<html>

<head>
    <%@ include file="/includes/logo.jsp" %>
    <span class="site-title">Admin Manage Hairdresser System</span>
    <title>Manage Hairdressers</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>
<body>

<jsp:include page="/includes/admin-nav.jsp" />
<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/hairdressers-nav.jsp" />

<h2>Manage Hairdressers</h2>
<br>
<form>
    <h3>Manage Hairdresser Status</h3>

    <label>Choose Hairdresser Number</label>
    <input type="number">

    <label>Activate or Deactivate</label>
    <input type="checkbox">


    <h3>Add/Remove Hairdresser?</h3>

    <label><h3>Add Hairdresser?</h3></label>
    <input type="checkbox" id="yes_add" name="add_hairdresser" value="YES">
    <label for="yes_add">YES</label>

    <label><h3>Remove Hairdresser?</h3></label>
    <input type="radio" id="no_remove" name="remove_hairdresser" value="NO">
    <label for="no_remove">NO</label>
    <input type="radio" id="yes_remove" name="remove_hairdresser" value="YES">
    <label for="yes_remove">YES</label>

    <button>Submit Changes</button>


</form>


</html>

