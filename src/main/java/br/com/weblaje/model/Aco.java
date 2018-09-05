package br.com.weblaje.model;

public enum Aco {

    CA25(500),
    CA50(500),
    CA60(600);

    /**
     * Unidade MPa
     */
    private int fyk;

    Aco(int fyk) {
        this.fyk = fyk;
    }

    public int getFyk() {
        return fyk;
    }
}
