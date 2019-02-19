package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {


    private String lexema;
    private String lexemaEntrada;
    private String caracteresLidosAux;
    private char c;
    private int lexemaPosicao;
    private int estado;
    private boolean i = true;

    private Button button;
    private TextView TextStringAnali;
    private TextView TextString;
    private TextView Resultado;
    private EditText entradaSring;
    private Stack<Lexema> lexemaStack = new Stack<>();
    private Stack<String> retorno = new Stack<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entradaSring = (EditText) findViewById(R.id.entradaString);
        TextStringAnali = (TextView) findViewById(R.id.TextStringAnali);
        TextString = (TextView) findViewById(R.id.TextString);
        Resultado = (TextView) findViewById(R.id.resultado);
        button = (Button) findViewById(R.id.button);

        Stack st = new Stack();
/*
        empilha("leia", "read",lexemaStack);
        empilha("x", "id",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("soma", "id",lexemaStack);
        empilha("=", "attr",lexemaStack);
        empilha("0", "num",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("enquanto", "while",lexemaStack);
        empilha("(", "lparen",lexemaStack);
        empilha("x", "id",lexemaStack);
        empilha(">", "gt",lexemaStack);
        empilha("1", "num",lexemaStack);
        empilha(")", "rparen",lexemaStack); //12
        empilha("soma", "id",lexemaStack);  //13
        empilha("=", "attr",lexemaStack);
        empilha("soma", "id",lexemaStack);
        empilha("*", "times",lexemaStack);
        empilha("x", "id",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("x", "id",lexemaStack);
        empilha("=", "attr",lexemaStack);
        empilha("x", "id",lexemaStack);
        empilha("-", "minus",lexemaStack);
        empilha("1", "num",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("fim", "end",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("escreva", "write",lexemaStack);   /27
        empilha("soma", "id",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("soma", "id",lexemaStack);
        empilha("=", "attr",lexemaStack);
        empilha("soma", "id",lexemaStack);
        empilha("-", "minus",lexemaStack);
        empilha("5", "num",lexemaStack);
        empilha(";", "semi",lexemaStack); //35
        empilha("se", "if",lexemaStack);
        empilha("(", "lparen",lexemaStack);
        empilha("soma", "id",lexemaStack);
        empilha(">", "gt",lexemaStack);
        empilha("7", "num",lexemaStack);    //40
        empilha(")", "rparen",lexemaStack);
        empilha("escreva", "write",lexemaStack);
        empilha("soma", "id",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("senao", "else",lexemaStack);
        empilha("escreva", "write",lexemaStack);
        empilha("x", "id",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("fim", "end",lexemaStack);
        empilha(";", "semi",lexemaStack);
        empilha("$", "$",lexemaStack);

*/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caracteresLidosAux = "";
                lexemaPosicao = -1;
                lexemaEntrada = entradaSring.getText().toString()+"$";
                String teste = "aaa";
                analiseLexica(lexemaStack);
                if (analiseSintatica(lexemaStack,retorno)) {
                    teste = "aceita";
                }else{
                    teste = "erro";
                }

                TextStringAnali.setText(retorno.get(retorno.size()-1));
                TextString.setText(leLexema(lexemaStack));
                Resultado.setText(teste);
            }
        });

        /*

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                caracteresLidosAux = "";
                stringAux = " ";
                inicio_lexema = -1;
                caracteresLidos = "º";
                c = '*';
                lexema = entradaSring.getText().toString();

                TextStringAnali.setText(funcao());
                TextString.setText(caracteresLidos);
            }
        });
        */


    }

    static void empilha(String lexema, String token, Stack<Lexema> lexemaStack){

        Lexema lexema1 = new Lexema();
        lexema1.setLexema(lexema);
        lexema1.setToken(token);
        lexemaStack.push(lexema1);

    }

    static Stack<Lexema> invertePilha(Stack<Lexema> lexemaStack){

        Stack<Lexema> reversedStack = new Stack<Lexema>();
        while (!lexemaStack.empty())
        {
            reversedStack.push(lexemaStack.pop());
        }
        return reversedStack;


    }

    static String leLexema(Stack<Lexema> lexemas){
        String texto = "";
        Stack<Lexema> lexemasAux;
        lexemasAux = lexemas;
        int tamalho =  lexemasAux.size() ;
        for(int i=0; i<tamalho;i++){
            texto = lexemasAux.pop().getLexema() + " " + texto ;

        }



        return texto;
    }

    static boolean analiseSintatica(Stack<Lexema> lexemasStack,Stack<String> retorno){

        Stack<String> pilha = new Stack<>();
        pilha.push("0");
        int apontador = 0;
        String estadoNoTopoDaPilha;
        String token;
        String acao;
        String lexemar;
        int aux;
        while(true){
            estadoNoTopoDaPilha = pilha.peek();
            token = lexemasStack.get(apontador).getToken();
            lexemar = lexemasStack.get(apontador).getLexema();

            if (acao(estadoNoTopoDaPilha,token).contains("empilha")){
                acao = acao(estadoNoTopoDaPilha,token).replace("empilha ","");
                pilha.push(token);
                retorno.push(lexemar);
                pilha.push(acao);
                apontador = apontador +1;
            }else if (acao(estadoNoTopoDaPilha,token).contains("reduzir")){
                acao = acao(estadoNoTopoDaPilha,token).replace("reduzir r","");
                producao(retorno,acao);
                int t = numElementos(acao)*2;
                for(int k = 0; k < t; k++){
                    pilha.pop();
                }
                String estado = pilha.peek();
                String regra = regra(acao);
                pilha.push(regra);
                pilha.push(transicao(estado,regra));

            }else if (acao(estadoNoTopoDaPilha,token)=="aceita"){
            return true;
            }else{
                return false;
            }
        }
    }

    static String acao(String estadoNoTopoDaPilha, String lexema){

        switch (estadoNoTopoDaPilha){
            case "0":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "1":
                if(lexema=="$"){
                    return "aceita";
                }
                break;
            case "2":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }else if(lexema=="$"){
                    return "reduzir r1";
                }
                break;
            case "3":
                if(lexema=="if"){
                    return "reduzir r3";
                }else if(lexema=="else"){
                    return "reduzir r3";
                }else if(lexema=="while"){
                    return "reduzir r3";
                }else if(lexema=="end"){
                    return "reduzir r3";
                }else if(lexema=="read"){
                    return "reduzir r3";
                }else if(lexema=="write"){
                    return "reduzir r3";
                }else if(lexema=="id"){
                    return "reduzir r3";
                }else if(lexema=="$"){
                    return "reduzir r3";
                }
                break;
            case "4":
                if(lexema=="semi"){
                    return "empilha 15";
                }
                break;
            case "5":
                if(lexema=="semi"){
                    return "empilha 16";
                }
                break;
            case "6":
                if(lexema=="semi"){
                    return "empilha 17";
                }
                break;
            case "7":
                if(lexema=="semi"){
                    return "empilha 18";
                }
                break;
            case "8":
                if(lexema=="semi"){
                    return "empilha 19";
                }
                break;
            case "9":
                if(lexema=="lparen"){
                    return "empilha 20";
                }
                break;
            case "10":
                if(lexema=="lparen"){
                    return "empilha 21";
                }
                break;
            case "11":
                if(lexema=="attr"){
                    return "empilha 22";
                }
                break;
            case "12":
                if(lexema=="id"){
                    return "empilha 23";
                }
                break;
            case "13":
                if(lexema=="id"){
                    return "empilha 24";
                }
                break;
            case "14":
                if(lexema=="if"){
                    return "reduzir r2";
                }else if(lexema=="else"){
                    return "reduzir r2";
                }else if(lexema=="while"){
                    return "reduzir r2";
                }else if(lexema=="end"){
                    return "reduzir r2";
                }else if(lexema=="read"){
                    return "reduzir r2";
                }else if(lexema=="write"){
                    return "reduzir r2";
                }else if(lexema=="id"){
                    return "reduzir r2";
                }else if(lexema=="$"){
                    return "reduzir r2";
                }
                break;
            case "15":
                if(lexema=="if"){
                    return "reduzir r4";
                }else if(lexema=="else"){
                    return "reduzir r4";
                }else if(lexema=="while"){
                    return "reduzir r4";
                }else if(lexema=="end"){
                    return "reduzir r4";
                }else if(lexema=="read"){
                    return "reduzir r4";
                }else if(lexema=="write"){
                    return "reduzir r4";
                }else if(lexema=="id"){
                    return "reduzir r4";
                }else if(lexema=="$"){
                    return "reduzir r4";
                }
                break;
            case "16":
                if(lexema=="if"){
                    return "reduzir r5";
                }else if(lexema=="else"){
                    return "reduzir r5";
                }else if(lexema=="while"){
                    return "reduzir r5";
                }else if(lexema=="end"){
                    return "reduzir r5";
                }else if(lexema=="read"){
                    return "reduzir r5";
                }else if(lexema=="write"){
                    return "reduzir r5";
                }else if(lexema=="id"){
                    return "reduzir r5";
                }else if(lexema=="$"){
                    return "reduzir r5";
                }
                break;
            case "17":
                if(lexema=="if"){
                    return "reduzir r6";
                }else if(lexema=="else"){
                    return "reduzir r6";
                }else if(lexema=="while"){
                    return "reduzir r6";
                }else if(lexema=="end"){
                    return "reduzir r6";
                }else if(lexema=="read"){
                    return "reduzir r6";
                }else if(lexema=="write"){
                    return "reduzir r6";
                }else if(lexema=="id"){
                    return "reduzir r6";
                }else if(lexema=="$"){
                    return "reduzir r6";
                }
                break;
            case "18":
                if(lexema=="if"){
                    return "reduzir r7";
                }else if(lexema=="else"){
                    return "reduzir r7";
                }else if(lexema=="while"){
                    return "reduzir r7";
                }else if(lexema=="end"){
                    return "reduzir r7";
                }else if(lexema=="read"){
                    return "reduzir r7";
                }else if(lexema=="write"){
                    return "reduzir r7";
                }else if(lexema=="id"){
                    return "reduzir r7";
                }else if(lexema=="$"){
                    return "reduzir r7";
                }
                break;
            case "19":
                if(lexema=="if"){
                    return "reduzir r8";
                }else if(lexema=="else"){
                    return "reduzir r8";
                }else if(lexema=="while"){
                    return "reduzir r8";
                }else if(lexema=="end"){
                    return "reduzir r8";
                }else if(lexema=="read"){
                    return "reduzir r8";
                }else if(lexema=="write"){
                    return "reduzir r8";
                }else if(lexema=="id"){
                    return "reduzir r8";
                }else if(lexema=="$"){
                    return "reduzir r8";
                }
                break;
            case "20":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "21":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "22":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "23":
                if(lexema=="semi") {
                    return "reduzir r13";
                }
                break;
            case "24":
                if(lexema=="semi") {
                    return "reduzir r14";
                }
                break;
            case "25":
                if(lexema=="plus"){
                    return "reduzir r20";
                }else if(lexema=="minus"){
                    return "reduzir r20";
                }else if(lexema=="times"){
                    return "reduzir r20";
                }else if(lexema=="over"){
                    return "reduzir r20";
                }else if(lexema=="semi"){
                    return "reduzir r20";
                }else if(lexema=="rparen"){
                    return "empilha 34";
                }else if(lexema=="or"){
                    return "reduzir r20";
                }else if(lexema=="and"){
                    return "reduzir r20";
                }else if(lexema=="eq"){
                    return "reduzir r20";
                }else if(lexema=="neq"){
                    return "reduzir r20";
                }else if(lexema=="gt"){
                    return "reduzir r20";
                }else if(lexema=="lt"){
                    return "reduzir r20";
                }else if(lexema=="ge"){
                    return "reduzir r20";
                }else if(lexema=="le"){
                    return "reduzir r20";
                }
                break;
            case "26":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r29";
                }else if(lexema=="rparen"){
                    return "reduzir r29";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "27":
                if(lexema=="plus"){
                    return "reduzir r19";
                }else if(lexema=="minus"){
                    return "reduzir r19";
                }else if(lexema=="times"){
                    return "reduzir r19";
                }else if(lexema=="over"){
                    return "reduzir r19";
                }else if(lexema=="semi"){
                    return "reduzir r19";
                }else if(lexema=="rparen"){
                    return "reduzir r19";
                }else if(lexema=="or"){
                    return "reduzir r19";
                }else if(lexema=="and"){
                    return "reduzir r19";
                }else if(lexema=="eq"){
                    return "reduzir r19";
                }else if(lexema=="neq"){
                    return "reduzir r19";
                }else if(lexema=="gt"){
                    return "reduzir r19";
                }else if(lexema=="lt"){
                    return "reduzir r19";
                }else if(lexema=="ge"){
                    return "reduzir r19";
                }else if(lexema=="le"){
                    return "reduzir r19";
                }
                break;
            case "28":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "29":
                if(lexema=="plus"){
                    return "reduzir r31";
                }else if(lexema=="minus"){
                    return "reduzir r31";
                }else if(lexema=="times"){
                    return "reduzir r31";
                }else if(lexema=="over"){
                    return "reduzir r31";
                }else if(lexema=="semi"){
                    return "reduzir r31";
                }else if(lexema=="rparen"){
                    return "reduzir r31";
                }else if(lexema=="or"){
                    return "reduzir r31";
                }else if(lexema=="and"){
                    return "reduzir r31";
                }else if(lexema=="eq"){
                    return "reduzir r31";
                }else if(lexema=="neq"){
                    return "reduzir r31";
                }else if(lexema=="gt"){
                    return "reduzir r31";
                }else if(lexema=="lt"){
                    return "reduzir r31";
                }else if(lexema=="ge"){
                    return "reduzir r31";
                }else if(lexema=="le"){
                    return "reduzir r31";
                }
                break;
            case "30":
                if(lexema=="plus"){
                    return "reduzir r32";
                }else if(lexema=="minus"){
                    return "reduzir r32";
                }else if(lexema=="times"){
                    return "reduzir r32";
                }else if(lexema=="over"){
                    return "reduzir r32";
                }else if(lexema=="semi"){
                    return "reduzir r32";
                }else if(lexema=="rparen"){
                    return "reduzir r32";
                }else if(lexema=="or"){
                    return "reduzir r32";
                }else if(lexema=="and"){
                    return "reduzir r32";
                }else if(lexema=="eq"){
                    return "reduzir r32";
                }else if(lexema=="neq"){
                    return "reduzir r32";
                }else if(lexema=="gt"){
                    return "reduzir r32";
                }else if(lexema=="lt"){
                    return "reduzir r32";
                }else if(lexema=="ge"){
                    return "reduzir r32";
                }else if(lexema=="le"){
                    return "reduzir r32";
                }
                break;
            case "31":
                if(lexema=="plus"){
                    return "reduzir r20";
                }else if(lexema=="minus"){
                    return "reduzir r20";
                }else if(lexema=="times"){
                    return "reduzir r20";
                }else if(lexema=="over"){
                    return "reduzir r20";
                }else if(lexema=="semi"){
                    return "reduzir r20";
                }else if(lexema=="rparen"){
                    return "empilha 48";
                }else if(lexema=="or"){
                    return "reduzir r20";
                }else if(lexema=="and"){
                    return "reduzir r20";
                }else if(lexema=="eq"){
                    return "reduzir r20";
                }else if(lexema=="neq"){
                    return "reduzir r20";
                }else if(lexema=="gt"){
                    return "reduzir r20";
                }else if(lexema=="lt"){
                    return "reduzir r20";
                }else if(lexema=="ge"){
                    return "reduzir r20";
                }else if(lexema=="le"){
                    return "reduzir r20";
                }
                break;
            case "32":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r12";
                }else if(lexema=="rparen"){
                    return "reduzir r29";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "33":
                if(lexema=="plus"){
                    return "reduzir r20";
                }else if(lexema=="minus"){
                    return "reduzir r20";
                }else if(lexema=="times"){
                    return "reduzir r20";
                }else if(lexema=="over"){
                    return "reduzir r20";
                }else if(lexema=="semi"){
                    return "reduzir r20";
                }else if(lexema=="rparen"){
                    return "reduzir r20";
                }else if(lexema=="or"){
                    return "reduzir r20";
                }else if(lexema=="and"){
                    return "reduzir r20";
                }else if(lexema=="eq"){
                    return "reduzir r20";
                }else if(lexema=="neq"){
                    return "reduzir r20";
                }else if(lexema=="gt"){
                    return "reduzir r20";
                }else if(lexema=="lt"){
                    return "reduzir r20";
                }else if(lexema=="ge"){
                    return "reduzir r20";
                }else if(lexema=="le"){
                    return "reduzir r20";
                }
                break;
            case "34":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "35":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "36":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "37":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "38":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "39":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "40":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "41":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "42":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "43":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "44":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "45":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "46":
                if(lexema=="lparen"){
                    return "empilha 28";
                }else if(lexema=="id"){
                    return "empilha 29";
                }else if(lexema=="num"){
                    return "empilha 30";
                }
                break;
            case "47":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r29";
                }else if(lexema=="rparen"){
                    return "empilha 62";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "48":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "49":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="else"){
                    return "empilha 65";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="end"){
                    return "empilha 64";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "50":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r21";
                }else if(lexema=="rparen"){
                    return "reduzir r21";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "51":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r22";
                }else if(lexema=="rparen"){
                    return "reduzir r22";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "52":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r23";
                }else if(lexema=="rparen"){
                    return "reduzir r23";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "53":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r24";
                }else if(lexema=="rparen"){
                    return "reduzir r24";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "54":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r25";
                }else if(lexema=="rparen"){
                    return "reduzir r25";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "55":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r26";
                }else if(lexema=="rparen"){
                    return "reduzir r26";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "56":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r27";
                }else if(lexema=="rparen"){
                    return "reduzir r27";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "57":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r28";
                }else if(lexema=="rparen"){
                    return "reduzir r28";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "58":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r15";
                }else if(lexema=="rparen"){
                    return "reduzir r15";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "59":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r16";
                }else if(lexema=="rparen"){
                    return "reduzir r16";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "60":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r17";
                }else if(lexema=="rparen"){
                    return "reduzir r17";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "61":
                if(lexema=="plus"){
                    return "empilha 43";
                }else if(lexema=="minus"){
                    return "empilha 44";
                }else if(lexema=="times"){
                    return "empilha 45";
                }else if(lexema=="over"){
                    return "empilha 46";
                }else if(lexema=="semi"){
                    return "reduzir r18";
                }else if(lexema=="rparen"){
                    return "reduzir r18";
                }else if(lexema=="or"){
                    return "empilha 35";
                }else if(lexema=="and"){
                    return "empilha 36";
                }else if(lexema=="eq"){
                    return "empilha 37";
                }else if(lexema=="neq"){
                    return "empilha 38";
                }else if(lexema=="gt"){
                    return "empilha 39";
                }else if(lexema=="lt"){
                    return "empilha 40";
                }else if(lexema=="ge"){
                    return "empilha 41";
                }else if(lexema=="le"){
                    return "empilha 42";
                }
                break;
            case "62":
                if(lexema=="plus"){
                    return "reduzir r30";
                }else if(lexema=="minus"){
                    return "reduzir r30";
                }else if(lexema=="times"){
                    return "reduzir r30";
                }else if(lexema=="over"){
                    return "reduzir r30";
                }else if(lexema=="semi"){
                    return "reduzir r30";
                }else if(lexema=="rparen"){
                    return "reduzir r30";
                }else if(lexema=="or"){
                    return "reduzir r30";
                }else if(lexema=="and"){
                    return "reduzir r30";
                }else if(lexema=="eq"){
                    return "reduzir r30";
                }else if(lexema=="neq"){
                    return "reduzir r30";
                }else if(lexema=="gt"){
                    return "reduzir r30";
                }else if(lexema=="lt"){
                    return "reduzir r30";
                }else if(lexema=="ge"){
                    return "reduzir r30";
                }else if(lexema=="le"){
                    return "reduzir r30";
                }
                break;
            case "63":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="end"){
                    return "empilha 66";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "64":
                if(lexema=="semi"){
                    return "reduzir r9";
                }
                break;
            case "65":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "66":
                if(lexema=="semi"){
                    return "reduzir r11";
                }
                break;
            case "67":
                if(lexema=="if"){
                    return "empilha 9";
                }else if(lexema=="while"){
                    return "empilha 10";
                }else if(lexema=="end"){
                    return "empilha 68";
                }else if(lexema=="read"){
                    return "empilha 12";
                }else if(lexema=="write"){
                    return "empilha 13";
                }else if(lexema=="id"){
                    return "empilha 11";
                }
                break;
            case "68":
                if(lexema=="semi"){
                    return "reduzir r10";
                }
                break;

            default:
                return "erro";
        }

        return "erro";
    }

    static String transicao(String estadoNoTopoDaPilha, String regra){
        switch (estadoNoTopoDaPilha) {
            case "0":
                switch (regra) {
                    case "Program":
                        return "1";
                    case "Stmts":
                        return "2";
                    case "Stmt":
                        return "3";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "2":
                switch (regra) {
                    case "Stmt":
                        return "14";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "20":
                switch (regra) {
                    case "Expr":
                        return "26";
                    case "Bool":
                        return "25";
                    case "Factor":
                        return "27";
                }
                break;
            case "21":
                switch (regra) {
                    case "Expr":
                        return "26";
                    case "Bool":
                        return "31";
                    case "Factor":
                        return "27";
                }
                break;
            case "22":
                switch (regra) {
                    case "Expr":
                        return "32";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "28":
                switch (regra) {
                    case "Expr":
                        return "47";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "34":
                switch (regra) {
                    case "Stmts":
                        return "49";
                    case "Stmt":
                        return "3";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "35":
                switch (regra) {
                    case "Expr":
                        return "50";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "36":
                switch (regra) {
                    case "Expr":
                        return "51";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "37":
                switch (regra) {
                    case "Expr":
                        return "52";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "38":
                switch (regra) {
                    case "Expr":
                        return "53";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "39":
                switch (regra) {
                    case "Expr":
                        return "54";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "40":
                switch (regra) {
                    case "Expr":
                        return "55";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "41":
                switch (regra) {
                    case "Expr":
                        return "56";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "42":
                switch (regra) {
                    case "Expr":
                        return "57";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "43":
                switch (regra) {
                    case "Expr":
                        return "58";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "44":
                switch (regra) {
                    case "Expr":
                        return "59";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "45":
                switch (regra) {
                    case "Expr":
                        return "60";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "46":
                switch (regra) {
                    case "Expr":
                        return "61";
                    case "Bool":
                        return "33";
                    case "Factor":
                        return "27";
                }
                break;
            case "48":
                switch (regra) {
                    case "Stmts":
                        return "63";
                    case "Stmt":
                        return "3";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "49":
                switch (regra) {
                    case "Stmt":
                        return "14";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "63":
                switch (regra) {
                    case "Stmt":
                        return "14";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "65":
                switch (regra) {
                    case "Stmts":
                        return "67";
                    case "Stmt":
                        return "3";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
            case "67":
                switch (regra) {
                    case "Stmt":
                        return "14";
                    case "If_decl":
                        return "4";
                    case "While_decl":
                        return "5";
                    case "Attrib_decl":
                        return "6";
                    case "Read_decl":
                        return "7";
                    case "Write_decl":
                        return "8";
                }
                break;
        }

        return"";
    }

    static String regra(String numeroDaRegrap){
        int numeroDaRegra = Integer.parseInt(numeroDaRegrap);

        if(numeroDaRegra==0){
            return "Program'";
        }else if(numeroDaRegra==1){
            return "Program";
        }else if(numeroDaRegra==2 || numeroDaRegra==3){
            return "Stmts";
        }else if(numeroDaRegra>=4 && numeroDaRegra<=8){
            return "Stmt";
        }else if(numeroDaRegra==9 || numeroDaRegra==10){
            return "If_decl";
        }else if(numeroDaRegra==11){
            return "While_decl";
        }else if(numeroDaRegra==12){
            return "Attrib_decl";
        }else if(numeroDaRegra==13){
            return "Read_decl";
        }else if(numeroDaRegra==14){
            return "Write_decl";
        }else if(numeroDaRegra>=15 && numeroDaRegra<=20){
            return "Expr";
        }else if(numeroDaRegra>=21 && numeroDaRegra<=29){
            return "Bool";
        }else if(numeroDaRegra>=30 && numeroDaRegra<=32){
            return "Factor";
        }

        return "";
    }

    static int numElementos(String numeroDaRegra){
        switch (numeroDaRegra){
            case "0":
                return 1;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 1;
            case "4":
                return 2;
            case "5":
                return 2;
            case "6":
                return 2;
            case "7":
                return 2;
            case "8":
                return 2;
            case "9":
                return 6;
            case "10":
                return 8;
            case "11":
                return 6;
            case "12":
                return 3;
            case "13":
                return 2;
            case "14":
                return 2;
            case "15":
                return 3;
            case "16":
                return 3;
            case "17":
                return 3;
            case "18":
                return 3;
            case "19":
                return 1;
            case "20":
                return 1;
            case "21":
                return 3;
            case "22":
                return 3;
            case "23":
                return 3;
            case "24":
                return 3;
            case "25":
                return 3;
            case "26":
                return 3;
            case "27":
                return 3;
            case "28":
                return 3;
            case "29":
                return 1;
            case "30":
                return 3;
            case "31":
                return 1;
            case "32":
                return 1;
                default:return -1;
        }

    }

    static boolean producao(Stack<String> pilhaVaL, String regra){
        int t;
        String texto = "";
        switch (regra){
            case "0":
                return true;
            case "1":
                return true;
            case "2":
                texto = pilhaVaL.get(pilhaVaL.size()-2) + pilhaVaL.get(pilhaVaL.size()-1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case "3":
                return true;
            case"4":
                texto = pilhaVaL.get(pilhaVaL.size()-2) + " ";
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"5":
                texto = pilhaVaL.get(pilhaVaL.size()-2) + " ";
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"6":
                texto = pilhaVaL.get(pilhaVaL.size()-2) + " ; ";
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"7":
                texto = pilhaVaL.get(pilhaVaL.size()-2) + " ; ";
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"8":
                texto = pilhaVaL.get(pilhaVaL.size()-2) + " ; ";
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"9":
                texto = " if "+
                        pilhaVaL.get(pilhaVaL.size()-5) +
                        pilhaVaL.get(pilhaVaL.size()-4) +
                        pilhaVaL.get(pilhaVaL.size()-3) + " { " +
                        pilhaVaL.get(pilhaVaL.size()-2) + " } ";
                        t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"10":
                texto = " if "+
                        pilhaVaL.get(pilhaVaL.size()-7) +
                        pilhaVaL.get(pilhaVaL.size()-6) +
                        pilhaVaL.get(pilhaVaL.size()-5) + " { " +
                        pilhaVaL.get(pilhaVaL.size()-4) + " }else{" +
                        pilhaVaL.get(pilhaVaL.size()-2) + " } " ;
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"11":
                texto = " while "+
                        pilhaVaL.get(pilhaVaL.size()-5) +
                        pilhaVaL.get(pilhaVaL.size()-4) +
                        pilhaVaL.get(pilhaVaL.size()-3) + " { " +
                        pilhaVaL.get(pilhaVaL.size()-2) + " } " ;
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"12":
                texto = pilhaVaL.get(pilhaVaL.size()-3) +
                        pilhaVaL.get(pilhaVaL.size()-2) +
                        pilhaVaL.get(pilhaVaL.size()-1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"13":
                texto = " read "+
                        pilhaVaL.get(pilhaVaL.size()-1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"14":
                texto = " write "+
                        pilhaVaL.get(pilhaVaL.size()-1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"15":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" + "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"16":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" - "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"17":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" * "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"18":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" / "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"19":
                return true;
            case"20":
                return true;
            case"21":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" || "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"22":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" && "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"23":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" == "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"24":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" != "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"25":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" > "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"26":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" < "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"27":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" >= "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"28":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +" <= "+
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"29":
                return true;
            case"30":
                texto = pilhaVaL.get(pilhaVaL.size() - 3) +
                        pilhaVaL.get(pilhaVaL.size() - 2) +
                        pilhaVaL.get(pilhaVaL.size() - 1);
                t = numElementos(regra);
                for(int k = 0; k < t; k++){
                    pilhaVaL.pop();
                }
                pilhaVaL.push(texto);
                return true;
            case"31":
                return true;
            case"32":
                return true;
                default:return true;
        }

    }

    void analiseLexica(Stack<Lexema> lexemaStack) {
        int i = lexemaEntrada.length();
        while (lexemaPosicao < i-1) {
            Lexema lexemaaux11;
            lexemaaux11 = automato();
            lexemaStack.push(lexemaaux11);
            caracteresLidosAux = "";
        }
    }

    char proxchar() {
        lexemaPosicao = lexemaPosicao + 1;
        caracteresLidosAux = caracteresLidosAux + lexemaEntrada.charAt(lexemaPosicao);
        return lexemaEntrada.charAt(lexemaPosicao);

    }

    Lexema automato() {
        Lexema lexemaRetorno = new Lexema();
        estado = 0;
        while (true) {
            switch (estado) {
                case -1:
                    lexemaRetorno.setToken("erro");
                    lexemaRetorno.setLexema("erro");
                    return lexemaRetorno;
                case 0:
                    c = proxchar();
                    if (c == ' ' || c == '\t' || c == '\n') {
                        estado = 0;
                        caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    } else if (c == 's') estado = 1;
                    else if (c == 'e') estado = 6;
                    else if (c == 'f') estado = 20;
                    else if (c == 'l') estado = 23;
                    else if (c == 'o') estado = 27;
                    else if (c == '+') estado = 29;
                    else if (c == '-') estado = 30;
                    else if (c == '*') estado = 31;
                    else if (c == '/') estado = 32;
                    else if (c == ';') estado = 33;
                    else if (c == '(') estado = 34;
                    else if (c == ')') estado = 35;
                    else if (c == '=') estado = 36;
                    else if (c == '!') estado = 37;
                    else if (c == '>') estado = 38;
                    else if (c == '<') estado = 39;
                    else estado = falha();
                    break;
                case 1:
                    c = proxchar();
                    if (c == 'e') estado = 2;
                    else estado = falha();
                    break;
                case 2:
                    c = proxchar();
                    if (c == 'n') estado = 3;
                    else if (digito()) estado = falha();
                    else estado = 44;
                    break;
                case 3:
                    c = proxchar();
                    if (c == 'a') estado = 4;
                    else estado = falha();
                    break;
                case 4:
                    c = proxchar();
                    if (c == 'o') estado = 5;
                    else estado = falha();
                    break;
                case 5:
                    c = proxchar();
                    if (digito()) estado = falha();
                    else estado = 45;
                    break;
                case 6:
                    c = proxchar();
                    if (c == 'n') estado = 7;
                    else if (c == 's') estado = 14;
                    else if (digito()) estado = falha();
                    else estado = 56;
                    break;
                case 7:
                    c = proxchar();
                    if (c == 'q') estado = 8;
                    else estado = falha();
                    break;
                case 8:
                    c = proxchar();
                    if (c == 'u') estado = 9;
                    else estado = falha();
                    break;
                case 9:
                    c = proxchar();
                    if (c == 'a') estado = 10;
                    else estado = falha();
                    break;
                case 10:
                    c = proxchar();
                    if (c == 'n') estado = 11;
                    else estado = falha();
                    break;
                case 11:
                    c = proxchar();
                    if (c == 't') estado = 12;
                    else estado = falha();
                    break;
                case 12:
                    c = proxchar();
                    if (c == 'o') estado = 13;
                    else estado = falha();
                    break;
                case 13:
                    c = proxchar();
                    if (digito()) estado = falha();
                    else estado = 46;
                    break;
                case 14:
                    c = proxchar();
                    if (c == 'c') estado = 15;
                    else estado = falha();
                    break;
                case 15:
                    c = proxchar();
                    if (c == 'r') estado = 16;
                    else estado = falha();
                    break;
                case 16:
                    c = proxchar();
                    if (c == 'e') estado = 17;
                    else estado = falha();
                    break;
                case 17:
                    c = proxchar();
                    if (c == 'v') estado = 18;
                    else estado = falha();
                    break;
                case 18:
                    c = proxchar();
                    if (c == 'a') estado = 19;
                    else estado = falha();
                    break;
                case 19:
                    c = proxchar();
                    if (digito()) estado = falha();
                    else estado = 47;
                    break;
                case 20:
                    c = proxchar();
                    if (c == 'i') estado = 21;
                    else estado = falha();
                    break;
                case 21:
                    c = proxchar();
                    if (c == 'm') estado = 22;
                    else estado = falha();
                    break;
                case 22:
                    c = proxchar();
                    if (digito()) estado = falha();
                    else estado = 48;
                    break;
                case 23:
                    c = proxchar();
                    if (c == 'e') estado = 24;
                    else estado = falha();
                    break;
                case 24:
                    c = proxchar();
                    if (c == 'i') estado = 25;
                    else estado = falha();
                    break;
                case 25:
                    c = proxchar();
                    if (c == 'a') estado = 26;
                    else estado = falha();
                    break;
                case 26:
                    c = proxchar();
                    if (digito()) estado = falha();
                    else estado = 49;
                    break;
                case 27:
                    c = proxchar();
                    if (c == 'u') estado = 28;
                    else estado = falha();
                    break;
                case 28:
                    c = proxchar();
                    if (digito()) estado = falha();
                    else estado = 50;
                    break;
                case 29:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("plus");
                    return lexemaRetorno;
                case 30:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("minus");
                    return lexemaRetorno;
                case 31:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("times");
                    return lexemaRetorno;
                case 32:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("over");
                    return lexemaRetorno;
                case 33:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("semi");
                    return lexemaRetorno;
                case 34:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("lparen");
                    return lexemaRetorno;
                case 35:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("rparen");
                    return lexemaRetorno;
                case 36:
                    c = proxchar();
                    if (c == '=') estado = 40;
                    else estado = 51;
                    break;
                case 37:
                    c = proxchar();
                    if (c == '=') estado = 41;
                    else estado = falha();
                    break;
                case 38:
                    c = proxchar();
                    if (c == '=') estado = 42;
                    else estado = 52;
                    break;
                case 39:
                    c = proxchar();
                    if (c == '=') estado = 43;
                    else estado = 53;
                    break;
                case 40:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("eq");
                    return lexemaRetorno;
                case 41:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("neq");
                    return lexemaRetorno;
                case 42:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("ge");
                    return lexemaRetorno;
                case 43:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("le");
                    return lexemaRetorno;
                case 44:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("if");
                    return lexemaRetorno;
                case 45:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("else");
                    return lexemaRetorno;
                case 46:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("while");
                    return lexemaRetorno;
                case 47:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("write");
                    return lexemaRetorno;
                case 48:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("end");
                    return lexemaRetorno;
                case 49:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("read");
                    return lexemaRetorno;
                case 50:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("or");
                    return lexemaRetorno;
                case 51:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("attr");
                    return lexemaRetorno;
                case 52:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("gt");
                    return lexemaRetorno;
                case 53:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("le");
                    return lexemaRetorno;
                case 54:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("and");
                    return lexemaRetorno;
                case 55:
                    c = proxchar();
                    if (letra()) estado = 56;
                    else estado = falha();
                    break;
                case 56:
                    c = proxchar();
                    if (letra() || digito()) estado = 56;
                    else estado = 57;
                    break;
                case 57:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length()-1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("id");
                    return lexemaRetorno;
                case 58:
                    c = proxchar();
                    if(digito())estado = 58;
                    else estado = 59;
                    break;
                case 59:
                    lexemaPosicao--;
                    caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("num");
                    return lexemaRetorno;
                case 60:
                    lexemaRetorno.setLexema(caracteresLidosAux);
                    lexemaRetorno.setToken("$");
                    return lexemaRetorno;

            }
        }
    }

    int falha() {
        if (letra() || digito()) {
            if(estado >= 1 && estado <= 28){
                caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
                lexemaPosicao--;
                return 56;
            }
        }
        if (digito() && estado==0){
            return 58;}
        if (letra() && estado==0){
            caracteresLidosAux = caracteresLidosAux.substring(0, caracteresLidosAux.length() - 1);
            lexemaPosicao--;
            return 55;
        }if(c =='$'){
            return 60;
        }

        return -1;
    }

    boolean digito() {
        return (c >= '0' && c <= '9');
    }

    boolean letra() {
        return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'));
    }
}