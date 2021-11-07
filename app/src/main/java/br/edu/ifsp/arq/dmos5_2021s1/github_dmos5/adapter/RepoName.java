package br.edu.ifsp.arq.dmos5_2021s1.github_dmos5.adapter;

import java.io.Serializable;

public class RepoName implements Serializable {

    private String name;

    public RepoName(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
