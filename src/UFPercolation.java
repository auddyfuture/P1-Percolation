import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    // Percolation system size, int n.
    int n;
    // Percolation system, boolean[][] open.
    boolean[][] open;
    // Number of open sites, int openSites.
    int openSites;
    // Union-find representation of the percolation system, WeightedQuickUnionUF uf.
    WeightedQuickUnionUF uf;
    //  Using virtual source and sink sites introduces what is called the back wash problem
    WeightedQuickUnionUF bw;

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        open = new boolean[n][n];
        openSites = 0;
        uf = new WeightedQuickUnionUF((n * n) + 2);
        bw = new WeightedQuickUnionUF((n * n) + 2);
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        // If site (i, j) is not open
        if (!isOpen(i, j)) {
            // Open the site
            open[i][j] = true;
            // Increment openSites by one
            openSites++;
        }
        // If the site is in the first (or last) row, connect the corresponding uf site with the
        // source (or sink)
        if (i == 0) {
            uf.union(encode(i, j), 0);
            bw.union(encode(i, j), 0);
        }
        if (i == n - 1) {
            uf.union(encode(i, j), n * n + 1);
        }
        // Checks if the north is open and connects ij
        if (i - 1 >= 0 && i - 1 < n && open[i - 1][j]) {
            uf.union(encode(i - 1, j), encode(i, j));
            // same as uf
            bw.union(encode(i - 1, j), encode(i, j));
        }
        // Checks if the south is open and connects ij
        if (i + 1 >= 0 && i + 1 < n && open[i + 1][j]) {
            uf.union(encode(i + 1, j), encode(i, j));
            // same as uf
            bw.union(encode(i + 1, j), encode(i, j));
        }
        // Checks if the west is open and connects ij
        if (j - 1 >= 0 && j - 1 < n && open[i][j - 1]) {
            // do stuff
            uf.union(encode(i, j - 1), encode(i, j));
            // same as uf
            bw.union(encode(i, j - 1), encode(i, j));
        }
        // Checks if the east is opena and connects ij
        if (j + 1 >= 0 && j + 1 < n && open[i][j + 1]) {
            // do stuff
            uf.union(encode(i, j + 1), encode(i, j));
            // same as uf
            bw.union(encode(i, j + 1), encode(i, j));
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        // int k;
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return (uf.connected(encode(i, j), 0) && bw.connected(encode(i, j), 0));
    }


    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {

        return uf.connected(0, n * n + 1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return (n*i)+j+1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}