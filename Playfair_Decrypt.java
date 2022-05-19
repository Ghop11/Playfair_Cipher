/*
         ▄▄▄▄▄▄▄ ▄▄   ▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄▄▄▄ ▄▄▄▄ ▄▄▄▄
        █       █  █ █  █       █       █    █    █
        █   ▄▄▄▄█  █▄█  █   ▄   █    ▄  ██   ██   █
        █  █  ▄▄█       █  █ █  █   █▄█ ██   ██   █
        █  █ █  █   ▄   █  █▄█  █    ▄▄▄██   ██   █
        █  █▄▄█ █  █ █  █       █   █    █   ██   █
        █▄▄▄▄▄▄▄█▄▄█ █▄▄█▄▄▄▄▄▄▄█▄▄▄█    █▄▄▄██▄▄▄█
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Playfair_Decrypt {

    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            System.out.print("First argument should be the keyword file name with extension\nSecond argument should be the ciphertext file name with extension.");
        } else {

            // TODO: get keyword from args[0]
            File keywordFile = new File(args[0]);
            Scanner readKeywordFile = new Scanner(keywordFile);
            String keyword = readKeywordFile.nextLine().toLowerCase();

            // TODO: get plaintext file name from args[1]
            File plaintextFile = new File(args[1]);
            Scanner readPlaintextFile = new Scanner(plaintextFile);

            // read file and append lines to String variable:
            // make sure everything is lowercase
            String ciphertext = "";
            while (readPlaintextFile.hasNextLine()){
                ciphertext += readPlaintextFile.nextLine().toLowerCase();
            }

            // TODO: Create key array:
            String[][] key = PlayfairKeyGen.KeyGenWord(keyword);

            // TODO: send to get decrypt:
            Decrypt(key, ciphertext);

        }
    }

    public static void Decrypt(String[][] key, String cipher) throws IOException {

        String cipher_remove = cipher;
        cipher_remove = cipher_remove.length() % 2 == 0 ? cipher : cipher_remove + "x";
        String plaintext = "";

//        for (int i = 0; i < cipher_remove.length() + 1/ 2; i++){
//            char first = cipher_remove.charAt(0);
//            char second = cipher_remove.charAt(1);
//
//            plaintext += DecryptTwoLetters(key, first, second);
//            cipher_remove = cipher_remove.substring(2);
//        }

        while (cipher_remove.length() > 0){
            char first = cipher_remove.charAt(0);
            char second = cipher_remove.charAt(1);

            plaintext += DecryptTwoLetters(key, first, second);
            cipher_remove = cipher_remove.substring(2);
        }

        PlainText(plaintext);
    }

    public static String DecryptTwoLetters(String[][] key, char first, char second){
        int first_col = 0;
        int first_row = 0;
        int second_row = 0;
        int second_col = 0;

        // TODO: search for position
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++ ){
                if (key[i][j].equals(new String(new char[]{first}))){ first_row = i; first_col = j; }
                if (key[i][j].equals(new String(new char[]{second}))){ second_row = i; second_col = j;}
            }
        }

        String results = "";

        // TODO: check if same column or same row
        if (first_row == second_row){
            // TODO: pick to the right column
            // TODO: if column is 0 --> go to zero "4"
            first_col = first_col == 0 ? 4 : first_col - 1;
            second_col = second_col == 0 ? 4 : second_col - 1;
        }
        else if (first_col == second_col){
            // TODO: pick row to the bottom.
            // TODO: if col is 0 -> go to zero "4"
            first_row = first_row == 0 ? 4 : first_row - 1;
            second_row = second_row == 0 ? 4 : second_row - 1;
        }

        results = key[first_row][second_col] + key[second_row][first_col];
        return results;
    }



    public static void PlainText(String plaintext) throws IOException {

        File CipherFile = new File("Playfair_Plaintext.txt");

        // output key results to text
        CipherFile.createNewFile();

        PrintWriter pw = new PrintWriter(CipherFile);

        pw.println(plaintext);
        pw.close();
    }

}































