/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.util.Arrays;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Harrison B
 */
public class AppLoginListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    static final String INITIAL_TASKS_KEY = "INITIAL_TASKS_KEY";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(INITIAL_TASKS_KEY,
                new TasksList(Arrays.asList("From me to you", "We can work it out", "Long and winding road")
                ));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
