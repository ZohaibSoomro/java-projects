import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class HummingbirdGameImpl {

    public static void main(String[] args) throws FileNotFoundException {
        //m=total no of lines in file & n=no of chars on a line
        int m = 0, n = 0;
        String fileContent = "";
        char[][] initialChars;//variable for holding values of 2D array of chars
        Scanner fileInput = new Scanner(new File("hummingbird_map.txt"));
        while (fileInput.hasNextLine()) {
            //reading from file
            String line = fileInput.nextLine();
            //inserting content of file into fileContent variable
            fileContent += line + "\n";
            if (n == 0) {
                n = line.length();    //count chars on a line in very first iteration
            }
            m++;   //count row on each iteration
        }
        //creating a mxn array of chars
        initialChars = new char[m][n];
        if (!fileContent.equals("")) {
            //breaking file content on each line
            String[] contentArray = fileContent.split("\n");
            for (int i = 0; i < contentArray.length; i++) {
                //inserting chars from file content to character array
                initialChars[i] = contentArray[i].toCharArray();
            }
        }
        System.out.println("Before: ");
        for (char[] charss : initialChars) {
            System.out.println(Arrays.toString(charss));
        }
        char[][] chars = Arrays.copyOf(initialChars, initialChars.length);
        boolean hasValidMove = true;
        int rounds = 0;
        while (hasValidMove) {
            moveRights(chars, n);
//            moveDowns(chars, m);
            rounds++;
            hasValidMove = hasMoves(initialChars, chars, m, n);
        }
        System.out.println("After: ");
        for (char[] charss : chars) {
            System.out.println(Arrays.toString(charss));
        }
        System.out.println("Rounds: " + rounds);
    }

    private static void moveRights(char[][] chars, int n) {
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (isMovingRight(chars[i][j])) {
                    int k = (j + 1) % n;
                    if (isHole(chars[i][k])) k = (k + 1) % n;
                    if (isEmptySquare(chars[i][k])) {
                        char t = chars[i][k];
                        chars[i][k] = chars[i][j];
                        chars[i][j] = t;
                        j=k;
                    }
                }
                System.out.print("row #" + i + ": ");
                System.out.println(Arrays.toString(chars[i]));
                System.out.println();
            }
        }
    }

    private static void moveDowns(char[][] chars, int m) {
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (isMovingDown(chars[i][j])) {
                    int k = (i + 1) % m;
                    if (isHole(chars[i][k])) k = (k + 1) % m;
                    if (isEmptySquare(chars[k][j])) {
                        char t = chars[k][j];
                        chars[k][j] = chars[i][j];
                        chars[i][j] = t;
                    }
                }
            }
        }
    }

    private static boolean hasMoves(char[][] initialChars, char[][] chars, int m, int n) {
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < chars[i].length; j++) {
                if (isMovingRight(chars[i][j])) {
                    int x = (j + 1) % n;
                    if (isHole(chars[i][x])) x = (x + 1) % n;
                    return !(isMovingRight(chars[i][x]) || isMovingDown(chars[i][x]));  //if its > or v on right side then no move for right moving
                } else if (isMovingDown(chars[i][j])) {
                    int x = (i + 1) % n;
                    if (isHole(chars[i][x])) x = (x + 1) % m;
                    return !(isMovingRight(chars[i][x]) || isMovingDown(chars[i][x]));
                }
            }
        }
        return true;
    }

    private static boolean isHole(char c) {
        return c == 'x';
    }

    private static boolean isEmptySquare(char c) {
        return c == '.';
    }

    private static boolean isMovingRight(char c) {
        return c == '>';
    }

    private static boolean isMovingDown(char c) {
        return c == 'v';
    }
}
