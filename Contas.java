/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Notebook
 */
public class Contas {
    public static void main(String[] args) {
        Object resposta;
        String c = JOptionPane.showInputDialog("Digite o nome do titular da conta");
        
       
        int n = Integer.parseInt(JOptionPane.showInputDialog("Digite o numero da conta"));

       
        float s = Float.parseFloat(JOptionPane.showInputDialog("Digite o saldo inicial"));

        contabancaria conta = new contabancaria(c,n,s);
        contapopan�a contapopan�a = new contapopan�a(c,n,s);
        contaespecial contaespecial = new contaespecial(c,n,s);
        contaespecial.setlimite(2000);
        
        while (conta.getSaldo() < 0) {


            conta.setSaldo(Float.parseFloat(JOptionPane.showInputDialog("Saldo inicial invalido, digite novamente")));

        }

        int resp = 0;
        while (resp==0) {
            String[] opcoes = {"Saque", "Deposito", "Conferir popan�a", "informa��es da conta"};
            resposta = JOptionPane.showInputDialog(null, "Qual opera��o deseja fazer?", "Escolha", JOptionPane.QUESTION_MESSAGE, null, opcoes, null);
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
            if (resposta == "Conferir popan�a") {
                
                conta.setSaldo(contapopan�a.calcularnovosaldo());
            }
            if (resposta == "informa��es da conta") {
                System.out.println("Dados do titular");
                System.out.println("Nome do titular: " + conta.getCliente());
                System.out.println("Numero da conta: " + conta.getNum_Conta());
                System.out.println("Saldo atual: " + conta.getSaldo());
                System.out.println("Dia de rendimanto: "+contapopan�a.getdiaderendimento());
                System.out.println("Limite de credito: " + contaespecial.getlimite()+"\n");
            }
            resp=JOptionPane.showConfirmDialog(null, "Deseja fazer outra opera��o?", "", 0, 1);
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
        JOptionPane.showMessageDialog(null, "Voc� ainda tem: " + saldo);
        return saldo;
    }

    float depositar(float deposito) {
        while (deposito < 0) {
            deposito = Float.parseFloat(JOptionPane.showInputDialog("Valor invalido,por favor digite novamente"));
        }
        this.saldo += deposito;
        JOptionPane.showMessageDialog(null, "Voc� ainda tem: " + saldo);
        return saldo;
    }
}

class contapopan�a extends contabancaria {
    public contapopan�a(String cliente,int num_conta,float saldo){
    super(cliente, num_conta,saldo);
}
    SimpleDateFormat data = new SimpleDateFormat("dd");
    private int diaderendimento=10;
    int getdiaderendimento(){
        return diaderendimento;
    }
    float calcularnovosaldo() {
        if (data.format(Calendar.getInstance().getTime()).equals(String.valueOf(diaderendimento))) {
            super.setSaldo(super.getSaldo()*0.05f + super.getSaldo());
            JOptionPane.showMessageDialog(null, "Dia de rendimento, voc� agora possui " + super.getSaldo());
        }
        else
            JOptionPane.showMessageDialog(null, "Hoje n�o � o dia de rendimento, seu saldo ainda � " + super.getSaldo());
        return super.getSaldo();
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
        JOptionPane.showMessageDialog(null, "Voc� ainda tem: " + super.getSaldo());
        return super.getSaldo();
    }
}