import java.util.NoSuchElementException;

public class Pilha<E>{

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
