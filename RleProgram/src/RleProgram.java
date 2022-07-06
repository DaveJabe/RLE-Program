import java.util.Arrays;
import java.util.Scanner;

public class RleProgram {

    // PRE-WORK METHODS

    // Pre-work Method 1 : "This method will take in an array of integers and return true if the array contains at least 4 consecutive numbers with the same value"
    public static boolean consecutiveFours(int[] arr) {
        int count = 1;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                count++;
            }
            else {
                count = 1;
            }
        }
        if (count >= 4) {
            return true;
        }
        return false;
    }

    // Pre-work Method 2 : "Parity is the formal name for the property of a number being even or odd.
    // This method will take in an array of integers, store the sum of all the values located at even indices in the first index of a new array,
    // then store the sum of all the values locatedat odd indices in the second index of this new array. Useful for method 4 (getDecodedLength)."
    public static int[] sumByParity(int[] arr) {
        int[] res = new int[2];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) // we are at even indices
                res[0] += arr[i];
            else // we are at odd indices
                res[1] += arr[i];
        }
        return res;
    }

    // Pre-work Method 3 : "This method will take in an array of integers and expand them into a larger array.
    // The value in the original array represents how many times that index (0-indexed) will appear in the new array. Useful for method 5 (decodeRle)."
    public static int[] expandByIndex(int[] arr) {
        // size of the output
        int size = 0;
        int index = 0;
        for (int item : arr) {
            size += item;
        }
        int[] res = new int[size];
        for (int i = 0; i < arr.length; i++) {
            int value = i;
            int repeats = arr[i];
            for (int j = 0; j < repeats; j++) {
                res[index] = value;
                index++;
            }
        }
        return res;
    }

    // Pre-work Method 4 : "This method will take in a string that is composed of numbers and letters.
    // It will return the count of numbers in that string, ignoring letters. Useful for method 6 (stringToData)."
    public static int numericalCount(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            Character.isDigit(string.charAt((i)));
            count++;
        }
        return count;

    }

        // 2B METHODS:


    // 2B Method 1 : "Translates data (RLE or raw) into a hexadecimal string (without delimiters). This method can also aid debugging."
    public static String toHexString(byte[] data) {
        String res = "";
        // adding hex digits from an Array to an empty String using a 'for' loop
        for (int i = 0; i < data.length; i++) {
            // case for A-F
            if ((data[i] >= 10) && (data[i] <= 15)) {
                res += Integer.toHexString(data[i]);
            }
            // case for numerical digits
            else {
                res += data[i];
            }
        }
        return res;
    }

    // 2B Method 2 : "Returns number of runs of data in an image data set; double this result for length of encoded (RLE) byte array."
    public static int countRuns(byte[] flatData) {
        int count = 0;
        int groups = 0;
        for (int i = 0; i < flatData.length - 1; i++) { // 'for' loop & 'if' statement to count consecutive digits in an Array
            if (flatData[i] == flatData[i + 1]) {
                count++;
                if (count == 15) { // data runs cannot be longer than 15 digits
                    groups++;
                    count = 0;
                }
                if (i == flatData.length - 2) { // ensures a run is added at the second to last index to
                    groups++;                   //account for final run (if consecutive digits)

                }
            }

            else { // ensures a run is added if the final two digits in the Array are not equal
                groups++;
                if (i == flatData.length - 2) {
                    groups++;
                }
            }
        }
        return groups;
    }

    // 2B Method 3 : "Returns encoding (in RLE) of the raw data passed in; used to generate RLE representation of a data."
    public static byte[] encodeRle(byte[] flatData) {
        int size = 2 * countRuns(flatData); // uses 'countRuns' method to set size for 'res' Array to return
        int count = 1; // count = 1 to account for the first digit in a run
        int ind = 0;
        byte[] res = new byte[size]; // sets size of 'res'
        do {
            for (int i = 0; i < flatData.length - 1; i++) { // 'for' loop using 'if' statements to build 'res'
                if (flatData[i] == flatData[i + 1]) // counts if two consecutive digits are equal
                    count++;

                if (count == 15) { // 'if' statement limits runs to 15 digits
                    res[ind++] = (byte) count;
                    count = 0;
                    res[ind++] = flatData[i];
                }
                if (flatData[i] != flatData[i + 1]) { // 'if' statement to end a run and store it in res
                    res[ind++] = (byte) count;
                    count = 1;
                    res[ind++] = flatData[i];
                }

                if (i == flatData.length - 2) { // 'if' statement to account for last digit in flatData
                    res[ind++] = (byte) count;
                    res[ind++] = flatData[i + 1];
                }

            }
            return res;
        } while (ind < flatData.length - 1);
    }


    // 2B Method 4 : "Returns decompressed size RLE data; used to generate flat data from RLE encoding. (Counterpart to #2)"
    public static int getDecodedLength(byte[] rleData) {
        byte[] res = new byte[1];
        for (int i = 0; i < rleData.length; i++) { // 'for' loop to get decompressed size RLE data
            if (i % 2 == 0) // we are at even indices
                res[0] += rleData[i];
        }
        int decodedLength = res[0]; // casts decompressed data to an integer
        return decodedLength;
    }

    //2B Method 5 : "Returns the decoded data set from RLE encoded data. This decompresses RLE data for use. (Inverse of #3)"
    public static byte[] decodeRle(byte[] rleData) {
        int index = 0;
        int size = 0;
        for (int i = 0; i < rleData.length; i++) { // 'for' loop set size for decoded RLE data
            if (i % 2 == 0) // only accounting for even indices
                size += rleData[i];
        }
        byte[] res = new byte[size];
        for (int i = 0; i < rleData.length - 1; i++) { // 'for' loop to repeat 'if' statement that checks for RLE data at
            if (i % 2 == 0) {                          //  even indices and initializes frequency and value variables
                int freq = rleData[i];
                byte value = rleData[i + 1];
                for (int j = 0; j < freq; j++) { // 'for' loop to repeat generation of 'res' based on how many times
                    res[index] = value;          // a 'value' appears in the RLE data
                    index++;
                }
            }
        }
        return res;
    }

    // 2B Method 6 : "Translates a string in hexadecimal format into byte data (can be raw or RLE). (Inverse of #1)"
    public static byte[] stringToData(String dataString) {
        byte[] res = new byte[dataString.length()]; // size of 'res' set by the length of the data string

        for (int i = 0; i < dataString.length(); i++) { // for loop to cast each 'char' in the string to a 'byte' in 'res'

            if ((dataString.charAt(i) >= 'a') && (dataString.charAt(i) <= 'f')) { // 'if' statement for hex letters A-F
                res[i] = Byte.parseByte(String.valueOf(dataString.charAt(i)), 16); // hex base for A-F (16)
            }
            else {
                res[i] = Byte.parseByte(String.valueOf(dataString.charAt(i)));
            }
        }
        return res;
    }

    // 2B Method 7 : "Translates RLE data into a human-readable representation."
    public static String toRleString(byte[] rleData) {
        String rleString = ""; // initializes rleString to concatenate
        for (int i = 0; i < rleData.length; i++) { // 'for' loop to concatenate rleString
            if (i % 2 == 0) { // even indices (always decimal value)
                rleString += rleData[i];
            }
            else if (i % 2 != 0) { // odd indices (always hexadecimal value)
                if (rleData[i] == 10) {
                    rleString += "a";
                }
                if (rleData[i] == 11) {
                    rleString += "b";
                }
                if (rleData[i] == 12) {
                    rleString += "c";
                }
                if (rleData[i] == 13) {
                    rleString += "d";
                }
                if (rleData[i] == 14) {
                    rleString += "e";
                }
                if (rleData[i] == 15) {
                    rleString += "f";
                }
                else if (rleData[i] < 10) {
                    rleString += rleData[i];
                }
            }
            if ((i != rleData.length - 1) && (i % 2 != 0)) { // ensures colon delimters are added to string-
                rleString += ":";                            // not at the end of the string and only after odd indices
            }
        }
        return rleString;
    }

    // 2b Method 8
    public static byte[] stringToRle(String rleString) {
        rleString = rleString.toLowerCase(); // sets all alphabetical string characters to lowercase
        String[] noColons = rleString.split(":"); // splits rleString at colon delimiters to create a String array so that each index includes a run length and run value
        int length = noColons.length; // initializes "length" variable based on "noColons" array length
        int count = 0; // initializes "count" variable to move up indices in 'for' loop
        byte[] rleData = new byte[length * 2]; // initializes rleData byte array to return; its length is equal to twice the length of
                                               // noColons to account for the run length and run value at each index of noColons
        for (String noColon : noColons) { // 'for' loop that adds values to rleData from rleString
            rleData[count] = Byte.parseByte(noColon.substring(0, noColon.length() - 1)); // sets rleData at "count" to the byte value of the run length (substring of each noColons index)
            char hexCh = noColon.charAt(noColon.length() - 1); // initializes 'char' = run value (occurs after the substring of each run length of each noColons index)
            byte hexByte = 0; // initializes 'byte' that will be set to = the value of hexCh
            if (hexCh <= '9') // 'if' statement to account for hexadecimal values < 9
                hexByte = (byte) (hexCh - 48);

            else if ((hexCh >= 'a') && (hexCh <= 'f')) // 'else if' statement to account for hexadecimal values > 9
                hexByte = (byte) (hexCh - 87);

            rleData[count + 1] = hexByte; // sets rleData at "count" + 1 to the run value accompanying the previous run length
            count = count + 2; // increments "count" by two to account for values being added to two indices during each iteration of the 'for' loop
        }
        return rleData;
    }


    // PROJECT 2 MAIN METHOD

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in); // creates scanner instance
        int start = 0; // initializes "start" to ensure program reverts to menu after any option (besides "exit program"") is executed
        int menu = 0; // initializes "menu" to maintain a menu loop
        int option = 0; // initializes "option" to later = user input (menu option)
        byte[] imageData = null; // initializes byte array that will later contain the RLE data for an image of the users choice

        // 1. Display welcome message
        System.out.println("Welcome to the RLE image encoder!");
        System.out.println();
        // 2. Display color test with the message
        System.out.println("Displaying Spectrum Image:");
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow); // displays spectrum image
        System.out.println();
        System.out.println();
        while (start == 0) {

            // 3. Display the menu - Part A: while loop or if-else chains
            // User might first enter 1 -> 6, 2 -> 6, etc.
            while (menu == 0) {
                System.out.println("RLE Menu");
                System.out.println("--------");
                System.out.println("0. Exit");
                System.out.println("1. Load File");
                System.out.println("2. Load Test Image");
                System.out.println("3. Read RLE String");
                System.out.println("4. Read RLE Hex String");
                System.out.println("5. Read Data Hex String");
                System.out.println("6. Display Image");
                System.out.println("7. Display RLE String");
                System.out.println("8. Display Hex RLE Data");
                System.out.println("9. Display Hex Flat Data");
                System.out.println();
                System.out.print("Select a Menu Option: ");

                option = scnr.nextInt(); // sets "option" = to user input
                menu = 1; // sets "menu" = 1 to move on to the user's option
            }

            // 3.0 - option 0: option to exit program
            if (option == 0) {
                System.exit(0);

            }
            // 3.1 - option 1: ConsoleGfx.loadfile(userInput) and you want to store the returned byte[] into imageData array
            else if (option == 1) {
                System.out.print("Enter name of file to load (i.e. testfiles/fileToLoad.gfx): ");
                imageData = ConsoleGfx.loadFile(scnr.next()); // "loadFile" method used to load file based on user input
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.2 - option 2: store ConsoleGfx.testImage into the imageData array
            else if (option == 2) {
                imageData = ConsoleGfx.testImage; // sets imageData = testImage
                System.out.println("Test image data loaded.");
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.3 - option 3: decodes an RLE string with delimiters
            else if (option == 3) {
                System.out.print("Enter an RLE string to be decoded: ");
                String rleString = scnr.next(); // initializes rleString = user input
                byte[] byteRleData = stringToRle(rleString); // initializes byteRleData = rleString converted to rleData
                imageData = decodeRle(byteRleData); // decodes byteRleData and assigns it to imageData
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.4 - option 4: decodes an RLE string without delimiters
            else if (option == 4) {
                System.out.print("Enter the hex string holding RLE data: ");
                String rleString = scnr.next(); // initializes rleString = user input
                byte[] byteRleData = stringToData(rleString); // initializes byteRleData = rleString converted to rleData
                imageData = decodeRle(byteRleData); // decodes "byteRleData" and assigns it to "imageData
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.5 - option 5: sets imageData = flat data
            else if (option == 5) {
                System.out.print("Enter the hex string holding flat data: ");
                String flatData = scnr.next(); // initializes "flatData" = user input
                byte[] flatDataArray = new byte[flatData.length()]; // initializes 'array' with a size  = to the length of the flat data
                for (int i = 0; i < flatData.length(); i++) { // 'for' loop to add to "flatDataArray" from "flatData" 'chars'
                    if (flatData.charAt(i) <= '9') // 'if' statement to account for hexadecimal values < 9
                        flatDataArray[i] = (byte) (flatData.charAt(i) - 48);
                    else if ((flatData.charAt(i) >= 'a') && (flatData.charAt(i) <= 'f')) // 'if' statement to account for hexadecimal values > 9
                        flatDataArray[i] = (byte) (flatData.charAt(i) - 87);
                }
                imageData = flatDataArray; // assigns "flatDataArray" to "imageData"
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.6 - option 6: display image stored in imageData array
            else if (option == 6) {
                System.out.println("Displaying image...");
                ConsoleGfx.displayImage(imageData);
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.7 - option 7: displays encoded RLE data as a string with delimiters
            else if (option == 7) {
                byte[] encodedRle = encodeRle(imageData); // encodes "imageData"
                String rleString = toRleString(encodedRle);
                if (rleString.length() > 100) { // 'if' statement accounts for excess "0"s
                    rleString = rleString.substring(0, rleString.length() - 15);
                }
                System.out.println("RLE representation: " + rleString); // displays encoded RLE data as a RLE string
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.8 - option 8: displays encoded RLE data without delimiters
            else if (option == 8) {
                byte[] encodedRle = encodeRle(imageData); // initializes array of encoded "imageData"
                String rleHexString = toHexString(encodedRle);
                rleHexString = rleHexString.substring(0, rleHexString.length() - 10); // accounts for excess "0"s
                System.out.println("RLE hex Values: " + rleHexString); // displays "rleHexString"
                menu = 0; // resets menu
                System.out.println();
            }
            // 3.9 - option 9: displays flat data as a string
            else if (option == 9) {
                System.out.println("Flat hex values: " + toHexString(imageData));
                menu = 0; // resets menu
                System.out.println();
            }

        }
    }
}
