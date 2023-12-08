import java.util.Scanner;
public class Main {
    private static int public_Key;
    private static int module;

    public static void main(String[] args) {
        String message_In = getString();
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the value of n: ");
        int value = scanner.nextInt();
        primeNumber(value);
        System.out.print("Enter First prime number: ");
        int firs_Prime = scanner.nextInt();
        System.out.print("Enter Last prime number: ");
        int second_Prime = scanner.nextInt();
        algorithmRSA(firs_Prime, second_Prime);
        System.out.print("\n Text message: " + "\n" + message_In);
        int[] msg_Encypt = convert_ASCII_Encrypt(message_In);
        int[] msg_Decrypt = decrypt(msg_Encypt);
        String message_Out = String.valueOf(reconvert_ASCII(msg_Decrypt));
        System.out.printf("\nDecrypted message: " + "\n" + message_Out);
    }

    private static String getString() {
        return "The only gripe I have of the V3, is the width. It occupies the entire height of a breadboard from the outer 5-pin sockets.. One hack someone suggested, getting a short Minimum 15-pin width (5X15) breadboard, and sawing it right down the center, so the 5-point rails could be placed further apart. (have done so already with an old Radio shack breadboard. 276-0003, and cut it right down the center of the chip area.). I’m still trying to figure out LUA on both the -01 & the -12 on the Lolin.. (had been running stock NodeMCU, recently updated to a custom from https://nodemcu-build.com/ (does the build from the V2.1.0 source, allowing you to pick which modules to add, then EMAILS You the Float & Integer firmwares.)";
    }

    private static StringBuilder reconvert_ASCII(int[] msg_Decrypt) {
        StringBuilder reconvert_Msg = new StringBuilder();
        String chars;
        for (int j : msg_Decrypt) {
            chars = Character.toString((char) j);
            reconvert_Msg.append(chars);
        }
        return reconvert_Msg;
    }

    private static int[] decrypt(int[] msg_Encypt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Private Key value: ");
        int input_Private_Key = scanner.nextInt();
        int[] msg_Decrypted = new int[msg_Encypt.length];
        for (int i = 0; i < msg_Encypt.length; i++) {
            int d = input_Private_Key;
            int decrypted = 1;
            while (d > 0) {
                decrypted *= msg_Encypt[i];
                decrypted %= module;
                d -= 1;
            }
            msg_Decrypted[i] = decrypted;
        }
        return msg_Decrypted;
    }
    private static void algorithmRSA(int firs_Prime, int second_Prime) {
        module = firs_Prime * second_Prime;
        int euler_Fun = (firs_Prime - 1) * (second_Prime - 1);
        int e = firs_Prime;
        while (gcd(e, euler_Fun) != 1) {
            e += 1;
        }
        public_Key = e;

        int d = second_Prime;
        while ((d * public_Key) % euler_Fun != 1) {
            d += 1;
        }
        int private_Key = d;

        System.out.print("Euler function = " + euler_Fun + ";");
        System.out.print("\nThe first part of public key: = " + module + ";");
        System.out.print("\n1 < Public Key = " + public_Key + " < " + euler_Fun + ";");
        System.out.print("\nPrivate Key = " + private_Key + ";");
    }

    private static int gcd(int e, int euler_Fun) {
        /*
         * gcd or greatest common divisor
         * */
        if (euler_Fun == 0) {
            return e;
        }
        return gcd(euler_Fun, e % euler_Fun);
    }

    private static int[] convert_ASCII_Encrypt(String name) {
        int[] charArray;
        charArray = new int[name.length()];
        for (int i = 0; i < name.length(); i++) {
            charArray[i] = (int) name.charAt(i);
        }
        return encrypt(charArray);
    }
    private static int[] encrypt(int[] charArray) {
        int encrypted_text;
        int e;
        int[] msgArray_Temp;
        msgArray_Temp = new int[charArray.length];
        StringBuilder msg;
        msg = new StringBuilder();
        for (int i = 0; i < charArray.length; i++) {
            e = public_Key;
            encrypted_text = 1;
            while (e > 0) {
                encrypted_text *= charArray[i];
                encrypted_text %= module;
                e -= 1;
            }
            msg.append(encrypted_text);
            msgArray_Temp[i] = encrypted_text;
        }
        return msgArray_Temp;
    }

    private static void primeNumber(int value) {
        StringBuilder primeNumbers = new StringBuilder();
        for (int i = 1; i <= value; i++) {
            int counter = 0;
            for (int num = i; num >= 1; num--) {
                if (i % num == 0) {
                    counter = counter + 1;
                }
            }
            if (counter == 2) {
                primeNumbers.append(i).append(", ");
            }
        }
        System.out.println("Prime numbers list from 2 to " + value + " are :");
        System.out.println(primeNumbers);
    }
}