import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {
    private int len;
    private char[][] image;
    private Map<Integer, Integer> coordToOptions;

    // sample size
    public static final int DEFAULT_N = 2;
    private int N;

    public static Generator fromInput() {
        Scanner in = new Scanner(System.in);

        int len = in.nextInt();
        in.nextLine();
        char[][] image = new char[len][len];

        for (int i = 0; i < len; i++) {
            String line = in.nextLine();
            for (int j = 0; j < len; j++) {
                image[i][j] = line.charAt(j);
            }
        }
        return new Generator(image, DEFAULT_N);
    }

    private int countRemainingViable(boolean[][] unviable) {
        int cnt = 0;
        for (int i = 0; i < unviable.length; i++) {
            for (int j = 0; j < unviable[0].length; j++) {
                if (!unviable[i][j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private int getMinCoord() {
        int minV = Integer.MAX_VALUE;
        int minK = 0;
        for (Map.Entry<Integer, Integer> entry : coordToOptions.entrySet()) {
            int k = entry.getKey();
            int v = entry.getValue();
            if (v < minV) {
                minK = k;
                minV = v;
            }
        }
        if (minV == Integer.MAX_VALUE) {
            return -1;
        }
        return minK;
    }

    public void fillNextPixel(char[][] gen, boolean[][][][] unviable) {
/*
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gen.length; i++) {
            for (int j = 0; j < gen[0].length; j++) {
                sb.append(gen[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
*/

        int minK = getMinCoord();
        if (minK == -1) return;
        int r = minK / gen[0].length;
        int c = minK % gen[0].length;
        coordToOptions.put(minK, Integer.MAX_VALUE);

        if (r < 0 || c < 0 || r >= gen.length || c >= gen[0].length) {
            return;
        }
        // now pick a random image for this pixel...
        boolean[][] uv = unviable[r][c];
        int remVia = countRemainingViable(uv);
        if (remVia <= 0) {
            throw new RuntimeException("Hit a dead end");
        }
        int rnd = ThreadLocalRandom.current().nextInt(remVia);

        int imageR = -1;
        int imageC = -1;
        for (int i = 0; i < uv.length; i++) {
            for (int j = 0; j < uv[0].length; j++) {
                if (uv[i][j] || imageC != -1) {
                    uv[i][j] = true;
                    continue;
                }
                if (rnd == 0) {
                    imageR = i;
                    imageC = j;
                }
                rnd--;
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (r + i >= gen.length || c + j >= gen[0].length) continue;
                if (gen[r + i][c + j] == '\u0000') {
                    // copy this pixel over.
                    gen[r + i][c + j] = image[imageR + i][imageC + j];
                    // update all affected neighbours viabilities
                    for (int x = r + i - N + 1; x <= r + i; x++) {
                        for (int y = c + j - N + 1; y <= c + j; y++) {
                            if (x == r && y == c || x < 0 || y < 0) continue; // self is not neighbour
                            // get the coordinate relative to x, y:
                            int coordX = r + i - x;
                            int coordY = c + j - y;

                            boolean[][] neighUv = unviable[x][y];

                            // check each viable image to see if its still viable
                            for (int ii = 0; ii < neighUv.length; ii++) {
                                for (int ij = 0; ij < neighUv[0].length; ij++) {
                                    if (neighUv[ii][ij]) continue;
                                    if (image[ii + coordX][ij + coordY] != gen[r + i][c + j]) {
                                        neighUv[ii][ij] = true;
                                        // update neighbour's options left
                                        int key = x * gen[0].length + y;
                                        coordToOptions.put(key, coordToOptions.get(key) - 1);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        fillNextPixel(gen, unviable);
    }

    public char[][] generate(int width, int height) {
        char[][] gen = new char[height][width];

        // viable[i][j] represents the viable image n*n squares with top left pixel starting at [i][j];
        // future optimization: use a BitSet for 8x memory savings..
        boolean [][][][] unviable = new boolean[gen.length][gen[0].length][image.length - N + 1][image[0].length - N + 1];

        // for each pixel, have a bitmask set to all 1s.
        // set to 0 when the sample starting with that pixel in the top left is impossible.
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[2]));
        // pq entry is row, col, remainingViable
        pq.add(new int[]{0,0, 100});
        for (int i = 0; i < gen.length; i++) {
            for (int j = 0; j < gen[0].length; j++) {
                int num = i * gen[0].length + j;
                coordToOptions.put(num, unviable[0][0].length * unviable[0][0][0].length);
            }
        }

        fillNextPixel(gen, unviable);
        return gen;
    }

    public Generator(char[][] image, int N) {
        if (!(image != null && image.length > 0 && image[0].length == image.length && image.length >= N)) {
            throw new RuntimeException();
        }
        this.len = image.length;
        this.image = image;
        this.N = N;
        this.coordToOptions = new HashMap<>();
    }

}
