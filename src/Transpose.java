import stdlib.StdArrayIO;

public class Transpose {
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        double[][] x = StdArrayIO.readDouble2D();
        StdArrayIO.print(transpose(x));
    }

    // Returns a new matrix that is the transpose of x.
    private static double[][] transpose(double[][] x) {
        /* create a new 2D matrix t with dimensions n x m, where m x n are the
        dimensions of x */
        int m = x.length;
        int n = x[0].length;
        // for each 0 <= i , m and 0 <= j < n, set t[j][i] to x[i][j]
        double [][] t = new double[n][m];
        int i, j;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) {
                t[j][i] = x[i][j];
            }
        }
        // Return t.
        return t;
    }
}
