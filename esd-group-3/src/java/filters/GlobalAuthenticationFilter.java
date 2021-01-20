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
            if (role == 0 && !(uri.endsWith("login.jsp") || uri.endsWith("newPatient.jsp") || uri.endsWith("LoginServlet") || uri.endsWith("SignupServlet") || uri.endsWith("esd-group-3/") || uri.endsWith(".css"))){
                this.context.log("Unauthorized access request");
                req.setAttribute("message", "ERROR - Please Login"); // Will be available as ${message}
                req.getRequestDispatcher("login.jsp").forward(request,response);
                res.sendRedirect("login.jsp");
            }
            
            // handles permissions for logged in users
            if ((uri.endsWith("admin_home.jsp") || uri.endsWith("invoiceViewer.jsp") || uri.endsWith("InvoiceViewerServlet") || uri.endsWith("InvoiceDownloadServlet") 
                    || uri.endsWith("NewEmployeeServlet") || uri.endsWith("prices.jsp") || uri.endsWith("PricesChanger") || uri.endsWith("PricesViewer")) && role != 4){
                is_errorHome = true;
                errorMsg = "ERROR - User is not an Administrator";
            }
            else if (uri.endsWith("client_home.jsp") && role != 3){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Patient";
            }
            else if (uri.endsWith("nurse_home.jsp") && role != 2){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Nurse";
            }
            else if (uri.endsWith("doctor_home.jsp") && role != 1){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Doctor";
            }
            else if (uri.endsWith("NewReferralServlet") && !(role == 1 || role == 2)){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Doctor or Nurse";
            }
            else if ((uri.endsWith("NewUserServlet") || uri.endsWith("ViewPatientsServlet")) && !(role == 1 || role == 2 || role == 4)){
                is_errorHome = true;
                errorMsg = "ERROR - User is not a Doctor, Nurse, or Admin";
            }
            else{
                    // Common files for logged in users, of any type:
                    // LogoutServlet
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
