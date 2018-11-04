package kostas_verveniotis_cbproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krocos
 */
public class DatabaseCreation {

    private static final String DB_URL = "localhost:3306";
    private static final String FULL_DB_URL = "jdbc:mysql://" + DB_URL + "/Kostas_Verve_Project1?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWD = "krocos1925";

    public Connection connect() {
        Connection connection = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: unable to load driver class!");
                System.out.println(ex.toString());
                System.exit(1);
            }
            connection = DriverManager.getConnection(FULL_DB_URL, DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            System.out.println("Sorry, problems with the database connection!");
            System.out.println(ex.toString());
            System.exit(0);
        }
        return connection;
    }

    public void createDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + DB_URL + "/?zeroDateTimeBehavior=convert_To_Null&useSSL=false", DB_USER, DB_PASSWD);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Creating database...");
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DROP DATABASE IF EXISTS `Kostas_Verve_Project1` ;");
            String sql = "CREATE DATABASE `Kostas_Verve_Project1`";
            stmt.executeUpdate(sql);
            stmt.executeUpdate("ALTER SCHEMA `kostas_verve_project1`  DEFAULT CHARACTER SET utf8  DEFAULT COLLATE utf8_general_ci ;");
            System.out.println("Database created successfully...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createUsers() {
        Connection con = connect();
        Statement stmt = null;
        System.out.println("Creating table users in given database...");
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE `kostas_verve_project1`.`users` (\n"
                    + "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `username` VARCHAR(25) NOT NULL,\n"
                    + "  `password` VARCHAR(45) NOT NULL,\n"
                    + "  `firstname` VARCHAR(25) NOT NULL,\n"
                    + "  `lastname` VARCHAR(25) NOT NULL,\n"
                    + "  `roleID` INT(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,\n"
                    + "  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,\n"
                    + "  INDEX `roleID_idx` (`roleID` ASC) VISIBLE,\n"
                    + "  CONSTRAINT `roleID`\n"
                    + "    FOREIGN KEY (`roleID`)\n"
                    + "    REFERENCES `kostas_verve_project1`.`roles` (`id`)\n"
                    + "    ON DELETE NO ACTION\n"
                    + "    ON UPDATE NO ACTION)\n"
                    + "ENGINE = InnoDB\n"
                    + "DEFAULT CHARACTER SET = utf8;";
            stmt.executeUpdate(sql);
            System.out.println("Created table users in given database...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createMessage() {
        Connection con = connect();
        Statement stmt = null;
        System.out.println("Creating table message in given database...");
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE `kostas_verve_project1`.`message` (\n"
                    + "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `message_data` VARCHAR(250) NOT NULL,\n"
                    + "  `date` TIMESTAMP NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n"
                    + "ENGINE = InnoDB\n"
                    + "DEFAULT CHARACTER SET = utf8;";
            stmt.executeUpdate(sql);
            System.out.println("Created table message in given database...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createRole() {
        Connection con = connect();
        Statement stmt = null;
        System.out.println("Creating table role in given database...");
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE `kostas_verve_project1`.`roles` (\n"
                    + "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `role` VARCHAR(45) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)\n"
                    + "ENGINE = InnoDB\n"
                    + "DEFAULT CHARACTER SET = utf8;";
            stmt.executeUpdate(sql);
            System.out.println("Created table role in given database...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void createSendMessage() {
        Connection con = connect();
        Statement stmt = null;
        System.out.println("Creating table send_message in given database...");
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS `kostas_verve_project1`.`send_message` (\n"
                    + "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `id_from` INT(11) NOT NULL,\n"
                    + "  `id_to` INT(11) NOT NULL,\n"
                    + "  `id_message` INT(11) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  INDEX `id_from_idx` (`id_from` ASC) VISIBLE,\n"
                    + "  INDEX `id_to_idx` (`id_to` ASC) VISIBLE,\n"
                    + "  INDEX `id_message_idx` (`id_message` ASC) VISIBLE,\n"
                    + "  CONSTRAINT `id_from`\n"
                    + "    FOREIGN KEY (`id_from`)\n"
                    + "    REFERENCES `kostas_verve_project1`.`users` (`id`)\n"
                    + "    ON DELETE NO ACTION\n"
                    + "    ON UPDATE NO ACTION,\n"
                    + "  CONSTRAINT `id_to`\n"
                    + "    FOREIGN KEY (`id_to`)\n"
                    + "    REFERENCES `kostas_verve_project1`.`users` (`id`)\n"
                    + "    ON DELETE NO ACTION\n"
                    + "    ON UPDATE NO ACTION,\n"
                    + "  CONSTRAINT `id_message`\n"
                    + "    FOREIGN KEY (`id_message`)\n"
                    + "    REFERENCES `kostas_verve_project1`.`message` (`id`)\n"
                    + "    ON DELETE NO ACTION\n"
                    + "    ON UPDATE NO ACTION)\n"
                    + "ENGINE = InnoDB\n"
                    + "DEFAULT CHARACTER SET = utf8;";
            stmt.executeUpdate(sql);
            System.out.println("Created table send_message in given database...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void alterUsers() {
        Connection con = connect();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO `kostas_verve_project1`.`roles` (`id`, `role`) VALUES ('1', 'super admin');";
            stmt.execute(sql);
            sql = "INSERT INTO `kostas_verve_project1`.`roles` (`id`, `role`) VALUES ('2', 'vip user');";
            stmt.execute(sql);
            sql = "INSERT INTO `kostas_verve_project1`.`roles` (`id`, `role`) VALUES ('3', 'super user');";
            stmt.execute(sql);
            sql = "INSERT INTO `kostas_verve_project1`.`roles` (`id`, `role`) VALUES ('4', 'power user');";
            stmt.execute(sql);
            sql = "INSERT INTO `kostas_verve_project1`.`roles` (`id`, `role`) VALUES ('5', 'user');";
            stmt.execute(sql);
            System.out.println("Roles have been added to table roles...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void insertAdmin() {
        Connection con = connect();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String sql = "INSERT INTO `kostas_verve_project1`.`users` (`id`, `username`, `password`, `firstname`, `lastname`, `roleID`) "
                    + "VALUES ('1', 'admin', 'admin', 'Kostas', 'Verveniotis', '1');";
            stmt.execute(sql);
            System.out.println("Admin has been added to the database...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void alterUpdate() {
        String sql = "ALTER TABLE `kostas_verve_project1`.`message`"
                + "ADD COLUMN `updated` TIMESTAMP NULL AFTER `date`";
        Connection con = connect();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Updated has been added to the table message...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void alterSendMessage() {
        String sql = "ALTER TABLE `kostas_verve_project1`.`send_message` \n"
                + "ADD COLUMN `deleted_by` INT(11) NULL AFTER `id_message`;";
        Connection con = connect();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Deleted_by has been added to the table send_message...");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseCreation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
