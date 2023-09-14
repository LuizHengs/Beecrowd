import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.lang.Exception;

public class Principal{

    public static void main (String[] args) throws outException{

        LSECircular energia = new LSECircular();
        Random rand = new Random();
        ArrayList<Integer> emes = new ArrayList<Integer>();

        int N = 0;
        int m = 1;
        Scanner sc = new Scanner(System.in);

        do {

            if (N != 0 && N != 1) {

                for (int i = 1; i <= N; i++)
                    energia.inserir(i, i);

                //System.out.println(trem.getConteudo());

                while (true) {
                    if (!(energia.desligarEnergia(N, m, emes))) {
                        while (energia.Tamanho() > 0)
                            energia.remover(1);
                        break;
                    }
                };


            }



            N = Integer.parseInt(sc.nextLine());
            m = 0;


        } while (N != 0);

        sc.close();

        for (int eme: emes)
            System.out.println(eme);

        energia = null;
    }

}

class outException extends Exception{

    private String code;

    public outException (String code, String message) {
        super(message);
        this.setCode(code);
    }

    public outException (String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

class LSECircular {

    private static String CODE = "Nao encontrado";

    class No {
        private int conteudo;
        private boolean ligado = true;
        private No proximo;
        private No anterior;

        public No() {
            proximo = null;
            anterior = null;
        }

        public int getConteudo() {
            return conteudo;
        }

        public No getProximo() {
            return proximo;
        }

        public No getAnterior() {
            return anterior;
        }

        public boolean isLigado() {
            return ligado;
        }

        public void setLigado(boolean ligado) {
            this.ligado = ligado;
        }

        public void setConteudo(int conteudo) {
            this.conteudo = conteudo;
        }

        public void setProximo(No proximo) {
            this.proximo = proximo;
        }

        public void setAnterior(No anterior) {
            this.anterior = anterior;
        }

    }

    private No cabeca;
    private No cauda;
    private int nElementos;

    public LSECircular() {
        cabeca = null;
        cauda = null;
        nElementos = 0;
    }

    public No getCabeca() {
        return cabeca;
    }

    public No getCauda() {
        return cauda;
    }

    public boolean vazia () {
        if (nElementos == 0)
            return true;
        else
            return false;
    }

    public int Tamanho() {
        return nElementos;
    }

    public No buscaPosicao (int posicao) throws outException {
        if (vazia())
            throw new outException(CODE, "Esta vazia!");
        if (posicao <= 0 || posicao > nElementos)
            throw new outException(CODE, "Este elemento nao existe aqui");

        No aux = new No();

        if (posicao <= nElementos/2) {
            aux = cabeca;
            for (int i = 1; i < posicao; i++)
                aux = aux.getProximo();
        }
        else{
            aux = cauda;
            for (int i = nElementos; i > posicao; i--)
                aux = aux.getAnterior();
        };
        return aux;

    }

    public int buscaPosicaoConteudo (int posicao) throws outException{
        return buscaPosicao(posicao).getConteudo();
    }

    public int buscaValor (int valor) throws outException {
        if (vazia())
            throw new outException(CODE, "Esta vazia!");

        No aux = new No();
        aux = cabeca;
        int posicao = 1;
        while (aux != null) {
            if (aux.getConteudo() == valor)
                return posicao;
            aux = aux.getProximo();
            posicao++;
        }

        if (aux == null)
            throw new outException(CODE, "Nao tem este valor aqui!");

        posicao = 0;
        return posicao;
    }

    private void inserirInicio(int conteudo) {
        No aux = new No();
        aux.conteudo = conteudo;

        if (!vazia()) {
            aux.setProximo(cabeca);
            aux.setAnterior(cauda);

            cabeca.setAnterior(aux);
            cauda.setProximo(aux);
        } else {
            cauda = aux;

        }

        cabeca = aux;

        nElementos++;

        aux = null;

    }

    private void inserirFinal(int conteudo) {
        No aux = new No();
        aux.conteudo = conteudo;

        aux.setAnterior(cauda);
        aux.setProximo(cabeca);

        cauda.setProximo(aux);
        cabeca.setAnterior(aux);
        cauda = aux;

        nElementos++;

        aux = null;

    }

    private void inserirMeio (int conteudo, int posicao) throws outException{

        No aux = new No();
        No trem;
        aux.conteudo = conteudo;
        //
        if (vazia())
            throw new outException(CODE, "Esta vazia!");
        if (posicao <= 0 || posicao > nElementos + 1)
            throw new outException(CODE, "Este elemento nao existe aqui");

        if (posicao <= nElementos/2 + 0.5) {
            trem = cabeca;
            for (int i = 1; i < posicao - 1; i++)
                trem = trem.getProximo();
        }
        else{
            trem = cauda;
            for (int i = nElementos; i > posicao - 1; i--)
                trem = trem.getAnterior();
        };
        //

        if (nElementos == 1) {
            trem.setProximo(aux);
            trem.setAnterior(aux);
        } else {
            aux.setProximo(trem.getProximo());
            aux.setAnterior(trem);
        }
        trem.getProximo().setAnterior(aux);
        trem.setProximo(aux);


        nElementos++;

        if (posicao == nElementos)
            cauda = aux;

        aux = null;

    }

    public void inserir (int conteudo, int posicao) throws outException {
        if (vazia() || posicao == 1)
            inserirInicio(conteudo);
        else if (posicao == nElementos + 1)
            inserirFinal(conteudo);
        else
            inserirMeio(conteudo, posicao);
    }

    public int remover (int posicao) throws outException {
        No aux;

        if (posicao <= nElementos/2) {
            aux = cabeca;
            for (int i = 1; i < posicao; i++)
                aux = aux.getProximo();
        }
        else{
            aux = cauda;
            for (int i = nElementos; i > posicao; i--)
                aux = aux.getAnterior();
        };

        int valorRemovido = aux.conteudo;



        if (nElementos > 1) {
            aux.getProximo().setAnterior(aux.getAnterior());
            aux.getAnterior().setProximo(aux.getProximo());
        }
        if (posicao == 1)
            cabeca = aux.getProximo();
        if (posicao == nElementos)
            cauda = aux.getAnterior();
        aux = null;

        nElementos--;

        return valorRemovido;
    }


    public boolean desligarEnergia  (int N, int m, ArrayList<Integer> emes) throws outException {
        No trem = new No();
        No ultimo = new No();
        int i;
        //int somatorio = 0;
        int somatorioLigados = N;

        //for (int k = 1; k <= N; k++)
        //  somatorio += k;

        while (++m > 0) {

            trem = cabeca;

            remover(buscaValor(trem.getConteudo()));
            ultimo = cabeca;

            //System.out.println("Agora o conteudo da cabeça e: " + cabeca.getConteudo());
            //System.out.println("Agora a posicao que tem o valor do conteudo da cabeca e: " + buscaValor(cabeca.getConteudo()));

            while (Tamanho() != 1) {

                for (int j = 1; j <= m; j++)
                    ultimo = ultimo.getProximo();

                trem = ultimo.getAnterior();

                remover(buscaValor(trem.getConteudo()));

                //if (i == somatorioLigados)
                   // ultimo = ultimo.getProximo();

            }

            //System.out.println(ultimo.getConteudo());
            ultimo = cabeca;

            if (ultimo.getConteudo() == 13) {
                emes.add(m);
                return false;
            }
            while (Tamanho() > 0)
                remover(1);

            for (int k = 1; k <= N; k++)
                inserir(k, k);

        }
        return true;
    }
}

