package org.example;

import jakarta.servlet.Servlet;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServletHttpHandlerAdapter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.scan("com.example");
        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher =
                container.addServlet("mvc", (Servlet)(new DispatcherServlet(context)));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
