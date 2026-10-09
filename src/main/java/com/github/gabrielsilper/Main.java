package com.github.gabrielsilper;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        int port = 8081;

        tomcat.setPort(port);
        tomcat.getConnector(); // Inicia o conector http;

        String webapp = new File("src/main/webapp").getAbsolutePath();

        Context context = tomcat.addWebapp("", webapp);

//        String servletName = "PessoaServlet";
//        Tomcat.addServlet(context, servletName, new PessoaServlet());
//        context.addServletMappingDecoded("/pessoa", servletName);
//
//        System.out.println("Starting server and listening on port" + port + "...");

        tomcat.start();
        tomcat.getServer().await();
    }
}