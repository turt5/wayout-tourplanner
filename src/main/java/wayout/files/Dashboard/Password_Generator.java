package wayout.files.Dashboard;

import java.util.Random;

public class Password_Generator {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

//    public static void main(String[] args) {
//
//        // Generate a random password
//        String password = generatePassword(8);
//
//        // Print the password
//        System.out.println("Random password: " + password);
//    }

    public String generatePassword(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }
}
