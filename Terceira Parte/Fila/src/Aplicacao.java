import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
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

class Fila<E> {

	private Celula<E> frente;
	private Celula<E> tras;
	
	Fila() {
		
		Celula<E> sentinela = new Celula<E>();
		frente = tras = sentinela;
	}

    public Fila<E> dividir(){
        if(vazia()){
            throw new IllegalArgumentException("A fila está vazia!");
        }
        Fila<E> impar = new Fila<>();
        Fila<E> par = new Fila<>();
        
        boolean isPar = true;
        
        while(!vazia()){
            if(isPar){
                par.enfileirar(this.desenfileirar());
            }
            else{
                impar.enfileirar(this.desenfileirar());
            }
            isPar = !isPar; 
        }
        
        frente = impar.frente;
        tras = impar.tras;
        return par;
    }

    public boolean verificarExistencia(E item){
        if(vazia()){
            //throw new IllegalArgumentException("A fila está vazia!");
            return false;
        }
        Celula<E> aux = new Celula<>();
        aux = frente.getProximo();
        while(aux != null){
            if(aux.getItem().equals(item)){
                return true;
            }
            aux = aux.getProximo();
        }
        return false;
    }
	
	public boolean vazia() {
		
		return (frente == tras);
	}
	
	public void enfileirar(E item) {
		
		Celula<E> novaCelula = new Celula<E>(item);
		
		tras.setProximo(novaCelula);
		tras = tras.getProximo();
	}
	
	public E desenfileirar() {
		
		E item = null;
		Celula<E> primeiro;
		
		item = consultarPrimeiro();
		
		primeiro = frente.getProximo();
		frente.setProximo(primeiro.getProximo());
		
		primeiro.setProximo(null);
			
		if (primeiro == tras)
			tras = frente;
		
		return item;
	}
	
	public E consultarPrimeiro() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na fila!");
		}

		return frente.getProximo().getItem();

	}
	
	public void imprimir() {
        if (vazia()) {
            System.out.println("Fila vazia");
            return;
        }
    
        Celula<E> atual = frente.getProximo();

        while (atual != null) {
            System.out.println(atual.getItem()); 
            atual = atual.getProximo(); 
        }
    }

    public Celula<E> getFrente() {
        return frente;
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

public class Aplicacao {
    public static void main(String[] args) {
        String csvFile = "/tmp/medallists.csv";
        String line;
        String csvSplitBy = ",";
    
        Fila<Medalhista> filaOriginal = new Fila<>();
        Fila<Medalhista> filaMedalhistas = new Fila<>();
        
    
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(csvSplitBy);
                String nome = dados[0]; 
                String tipoMedalha = dados[1]; 
                LocalDate dataMedalha = LocalDate.parse(dados[2]); 
                String genero = dados[3]; 
                LocalDate nascimento = LocalDate.parse(dados[4]); 
                String pais = dados[5]; 
    
                Medalhista medalhista = new Medalhista(nome, genero, nascimento, pais);
                filaOriginal.enfileirar(medalhista); 
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    
        Scanner scanner = new Scanner(System.in);
        String comando;
    
        while (true) {
            comando = scanner.nextLine().trim();
            String[] partesComando = comando.split(" ", 2);
            String acao = partesComando[0];
    
            if (acao.equals("FIM")) {
                break;
            }
    
            switch (acao) {
                case "ENFILEIRAR":
                    if (partesComando.length > 1) {
                        String nomeMedalhista = partesComando[1];
                        for (Celula<Medalhista> aux = filaOriginal.getFrente().getProximo(); aux != null; aux = aux.getProximo()) {
                            if (aux.getItem().getName().equals(nomeMedalhista)) {
                                filaMedalhistas.enfileirar(aux.getItem());
                                break;
                            }
                        }
                    }
                    break;
    
                case "DESENFILEIRAR":
                    if (!filaMedalhistas.vazia()) {
                        Medalhista desenfileirado = filaMedalhistas.desenfileirar();
                        System.out.println("(DESENFILEIRADO) " + desenfileirado.getName());
                    } 
                    break;
    
                case "EXISTE":
                    if (partesComando.length > 1) {
                        String nomeBusca = partesComando[1];
                        boolean existe = filaMedalhistas.verificarExistencia(new Medalhista(nomeBusca, "", null, ""));
                        System.out.println(nomeBusca + " EXISTE NA FILA? " + (existe ? "SIM" : "NAO"));
                    }
                    break;
    
                case "DIVIDIR":
                    if (!filaMedalhistas.vazia()) {
                        Fila<Medalhista> filaDividida = filaMedalhistas.dividir();
                        System.out.println();
                        System.out.println("FILA ORIGINAL");
                        filaMedalhistas.imprimir();
                        System.out.println();
                        System.out.println("FILA NOVA");
                        filaDividida.imprimir();
                        System.out.println();
                        filaMedalhistas = new Fila<>();
                        filaDividida = new Fila<>();
                    } else {
                        System.out.println("Fila vazia para dividir.");
                    }
                    break;
    
                default:
                    System.out.println("Comando inválido");
            }
        }
    
        scanner.close();
    }
}

