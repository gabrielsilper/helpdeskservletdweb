package com.github.gabrielsilper.repository.impl;

import com.github.gabrielsilper.domain.model.Chamado;
import com.github.gabrielsilper.repository.IChamadoRepository;

import java.util.ArrayList;
import java.util.List;

public class ChamadoRepositoryImpl implements IChamadoRepository {
    private final List<Chamado> chamados = new ArrayList<>();
    private int proximoId = 1;

    @Override
    public void adicionar(Chamado chamado) {
        if (chamado.getId() == null) {
            chamado.setId(proximoId++);
        }
        chamados.add(chamado);
    }

    @Override
    public List<Chamado> listar() {
        return new ArrayList<>(chamados);
    }
}
