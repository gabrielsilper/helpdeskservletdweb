package com.github.gabrielsilper.domain.model;

import java.util.Objects;

public class Departamento {
    private Integer id;
    private String nome;
    private String sigla;
    private String responsavel;

    public Departamento() {
    }

    public Departamento(String nome, String sigla, String responsavel) {
        this.nome = nome;
        this.sigla = sigla;
        this.responsavel = responsavel;
    }

    public Departamento(Integer id, String nome, String sigla, String responsavel) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.responsavel = responsavel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Departamento that = (Departamento) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(sigla,
                that.sigla
        ) && Objects.equals(responsavel, that.responsavel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, sigla, responsavel);
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sigla='" + sigla + '\'' +
                ", responsavel='" + responsavel + '\'' +
                '}';
    }
}
