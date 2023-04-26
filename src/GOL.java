import java.util.Random;
public class GOL {
    static int rows = 0;
    static int cols = 0;
    static int generations = 0;
    static int SPEED = 0;
    static String[] splitPopulation;
    public static void validations(String[] args) {
        try {
            validateWidth(args[0]);
            validateHeight(args[1]);
            validateGenerations(args[2]);
            validateSpeed(args[3]);
            validatePopulation(args[4]);
            generateMatrix(rows, cols, GOL.generations, SPEED, splitPopulation);
        } catch (Exception e) {
            errorMessage();
        }
    }

    public static void validateWidth(String arg) {
        String[] colsString = arg.split("=");
        int width = Integer.parseInt(colsString[1]);
        if (width == 10 || width == 20 || width == 40 || width == 80) {
            cols = width;
        } else {
            errorMessage();
        }
    }

    public static void validateHeight(String arg) {
        String[] rowsString = arg.split("=");
        int height = Integer.parseInt(rowsString[1]);
        if (height == 10 || height == 20 || height == 40) {
            rows = height;
        } else {
            errorMessage();
        }
    }

    public static void validateGenerations(String arg) {
        String[] generation = arg.split("=");
        int generationParam = Integer.parseInt(generation[1]);
        if (generationParam > 0) {
            GOL.generations = generationParam;
        } else if (generationParam == 0) {
            GOL.generations = Integer.MAX_VALUE;
        } else {
            errorMessage();
        }
    }

    public static void validateSpeed(String arg) {
        String[] speedParam = arg.split("=");
        SPEED = Integer.parseInt(speedParam[1]);
        if (SPEED < 250 || SPEED > 1000) {
            errorMessage();
        }
    }

    public static void validatePopulation(String arg) {
        String[] populationInput = arg.split("=");
        if (populationInput[1].equals("rnd")) {
            Random random = new Random();
            StringBuilder population = new StringBuilder();
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    population.append(random.nextInt(2));
                }
                population.append("#");
            }
            population = new StringBuilder(population.substring(0, population.length() - 1));
            splitPopulation = population.toString().split("#");
            System.out.print("random population: " + population + "\n");
        } else if (populationInput[1].contains("#")) {
            splitPopulation = populationInput[1].split("#");
        } else {
            errorMessage();
        }
    }

    public static void errorMessage() {
        System.out.print("invalid argument");
        System.exit(0);
    }



    public static void generateMatrix(int rows, int cols, int generations, int speed, String[] splitPopulation) {
        int[][] grid = new int[rows][cols];


        for (int i = 0; i < splitPopulation.length; i++) {
            char[] part = splitPopulation[i].toCharArray();
            for (int j = 0; j < part.length; j++) {
                int value = part[j] - '0';
                grid[i][j] = value;
            }
        }


        int generationCount = 1;
        while (GOL.generations == 0 || generationCount <= GOL.generations) {
            System.out.println("|============  Generation: " + generationCount + " ============|");
            if (generationCount == 0) {
                System.out.println("Given Generation");
            }
            for (int row = 0; row < rows; row++) {
                System.out.println();
                for (int col = 0; col < cols; col++) {
                    System.out.print(grid[row][col] == -1 ? "." : grid[row][col] == 1 ? "#" : ".");
                }
            }
            System.out.println();
            generationCount++;
        }

            int[][] nextGrid = new int[rows][cols];
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int neighbors = countNeighbors(grid, row, col);
                    if (grid[row][col] == 1 && (neighbors < 2 || neighbors > 3)) {

                        nextGrid[row][col] = 0;
                    } else if (grid[row][col] == 1 && (neighbors == 2 || neighbors == 3)) {
                        nextGrid[row][col] = 1;
                    } else if (grid[row][col] == 0 && neighbors == 3) {
                        nextGrid[row][col] = 1;
                    } else {
                        nextGrid[row][col] = grid[row][col];
                    }
                }
            }
            grid = nextGrid;
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ignored) {
            }
        }

    private static int countNeighbors(int[][] grid, int rows, int cols) {
        int count = 0;
        for (int row = rows - 1; row <= rows + 1; row++) {

            for (int col = cols - 1; col <= cols + 1; col++) {
                if (col >= 0 && col < grid.length && col < grid[0].length && !(col == rows && col == cols)) {

                    count += grid[col][col];
                }
            }
        }
        return count;
    }
    public static void main(String[] args) {
        validations(args);
    }
}