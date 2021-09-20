package Dados;
import java.util.Comparator;
import Classes.Venda;

public class Comparator_VendaPorData implements Comparator<Venda>{

    @Override
    public int compare(Venda v1, Venda v2) {
        int difHora = v1.getData().getYear() - v2.getData().getYear();

        if(difHora != 0) {
            return difHora;
        }

        int difMinuto = v1.getData().getMonthValue() - v2.getData().getMonthValue();

        if(difMinuto != 0) {
            return difMinuto;
        }

        int difSegundos = v1.getData().getDayOfMonth() - v2.getData().getDayOfMonth();

        return difSegundos;
    }
    
}