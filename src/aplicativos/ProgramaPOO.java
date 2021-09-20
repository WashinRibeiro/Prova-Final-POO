package aplicativos;

//Import java.Util
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Classes.*;
import Dados.*;

// Import Array
import java.util.ArrayList;
import java.util.List;

//Import para Datas
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ProgramaPOO {
    
    //Formatador de Data padrão dd/mm/yyyy ( Dia/ Mês/ Ano - exemplo 19/09/2021 )
    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    final static String padraoData = "\\d{2}/\\d{2}/\\d{4}";

    public static void main(String[] args) throws InterruptedException, IOException {
        int opcao, qtdCadastrados = 0;
        int codigoProduto = 0;
        //Array e List
        ArrayList<Produto> produtos = new ArrayList<>();  //Lista de produtos cadastrados
        List<Venda> vendasRealizadas = new ArrayList<>(); //Lista de vendas cadastrados
        Scanner in = new Scanner(System.in);

        

            //Lista do MENU
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



            if (opcao == 1) {
                //Cadastre seu produto aqui
                System.out.println("-----------------------------------");
                System.out.println("        CADASTRO DE PRODUTO      ");
                System.out.println("-----------------------------------\n");

                //Cadastrar nome do produto
                System.out.print("Nome do Produto: ");
                String nome = in.nextLine();
                
                //Cadastrar código do produto
                System.out.print("Código do Produto: ");
                Integer codigo = in.nextInt();
                for (Produto i : produtos) {
                    if (codigo.equals(getCodigo(i))) {
                
                   }
                }
                 
                //Cadastro Valor do Produto
                System.out.print("Valor do Produto R$: ");
                double valor = in.nextDouble();

                //Cadastro Quantidade em Estoque do Produto
                System.out.print("Quantidade em Estoque do Produto: ");
                Integer qtdEstoque = in.nextInt();
                in.nextLine();
                if (qtdEstoque < 0) {
                    System.out.println("\nA Quantidade em estoque não poderá ser NEGATIVA");
                    voltarMenu(in);
                 in.nextLine();
                } 
                
                //Colocando todos os "valores" passados para a lista a partir de um método construtor
                produtos.add(new Produto(nome, codigo, valor, qtdEstoque));

                //Cadastro finalizado.
                System.out.println("\nProduto cadastrado com sucesso !!!");
                voltarMenu(in);



            } else if (opcao == 2) {

                if (produtos.isEmpty()) {
                    System.out.println("\nNão há produtos cadastrados para exibir.");
                    voltarMenu(in);
                    continue;
                }
                System.out.print("\nDigite o Código do Produto: ");

                 Integer CodBusca = in.nextInt();
                 in.nextLine();

                for (Produto p : produtos){
                  if(CodBusca.equals(p.getCodigo())){
                    System.out.println("\n------------------------------------------------------");
                    System.out.printf("\nCódigo do produto: %d \nDescrição: %s \nValor unitário: R$ \nQuantidade em estoque: %d", p.getCodigo(), p.getNome(), p.getValor(), p.getQtdEstoque());
                    System.out.println("\n------------------------------------------------------");
                    }
                }    
         
                 voltarMenu(in);
                  
             
              
            } else if (opcao == 3) {

               // Se não tem ninguém cadastrado no vetor, caio fora
              if (produtos.isEmpty()) {
                 System.out.println("\nNão há produtos cadastrados para exibir.");
                 voltarMenu(in);
                 continue;
                }


               // Exiba os produtos aqui
               System.out.println("###############################");
               System.out.println("LISTAGEM DE PRODUTOS CADASTRADOS");
               System.out.println("###############################");
               System.out.println("Ordem de A - Z");


               Double soma = 0.0;
               Double maior = 0.0;
               int somaQTDEstoque = 0;
               
               //Ordenação sort por código
               produtos.sort(null);

               for (Produto produto : produtos){
                
                 System.out.println("\n------------------------------------------------------");
                 System.out.println("Código do produto:  " + produto.getCodigo() +"\nDescrição:  " +  produto.getNome());
                 System.out.println("Valor unitário: R$" + produto.getValor() + " " + "\nQuantidade em estoque: " + produto.getQtdEstoque());
                 System.out.println("\n------------------------------------------------------");

                 //Calculo para MEDIA
                 soma = soma + (produto.getValor() * produto.getQtdEstoque());
                 somaQTDEstoque = somaQTDEstoque + produto.getQtdEstoque();

                 if(produto.getValor() > maior){
                   maior = produto.getValor();

                  }
               } 
              

               //For para MENOR
               //Menor recebe valor de maior, para procurar um valor MAIOR do que MAIOR que foi definido.
               Double menor = maior;
              
              for (Produto produto : produtos) {
                //Double menor = maior;
                if(produto.getValor() < menor){
                  menor = produto.getValor();

                }
              }


              //Mostra a quantidade cadastrada
              System.out.println("\nTotal de " + produtos.size() + " produtos cadastrados ");
              
              //Mostra a quantidade de produtos em estoque
              System.out.println("Quantidade em estoque é de: " + somaQTDEstoque + " produtos");

              System.out.println("\nValor médio em estoque é: " + soma / somaQTDEstoque);

              System.out.println("\nO produto de MENOR valor cadastrado é de: R$" + maior);

              System.out.println("\nO produto de MAIOR valor cadastrado é de: R$" + menor);
            

             voltarMenu(in);



            }else if (opcao == 4) {

                if (vendasRealizadas.isEmpty()) {
                    System.out.println("\nNão há vendas realizadas para exibir.");
                    voltarMenu(in);
                    continue;
                }


                //Resgitro de venda realizadas
                System.out.println("###########################################");
                System.out.println("       REGISTRO DE VENDAS REALIZADAS       ");
                System.out.println("###########################################");

                System.out.println("Determine o período que esta contido as vendas");
                System.out.print("\nData incial [dd/mm/yyyy] (Digite ENTER para inserir a Data de Hoje): ");

                LocalDate dataInicial = LocalDate.parse("2000-01-01");
                boolean verificador = false;
                do {
                    try{

                        String data = in.nextLine(); // Para recolher o ENTER caso deseja a data atual
                        if(data.equals("")) {
                            String formatoPadrao = LocalDate.now().format(formatter);
                            dataInicial = LocalDate.parse(formatoPadrao, formatter);
                            verificador = true;
                            continue;
                        }
                        dataInicial = LocalDate.parse(data, formatter);

                        verificador = true;

                    } catch(DateTimeParseException ex)  {
                        System.out.println("\nDigite a data no padrão informado ou Pressione ENTER para colocar a data Hoje !!");
                        System.out.print("\nData Inicial [dd/mm/yyyy]: ");
                    }

                } while(!verificador);

                System.out.print("\nData Final [dd/mm/yyyy] ((Digite ENTER para inserir a Data de Hoje): ");

                verificador = false;
                LocalDate dataFinal = LocalDate.parse("2000-01-01"); // Atribuição inicial para evitar erros de compilação
                do {
                    try{
                        String data = in.nextLine(); // Para recolher o ENTER caso deseja a data de hoje
                        if(data.equals("")) {
                            String formatoPadrao = LocalDate.now().format(formatter);
                            dataFinal = LocalDate.parse(formatoPadrao, formatter);
                            verificador = true;
                            continue;
                        }

                        // Verificação para impedir que a data final se antes da data inicial
                        if(dataInicial.isAfter(dataFinal)) {
                            System.out.println("A data Final nao pode ser antes da INICIAL");
                            System.out.print("\nData Final [dd/mm/yyyy]: ");
                            continue;
                        }

                        dataFinal = LocalDate.parse(data, formatter);

                        verificador = true;

                    } catch(DateTimeParseException ex)  {
                        System.out.println("\nDigite a data no padrão informado ou Pressione ENTER para colocar a data de Hoje");
                        System.out.print("\nData Final [dd/mm/yyyy]: ");
                    }

                } while(!verificador);

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


               //Resgitro de venda dos produtos
               System.out.println("###############################");
               System.out.println("       REGISTRO DE VENDA       ");
               System.out.println("###############################");
              
               System.out.println("Voltar ao MENU, digite um caractere alfabético\n");

               System.out.println("Digite o código do Produto: ");

               
               String verificacaoEntrada = ""; 
               boolean verificador = false;
               Venda novaVenda = new Venda();

              
               do{
                   try{
                       verificacaoEntrada = in.nextLine();
                       if(!verificacaoEntrada.matches("[0-9]*") || verificacaoEntrada.equals("")) {
                           break;
                       }

                       int codigo = Integer.parseInt(verificacaoEntrada);

                       
                       novaVenda.setProdutoVendido(produtos.stream()
                       .filter(p -> p.getCodigo() == codigo)
                       .findFirst()
                       .get()); 
                       
                       verificador = true;
                   } catch(NoSuchElementException ex) {
                       System.out.println("\nNao foi encontrado nenhum produto com o Codigo fornecido");
                       System.out.println("Digite Novamente um código válido");
                       System.out.print("\nCodigo: ");
                       
                   } 

               }while(!verificador);

               // Volta para o Menu caso digite caracteres alfabéticos
               if(!verificador) { 
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
                       
                   } catch(InputMismatchException ex) {
                       System.out.println("Digite Somente Números !!");
                       in.nextLine();

                   } catch(Qtd_Zero_Negativa_Exception ex) {
                       System.out.println(ex.getMessage());
                       System.out.print("\nQuantidade: ");
                       in.nextLine();
                   }

               } while(!verificador);

              
               System.out.printf("Digite a data de Venda do Produto (ENTER para Data Atual) no Formato: [dd/mm/yyyy]");
               System.out.print("\nData: ");
               

               verificador = false;
               do {

                   try{
                       String dataVenda = in.nextLine();

                       if(dataVenda.equals("")) { // Caso seja ENTER a entrada, atribui a data de Hoje
                           dataVenda = LocalDate.now().format(formatter).toString();

                       } else if(!dataVenda.matches(padraoData)) {
                           System.out.println("\nO Formato de Data digitado não é válido!\n");
                           System.out.printf("\nDigite a data de Venda do Produto (ENTER para Data Atual) no Formato: [dd/mm/yyyy]");
                           System.out.print("\nData: ");
                           continue;
                       }  

                       novaVenda.setData(LocalDate.parse(dataVenda, formatter));
                       verificador = true;

                   } catch(DataInvalida_Exception ex) {
                       System.out.println(ex.getMessage());
                       System.out.print("\nData: ");
                   }

               } while(!verificador);


               try {
                   novaVenda.finalizarVenda();

                   vendasRealizadas.add(novaVenda);
                   System.out.println("Produto vendido!");
               } catch(Qtd_Zero_Negativa_Exception ex) {
                   System.out.println(ex.getMessage());
                   System.out.println("Não foi possível realizar a venda!");
               }

               System.out.println("\nPressioner ENTER para continuar...");
               in.nextLine();
               voltarMenu(in);

            }
            else if (opcao != 0) {
                System.out.println("\n**Opção inválida!**");
            }
        } while (opcao != 0);

        System.out.println("Fim do programa!");

        in.close();
    }


    //Voltar para o MENU
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

    
    // Comando para limpar tela
    private static void cls() throws IOException, InterruptedException {
        
        if (System.getProperty("os.name").contains("Windows"))
         new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
         System.out.print("\033[H\033[2J");
        
        System.out.flush();  
    }

    
    //Gerador de Relatorio de Vendas
    private static void RelatorioVendas(List<Venda> vendasRealizadas, LocalDate dataInicial, LocalDate dataFinal) throws IOException, InterruptedException{
       
        cls();
        System.out.printf("Vendas no Periodo: %s - %s\n\n", 
            dataInicial.format(formatter).toString(),
            dataFinal.format(formatter).toString());

         
        List<Venda> novoArrayRelatorioVendas = new ArrayList<>();
        novoArrayRelatorioVendas.addAll(vendasRealizadas);
        novoArrayRelatorioVendas.removeIf(v -> (v.getData().isAfter(dataFinal) || v.getData().isBefore(dataInicial)));

        // Não há vendas no periodo solicitado
        if(novoArrayRelatorioVendas.size() == 0){
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
        System.out.println("###########################################");
        System.out.println("       REGISTRO DE VENDAS REALIZADAS       ");
        System.out.println("###########################################");

        for (Venda venda : novoArrayRelatorioVendas) {

                
                System.out.println();
            
                //System.out.printf para Relatorio de Vendas
                System.out.println("\n------------------------------------------------------");

                System.out.format(venda.getData().format(formatter).toString()); // Pega o valor unitário do produto

                System.out.printf("\n Produto: " + venda.getProdutoVendido().getNome()); //Nome do Produto
                System.out.printf("\n Quantidade vendida: " + venda.getQtdProdutoVendido()); //Quantidade vendida
                System.out.printf("\n Valor unitário: R$" + venda.getProdutoVendido().getValor()); //Valor Unitário
                System.out.println("\n------------------------------------------------------");

                //Somar valores de vendas TOTAL
                valorTotal += venda.getQtdProdutoVendido() * venda.getProdutoVendido().getValor();
                
                //Somar quantidade de produtos vendido TOTAL
                QtdVendidaTotal += venda.getQtdProdutoVendido();

                //Dividir TOTAL de valores VENDA pela QUANTIDADE vendida
                valorMedio = (valorTotal/QtdVendidaTotal);
                
        }
        System.out.printf("\n\nValor TOTAL de vendas no periodo: R$%.2f", valorTotal);
        System.out.printf("\n\nValor MÉDIO de vendas no periodo: R$%.2f", valorMedio);

    }







}