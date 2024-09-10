import java.util.Collection;

public abstract class Divida {
    // Atributos da dívida
    private String nomeDivida;
    private String [] nichos = {"Alimentação", "Compras", "Educação", "Lazer", "Pets", "Saúde", "Serviços", "Transporte"};
    private float valorDivida;
    private Integer[] parcelas = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Integer[] diaDivida = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
    private Integer[] mesDivida = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    public String getNomeDivida() {
        return nomeDivida;
    }

    public void setNomeDivida(String nomeDivida) {
        this.nomeDivida = nomeDivida;
    }

    public String[] getNichos() {
        return nichos;
    }

    public void setNichos(String[] nichos) {
        this.nichos = nichos;
    }

    public float getValorDivida() {
        return valorDivida;
    }

    public void setValorDivida(float valorDivida) {
        this.valorDivida = valorDivida;
    }

    public Integer[] getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer[] parcelas) {
        this.parcelas = parcelas;
    }

    public Integer[] getDiaDivida() {
        return diaDivida;
    }

    public void setDiaDivida(Integer[] diaDivida) {
        this.diaDivida = diaDivida;
    }

    public Integer[] getMesDivida() {
        return mesDivida;
    }

    public void setMesDivida(Integer[] mesDivida) {
        this.mesDivida = mesDivida;
    }

}
