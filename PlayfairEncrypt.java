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
import java.util.Locale;
import java.util.Scanner;

public class PlayfairEncrypt {

    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            System.out.print("First argument should be the keyword file name with extension\nSecond argument should be the plaintext file name with extension.");
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
            String plaintext = "";
            while (readPlaintextFile.hasNextLine()){
                plaintext += readPlaintextFile.nextLine().toLowerCase();
            }

            // TODO: remove punctuations and spaces:
            String plaintext_ready_to_encrypt = plaintext.replaceAll("[^A-Za-z]", "").toLowerCase();
            // TODO: Create key array:
            String[][] key = PlayfairKeyGen.KeyGenWord(keyword);

            // TODO: send to get encrypt:
            Encrypt(key, plaintext_ready_to_encrypt);

        }
    }

    public static void Encrypt(String[][] key, String plaintext) throws IOException {
        String cipherText = "";

        // add x to odd count
        String plaintext_remove = plaintext.length() % 2 == 0 ? plaintext: plaintext + "x";
//        if (plaintext.length() / 2 != 0){
//            plaintext += "x";
//        }
//
//
//        String plaintext_remove = plaintext;
        for (int i = 0; i < plaintext.length() / 2 + 1; i++){
            char first = plaintext_remove.charAt(0);
            char second = plaintext_remove.charAt(1);

            // TODO: make sure they are not the same char. if they are change second to 'x'
            if (first == second) {second = 'x';}

            if (first == 'j') {first = 'i';}
            if (second == 'j') {second = 'i';}

            cipherText += EncryptTwoLetters(key, first, second);
            plaintext_remove = plaintext_remove.substring(2);

        }

        // TODO: output cipher text in file
        CipherText(cipherText);

    }

    public static String EncryptTwoLetters(String[][] key, char first, char second){
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
            // TODO: if column is 4 --> go to zero "0"
            first_col = first_col == 4 ? 0 : first_col + 1;
            second_col = second_col == 4 ? 0 : second_col + 1;
        }
        else if (first_col == second_col){
            // TODO: pick row to the bottom.
            // TODO: if col is 4 -> go to zero "0"
            first_row = first_row == 4 ? 0 : first_row + 1;
            second_row = second_row == 4 ? 0 : second_row + 1;
        }

        results = key[first_row][second_col] + key[second_row][first_col];
        return results;
    }

    public static void CipherText(String cipher) throws IOException {

        File CipherFile = new File("Playfair_Ciphertext.txt");

        // output key results to text
        CipherFile.createNewFile();

        PrintWriter pw = new PrintWriter(CipherFile);

        pw.println(cipher);
        pw.close();
    }



}




















