package com.app.viveragro.viveragro2.extras;

/**
 * Created by Matt on 30/03/2018.
 */

public class VerificaErros {
    private String verifica;
    String[] string = new String[25];
    private String erro_1,erro_2,erro_3,erro_4,erro_5,erro_6,erro_7,erro_8;
    private String erro_9,erro_10,erro_11,erro_12,erro_13,erro_14,erro_15;
    private String erro_16,erro_17,erro_18,erro_19,erro_20,erro_21,erro_22;
    private String erro_23,erro_24,erro_25,erro_26,erro_27,erro_28,erro_29;

    public VerificaErros(){
        erro_1 = "A transação precisa ser executada novamente com dados atuais";
        erro_2 = "O servidor indicou que essa operação falhou";
        erro_3 = "Este cliente não tem permissão para executar esta operação";
        erro_4 = "A operação teve de ser interrompida devido a uma desconexão de rede";
        erro_5 = "As credenciais de auth ativas ou pendentes foram substituídas por outra chamada para auth";
        erro_6 = "O token de autenticação fornecido expirou";
        erro_7 = "O token de autenticação fornecido era inválido";
        erro_8 = "A transação teve muitas tentativas";
        erro_9 = "A transação foi substituída por um conjunto posterior";
        erro_10 = "O serviço está indisponível";
        erro_11 = "O código de usuário chamado do runlop do Firebase lançou uma exceção: ";
        erro_12 = "O tipo de autenticação especificado não está habilitado para este Firebase.";
        erro_13 = "O tipo de autenticação especificado não está configurado corretamente para esta Firebase.";
        erro_14 = "Provedor inválido especificado, verifique o código do aplicativo.";
        erro_15 = "O endereço de e-mail especificado está incorreto.";
        erro_16 = "A senha especificada está incorreta.";
        erro_17 =  "O usuário especificado não existe.";
        erro_18 = "O endereço de e - mail especificado já está em uso.";
        erro_19 = "Solicitação de autenticação negada pelo usuário.";
        erro_20 = "Credenciais de autenticação inválidas fornecidas.";
        erro_21 = "Argumentos de autenticação inválidos fornecidos.";
        erro_22 = "Ocorreu um erro de fornecedor de terceiros. Consulte dados para obter detalhes.";
        erro_23 = "Limites excedidos.";
        erro_24 = "A operação não pôde ser executada devido a um erro de rede";
        erro_25 = "A escrita foi cancelada pelo usuário.";


    }


    public String getErro(int code){
        if(code == -1)
            return  erro_1;
        if(code == -2)
            return erro_2;
        if(code == -3)
            return erro_3;
        if(code == -4)
            return erro_4;
        if(code == -5)
            return erro_5;
        if(code == -6)
            return erro_6;
        if(code == -7)
            return erro_7;
        if(code == -8)
            return erro_8;
        if(code == -9)
            return erro_9;
        if(code == -10)
            return erro_10;
        if(code == -11)
            return erro_11;
        if(code == -12)
            return erro_12;

        if(code == -13)
            return  erro_13;
        if(code == -14)
            return erro_14;
        if(code == -15)
            return erro_15;
        if(code == -16)
            return erro_16;
        if(code == -17)
            return erro_17;
        if(code == -18)
            return erro_18;
        if(code == -19)
            return erro_19;
        if(code == -20)
            return erro_20;
        if(code == -21)
            return erro_21;
        if(code == -22)
            return erro_22;
        if(code == -23)
            return erro_23;
        if(code == -24)
            return erro_24;
        if(code == -25)
            return erro_25;


        return null;


    }
}
