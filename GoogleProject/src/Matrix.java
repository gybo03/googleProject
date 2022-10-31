public class Matrix {
    private Fraction[][] matrix;
    private Fraction[] stateWeightArray;

    private boolean[] terminalStateArray;

    private boolean[] transientStateArray;

    private final int commonDenominator;
    
    private final int length;

    public Matrix(int[][] matrix) {
        this.length= matrix.length;
        this.matrix = new Fraction[length][length];
        this.stateWeightArray = new Fraction[length];
        this.terminalStateArray = new boolean[length];
        this.transientStateArray = new boolean[length];

        matrix = clearMainDiagonal(matrix);
        matrix = clearTransientStates(matrix);

        this.fillDenominators(matrix);
        this.fillStateWeight();

        commonDenominator = findCommonDenominator();

        this.findStateWeight();
    }

    private void fillDenominators(int[][] matrix) {
        int prob = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j] >= 0) {
                    prob += matrix[i][j];
                }

            }
            for (int j = 0; j < length; j++) {
                if (matrix[i][j] >= 0) {
                    this.matrix[i][j] = new Fraction(matrix[i][j], prob);
                } else {
                    this.matrix[i][j] = new Fraction(0, prob);
                }
            }
            prob = 0;
        }
    }

    //<editor-fold desc="cleaning">
    public int[][] clearTransientStates(int[][] matrix) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (isStateTransientv1(j, matrix) && matrix[i][j] != 0) {
                    matrix[i][entryOfTransientStatev1(j, matrix)] += matrix[i][j];
                    matrix[i][j] = 0;
                }

            }
            if (isStateTransientv1(i, matrix)) {
                transientStateArray[i] = true;
                terminalStateArray[i] = false;
                for (int j = 0; j < length; j++) {
                    matrix[i][j] = 0;
                }
            } else {
                transientStateArray[i] = false;
                if (isStateTerminalv1(i, matrix)) {
                    terminalStateArray[i] = true;
                }
            }

        }
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        return matrix;
    }

    private int[][] clearMainDiagonal(int[][] matrix) {
        for (int i = 0; i < length; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }
    //</editor-fold>

    //<editor-fold desc="Arrays">
    private boolean isStateTerminal(int i) {
        for (int j = 0; j < length; j++) {
            if (matrix[i][j].getNominator() > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isStateTerminalv1(int i, int[][] matrix) {
        for (int j = 0; j < length; j++) {
            if (matrix[i][j] != 0) {
                return false;
            }
        }
        return true;
    }

    private int entryOfTransientStatev1(int i, int[][] matrix) {
        for (int j = 0; j < length; j++) {
            if (i < j && matrix[i][j] != 0) {
                return j;
            }
        }
        return -1;
    }

    private int entryOfTransientState(int i) {
        for (int j = 0; j < length; j++) {
            if (i < j && matrix[i][j].getNominator() != 0) {
                return j;
            }
        }
        return -1;
    }


    public boolean isStateTransient(int i) {
        int temp = 0;
        for (int j = 0; j < length; j++) {
            if (matrix[i][j].getNominator() != 0) {
                temp++;
            }
        }
        if (temp == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStateTransientv1(int i, int[][] matrix) {
        int temp = 0;
        for (int j = 0; j < length; j++) {
            if (matrix[i][j] != 0) {
                temp++;
            }
        }
        if (temp == 1) {
            return true;
        } else {
            return false;
        }
    }

    private void fillTerminalStateArray() {
        for (int i = 0; i < length; i++) {
            terminalStateArray[i] = isStateTerminal(i);
        }
    }

    private void fillTransientStateArray() {
        for (int i = 0; i < length; i++) {
            transientStateArray[i] = isStateTransient(i);
        }
    }

    private void fillStateWeight() {
        for (int i = 0; i < length; i++) {
            stateWeightArray[i] = new Fraction(1, 1);
        }
    }

    public void terminalStateArrayOutput() {
        for (int i = 0; i < length; i++) {
            System.out.println(terminalStateArray[i]);
        }
    }

    public String terminalStateArrayOutput(int i) {
        return String.format("%b", terminalStateArray[i]);
    }

    public void transientStateArrayOutput() {
        for (int i = 0; i < length; i++) {
            System.out.println(transientStateArray[i]);
        }
    }

    public String transientStateArrayOutput(int i) {
        return String.format("%b", transientStateArray[i]);
    }

    public void stateWeightArrayOutput() {
        for (int i = 0; i < length; i++) {
            System.out.print(stateWeightArray[i] + " ");
        }
        System.out.println();
        System.out.println();
    }

    public String stateWeightArrayOutput(int i) {
        return String.format("|" + stateWeightArray[i] + "|  ");
    }
    //</editor-fold>

    //<editor-fold desc="Operations">
    private static Fraction multiplyFraction(Fraction f1, Fraction f2) {
        return new Fraction(f1.getNominator() * f2.getNominator(), f1.getDenominator() * f2.getDenominator());
    }

    public Fraction addFractions(Fraction f1, Fraction f2) {
        if (f1.getDenominator() == f2.getDenominator()) {
            return new Fraction(f1.getNominator() + f2.getNominator(), f1.getDenominator());
        } else {
            int commonDenominator = f1.getDenominator() * f2.getDenominator();
            return new Fraction(f1.getNominator() * f2.getDenominator() + f2.getNominator() * f1.getDenominator(), commonDenominator);
        }

    }
    //</editor-fold>

    public void findStateWeight() {

        int currentRowWeight = 0;
        setAllDenominatorsToCommonDenomionator();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                currentRowWeight += matrix[j][i].getNominator();
            }
            stateWeightArray[i].setNominator(currentRowWeight);
            stateWeightArray[i].setDenominator(commonDenominator);
            currentRowWeight = 0;
        }
    }


    public void stackStateWeight() {
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i < j) {
                    if (!isStateTerminal(i)) {
                        /*stateWeightArray[i].setNominator(matrix[i][j].getNominator() * stateWeightArray[i].getNominator());
                        stateWeightArray[i].setDenominator((matrix[i][j].getDenominator() * stateWeightArray[i].getDenominator()));*/
                        matrix[i][j].setNominator(matrix[i][j].getNominator() * stateWeightArray[i].getNominator());
                        matrix[i][j].setDenominator((matrix[i][j].getDenominator() * stateWeightArray[i].getDenominator()));
                    }
                }
            }
        }
        findStateWeight();
        //shortFractions();
    }

    public void shortFractions() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j].getDenominator() != commonDenominator) {
                    matrix[i][j].setNominator(matrix[i][j].getNominator() / commonDenominator);
                    matrix[i][j].setDenominator(matrix[i][j].getDenominator() / commonDenominator);
                }
            }
        }
    }

    public void outputTerminalState() {
        int temp = 0;
        int terminalTemp = 0;
        int[] rowTemp = new int[length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                temp += matrix[i][j].getNominator();
            }
            if (temp == 0) {
                terminalTemp += stateWeightArray[i].getNominator();
                rowTemp[i] = stateWeightArray[i].getNominator();
            }
            temp = 0;
        }
        for (int i = 0; i < stateWeightArray.length; i++) {
            stateWeightArray[i].setDenominator(terminalTemp);
        }

        for (int i = 0; i < rowTemp.length; i++) {
            if (rowTemp[i] != 0) {
                stateWeightArray[i].setNominator(rowTemp[i]);
                System.out.println(stateWeightArray[i]);
            }
        }
    }




    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i != j) {
                    stringBuilder.append("|");
                    stringBuilder.append(matrix[i][j].toString());
                    stringBuilder.append("|");
                } else {
                    stringBuilder.append("|       |");
                }
            }
            stringBuilder.append("\t|\t " + stateWeightArrayOutput(i) + "\t");
            stringBuilder.append(terminalStateArrayOutput(i) + "\t");
            stringBuilder.append(transientStateArrayOutput(i) + "\n");
        }
        return stringBuilder.toString();
    }


    public void setAllDenominatorsToCommonDenomionator() {
        int commonDenominator = findCommonDenominator();
        for (int i = 0; i < length; i++) {
            if (matrix[i][0].getDenominator() != 0) {
                int multiplier = commonDenominator / matrix[i][0].getDenominator();
                for (int j = 0; j < length; j++) {
                    matrix[i][j].setNominator(matrix[i][j].getNominator() * multiplier);
                    matrix[i][j].setDenominator(matrix[i][j].getDenominator() * multiplier);
                }
            }

        }
    }

    public int findCommonDenominator() {
        int lcm=1;
        for (int i = 0; i < length - 1; i++) {
            if (matrix[i][0].getDenominator() == 0 || matrix[i + 1][0].getDenominator() == 0) {
                continue;
            }
            int absNumber1 = Math.abs(matrix[i][0].getDenominator());
            int absNumber2 = Math.abs(matrix[i + 1][0].getDenominator());
            int absHigherNumber = Math.max(absNumber1, absNumber2);
            int absLowerNumber = Math.min(absNumber1, absNumber2);
            lcm = absHigherNumber;
            while (lcm % absLowerNumber != 0) {
                lcm += absHigherNumber;
            }

        }
        return lcm;
    }
}
