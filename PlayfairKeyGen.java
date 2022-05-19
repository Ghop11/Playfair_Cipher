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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayfairKeyGen {


    public static void main(String[] args) throws IOException {

        if (args.length == 0){
            KeyGenStandard();
        }
        else if (args.length == 1){
            KeyGenWord(args[0]);
        } else {
            System.out.print("Invalid arguments. Please either leave blank or only enter a keyword");
        }
    }

    public static void KeyGenStandard() throws IOException {
        // i and j will just count as i

        String[][] key = {
                {"a", "b", "c", "d", "e"},
                {"f", "g", "h", "i", "k"},
                {"l", "m", "n", "o", "p"},
                {"q", "r", "s", "t", "u"},
                {"v", "w", "x", "y", "z"},
        };

        KeyText(key);
    }

    public static String[][] KeyGenWord(String keyword) throws IOException {

        String keyword_update = KeywordRemoveDup(keyword);
        String keyword_update_input = keyword_update;

        // put keyword_update in matrix
        String[][] key = new String[5][5];


        // TODO: First put in key word and the rest of the values with x
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if (!keyword_update_input.equals("")){
                    key[i][j] = keyword_update_input.charAt(0) + "";
                    keyword_update_input = keyword_update_input.substring(1); // pops first char in string
                } else {
                    key[i][j] = "x";
                }
            }
        }

        // TODO: Need to remove characters from alphabet that was added from keyword:
        String remaining_chars = RemainingChars(keyword_update);
        String remaining_chars_input = remaining_chars;

        // TODO: Add remaining alphabet to "key" array
        for (int w = 0; w < 5; w++){
            for (int v = 0; v < 5; v++){
                if (key[w][v] == "x"){
                    key[w][v] = remaining_chars_input.charAt(0) + "";
                    remaining_chars_input = remaining_chars_input.substring(1);
                }
            }
        }

        // TODO: Create text files
        KeyText(key);
        KeyWord(keyword);
        return key;
    }

    public static void KeyWord(String keyword) throws IOException {
        File Key = new File("Playfair_Keyword.txt");

        // output key results to text
        Key.createNewFile();

        PrintWriter pw = new PrintWriter(Key);
        pw.println(keyword);
        pw.close();
    }

    public static void KeyText(String[][] key) throws IOException {
        // TODO: Create file to store the key in:
        //  has to be a for loop

        File Key = new File("Playfair_Key.txt");

        // output key results to text
        Key.createNewFile();

        PrintWriter pw = new PrintWriter(Key);

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                pw.println(key[i][j]);
            }
        }
        pw.close();
        Print5By5(key);

    }

    public static void Print5By5(String[][] key) throws IOException {

        File Key = new File("Playfair_Key_Visual.txt");

        // output key results to text
        Key.createNewFile();

        PrintWriter pw = new PrintWriter(Key);

        for (int i = 0; i < 5; i++){
            String line = "";

            for (int j = 0; j < 5; j++){
                line += key[i][j] + " ";
            }
            pw.println(line);
        }
        pw.close();
    }

    public static String KeywordRemoveDup(String keyword){
        char[] keyword_arr = keyword.toCharArray();
        Set<Character> charSet = new LinkedHashSet<Character>();
        for (char c : keyword_arr) {
            charSet.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (Character character : charSet) {
            sb.append(character);
        }

        return sb.toString();
    }

    public static String RemainingChars(String keyword){
        String alphabet = "abcdefghiklmnopqrstuvwxyz"; // no j
        char[] alphabet_array = alphabet.toCharArray();
        char[] keyword_array = keyword.toCharArray();
        String results = "";

        for (int i = 0; i < alphabet.length(); i++){
            boolean found = false;
            for (int j = 0; j < keyword.length(); j++){
                if (alphabet_array[i] == keyword_array[j]){
                    found = true;
                }
            }
            if (!found){
                results += alphabet_array[i];
            }
        }

        return results;
    }

}
































