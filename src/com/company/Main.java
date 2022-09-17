package com.company;

public class Main {

    public static void main(String[] args) {
        Campus fapa = new Campus();

        fapa.criarLocal(1, "Prédio 1", "predio");
        fapa.criarLocal(2, "Prédio 3", "predio");
        fapa.criarLocal(3, "Prédio 5", "predio");
        fapa.criarLocal(4, "Prédio 7", "predio");
        fapa.criarConexao(1, 2);
        fapa.criarConexao(2, 3);
        fapa.criarConexao(3, 4);


        try {
            fapa.printCaminho(4, 2);
        }
        catch (Exception e) {
            System.out.println("Código não encontrado");
        }
    }
}
