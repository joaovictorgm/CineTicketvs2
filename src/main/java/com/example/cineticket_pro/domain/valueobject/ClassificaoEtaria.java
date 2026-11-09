package com.example.cineticket_pro.domain.valueobject;

public class ClassificaoEtaria {

    private final int idadeMinima;

    public ClassificaoEtaria(String valor){
        String classificao = valor == null ? "" : valor.trim().toUpperCase().replace("+", "").replace("ANOS", "").trim();

        int minima;
        if(classificao.equals("L") || classificao.equals("LIVRE")) {
            minima = 0;
        } else if (classificao.matches("10|12|14|16|18")) {
            minima = Integer.parseInt(classificao);
        } else {
            throw new IllegalArgumentException("Classificação etária inválida");
        }
        this.idadeMinima = minima;
    }

    public boolean permiteIdade(int idade) {
        return idade >= idadeMinima;
    }

    @Override
    public boolean equals(Object o){
        return o instanceof  ClassificaoEtaria classificaoEtaria && idadeMinima == classificaoEtaria.idadeMinima;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(idadeMinima);
    }

    @Override
    public String toString(){
        return idadeMinima == 0 ? "L" : String.valueOf(idadeMinima);
    }
}
