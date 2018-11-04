package kostas_verveniotis_cbproject1;

import java.util.Scanner;

/**
 *
 * @author krocos
 */
public class Helper {

    public static String checkPassLength(String password) {
        while (password.length() < 3) {
            System.out.println("Password length should be more than 3 characters, numbers or symbols, please enter again: ");
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine();
        }
        return password;
    }

    public static String checkStringLength(String name) {
        while (name.length() > 25 || name.length() <= 0) {
            System.out.println("Length cannot be more than 25 characters, give option again: ");
            Scanner scanner = new Scanner(System.in);
            name = scanner.nextLine();
        }
        return name;
    }

    public static boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean containsOnlyNumbers(String str) {
        if (empty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int checkRoleId(String roleId) {
        boolean flag = false;
        int id = 0;
        Scanner scanner = new Scanner(System.in);
        while (flag == false) {
            if (roleId.equals("1") || roleId.equals("2") || roleId.equals("3") || roleId.equals("4") || roleId.equals("5")) {
                flag = true;
            } else {
                System.out.println("Role ID must be between 1 and 5, please type role ID again: ");
                roleId = scanner.nextLine();
            }
        }
        id = Integer.parseInt(roleId);
        return id;
    }

    public static String isCapital(String name) {
        boolean isCapital = Character.isUpperCase(name.charAt(0));
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return name;
    }

    public static String checkMessageLength(String messageData) {
        while (messageData.length() > 250) {
            System.out.println("Message data must be less than 255 characters. Write message again: ");
            Scanner scanner = new Scanner(System.in);
            messageData = scanner.nextLine();
        }
        return messageData;
    }

    public static boolean requestConfirmation() {
        Scanner scanner = new Scanner(System.in);
        String in = "";
        while (true) {
            System.out.print("Confirm Operation (y/n)... ");
            in = scanner.nextLine();
            if (in.equals("y") || in.equals("yes")) {
                return true;
            } else if (in.equals("n") || in.equals("no")) {
                return false;
            }
        }
    }

}
