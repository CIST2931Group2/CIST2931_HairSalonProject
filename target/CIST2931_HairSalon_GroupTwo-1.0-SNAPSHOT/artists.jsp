<%--
  Created by IntelliJ IDEA.
  User: Ravid
  Date: 3/25/2026
  Time: 9:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Our Artists</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="css/styles.css">
  <!-- Add icons for footer -->
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<jsp:include page="/includes/home-header.jsp" />

<main class="artists-page">

  <section class="artists-hero container-width">
    <div class="artists-hero-overlay">
      <h1>Meet Our Artists</h1>
      <p>Our talented stylists and artists are here to help you look and feel your best.</p>
    </div>
  </section>

  <section class="artists-section container-width">

    <div class="artist-card">
      <img src="<%= request.getContextPath() %>/images/hairdresser%201_Mirus%20Dracone.png" alt="Mirus Dracone">
      <div class="artist-info">
        <h2>Mirus Dracone</h2>
        <p>
          Mirus specializes in precision cuts, color work, and modern styling.
          With a creative eye and attention to detail, Mirus helps clients achieve a polished and confident look.
        </p>
      </div>
    </div>

    <div class="artist-card">
      <img src="<%= request.getContextPath() %>/images/hairdresser%202_Yolanda%20Dante.png" alt="Yolanda Dante">
      <div class="artist-info">
        <h2>Yolanda Dante</h2>
        <p>
          Yolanda is known for elegant styling, classic looks, and personalized care.
          She enjoys helping clients find styles that match their personality and lifestyle.
        </p>
      </div>
    </div>

    <div class="artist-card">
      <img src="<%= request.getContextPath() %>/images/hairdresser%203_Juno%20Lilliam.png" alt="Juno Lilliam">
      <div class="artist-info">
        <h2>Juno Lilliam</h2>
        <p>
          Juno focuses on vibrant color, fresh trends, and creative transformations.
          Clients appreciate Juno’s artistic approach and passion for stylish results.
        </p>
      </div>
    </div>

    <div class="artist-card">
      <img src="<%= request.getContextPath() %>/images/hairdresser%204_Johnson%20Tyler.png" alt="Tyler Johnson">
      <div class="artist-info">
        <h2>Tyler Johnson</h2>
        <p>
          Tyler offers expert styling, grooming, and modern salon services with a professional touch.
          Tyler is committed to delivering a relaxed experience and great-looking results.
        </p>
      </div>
    </div>

    <div class="artist-card">
      <img src="<%= request.getContextPath() %>/images/hairdresser%205_Yasmine%20Liliac.png" alt="Yasmine Liliac">
      <div class="artist-info">
        <h2>Yasmine Liliac</h2>
        <p>
          Yasmine is passionate about beauty, healthy hair, and client-centered service.
          She enjoys creating flattering looks that bring out each client’s individual style.
        </p>
      </div>
    </div>

  </section>

  <div class="artists-back-link">
    <a href="<%= request.getContextPath() %>/index.jsp" class="btn">Back to Home</a>
  </div>

</main>

<jsp:include page="/includes/footer.jsp" />



</body>
</html>
