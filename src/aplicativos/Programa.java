package aplicativos;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import Classes.*;
import Dados.*;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Programa {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formatador de Data padrão
    final static String padraoData = "\\d{2}/\\d{2}/\\d{4}";

    public static void main(String[] args) throws InterruptedException, IOException {
        int opcao = 0;
        int qtdCadastrados = 0;

        ArrayList<Produto> produtos = new ArrayList<>(); // Lista de produtos cadastrados
        List<Venda> vendasRealizadas = new ArrayList<>(); // Lista de vendas cadastrados
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("\n****\nMENU\n****\n");
            System.out.println("1 - Incluir produto");
            System.out.println("2 - Consultar produto");
            System.out.println("3 - Listagem de produtos");
            System.out.println("4 - Vendas por período – detalhado");
            System.out.println("5 - Realizar venda");
            System.out.println("0 - Sair");
            System.out.print("\nOpção: ");

            opcao = in.nextInt();
            in.nextLine(); // Tira o ENTER que ficou na entrada na instrução anterior

            // Cadastro do produto
            if (opcao == 1) {

                System.out.println("-----------------------------------");
                System.out.println("        CADASTRO DE PRODUTO      ");
                System.out.println("-----------------------------------\n");

                // Cadastrar o nome do produto
                String nome;
                do {
                    System.out.print("Nome do Produto: ");
                    nome = in.nextLine();
                    if (nome.equals("")) {
                        System.out.println("O nome do produto não pode ser vazio... Tente novamente");
                    }
                } while (nome.equals(""));

                // Cadastrar código do produto
                Integer codigo = null;
                boolean codigocerto = false;
                do {
                    try {
                        System.out.print("Código do Produto: ");
                        codigo = in.nextInt();
                        in.nextLine();

                        // Verifica se já existe algum produto com este código, se sim, pede para
                        // digitar novamente
                        if (BuscaCodigo(produtos, codigo)) {
                            System.out.print("Este codigo ja pertence a um produto. Por favor, digite novamente !!\n");
                            continue;
                        }

                        codigocerto = true;

                    } catch (InputMismatchException e) {
                        System.out.println("Por favor, digite apenas números!!");
                        in.nextLine();
                    }

                } while (codigocerto == false);

                // Cadastro Valor do Produto
                double valor;
                do {
                    System.out.print("Valor do produto R$: ");
                    valor = in.nextDouble();

                    if (valor < 0) {
                        System.out.println("\nO valor do produto não poderá ser negativo");
                    }

                } while (valor < 0);

                // Cadastro Quantidade em Estoque do Produto
                Integer qtdEstoque;
                do {
                    System.out.print("Quantidade em estoque: ");
                    qtdEstoque = in.nextInt();

                    if (qtdEstoque < 0) {
                        System.out.println("\nA Quantidade em estoque não poderá se negativa");
                    }

                } while (qtdEstoque < 0);

                // Passando os "valores" preenchidos anteriormente para a lista produtos a
                // partir de um método construtor
                produtos.add(new Produto(nome, codigo, valor, qtdEstoque));

                // Cadastro finalizado.
                qtdCadastrados++;
                System.out.println("\nProduto cadastrado com sucesso !!!");
                System.out.println("\nItens Cadastrados: " + qtdCadastrados);
                voltarMenu(in);

                // Consultar produto
            } else if (opcao == 2) {

                // O método isEmpty() verifica se a string de entrada está vazia ou não
                if (produtos.isEmpty()) {
                    System.out.println("\nNão há produtos cadastrados para exibir.");
                    voltarMenu(in);
                    continue;
                }

                System.out.println("-----------------------------------");
                System.out.println("         CONSULTA DE PRODUTO       ");
                System.out.println("-----------------------------------\n");

                System.out.print("Digite o código do produto: ");
                Integer codBusca = in.nextInt();
                in.nextLine();

                for (Produto prod : produtos) {
                    if (prod.getCodigo() == codBusca) {
                        System.out.println("\n------------------------");
                        System.out.printf(
                                "Código do produto: %d \nNome do produto: %s \nValor unitário: R$ %.2f \nQuantidade em estoque: %d",
                                prod.getCodigo(), prod.getNome(), prod.getValor(), prod.getQtdEstoque());
                        System.out.println("\n------------------------");
                    }
                }

                voltarMenu(in);

                // Listagem de Produtos
            } else if (opcao == 3) {

                // Método que verifica se uma String está ou não vazia
                if (produtos.isEmpty()) {
                    System.out.println("\nNão há produtos cadastrados para exibir.");
                    voltarMenu(in);
                    continue;
                }

                // Exiba os produtos aqui
                System.out.println("---------------------------------------");
                System.out.println("          LISTAGEM DE PRODUTOS         ");
                System.out.println("---------------------------------------");

                // Estatisticas do Produto
                DoubleSummaryStatistics estatisticas = produtos.stream()
                        .collect(Collectors.summarizingDouble(Produto::getValor));

                int qtdTotalEstoque = 0;
                for (Produto prod : produtos) {
                    System.out.printf("\n>>> Produto: %d: %s \nValor unitário: R$ %.2f \nQuantidade em estoque: %d\n",
                            prod.getCodigo(), prod.getNome(), prod.getValor(), prod.getQtdEstoque());

                    qtdTotalEstoque = qtdTotalEstoque + prod.getQtdEstoque();
                }

                System.out.println("\n--------------------------------");
                System.out.println("\nTotal de " + estatisticas.getCount() + " produtos cadastrados ");
                System.out.println("Quantidade de produtos no estoque: " + qtdTotalEstoque);
                System.out.println("Valor médio: R$ " + estatisticas.getAverage());
                System.out.println("Menor valor: R$ " + estatisticas.getMin());
                System.out.println("Maior valor: R$ " + estatisticas.getMax());

                voltarMenu(in);

            } else if (opcao == 4) {

                if (vendasRealizadas.isEmpty()) {
                    System.out.println("\nNão há vendas realizadas para exibir.");
                    voltarMenu(in);
                    continue;
                }

                // Resgitro de venda realizadas
                System.out.println("-------------------------------------------");
                System.out.println("       REGISTRO DE VENDAS REALIZADAS       ");
                System.out.println("-------------------------------------------");

                System.out.println("Determine o período que esta contido as vendas");
                System.out.print("Data inicial [dd/mm/yyyy] (Digite ENTER para inserir a Data de Hoje): ");


                // INICIAL

                LocalDate dataInicial = LocalDate.parse("2000-01-01");
                boolean verificadorInicial = false;
                do {
                    try {

                        String data = in.nextLine(); // Para recolher o ENTER caso deseja a data atual
                        if (data.equals("")) {
                            String formatoPadrao = LocalDate.now().format(formatter);
                            dataInicial = LocalDate.parse(formatoPadrao, formatter);
                            verificadorInicial = true;
                            continue;
                        }

                        dataInicial = LocalDate.parse(data, formatter);
                        verificadorInicial = true;

                    } catch (DateTimeParseException e) {
                        System.out.println("\nDigite a data no padrão informado ou Pressione ENTER para colocar a data Hoje !!");
                        System.out.print("\nData Inicial [dd/mm/yyyy]: ");
                    }

                } while (!verificadorInicial);

                System.out.print("\nData Final [dd/mm/yyyy] ((Digite ENTER para inserir a Data de Hoje): ");



                // FINAL

                LocalDate dataFinal = LocalDate.parse("2000-01-01"); // Atribuição inicial para evitar erros de compilação
                boolean verificadorFinal = false;

                do {
                    try {
                        String data = in.nextLine(); // Para recolher o ENTER caso deseja a data de hoje
                        if (data.equals("")) {
                            String formatoPadrao = LocalDate.now().format(formatter);
                            dataFinal = LocalDate.parse(formatoPadrao, formatter);
                            verificadorFinal = true;
                            continue;
                        }

                        // Verificação para impedir que a data final se antes da data inicial
                        if (dataFinal.isAfter(dataInicial)) {
                            System.out.println("A data Final nao pode ser antes da INICIAL");
                            System.out.print("\nData Final [dd/mm/yyyy]: ");
                            continue;
                        }

                        dataFinal = LocalDate.parse(data, formatter);

                        verificadorFinal = true;

                    } catch (DateTimeParseException ex) {
                        System.out.println("\nDigite a data no padrão informado ou Pressione ENTER para colocar a data de Hoje");
                        System.out.print("\nData Final [dd/mm/yyyy]: ");
                    }

                } while (!verificadorFinal);

                RelatorioVendas(vendasRealizadas, dataInicial, dataFinal);

                System.out.println("\n\nPressioner ENTER para Sair...");
                in.nextLine();
                voltarMenu(in);


            } else if (opcao == 5) {

                if (produtos.isEmpty()) {
                    System.out.println("\nNão há produtos cadastrados para vender.");
                    voltarMenu(in);
                    continue;
                }

                // Resgitro de venda dos produtos
                System.out.println("------------------------------------");
                System.out.println("         REGISTRO DE VENDA          ");
                System.out.println("------------------------------------");

                System.out.println("Voltar ao MENU, digite um caractere alfabético\n");

                String verificacaoEntrada = "";
                boolean verificador = false;
                Venda novaVenda = new Venda();

                do {
                    try {
                        System.out.print("Digite o código do Produto: ");
                        verificacaoEntrada = in.nextLine();

                        if (!verificacaoEntrada.matches("[0-9]*") || verificacaoEntrada.equals("")) {
                            break;
                        }

                        int codigo = Integer.parseInt(verificacaoEntrada);

                        novaVenda.setProdutoVendido(
                                produtos.stream().filter(p -> p.getCodigo() == codigo).findFirst().get());

                        verificador = true;
                    } catch (NoSuchElementException ex) {
                        System.out.println("\nNao foi encontrado nenhum produto com o Codigo fornecido");
                    }

                } while (!verificador);

                // Volta para o Menu caso digite caracteres alfabéticos
                if (!verificador) {
                    voltarMenu(in);
                    continue;
                }

                // Continua para vendas
                verificador = false;

                System.out.printf("Produto selecionado: " + novaVenda.getProdutoVendido().getNome());
                System.out.printf("\nInforme a quantidade a ser vendida: ");

                do {
                    try {

                        novaVenda.setQtdProdutoVendido(in.nextInt());
                        in.nextLine();

                        verificador = true;

                    } catch (InputMismatchException ex) {
                        System.out.println("Digite Somente Números !!");
                        in.nextLine();

                    } catch (Qtd_Zero_Negativa_Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.print("\nQuantidade: ");
                        in.nextLine();
                    }

                } while (!verificador);

                System.out.printf("Digite a data de Venda do Produto (ENTER para Data Atual) no Formato: [dd/mm/yyyy]");
                System.out.print("\nData: ");

                verificador = false;
                do {

                    try {
                        String dataVenda = in.nextLine();

                        if (dataVenda.equals("")) { // Caso seja ENTER a entrada, atribui a data de Hoje
                            dataVenda = LocalDate.now().format(formatter).toString();

                        } else if (!dataVenda.matches(padraoData)) {
                            System.out.println("\nO Formato de Data digitado não é válido!\n");
                            System.out.printf(
                                    "\nDigite a data de Venda do Produto (ENTER para Data Atual) no Formato: [dd/mm/yyyy]");
                            System.out.print("\nData: ");
                            continue;
                        }

                        novaVenda.setData(LocalDate.parse(dataVenda, formatter));
                        verificador = true;

                    } catch (DataInvalida_Exception ex) {
                        System.out.println(ex.getMessage());
                        System.out.print("\nData: ");
                    }

                } while (!verificador);

                try {
                    novaVenda.finalizarVenda();
                    vendasRealizadas.add(novaVenda);
                    System.out.println("Produto vendido!");
                } catch (Qtd_Zero_Negativa_Exception ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("Não foi possível realizar a venda!");
                }

                /* System.out.println("\nPressioner ENTER para continuar...");
                in.nextLine(); */
                voltarMenu(in);

            } else if (opcao != 0) {
                System.out.println("\n**Opção inválida!**");
            }

        } while (opcao != 0);

        System.out.println("Fim do programa!");
        in.close();
    }

    // Voltar para o MENU
    private static void voltarMenu(Scanner in) throws InterruptedException, IOException {
        System.out.println("\nPressione ENTER para voltar ao menu...");
        in.nextLine();

        // Limpa toda a tela, deixando novamente apenas o menu
        if (System.getProperty("os.name").contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
            System.out.print("\033[H\033[2J");

        System.out.flush();
    }

    // Gerador de Relatorio de Vendas
    private static void RelatorioVendas(List<Venda> vendasRealizadas, LocalDate dataInicial, LocalDate dataFinal)
            throws IOException, InterruptedException {

        System.out.printf("Vendas no Periodo: %s - %s\n\n", dataInicial.format(formatter).toString(),
                dataFinal.format(formatter).toString());

        List<Venda> novoArrayRelatorioVendas = new ArrayList<>();
        novoArrayRelatorioVendas.addAll(vendasRealizadas);
        novoArrayRelatorioVendas.removeIf(v -> (v.getData().isBefore(dataInicial) || v.getData().isAfter(dataFinal)));

        // Não há vendas no periodo solicitado
        if (novoArrayRelatorioVendas.size() == 0) {
            System.out.println("\nNao foi possivel encontrar nenhuma venda no periodo: ");
            System.out.printf("Data Inicial: %s \nData Final: %s", dataInicial.format(formatter).toString(),
                    dataFinal.format(formatter).toString());
            return;
        }

        // Exibe o rodapé: Valor total de Vendas no período especificado
        double valorTotal = 0.0;
        double valorMedio = 0.0;
        double QtdVendidaTotal = 0;

        // Ordernar por data
        novoArrayRelatorioVendas.sort(new Comparator_VendaPorData());
        System.out.println("---------------------------------------------");
        System.out.println("         REGISTRO DE VENDAS REALIZADAS       ");
        System.out.println("---------------------------------------------");

        for (Venda venda : novoArrayRelatorioVendas) {

            System.out.println();

            System.out.println("\n------------------------------------------------------");

            System.out.format(venda.getData().format(formatter).toString());

            System.out.printf("Produto: " + venda.getProdutoVendido().getNome());
            System.out.printf("Quantidade vendida: " + venda.getQtdProdutoVendido()); 
            System.out.printf("Valor unitário: R$" + venda.getProdutoVendido().getValor());
            System.out.println("------------------------------------------------------");

            // Somar valores de vendas TOTAL
            valorTotal += venda.getQtdProdutoVendido() * venda.getProdutoVendido().getValor();

            // Somar quantidade de produtos vendido TOTAL
            QtdVendidaTotal += venda.getQtdProdutoVendido();

            // Dividir TOTAL de valores VENDA pela QUANTIDADE vendida
            valorMedio = (valorTotal / QtdVendidaTotal);

        }
        System.out.printf("\n\nValor TOTAL de vendas no periodo: R$%.2f", valorTotal);
        System.out.printf("\n\nValor MÉDIO de vendas no periodo: R$%.2f", valorMedio);

    }

    /*
     * Função que procura se na lista já existe algum produto com o mesmo código. Se
     * possuir, não permite cadastrar o produto
     */
    private static boolean BuscaCodigo(List<Produto> produtos, int codigo) {

        for (Produto prod : produtos) {
            if (prod.getCodigo() == codigo) {
                return true;
            }
        }

        return false;
    }

}