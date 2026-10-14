package com.github.gabrielsilper.servlet;

import com.github.gabrielsilper.dao.CidadeDAO;
import com.github.gabrielsilper.domain.model.Cidade;
import com.github.gabrielsilper.repository.ICidadeRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CidadeServlet extends HttpServlet {
    private final ICidadeRepository cidadeRepository;

    public CidadeServlet(ICidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().printf("""
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>Cidades</title>
                </head>
                  <body>
                    %s
                  </body>
                </html>""", gerarListaCidades());
    }

    private String gerarListaCidades() {
        String itens = gerarItensLista();

        return String.format("""
                <h2>Cidades do Banco de Dados</h2>
                <ul>
                  %s
                </ul>""", itens);
    }

    private String gerarItensLista() {
        List<Cidade> cidades = cidadeRepository.listar();
        StringBuilder sb = new StringBuilder();

        for (Cidade cidade : cidades) {
            sb.append("  <li>")
                    .append(cidade.getNome())
                    .append(" - ")
                    .append(cidade.getEstado().getSigla())
                    .append("</li>\n");
        }

        return sb.toString();
    }
}
