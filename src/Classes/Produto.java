package Classes;

import Dados.Qtd_Zero_Negativa_Exception;

public class Produto implements Comparable<Produto>{
    private Integer codigo;
    private String nome;
    private double valor;
    private Integer qtdEstoque;
      

    public Produto(String nome, Integer codigo, double valor, Integer qtdEstoque) {
        this.nome = nome;
        this.codigo = codigo;
        this.valor = valor;
        this.qtdEstoque = qtdEstoque;
    }
    public Produto() {

    }
    public Produto(int nextInt) {
    }

    public Integer getCodigo() {
        return this.codigo;
    }
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) throws Qtd_Zero_Negativa_Exception{
        if(valor < 1) {
            throw new Qtd_Zero_Negativa_Exception();
        }
        this.valor = valor;
    }
    public int getQtdEstoque() {
        return qtdEstoque;
    }
    public void setQtdEstoque(int qtdEstoque) throws Qtd_Zero_Negativa_Exception{
        if(qtdEstoque < 0) {
            throw new Qtd_Zero_Negativa_Exception();
        }

        this.qtdEstoque = qtdEstoque;
    }


    @Override
    public String toString() {
        return String.format("%s:%s:%s:%s", getNome(), getCodigo(), getValor(), getQtdEstoque());
    }
    @Override
    public int compareTo(Produto outroProduto) {
        return getNome().compareTo(outroProduto.getNome());
    }


}
