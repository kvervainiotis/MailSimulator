package kostas_verveniotis_cbproject1;

import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 *
 * @author krocos
 */
public class Kostas_Verveniotis_CBProject1 {

    /**
     * @param args the command line arguments
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static User user = null;
    private static Database db = null;
    private static int di = 0;
    private static Message msg = null;

    public static void main(String[] args) {
//        DATABASE CREATION
//        DatabaseCreation dbcr = new DatabaseCreation();
//        dbcr.createDatabase();
//        dbcr.createRole();
//        dbcr.createUsers();
//        dbcr.createMessage();
//        dbcr.createSendMessage();
//        dbcr.alterUsers();
//        dbcr.insertAdmin();
//        dbcr.alterUpdate();
//        dbcr.alterSendMessage();

//        MAIN PROGRAM
        db = new Database();
        LoginMenu login = new LoginMenu();
        user = login.loginMenu(db);
        db.setUser(user);
        new Kostas_Verveniotis_CBProject1().mainMenu();
    }

    private void mainMenu() {
        Menu menu = new Menu();
        menu.setTitle("***  Welcome " + user.getFirstName() + " " + user.getLastName() + "  ***");
        menu.addItem(new MenuItem("My profile", this, "myProfile"));
        String id = user.getRole();
        switch (id) {
            case "super admin":
                menu.addItem(new MenuItem("Lobby", this, "lobby"));
                menu.addItem(new MenuItem("Manage database", this, "superAdminMenu"));
                break;
            case "vip user":
                menu.addItem(new MenuItem("Lobby", this, "lobby"));
                break;
            case "super user":
                menu.addItem(new MenuItem("Lobby", this, "lobby"));
                break;
            case "power user":
                menu.addItem(new MenuItem("Lobby", this, "lobby"));
                break;
        }
        menu.execute();
    }

    public void lobby() {
        Menu menu = new Menu();
        menu.setTitle("******  LOBBY  ******");
        menu.addItem(new MenuItem("All messages", db, "printAllMessages"));
        menu.addItem(new MenuItem("Reply to a message", this, "replyToGeneralMessage"));
        switch (user.getRole()) {
            case "super admin":
                menu.addItem(new MenuItem("Update message", this, "updateGeneralMessage"));
                menu.addItem(new MenuItem("Delete message", db, "deleteMessage"));
                break;
            case "vip user":
                menu.addItem(new MenuItem("Update message", this, "updateGeneralMessage"));
                menu.addItem(new MenuItem("Delete message", db, "deleteMessage"));
                break;
            case "super user":
                menu.addItem(new MenuItem("Update message", this, "updateGeneralMessage"));
                break;
        }
        menu.execute();
    }

    public void myProfile() {
        Menu menu = new Menu();
        menu.setTitle("*****  " + user.getUsername().toUpperCase() + "  *****");
        menu.addItem(new MenuItem("New message", this, "sendMessage"));
        menu.addItem(new MenuItem("My messages", this, "myMessages"));
        menu.addItem(new MenuItem("My profile", this, "printData"));
        menu.execute();
    }

    public void superAdminMenu() {
        Menu menu = new Menu();
        menu.setTitle("***  SUPER ADMIN STUFF  ***");
        menu.addItem(new MenuItem("Create user", db, "createUser"));
        menu.addItem(new MenuItem("View users", db, "printUsers"));
        menu.addItem(new MenuItem("Delete user", db, "deleteUser"));
        menu.addItem(new MenuItem("Update user", this, "updateUser"));
        menu.addItem(new MenuItem("Backup files", this, "backupFiles"));
        menu.execute();
    }

    public void printData() {
        System.out.println(user);
    }

    public void backupFiles() {
        db.backup();
    }

    public void updateGeneralMessage() {
        db.updateMessage(1);
    }

    public void updateMyMessage() {
        db.updateMessage(2);
    }

    public void updateUser() {
        Menu menu = new Menu();
        db.printUsers();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose user id you wish to update or type exit to return: ");
        String id = scanner.nextLine();
        while (!Helper.containsOnlyNumbers(id)) {
            if (id.equals("exit")) {
                return;
            }
            System.out.println("'" + id + "' is not a valid ID, please choose another or type exit to return:");
            id = scanner.nextLine();
        }
        di = Integer.parseInt(id);
        di = db.checkUserId(di);
        if (di == 0) {
            return;
        }
        db.printUser(di);
        menu.setTitle("***  UPDATE FIELDS  ***");
        menu.addItem(new MenuItem("Update username", this, "updateUsername"));
        menu.addItem(new MenuItem("Update password", this, "updatePassword"));
        menu.addItem(new MenuItem("Update firstname", this, "updateFirstname"));
        menu.addItem(new MenuItem("Update lastname", this, "updateLastname"));
        menu.addItem(new MenuItem("Update role", this, "updateRoleId"));
        menu.execute();
    }

    public void updateUsername() {
        db.printUser(di);
        db.updateQuery("username", di, true, false);
        db.printUser(di);
    }

    public void updatePassword() {
        db.printUser(di);
        db.updateQuery("password", di, false, false);
        db.printUser(di);
    }

    public void updateFirstname() {
        db.printUser(di);
        db.updateQuery("firstname", di, false, true);
        db.printUser(di);
    }

    public void updateLastname() {
        db.printUser(di);
        db.updateQuery("lastname", di, false, true);
        db.printUser(di);
    }

    public void updateRoleId() {
        db.printUser(di);
        db.updateQuery("roleID", di, false, false);
        db.printUser(di);
    }

    public void deleteMessage() {
        db.deletePersonalMessage();
    }

    public void myMessages() {
        Menu menu = new Menu();
        menu.setTitle("***  MY MESSAGES  ***");
        menu.addItem(new MenuItem("All messages", this, "printAllYourMessages"));
        menu.addItem(new MenuItem("Inbox", this, "printInboxMessages"));
        menu.addItem(new MenuItem("Sent", this, "printSentMessages"));
        menu.addItem(new MenuItem("Reply", this, "replyToInboxMessage"));
        menu.addItem(new MenuItem("Update message", this, "updateMyMessage"));
        menu.addItem(new MenuItem("Delete message", this, "deleteMessage"));
        menu.execute();
    }

    public void replyToGeneralMessage() {
        db.printAllMessages();
        db.replyToMessage(false);
    }

    public void replyToInboxMessage() {
        db.printUserInboxMessages();
        db.replyToMessage(true);
    }

    public void printAllYourMessages() {
        db.printUserAllMessages();
    }

    public void printSentMessages() {
        db.printUserSentMessages();
    }

    public void printInboxMessages() {
        db.printUserInboxMessages();
    }

    public void sendMessage() {
        msg = new Message();
        msg.setIdFrom(user.getId());
        msg.setSender(user.getUsername());
        db.sendMessage(msg, false);
    }

}
