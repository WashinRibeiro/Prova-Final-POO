package Dados;

import java.util.Comparator;
import Classes.Produto;;

public class Comparator_ProdutosPorValor implements Comparator<Produto>{

    @Override
    public int compare(Produto p1, Produto p2) {
        // TODO Auto-generated method stub
        return p1.getValor().compareTo(p2.getValor());
    }
    
}