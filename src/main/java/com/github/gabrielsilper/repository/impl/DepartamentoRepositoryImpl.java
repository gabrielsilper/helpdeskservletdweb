package com.github.gabrielsilper.repository.impl;

import com.github.gabrielsilper.domain.model.Departamento;
import com.github.gabrielsilper.repository.IDepartamentoRepository;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoRepositoryImpl implements IDepartamentoRepository {
    private final List<Departamento> departamentos = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void adicionar(Departamento departamento) {
        if (departamento.getId() == null) {
            departamento.setId(proximoId++);
        }
        departamentos.add(departamento);
    }

    @Override
    public List<Departamento> listar() {
        return new ArrayList<>(departamentos);
    }
}
