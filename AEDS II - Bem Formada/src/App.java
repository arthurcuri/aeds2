import java.util.Scanner;
import java.util.NoSuchElementException;

public class App {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        String i = sc.nextLine();

        while(!i.equals("FIM")) {
            Boolean bool = eBemFormada(i);
            i = sc.nextLine();
            if(bool){
                System.out.println("correto");
            }
            else{
                System.out.println("incorreto");
            }
        }
        
        sc.close();
    }
    

    public static boolean eBemFormada(String expressao){
        Pilha<Character> pilha = new Pilha<>();

        for (char c : expressao.toCharArray()) {
            if (c == '(' || c == '[') {
                pilha.push(c);
            } else if (c == ')') {
                if (pilha.vazio() || pilha.pop() != '(') {
                    return false;
                }
            } else if (c == ']') {
                if (pilha.vazio() || pilha.pop() != '[') {
                    return false;
            }
        }
    }
    return pilha.vazio();
    }
}

class Pilha<E>{

    private Celula<E> topo;
    private Celula<E> fundo;

    public Pilha(){
        Celula<E> sentinela = new Celula<>();
        fundo = topo = sentinela;
    }

    public void push(E item){
        Celula<E> novaCelula = new Celula<>(item, topo);
        topo = novaCelula;
    }

    public E pop(){
        E desempilhada = top();
        topo = topo.getProximo();
        return desempilhada;
    }

    public boolean vazio(){
        return (topo==fundo);
    }

    public E top(){
        if(vazio()){
            throw new NoSuchElementException("Erro!! A pilha está vazia.");
        }
        return (topo.getItem());
    }
}

class Celula<T> {

    private final T item;
    private Celula<T> proximo;

    public Celula(){
        this.item = null;
        this.proximo = null;
    }

    public Celula(T item){
        this.item = item;
        this.proximo = null;
    }

    public Celula(T item, Celula<T> proximo){
        this.item = item;
        this.proximo = proximo;
    }

    public T getItem() {
        return item;
    }

    public Celula<T> getProximo() {
        return proximo;
    }

    public void setProximo(Celula<T> proximo) {
        this.proximo = proximo;
    }
}
