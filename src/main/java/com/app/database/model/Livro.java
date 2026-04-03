package com.app.database.model;

import java.time.LocalDateTime;

public class Livro {
    private Long id;
    private String titulo;
    private Long autorId;
    private Boolean disponivel;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;

    public Livro() {

    }

    public Livro(Long id, String titulo, Long autorId, Boolean disponivel, LocalDateTime dataCadastro, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
        this.disponivel = disponivel;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Livro(Long id, String titulo, Long autorId) {
        this.id = id;
        this.titulo = titulo;
        this.autorId = autorId;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}