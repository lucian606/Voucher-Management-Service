import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VMS_GUI {
    private JPanel panelStart;
    private JButton addFilesButton;
    private JTextField campaignsFileField;
    private JTextField usersFileField;
    private JTextField UsernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton importCampaignsButton;
    private JButton importFilesButton;
    private File users_file;
    private File campaigns_file;
    public static LocalDateTime date=LocalDateTime.now();
    private VMS vms;
    private User logged_user;

    public VMS getVms() {
        return vms;
    }

    public User getLogged_user() {
        return logged_user;
    }

    public VMS_GUI() {
        addFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                users_file=new File(usersFileField.getText());
                campaigns_file=new File(campaignsFileField.getText());
                if(users_file.exists() && campaigns_file.exists()) {
                    try {
                        vms=VMS.getInstance();
                        Scanner user_input =new Scanner(users_file);
                        Scanner campaigns_input=new Scanner(campaigns_file);
                        int i;
                        int number_of_users=Integer.parseInt(user_input.next());
                        for(i=1; i <= number_of_users; i++) {
                            String line=user_input.next();
                            String[] user_details=line.split(";",-1);
                            User new_user;
                            Integer id=Integer.valueOf(user_details[0]);
                            String name=user_details[1];
                            String email=user_details[3];
                            String password=user_details[2];
                            String privileges=user_details[4];
                            if(privileges.equals("ADMIN"))
                                new_user=new User(id,name,email,password,User.UserType.ADMIN);
                            else
                                new_user=new User(id,name,email,password,User.UserType.GUEST);
                            if(vms.getUser(id) == null)
                                vms.addUser(new_user);
                        }
                        int number_of_campaigns=Integer.parseInt(campaigns_input.next());
                        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String date_as_string=campaigns_input.next()+" "+campaigns_input.next();
                        campaigns_input.nextLine();
                        for(i=1; i <= number_of_campaigns; i++) {
                            String line=campaigns_input.nextLine();
                            String[] campaign_details=line.split(";",-1);
                            Integer id=Integer.valueOf(campaign_details[0]);
                            String name=campaign_details[1];
                            String description=campaign_details[2];
                            LocalDateTime start_date=LocalDateTime.parse(campaign_details[3],formatter);
                            LocalDateTime end_date=LocalDateTime.parse(campaign_details[4],formatter);
                            Integer number_of_vouchers=Integer.valueOf(campaign_details[5]);
                            char strat=campaign_details[6].charAt(0);
                            Campaign new_campaign=new Campaign(id,name,description,start_date,end_date,number_of_vouchers,strat);
                            if(date.equals(new_campaign.getStarting_date()) || date.isAfter(new_campaign.getStarting_date()))
                                new_campaign.setCampaign_status(Campaign.CampaignStatusType.STARTED);
                            if(date.equals(new_campaign.getEnding_date()) || date.isAfter(new_campaign.getEnding_date()))
                                new_campaign.setCampaign_status(Campaign.CampaignStatusType.EXPIRED);
                            if(vms.getCampaign(id) == null)
                                vms.addCampaign(new_campaign);
                        }
                    }
                    catch (IOException exc) {
                        System.out.println("FILES NOT FOUND");
                    }
                }
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vms=VMS.getInstance();
                int ok=0;
                ArrayList<User> users=(ArrayList<User>) vms.getUsers();
                for(User user : users) {
                    if(UsernameField.getText().equals(user.getName())) {
                        String password=String.valueOf(passwordField.getPassword());
                        if(password.equals(user.getPassword())) {
                            logged_user=user;
                            ok=1;
                        }
                    }
                    if(ok != 0)
                        break;
                }
                if(ok == 0)
                    JOptionPane.showMessageDialog(null, "LOGIN FAILED");
                else if(logged_user.getUser_type().equals(User.UserType.GUEST)) { //Guest page
                    JFrame guestFrame=new JFrame("GUEST");
                    guestFrame.setContentPane(new GUEST_GUI(logged_user.getUser_ID()).getGuestPanel());
                    guestFrame.setVisible(true);
                    guestFrame.pack();
                    guestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
                else if(logged_user.getUser_type().equals(User.UserType.ADMIN)) { //Admin page
                    JFrame adminFrame=new JFrame("ADMIN");
                    adminFrame.setContentPane(new ADMIN_GUI(logged_user.getUser_ID()).getAdminPanel());
                    adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    adminFrame.pack();
                    adminFrame.setVisible(true);
                }
            }
        });
        importCampaignsButton.addActionListener(new ActionListener() { //Importing files
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame importFrame=new JFrame("Import Campaigns File");
                importFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFileChooser campaigns_choice=new JFileChooser();
                campaigns_choice.setVisible(true);
                importFrame.add(campaigns_choice);
                importFrame.pack();
                importFrame.setVisible(true);
                if(campaigns_choice.showOpenDialog(importFrame) == JFileChooser.APPROVE_OPTION)
                campaignsFileField.setText(campaigns_choice.getSelectedFile().getAbsolutePath());
                importFrame.dispatchEvent(new WindowEvent(importFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
        importFilesButton.addActionListener(new ActionListener() { //Importing files
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame importFrame=new JFrame("Import Users File");
                importFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFileChooser users_choice=new JFileChooser();
                users_choice.setVisible(true);
                importFrame.add(users_choice);
                importFrame.pack();
                importFrame.setVisible(true);
                if(users_choice.showOpenDialog(importFrame) == JFileChooser.APPROVE_OPTION)
                    usersFileField.setText(users_choice.getSelectedFile().getAbsolutePath());
                importFrame.dispatchEvent(new WindowEvent(importFrame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        date=LocalDateTime.now();
        JFrame frame=new JFrame("VMS");
        frame.setContentPane(new VMS_GUI().panelStart);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
