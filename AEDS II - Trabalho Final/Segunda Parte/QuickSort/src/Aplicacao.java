import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;


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

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Medalha[] getMedals() {
        return medals;
    }

    public void setMedals(Medalha[] medals) {
        this.medals = medals;
    }

    public int getMedalCount() {
        return medalCount;
    }

    public void setMedalCount(int medalCount) {
        this.medalCount = medalCount;
    }

    public int compareTo(Medalhista outro) {
        return this.birthDate.compareTo(outro.birthDate);
    }

    /**
     * Deve retornar os dados pessoais do medalhista, sem as medalhas, conforme
     * especificado no enunciado da atividade.
     * 
     * @return String de uma linha, com os dados do medalhista, sem dados da
     *         medalha.
     */
    public String toString() {
        return name + " (" + gender + "), " + birthDate + ", " + country;
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

class Pais implements Comparable<Pais> {
    private String nome;
    private Medalhista[] medalhistas;

    public Pais(String nome) {
        this.nome = nome;
        this.medalhistas = new Medalhista[1000];
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void incluirMedalha(Medalhista medalhista) {
        for (int i = 0; i < medalhistas.length; i++) {
            if (medalhistas[i] == null) {
                medalhistas[i] = medalhista;
                return;
            }
        }
        System.out.println("Limite de medalhistas atingido para o país: " + nome);
    }

    public int totalDeMedalhas() {
        int totalMedalhas = 0;
        for (Medalhista medalhista : medalhistas) {
            if (medalhista != null) {
                totalMedalhas += medalhista.totalMedalhas();
            }
        }
        return totalMedalhas;
    }

    public int totalDeMedalhas(TipoMedalha tipo) {
        int total = 0;
        for (Medalhista medalhista : medalhistas) {
            if (medalhista != null) {
                total += medalhista.totalDeMedalhas(tipo);
            }
        }
        return total;
    }

    public int compareTo(Pais outro) {
        return Integer.compare(outro.totalDeMedalhas(), this.totalDeMedalhas());
    }

    public String relatorioMedalhistas() {
        StringBuilder relatorio = new StringBuilder("Medalhistas do país: " + nome + "\n");
        for (Medalhista medalhista : medalhistas) {
            if (medalhista != null) {
                relatorio.append(medalhista.toString()).append("\n");
            }
        }
        return relatorio.toString().trim();
    }

    @Override
    public String toString() {
        return "Pais [nome=" + nome + ", total de medalhistas="
                + (int) Arrays.stream(medalhistas).filter(m -> m != null).count() + "]";
    }
}

class Evento {
    private String event;
    private String discipline;
    private int quantMedalhistas;
    private Medalhista[] medallists;

    private static final int MAX_MEDALHISTAS = 100;

    public Evento(String event, String discipline) {
        this.event = event;
        this.discipline = discipline;
        this.medallists = new Medalhista[MAX_MEDALHISTAS];
        this.quantMedalhistas = 0;
    }

    public void incluirMedalhista(Medalhista medalhista) {
        if (quantMedalhistas < MAX_MEDALHISTAS) {
            medallists[quantMedalhistas] = medalhista;
            quantMedalhistas++;
        } else {
            System.out.println("Limite de medalhistas atingido para o evento: " + event);
        }
    }

    public int compareTo(Evento outroEvento) {
        return Integer.compare(outroEvento.quantMedalhistas, this.quantMedalhistas);
    }

    public String relatorioMedalhista() {
        StringBuilder relatorio = new StringBuilder("Relatório de Medalhistas no evento: " + event + "\n");
        for (int i = 0; i < quantMedalhistas; i++) {
            if (medallists[i] != null) {
                relatorio.append(medallists[i].toString()).append("\n");
            }
        }
        return relatorio.toString().trim();
    }

    @Override
    public String toString() {
        return "Evento [event=" + event + ", discipline=" + discipline + ", quantMedalhistas=" + quantMedalhistas
                + ", medallists=" + Arrays.toString(medallists) + "]";
    }
}

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

public class Aplicacao {
    public static void main(String[] args) {
        Map<String, Pais> paisesMap = carregarPaises("/tmp/medallists.csv");
        Scanner leitura = new Scanner(System.in);
        
        int numPaises = Integer.parseInt(leitura.nextLine());
    
        Pais[] paisesOrdenar = new Pais[numPaises];
        for (int i = 0; i < numPaises; i++) {
            String nomePais = leitura.nextLine();
            Pais pais = paisesMap.get(nomePais);
            if (pais == null) {
                i--;
                continue;
            }
            paisesOrdenar[i] = pais;
        }
        leitura.close();
    
        Quicksort<Pais> quicksort = new Quicksort<>(paisesOrdenar);
        quicksort.setComparador(new Comparator<Pais>() {
            @Override
            public int compare(Pais p1, Pais p2) {
                int comparacaoOuros = Integer.compare(p2.totalDeMedalhas(TipoMedalha.OURO), p1.totalDeMedalhas(TipoMedalha.OURO));
                if (comparacaoOuros != 0) {
                    return comparacaoOuros;
                }
                
                int comparacaoPratas = Integer.compare(p2.totalDeMedalhas(TipoMedalha.PRATA), p1.totalDeMedalhas(TipoMedalha.PRATA));
                if (comparacaoPratas != 0) {
                    return comparacaoPratas;
                }
                
                return Integer.compare(p2.totalDeMedalhas(TipoMedalha.BRONZE), p1.totalDeMedalhas(TipoMedalha.BRONZE)); // Ordena por bronze
            }
        });
        quicksort.ordenar();
    
        for (Pais pais : paisesOrdenar) {
            if (pais != null) {
                int totalOuros = pais.totalDeMedalhas(TipoMedalha.OURO);
                int totalPratas = pais.totalDeMedalhas(TipoMedalha.PRATA);
                int totalBronzes = pais.totalDeMedalhas(TipoMedalha.BRONZE);
                int totalMedalhas = totalOuros + totalPratas + totalBronzes;
    
                System.out.printf("%s: %02d ouros %02d pratas %02d bronzes. Total: %02d medalhas.%n",
                                  pais.getNome(), totalOuros, totalPratas, totalBronzes, totalMedalhas);
            }
        }
        
        log("_quicksort.txt", quicksort);
    }

    private static Map<String, Pais> carregarPaises(String csvFile) {
        Map<String, Medalhista> medalhistas = new HashMap<>();
        Map<String, Pais> paises = new HashMap<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
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

                Medalhista medalhista = medalhistas.get(nome);
                if (medalhista == null) {
                    medalhista = new Medalhista(nome, genero, nascimento, paisNome);
                    medalhistas.put(nome, medalhista);
                }

                Medalha medalha = new Medalha(tipoMedalha, dataMedalha, disciplina, eventoNome);
                medalhista.incluirMedalha(medalha);

                Pais pais = paises.get(paisNome);
                if (pais == null) {
                    pais = new Pais(paisNome);
                    paises.put(paisNome, pais);
                }
                pais.incluirMedalha(medalhista);
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

    private static void log(String nome, IOrdenator<?> ordenador) {

        try {
          String matricula = "729488";
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
      