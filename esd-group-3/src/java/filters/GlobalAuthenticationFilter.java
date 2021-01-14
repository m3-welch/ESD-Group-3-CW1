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
		
                // admin, access all pages
                if(role == 4){
                    chain.doFilter(request, response);
                }
                // not admin, pages that require admin authenticaion
                else if(role != 4 && (uri.endsWith("admin.jsp") || uri.endsWith("admin_home.jsp") || uri.endsWith("InvoiceViewerServlet") || uri.endsWith("InvoiceDownloadServlet"))){
                    // if user is not admin, redirect to dashboard and display message
                    this.context.log("Unauthorized access request");
                    req.setAttribute("message", "ERROR - User is not an Administrator"); // Will be available as ${message}
                    req.getRequestDispatcher("dashboards/" + user_role + "_home.jsp").forward(request,response);
                    res.sendRedirect("dashboards/" + user_role + "_home.jsp");
                }
                // not dr, pages that require dr authenticaion
                else if(role != 1 && (uri.endsWith("doctor_home.jsp"))){
                    // if user is not dr, redirect to dashboard and display message
                    this.context.log("Unauthorized access request");
                    req.setAttribute("message", "ERROR - User is not a Doctor"); // Will be available as ${message}
                    req.getRequestDispatcher("dashboards/" + user_role + "_home.jsp").forward(request,response);
                    res.sendRedirect("dashboards/" + user_role + "_home.jsp");
                }
                // not nurse, pages that require nurse authenticaion
                else if(role != 2 && (uri.endsWith("nurse_home.jsp"))){
                    // if user is not nurse, redirect to dashboard and display message
                    this.context.log("Unauthorized access request");
                    req.setAttribute("message", "ERROR - User is not a Nurse"); // Will be available as ${message}
                    req.getRequestDispatcher("dashboards/" + user_role + "_home.jsp").forward(request,response);
                    res.sendRedirect("dashboards/" + user_role + "_home.jsp");
                }
                // not patient, pages that require patient authenticaion
                else if(role != 1 && (uri.endsWith("client_home.jsp"))){
                    // if user is not dr, redirect to dashboard and display message
                    this.context.log("Unauthorized access request");
                    req.setAttribute("message", "ERROR - User is not a Patient"); // Will be available as ${message}
                    req.getRequestDispatcher("dashboards/" + user_role + "_home.jsp").forward(request,response);
                    res.sendRedirect("dashboards/" + user_role + "_home.jsp");
                }
                // login, pages that do not require any authentication
                else if(role != 0 && (uri.endsWith("login.jsp") || uri.endsWith("signup.jsp") || uri.endsWith("LoginServlet") || uri.endsWith("SignupServlet") || uri.endsWith("esd-group-3/"))){
                    // if user is logged in, redirect to dashboard and display message
                    this.context.log("Unauthorized access request");
                    req.setAttribute("message", "ERROR - Please Logout to Signup or Login"); // Will be available as ${message}
                    req.getRequestDispatcher("dashboards/" + user_role + "_home.jsp").forward(request,response);
                    res.sendRedirect("dashboards/" + user_role + "_home.jsp");
                }
                // no login, pages that require any authentication
                else if(role == 0){
                    if (uri.endsWith("login.jsp") || uri.endsWith("signup.jsp") || uri.endsWith("LoginServlet") || uri.endsWith("SignupServlet") || uri.endsWith("esd-group-3/")){
                        chain.doFilter(request, response);
                    }
                    else{
                        // if no user logged in, and page requires authentication, send to login
                        this.context.log("Unauthorized access request");
                        req.setAttribute("message", "ERROR - Please Login"); // Will be available as ${message}
                        req.getRequestDispatcher("login.jsp").forward(request,response);
                        res.sendRedirect("login.jsp");
                    }
                }
                else{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
                
	}

	public void destroy() {
            //close any resources here
	}

}
