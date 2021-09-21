package Classes;

import java.time.LocalDate;
import Classes.Produto;
import Dados.Qtd_Zero_Negativa_Exception; 
import Dados.DataInvalida_Exception; 

public class Venda {
  private LocalDate data;
  private Produto produtoVendido;
  private Integer qtdProdutoVendido;

  public LocalDate getData() {
      return data;
  }
  public void setData(LocalDate data) throws DataInvalida_Exception {
      if(data.isAfter(LocalDate.now())) {
          throw new DataInvalida_Exception();
      }
      this.data = data;
  }

  public Produto getProdutoVendido() {
      return produtoVendido;
  }
  public void setProdutoVendido(Produto produtoVendido) {
      this.produtoVendido = produtoVendido;
  }
  public Integer getQtdProdutoVendido() {
      return qtdProdutoVendido;
  }
  public void setQtdProdutoVendido(Integer qtdProdutoVendido) throws Qtd_Zero_Negativa_Exception{

      if(qtdProdutoVendido <= 0) {
          throw new Qtd_Zero_Negativa_Exception();
      }
      if(produtoVendido.getQtdEstoque() < qtdProdutoVendido) {
          throw new Qtd_Zero_Negativa_Exception("O produto selecionado nao possui esta quantia no estoque: " + qtdProdutoVendido +
          "\nQuantidade Atual: " + produtoVendido.getQtdEstoque());
      }

      this.qtdProdutoVendido = qtdProdutoVendido;
  }

  public Venda() {

  }

  public Venda(LocalDate data, Produto produtoVendido, Integer qtdProdutoVendido) {
      this.data = data;
      this.produtoVendido = produtoVendido;
      this.qtdProdutoVendido = qtdProdutoVendido;
  }

  public void finalizarVenda() throws Qtd_Zero_Negativa_Exception {
      produtoVendido.setQtdEstoque(produtoVendido.getQtdEstoque() - qtdProdutoVendido);
  } 
  
}