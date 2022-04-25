package com.example.testelogin;

import java.io.Serializable;

public class Contato implements Serializable {
    String nome, email, usuario,senha;
    int id;

    public int getId() {        return id;    }
    public void setId(int id) {        this.id = id;    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    @Override
    public String toString(){
        return getId()+" "+getNome().toString()+": email: "+getEmail().toString();
    }
}

