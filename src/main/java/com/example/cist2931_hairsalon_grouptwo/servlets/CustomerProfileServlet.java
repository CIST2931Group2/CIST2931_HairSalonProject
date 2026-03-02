package com.example.cist2931_hairsalon_grouptwo.servlets;

import com.example.cist2931_hairsalon_grouptwo.model.Customer;
import com.example.cist2931_hairsalon_grouptwo.service.CustomerService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/customerProfile")
public class CustomerProfileServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        Integer userId = (Integer) request.getSession().getAttribute("userId");

        Customer customer = customerService.getCustomerByUserId(userId);

        request.setAttribute("customer", customer);

        request.getRequestDispatcher("/customer/profile.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {
		// Get userId from session
        Integer userId = (Integer) request.getSession().getAttribute("userId");

        // Load existing customer
        Customer customer = customerService.getCustomerByUserId(userId);

        // Extract form fields
        String firstName = request.getParameter("firstName");
        String lastName  = request.getParameter("lastName");
        String phone     = request.getParameter("phone");

        // Update model
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);

        // Call service
        customerService.updateCustomerProfile(customer);

        response.sendRedirect(request.getContextPath() + "/customerDashboard");
    }
}
