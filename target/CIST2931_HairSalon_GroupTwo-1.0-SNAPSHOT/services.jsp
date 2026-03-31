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
    <title>Our Services</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/styles.css">
    <!-- Add icons for footer -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>

<jsp:include page="/includes/home-header.jsp" />

<main class="services-page">

    <section class="services-hero container-width">
        <div class="services-hero-overlay">
            <h1>Our Services</h1>
            <p>Explore our full range of salon services and pricing.</p>
        </div>
    </section>

    <section class="services-section container-width">

        <!-- Hair Cutting Services -->
        <div class="services-card">
            <h2>Hair Cutting Services</h2>
            <table class="services-table">
                <tr>
                    <th>Service</th>
                    <th>Protégé</th>
                    <th>Level 1</th>
                    <th>Level 2</th>
                    <th>Level 3</th>
                </tr>
                <tr>
                    <td>Consultation</td>
                    <td>$19</td>
                    <td>$19</td>
                    <td>$19</td>
                    <td>$24</td>
                </tr>
                <tr>
                    <td>Cut &amp; Style</td>
                    <td>$56</td>
                    <td>$61</td>
                    <td>$67</td>
                    <td>$72</td>
                </tr>
                <tr>
                    <td>Clipper cut/Barber</td>
                    <td>$36</td>
                    <td>$39</td>
                    <td>$43</td>
                    <td>$48</td>
                </tr>
            </table>
            <p class="services-note"><strong>Our prices may change due to density, length or special requirements by the client.</strong></p>
        </div>

        <!-- Design and Styling Services -->
        <div class="services-card">
            <h2>Design and Styling Services</h2>
            <table class="services-table">
                <tr>
                    <th>Service</th>
                    <th>Protégé</th>
                    <th>Level 1</th>
                    <th>Level 2</th>
                    <th>Level 3</th>
                </tr>
                <tr>
                    <td>Shampoo / Style</td>
                    <td>$47</td>
                    <td>$52</td>
                    <td>$52</td>
                    <td>$57</td>
                </tr>
                <tr>
                    <td>Updo</td>
                    <td colspan="4">Starts at $107</td>
                </tr>
                <tr>
                    <td>
                        Bridal Design<br>
                        <span class="small-text">See Bridal Services Below.</span>
                    </td>
                    <td colspan="4">Priced by consultation</td>
                </tr>
            </table>
            <p class="services-note"><strong>Our prices may change due to density, length or special requirements by the client.</strong></p>
        </div>

        <!-- Color & Highlighting Services -->
        <div class="services-card">
            <h2>Color &amp; Highlighting Services</h2>
            <table class="services-table">
                <tr>
                    <th>Service</th>
                    <th>Protégé</th>
                    <th>Level 1</th>
                    <th>Level 2</th>
                    <th>Level 3</th>
                </tr>
                <tr>
                    <td>Single Process <span class="small-text">(roots)</span></td>
                    <td>$76</td>
                    <td>$83</td>
                    <td>$91</td>
                    <td>$96</td>
                </tr>
                <tr>
                    <td>Glaze With Service</td>
                    <td>$49</td>
                    <td>$49</td>
                    <td>$49</td>
                    <td>$49</td>
                </tr>
                <tr>
                    <td>Glaze</td>
                    <td>$66</td>
                    <td>$66</td>
                    <td>$66</td>
                    <td>$66</td>
                </tr>
                <tr>
                    <td>Custom Highlights</td>
                    <td>$96</td>
                    <td>$103</td>
                    <td>$108</td>
                    <td>$113</td>
                </tr>
                <tr>
                    <td>Full Foil Highlights</td>
                    <td>$138</td>
                    <td>$149</td>
                    <td>$161</td>
                    <td>$171</td>
                </tr>
                <tr>
                    <td>Corrective Color</td>
                    <td colspan="4">Priced by consultation ONLY</td>
                </tr>
                <tr>
                    <td>Balayage</td>
                    <td>$161</td>
                    <td>$171</td>
                    <td>$182</td>
                    <td>$196</td>
                </tr>
                <tr>
                    <td>Ombre</td>
                    <td colspan="4">Priced by consultation ONLY</td>
                </tr>
                <tr>
                    <td>Men's Camo</td>
                    <td>$64</td>
                    <td>$64</td>
                    <td>$64</td>
                    <td>$64</td>
                </tr>
            </table>
            <p class="services-note"><strong>Our prices may change due to density, length or special requirements by the client.</strong></p>
        </div>

        <!-- Hair Treatments -->
        <div class="services-card">
            <h2>Hair Treatments</h2>
            <table class="services-table services-table-simple">
                <tr><td>Kérastase Fusio-Dose</td><td>$48</td></tr>
                <tr><td>Surface Awaken Scalp Facial</td><td>$38</td></tr>
                <tr><td>Kerastase Chroma Shine Treatment</td><td>$24</td></tr>
                <tr><td>Kerastase Fusio Scrub</td><td>$24</td></tr>
                <tr><td>Kerastase Premiere Treatment</td><td>$48</td></tr>
                <tr><td>Shu Uemura Izumi Gloss Treatment</td><td>$24</td></tr>
                <tr><td>Mask</td><td>$21</td></tr>
                <tr><td>L'Oreal Molecular Repair Treatment</td><td>$48</td></tr>
            </table>
            <p class="services-note"><strong>Our prices may change due to density, length or special requirements by the client.</strong></p>
        </div>

        <!-- Chemical Treatments -->
        <div class="services-card">
            <h2>Chemical Treatments</h2>
            <table class="services-table services-table-simple">
                <tr><td>Cezanne Smoothing Treatment</td><td>Starts at $399</td></tr>
                <tr><td>Keratin Smoothing Treatment</td><td>Starts at $399</td></tr>
                <tr><td>Custom Perm</td><td>Starts at $117</td></tr>
            </table>
        </div>

        <!-- Bridal Services + Hair Extensions -->
        <div class="services-two-column">
            <div class="services-card">
                <h2>Bridal Services</h2>
                <table class="services-table services-table-simple">
                    <tr><td>Bridal Make Up</td><td>Priced by consultation</td></tr>
                    <tr><td>Bridal Hair</td><td>Priced by consultation</td></tr>
                    <tr><td>Bridal Party</td><td>Priced by consultation</td></tr>
                </table>
            </div>

            <div class="services-card">
                <h2>Hair Extensions</h2>
                <table class="services-table services-table-simple">
                    <tr><td>Bellami Hair Extensions</td><td>Priced by consultation</td></tr>
                    <tr><td>Amplify Hair Extensions</td><td>Priced by consultation</td></tr>
                    <tr><td>Halo Couture Hair Extensions</td><td>Priced by consultation</td></tr>
                </table>
            </div>
        </div>

        <!-- Makeup / Waxing / Threading -->
        <div class="services-card">
            <h2>Makeup, Waxing &amp; Threading Services</h2>
            <table class="services-table services-table-simple">
                <tr><td>Lip/Chin</td><td>$17</td></tr>
                <tr><td>Eyebrows</td><td>$21</td></tr>
                <tr><td>Full Face</td><td>$37</td></tr>
                <tr><td>Eyelash Application</td><td>$22</td></tr>
                <tr><td>Makeup Application</td><td>$84</td></tr>
            </table>
        </div>

        <!-- Bottom policy text -->
        <div class="services-card services-policy">
            <h2>Service Guarantee</h2>
            <p>
                Our goal is 100% customer satisfaction. However, if you are dissatisfied with a service you have received,
                we are happy to provide you with the opportunity to return to the salon. Guest is required to call within
                2 weeks of the service received.
            </p>

            <h2>Appointment Policies</h2>
            <p>
                Please arrive at your reservation on time. We cannot guarantee service for late arrivals. If a guest is tardy,
                ChattTech Hair Salon has the right to reschedule some or all the services scheduled.
            </p>

            <p>
                We require 24 hour notice for canceling appointments. If an appointment is canceled less than 24 hours in advance,
                we will charge 50% of the schedule service total. This includes no show / no contact and in some cases, late arrivals.
            </p>

            <p>
                Guests who often cancel or “No Show” appointments will no longer be able to schedule appointments but will always be
                welcomed as a walk-in guest.
            </p>
        </div>

        <div class="artists-back-link">
            <a href="<%= request.getContextPath() %>/index.jsp" class="btn">Back to Home</a>
        </div>

    </section>
</main>

<jsp:include page="/includes/footer.jsp" />



</body>
</html>
