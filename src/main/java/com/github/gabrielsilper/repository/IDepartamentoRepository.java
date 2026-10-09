package com.github.gabrielsilper.repository;

import com.github.gabrielsilper.domain.model.Departamento;

import java.util.List;

public interface IDepartamentoRepository {
    void adicionar(Departamento departamento);

    List<Departamento> listar();
}
