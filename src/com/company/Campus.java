package com.company;

import java.util.*;

public class Campus {
    private ArrayList<Local> locais;
    private ArrayList<Connection> connections;

    public Campus() {
        this.locais = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public boolean localExiste(int codigo) {
        for (Local l : locais) {
            if (l.getCodigo() == codigo)
                return true;
        }

        return false;
    }

    public boolean connectionExiste(int codigo1, int codigo2) {
        for (Connection c : connections) {
            if (
                    (c.passaPor(codigo1) && c.passaPor(codigo2) && ! c.isLaco())
                    || (c.isLaco() && codigo1 == codigo2 && c.passaPor(codigo1))
            ){
                return true;
            }
        }

        return false;
    }

    public boolean criarLocal(int codigo, String nome, String tipo) {
        if (localExiste(codigo) || codigo <= 0)
            return false;

        this.locais.add(new Local(codigo, nome, tipo));

        return true;
    }

    public boolean removerLocal(int codigo) throws Exception {
        if (!localExiste(codigo))
            return false;

        this.removerConexoes(codigo);
        this.locais.remove(this.byCodigo(codigo));

        return true;
    }

    public Local byCodigo(int codigo) throws Exception {
        for (Local l : locais) {
            if (l.getCodigo() == codigo)
                return l;
        }

        throw new Exception("Código não encontrado");
    }

    public boolean criarConexao(int codigo1, int codigo2) {
        if (!localExiste(codigo1) || !localExiste(codigo2))
            return false;

        this.connections.add( new Connection(codigo1, codigo2) );

        return true;
    }

    public boolean removerConexao(int codigo1, int codigo2) {
        if (!localExiste(codigo1) || !localExiste(codigo2))
            return false;

        connections.removeIf(c -> c.passaPor(codigo1) && c.passaPor(codigo2));

        return true;
    }

    public boolean removerConexoes(int codigo) {
        if (!localExiste(codigo))
            return false;

        for (Local l: locais)
            connections.removeIf(c -> c.passaPor(l.getCodigo()) && c.passaPor(codigo));

        return true;
    }

    public ArrayList<Local> getLocais() {
        return locais;
    }

    public int grau(int codigo) {
        if (! localExiste(codigo)) {
            System.out.println("Local não existe no grafo");
            return -1;
        }

        int g = 0;

        for (Connection c : connections)
            if (c.passaPor(codigo))
                g++;

        return g;
    }

    public boolean adjacentes(int codigo1, int codigo2) {
        for (Connection c : connections) {
            if (c.verify(codigo1, codigo2)) {
                return true;
            }
        }

        return false;
    }

    public boolean existeLaco(int codigo) {
        for (Connection c : connections) {
            if (c.passaPor(codigo) && c.isLaco())
                return true;
        }

        return false;
    }

    public ArrayList<Integer> adjacentes(int codigo) {
        ArrayList<Integer> adj = new ArrayList<>();

        for (Connection c : connections) {
            if (c.passaPor(codigo))
                adj.add(c.outroCodigo(codigo));
        }

        return adj;
    }

    public boolean temCaminho(int codigo1, int codigo2) {
        if (!localExiste(codigo1) || !localExiste(codigo2))
            return false;

        ArrayList<Local> locaisPossiveis = new ArrayList<>();
        locaisPossiveis = (ArrayList) locais.clone();

        ArrayList<Integer> adjacentes = adjacentes(codigo1);

        while (locaisPossiveis.size() > 0) {
            locaisPossiveis.removeIf(l -> l.getCodigo() == codigo1);

            if (adjacentes.size() == 0)
                break;

            if (adjacentes.contains(codigo2))
                return true;

            locaisPossiveis.removeIf(l -> adjacentes.contains(l.getCodigo()));

            ArrayList<Integer> temp = new ArrayList<>();
            temp = (ArrayList<Integer>) adjacentes.clone();
            adjacentes.clear();

            for (int i : temp)
                adjacentes.addAll(this.adjacentes(i));

            // remover os q nao sao mais possiveis
            // mapear os possiveis para retornar os codigos
            ArrayList<Integer> codigosPossiveis = new ArrayList<>();
            for (Local l : locaisPossiveis)
                codigosPossiveis.add(l.getCodigo());

            adjacentes.removeIf(a -> ! codigosPossiveis.contains(a));
        }

        return false;
    }

    public void printCaminho(int codigo1, int codigo2) throws Exception {
        // Lista de codigos já visitados durante a execução recursiva
        ArrayList<Integer> visitados = new ArrayList<>();

        // Lista que guarda o caminho
        ArrayList<String> caminho = new ArrayList<>();

        // Adiciona o ponto de partida na lista de visitados e no caminho
        visitados.add(codigo1);
        Local current = this.byCodigo(codigo1);
        caminho.add(current.getNome());

        // Inicia busca recursiva
        recursivePrintCaminho(codigo1, codigo2, visitados, caminho);
    }

    public void recursivePrintCaminho(int codigo1, int codigo2, ArrayList<Integer> visitados, ArrayList<String> caminho) throws Exception
    {
        // Verifica se o codigo foi encontrado e printa o caminho
        if (codigo1 == codigo2) {
            System.out.println(caminho);
            return;
        }

        // Adiciona o codigo atual aos visitados para evitar repetições
        visitados.add(codigo1);

        // Itera pelos adjacentes do vertice atual
        for (int a : this.adjacentes(codigo1)) {
            // Verifica se o vertice atual ja foi visitado para evitar repetições
            if (! visitados.contains(a)) {
                // Buscar local pelo código para obter o nome
                Local l = this.byCodigo(a);
                // Adiciona nome ao caminho
                caminho.add(l.getNome());

                // Continua busca recursiva nos adjacentes ao atual
                recursivePrintCaminho(a, codigo2, visitados, caminho);

                // Remove o atual do caminho, pois não encontrou o destino nos seus adjacentes
                caminho.remove(l.getNome());
            }
        }
    }

    public void printLocais() {
        for (Local l : this.locais) {
            System.out.println(
                    l.getNome() + " (código=" + l.getCodigo() + ")"
            );
        }
    }

    public ArrayList<Connection> conexoesPorLocal(int codigo) {
        ArrayList<Connection> cs = (ArrayList) connections.clone();
        cs.removeIf(c -> ! c.passaPor(codigo));
        return cs;
    }

    public void print() {
        for (Local l : this.locais) {
            System.out.println(
                    l.getNome()
                        + " (código=" + l.getCodigo() + ")"
                        + " - " + conexoesToString(conexoesPorLocal(l.getCodigo()), l.getCodigo())
            );
        }
    }

    private String conexoesToString(ArrayList<Connection> cs, int codigo) {
        String ret = "[";

        int size = cs.size();

        for (int i = 0; i < size; i++) {
            ret += cs.get(i).outroCodigo(codigo);

            if (i < size - 1)
                ret += ",";
        }

        ret += "]";

        return ret;
    }

    public void ponderar() {
        System.out.println("Informe os valores das arestas:");
        Scanner scn = new Scanner(System.in);
        for (Connection c : connections) {
            System.out.println("Aresta " + c.toString() + " :");
            int valor = scn.nextInt();
            c.setValue(valor);
        }
    }

    public void printConnetions() {
        for (Connection c : connections) {
            String str = c.toString();

            if (c.getValue() != Connection.emptyValue) {
                str += " Valor=" + c.getValue();
            }

            System.out.println(str);
        }
    }

    public boolean isSubGrafo(ArrayList<Local> locais, ArrayList<Connection> connections) {
        if (! (locais.size() > 0) || ! (connections.size() > 0))
            return false;

        for (Local l : locais) {
            if (! localExiste(l.getCodigo()))
                return false;
        }

        for (Connection c : connections) {
            if (! connectionExiste(c.getCodigo(0), c.getCodigo(1)))
                return false;
        }

        return true;
    }

    public boolean isCompleto() {
        for (Local l : locais) {
            for (Local li : locais) {
                if (l.getCodigo() == li.getCodigo())
                    continue;

                if (! adjacentes(l.getCodigo(), li.getCodigo()))
                    return false;
            }
        }

        return true;
    }
}
