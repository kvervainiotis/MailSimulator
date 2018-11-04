package kostas_verveniotis_cbproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krocos
 */
public class Database {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/Kostas_Verve_Project1?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&allowPublicKeyRetrieval=true";
    private static final String DB_USER = "user";
    private static final String DB_PASSWD = "123!@#";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private User user;

    public Database() {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public Connection connect() {
        Connection connection = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: unable to load driver class!!!");
                System.out.println(ex.toString());
                System.exit(1);
            }
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!!!");
            System.out.println(ex.toString());
            System.exit(0);
        }
        return connection;
    }

    public void closeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeStatement(Statement stm) {
        try {
            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User checkLogin(String username, String password) {
        String sql = "SELECT * FROM users";
        User temp = null;
        Scanner scanner = new Scanner(System.in);
        Connection c = connect();
        try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                if (resultSet.getString(2).equals(username) && resultSet.getString(3).equals(password)) {
                    temp = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6));
                    closeConnection(c);
                    return temp;
                }
            }
            System.out.println("Username or (and) password is wrong!!!");
            LoginMenu lm = new LoginMenu();
            temp = lm.loginMenu(this);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        } finally {
            closeConnection(c);
        }
        return temp;
    }

    public void createUser() {
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give username or type 'exit' to return: ");
        String username = scanner.nextLine();
        username = Helper.checkStringLength(username);
        username = checkIfUsernameExists(username);
        if (username.equals("exit")) {
            return;
        }
        System.out.println("Give password or type 'exit' to return: ");
        String password = scanner.nextLine();
        password = Helper.checkPassLength(password);
        if (password.equals("exit")) {
            return;
        }
        System.out.println("Give firstame or type 'exit' to return: ");
        String firstname = scanner.nextLine();
        firstname = Helper.checkStringLength(firstname);
        if (firstname.equals("exit")) {
            return;
        }
        firstname = Helper.isCapital(firstname);
        System.out.println("Give lastname or type 'exit' to return: ");
        String lastname = scanner.nextLine();
        lastname = Helper.checkStringLength(lastname);
        if (lastname.equals("exit")) {
            return;
        }
        lastname = Helper.isCapital(lastname);
        this.printRoleIds();
        System.out.println("Give role id from the above list or type 'exit' to return: ");
        String rid = scanner.nextLine();
        int roleId = Helper.checkRoleId(rid);
        if (roleId == 0) {
            return;
        }
        conn = connect();
        String sql;
        sql = new StringBuilder()
                .append("INSERT users (username, password, firstName, lastName, roleId )\n")
                .append("VALUES (?, ?, ?, ?, ?);").toString();
        if (Helper.requestConfirmation() == false) {
            System.out.println("\nCreation of user '" + username + "' was cancelled...");
            return;
        }
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            statement.setInt(5, roleId);
            int rowsAffected = statement.executeUpdate();
            System.out.println("\nUser '" + username + "' was created successfully!!!");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        closeConnection(conn);
    }

    public int checkUserId(int id) {
        Connection c = null;
        String di = Integer.toString(id);
        String sql = "SELECT id FROM users";   //COUNT *
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        c = connect();
        while (flag == false) {
            try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    if (resultSet.getInt("id") == id) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
                if (flag == false) {
                    if (di.equals("exit")) {
                        closeConnection(c);
                        return 0;
                    }
                    System.out.println("'" + di + "' is not a valid ID1! ! !\nChoose another ID or type exit to return:");
                    di = scanner.nextLine();
                    while (!Helper.containsOnlyNumbers(di)) {
                        if (di.equals("exit")) {
                            closeConnection(c);
                            return 0;
                        }
                        System.out.println("'" + di + "' is not a valid ID2! ! !\nChoose another ID or type exit to return:");
                        di = scanner.nextLine();
                    }
                    id = Integer.parseInt(di);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConnection(c);
        return id;
    }

    public String checkIfUsernameExists(String username) {
        Connection c = null;
        String sql = "SELECT username FROM users";
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        c = connect();
        while (flag == false) {
            try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    if (resultSet.getString("username").equals(username) == true) {
                        flag = false;
                        break;
                    } else {
                        flag = true;
                    }
                }
                if (flag == false) {
                    System.out.println("Username '" + username + "' already exist, choose another one or type exit to return: ");
                    username = scanner.nextLine();
                    if (username.equals("εxit")) {
                        return username;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConnection(c);
        return username;
    }

    public void deleteUser() {
        Connection c = null;
        this.printUsers();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose user id you wish to delete or type exit to return: ");
        String di = scanner.nextLine();
        while (!Helper.containsOnlyNumbers(di)) {
            if (di.equals("exit")) {
                return;
            }
            System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
            di = scanner.nextLine();
        }
        int id = Integer.parseInt(di);
        id = this.checkUserId(id);
        if (id == 0) {
            return;
        }
        if (Helper.requestConfirmation() == false) {
            System.out.println("\nDelete was cancelled...");
            return;
        }
        this.deleteUserMessages(id);
        String sql = "DELETE FROM users WHERE id = " + id + ";";
        c = connect();
        try {
            try (PreparedStatement statement = c.prepareStatement(sql)) {
                int rowsAffected = statement.executeUpdate();
                System.out.println("\nUser '" + id + "' was deleted successfully!!!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);;
        } finally {
            closeConnection(c);
        }
    }

    public void deleteMessage() {
        Connection c = null;
        this.printAllMessages();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose message id you wish to delete or type 'exit' to return: ");
        String di = scanner.nextLine();
        while (!Helper.containsOnlyNumbers(di)) {
            if (di.equals("exit")) {
                return;
            }
            System.out.println("'" + di + "' is not a valid ID ! ! !\nChoose another ID or type exit to return:");
            di = scanner.nextLine();
        }
        int id = Integer.parseInt(di);
        id = this.checkMessageId(id);
        if (id == 0) {
            return;
        }
        c = connect();
        String sql = "DELETE from Kostas_Verve_Project1.send_message \n"
                + "WHERE send_message.id_message = " + id + ";";
        if (Helper.requestConfirmation() == false) {
            System.out.println("\nDelete was cancelled...");
            return;
        }
        try (PreparedStatement statement = c.prepareStatement(sql)) {;
            int rowsAffected = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        sql = "DELETE from Kostas_Verve_Project1.message \n"
                + "WHERE message.id = " + id + ";";
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            int rowsAffected = statement.executeUpdate();
            System.out.println("\nMessage '" + id + "' was deleted successfully!!!");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
        }
    }

    public void deleteUserMessages(int userId) {
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        String sql = "SELECT send_message.id_from, send_message.id_to, message.id\n"
                + "FROM send_message, message\n"
                + "WHERE ((send_message.id_from = " + userId + ") AND (message.id = send_message.id_message))\n"
                + "OR ((send_message.id_to = " + userId + ") AND (message.id = send_message.id_message))";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                String sql1 = "DELETE from Kostas_Verve_Project1.send_message \n"
                        + "WHERE send_message.id_from = " + userId + " AND send_message.id_message = " + rs.getInt(3) + "\n"
                        + "OR send_message.id_to = " + userId + " AND send_message.id_message = " + rs.getInt(3) + ";";
                try (PreparedStatement statement = c.prepareStatement(sql1)) {;
                    int rowsAffected = statement.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
                String sql2 = "DELETE from Kostas_Verve_Project1.message \n"
                        + "WHERE message.id = " + rs.getInt(3) + ";";
                try (PreparedStatement statement = c.prepareStatement(sql2)) {
                    int rowsAffected = statement.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void updateQuery(String field, int id, boolean isUsername, boolean isName) {
        Connection c = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose new " + field + ": ");
        String newField = null;
        if (isUsername == true) {
            newField = scanner.nextLine();
            newField = Helper.checkStringLength(newField);
            newField = checkIfUsernameExists(newField);
        }
        if (isName == true) {
            newField = scanner.nextLine();
            newField = Helper.checkStringLength(newField);
            newField = Helper.isCapital(newField);
        }
        if (field.equals("password")) {
            newField = scanner.nextLine();
            newField = Helper.checkPassLength(newField);
        }
        if (field.equals("roleID")) {
            this.printRoleIds();
            newField = scanner.nextLine();
            int roleId = Helper.checkRoleId(newField);
            newField = Integer.toString(roleId);
        }
        String sql = "UPDATE `Kostas_Verve_Project1`.`users` SET `" + field + "` = '" + newField + "' WHERE (`id` = '" + id + "');";
        if (Helper.requestConfirmation() == false) {
            System.out.println("\nUpdate was cancelled...");
            return;
        }
        c = connect();
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            int rowsAffected = statement.executeUpdate();
            System.out.println("\n" + field + " was updated successfully!!!");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
        }
    }

    public void updateMessage(int flag) {
        Connection c = null;
        Scanner scanner = new Scanner(System.in);
        if (flag == 1) {
            this.printAllMessages();
        } else {
            this.printUserSentMessages();
        }
        System.out.println("\nChoose message id you wish to update or type 'exit' to return: ");
        String id = scanner.nextLine();
        while (!Helper.containsOnlyNumbers(id)) {
            if (id.equals("exit")) {
                return;
            }
            System.out.println("'" + id + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
            id = scanner.nextLine();
        }
        int di = Integer.parseInt(id);
        if (flag == 1) {
            di = checkMessageId(di);
        } else {
            di = this.checkIdToUpdatePersonalMessage(di);
        }
        if (di == 0) {
            return;
        }
        System.out.println("Type new message: ");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String message = scanner.nextLine();
        message = Helper.checkMessageLength(message);
        String sql = "UPDATE `Kostas_Verve_Project1`.`message` SET `message_data` = '" + message + "' WHERE (`id` = '" + di + "');";
        if (Helper.requestConfirmation() == false) {
            System.out.println("\nUpdate was cancelled...");
            return;
        }
        c = connect();
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            int rowsAffected = statement.executeUpdate();
            System.out.println("\nMessage '" + di + "' was updated successfully!!!");
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }
        String sql2 = "UPDATE `Kostas_Verve_Project1`.`message` SET `updated` = '" + timestamp + "' WHERE (`id` = '" + di + "');";
        try (PreparedStatement statement = c.prepareStatement(sql2)) {
            int rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            printUpdatedMessages(di);
            closeConnection(c);
        }
    }

    public void printUserAllMessages() {
        System.out.println();
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        System.out.println("*******************************************************************   "
                + user.getUsername() + "   *******************************************************************");
        System.out.println("----------------------------------------------------------------------"
                + "---------------------------------------------------------------------------");
        System.out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
        System.out.println("---------------------------------------------------------------------"
                + "----------------------------------------------------------------------------");
        String sql = "SELECT sender.username , receiver.username, message.id, message.date, updated, message.message_data\n"
                + "FROM send_message, message, users AS sender, users AS receiver\n"
                + "WHERE (message.id = send_message.id_message) AND (sender.id = send_message.id_from) AND (receiver.id = send_message.id_to)\n"
                + "AND (sender.id = " + user.getId() + " OR receiver.id = " + user.getId() + ") AND deleted_by != " + user.getId() + ";";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%12d%27s%27s%22s%22s%35s%n", rs.getInt(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void printUpdatedMessages(int msgId) {
        System.out.println();
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        System.out.println("***************************************************************"
                + "  UPDATED MESSAGE  ***************************************************************");
        System.out.println("---------------------------------------------------------------------"
                + "----------------------------------------------------------------------------");
        System.out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
        System.out.println("--------------------------------------------------------------------"
                + "-----------------------------------------------------------------------------");
        String sql = "SELECT sender.username , receiver.username, message.id, message.date, message.updated, message.message_data\n"
                + "FROM send_message, message, users AS sender, users AS receiver\n"
                + "WHERE (message.id = send_message.id_message) AND (sender.id = send_message.id_from) AND (receiver.id = send_message.id_to)\n"
                + "AND (message.id = " + msgId + ");";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%12d%27s%27s%22s%22s%35s%n", rs.getInt(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void printUserInboxMessages() {
        System.out.println();
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        System.out.println("********************************************************************  INBOX  ********************************************************************");
        System.out.println("------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------");
        System.out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
        System.out.println("------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------");
        String sql = "SELECT sender.username , receiver.username, message.id, message.date,message.updated, message.message_data\n"
                + "FROM send_message, message, users AS sender, users AS receiver\n"
                + "WHERE (message.id = send_message.id_message) AND (sender.id = send_message.id_from) AND (receiver.id = send_message.id_to)\n"
                + "AND (receiver.id = " + user.getId() + ") AND deleted_by != " + user.getId() + ";";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%12d%27s%27s%22s%22s%35s%n", rs.getInt(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void printUserSentMessages() {
        System.out.println();
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        System.out.println("********************************************************************  SENT  *********************************************************************");
        System.out.println("------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------");
        System.out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
        System.out.println("------------------------------------------------------------------------"
                + "-------------------------------------------------------------------------");
        String sql = "SELECT sender.username , receiver.username, message.id, message.date,message.updated, message.message_data\n"
                + "FROM send_message, message, users AS sender, users AS receiver\n"
                + "WHERE (message.id = send_message.id_message) AND (sender.id = send_message.id_from) AND (receiver.id = send_message.id_to)\n"
                + "AND (sender.id = " + user.getId() + ") AND deleted_by != " + user.getId() + ";";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%12d%27s%27s%22s%22s%35s%n", rs.getInt(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void printAllMessages() {
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        System.out.println();
        System.out.println("********************************************************************"
                + "  LOBBY  ********************************************************************");
        System.out.println("----------------------------------------------------------------------"
                + "---------------------------------------------------------------------------");
        System.out.printf("%12s%27s%27s%22s%22s%35s%n", "MESSAGE ID", "SENDER", "RECEIVER", "RECEIVED DATE", "UPDATED DATE", "MESSAGE DATA");
        System.out.println("---------------------------------------------------------------------"
                + "----------------------------------------------------------------------------");
        String sql = "SELECT sender.username , receiver.username, message.id, message.date, updated, message.message_data\n"
                + "FROM send_message, message, users AS sender, users AS receiver\n"
                + "WHERE (message.id = send_message.id_message) AND (sender.id = send_message.id_from) AND (receiver.id = send_message.id_to) AND deleted_by != " + user.getId() + ";";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                System.out.printf("%12d%27s%27s%22s%22s%35s%n", rs.getInt(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void backup() {
        Connection c = connect();
        Statement stm = null;
        ResultSet rs = null;
        FileManipulation fm = new FileManipulation();
        System.out.println();
        String sql = "SELECT sender.username , receiver.username, message.id, message.date,message.updated, message.message_data\n"
                + "FROM send_message, message, users AS sender, users AS receiver\n"
                + "WHERE (message.id = send_message.id_message) AND (sender.id = send_message.id_from) AND (receiver.id = send_message.id_to)";
        try {
            stm = c.createStatement();
            rs = stm.executeQuery(sql);
            fm.dateBackup();
            while (rs.next()) {
                fm.backupFiles(rs.getInt(3), rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(5), rs.getString(6));
            }
            System.out.println("Files were stored in file 'backup.txt' successfully!!!");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stm);
            closeResultSet(rs);
        }
    }

    public void printUsersForMessage() {
        Connection c = connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT users.id, users.username, roles.role\n"
                    + "FROM users, roles\n"
                    + "WHERE users.roleID = roles.id;");
            System.out.println("\n***************  FRIENDS LIST  ***************");
            System.out.println("---------------------"
                    + "-------------------------");
            System.out.printf("%3s%30s%13s%n", "ID", "USERNAME", "ROLE");
            System.out.println("---------------------"
                    + "-------------------------");
            while (rs.next()) {
                System.out.printf("%3d%30s%13s%n", rs.getInt(1), rs.getString(2), rs.getString(3));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    public void printUsers() {
        Connection c = connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT users.id, users.username, users.password, users.firstName, users.lastName, users.roleID, roles.role\n"
                    + "FROM users, roles\n"
                    + "WHERE users.roleID = roles.id;");
            System.out.println("\n**********************************************************************  USERS  **********************************************************************");
            System.out.println("---------------------------------------------------------------------------"
                    + "--------------------------------------------------------------------------");
            System.out.printf("%3s%30s%30s%30s%30s%13s%13s%n", "ID", "USERNAME", "PASSWORD", "FIRSTNAME", "LASTNAME", "ROLE ID", "ROLE");
            System.out.println("---------------------------------------------------------------------------"
                    + "--------------------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%3d%30s%30s%30s%30s%13d%13s%n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    public void printUser(int id) {
        Connection c = connect();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT users.id, users.username, users.password, users.firstName, users.lastName, users.roleID, roles.role\n"
                    + "FROM users, roles\n"
                    + "WHERE users.roleID = roles.id AND users.id = " + id + ";");
            rs.first();
            System.out.println();
            System.out.println("\n*******************************************************************  UPDATE USER  ****"
                    + "***************************************************************");
            System.out.printf("%3s%30s%30s%30s%30s%13s%13s%n", "ID", "USERNAME", "PASSWORD", "FIRSTNAME", "LASTNAME", "ROLE ID", "ROLE");
            System.out.println("------------------------------------------------------------------------"
                    + "-----------------------------------------------------------------------------");
            System.out.printf("%3d%30s%30s%30s%30s%13d%13s%n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7));
            System.out.println();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    public int checkIdToUpdatePersonalMessage(int id) {
        Connection c = connect();
        Scanner scanner = new Scanner(System.in);
        String di = Integer.toString(id);
        try {
            String sql = "SELECT * FROM send_message, message WHERE id_from = " + user.getId() + " AND send_message.id_message = message.id "
                    + "AND send_message.deleted_by != " + user.getId() + ";";
            boolean flag = false;
            while (flag == false) {
                try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("id_from") == user.getId() && resultSet.getInt("id_message") == id) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag == false) {
                        System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                        di = scanner.nextLine();
                        while (!Helper.containsOnlyNumbers(di)) {
                            if (di.equals("exit")) {
                                closeConnection(c);
                                return 0;
                            }
                            System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                            di = scanner.nextLine();
                        }
                        id = Integer.parseInt(di);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            closeConnection(c);
        }
        return id;
    }

    public int checkMessageId(int id) {
        Connection c = connect();
        Scanner scanner = new Scanner(System.in);
        String di = Integer.toString(id);
        try {
            String sql = "SELECT id_message, deleted_by FROM send_message";
            boolean flag = false;
            while (flag == false) {
                try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("id_message") == id && resultSet.getInt("deleted_by") != user.getId()) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag == false) {
                        System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                        di = scanner.nextLine();
                        while (!Helper.containsOnlyNumbers(di)) {
                            if (di.equals("exit")) {
                                closeConnection(c);
                                return 0;
                            }
                            System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                            di = scanner.nextLine();
                        }
                        id = Integer.parseInt(di);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            closeConnection(c);
        }
        return id;
    }

    public int checkIdToDeletePersonalMessage(int id) {
        Connection c = connect();
        Scanner scanner = new Scanner(System.in);
        String di = Integer.toString(id);
        try {
            String sql = "SELECT * FROM send_message, message WHERE (id_from = " + user.getId() + " OR id_to = " + user.getId() + ") AND send_message.id_message = message.id "
                    + "AND send_message.deleted_by != " + user.getId() + ";";
            boolean flag = false;
            while (flag == false) {
                try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        if ((resultSet.getInt("id_from") == user.getId() || resultSet.getInt("id_to") == user.getId()) && resultSet.getInt("id_message") == id) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag == false) {
                        System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                        di = scanner.nextLine();
                        while (!Helper.containsOnlyNumbers(di)) {
                            if (di.equals("exit")) {
                                closeConnection(c);
                                return 0;
                            }
                            System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                            di = scanner.nextLine();
                        }
                        id = Integer.parseInt(di);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            closeConnection(c);
        }
        return id;
    }

    public void deletePersonalMessage() {
        Connection c = null;
        this.printUserAllMessages();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nChoose message id you wish to delete or type 'exit' to return: ");
        String di = scanner.nextLine();
        while (!Helper.containsOnlyNumbers(di)) {
            if (di.equals("exit")) {
                return;
            }
            System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type 'exit' to return:");
            di = scanner.nextLine();
        }
        int id = Integer.parseInt(di);
        id = checkIdToDeletePersonalMessage(id);
        if (id == 0) {
            return;
        }
        c = connect();
        int isDeleted = -1;
        int idFrom = 0;
        int idTo = 0;
        String sql1 = "SELECT deleted_by, id_from, id_to FROM send_message WHERE id_message = " + id + ";";
        try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql1)) {
            resultSet.first();
            isDeleted = resultSet.getInt("deleted_by");
            idFrom = resultSet.getInt("id_from");
            idTo = resultSet.getInt("id_to");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (Helper.requestConfirmation() == false) {
            System.out.println("\nDelete was cancelled...");
            return;
        }
        if ((isDeleted == 0) && (idFrom != idTo)) {
            String sql2 = "UPDATE send_message SET deleted_by = " + user.getId() + " WHERE id_message = " + id + ";";
            try (PreparedStatement statement = c.prepareStatement(sql2)) {
                int rowsAffected = statement.executeUpdate();
                System.out.println("\nMessage '" + id + "' deleted successfully!");

            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String sql = "DELETE from Kostas_Verve_Project1.send_message \n"
                    + "WHERE send_message.id_message = " + id + ";";
            try (PreparedStatement statement = c.prepareStatement(sql)) {;
                int rowsAffected = statement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            sql = "DELETE from Kostas_Verve_Project1.message \n"
                    + "WHERE message.id = " + id + ";";
            try (PreparedStatement statement = c.prepareStatement(sql)) {
                int rowsAffected = statement.executeUpdate();
                System.out.println("\nMessage '" + id + "' deleted successfully!");
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConnection(c);
    }

    public void sendMessage(Message m, boolean reply) {
        Connection c = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        if (reply == false) {
            m = prepareMessage(m);
        }
        if (m == null) {
            return;
        }
        String time = sdf.format(m.getDate());
        String sql = new StringBuilder()
                .append("INSERT INTO `Kostas_Verve_Project1`.`message` (`message_Data`, `date`) ")
                .append("VALUES (?, ?);").toString();
        c = connect();
        try (PreparedStatement statement = c.prepareStatement(sql)) {
            statement.setString(1, m.getMessageData());
            statement.setString(2, time);

            int rowsAffected = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql2 = "SELECT id FROM message WHERE date LIKE '" + time + "';";
        try {
            stmt1 = c.createStatement();
            rs = stmt1.executeQuery(sql2);
            int id = 0;
            rs.first();
            m.setId(rs.getInt(1));
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        String from = Integer.toString(m.getIdFrom());
        String to = Integer.toString(m.getIdTo());
        String idMessage = Integer.toString(m.getId());
        String sql3 = new StringBuilder()
                .append("INSERT INTO `Kostas_Verve_Project1`.`send_message` (`id_from`, `id_to`, `id_message`, `deleted_by`) ")
                .append("VALUES (?, ?, ?, ?);").toString();
        try (PreparedStatement statement = c.prepareStatement(sql3)) {
            statement.setString(1, from);
            statement.setString(2, to);
            statement.setString(3, idMessage);
            statement.setInt(4, 0);
            int rowsAffected = statement.executeUpdate();
            System.out.println("\nMessage delivered!!!");
            FileManipulation fm = new FileManipulation();
            fm.checkDestination(m);
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        } finally {
            closeConnection(c);
            closeStatement(stmt1);
            closeResultSet(rs);
        }
    }

    public int checkUsername(String username, Message m) {
        int idTo = 0;
        Connection c = null;
        String sql = "SELECT id, username FROM users";
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        c = connect();
        while (flag == false) {
            try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    if (resultSet.getString("username").equals(username) == true) {
                        idTo = resultSet.getInt("id");
                        m.setReceiver(username);
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
                if (flag == false) {
                    if (username.equals("exit")) {
                        closeConnection(c);
                        return 0;
                    }
                    System.out.println("Username '" + username + "' doesnt exist! ! ! Choose another username one or type exit to return: ");
                    username = scanner.nextLine();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        closeConnection(c);
        return idTo;
    }

    public Message prepareMessage(Message m) {
        this.printUsersForMessage();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select user you wish to send a message to or type 'exit' to return: ");
        String username = scanner.nextLine();
        int idTo = checkUsername(username, m);
        if (idTo == 0) {
            return null;
        }
        System.out.println("Type your message: ");
        String messageData = scanner.nextLine();
        m.setIdTo(idTo);
        m.setMessageData(messageData);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        m.setDate(timestamp);
        return m;
    }

    public void printRoleIds() {
        Connection c = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            c = connect();
            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT roles.id, roles.role\n"
                    + "FROM roles;");
            int width = 20;
            String format = "%" + width + "s";
            System.out.println("\n******* ROLES *******");
            System.out.printf("ID");
            System.out.printf(format, "ROLE\n");
            System.out.println("---------------------");
            while (rs.next()) {
                System.out.print(rs.getInt(1));
                System.out.printf(format, rs.getString(2));
                System.out.println();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection(c);
            closeStatement(stmt);
            closeResultSet(rs);
        }
    }

    public void replyToMessage(boolean isPersonalMessage) {
        Message m = new Message();
        System.out.println("Give message id you wish to reply, or type 'exit' to return: ");
        Scanner scanner = new Scanner(System.in);
        String di = scanner.nextLine();
        while (!Helper.containsOnlyNumbers(di)) {
            if (di.equals("exit")) {
                return;
            }
            System.out.println("'" + di + "' is not a valid ID ! ! !\nChoose another ID or type exit to return:");
            di = scanner.nextLine();
        }
        int id = Integer.parseInt(di);
        if (isPersonalMessage == true) {
            m = this.checkINboxMessageForReply(id);
        } else {
            m = this.checkGeneralMessageIdForReply(id);
        }
        if (m == null) {
            return;
        }
        System.out.println("Type your message: ");
        String messageData = scanner.nextLine();
        m.setSender(user.getUsername());
        m.setMessageData(messageData);
        m.setReceiver(findReceiverUsername(m.getIdTo()));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        m.setDate(timestamp);
        this.sendMessage(m, true);
    }

    public String findReceiverUsername(int id) {
        String username = null;
        Connection c = connect();
        String sql = "SELECT users.username FROM users WHERE id = " + id + ";";
        try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }

    public Message checkGeneralMessageIdForReply(int id) {
        Message m = new Message();
        Connection c = connect();
        Scanner scanner = new Scanner(System.in);
        String di = Integer.toString(id);
        try {
            boolean flag = false;
            while (flag == false) {
                String sql = "SELECT send_message.id_from, send_message.id_to, send_message.id_message FROM send_message, message "
                        + "WHERE send_message.id_message = message.id AND send_message.id_message = " + id + " AND send_message.deleted_by != " + user.getId() + ";";
                try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("id_message") == id) {
                            m.setIdFrom(user.getId());
                            m.setIdTo(resultSet.getInt("id_from"));
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag == false) {
                        System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                        di = scanner.nextLine();
                        while (!Helper.containsOnlyNumbers(di)) {
                            if (di.equals("exit")) {
                                closeConnection(c);
                                return null;
                            }
                            System.out.println("'" + di + "' is not a valid ID! ! !\nChoose another ID or type exit to return:");
                            di = scanner.nextLine();
                        }
                        id = Integer.parseInt(di);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            closeConnection(c);
        }
        return m;
    }

    public Message checkINboxMessageForReply(int id) {
        Connection c = connect();
        Message m = new Message();
        Scanner scanner = new Scanner(System.in);
        String di = Integer.toString(id);
        try {
            boolean flag = false;
            while (flag == false) {
                String sql = "SELECT send_message.id_from, send_message.id_to, send_message.id_message FROM send_message, message WHERE id_to = " + user.getId()
                        + " AND send_message.id_message = message.id AND send_message.id_message = " + id + " AND send_message.deleted_by != " + user.getId() + ";";
                try (Statement statement = c.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        if (resultSet.getInt("id_to") == user.getId() && resultSet.getInt("id_message") == id) {
                            m.setIdFrom(user.getId());
                            m.setIdTo(resultSet.getInt("id_from"));
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                    if (flag == false) {
                        System.out.println("'" + di + "' is not a valid ID11! ! !\nChoose another ID or type exit to return:");
                        di = scanner.nextLine();
                        while (!Helper.containsOnlyNumbers(di)) {
                            if (di.equals("exit")) {
                                closeConnection(c);
                                return null;
                            }
                            System.out.println("'" + di + "' is not a valid ID22! ! !\nChoose another ID or type exit to return:");
                            di = scanner.nextLine();
                        }
                        id = Integer.parseInt(di);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            closeConnection(c);
        }
        return m;
    }

}
