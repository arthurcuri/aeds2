import java.util.Arrays;

public class AppB {
    public static void main(String[] args) throws Exception {
        int n = 15625;
        final long LIM_TEMPO_NANOSEGUNDOS = 50_000_000_000L;

        for (int i = n; i <= 2000000000; i *= 2) {
            long[] tempos = new long[5];
            //as operacoes nao precisariam ser armazenadas em vetor, pois as mesmas nao variam a resposta.
            //mantive assim apenas para realizar um "teste" que esta comentado, porem o mesmo nao afeta o programa.
            long[] operacoes = new long[5];
            int[] vet = new int[i];

            for (int j = 0; j < 5; j++) {

                long inicio = System.nanoTime();
                long numOp = codigoB(vet);
                long fim = System.nanoTime();

                tempos[j] = fim - inicio;
                operacoes[j] = numOp;
   
                
            }
            Arrays.sort(tempos);
            long tempoMedio = (tempos[1] + tempos[2] + tempos[3]) / 3;
            //System.out.println("Tempo 1: " + tempos[1] + ", Tempo 2: " + tempos[2] + ", Tempo 3: " + tempos[3]);
            //System.out.println("Operacoes 1: " + operacoes[1] + ", Operacoes 2: " + operacoes[2] + ", Operacoes 3: " + operacoes[3]);
            System.out.println("N: " + i + ", Operações: " + operacoes[0] + ", Tempo médio (ns): " + tempoMedio);

            if (tempoMedio > LIM_TEMPO_NANOSEGUNDOS) {
                System.out.println("Ultrapassou o tempo medio de 50s");
                break;
            }
        }
    }

    //troquei o void por int para retornar a contagem de operacoes
    public static long codigoB(int[] vet) {
        long operacoesB = 0;
        for (int i = 0; i < vet.length; i += 2) {
            for (int j = i; j < (vet.length / 2); j++) {
                vet[i] += vet[j];
                operacoesB++;
            }
        }
        //retorno da contagem
        return operacoesB;
    }
}