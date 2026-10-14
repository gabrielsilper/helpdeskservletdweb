package com.github.gabrielsilper.repository.impl;

import com.github.gabrielsilper.dao.CidadeDAO;
import com.github.gabrielsilper.domain.model.Cidade;
import com.github.gabrielsilper.repository.ICidadeRepository;

import java.util.List;

public class CidadeRepositoryImpl implements ICidadeRepository {
    private final CidadeDAO cidadeDAO;

    public CidadeRepositoryImpl(CidadeDAO cidadeDAO) {
        this.cidadeDAO = cidadeDAO;
    }

    @Override
    public List<Cidade> listar() {
        return cidadeDAO.listar();
    }
}
