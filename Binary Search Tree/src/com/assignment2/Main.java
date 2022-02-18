package com.assignment2;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        Scanner systemInput = new Scanner(System.in);
        Scanner fileInput = null;
        String nameOfFile = "";
        do {
            System.out.print("Enter input file name(quit to exit loop): ");
            nameOfFile = systemInput.nextLine();
            if (nameOfFile.equalsIgnoreCase("quit"))
                break;
            try {
                fileInput = new Scanner(new File(nameOfFile));
                while (fileInput.hasNextLine()) {
                    String line = fileInput.nextLine();
                    char[] characters = line.replaceAll(" ", "").toCharArray();
                    for (int i = 0; i < characters.length; i++) {
                        bst.insert(characters[i]);
                    }
                }
            } catch (Exception e) {
                System.err.println("\nException occurred: " + e.getMessage() + "\nTry again...\n");
            }
        } while (true);

        bst.print();
        System.out.println("Minimum character: " + bst.findMin());
        System.out.println("Maximum character: " + bst.findMax());

        do {
            System.out.print("Enter a character to search: ");
            String searchChar = systemInput.nextLine();
            if (searchChar.length() == 1) {
                BinarySearchTree result = bst.search(searchChar.charAt(0));
                if (result == null)
                    System.out.println("No such element!");
                else {
                    System.out.println("Result of search(" + searchChar.charAt(0) + "):");
                    result.print();
                }
                break;
            } else
                System.err.println("\nInput only single character!\nTry again...\n\n");
        } while (true);

        do {
            System.out.print("Enter a character to remove: ");
            String removeChar = systemInput.nextLine();
            if (removeChar.length() == 1) {
                bst = bst.remove(removeChar.charAt(0));
                System.out.println("Result of remove(" + removeChar.charAt(0) + "):");
                bst.print();
                break;
            } else
                System.err.println("\nInput only single character!\nTry again...\n\n");
        } while (true);
        System.out.println("Height of tree: " + bst.height(bst));
        System.out.println("Successfully exited.");
    }
}

