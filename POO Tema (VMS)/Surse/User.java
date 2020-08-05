import java.util.ArrayList;
import java.util.List;

public class User implements Observer {
    private Integer user_ID;
    private String name;
    private String email;
    private String password;
    public enum UserType {
        ADMIN,
        GUEST
    }
    private UserType user_type;
    private UserVoucherMap<Integer, List<Voucher> > received_vouchers;
    private List<Notification> received_notifications;

    public User(Integer user_ID, String name, String email, String password, UserType user_type) {
        this.user_ID = user_ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.user_type = user_type;
        received_vouchers =new UserVoucherMap<>();
        received_notifications =new ArrayList<>();
    }

    public Integer getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(Integer user_ID) {
        this.user_ID = user_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUser_type() {
        return user_type;
    }

    public void setUser_type(UserType user_type) {
        this.user_type = user_type;
    }

    public UserVoucherMap<Integer, List<Voucher>> getReceived_vouchers() {
        return received_vouchers;
    }

    public void setReceived_vouchers(UserVoucherMap<Integer, List<Voucher>> received_vouchers) {
        this.received_vouchers = received_vouchers;
    }

    public List<Notification> getReceived_notifications() {
        return received_notifications;
    }

    public void setReceived_notifications(List<Notification> received_notifications) {
        this.received_notifications = received_notifications;
    }

    @Override
    public void Update(Notification notification) {
        received_notifications.add(notification); //Adding the notification to the user's list
    }

    @Override
    public String toString() {
        return  user_ID+";"+name+";"+email+";"+user_type;
    }
}
