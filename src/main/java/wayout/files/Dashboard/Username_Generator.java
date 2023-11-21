package wayout.files.Dashboard;

import java.util.Random;

public class Username_Generator {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";

//    public static void main(String[] args) {
//
//        // Generate a random username
//        String username = generateUsername(8);
//
//        // Print the username
//        System.out.println("Random username: " + username);
//    }

    public String generateUsername(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }
}
