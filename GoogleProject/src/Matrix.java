public class Matrix {
    private Fraction[][] matrix;
    private Fraction[] rowWeight;

    private int commonDenominator;

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
        commonDenominator=findCommonDenominator();
    }



    public void fillRowWeight(){
        for (int i = 0; i < rowWeight.length; i++) {
            rowWeight[i]=new Fraction(1,1);
        }
    }
    public void findRowWeight(){

        int commonDenominator=findCommonDenominator();
        int currentRowWeight=0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                currentRowWeight+=matrix[j][i].getNominator();
            }
            rowWeight[i].setNominator(currentRowWeight);
            rowWeight[i].setDenominator(commonDenominator);
            currentRowWeight=0;
        }
    }

    public void stackRowWeight(){
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i<j){
                    matrix[i][j].setNominator(matrix[i][j].getNominator()*rowWeight[i].getNominator());
                    matrix[i][j].setDenominator((matrix[i][j].getDenominator()*rowWeight[i].getDenominator()));
                }

            }

        }
        shortFractions();
    }

    public void shortFractions(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].getDenominator()!=commonDenominator){
                    matrix[i][j].setNominator(matrix[i][j].getNominator()/commonDenominator);
                    matrix[i][j].setDenominator(matrix[i][j].getDenominator()/commonDenominator);
                }
            }
        }
    }

    public void outputTerminalState(){
        int temp=0;
        int terminalTemp=0;
        int[] rowTemp=new int[rowWeight.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp+=matrix[i][j].getNominator();
            }
            if (temp==0){
                terminalTemp+=rowWeight[i].getNominator();
                rowTemp[i]=rowWeight[i].getNominator();
            }
            temp=0;
        }
        for (int i = 0; i < rowWeight.length; i++) {
            rowWeight[i].setDenominator(terminalTemp);
        }

        for (int i = 0; i < rowTemp.length; i++) {
            if (rowTemp[i]!=0){
                rowWeight[i].setNominator(rowTemp[i]);
                System.out.println(rowWeight[i]);
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

    private static Fraction multiplyFraction(Fraction f1,Fraction f2){
        return new Fraction(f1.getNominator()*f2.getNominator(),f1.getDenominator()*f2.getDenominator());
    }

    public Fraction addFractions(Fraction f1,Fraction f2){
        if (f1.getDenominator()==f2.getDenominator()){
            return new Fraction(f1.getNominator()+f2.getNominator(), f1.getDenominator());
        }else{
            int commonDenominator= f1.getDenominator()*f2.getDenominator();
            return new Fraction(f1.getNominator()*f2.getDenominator()+f2.getNominator()*f1.getDenominator(), commonDenominator);
        }

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

    public String rowWeightToString(){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < rowWeight.length; i++) {
            stringBuilder.append(rowWeight[i]+" ");
        }
        return stringBuilder.toString();
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

    public int findCommonDenominator() {
        int commonDenominator = 1;
        for (int i = 0; i < matrix.length-1; i++) {
            if (matrix[i][0].getDenominator() != 0&&matrix[i][0].getDenominator()!=matrix[i+1][0].getDenominator()) {
                commonDenominator *= matrix[i][0].getDenominator();
            }
        }
        return commonDenominator;
    }
}
