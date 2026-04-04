package com.app.database.model;

import java.time.LocalDateTime;

public class Emprestimo {
    private Long id;
    private Long livroId;
    private Long usuarioId;
    private LocalDateTime dataEmprestimo;
    private LocalDateTime dataDevolucao;

    public Emprestimo(long id, long livroId, long usuarioId) {
        this.id = id;
        this.livroId = livroId;
        this.usuarioId = usuarioId;
    }

    public Emprestimo(long livroId, long usuarioId) {
        this.livroId = livroId;
        this.usuarioId = usuarioId;
    }

    public Emprestimo() {
    }


    public Emprestimo(Long id, Long livroId, Long usuarioId, LocalDateTime dataEmprestimo, LocalDateTime dataDevolucao) {
        this.id = id;
        this.livroId = livroId;
        this.usuarioId = usuarioId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public LocalDateTime getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDateTime dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDateTime getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
