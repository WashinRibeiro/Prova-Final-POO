package Dados;

public class Qtd_Zero_Negativa_Exception extends Exception{
    
    public Qtd_Zero_Negativa_Exception() {
        super("Digite apenas valores positivos ou maiores que 0 !!");
    }

    public Qtd_Zero_Negativa_Exception(String string) {
        super(string);
    }
}
