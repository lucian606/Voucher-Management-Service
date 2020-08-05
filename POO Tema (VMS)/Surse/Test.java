import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static LocalDateTime date;
    public static void main(String[] args) throws FileNotFoundException {
        int i;
        VMS vms=VMS.getInstance();
        Scanner stdin=new Scanner(System.in);
        System.out.println("Insert test number: ");
        int test_number=stdin.nextInt();
        File users_file=new File(".\\Teste\\test0"+test_number+"\\input\\users.txt");
        File events_file=new File(".\\Teste\\test0"+test_number+"\\input\\events.txt");
        File campaigns_file=new File(".\\Teste\\test0"+test_number+"\\input\\campaigns.txt");
        Scanner user_input=new Scanner(users_file);
        Scanner events_input=new Scanner(events_file);
        Scanner campaigns_input=new Scanner(campaigns_file);
        int number_of_users=Integer.parseInt(user_input.next());
        for(i=1; i <= number_of_users; i++) {
            String line=user_input.next(); //Each line has the details of a user
            String[] user_details=line.split(";",-1); //Splitting the details;
            User new_user;
            Integer id=Integer.valueOf(user_details[0]);
            String name=user_details[1];
            String password=user_details[2];
            String email=user_details[3];
            String privileges=user_details[4];
            if(privileges.equals("ADMIN"))
                new_user=new User(id,name,email,password,User.UserType.ADMIN);
            else
                new_user=new User(id,name,email,password,User.UserType.GUEST);
            vms.addUser(new_user);
        }
        int number_of_campaigns=Integer.parseInt(campaigns_input.next());
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String date_as_string=campaigns_input.next()+" "+campaigns_input.next();
        date=LocalDateTime.parse(date_as_string,formatter);
        campaigns_input.nextLine();
        for(i=1; i <= number_of_campaigns; i++) {
            String line=campaigns_input.nextLine(); //Each line has the details of a campaign
            String[] campaign_details=line.split(";",-1); //Splitting the details
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
            vms.addCampaign(new_campaign);
        }
        events_input.nextLine();
        int number_of_events=Integer.parseInt(events_input.nextLine());
        for(i=1; i <= number_of_events; i++) {
            String event=events_input.nextLine(); //Each line has the details of an event
            String[] event_details=event.split(";",-1); //Splitting the details
            String event_name=event_details[1];
            Integer user_id=Integer.valueOf(event_details[0]);
            if(event_name.equals("addCampaign")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    String name=event_details[3];
                    String description=event_details[4];
                    String start_date=event_details[5];
                    String end_date=event_details[6];
                    Integer number_of_vouchers=Integer.valueOf(event_details[7]);
                    char strat=event_details[8].charAt(0);
                    Campaign new_campaign=new Campaign(campaign_id,name,description,LocalDateTime.parse(start_date,formatter),LocalDateTime.parse(end_date,formatter),number_of_vouchers,strat);
                    vms.addCampaign(new_campaign);
                }
            }
            if(event_name.equals("editCampaign")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    String name=event_details[3];
                    String description=event_details[4];
                    LocalDateTime start_date=LocalDateTime.parse(event_details[5],formatter);
                    LocalDateTime end_date=LocalDateTime.parse(event_details[6],formatter);
                    Integer budget= Integer.valueOf(event_details[7]);
                    Campaign new_campaign=new Campaign(campaign_id,name,description,start_date,end_date,budget,'A');
                    vms.updateCampaign(campaign_id,new_campaign);
                    if(date.isAfter(vms.getCampaign(campaign_id).getEnding_date()))
                        vms.getCampaign(campaign_id).setCampaign_status(Campaign.CampaignStatusType.EXPIRED);
                }
            }
            if(event_name.equals("cancelCampaign")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    vms.cancelCampaign(campaign_id);
                }
            }
            if(event_name.equals("generateVoucher")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    String email=event_details[3];
                    String voucherType=event_details[4];
                    float value=(float) Integer.valueOf(event_details[5]);
                    User searched_user = vms.getUserbyEmail(email);
                    if(vms.getCampaign(campaign_id).getAvailable_vouchers() !=0) {
                        vms.getCampaign(campaign_id).addObserver(searched_user);
                    }
                    vms.getCampaign(campaign_id).generateVoucher(email,voucherType,value);
                }
            }
            if(event_name.equals("redeemVoucher")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    String email=event_details[3];
                    LocalDateTime date=LocalDateTime.parse(event_details[4],formatter);
                    vms.getCampaign(campaign_id).redeemVoucher(email,date);
                }
            }
            if(event_name.equals("getVouchers")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.GUEST))
                    System.out.println(vms.getUser(user_id).getReceived_vouchers());
            }
            if(event_name.equals("getObservers")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    System.out.println(vms.getCampaign(campaign_id).getObservers());
                }
            }
            if(event_name.equals("getNotifications")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.GUEST)) {
                    ArrayList<Notification> notifications=(ArrayList<Notification>) vms.getUser(user_id).getReceived_notifications();
                    for(Notification notification : notifications) {
                        Integer campaign_ID=notification.getCampaign_ID();
                        ArrayList<Voucher> user_vouchers=vms.getUser(user_id).getReceived_vouchers().get(campaign_ID);
                        ArrayList<Integer> voucher_ids=new ArrayList<>();
                        for(Voucher voucher : user_vouchers) {
                            voucher_ids.add(voucher.getVoucher_id());
                        }
                        notification.setVoucher_ids(voucher_ids);
                    }
                    System.out.println(vms.getUser(user_id).getReceived_notifications());
                }
            }
            if(event_name.equals("getVoucher")) {
                if(vms.getUser(user_id).getUser_type().equals(User.UserType.ADMIN)) {
                    Integer campaign_id=Integer.valueOf(event_details[2]);
                    Campaign campaign=vms.getCampaign(campaign_id);
                    if(!campaign.getCampaign_status().equals(Campaign.CampaignStatusType.CANCELLED)) {
                        Voucher result=vms.execute(campaign);
                        if(result != null)
                            System.out.println(result);
                    }
                }
            }
        }
    }
}
