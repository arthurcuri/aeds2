import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * Classe Medalhista: representa um medalhista olímpico e sua coleção de
 * medalhas
 * nas Olimpíadas de Paris 2024
 */
class Medalhista implements Comparable<Medalhista> {
    /** Para criar o vetor com no máximo 8 medalhas */
    private static final int MAX_MEDALHAS = 8;
    /** Nome do medalhista */
    private String name;
    /** Gênero do medalhista */
    private String gender;
    /** Data de nascimento do medalhista */
    private LocalDate birthDate;
    /** País do medalhista */
    private String country;
    /** Coleção de medalhas do medalhista */
    private Medalha[] medals;
    /** Contador de medalhas e índice para controlar o vetor de medalhas */
    private int medalCount;

    /**
     * Cria um medalhista olímpico. Nenhum dado precisa ser validado.
     * 
     * @param nome       Nome do medalhista no formato "SOBRENOME nome"
     * @param genero     Gênero do medalhista
     * @param nascimento Data de nascimento do medalhista
     * @param pais       País do medalhista (conforme dados originais, em inglês)
     */
    public Medalhista(String nome, String genero, LocalDate nascimento, String pais) {
        this.name = nome;
        this.gender = genero;
        this.birthDate = nascimento;
        this.country = pais;
        this.medals = new Medalha[MAX_MEDALHAS];
        this.medalCount = 0;
    }

    /**
     * Inclui uma medalha na coleção do medalhista. Retorna a quantidade atual de
     * medalhas do atleta.
     * 
     * @param medalha A medalha a ser armazenada.
     * @return A quantidade total de medalhas do atleta após a inclusão.
     */
    public int incluirMedalha(Medalha medalha) {
        if (medalCount < MAX_MEDALHAS) {
            medals[medalCount] = medalha;
            medalCount++;
        }
        return medalCount;
    }

    /**
     * Total de medalhas do atleta. É um número maior ou igual a 0.
     * 
     * @return Inteiro com o total de medalhas do atleta (>=0)
     */
    public int totalMedalhas() {
        return medalCount;
    }

    public int totalDeMedalhas(TipoMedalha tipo) {
        int total = 0;
        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                total++;
            }
        }
        return total;
    }

    /**
     * Retorna um relatório das medalhas do atleta conforme o tipo solicitado pelo
     * parâmetro. Veja no enunciado da atividade o formato correto deste relatório.
     * Em caso de não possuir medalhas deste tipo, a resposta deve ser "Nao possui
     * medalha de TIPO".
     * 
     * @param tipo Tipo da medalha para o relatório
     * @return Uma string, multilinhas, com o relatório de medalhas daquele tipo.
     */
    public String relatorioDeMedalhas(TipoMedalha tipo) {
        StringBuilder relatorio = new StringBuilder();
        boolean possuiMedalha = false;

        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                relatorio.append(medals[i].toString()).append("\n");
                possuiMedalha = true;
            }
        }

        if (!possuiMedalha) {
            return "Nao possui medalha de " + tipo;
        }

        return relatorio.toString().trim();
    }

    /**
     * Retorna o nome do país do medalhista (conforme arquivo original em inglês.)
     * 
     * @return String contendo o nome do país do medalhista (conforme arquivo
     *         original em inglês, iniciais em maiúsculas.)
     */
    public String getPais() {
        return country;
    }

    /**
     * Retorna uma cópia da data de nascimento do medalhista.
     * 
     * @return LocalDate com a data de nascimento do medalhista.
     */
    public LocalDate getNascimento() {
        return LocalDate.from(birthDate);
    }

    public String getGenero() {
        return gender;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Medalhista other = (Medalhista) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Deve retornar os dados pessoais do medalhista, sem as medalhas, conforme
     * especificado no enunciado da atividade.
     * 
     * @return String de uma linha, com os dados do medalhista, sem dados da
     *         medalha.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataNascimento = (this.birthDate != null) ? this.birthDate.format(formatter)
                : "Data de nascimento não disponível";
        return name + ", " + gender + ". Nascimento: " + dataNascimento + ". Pais: " + country;
    }

    @Override
    public int compareTo(Medalhista o) {
        return this.getName().compareTo(o.getName());
    }

    public boolean temMedalha(TipoMedalha tipo) {
        for (int i = 0; i < medalCount; i++) {
            if (medals[i] != null && medals[i].getTipo() == tipo) {
                return true;
            }
        }
        return false;
    }

    public int contarMedalhas(TipoMedalha tipo) {
        int count = 0;
        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                count++;
            }
        }
        return count;
    }

    public Medalha[] getMedals() {
        return medals;
    }
}

/** Enumerador para medalhas de ouro, prata e bronze */
enum TipoMedalha {
    OURO,
    PRATA,
    BRONZE
}

/**
 * Representa uma medalha obtida nos Jogos Olímpicos de Paris em 2024.
 */
class Medalha {
    /** Tipo/cor da medalha conforme o enumerador */
    private TipoMedalha metalType;
    /** Data de obtenção da medalha */
    private LocalDate medalDate;
    /** Evento da medalha, conforme arquivo de dados */
    private String event;

    /** Cria uma medalha com os dados do parâmetro. Nenhum dado é validado */
    public Medalha(TipoMedalha tipo, LocalDate data) {
        this.metalType = tipo;
        this.medalDate = data;
    }

    /**
     * Retorna o tipo de medalha, conforme o enumerador
     * 
     * @return TipoMedalha (enumerador) com o tipo/cor desta medalha
     */
    public TipoMedalha getTipo() {
        return metalType;
    }

    public LocalDate getDate() {
        return medalDate;
    }

    public String getEvent() {
        return event;
    }

    /**
     * Retorna uma string com o "relatório" da medalha de acordo com o especificado
     * no enunciado do problema.
     * Contém uma linha que já formata a data da medalha no formato brasileiro. O
     * restante deve ser implementado.
     */
    public String toString() {
        String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(medalDate); // formata a data em
                                                                                            // DD/MM/AAAA
        return metalType + " medalha, " + /* discipline + */", " + event + ", " + dataFormatada;
    }
}

class Evento implements Comparable<Evento> {
    private String event;
    private String discipline;
    private int quantMedalhistas;
    private BST<Medalhista> medalhistas;

    public Evento(String nomeEvento, String disciplina) {
        setEvent(nomeEvento);
        setDiscipline(disciplina);
        medalhistas = new BST<>();
    }

    public void incluirMedalhista(Medalhista medalhista) {
        medalhistas.add(medalhista);
        quantMedalhistas++;
    }

    public Medalhista pesquisarMedalhista(String nome) throws NullPointerException {
        Medalhista medalhistaAPesquisar = new Medalhista(nome, null, null, null);
        return medalhistas.localizar(medalhistaAPesquisar);
    }

    public void relatorioMedalhistas() {
        medalhistas.caminhamentoEmOrdem();
    }

    public Lista<Medalhista> listaMedalhistas() {
        return medalhistas.emOrdem();
    }

    @Override
    public int compareTo(Evento outro) {
        int disciplinaComp = this.discipline.trim().toLowerCase().compareTo(outro.discipline.trim().toLowerCase());

        if (disciplinaComp != 0) {
            return disciplinaComp;
        }

        return this.event.trim().toLowerCase().compareTo(outro.event.trim().toLowerCase());
    }

    @Override
    public String toString() {
        return event + " - " + discipline;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Evento evento = (Evento) obj;
        return this.event.equalsIgnoreCase(evento.event) &&
                this.discipline.equalsIgnoreCase(evento.discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.event.toLowerCase(), this.discipline.toLowerCase());
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String evento) {
        this.event = evento;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String disciplina) {
        this.discipline = disciplina;
    }
}

class Entrada<K, V> {

    private final K chave;
    private V valor;

    public Entrada(K chave, V item) {
        this.chave = chave;
        this.valor = item;
    }

    public K getChave() {
        return chave;
    }

    public V getValor() {
        return valor;
    }

    public void setValor(V valor) {
        this.valor = valor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object outroObjeto) {

        Entrada<K, V> outraEntrada;

        if (this == outroObjeto)
            return true;
        else if (outroObjeto == null || !(outroObjeto.getClass() == this.getClass()))
            return false;
        else {
            outraEntrada = (Entrada<K, V>) outroObjeto;
            return (outraEntrada.getChave().equals(this.chave));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.chave);
    }

    @Override
    public String toString() {
        return (this.chave + "\n" + this.valor);
    }
}

class TabelaHash<K, V> {

    private Lista<Entrada<K, V>>[] tabelaHash; /// tabela que referenciará todas as listas lineares encadeadas.
    /// Nesse caso, estamos utilizando uma tabela hash com endereçamento em
    /// separado,
    /// ou seja, os itens são armazenados em listas lineares encadeadas.

    private int capacidade; /// tamanho da tabela hash.
                            /// deve ser um número primo grande para diminuirmos a probabilidade de
                            /// colisões.

    /**
     * Construtor da classe.
     * Esse método é responsável por inicializar a tabela hash que trabalha com
     * endereçamento em separado.
     * Assim, esse método atribui, ao atributo "capacidade", dessa classe, o valor
     * passado por meio do parâmetro "capacidade".
     * Esse método também cria um vetor, de tamanho "capacidade", de listas
     * lineares; e o atribui ao atributo "tabelaHash".
     * Adicionalmente, cada posição do vetor é inicializada com uma lista encadeada
     * vazia.
     * 
     * @param capacidade: tamanho da tabela hash.
     */
    @SuppressWarnings("unchecked")
    public TabelaHash(int capacidade) {

        this.capacidade = capacidade;
        this.tabelaHash = (Lista<Entrada<K, V>>[]) new Lista[this.capacidade];

        for (int i = 0; i < this.capacidade; i++)
            this.tabelaHash[i] = new Lista<>();
    }

    public Lista<K> chaves() {
        Lista<K> listaChaves = new Lista<>();
        for (int i = 0; i < tabelaHash.length; i++) {
            if (tabelaHash[i] != null) {
                for (Entrada<K, V> entrada : tabelaHash[i]) {
                    listaChaves.inserirFinal(entrada.getChave());
                }
            }
        }
        return listaChaves;
    }

    /**
     * Esse método implementa a função de transformação da tabela hash,
     * ou seja, calcula a posição, na tabela hash, em que o item,
     * que possui a chave informada por meio do parâmetro "chave", deve ser
     * encontrado.
     * A função de transformação utilizada corresponde ao resto da divisão do
     * hashCode de "chave" pelo tamanho da tabela hash.
     * 
     * @param chave: chave da qual desejamos saber a posição na tabela hash.
     * @return a posição que o item, cuja chave corresponde a que foi passada como
     *         parâmetro para esse método, deve ocupar na tabela hash.
     */
    private int funcaoHash(K chave) {
        return Math.abs(chave.hashCode() % this.capacidade);
    }

    /**
     * Método responsável por inserir um novo item na tabela hash.
     * Não é permitido inserir, nessa tabela hash, mais de um item com uma mesma
     * chave.
     * 
     * @param chave: chave do item que deve ser inserido na tabela hash.
     * @param item:  referência ao item que deve ser inserido na tabela hash.
     * @return a posição na tabela hash em que o novo item foi inserido.
     */
    public void inserir(K chave, V valor) {
        int posicao = funcaoHash(chave);
        // System.out.println("Posição calculada para a chave " + chave + ": " +
        // posicao); dbug

        if (this.tabelaHash[posicao] == null) {
            this.tabelaHash[posicao] = new Lista<>();
        }

        Entrada<K, V> novaEntrada = new Entrada<>(chave, valor);
        this.tabelaHash[posicao].inserirFinal(novaEntrada);
    }

    /**
     * Método responsável por localizar, na tabela hash, o item
     * cuja chave corresponde à que foi passada como parâmetro para esse método.
     * 
     * @param chave: chave do item que deve ser localizado na tabela hash.
     * @return uma referência ao item encontrado.
     *         O método lança uma exceção caso o item não seja localizado na tabela
     *         hash.
     */

    public V pesquisar(K chave) {
        // System.out.println("Pesquisando para a chave: " + chave); dbug
        // Cálculo da posição na tabela hash usando a função de hash
        int posicao = funcaoHash(chave);

        // Verifica se a lista na posição não está vazia
        if (this.tabelaHash[posicao] != null) {
            for (Entrada<K, V> entrada : this.tabelaHash[posicao]) {
                if (entrada.getChave().equals(chave)) {
                    return entrada.getValor(); // Retorna o valor se encontrado
                }
            }
        }

        // Se não encontrado, retorna null
        return null;
    }

    /**
     * Método responsável por remover, da tabela hash, o item
     * cuja chave corresponde à que foi passada como parâmetro para esse método.
     * 
     * @param chave: chave do item que deve ser removido da tabela hash.
     * @return uma referência ao item removido.
     *         O método lança uma exceção caso o item não tenha sido localizado na
     *         tabela hash.
     */
    public V remover(K chave) {

        /// cálculo da posição da tabela hash em que o item deve estar armazenado.
        int posicao = funcaoHash(chave);

        Entrada<K, V> procurado = new Entrada<>(chave, null);

        /// remove o item, cuja chave foi passada como parâmetro para esse método,
        /// da lista encadeada associada à posição, da tabela hash, em que esse item
        /// deve estar armazenado.
        procurado = this.tabelaHash[posicao].remover(procurado);
        return procurado.getValor();
    }

    /**
     * Método responsável por imprimir todo o conteúdo da tabela hash.
     * É impresso o índice da tabela hash e seu correspondente conteúdo.
     * Se a posição da tabela hash estiver vazia, é impressa uma mensagem
     * explicativa.
     * Caso contrário, para todos os itens, armazenados na lista encadeada
     * associada a uma posição da tabela hash, são impressos seus dados.
     */
    public void imprimir() {

        for (int i = 0; i < this.capacidade; i++) {
            System.out.println("Posição[" + i + "]: ");
            if (this.tabelaHash[i].vazia())
                System.out.println("vazia");
            else
                this.tabelaHash[i].imprimir();
        }
    }
}

class Lista<E> implements Iterable<E> {

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int tamanho;

    public Lista() {

        Celula<E> sentinela = new Celula<>();

        this.primeiro = this.ultimo = sentinela;
        this.tamanho = 0;
    }

    public boolean vazia() {

        return (this.primeiro == this.ultimo);
    }

    public void inserir(E novo, int posicao) {

        Celula<E> anterior, novaCelula, proximaCelula;

        if ((posicao < 0) || (posicao > this.tamanho))
            throw new IndexOutOfBoundsException("Não foi possível inserir o item na lista: "
                    + "a posição informada é inválida!");

        anterior = this.primeiro;
        for (int i = 0; i < posicao; i++)
            anterior = anterior.getProximo();

        novaCelula = new Celula<>(novo);

        proximaCelula = anterior.getProximo();

        anterior.setProximo(novaCelula);
        novaCelula.setProximo(proximaCelula);

        if (posicao == this.tamanho)
            this.ultimo = novaCelula;

        this.tamanho++;
    }

    public void inserirFinal(E novo) {

        Celula<E> novaCelula = new Celula<>(novo);

        this.ultimo.setProximo(novaCelula);
        this.ultimo = novaCelula;

        this.tamanho++;
    }

    private E removerProxima(Celula<E> anterior) {

        Celula<E> celulaRemovida, proximaCelula;

        celulaRemovida = anterior.getProximo();

        proximaCelula = celulaRemovida.getProximo();

        anterior.setProximo(proximaCelula);
        celulaRemovida.setProximo(null);

        if (celulaRemovida == this.ultimo)
            this.ultimo = anterior;

        this.tamanho--;

        return (celulaRemovida.getItem());
    }

    public E remover(int posicao) {

        Celula<E> anterior;

        if (vazia())
            throw new IllegalStateException("Não foi possível remover o item da lista: "
                    + "a lista está vazia!");

        if ((posicao < 0) || (posicao >= this.tamanho))
            throw new IndexOutOfBoundsException("Não foi possível remover o item da lista: "
                    + "a posição informada é inválida!");

        anterior = this.primeiro;
        for (int i = 0; i < posicao; i++)
            anterior = anterior.getProximo();

        return (removerProxima(anterior));
    }

    public E remover(E elemento) {

        Celula<E> anterior;

        if (vazia())
            throw new IllegalStateException("Não foi possível remover o item da lista: "
                    + "a lista está vazia!");

        anterior = this.primeiro;
        while ((anterior.getProximo() != null) && !(anterior.getProximo().getItem().equals(elemento)))
            anterior = anterior.getProximo();

        if (anterior.getProximo() == null)
            throw new NoSuchElementException("Item não encontrado!");
        else {
            return (removerProxima(anterior));
        }
    }

    public E pesquisar(E procurado) {

        Celula<E> aux;

        aux = this.primeiro.getProximo();

        while (aux != null) {
            if (aux.getItem().equals(procurado))
                return aux.getItem();
            aux = aux.getProximo();
        }

        return null;
    }

    public void imprimir() {

        Celula<E> aux;

        aux = this.primeiro.getProximo();

        while (aux != null) {
            System.out.println(aux.getItem());
            aux = aux.getProximo();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Celula<E> atual = primeiro.getProximo();

            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public E next() {
                E item = atual.getItem();
                atual = atual.getProximo();
                return item;
            }
        };
    }
}

class Celula<T> {

    private final T item;
    private Celula<T> proximo;

    public Celula() {
        this.item = null;
        setProximo(null);
    }

    public Celula(T item) {
        this.item = item;
        setProximo(null);
    }

    public Celula(T item, Celula<T> proximo) {
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

class Node<E extends Comparable<E>> {
    private Node<E> esquerda;
    private Node<E> direita;
    private E item;

    public Node() {
        this.setItem(null);
        this.setEsquerda(null);
        this.setDireita(null);
    }

    public Node(E item) {
        this.setItem(item);
        this.setEsquerda(null);
        this.setDireita(null);
    }

    public Node(E item, Node<E> esquerda, Node<E> direita) {
        this.setItem(null);
        this.setEsquerda(esquerda);
        this.setDireita(direita);
    }

    public Node<E> getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Node<E> esquerda) {
        this.esquerda = esquerda;
    }

    public Node<E> getDireita() {
        return direita;
    }

    public void setDireita(Node<E> direita) {
        this.direita = direita;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }
}

class BST<E extends Comparable<E>> {
    private Node<E> raiz;

    public BST() {
        this.raiz = null;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }

    public E localizar(E item) {
        return localizar(raiz, item);
    }

    private E localizar(Node<E> raizArvore, E item) {
        int comparacao;
        if (raizArvore == null) {
            return null;
        }
        comparacao = item.compareTo(raizArvore.getItem());
        if (comparacao == 0) {
            return raizArvore.getItem();
        } else if (comparacao < 0) {
            return localizar(raizArvore.getEsquerda(), item);
        } else {
            return localizar(raizArvore.getDireita(), item);
        }
    }

    public void add(E item) {
        this.raiz = add(raiz, item);
    }

    private Node<E> add(Node<E> raizArvore, E item) {
        if (raizArvore == null) {
            return new Node<E>(item);
        }

        int comparacao = item.compareTo(raizArvore.getItem());

        if (comparacao == 0) {
            return raizArvore;
        } else if (comparacao < 0) {
            raizArvore.setEsquerda(add(raizArvore.getEsquerda(), item));
        } else {
            raizArvore.setDireita(add(raizArvore.getDireita(), item));
        }

        return raizArvore;
    }

    public void remove(E item) {
        this.raiz = remove(raiz, item);
    }

    private Node<E> remove(Node<E> raizArvore, E item) {
        int comparacao;
        if (raizArvore == null) {
            throw new IllegalStateException("Elemento não encontrado ou árvore vazia.");
        }
        comparacao = item.compareTo(raizArvore.getItem());
        if (comparacao == 0) {
            if (raizArvore.getEsquerda() == null) {
                raizArvore = raizArvore.getDireita();
            } else if (raizArvore.getDireita() == null) {
                raizArvore = raizArvore.getEsquerda();
            } else {
                raizArvore.setEsquerda(removeNoAntecessor(raizArvore, raizArvore.getEsquerda()));
            }
        } else if (comparacao < 0) {
            raizArvore.setEsquerda(remove(raizArvore.getEsquerda(), item));
        } else {
            raizArvore.setDireita(remove(raizArvore.getDireita(), item));
        }
        return raizArvore;
    }

    private Node<E> removeNoAntecessor(Node<E> removido, Node<E> raizArvore) {
        if (raizArvore.getDireita() != null) {
            raizArvore.setDireita(removeNoAntecessor(removido, raizArvore.getDireita()));
        } else {
            removido.setItem(raizArvore.getItem());
            raizArvore = raizArvore.getEsquerda();
        }
        return raizArvore;
    }

    public void caminhamentoEmOrdem() {
        if (isEmpty()) {
            throw new IllegalStateException("Árvore vazia.");
        } else {
            caminhamentoEmOrdem(this.raiz);
        }
    }

    private void caminhamentoEmOrdem(Node<E> raizArvore) {
        if (raizArvore != null) {
            caminhamentoEmOrdem(raizArvore.getEsquerda());
            System.out.println(raizArvore.getItem().toString());
            caminhamentoEmOrdem(raizArvore.getDireita());
        }
    }

    public Lista<E> emOrdem() {
        Lista<E> elementos = new Lista<>();
        caminhamentoEmOrdem(this.raiz, elementos);
        return elementos;
    }

    private void caminhamentoEmOrdem(Node<E> raizArvore, Lista<E> elementos) {
        if (raizArvore != null) {
            caminhamentoEmOrdem(raizArvore.getEsquerda(), elementos);
            elementos.inserirFinal(raizArvore.getItem());
            caminhamentoEmOrdem(raizArvore.getDireita(), elementos);
        }
    }
}

public class Aplicacao {
    public static void main(String[] args) {
        TabelaHash<String, BST<Evento>> tabelaHash = new TabelaHash<>(15);

        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoEntradaOriginal = new SimpleDateFormat("dd/MM/yyyy");

        try (BufferedReader br = new BufferedReader(new FileReader("/tmp/medallists.csv"))) {
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                String data = campos[2].trim();

                try {
                    Date dataObj;
                    if (data.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        dataObj = formatoEntrada.parse(data);
                    } else {
                        dataObj = formatoEntradaOriginal.parse(data);
                    }
                    String dataFormatada = formatoEntrada.format(dataObj);

                    String esporte = campos[6].trim();
                    String nomeEvento = campos[7].trim();

                    Evento evento = new Evento(esporte, nomeEvento);

                    BST<Evento> eventosDoDia = tabelaHash.pesquisar(dataFormatada);
                    if (eventosDoDia == null) {
                        eventosDoDia = new BST<>();
                        tabelaHash.inserir(dataFormatada, eventosDoDia);
                    }

                    eventosDoDia.add(evento);

                } catch (Exception e) {
                    System.err.println("Erro ao formatar a data: " + data);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) {
                break;
            }

            try {
                Date dataObj;
                if (linha.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    dataObj = formatoEntrada.parse(linha);
                } else {

                    dataObj = formatoEntradaOriginal.parse(linha);
                }
                String dataFormatada = formatoEntrada.format(dataObj);

                BST<Evento> eventos = tabelaHash.pesquisar(dataFormatada);
                if (eventos != null) {
                    System.out.println("Eventos do dia " + linha);
                    Lista<Evento> emOrdem = eventos.emOrdem();

                    for (Evento x : emOrdem) {
                        System.out.println(x.getEvent() + " - " + x.getDiscipline());
                    }
                    System.out.println();

                } else {
                    System.out.println("Nenhum evento encontrado para o dia " + linha);
                }

            } catch (Exception e) {
                System.err.println("Erro ao formatar a data fornecida para pesquisa: " + linha);
            }
        }
        sc.close();
    }
}
