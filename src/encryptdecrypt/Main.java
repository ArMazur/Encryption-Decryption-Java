package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static String mode = "enc";
    private static int key = 0;
    private static String data = "";
    private static String inArg = "";
    private static String outArg = "";
    private static String alg = "shift";


    public static void main(String[] args) {
        getArguments(args);
        action(mode, key);
    }

    /**
     * Method takes command-line-arguments and sets
     * appropriate fields to passed values
     *
     * @param args command-line-arguments passed by user
     */
    private static void getArguments(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0) {
                switch (args[i]) {
                    case "-mode":
                        mode = args[++i];
                        break;
                    case "-key":
                        key = Integer.parseInt(args[++i]);
                        break;
                    case "-data":
                        data = args[++i];
                        inArg = "";
                        break;
                    case "-in":
                        if (data.isBlank()) {
                            inArg = args[++i];
                        }
                        break;
                    case "-out":
                        outArg = args[++i];
                        break;
                    case "-alg":
                        alg = args[++i];
                        break;
                }
            }
        }
    }

    /**
     * This method does appropriate action, chosen by user when passing command-line-arguments:
     * decryption or encryption;
     * algorithm of decryption or encryption;
     * key for decryption or encryption algorithm;
     * input and output files (if chosen);
     * <p>
     * Default parameters are:
     * -mode enc (encryption);
     * -alg shift;
     * -data empty_string;
     * -key 0
     *
     * @param action action parameter is set by user by passing command-line-arguments,
     *               it determines encryption or decryption algorithm
     * @param key    key is the value used to encrypt or decrypt message
     */
    private static void action(String action, int key) {
        // if data field is blank, reads data from file,
        // otherwise reads string in -data argument
        if (data.isBlank()) {
            data = readData(inArg);
        }

        switch (action) {
            case "enc":
                if (outArg.isBlank()) {
                    System.out.println(chooseEncryptionAlgorithm(alg, data, key));
                } else {
                    writeData(outArg, chooseEncryptionAlgorithm(alg, data, key));
                }
                break;
            case "dec":
                if (outArg.isBlank()) {
                    System.out.println(chooseDecryptionAlgorithm(alg, data, key));
                } else {
                    writeData(outArg, chooseDecryptionAlgorithm(alg, data, key));
                }
                break;
        }
    }

    /**
     * Reads data from file, if exists.
     * Otherwise, prints "Error in read data".
     * <p>Note:
     * data will be read from a file ONLY if -data argument was empty
     *
     * @param fileName the name of a file to read data from
     * @return returns a string inside file
     */
    private static String readData(String fileName) {
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)) {
            data = scanner.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Error in read data");
        }
        return data;
    }

    /**
     * Writes data to a file, if -out argument is not blank.
     * If file already exists, overrides contents of a file.
     * If method couldn't write to a file, prints "Error in write data" to a console.
     *
     * @param fileName the name of a file to write data to
     * @param data     data string to write to a file
     */
    private static void writeData(String fileName, String data) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("Error in write data");
        }
    }

    /**
     * Performs decryption with algorithm passed to -alg argument
     * By default -alg argument set to "shift"
     *
     * @param alg  chosen algorithm of decryption
     * @param data string to decrypt
     * @param key  key for decryption algorithm
     * @return decrypted string
     */
    private static String chooseDecryptionAlgorithm(String alg, String data, int key) {
        if (alg.equals("unicode")) {
            return decryptMessage(data, key);
        } else {
            return decryptMessageOnlyAlphabet(data, key);
        }
    }

    /**
     * Performs encryption with algorithm passed to -alg argument
     * By default -alg argument set to "shift"
     *
     * @param alg  chosen algorithm of encryption
     * @param data string to encrypt
     * @param key  key for encryption algorithm
     * @return encrypted string
     */
    private static String chooseEncryptionAlgorithm(String alg, String data, int key) {
        if (alg.equals("unicode")) {
            return encryptMessageUnicode(data, key);
        } else {
            return encryptMessageShift(data, key);
        }
    }

    /**
     * Performs encryption by shifting chars forward by specified number of positions (-key argument).
     * Method uses all Unicode table to shift characters.
     * For example, if char 'Z' is shifted by key 1, it will become '[', as next
     * char in Unicode table.
     *
     * @param message message to encrypt
     * @param key     describes how many places forward each character will be shifted
     * @return encrypted string
     */
    private static String encryptMessageUnicode(String message, int key) {
        if (message.isBlank()) {
            return message;
        }

        StringBuilder sb = new StringBuilder();
        var charArr = message.toCharArray();
        for (var c : charArr) {
            c = shiftChar(c, key);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * This method uses encryptMessageUnicode(message, key) method with reversed key.
     * Basically, it shifts characters backwards by specified number of positions (in -key argument).
     *
     * @param message message to decrypt
     * @param key     describes how many places backwards each character will be shifted
     * @return decrypted string
     */
    private static String decryptMessage(String message, int key) {
        return encryptMessageUnicode(message, key * -1);
    }

    /**
     * Method converts character to int, adds "key" value to it,
     * and returns new character
     *
     * @param c   character to change
     * @param key value to add to character
     * @return new character
     */
    private static char shiftChar(char c, int key) {
        int charInt = c;
        charInt += key;
        c = (char) charInt;
        return c;
    }


    /**
     * Method shifts each character of passed string forward, in english alphabet,
     * by specified number of places (in -key argument).
     * <p>This method is key sensitive. If char is uppercase 'A' with key 1, result
     * will be uppercase 'B'.
     * <p>Method will start back from 'A', if char is uppercase 'Z' with key 1.
     *
     * @param message message to encrypt
     * @param key     describes how many places forward each character will be shifted in english alphabet
     * @return encrypted string
     */
    private static String encryptMessageShift(String message, int key) {
        StringBuilder encryptedMessage = new StringBuilder();
        var msgArray = message.toCharArray();
        boolean upperCase = false;

        for (var c : msgArray) {

            if (Character.isUpperCase(c)) {
                upperCase = true;
                c = Character.toLowerCase(c);
            }

            if (ALPHABET.contains("" + c)) {
                c = shiftCharOnlyAlphabet(c, key);
            }

            if (upperCase) {
                c = Character.toUpperCase(c);
                upperCase = false;
            }

            encryptedMessage.append(c);
        }
        return encryptedMessage.toString();
    }

    /**
     * This method internally uses encryptMessageShift(message, key) method,
     * but shifts each character backwards in english alphabet
     *
     * @param message message to decrypt
     * @param key     describes how many places backwards each character will be shifted in english alphabet
     * @return decrypted string
     */
    private static String decryptMessageOnlyAlphabet(String message, int key) {
        return encryptMessageShift(message, key * -1);
    }


    /**
     * Shifts character forward in english alphabet by given number of places.
     * If char is 'z' and key is 1, method will return 'a'.
     * Algorithm uses modulus for shifting characters inside english alphabet (not a remainder)
     * @param c character to shift forward in english alphabet
     * @param key number of places to shift character
     * @return new character
     */
    private static char shiftCharOnlyAlphabet(char c, int key) {
        int index = ALPHABET.indexOf(c);
        int value = index + key;
        int newIndex = ((value % 26) + 26) % 26;
        return ALPHABET.charAt(newIndex);
    }
}
