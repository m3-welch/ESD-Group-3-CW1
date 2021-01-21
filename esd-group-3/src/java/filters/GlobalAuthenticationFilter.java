/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/GlobalAuthenticationFilter")
public class GlobalAuthenticationFilter implements Filter {

	private ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException {
            this.context = fConfig.getServletContext();
            this.context.log("GlobalAuthenticationFilter initialized");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String uri = req.getRequestURI();
            this.context.log("Requested Resource::"+uri);

            HttpSession loginSession = req.getSession(false);

            // 0 = bad login, 1 = doctor, 2 = nurse, 3 = client, 4 = admin
            Integer role = 0;
            if(loginSession != null){
                role = (Integer) loginSession.getAttribute("role");
                if(role == null){
                    role = 0;
                }
            }
            else {
                role = 0;
            }

            // set the name for user roles, for dashboard redirects
            String user_role = "";
            if(role != 0){
                switch(role) {
                    case 1:
                        user_role = "doctor";
                        break;
                    case 2:
                        user_role = "nurse";
                        break;
                    case 3:
                        user_role = "client";
                        break;
                    case 4:
                        user_role = "admin";
                        break;
                }
            }

            // authentication logic
            // NOTE - servlet names are as per web.xml
            boolean is_errorHome = false;
            String errorMsg = "";

            // handles cases of no user logged in
            if (role == 0 && !(uri.endsWith("login.jsp") || uri.endsWith("newEmployeeSignup.jsp") || uri.endsWith("newPatientSignup.jsp") || uri.endsWith("LoginServlet") || uri.endsWith("PatientSignupServlet") || uri.endsWith("EmployeeSignupServlet") || uri.endsWith("esd-group-3/") || uri.endsWith(".css"))){
                this.context.log("Unauthorized access request");
                req.setAttribute("message", "ERROR - Please Login"); // Will be available as ${message}
                req.getRequestDispatcher("login.jsp").forward(request,response);
                res.sendRedirect("login.jsp");
            }
            
            // handles permissions for logged in users
            if (( uri.endsWith("InvoiceDownloadServlet") || uri.endsWith("NewEmployeeServlet") || uri.endsWith("PricesChangerServlet") 
                    || uri.endsWith("PricesViewerServlet") || uri.endsWith("ApproveNewUsersServlet") || uri.endsWith("NewEmployeeServlet") 
                    || uri.endsWith("NewUserServlet") || uri.endsWith("ViewEmployeeServlet") || uri.endsWith("ViewUsersServlet") 
                    || uri.endsWith("admin_home.jsp") || uri.endsWith("ViewTurnover.jsp") ||  uri.endsWith("prices.jsp") || uri.endsWith("ApproveNewUsers.jsp")
                    || uri.endsWith("NewEmployee.jsp") || uri.endsWith("NewUser.jsp") || uri.endsWith("ViewEmployees.jsp") || uri.endsWith("ViewUsers.jsp")
                    || uri.endsWith("ViewTurnover.jsp")
                    ) && (role != 4)){
                is_errorHome = true;
                errorMsg = "ERROR - User is not an Administrator";
            }
            else if ((uri.endsWith("client_home.jsp") || uri.endsWith("PayInvoiceServlet") || uri.endsWith("NewAppointmentServlet") || uri.endsWith("ViewPrescriptionsServlet")
                    || uri.endsWith("NewAppointment.jsp") || uri.endsWith("ViewPrescriptions.jsp")
                    ) && role != 3){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Patient";
            }
            else if (uri.endsWith("nurse_home.jsp") 
                    && role != 2){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Nurse";
            }
            else if (uri.endsWith("doctor_home.jsp") 
                    && role != 1){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Doctor";
            }
            else if ((uri.endsWith("NewReferralServlet") || uri.endsWith("CreatePrescriptionServlet") || uri.endsWith("NewEmployeeAppointmentServlet") 
                    || uri.endsWith("RespondToPendingPrescriptionExtensionsServlet") || uri.endsWith("CreatePrescriptions.jsp") 
                    || uri.endsWith("NewEmployeeAppointment.jsp") || uri.endsWith("NewReferral.jsp") || uri.endsWith("ViewAppointments.jsp")
                    || uri.endsWith("ViewPendingPrescriptionExtensions.jsp")
                    ) && !(role == 1 || role == 2)){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Doctor or Nurse";
            }
            else if ((uri.endsWith("NewUserServlet") || uri.endsWith("ViewPatientsServlet") || uri.endsWith("CancelAppointmentServlet") || uri.endsWith("ViewPatientsServlet")
                    || uri.endsWith("ViewReferralsServlet") || uri.endsWith("ViewPatients.jsp") || uri.endsWith("ViewReferrals.jsp")
                    ) && !(role == 1 || role == 2 || role == 4)){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Doctor, Nurse, or Admin";
            }
            else{
                    // Common files for logged in users, of any type:
                    // LogoutServlet, InvoiceViewerServlet, 
                    chain.doFilter(request, response);
            }
            
            // ERROR send home
            if(is_errorHome) { 
                this.context.log("Unauthorized access request");
                req.setAttribute("message", errorMsg); // Will be available as ${message}
                req.getRequestDispatcher("dashboards/" + user_role + "_home.jsp").forward(request,response);
                res.sendRedirect("dashboards/" + user_role + "_home.jsp");
            }
	}
        
	public void destroy() {
            //close any resources here
	}

}
