package com.company;

import java.util.ArrayList;

public class Connection {
    private ArrayList<Integer> codigos;
    private int value;

    public Connection(int codigo1, int codigo2) {
        this.codigos = new ArrayList<>();
        codigos.add(codigo1);
        codigos.add(codigo2);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean passaPor(int codigo) {
        return codigos.contains(codigo);
    }

    public boolean isLaco() {
        return this.codigos.get(0) == this.codigos.get(1);
    }

    public int outroCodigo(int codigo) {
        for (int code : codigos)
            if (codigo != code)
                return code;

        return -1;
    }
}
