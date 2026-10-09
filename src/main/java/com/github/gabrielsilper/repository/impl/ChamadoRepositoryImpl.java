package com.github.gabrielsilper.repository.impl;

import com.github.gabrielsilper.domain.model.Chamado;
import com.github.gabrielsilper.repository.IChamadoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChamadoRepositoryImpl implements IChamadoRepository {
    private final List<Chamado> chamados;
    private int proximoId = 1;

    public ChamadoRepositoryImpl (List<Chamado> chamados) {
        this.chamados = chamados;
    }

    public ChamadoRepositoryImpl () {
        this.chamados = new ArrayList<>();
    }

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

    @Override
    public List<Chamado> listar(String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return listar();
        }

        String termo = filtro.toLowerCase();

        return chamados.stream()
                .filter(chamado -> chamado.getTitulo().toLowerCase().contains(termo)
                        || chamado.getSolicitante().toLowerCase().contains(termo))
                .toList();
    }

    @Override
    public Chamado buscarPorId(Integer id) {
        return chamados.stream()
                .filter(chamado -> Objects.equals(chamado.getId(), id))
                .findFirst().orElse(null);
    }

    @Override
    public void atualizar(Chamado chamado) {
        for (int i = 0; i < chamados.size(); i++) {
            if (Objects.equals(chamados.get(i).getId(), chamado.getId())) {
                chamados.set(i, chamado);
                return;
            }
        }
    }
}
