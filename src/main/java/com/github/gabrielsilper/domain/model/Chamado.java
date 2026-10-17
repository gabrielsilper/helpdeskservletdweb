package com.github.gabrielsilper.domain.model;

import com.github.gabrielsilper.domain.enums.TipoAtendimento;

import java.util.Objects;

public class Chamado {
    private Integer id;
    private String titulo;
    private String solicitante;
    private String descricao;
    private int codigoEquipamento;
    private int codigoSalaAtendimento;
    private TipoAtendimento tipoAtendimento;
    private Departamento departamento;

    public Chamado() {}

    public Chamado(
            String titulo,
            String solicitante,
            String descricao,
            int codigoEquipamento,
            int codigoSalaAtendimento,
            TipoAtendimento tipoAtendimento,
            Departamento departamento
    ) {
        this.titulo = titulo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.codigoEquipamento = codigoEquipamento;
        this.codigoSalaAtendimento = codigoSalaAtendimento;
        this.tipoAtendimento = tipoAtendimento;
        this.departamento = departamento;
    }

    public Chamado(
            Integer id,
            String titulo,
            String solicitante,
            String descricao,
            int codigoEquipamento,
            int codigoSalaAtendimento,
            TipoAtendimento tipoAtendimento,
            Departamento departamento
    ) {
        this.id = id;
        this.titulo = titulo;
        this.solicitante = solicitante;
        this.descricao = descricao;
        this.codigoEquipamento = codigoEquipamento;
        this.codigoSalaAtendimento = codigoSalaAtendimento;
        this.tipoAtendimento = tipoAtendimento;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodigoEquipamento() {
        return codigoEquipamento;
    }

    public void setCodigoEquipamento(int codigoEquipamento) {
        this.codigoEquipamento = codigoEquipamento;
    }

    public int getCodigoSalaAtendimento() {
        return codigoSalaAtendimento;
    }

    public void setCodigoSalaAtendimento(int codigoSalaAtendimento) {
        this.codigoSalaAtendimento = codigoSalaAtendimento;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Chamado that = (Chamado) o;
        return codigoEquipamento == that.codigoEquipamento
                && codigoSalaAtendimento == that.codigoSalaAtendimento
                && Objects.equals(tipoAtendimento, that.tipoAtendimento)
                && Objects.equals(id, that.id)
                && Objects.equals(titulo, that.titulo)
                && Objects.equals(solicitante, that.solicitante)
                && Objects.equals(descricao, that.descricao)
                && Objects.equals(departamento, that.departamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, solicitante, descricao, codigoEquipamento,
                codigoSalaAtendimento, tipoAtendimento, departamento);
    }

    @Override
    public String toString() {
        return "Chamado{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", solicitante='" + solicitante + '\'' +
                ", descricao='" + descricao + '\'' +
                ", codigoEquipamento=" + codigoEquipamento +
                ", codigoSalaAtendimento=" + codigoSalaAtendimento +
                ", tipoAtendimento=" + tipoAtendimento +
                ", departamento=" + departamento +
                '}';
    }
}
