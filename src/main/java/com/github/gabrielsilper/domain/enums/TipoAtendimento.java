package com.github.gabrielsilper.domain.enums;

import java.util.List;

// Opção 3: Rádio — Tipo de Atendimento: Presencial ou Remoto
public enum TipoAtendimento {
    PRESENCIAL("Presencial"),
    REMOTO("Remoto");

    public final String valor;

    TipoAtendimento(String valor) {
        this.valor = valor;
    }

    public static List<TipoAtendimento> getTiposAtendimento() {
        return List.of(TipoAtendimento.values());
    }
}
