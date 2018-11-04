package kostas_verveniotis_cbproject1;

import java.util.Scanner;

/**
 *
 * @author krocos
 */
public class LoginMenu {

    public User loginMenu(Database db) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("***  WHITE PAPER  ***\n");
        System.out.println("1... Login");
        System.out.println("2... Exit");
        System.out.print(">");
        String choice = scanner.nextLine();
        while (!choice.equals("1") && !choice.equals("2")) {
            System.out.println("Error: '" + choice + "' is not a valid option!!");
            System.out.println("***  WHITE PAPER  ***\n");
            System.out.println("1... Login");
            System.out.println("2... Exit");
            System.out.print(">");
            choice = scanner.nextLine();
        }
        if (choice.equals("2")) {
            System.exit(0);
        }
        System.out.print("\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("\nPassword: ");
        String password = scanner.nextLine();
        User user = null;
        user = db.checkLogin(username, password);
        return user;
    }

}
