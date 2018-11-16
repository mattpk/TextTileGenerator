public class Main {
    public static void main(String[] args) {
        Generator generator = Generator.fromInput();
        char[][] image = generator.generate(12, 12);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                sb.append(image[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
