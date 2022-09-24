package com.company;

import java.util.ArrayList;

public class Connection {
    public static int emptyValue = -999999999;

    private ArrayList<Integer> codigos;
    private int value;

    public Connection(int codigo1, int codigo2) {
        this.codigos = new ArrayList<>();
        codigos.add(codigo1);
        codigos.add(codigo2);
        this.value = emptyValue;
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
        if (isLaco())
            return codigo;

        for (int code : codigos)
            if (codigo != code)
                return code;

        return -1;
    }

    @Override
    public String toString() {
        return this.codigos.get(0) + "-" + this.codigos.get(1);
    }
}
