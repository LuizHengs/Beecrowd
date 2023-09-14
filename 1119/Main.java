import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Exception;


class Main {

    public static void main (String[] args) throws outException {

        LDECircular candidatos = new LDECircular();
        int k, N, m, i, j;
        int indice = 0;
        Scanner sc = new Scanner(System.in);
        ArrayList<ArrayList<Integer>> horarios = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> antihorarios = new ArrayList<ArrayList<Integer>>();



        do {

            N = sc.nextInt();
            k = sc.nextInt();
            m = sc.nextInt();
            sc.nextLine();

            if (k > 0 && N > 0 && m > 0) {
                horarios.add(new ArrayList<Integer>());
                antihorarios.add(new ArrayList<Integer>());
                for (int l = 1; l <= N; l++)
                    candidatos.inserir(l, l);

                //candidatos.remover(8);

                candidatos.remocaoDupla(k, N, m, horarios, antihorarios, indice);

                for (i = 0, j = 0; (i < horarios.get(indice).size() || j < antihorarios.get(indice).size()); i++, j++) {
                    System.out.printf("%3d", horarios.get(indice).get(i));
                    if (horarios.get(indice).get(i) != antihorarios.get(indice).get(j))
                        System.out.printf("%3d", antihorarios.get(indice).get(j));
                    if (i < horarios.get(indice).size() - 1)
                        System.out.print(',');
                    else
                        System.out.println();
                }

            }

            indice++;
        } while (!(k == 0 && m == 0 && N == 0));

        sc.close();
        /*
        for (int a = 0; a < indice - 1; a++) {
            for (i = 0, j = 0; (i < horarios.get(a).size() || j < antihorarios.get(a).size()); i++, j++) {
                System.out.printf("%3d", horarios.get(a).get(i));
                if (horarios.get(a).get(i) != antihorarios.get(a).get(j))
                    System.out.printf("%3d", antihorarios.get(a).get(j));
                if (i < horarios.get(a).size() - 1)
                    System.out.print(',');
                else
                    System.out.println();
            }
        }

         */


    }

}

class outException extends Exception{

    private String code;

    public outException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public outException(String code, String message, Throwable cause) {
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

class LDECircular {

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

    public LDECircular() {
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
        /*if (posicao <= 0 || posicao > nElementos)
            throw new outException(CODE, "Este elemento nao existe aqui");
        */
        No aux = new No();

        if (posicao > nElementos) {
            posicao -= nElementos;
            posicao = nElementos - posicao;
        }

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

    public int buscaPosicaoConteudo (int posicao) throws outException {
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
            // System.out.println("aux.getConteudo() e: " + aux.getConteudo());
            // System.out.println("posicao e: " + posicao);
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

    private void inserirMeio (int conteudo, int posicao) throws outException {

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



        if (nElementos >= 1) {
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

    public void remocaoDupla (int k, int N, int m, ArrayList<ArrayList<Integer>> horarios, ArrayList<ArrayList<Integer>> antihorarios, int indice) throws outException {

        No tremF = new No();
        No tremC = new No();

        int i = k;
        int j = N + m - 1;
        int a = 0;

        //if (m + k <= N)
        //    a = 1;

        tremF = cabeca;
        tremC = cauda;


        while (!(vazia())) {

            if (N > 1 && a == 0) {
                for (int l = 1; l < k; l++)
                    tremF = tremF.getProximo();

                for (int l = 1; l < m; l++)
                    tremC = tremC.getAnterior();
                a = 1;
            } else {
                if (N > 1) {
                    for (int l = 1; l <= k; l++)
                        tremF = tremF.getProximo();

                    for (int l = 1; l <= m; l++)
                        tremC = tremC.getAnterior();
                }
            }

            horarios.get(indice).add(tremF.getConteudo());
            antihorarios.get(indice).add(tremC.getConteudo());

            if (N != 1) {
                if (cabeca == tremF || cabeca == tremC)
                    cabeca = cabeca.getProximo();

                if (cauda == tremF || cauda == tremC)
                    cauda = cauda.getAnterior();
            }
            if (N == 1) {
                nElementos--;
            } else if (tremF.getProximo() != tremC && tremC.getProximo() != tremF) {
                if (tremF != tremC) {
                    tremF.getProximo().setAnterior(tremF.getAnterior());
                    tremF.getAnterior().setProximo(tremF.getProximo());
                    nElementos--;
                }
                tremC.getProximo().setAnterior(tremC.getAnterior());
                tremC.getAnterior().setProximo(tremC.getProximo());
                nElementos--;
            } else if (tremF.getProximo() == tremC) {
                if (tremF == tremC) {
                    tremC.getProximo().setAnterior(tremC.getAnterior());
                    tremC.getAnterior().setProximo(tremC.getProximo());
                    nElementos--;
                } else {
                    tremF.getAnterior().setProximo(tremF.getProximo().getProximo());
                    tremF.setProximo(tremF.getProximo().getProximo());
                    tremC.getProximo().setAnterior(tremC.getAnterior().getAnterior());
                    tremC.setAnterior(tremC.getAnterior().getAnterior());
                    nElementos--;
                    nElementos--;
                }
            } else {
                if (tremF == tremC) {
                    tremC.getProximo().setAnterior(tremC.getAnterior());
                    tremC.getAnterior().setProximo(tremC.getProximo());
                    nElementos--;
                } else {
                    tremF.getProximo().setAnterior(tremF.getAnterior().getAnterior());
                    tremF.setAnterior(tremF.getAnterior().getAnterior());
                    tremC.getAnterior().setProximo(tremC.getProximo().getProximo());
                    tremC.setProximo(tremC.getProximo().getProximo());
                    nElementos--;
                    nElementos--;
                }
            }

        /*
            System.out.println("TremF.getConteudo() e: " + tremF.getConteudo());
            System.out.println("TremC.getConteudo() e: " + tremC.getConteudo());
            for (int l = 0; l < horarios.size(); l++)
                System.out.println("horarios e: " + horarios.get(l));
            for (int l = 0; l < antihorarios.size(); l++)
                System.out.println("antihorarios e: " + antihorarios.get(l));
            //System.out.println("Posicao do valor e: " + buscaValor(tremF.getConteudo()));
            //System.out.println("Posicao do valor e: " + buscaValor(tremC.getConteudo()));

         */



        }


    }
}