package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private Campus campus;
    private int code1;
    private int code2;
    private String name;
    private String type;
    private Scanner scanner;

    public Menu(Campus campus) {
        this.campus = campus;
        this.scanner = new Scanner(System.in);
        this.code1 = 0;
        this.code2 = 0;
    }

    private void readCodes() {
        System.out.println("Digite o código do primeiro Local: ");
        code1 = scanner.nextInt();
        System.out.println("Digite o código do segundo Local: ");
        code2 = scanner.nextInt();
    }

    private void printOptions(){
        System.out.println("==== Grafos Fapa ====");
        System.out.println("1 - Exibir Grafo");
        System.out.println("2 - Adicionar Aresta");
        System.out.println("3 - Remover Aresta");
        System.out.println("4 - Verificar Adjacências");
        System.out.println("5 - Verificar Laço");
        System.out.println("6 - Verificar se existe caminho");
        System.out.println("7 - Exibir caminho");
        System.out.println("8 - Tornar ponderado");
        System.out.println("9 - Verificar subgrafo");
        System.out.println("10 - Verificar se é completo");
        System.out.println("#11 - Calcular custo do caminho entre vértices");
        System.out.println("12 - Exibir arestas");
        System.out.println("13 - Grau de um vértice");
        System.out.println("14 - Adicionar vértice");
        System.out.println("15 - Remover vértice");
        System.out.println("0 - Sair");
        System.out.println("Escolha a opção: ");
    }

    public void render() throws Exception {
        boolean exit = false;

        while (!exit) {
            printOptions();

            int choice = this.scanner.nextInt();

            switch(choice) {
                case 1:
                    System.out.println("- Grafo -");
                    this.campus.print();
                    System.in.read();
                    break;
                case 2:
                    System.out.println("- Adicionar Aresta -");
                    this.readCodes();
                    if(this.campus.adjacentes(code1,code2)){
                        System.out.println("A aresta já existe.");
                        System.in.read();
                        break;
                    }
                    boolean added = this.campus.criarConexao(code1, code2);
                    System.out.println(added ? "Aresta adicionada!" : "Algum dos códigos não existe no grafo, verifique e tente novamente.");
                    System.in.read();
                    break;
                case 3:
                    System.out.println("- Remover Aresta -");
                    this.readCodes();
                    boolean removed = this.campus.removerConexao(code1, code2);
                    System.out.println(removed ? "Aresta removida!" : "Aresta não encontrada.");
                    System.in.read();
                    break;
                case 4:
                    System.out.println("- Verificar Adjacência -");
                    this.readCodes();
                    if(!this.campus.localExiste(code1) || !this.campus.localExiste(code2)){
                        System.out.println("Vertice informado não existe");
                        System.in.read();
                        break;
                    }
                    boolean adj = this.campus.adjacentes(code1, code2);
                    System.out.println(adj ? "São adjacentes!" : "Não são adjacentes.");
                    System.in.read();
                    break;
                case 5:
                    System.out.println("- Verificar Laço -");
                    System.out.println("Digite o código:");
                    code1 = scanner.nextInt();
                    if(!this.campus.localExiste(code1)){
                        System.out.println("Vertice informado não existe");
                        System.in.read();
                        break;
                    }
                    boolean laco = this.campus.existeLaco(code1);
                    System.out.println(laco ? "Existe laço!" : "Não existe laço nesse vértice.");
                    System.in.read();
                    break;
                case 6:
                    System.out.println("- Verificar se existe caminho -");
                    this.readCodes();
                    if(!this.campus.localExiste(code1) || !this.campus.localExiste(code2)){
                        System.out.println("Vertice informado não existe");
                        System.in.read();
                        break;
                    }
                    boolean exists = this.campus.temCaminho(code1, code2);
                    System.out.println(exists ? "Existe um caminho!" : "Não existe caminho.");
                    System.in.read();
                    break;
                case 7:
                    System.out.println("- Exibir caminho -");
                    this.readCodes();
                    if(!this.campus.localExiste(code1) || !this.campus.localExiste(code2)){
                        System.out.println("Vertice informado não existe");
                        System.in.read();
                        break;
                    }
                    if (!this.campus.temCaminho(code1, code2)) {
                        System.out.println("Não existe caminho entre estes vértices!");
                        System.in.read();
                        break;
                    }

                    this.campus.printCaminho(code1, code2);
                    System.in.read();
                    break;
                case 8:
                    System.out.println("- Tornar Ponderado - ");
                    this.campus.ponderar();
                    System.out.println("Pronto!");
                    System.in.read();
                    break;
                case 9:
                    System.out.println("- Verificar SubGrafo -");
                    System.out.println("Informe os vértices do subgrafo: ");
                    ArrayList<Local> subLocais = new ArrayList<>();
                    code1 = -1;
                    while(code1 != 0) {
                        System.out.println("Informe o código do próximo vértice (0 para finalizar):");
                        code1 = scanner.nextInt();
                        if (code1 == 0)
                            break;

                        subLocais.add(new Local(code1, "", ""));
                    }

                    ArrayList<Connection> subConnections = new ArrayList<>();
                    System.out.println("Informe as arestas do subgrafo: ");
                    code1 = -1;
                    while(code1 != 0) {
                        System.out.println("Informe o código do primeiro vértice (0 para finalizar):");
                        code1 = scanner.nextInt();
                        if (code1 == 0)
                            break;
                        System.out.println("Informe o código do segundo vértice:");
                        code2 = scanner.nextInt();

                        subConnections.add(new Connection(code1, code2));
                    }

                    boolean isSubGrafo = this.campus.isSubGrafo(subLocais, subConnections);

                    System.out.println("====");
                    System.out.println("O grafo informado " + (isSubGrafo ? "" : "não ") + "é um subgrafo!");
                    System.in.read();
                    break;
                case 10:
                    System.out.println("- Verificar se é completo - ");
                    boolean completo = this.campus.isCompleto();
                    System.out.println("O grafo informado " + (completo ? "" : "não ") + "é completo!");
                    System.in.read();
                    break;
                case 12:
                    System.out.println("- Exibir Arestas - ");
                    this.campus.printConnetions();
                    System.in.read();
                    break;
                case 13:
                    System.out.println("- Gráu de um vértice - ");
                    System.out.println("Digite o código: ");
                    code1 = scanner.nextInt();
                    if(!this.campus.localExiste(code1)){
                        System.out.println("Vertice informado não existe");
                        System.in.read();
                        break;
                    }
                    int grau = this.campus.grau(code1);
                    if (grau != -1)
                        System.out.println("Grau: " + grau);
                    System.in.read();
                    break;
                case 14:
                    System.out.println("- Criar vértice - ");
                    System.out.println("Digite o código do vertice: ");
                    code1 = scanner.nextInt();
                    System.out.println("Digite o nome do vertice: ");
                    name = scanner.next();
                    System.out.println("Digite o tipo do vertice: ");
                    type = scanner.next();
                    boolean created = this.campus.criarLocal(code1, name, type);
                    System.out.println(created ? "Vértice criado!" : "Vértice não foi criado, verifique se já existe um vértice com o mesmo código.");
                    System.in.read();
                    break;
                case 15:
                    System.out.println("- Remover vértice - ");
                    System.out.println("Digite o código do vertice: ");
                    code1 = scanner.nextInt();
                    boolean remove = this.campus.removerLocal(code1);
                    System.out.println(remove ? "Vértice removido!" : "Vértice não foi removido, verifique se código informado existe.");
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Não implementado");
                    System.in.read();
                    break;
            }
        }
    }
}
