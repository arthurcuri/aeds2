import java.io.BufferedReader;
import java.io.BufferedWriter; // Import necessário para escrever no arquivo
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter; // Import necessário para escrever no arquivo
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

interface IOrdenator<T> {

  public T[] ordenar();

  public void setComparador(Comparator<T> comparador);

  public int getComparacoes();

  public int getMovimentacoes();

  public double getTempoOrdenacao();
}

class Quicksort<T> implements IOrdenator<T> {

  private T[] array;
  private Comparator<T> comparador;
  private int comparacoes;
  private int movimentacoes;
  private long tempoInicial;

  public Quicksort(T[] array) {
    this.array = array;
    this.comparacoes = 0;
    this.movimentacoes = 0;
  }

  @Override
  public T[] ordenar() {
    tempoInicial = System.nanoTime();
    quicksort(0, array.length - 1);
    return array;
  }

  private void quicksort(int esquerda, int direita) {
    if (esquerda < direita) {
      int posicaoPivo = particionar(esquerda, direita);
      quicksort(esquerda, posicaoPivo - 1);
      quicksort(posicaoPivo + 1, direita);
    }
  }

  private int particionar(int esquerda, int direita) {
    T pivo = array[direita];
    int i = esquerda - 1;
    for (int j = esquerda; j < direita; j++) {
      comparacoes++;
      if (comparador.compare(array[j], pivo) < 0) {
        i++;
        trocar(i, j);
      }
    }
    trocar(i + 1, direita);
    return i + 1;
  }

  private void trocar(int i, int j) {
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
    movimentacoes++;
  }

  @Override
  public void setComparador(Comparator<T> comparador) {
    this.comparador = comparador;
  }

  @Override
  public int getComparacoes() {
    return comparacoes;
  }

  @Override
  public int getMovimentacoes() {
    return movimentacoes;
  }

  @Override
  public double getTempoOrdenacao() {
    return (System.nanoTime() - tempoInicial) / 1_000_000.0;
  }
}

/**
 * Classe Medalhista: representa um medalhista olímpico e sua coleção de
 * medalhas nas Olimpíadas de Paris 2024.
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
    name = nome;
    gender = genero.toUpperCase();
    birthDate = nascimento;
    country = pais;
    medals = new Medalha[MAX_MEDALHAS];
    medalCount = 0;
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
      medals[medalCount++] = medalha;
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
   * parâmetro.
   * Em caso de não possuir medalhas deste tipo, a resposta deve ser
   * "Nao possui medalha de TIPO".
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

  /**
   * Retorna um resumo das medalhas do medalhista.
   *
   * @return String com a quantidade de medalhas de ouro, prata e bronze.
   */
  public String relatorioResumoMedalhas() {
    StringBuilder rel = new StringBuilder();
    if (ouros() > 0) {
      rel.append("Quantidade de medalhas de ouro: ").append(ouros()).append("\n");
    }
    if (pratas() > 0) {
      rel.append("Quantidade de medalhas de prata: ").append(pratas()).append("\n");
    }
    if (bronzes() > 0) {
      rel.append("Quantidade de medalhas de bronze: ").append(bronzes()).append("\n");
    }
    return rel.toString();
  }

  /**
   * Retorna o nome do medalhista.
   *
   * @return o nome do medalhista
   */
  public String getNome() {
    return name;
  }

  /**
   * Retorna o nome do país do medalhista (conforme arquivo original em inglês).
   *
   * @return String contendo o nome do país do medalhista
   */
  public String getPais() {
    return country;
  }

  /**
   * Retorna uma cópia da data de nascimento do medalhista.
   *
   * @return LocalDate com a data de nascimento do medalhista
   */
  public LocalDate getNascimento() {
    return LocalDate.from(birthDate);
  }

  /**
   * Retorna a quantidade de medalhas de ouro do medalhista.
   *
   * @return número de medalhas de ouro
   */
  public int ouros() {
    int ouros = 0;
    for (int i = 0; i < medalCount; i++) {
      if (medals[i].getTipo() == TipoMedalha.OURO) {
        ouros++;
      }
    }
    return ouros;
  }

  /**
   * Retorna a quantidade de medalhas de prata do medalhista.
   *
   * @return número de medalhas de prata
   */
  public int pratas() {
    int pratas = 0;
    for (int i = 0; i < medalCount; i++) {
      if (medals[i].getTipo() == TipoMedalha.PRATA) {
        pratas++;
      }
    }
    return pratas;
  }

  /**
   * Retorna a quantidade de medalhas de bronze do medalhista.
   *
   * @return número de medalhas de bronze
   */
  public int bronzes() {
    int bronzes = 0;
    for (int i = 0; i < medalCount; i++) {
      if (medals[i].getTipo() == TipoMedalha.BRONZE) {
        bronzes++;
      }
    }
    return bronzes;
  }

  /**
   * Retorna uma string com os dados pessoais do medalhista, sem as medalhas.
   *
   * @return String de uma linha, com os dados do medalhista
   */
  @Override
  public String toString() {
    String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(birthDate); // formata a data em DD/MM/AAAA

    return name + ", " + gender + ". Nascimento: " + dataFormatada + ". Pais: " + country;
  }

  /**
   * Compara este medalhista com outro pelo nome, em ordem alfabética.
   *
   * @param m o medalhista a ser comparado
   * @return valor negativo, zero ou positivo conforme este nome seja menor, igual
   *         ou maior que o nome do outro medalhista
   */
  @Override
  public int compareTo(Medalhista m) {
    return name.toUpperCase().compareTo(m.name.toUpperCase());
  }
}

class Pais implements Comparable<Pais> {
  private String nome;
  private Set<Medalhista> medalhistas;

  public Pais(String nome) {
    this.nome = nome;
    this.medalhistas = new HashSet<>();
  }

  public String getNome() {
    return nome;
  }

  public void adicionarMedalhista(Medalhista medalhista) {
    this.medalhistas.add(medalhista);
  }

  public int getOuros() {
    int ouros = 0;
    for (Medalhista m : medalhistas) {
      ouros += m.ouros();
    }
    return ouros;
  }

  public int getPratas() {
    int pratas = 0;
    for (Medalhista m : medalhistas) {
      pratas += m.pratas();
    }
    return pratas;
  }

  public int getBronzes() {
    int bronzes = 0;
    for (Medalhista m : medalhistas) {
      bronzes += m.bronzes();
    }
    return bronzes;
  }

  public int getTotalMedalhas() {
    return getOuros() + getPratas() + getBronzes();
  }

  @Override
  public int compareTo(Pais outro) {
    if (this.getOuros() != outro.getOuros()) {
      return outro.getOuros() - this.getOuros();
    } else if (this.getPratas() != outro.getPratas()) {
      return outro.getPratas() - this.getPratas();
    } else {
      return outro.getBronzes() - this.getBronzes();
    }
  }

  @Override
  public String toString() {
    return String.format("%s: %02d ouros %02d pratas %02d bronzes Total: %02d medalhas.",
        nome, getOuros(), getPratas(), getBronzes(), getTotalMedalhas());
  }
}

/**
 * Enumerador para medalhas de ouro, prata e bronze.
 */
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

  /**
   * Cria uma medalha com os dados do parâmetro. Nenhum dado é validado.
   *
   * @param tipo       Tipo da medalha
   * @param data       Data de obtenção da medalha
   * @param disciplina Disciplina da medalha
   * @param evento     Evento da medalha
   */
  public Medalha(TipoMedalha tipo, LocalDate data, String disciplina, String evento) {
    metalType = tipo;
    medalDate = data;
    discipline = disciplina;
    event = evento;
  }

  /**
   * Retorna o tipo de medalha, conforme o enumerador.
   *
   * @return TipoMedalha (enumerador) com o tipo/cor desta medalha
   */
  public TipoMedalha getTipo() {
    return metalType;
  }

  /**
   * Retorna uma string com o "relatório" da medalha.
   *
   * @return String com os dados da medalha
   */
  @Override
  public String toString() {
    String dataFormatada = DateTimeFormatter.ofPattern("dd/MM/YYYY").format(medalDate); // formata a data em DD/MM/AAAA

    return metalType + " - " + discipline + " - " + event + " - " + dataFormatada;
  }
}

/**
 * Classe Aplicacao: contém os métodos principais da aplicação que gerencia os
 * medalhistas e suas medalhas.
 */
public class AplicacaoQuick {

  /**
   * Método principal que controla o fluxo de interação com o usuário e gerencia a
   * exibição de relatórios de medalhistas.
   *
   * @param args Argumentos de linha de comando
   */
  public static void main(String[] args) {
    Map<String, Pais> paisesMap = carregarPaises("/tmp/medallists.csv");
    Scanner leitura = new Scanner(System.in);
    int numPaises = Integer.parseInt(leitura.nextLine());

    Pais[] paisesOrdenar = new Pais[numPaises];
    for (int i = 0; i < numPaises; i++) {
      String nomePais = leitura.nextLine();
      Pais pais = paisesMap.get(nomePais);
      if (pais == null) {
        System.out.println("País não encontrado");
        continue;
      }
      paisesOrdenar[i] = pais;
    }
    leitura.close();

    // Ordenar os países usando Quicksort
    Quicksort<Pais> quicksort = new Quicksort<>(paisesOrdenar);
    quicksort.setComparador(Pais::compareTo);
    quicksort.ordenar();

    // Imprimir os países ordenados
    for (Pais pais : paisesOrdenar) {
      if (pais != null) {
        System.out.println(pais);
      }
    }

    // Gerar o arquivo de log
    log("_quicksort.txt", quicksort);
  }

  private static Map<String, Pais> carregarPaises(String csvFile) {
    Map<String, Medalhista> medalhistas = new HashMap<>();
    Map<String, Pais> paises = new HashMap<>();
    String line;
    String csvSplitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      br.readLine(); // Pula a primeira linha do arquivo (cabeçalho)
      while ((line = br.readLine()) != null) {
        String[] dados = line.split(csvSplitBy);

        String nome = dados[0];
        String genero = dados[3];
        LocalDate nascimento = LocalDate.parse(dados[4]);
        String paisNome = dados[5];

        TipoMedalha tipoMedalha = TipoMedalha.valueOf(dados[1]);
        LocalDate dataMedalha = LocalDate.parse(dados[2]);
        String disciplina = dados[6];
        String eventoNome = dados[7];

        // Criar ou obter o medalhista
        Medalhista medalhista = medalhistas.get(nome);
        if (medalhista == null) {
          medalhista = new Medalhista(nome, genero, nascimento, paisNome);
          medalhistas.put(nome, medalhista);
        }

        // Criar a medalha e adicioná-la ao medalhista
        Medalha medalha = new Medalha(tipoMedalha, dataMedalha, disciplina, eventoNome);
        medalhista.incluirMedalha(medalha);

        // Criar ou obter o país
        Pais pais = paises.get(paisNome);
        if (pais == null) {
          pais = new Pais(paisNome);
          paises.put(paisNome, pais);
        }

        // Adicionar o medalhista ao país
        pais.adicionarMedalhista(medalhista);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Arquivo não encontrado: " + csvFile);
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Erro ao ler o arquivo: " + csvFile);
      e.printStackTrace();
    }

    return paises;
  }

  /**
   * Registra os dados de ordenação em um arquivo de log.
   *
   * @param nome      Sufixo do nome do arquivo
   * @param ordenador O objeto ordenador utilizado
   */
  private static void log(String nome, IOrdenator<?> ordenador) {

    // Criação do arquivo de log
    try {
      String matricula = "833005"; // Substitua pelo seu número de matrícula
      String nomeArquivo = matricula + nome;
      BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo));
      writer.write(matricula + "\t" + ordenador.getTempoOrdenacao() + "\t" + ordenador.getComparacoes() + "\t"
          + ordenador.getMovimentacoes());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
