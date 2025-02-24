import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String dataNascimento = (this.birthDate != null) ? this.birthDate.format(formatter) : "Data de nascimento não disponível";
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

    public Lista<Medalhista> recortarMedalhistas(String deOnde, String ateOnde) throws NullPointerException {
        Medalhista medalhistA = new Medalhista(deOnde, null, null, null);
        medalhistA = medalhistas.localizar(medalhistA);
        
        /*if (medalhistA == null) {
            System.out.println("Medalhista " + deOnde + " não encontrado.");
        }*/
        
        Medalhista medalhistB = new Medalhista(ateOnde, null, null, null);
        medalhistB = medalhistas.localizar(medalhistB);
        
        /*if (medalhistB == null) {
            System.out.println("Medalhista " + ateOnde + " não encontrado.");
        }*/
    
        if (medalhistA == null || medalhistB == null) {
            System.out.println("Nao ha medalhistas entre os indicados.");
        }
        
        if(deOnde != null && ateOnde != null && medalhistA != null && medalhistB != null){
        return medalhistas.recortar(medalhistA, medalhistB);
        }
        return null;
    }
    
    

    public void incluirMedalhista(Medalhista medalhista) {
        medalhistas.add(medalhista);
        quantMedalhistas++;
    }

    public Medalhista pesquisarMedalhista(String nome) throws NullPointerException{
        Medalhista medalhistaAPesquisar = new Medalhista(nome, null, null, null);
        return medalhistas.localizar(medalhistaAPesquisar);
    }

    public void relatorioMedalhistas() {
        medalhistas.caminhamentoEmOrdem();
    }

    public Lista<Medalhista> listaMedalhistas(){
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
        return "Medalhistas " + discipline + " - " + event;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event.toLowerCase(), discipline.toLowerCase());
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro)
            return true;
        if (outro == null || getClass() != outro.getClass())
            return false;
        Evento evento = (Evento) outro;
        return event.trim().equalsIgnoreCase(evento.event.trim()) &&
                discipline.trim().equalsIgnoreCase(evento.discipline.trim());
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

class Lista<E> implements Iterable<E> {

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int quantidade;

    Lista() {
        Celula<E> sentinela = new Celula<E>();
        primeiro = ultimo = sentinela;
        quantidade = 0;
    }

    public boolean isEmpty() {
        return (primeiro == ultimo);
    }

    public void inserirInicio(E item) {
        Celula<E> novaCelula = new Celula<>(item);
        novaCelula.setProximo(this.primeiro.getProximo());
        this.primeiro.setProximo(novaCelula);
        if (isEmpty())
            this.ultimo = novaCelula;
        this.quantidade++;
    }

    public void inserirFinal(E item) {
        Celula<E> novaCelula;
        novaCelula = new Celula<>(item);
        ultimo.setProximo(novaCelula);
        ultimo = novaCelula;
        quantidade++;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListaIterator();
    }

    private class ListaIterator implements Iterator<E> {
        private Celula<E> current = primeiro;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E item = current.getItem();
            current = current.getProximo();
            return item;
        }
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

    //
    //
    //DEBUG
    /*public E localizar(E item) {
        return localizar(raiz, item);
    }

    private E localizar(Node<E> raizArvore, E item) throws NullPointerException {
        if (raizArvore == null) {
            System.out.println("Item não encontrado: " + item);
            return null;
        }
        int comparacao = item.compareTo(raizArvore.getItem());
        System.out.println("Comparando " + item + " com " + raizArvore.getItem() + " -> Resultado: " + comparacao);

        if (comparacao == 0) {
            System.out.println("Item encontrado: " + raizArvore.getItem());
            return raizArvore.getItem();
        } else if (comparacao < 0) {
            return localizar(raizArvore.getEsquerda(), item);
        } else {
            return localizar(raizArvore.getDireita(), item);
        }
    }*/
    //
    //
    //DEBUG

    public void add(E item) {
        this.raiz = add(raiz, item);
    }

    private Node<E> add(Node<E> raizArvore, E item) {
        int comparacao;
        if (raizArvore == null) {
            raizArvore = new Node<E>(item);
        } else {
            comparacao = item.compareTo(raizArvore.getItem());
            if (comparacao == 0) {
                throw new IllegalArgumentException("Elemento duplicado não permitido na árvore.");
            } else if (comparacao < 0) {
                raizArvore.setEsquerda(add(raizArvore.getEsquerda(), item));
            } else {
                raizArvore.setDireita(add(raizArvore.getDireita(), item));
            }
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

    public Lista<E> recortar(E deOnde, E ateOnde) throws NullPointerException {
        Lista<E> resultado = new Lista<>();
        recortar(raiz, deOnde, ateOnde, resultado);
        return resultado;
    }

    private void recortar(Node<E> atual, E deOnde, E ateOnde, Lista<E> resultado) throws NullPointerException {
        if (atual == null)
            return;

        if (deOnde.compareTo(atual.getItem()) < 0) {
            recortar(atual.getEsquerda(), deOnde, ateOnde, resultado);
        }
        if (deOnde.compareTo(atual.getItem()) < 0 && ateOnde.compareTo(atual.getItem()) > 0) {
            resultado.inserirFinal(atual.getItem());
        }
        if (ateOnde.compareTo(atual.getItem()) > 0) {
            recortar(atual.getDireita(), deOnde, ateOnde, resultado);
        }
    }
}

public class Aplicacao {
    public static void main(String[] args) {
        
        BST<Evento> arvoreEventos = carregarEventos("/tmp/medallists.csv");
        Scanner scanner = new Scanner(System.in);


        while (true) {
            String linha = scanner.nextLine().trim();
            if (linha.equalsIgnoreCase("FIM")) {
                break;
            }

            String[] partes = linha.split(" - ");
            String comando = partes[0].trim().toUpperCase();

            switch (comando) {
                case "PESQUISAR": {
                    if (partes.length < 4) {
                        System.out.println("Formato inválido. Use: PESQUISAR - Esporte - Evento - Medalhista.");
                        continue;
                    }

                    String evento = partes[1].trim();
                    String esporte = partes[2].trim();
                    String medalhista = partes[3].trim();

                    Evento eventoPesquisado = new Evento(esporte, evento);
                    Evento encontrado = arvoreEventos.localizar(eventoPesquisado);

                    if (encontrado != null && encontrado.pesquisarMedalhista(medalhista) != null) {
                        System.out.println(medalhista + " foi medalhista no evento " + esporte + " do esporte " + evento);
                        System.out.println();
                    } else {
                        System.out.println(medalhista + " nao foi medalhista no evento " + evento + " do esporte " + esporte);
                        System.out.println();
                    }
                    break;
                }
                case "IMPRIMIR": {
                    if (partes.length < 3) {
                        System.out.println("Formato inválido. Use: IMPRIMIR - Esporte - Evento.");
                        continue;
                    }

                    String esporte = partes[1].trim();
                    String evento = partes[2].trim();

                    Evento eventoImprimir = new Evento(evento, esporte);
                    Evento encontrado = arvoreEventos.localizar(eventoImprimir);

                    if (encontrado != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        System.out.println("Medalhistas " + esporte + " - " + evento);
                        Lista<Medalhista> medallist = encontrado.listaMedalhistas();
                        for (Medalhista medalhista : medallist) {
                            if (medalhista != null) {
                                String dataFormatada1 = medalhista.getNascimento().format(formatter);
                                System.out.print(medalhista.getName() + ", " + medalhista.getGenero() + ". ");
                                System.out.println("Nascimento: " + dataFormatada1+ ". Pais: " + medalhista.getPais() + ".");
                                
                                //provavelmente estou instanciando 2 medalhistas com mesmos atributos 
                                //e as medalhas estao ficando em instancias diferentes.

                                for (Medalha medalha : medalhista.getMedals()) {
                                    if(medalha != null){
                                    String dataFormatada2 = medalha.getDate().format(formatter);
                                    System.out.println(medalha.getTipo() + " - " + encontrado.getDiscipline() + " - " + encontrado.getEvent() + " - " + dataFormatada2);
                                    System.out.println();
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("Evento nao encontrado.");
                    }
                    break;
                }
                case "RECORTAR": {
                    if (partes.length < 5) {
                        System.out.println(
                                "Formato inválido. Use: RECORTAR - Esporte - Evento - Medalhista1 - Medalhista2.");
                        continue;
                    }

                    String esporte = partes[2].trim();
                    String evento = partes[1].trim();
                    String medalhistaInicio = partes[3].trim().split("RECORTAR")[0].trim(); 
                    String medalhistaFim = partes[4].trim().split("RECORTAR")[0].trim();  

                    Evento eventoRecortar = new Evento(esporte, evento);
                    Evento encontrado = arvoreEventos.localizar(eventoRecortar);

                    if (encontrado != null) {
                        System.out.println("Medalhistas entre " + medalhistaInicio + " e " + medalhistaFim + ":");
                        Lista<Medalhista> encontrados = encontrado.recortarMedalhistas(medalhistaInicio, medalhistaFim);
                        if(encontrados != null){
                        for(Medalhista x : encontrados){
                            System.out.println(x);
                        }
                        }
                    } else {
                        System.out.println("Evento não encontrado.");
                    }
                    break;
                }
                default:
                    System.out.println("Comando não reconhecido. Use PESQUISAR, IMPRIMIR ou RECORTAR.");
            }
        }
        scanner.close();
    }

    
    private static BST<Evento> carregarEventos(String csvFile) {
        BST<Evento> eventos = new BST<>();
        String line;
        String csvSplitBy = ",";
    
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
    
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(csvSplitBy);
    
                String nome = dados[0].trim();
                String tipoMedalhaStr = dados[1].trim().toUpperCase();
                LocalDate medalhaData = LocalDate.parse(dados[2].trim());
                String genero = dados[3].trim();
                LocalDate nascimento = LocalDate.parse(dados[4].trim());
                String pais = dados[5].trim();
                String disciplina = dados[6].trim();
                String eventoNome = dados[7].trim();
    
                TipoMedalha tipoMedalha;
                try {
                    tipoMedalha = TipoMedalha.valueOf(tipoMedalhaStr);
                } catch (IllegalArgumentException e) {
                    System.out.println("Tipo de medalha inválido: " + tipoMedalhaStr);
                    continue;
                }
    
                Medalhista medalhista = new Medalhista(nome, genero, nascimento, pais);
                Medalha medalha = new Medalha(tipoMedalha, medalhaData);
                
    
                Evento evento = new Evento(eventoNome, disciplina);
                Evento eventoExistente = eventos.localizar(evento);
    
                if (eventoExistente != null) {
                    boolean medalhistaExistente = false;
                    for (Medalhista m : eventoExistente.listaMedalhistas()) {
                        if (m != null && m.getName().equals(nome)) {
                            m.incluirMedalha(medalha);
                            medalhistaExistente = true;
                            break;
                        }
                    }
                    if (!medalhistaExistente) {
                        eventoExistente.incluirMedalhista(medalhista);
                        medalhista.incluirMedalha(medalha);
                    }
                } else {
                    evento.incluirMedalhista(medalhista);
                    medalhista.incluirMedalha(medalha);
                    eventos.add(evento);
                }
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
                //
                //
                //DEBUG
                /*
                    Lista<Evento> eventox = eventos.emOrdem();

                    for (Evento evento : eventox) {
                        if (evento != null) {
                            System.out.println("Evento: " + evento.getEvent() + " - " + evento.getDiscipline());
                        
                            BST<Medalhista> arvoreMedalhistas = evento.getArvoreMedalhistas();
                            
                            if (arvoreMedalhistas != null) {
                                Lista<Medalhista> aM = arvoreMedalhistas.emOrdem();

                                for (Medalhista medalhista : aM) {
                                    if (medalhista != null) {
                                        System.out.println("  " + medalhista.getName() + ", " + medalhista.getGenero() + ". Nascimento: "
                                                + medalhista.getNascimento() + ". Pais: " + medalhista.getPais());

                                        for (Medalha medalha : medalhista.getMedals()) {
                                            if(medalha != null){
                                            System.out.println("    Medalha: " + medalha.getTipo() + " - Data: " + medalha.getDate());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                 */               
                //DEBUG
                //
                //

        return eventos;
    }
}
