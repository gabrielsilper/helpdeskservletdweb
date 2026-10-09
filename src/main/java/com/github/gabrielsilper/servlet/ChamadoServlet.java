package com.github.gabrielsilper.servlet;

import com.github.gabrielsilper.domain.model.Chamado;
import com.github.gabrielsilper.domain.model.Departamento;
import com.github.gabrielsilper.repository.IChamadoRepository;
import com.github.gabrielsilper.repository.IDepartamentoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

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
    public void init() {
        Departamento rh = new Departamento("Recursos Humanos", "RH", "Maria Silva");
        Departamento financeiro = new Departamento("Financeiro", "FIN", "João Santos");
        Departamento laboratorios = new Departamento("Laboratórios", "LAB", "Ana Costa");
        Departamento secretaria = new Departamento("Secretaria", "SEC", "Pedro Oliveira");

        departamentoRepository.adicionar(rh);
        departamentoRepository.adicionar(financeiro);
        departamentoRepository.adicionar(laboratorios);
        departamentoRepository.adicionar(secretaria);

        chamadoRepository.adicionar(new Chamado(
                "Computador não liga",
                "Carlos Souza",
                "Computador da recepção não apresenta sinal de energia ao pressionar o botão.",
                10234,
                101,
                laboratorios
        ));
        chamadoRepository.adicionar(new Chamado(
                "Impressora sem tinta",
                "Fernanda Lima",
                "Impressora do setor financeiro parou de imprimir e exibe alerta de toner.",
                20567,
                203,
                financeiro
        ));
        chamadoRepository.adicionar(new Chamado(
                "Rede lenta no setor",
                "Paulo Mendes",
                "Conexão com a internet muito lenta, dificultando o acesso ao sistema de folha.",
                30112,
                105,
                rh
        ));
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
       String filtro = req.getParameter("pesquisa");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().printf("""
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <title>Chamados</title>
                </head>
                  <body>
                    <h1>Helpdesk - Chamados de Suporte de TI</h1>
                    %s
                    %s
                  </body>
                </html>""", gerarHeader(), gerarMain(filtro));
    }

    private String gerarMain(String filtro) {
        String tabelaChamados = gerarTabelaChamados(filtro);

        return String.format("""
                <main>
                  %s
                </main>
                """, tabelaChamados);
    }

    private String gerarHeader() {
        return """
                <header>
                    <form method="get" action="chamados">
                      <div>
                        <label for="pesquisa">Filtrar por título ou solicitante:</label>
                        <input type="text" id="pesquisa" name="pesquisa" placeholder="Digite o título ou solicitante..."/>
                      </div>
                      <button type="submit">Filtrar</button>
                    </form>
                </header>
                """;
    }

    private String gerarTabelaChamados(String filtro) {
        String linhas = gerarLinhasChamados(filtro);

        return String.format("""
                <table border="1">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Título</th>
                      <th>Solicitante</th>
                      <th>Descrição</th>
                      <th>Patrimônio</th>
                      <th>Sala</th>
                      <th>Departamento</th>
                    </tr>
                  </thead>
                  <tbody>
                    %s
                  </tbody>
                </table>
                """, linhas);
    }

    private String gerarLinhasChamados(String filtro) {
        List<Chamado> chamados = chamadoRepository.listar(filtro);
        StringBuilder sb = new StringBuilder();

        for (Chamado chamado : chamados) {
            sb.append("    <tr>\n");
            sb.append("      <td>").append(chamado.getId()).append("</td>\n");
            sb.append("      <td>").append(chamado.getTitulo()).append("</td>\n");
            sb.append("      <td>").append(chamado.getSolicitante()).append("</td>\n");
            sb.append("      <td>").append(chamado.getDescricao()).append("</td>\n");
            sb.append("      <td>").append(chamado.getCodigoEquipamento()).append("</td>\n");
            sb.append("      <td>").append(chamado.getCodigoSalaAtendimento()).append("</td>\n");
            sb.append("      <td>").append(chamado.getDepartamento().getNome()).append("</td>\n");
            sb.append("    </tr>\n");
        }

        return sb.toString();
    }
}
