package com.github.gabrielsilper.repository.impl;

import com.github.gabrielsilper.domain.model.Departamento;
import com.github.gabrielsilper.repository.IDepartamentoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DepartamentoRepositoryImpl implements IDepartamentoRepository {
    private final List<Departamento> departamentos;
    private int proximoId = 1;

    public DepartamentoRepositoryImpl (List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public DepartamentoRepositoryImpl () {
        this.departamentos = new ArrayList<>();
    }

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

    @Override
    public Departamento buscarPorId(Integer id) {
        return departamentos.stream()
                .filter(departamento -> Objects.equals(departamento.getId(), id))
                .findFirst().orElse(null);
    }
}
