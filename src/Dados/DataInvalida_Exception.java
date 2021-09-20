package Dados;

import java.time.LocalDate;

public class DataInvalida_Exception extends Exception {

    public DataInvalida_Exception() {
                
        super(String.format("Data inválida! A data não pode ser posterior a data Atual: %s  !", LocalDate.now().toString()));
    }

    public DataInvalida_Exception(String exceptionEspecifica) {
        super(exceptionEspecifica);
    }
}