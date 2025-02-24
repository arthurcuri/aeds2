import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe Medalhista: representa um medalhista olímpico e sua coleção de
 * medalhas
 * nas Olimpíadas de Paris 2024
 */
class Medalhista {
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
        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == tipo) {
                relatorio.append(medals[i].toString()).append("\n");
            }
        }
        if (relatorio.length() == 0) {
            relatorio.append("Nao possui medalha de ").append(tipo).append("\n");
        }
        return relatorio.toString();
    }

    public String relatorioDeMedalhasSemParametro() {
        StringBuilder relatorio = new StringBuilder();
        int countOuro = 0;
        int countPrata = 0;
        int countBronze = 0;

        for (int i = 0; i < medalCount; i++) {
            if (medals[i].getTipo() == TipoMedalha.OURO) {
                countOuro++;
            } else if (medals[i].getTipo() == TipoMedalha.PRATA) {
                countPrata++;
            } else if (medals[i].getTipo() == TipoMedalha.BRONZE) {
                countBronze++;
            }
        }

        if (countOuro > 0) {
            relatorio.append("Quantidade de medalhas de ouro: ").append(countOuro).append("\n");
        }
        if (countPrata > 0) {
            relatorio.append("Quantidade de medalhas de prata: ").append(countPrata).append("\n");
        }
        if (countBronze > 0) {
            relatorio.append("Quantidade de medalhas de bronze: ").append(countBronze).append("\n");
        }
        return relatorio.toString();
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Medalhista that = (Medalhista) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
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
        return name + ", " + gender + ". Nascimento: " + birthDate.format(formatter) + ". Pais: " + country;
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
    /** Disciplina da medalha, conforme arquivo de dados */
    private String discipline;
    /** Evento da medalha, conforme arquivo de dados */
    private String event;

    /** Cria uma medalha com os dados do parâmetro. Nenhum dado é validado */
    public Medalha(TipoMedalha tipo, LocalDate data, String disciplina, String evento) {
        this.metalType = tipo;
        this.medalDate = data;
        this.discipline = disciplina;
        this.event = evento;
    }

    /**
     * Retorna o tipo de medalha, conforme o enumerador
     * 
     * @return TipoMedalha (enumerador) com o tipo/cor desta medalha
     */
    public TipoMedalha getTipo() {
        return metalType;
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

class ListaDuplamente<E> {

    private Celula<E> primeiro;
    private Celula<E> ultimo;
    private int quantidade;

    ListaDuplamente() {
        Celula<E> sentinela = new Celula<E>();
        primeiro = ultimo = sentinela;
        quantidade = 0;
    }

    public boolean isEmpty() {
        return (primeiro == ultimo);
    }

    public void inserirFinal(E novo) {
        Celula<E> novaCelula = new Celula<>(novo, this.ultimo, null);

        this.ultimo.setProximo(novaCelula);
        this.ultimo = novaCelula;
        this.quantidade++;

    }

    public E removerFinal() {
        Celula<E> removida, penultima;
        if (isEmpty()) {
            throw new IllegalStateException("Lista Duplamente Encadeada está vazia.");
        }
        removida = this.ultimo;
        penultima = this.ultimo.getAnterior();
        penultima.setProximo(null);
        removida.setAnterior(null);

        this.ultimo = penultima;
        this.quantidade--;
        return (removida.getItem());
    }

    public E removerInicio() {
        Celula<E> removida;
        if (isEmpty()) {
            throw new IllegalStateException("Lista Duplamente Encadeada está vazia.");
        }
        removida = primeiro.getProximo();

        primeiro.setProximo(removida.getProximo());
        if(removida == ultimo){
            ultimo = primeiro;
        }
        else{
            removida.getProximo().setAnterior(primeiro);
        }
        removida.setAnterior(null);
        removida.setProximo(null);
        quantidade--;

        return removida.getItem();
    }

    public void mesclar(ListaDuplamente<E> lista){
        ListaDuplamente<E> duplamente = new ListaDuplamente<>();
        int tamanho = this.quantidade + lista.quantidade;
        for(int i=0; i < tamanho ;){
            if(!this.isEmpty()){
                duplamente.inserirFinal(this.removerInicio());
                i++;
            }
            if(!lista.isEmpty()){
                duplamente.inserirFinal(lista.removerInicio());
                i++;
            }
        }
        this.primeiro = duplamente.primeiro;
        this.ultimo = duplamente.ultimo;
        this.quantidade = duplamente.quantidade;
    }

    public boolean contemSequencia(ListaDuplamente<E> lista){
        Celula<E> aux, ref;
        aux = primeiro.getProximo();
        ref = lista.primeiro.getProximo();
        while(aux != null){
            Celula<E> tempAux = aux;
            Celula<E> refAux = ref;
            while (tempAux != null && refAux != null && tempAux.getItem().equals(refAux.getItem())) {
                tempAux = tempAux.getProximo();
                refAux = refAux.getProximo();
            }
            if (refAux == null) {
                return true;
            }
            aux = aux.getProximo(); 
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Celula<E> atual = this.primeiro.getProximo();
        while (atual != null) {
            sb.append(atual.getItem().toString()).append("\n");
            atual = atual.getProximo();
        }
        return sb.toString();
    }
}

class Celula<T> {

    private final T item;
    private Celula<T> proximo;
    private Celula<T> anterior;

    public Celula() {
        this.item = null;
        setProximo(null);
        setAnterior(null);
    }

    public Celula(T item) {
        this.item = item;
        setProximo(null);
        setAnterior(null);
    }

    public Celula(T item, Celula<T> anterior, Celula<T> proximo) {
        this.item = item;
        this.anterior = anterior;
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

    public Celula<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(Celula<T> anterior) {
        this.anterior = anterior;
    }
}

public class Aplicacao {
    public static void main(String[] args) {
        Map<String, Medalhista> medalhistas = carregarMedalhistas("/tmp/medallists.csv");
        Scanner leitura = new Scanner(System.in);
        String entrada;
    
        ListaDuplamente<Medalhista> medalhistasLista = new ListaDuplamente<>();
    
        while (true) {
            entrada = leitura.nextLine();
    
            if (entrada.equalsIgnoreCase("FIM")) {
                break;
            }
    
            String[] dadosEntrada = entrada.split(";");
            String comando = dadosEntrada[0].trim();
    
            switch (comando.toUpperCase()) {
                case "INSERIR FINAL":
                    if (dadosEntrada.length > 1) {
                        String nomeFinal = dadosEntrada[1].trim();
                        Medalhista medalhistaFinal = medalhistas.get(nomeFinal);
                        if (medalhistaFinal != null) {
                            medalhistasLista.inserirFinal(medalhistaFinal);
                        } else {
                            System.out.println("Medalhista não encontrado: " + nomeFinal);
                        }
                    }
                    break;
    
                case "CONTEM SEQUENCIA":
                    if (dadosEntrada.length > 1) {
                        int quantidade = Integer.parseInt(dadosEntrada[1].trim());
                        ListaDuplamente<Medalhista> sequenciaLista = new ListaDuplamente<>();
    
                        for (int i = 0; i < quantidade; i++) {
                            String nomeSequencia = leitura.nextLine().trim();
                            Medalhista medalhistaSequencia = medalhistas.get(nomeSequencia);
                            if (medalhistaSequencia != null) {
                                sequenciaLista.inserirFinal(medalhistaSequencia);
                            } else {
                                System.out.println("Medalhista não encontrado: " + nomeSequencia);
                            }
                        }
    
                        boolean contem = medalhistasLista.contemSequencia(sequenciaLista);
                        if(contem){
                            System.out.println("LISTA DE MEDALHISTAS CONTEM SEQUENCIA"); 
                            System.out.println();
                        }
                        else{
                            System.out.println("LISTA DE MEDALHISTAS NAO CONTEM SEQUENCIA");
                            System.out.println();
                        }
                    }
                    break;
    
                case "MESCLAR":
                    if (dadosEntrada.length > 1) {
                        int quantidade = Integer.parseInt(dadosEntrada[1].trim());
                        ListaDuplamente<Medalhista> outraLista = new ListaDuplamente<>();
    
                        for (int i = 0; i < quantidade; i++) {
                            String nomeOutraLista = leitura.nextLine().trim();
                            Medalhista medalhistaOutraLista = medalhistas.get(nomeOutraLista);
                            if (medalhistaOutraLista != null) {
                                outraLista.inserirFinal(medalhistaOutraLista);
                            } else {
                                System.out.println("Medalhista não encontrado: " + nomeOutraLista);
                            }
                        }
    
                        medalhistasLista.mesclar(outraLista);
                        System.out.println("LISTA MESCLADA DE MEDALHISTAS");
                        System.out.println(medalhistasLista);
                        
                        medalhistasLista = new ListaDuplamente<>();
                    }
                    break;
    
                default:
                    System.out.println("Comando inválido.");
                    break;
            }
        }
        leitura.close();
    }
    
    private static Map<String, Medalhista> carregarMedalhistas(String csvFile) {
        Map<String, Medalhista> medalhistas = new HashMap<>();
        String line;
        String csvSplitBy = ",";
    
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(csvSplitBy);
    
                String nome = dados[0];
                String genero = dados[3];
                LocalDate nascimento = LocalDate.parse(dados[4]);
                String pais = dados[5];
    
                TipoMedalha tipoMedalha = TipoMedalha.valueOf(dados[1]);
                LocalDate dataMedalha = LocalDate.parse(dados[2]);
                String disciplina = dados[6];
                String evento = dados[7];
    
                Medalhista medalhista = medalhistas.getOrDefault(nome, new Medalhista(nome, genero, nascimento, pais));
                Medalha medalha = new Medalha(tipoMedalha, dataMedalha, disciplina, evento);
                medalhista.incluirMedalha(medalha);
    
                medalhistas.put(nome, medalhista);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return medalhistas;
    }
}    