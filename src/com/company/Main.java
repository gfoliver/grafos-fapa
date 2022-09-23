package com.company;

public class Main {

    public static void main(String[] args) {
        Campus fapa = new Campus();

        fapa.criarLocal(1, "Prédio 1", "predio");
        fapa.criarLocal(3, "Prédio 3", "predio");
        fapa.criarLocal(5, "Prédio 5", "predio");
        fapa.criarLocal(7, "Prédio 7", "predio");
        fapa.criarConexao(1, 3);
        fapa.criarConexao(3, 5);
        fapa.criarConexao(5, 7);

        Menu menu = new Menu(fapa);

        try {
            menu.render();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
