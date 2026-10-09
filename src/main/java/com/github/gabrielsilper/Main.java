package com.github.gabrielsilper;

import com.github.gabrielsilper.repository.IChamadoRepository;
import com.github.gabrielsilper.repository.IDepartamentoRepository;
import com.github.gabrielsilper.repository.impl.ChamadoRepositoryImpl;
import com.github.gabrielsilper.repository.impl.DepartamentoRepositoryImpl;
import com.github.gabrielsilper.servlet.ChamadoServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();

        int port = 8081;

        tomcat.setPort(port);
        tomcat.getConnector();

        String webapp = new File("src/main/webapp").getAbsolutePath();

        Context context = tomcat.addWebapp("", webapp);

        String servletName = "ChamadoServlet";

        IDepartamentoRepository departamentoRepository = new DepartamentoRepositoryImpl();
        IChamadoRepository chamadoRepository = new ChamadoRepositoryImpl();

        Tomcat.addServlet(context, servletName, new ChamadoServlet(
                departamentoRepository,
                chamadoRepository
        ));

        context.addServletMappingDecoded("/chamados", servletName);

        System.out.println("Starting server and listening on port" + port + "...");

        tomcat.start();
        tomcat.getServer().await();
    }
}