package com.github.gabrielsilper.servlet;

import com.github.gabrielsilper.domain.model.Chamado;
import com.github.gabrielsilper.domain.model.Departamento;
import com.github.gabrielsilper.repository.IChamadoRepository;
import com.github.gabrielsilper.repository.IDepartamentoRepository;
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
       String action = req.getParameter("action");
       String filtro = req.getParameter("pesquisa");

        if ("salvar".equals(action)) {
            salvarChamado(req);
        }

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
                </html>""", gerarHeader(), gerarMain(action, filtro));
    }

    private void salvarChamado(HttpServletRequest req) {
        String titulo = req.getParameter("titulo");
        String solicitante = req.getParameter("solicitante");
        String descricao = req.getParameter("descricao");
        String codigoEquipamento = req.getParameter("codigoEquipamento");
        String codigoSalaAtendimento = req.getParameter("codigoSalaAtendimento");
        String departamentoId = req.getParameter("departamentoId");

        if (titulo == null || titulo.isBlank()
                || solicitante == null || solicitante.isBlank()
                || descricao == null || descricao.isBlank()
                || codigoEquipamento == null || codigoEquipamento.isBlank()
                || codigoSalaAtendimento == null || codigoSalaAtendimento.isBlank()
                || departamentoId == null || departamentoId.isBlank()) {
            return;
        }

        Integer idDepartamento = Integer.parseInt(departamentoId);
        Departamento departamento = departamentoRepository.buscarPorId(idDepartamento);

        if (departamento == null) {
            return;
        }

        chamadoRepository.adicionar(new Chamado(
                titulo,
                solicitante,
                descricao,
                Integer.parseInt(codigoEquipamento),
                Integer.parseInt(codigoSalaAtendimento),
                departamento
        ));
    }

    private String gerarMain(String action, String filtro) {
        if (action == null) {
            action = "listar";
        }

        String conteudo;
        if ("cadastrar".equals(action)) {
            conteudo = gerarFormCadastro();
        } else {
            conteudo = gerarTabelaChamados(filtro);
        }

        return String.format("""
                <main>
                  %s
                </main>
                """, conteudo);
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
                    <a href="chamados?action=cadastrar">Cadastrar Chamado</a>
                </header>
                """;
    }

    private String gerarFormCadastro() {
        String opcoes = gerarOpcoesDepartamentos();

        return String.format("""
                <form method="get" action="chamados">
                  <input type="hidden" name="action" value="salvar"/>
                  <div>
                    <label for="titulo">Título:</label>
                    <input type="text" id="titulo" name="titulo"/>
                  </div>
                  <div>
                    <label for="solicitante">Solicitante:</label>
                    <input type="text" id="solicitante" name="solicitante"/>
                  </div>
                  <div>
                    <label for="descricao">Descrição:</label>
                    <input type="text" id="descricao" name="descricao"/>
                  </div>
                  <div>
                    <label for="codigoEquipamento">Patrimônio:</label>
                    <input type="number" id="codigoEquipamento" name="codigoEquipamento"/>
                  </div>
                  <div>
                    <label for="codigoSalaAtendimento">Sala:</label>
                    <input type="number" id="codigoSalaAtendimento" name="codigoSalaAtendimento"/>
                  </div>
                  <div>
                    <label for="departamentoId">Departamento:</label>
                    <select id="departamentoId" name="departamentoId">
                      %s
                    </select>
                  </div>
                  <button type="submit">Salvar</button>
                </form>
                """, opcoes);
    }

    private String gerarOpcoesDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.listar();
        StringBuilder sb = new StringBuilder();

        for (Departamento departamento : departamentos) {
            sb.append("      <option value=\"").append(departamento.getId()).append("\">")
                    .append(departamento.getNome()).append("/").append(departamento.getSigla())
                    .append("</option>\n");
        }

        return sb.toString();
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
