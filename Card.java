public abstract class Card {
    protected int limiteCartao;
    protected Integer[] vencimento = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};

    public int getLimiteCartao() {
        return limiteCartao;
    }

    public void setLimiteCartao(int limiteCartao) {
        this.limiteCartao = limiteCartao;
    }

    public Integer[] getVencimento() {
        return vencimento;
    }

    public void setVencimento(Integer[] vencimento) {
        this.vencimento = vencimento;
    }
}
