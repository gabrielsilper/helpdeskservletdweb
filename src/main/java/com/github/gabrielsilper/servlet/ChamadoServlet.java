package com.github.gabrielsilper.servlet;

import com.github.gabrielsilper.repository.IChamadoRepository;
import com.github.gabrielsilper.repository.IDepartamentoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChamadoServlet extends HttpServlet {
    private final IDepartamentoRepository departamentoRepository;
    private final IChamadoRepository chamadoRepository;

    public ChamadoServlet(
            IDepartamentoRepository departamentoRepository,
            IChamadoRepository chamadoRepository
    ) {
        this.departamentoRepository = departamentoRepository;
        this.chamadoRepository = chamadoRepository;
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().println("""
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <title>Chamados</title>
                </head>
                <body>
                    <h1>Helpdesk - Chamados de Suporte de TI</h1>
                </body>
                </html>""");
    }
}
