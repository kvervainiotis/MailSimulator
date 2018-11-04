package kostas_verveniotis_cbproject1;

/**
 *
 * @author krocos
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private int roleId;

    public User() {
    }

    public User(int id, String username, String password, String firstName, String lastName, int roleId) {
        this.id = id;
        setRoleId(roleId);
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
        if (roleId == 1) {
            this.role = "super admin";
        } else if (roleId == 2) {
            this.role = "vip user";
        } else if (roleId == 3) {
            this.role = "super user";
        } else if (roleId == 4) {
            this.role = "power user";
        } else if (roleId == 5) {
            this.role = "user";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "\n******  MY DATA  ******"
                + "\n-----------------------"
                + "\nUser ID:   " + this.id + "\nUsername:  " + this.username + "\nPassword:  " + this.password + "\nFirstname: "
                + this.firstName + "\nLastname:  " + this.lastName + "\nRole:      " + this.role;
    }

}
