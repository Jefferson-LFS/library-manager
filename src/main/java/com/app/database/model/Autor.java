package com.app.database.model;

import java.time.LocalDate;

public class Autor {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;

    public Autor() {

    }

    public Autor(Long id, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Autor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}

