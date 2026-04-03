package com.app.database.model;

import java.time.LocalDate;

public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String perfil;
    private String email;
    private String telefone;
    private Boolean status;
    private LocalDate dataNascimento;


    public Usuario() {
    }

    public Usuario(Long id, String nome, String login, String perfil, String senha, String telefone, String email, Boolean status,LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.perfil = perfil;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.status = status;
    }

    public Usuario(Long id, String nome, String login, String perfil, Boolean status) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.perfil = perfil;
        this.status = status;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", login='" + getNome() + '\'' +
                ", perfil='" + getPerfil() + '\'' +
                '}';
    }
}
