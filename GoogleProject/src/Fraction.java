public class Fraction {
    private int nominator;
    private int denominator;


    public Fraction(int nominator, int denominator) {
        this.nominator = nominator;
        this.denominator = denominator;
    }


    @Override
    public String toString() {
        return String.format("%3d/%3d",nominator,denominator);
    }


    public void setNominator(int nominator) {
        this.nominator = nominator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public int getNominator() {
        return nominator;
    }

    public int getDenominator() {
        return denominator;
    }
}
