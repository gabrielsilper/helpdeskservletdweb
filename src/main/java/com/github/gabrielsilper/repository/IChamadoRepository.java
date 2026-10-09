package com.github.gabrielsilper.repository;

import com.github.gabrielsilper.domain.model.Chamado;

import java.util.List;

public interface IChamadoRepository {
    void adicionar(Chamado chamado);
    List<Chamado> listar();
    List<Chamado> listar(String filtro);
    Chamado buscarPorId(Integer id);
    void atualizar(Chamado chamado);
}
