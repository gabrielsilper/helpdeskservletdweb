Olá, pessoal!

Nesta atividade prática, vocês irão trabalhar em duplas para desenvolver um sistema web de Helpdesk / Chamados de Suporte de TI, aplicando os conceitos de arquitetura Web com Servlets Java e manipulação de formulários HTTP vistos em sala.

## 1. Estrutura do Modelo de Domínio

O projeto deve conter pelo menos duas classes no pacote model:

- **Departamento:** atributos para identificação, nome do setor (ex: *"RH"*, *"Financeiro"*, *"Laboratórios"*, *"Secretaria"*) e sigla/responsável.
- **Chamado:**
  - **Campos textuais:** Título do chamado, Solicitante (nome do usuário) e Descrição do problema.
  - **Campo numérico:** Número do Patrimônio do equipamento (ou Número da Sala do atendimento).
  - **Relacionamento/Seleção:** Associação direta com um objeto Departamento.

## 2. Requisitos da Aplicação (ChamadoServlet)

A aplicação deve ser desenvolvida em um único Servlet gerenciando o ciclo de vida dos dados em memória:

- **Carga Inicial (init):** Pré-carregar uma lista de departamentos disponíveis e pelo menos 3 chamados cadastrados para demonstração imediata.

### Listagem com Filtro (Read)

- Tabela exibindo todos os dados dos chamados cadastrados.
- Campo de busca funcional para filtrar chamados por título ou solicitante.
- Ações por linha: links diretos para **Alterar** e **Excluir** o registro correspondente.

### Cadastro (Create)

- Formulário com campos de texto, numérico e um `<select>` populado dinamicamente com os departamentos cadastrados.

### Edição (Update)

- O formulário deve carregar os dados do chamado selecionado e manter o `<select>` com o departamento atual pré-selecionado (`selected`).

### Exclusão (Delete)

- Remover o chamado da lista em memória e atualizar a listagem.

## Entrega

- **Formato:** Arquivo compactado no formato `.zip` contendo todo o código-fonte (pasta `src`) do projeto.
- Apenas um aluno da dupla precisa fazer o upload do arquivo no Classroom, informando o nome completo dos dois integrantes no campo de comentários da entrega.
