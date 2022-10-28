public class Matrix {
    private Fraction[][] matrix;
    private Fraction[] rowWeight;

    public Matrix(int[][] matrix) {
        this.matrix = new Fraction[matrix.length][matrix[0].length];
        this.rowWeight=new Fraction[matrix.length];
        this.fillRowWeight();
        int prob = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                prob += matrix[i][j];
            }
            for (int j = 0; j < matrix[0].length; j++) {
                this.matrix[i][j] = new Fraction(matrix[i][j], prob);
            }
            prob = 0;
        }
    }

    private static Fraction multiplyFraction(Fraction f1,Fraction f2){
        return new Fraction(f1.getNominator()*f2.getNominator(),f1.getDenominator()*f2.getDenominator());
    }

    public void fillRowWeight(){
        for (int i = 0; i < rowWeight.length; i++) {
            rowWeight[i]=new Fraction(1,1);
        }
    }
    public void findRowWeight(){
        setAllDenominatorsToCommonDenomionator();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0].getDenominator()!=0){
                for (int j = 0; j < matrix[0].length; j++) {
                    rowWeight[i]=multiplyFraction(matrix[j][i],rowWeight[i]);
                }
            }
        }
    }

    public String rowWeightToString(){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < rowWeight.length; i++) {
            stringBuilder.append(rowWeight[i]+" ");
        }
        return stringBuilder.toString();
    }
    public int findCommonDenominator() {
        int commonDenominator = 1;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0].getDenominator() != 0) {
                commonDenominator *= matrix[i][0].getDenominator();
            }
        }
        return commonDenominator;
    }

    public void setAllDenominatorsToCommonDenomionator() {
        int commonDenominator = findCommonDenominator();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0].getDenominator() != 0) {
                int multiplier = commonDenominator / matrix[i][0].getDenominator();
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j].setNominator(matrix[i][j].getNominator() * multiplier);
                    matrix[i][j].setDenominator(matrix[i][j].getDenominator() * multiplier);
                }
            }

        }
    }

    public void setFractionWeights() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].getNominator() != 0) {

                }
            }
        }
    }

    public int findSumOfNominatorOfCyclics() {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].getNominator() != 0 && matrix[j][i].getNominator() != 0) {

                }
            }
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                stringBuilder.append("|");
                stringBuilder.append(matrix[i][j].toString());
                stringBuilder.append("|  ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
