package Dados;

import java.util.Comparator;

import Classes.Produto;

public class Comparator_Codigo implements Comparator<Produto>{

    @Override
    public int compare(Produto p1, Produto p2) {
        // TODO Auto-generated method stub
        return p1.getCodigo().compareTo(p2.getCodigo());
    }
    


}
