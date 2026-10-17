package com.github.gabrielsilper.servlet;

import com.github.gabrielsilper.domain.enums.TipoAtendimento;
import com.github.gabrielsilper.domain.model.Chamado;
import com.github.gabrielsilper.domain.model.Departamento;
import com.github.gabrielsilper.repository.IChamadoRepository;
import com.github.gabrielsilper.repository.IDepartamentoRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
                TipoAtendimento.PRESENCIAL,
                laboratorios
        ));
        chamadoRepository.adicionar(new Chamado(
                "Impressora sem tinta",
                "Fernanda Lima",
                "Impressora do setor financeiro parou de imprimir e exibe alerta de toner.",
                20567,
                203,
                TipoAtendimento.PRESENCIAL,
                financeiro
        ));
        chamadoRepository.adicionar(new Chamado(
                "Rede lenta no setor",
                "Paulo Mendes",
                "Conexão com a internet muito lenta, dificultando o acesso ao sistema de folha.",
                30112,
                105,
                TipoAtendimento.PRESENCIAL,
                rh
        ));

        chamadoRepository.adicionar(new Chamado(
                "Título teste para chamado remoto",
                "Dona Maria",
                "Uma descrição teste para o chamado remoto",
                40513,
                404,
                TipoAtendimento.REMOTO,
                secretaria
        ));
    }

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        String action = req.getParameter("action");
        String filtro = req.getParameter("pesquisa");
        String id = req.getParameter("id");

        if ("salvar".equals(action)) {
            salvarChamado(req);
            resp.sendRedirect("chamados");
            return;
        }

        if ("atualizar".equals(action)) {
            atualizarChamado(req);
            resp.sendRedirect("chamados");
            return;
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
                  </body>
                </html>""", gerarMain(action, filtro, id)
        );
    }

    private void salvarChamado(HttpServletRequest req) {
        String titulo = req.getParameter("titulo");
        String solicitante = req.getParameter("solicitante");
        String descricao = req.getParameter("descricao");
        String codigoEquipamento = req.getParameter("codigoEquipamento");
        String codigoSalaAtendimento = req.getParameter("codigoSalaAtendimento");
        String tipoAtendimento = req.getParameter("tipoAtendimento");
        String departamentoId = req.getParameter("departamentoId");

        if (titulo == null || titulo.isBlank()
                || solicitante == null || solicitante.isBlank()
                || descricao == null || descricao.isBlank()
                || codigoEquipamento == null || codigoEquipamento.isBlank()
                || codigoSalaAtendimento == null || codigoSalaAtendimento.isBlank()
                || tipoAtendimento == null || tipoAtendimento.isBlank()
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
                TipoAtendimento.valueOf(tipoAtendimento),
                departamento
        ));
    }

    private void atualizarChamado(HttpServletRequest req) {
        String id = req.getParameter("id");
        String titulo = req.getParameter("titulo");
        String solicitante = req.getParameter("solicitante");
        String descricao = req.getParameter("descricao");
        String codigoEquipamento = req.getParameter("codigoEquipamento");
        String codigoSalaAtendimento = req.getParameter("codigoSalaAtendimento");
        String departamentoId = req.getParameter("departamentoId");

        if (id == null || id.isBlank()
                || titulo == null || titulo.isBlank()
                || solicitante == null || solicitante.isBlank()
                || descricao == null || descricao.isBlank()
                || codigoEquipamento == null || codigoEquipamento.isBlank()
                || codigoSalaAtendimento == null || codigoSalaAtendimento.isBlank()
                || departamentoId == null || departamentoId.isBlank()) {
            return;
        }

        Chamado chamado = chamadoRepository.buscarPorId(Integer.parseInt(id));
        Departamento departamento = departamentoRepository.buscarPorId(Integer.parseInt(departamentoId));

        if (chamado == null || departamento == null) {
            return;
        }

        chamado.setTitulo(titulo);
        chamado.setSolicitante(solicitante);
        chamado.setDescricao(descricao);
        chamado.setCodigoEquipamento(Integer.parseInt(codigoEquipamento));
        chamado.setCodigoSalaAtendimento(Integer.parseInt(codigoSalaAtendimento));
        chamado.setDepartamento(departamento);

        chamadoRepository.atualizar(chamado);
    }

    private String gerarMain(String action, String filtro, String id) {
        if (action == null) {
            action = "listar";
        }

        // Ainda preciso melhorar esse monte de if e uso do switch para ficar algo mais legível
        String conteudo;
        switch (action) {
            case "cadastrar" -> conteudo = gerarFormCadastro();

            case "editar" -> {
                if (id != null) {
                    Chamado chamado = chamadoRepository.buscarPorId(Integer.parseInt(id));
                    if (chamado != null) {
                        conteudo = gerarFormEdicao(chamado);
                    } else {
                        conteudo = gerarTabelaChamados(filtro);
                    }
                } else {
                    conteudo = gerarTabelaChamados(filtro);
                }
            }

            case "deletar" -> {
                if (id != null) {
                    Chamado chamado = chamadoRepository.buscarPorId(Integer.parseInt(id));
                    if (chamado != null) {
                        chamadoRepository.deletar(chamado);
                    }
                }
                conteudo = gerarTabelaChamados(filtro);
            }

            default -> conteudo = gerarTabelaChamados(filtro);
        }

        return String.format("""
                <main>
                  %s
                </main>
                """, conteudo
        );
    }

    private String gerarFormCadastro() {
        String opcoes = gerarOpcoesDepartamentos(null);
        String opcoesTiposAtendimento = gerarOpcoesTiposAtendimento(null);

        return String.format("""
                <h2>Cadastra Chamado</h2>
                
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
                    <span>Qual o tipo de Atendimento?</span>
                    %s
                  </div>
                  <div>
                    <label for="departamentoId">Departamento:</label>
                    <select id="departamentoId" name="departamentoId">
                      %s
                    </select>
                  </div>
                  <button type="submit">Salvar</button>
                  <a href="chamados">Cancelar</a>
                </form>
                """, opcoesTiposAtendimento, opcoes
        );
    }

    private String gerarFormEdicao(Chamado chamado) {
        String opcoes = gerarOpcoesDepartamentos(chamado.getDepartamento().getId());
        String opcoesTiposAtendimento = gerarOpcoesTiposAtendimento(chamado.getTipoAtendimento());

        return String.format("""
                        <h2>Atualiza Chamado</h2>
                        
                        <form method="get" action="chamados">
                          <input type="hidden" name="action" value="atualizar"/>
                          <input type="hidden" name="id" value="%s"/>
                          <div>
                            <label for="titulo">Título:</label>
                            <input type="text" id="titulo" name="titulo" value="%s"/>
                          </div>
                          <div>
                            <label for="solicitante">Solicitante:</label>
                            <input type="text" id="solicitante" name="solicitante" value="%s"/>
                          </div>
                          <div>
                            <label for="descricao">Descrição:</label>
                            <input type="text" id="descricao" name="descricao" value="%s"/>
                          </div>
                          <div>
                            <label for="codigoEquipamento">Patrimônio:</label>
                            <input type="number" id="codigoEquipamento" name="codigoEquipamento" value="%s"/>
                          </div>
                          <div>
                            <label for="codigoSalaAtendimento">Sala:</label>
                            <input type="number" id="codigoSalaAtendimento" name="codigoSalaAtendimento" value="%s"/>
                          </div>
                          <div>
                            <span>Qual o tipo de Atendimento?</span>
                            %s
                          </div>
                          <div>
                            <label for="departamentoId">Departamento:</label>
                            <select id="departamentoId" name="departamentoId">
                              %s
                            </select>
                          </div>
                          <button type="submit">Atualizar</button>
                          <a href="chamados">Cancelar</a>
                        </form>
                        """, chamado.getId(), chamado.getTitulo(), chamado.getSolicitante(),
                chamado.getDescricao(), chamado.getCodigoEquipamento(),
                chamado.getCodigoSalaAtendimento(), opcoesTiposAtendimento, opcoes
        );
    }

    private String gerarOpcoesDepartamentos(Integer idDepartamentoSelecionado) {
        List<Departamento> departamentos = departamentoRepository.listar();
        StringBuilder sb = new StringBuilder();

        for (Departamento departamento : departamentos) {
            sb.append("      <option value=\"").append(departamento.getId()).append("\"");

            if (Objects.equals(departamento.getId(), idDepartamentoSelecionado)) {
                sb.append(" selected");
            }

            sb.append(">")
                    .append(departamento.getNome()).append("/").append(departamento.getSigla())
                    .append("</option>\n");
        }

        return sb.toString();
    }

    private String gerarOpcoesTiposAtendimento(TipoAtendimento tipoSelecionado) {
        List<TipoAtendimento> tiposAtendimento = TipoAtendimento.getTiposAtendimento();
        StringBuilder sb = new StringBuilder();

        for (TipoAtendimento tipoAtendimento : tiposAtendimento) {
            sb.append("<label for=\"tipoAtendimento\">").append(tipoAtendimento.valor).append(":</label>\n")
                    .append("<input type=\"radio\" id=\"tipoAtendimento\" name=\"tipoAtendimento\"");

            if (Objects.equals(tipoAtendimento, tipoSelecionado)) {
                sb.append(" checked");
            }

            sb.append(" value=\"").append(tipoAtendimento.name()).append("\" />\n\n");
        }

        return sb.toString();
    }

    private String gerarTabelaChamados(String filtro) {
        String linhas = gerarLinhasChamados(filtro);

        return String.format("""
                <form method="get" action="chamados">
                  <div>
                    <label for="pesquisa">Filtrar por título ou solicitante:</label>
                    <input type="text" id="pesquisa" name="pesquisa" placeholder="Digite o título ou solicitante..."/>
                  </div>
                  <button type="submit">Filtrar</button>
                </form>
                
                <a href="chamados?action=cadastrar">Cadastrar Chamado</a>
                <table border="1">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Título</th>
                      <th>Solicitante</th>
                      <th>Descrição</th>
                      <th>Patrimônio</th>
                      <th>Sala Atendimento</th>
                      <th>Tipo Atendimento</th>
                      <th>Departamento</th>
                      <th>Ações</th>
                    </tr>
                  </thead>
                  <tbody>
                    %s
                  </tbody>
                </table>
                """, linhas
        );
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
            sb.append("      <td>").append(chamado.getTipoAtendimento().valor).append("</td>\n");
            sb.append("      <td>").append(chamado.getDepartamento().getNome()).append("</td>\n");
            sb.append(getTableDataAcoes(chamado));
            sb.append("    </tr>\n");
        }

        return sb.toString();
    }

    private String getTableDataAcoes(Chamado chamado) {
        return String.format("""
                <td>
                  <a href="chamados?action=editar&id=%d">Alterar</a>
                  <a href="chamados?action=deletar&id=%d">Deletar</a>
                </td>
                """, chamado.getId(), chamado.getId()
        );
    }
}
