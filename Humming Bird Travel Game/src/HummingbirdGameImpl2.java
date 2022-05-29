import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class HummingbirdGameImpl2 {

    public static void main(String[] args) throws IOException {
        //m=total no of lines in file & n=no of chars on a line
        int m = 0, n = 0;
        String fileContent = "";
        String[] strs;//variable for holding values of 2D array of chars
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
        //breaking file content on each line
        strs = fileContent.split("\n");
        System.out.println("Before: ");
        for (String str : strs) {
            System.out.println(str);
        }

        int rounds = 0;

        for (int i = 0; i < n/2; i++) {
            moveRights(strs);
            moveDown(strs);
            rounds++;
        }
        System.out.println("After: ");
        print(strs);
        System.out.println("Rounds: "+rounds);
    }

    private static void print(String[] strs) {
        for (String str : strs) {
            System.out.println(str);
        }
    }


    private static boolean hasMoreMoves(String[] strs) {
        for (String str : strs) {
            if (str.contains(">.") || str.contains(">x.") || str.contains(".x>>") || str.contains(".x.>")) return true;
        }
        for (int i = 0; i < strs.length - 1; i++) {
            for (int j = 0; j < strs[i].length(); j++) {
                if (strs[i].charAt(j) == 'v' && strs[i + 1].charAt(j) == '.') return true;
            }
        }
        return false;
    }

    private static void moveRights(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].startsWith(".") && strs[i].endsWith(">"))
                strs[i] = ">" + strs[i].substring(1, strs[i].length() - 1) + ".";
            strs[i] = strs[i].replace(".xv>", ">xv.");
            strs[i] = strs[i].replace(">.", ".>");
            strs[i] = strs[i].replace(">x.", ".x>");
        }
    }

    private static void moveDown(String[] strs) {
        for (int i = 0; i < strs.length - 1; i++) {
            String str1 = strs[i];
            String str2 = strs[i + 1];
            for (int k = 0; k < str1.length(); k++) {
                if (str2.charAt(k) == 'x') continue;
                if (str1.charAt(k) == 'v' && str2.charAt(k) == '.') {
                    strs[i] = str1.substring(0, str1.indexOf(str1.charAt(k))) + str2.charAt(k) + str1.substring(k + 1);
                    strs[i + 1] = str2.substring(0, str2.indexOf(str2.charAt(k))) + str1.charAt(k) + str2.substring(k + 1);
                }
            }
        }
    }

}