/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Notebook
 */
public class JavaApplication6 {
    public static void main(String[] args) {
        Object resposta;
        String c = JOptionPane.showInputDialog("Digite o nome do titular da conta");
        int n = 0;
        float s = 0;


        try{
        n = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero da conta"));
    	} catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
        }


        try{
        s = Float.parseFloat(JOptionPane.showInputDialog("Digite o saldo inicial"));
    	} catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, e);
        }

        
        contabancaria conta = new contabancaria(c,n,s);
        contapopança contapopança = new contapopança(c,n,s);
        contaespecial contaespecial = new contaespecial(c,n,s);
        contapopança.setsaldopopanca(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor que queres tranferir para a poupança ")));
        contaespecial.setlimite(2000);
        while (conta.getSaldo() < 0) {
            conta.setSaldo(Float.parseFloat(JOptionPane.showInputDialog("Saldo inicial invalido, digite novamente")));
        }
        while (conta.getSaldo() < contapopança.getsaldopopanca()|| contapopança.getsaldopopanca()<0) {
            contapopança.setsaldopopanca(Float.parseFloat(JOptionPane.showInputDialog("Saldo da poupança maior que o saldo bancario ou valor invalido, digite novamente")));
        }
        int resp = 0;
        while (resp==0) {
            String[] opcoes = {"Saque", "Deposito", "Conferir popança", "informações da conta","Tranferencia Bancaria","Tranferir dinheiro para popança","Retirar dinheiro da popança"};
            resposta = JOptionPane.showInputDialog(null, "Qual operação deseja fazer?", "Escolha", JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
            if (resposta == "Saque") {
                if (JOptionPane.showConfirmDialog(null, "Deseja utilizar o limite da conta?", "", 0, 1) == 0) {
                    conta.setSaldo(contaespecial.sacar(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do saque"))));
                } else {
                    conta.setSaldo(conta.sacar(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do saque"))));
                }
            }
            if (resposta == "Deposito") {
                conta.setSaldo(conta.depositar(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do deposito"))));
            }
            if (resposta == "Conferir popança") {
                contapopança.setsaldopopanca(contapopança.calcularnovosaldo());
            }
            if (resposta == "Tranferencia Bancaria") {
                if (JOptionPane.showConfirmDialog(null, "Deseja utilizar o limite da conta?", "", 0, 1) == 0) {
                    conta.setSaldo(contaespecial.transferenciabancaria(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da tranferencia entre contas"))));
                } else {
                    conta.setSaldo(conta.transferenciabancaria(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor da tranferencia entre contas"))));
                }
            }
            if (resposta == "Tranferir dinheiro para popança") {
                contapopança.setsaldopopanca(contapopança.aumentarpopanca(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do acrescimo"))));
            }
            if (resposta == "Retirar dinheiro da popança") {
                contapopança.setsaldopopanca(contapopança.diminuirpopanca(Float.parseFloat(JOptionPane.showInputDialog("Digite o valor do decrescimo"))));
            }
            if (resposta == "informações da conta") {
                System.out.println("Dados do titular");
                System.out.println("Nome do titular: " + conta.getCliente());
                System.out.println("Numero da conta: " + conta.getNum_Conta());
                System.out.println("Saldo atual: " + conta.getSaldo());
                System.out.println("Dia de rendimanto: "+contapopança.getdiaderendimento());
                System.out.println("Limite de credito: " + contaespecial.getlimite()+"\n");
            }
            resp=JOptionPane.showConfirmDialog(null, "Deseja fazer outra operação?", "", 0, 1);
        }
    }
}

class contabancaria {
    private String cliente;
    private int num_conta;
    private float saldo;
    public contabancaria(String cliente,int num_conta,float saldo){
    this.cliente=cliente;
    this.num_conta=num_conta;
    this.saldo=saldo;
}
    void setCliente(String Cliente){
    this.cliente = Cliente;
  }

  void setNum_Conta(int Num_Conta){
    this.num_conta = Num_Conta;
  }

  void setSaldo(float Saldo){
    this.saldo = Saldo;
  }

  String getCliente(){
    return this.cliente;
  }

  int getNum_Conta(){
    return this.num_conta;
  }

  float getSaldo(){
    return this.saldo;
  }
    float sacar(float saque) {
        while (saque < 0 || saque > saldo) {
            saque = Float.parseFloat(JOptionPane.showInputDialog("Valor invalido ou superior ao seu saldo,por favor digite novamente"));
        }
        this.saldo -= saque;
        JOptionPane.showMessageDialog(null, "Você ainda tem: " + saldo);
        return saldo;
    }

    float depositar(float deposito) {
        while (deposito < 0) {
            deposito = Float.parseFloat(JOptionPane.showInputDialog("Valor invalido,por favor digite novamente"));
        }
        this.saldo += deposito;
        JOptionPane.showMessageDialog(null, "Você ainda tem: " + saldo);
        return saldo;
    }
    float transferenciabancaria(float deposito){
        while (deposito < 0 || deposito > saldo) {
            deposito = Float.parseFloat(JOptionPane.showInputDialog("Valor invalido ou superior ao seu saldo,por favor digite novamente"));
        }
        saldo-=deposito;
        JOptionPane.showMessageDialog(null, "Você ainda tem: " + saldo);
        return saldo;
    }
}

class contapopança extends contabancaria {
    public contapopança(String cliente,int num_conta,float saldo){
    super(cliente, num_conta,saldo);
}
    SimpleDateFormat data = new SimpleDateFormat("dd");
    private float saldopopanca;
    private final int diaderendimento=10;
    int getdiaderendimento(){
        return diaderendimento;
    }
    float getsaldopopanca(){
        return saldopopanca;
    }
    void setsaldopopanca(float valor){
        this.saldopopanca=valor;
    }
    float calcularnovosaldo() {
        if (data.format(Calendar.getInstance().getTime()).equals(String.valueOf(diaderendimento))) {
            saldopopanca+=saldopopanca*0.05;
            JOptionPane.showMessageDialog(null, "Dia de rendimento, você agora possui " + saldopopanca);
        }
        else
            JOptionPane.showMessageDialog(null, "Hoje não é o dia de rendimento, seu saldo ainda é " + super.getSaldo());
        return saldopopanca;
    }
    float aumentarpopanca(float valor){
        while (valor < 0 || valor > super.getSaldo()) {
            valor =Float.parseFloat(JOptionPane.showInputDialog("Valor invalido ou superior ao seu saldo,por favor digite novamente"));
        }
        super.setSaldo(super.getSaldo()-valor);
        saldopopanca+=valor;
        JOptionPane.showMessageDialog(null, "Você ainda tem na poupança: " + saldopopanca);
        return saldopopanca;
    }
    float diminuirpopanca(float valor){
        while (valor < 0 || valor > saldopopanca) {
            JOptionPane.showInputDialog("Valor invalido ou superior ao seu saldo na poupança,por favor digite novamente");
        }
        saldopopanca-=valor;
        super.setSaldo(super.getSaldo()+valor);
        JOptionPane.showMessageDialog(null, "Você ainda tem na poupança: " + saldopopanca);
        return saldopopanca;
    }
}

class contaespecial extends contabancaria {
    public contaespecial(String cliente,int num_conta,float saldo){
    super(cliente, num_conta,saldo);
}
    private float limite;
    
    void setlimite(float lim){
        this.limite = lim;
  }

    float getlimite(){
        return limite;
  }
  
    @Override
    float sacar(float saque) {
        while (saque < 0 || saque > super.getSaldo() + limite) {
            saque = Float.parseFloat(JOptionPane.showInputDialog("Valor invalido ou superior ao seu saldo e limite,por favor digite novamente"));
        }
        super.setSaldo(super.getSaldo()-saque);
        JOptionPane.showMessageDialog(null, "Você ainda tem: " + super.getSaldo());
        return super.getSaldo();
    }
    @Override
    float transferenciabancaria(float deposito){
        while (deposito < 0 || deposito > super.getSaldo()+limite) {
            deposito=Float.parseFloat(JOptionPane.showInputDialog("Valor invalido ou superior ao seu saldo,por favor digite novamente"));
        }
        super.setSaldo(super.getSaldo()-deposito);
        JOptionPane.showMessageDialog(null, "Você ainda tem: " + super.getSaldo());
        return super.getSaldo();
    }
}
