import java.util.Arrays;

public class AppBinario {
    public static void main(String[] args) throws Exception {
        int n = 7_500_000;

        for (int i = n; i <= 2_000_000_000; i *= 2) {
            int[] vect = new int[i];

            for (int j = 0; j < i; j++) {
                vect[j] = j + 1;
            }

            medicoes(vect, vect[0], "Melhor Caso");

            medicoes(vect, vect[i / 2], "Caso Médio");

            medicoes(vect, -1, "Pior Caso");

            System.out.println();
        }
    }

    public static void medicoes(int[] vect, int valorPesquisa, String descricao) {
        long[] tempos = new long[5];
        long[] operacoes = new long[5];

        for (int j = 0; j < 5; j++) {
            long inicio = System.nanoTime();
            int numOp = pesquisaBinaria(valorPesquisa, vect, 0, vect.length - 1, 0);
            long fim = System.nanoTime();

            tempos[j] = fim - inicio;
            operacoes[j] = numOp;
        }

        Arrays.sort(tempos);
        long tempoMedio = (tempos[1] + tempos[2] + tempos[3]) / 3;

        System.out.println(descricao + " | N: " + vect.length + ", Operações: " + operacoes[0] + ", Tempo médio (ns): " + tempoMedio);
    }

    public static int pesquisaBinaria(int valor, int[] vect, int inicio, int fim, int cc) {
        if (inicio > fim) {
            return cc;
        }

        int meio = (inicio + fim) / 2;
        cc++;  

        if (vect[meio] == valor) {
            return cc;  
        } else if (vect[meio] < valor) {
            return pesquisaBinaria(valor, vect, meio + 1, fim, cc);
        } else {
            return pesquisaBinaria(valor, vect, inicio, meio - 1, cc);
        }
    }
}
