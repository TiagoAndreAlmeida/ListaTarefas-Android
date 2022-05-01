package com.example.listatarefas.model;

import java.io.Serializable;

public class Tarefa implements Serializable {
    private String nome;
    private Integer id;

    public Tarefa(String nome, Integer id) {
        this.nome = nome;
        this.id = id;
    }

    public  Tarefa() {}

    public Tarefa(String tarefaNome) {
        this.nome = tarefaNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
