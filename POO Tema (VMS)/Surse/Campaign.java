import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Campaign implements Observable {
    private Integer campaign_ID;
    private String campaign_name;
    private String description;
    private LocalDateTime starting_date;
    private LocalDateTime ending_date;
    private Integer number_of_vouchers;
    private Integer available_vouchers;

    public enum CampaignStatusType {
        NEW,
        STARTED,
        EXPIRED,
        CANCELLED
    }

    private CampaignStatusType campaign_status;
    private CampaignVoucherMap<String, List<Voucher>> sent_vouchers;
    private List<User> vouched_users;
    private char strategy;
    private int voucherCounter; //Used in voucher generation for unique IDs
    private Strategy type_of_strategy;

    public Campaign(Integer campaign_ID, String campaign_name, String description, LocalDateTime starting_date, LocalDateTime ending_date, Integer number_of_vouchers,char strategy) {
        this.campaign_ID = campaign_ID;
        this.campaign_name = campaign_name;
        this.description = description;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
        this.number_of_vouchers = number_of_vouchers;
        this.available_vouchers = number_of_vouchers;
        this.campaign_status = CampaignStatusType.NEW;
        this.strategy=strategy;
        sent_vouchers = new CampaignVoucherMap<>();
        vouched_users = new ArrayList<>();
        voucherCounter=1;
    }

    public Integer getCampaign_ID() {
        return campaign_ID;
    }

    public void setCampaign_ID(Integer campaign_ID) {
        this.campaign_ID = campaign_ID;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(LocalDateTime starting_date) {
        this.starting_date = starting_date;
    }

    public LocalDateTime getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(LocalDateTime ending_date) {
        this.ending_date = ending_date;
    }

    public Integer getNumber_of_vouchers() {
        return number_of_vouchers;
    }

    public void setNumber_of_vouchers(Integer number_of_vouchers) {
        this.number_of_vouchers = number_of_vouchers;
    }

    public Integer getAvailable_vouchers() {
        return available_vouchers;
    }

    public void setAvailable_vouchers(Integer available_vouchers) {
        this.available_vouchers = available_vouchers;
    }

    public CampaignStatusType getCampaign_status() {
        return campaign_status;
    }

    public void setCampaign_status(CampaignStatusType campaign_status) {
        this.campaign_status = campaign_status;
    }

    public CampaignVoucherMap<String, List<Voucher>> getVouchers() {
        return sent_vouchers;
    }

    public void setSent_vouchers(CampaignVoucherMap<String, List<Voucher>> sent_vouchers) {
        this.sent_vouchers = sent_vouchers;
    }

    public List<User> getObservers() {
        return vouched_users;
    }

    public void setVouched_users(List<User> vouched_users) {
        this.vouched_users = vouched_users;
    }

    public char getStrategy() {
        return strategy;
    }

    public void setStrategy(char strategy) {
        this.strategy = strategy;
    }

    public Voucher getVoucher(int ID) {
        for (ArrayMap.Entry entry : sent_vouchers.getEntries()) {
            ArrayList<Voucher> voucherList = (ArrayList<Voucher>) entry.getValue();
            for (Voucher voucher : voucherList) {   //We search for the ID in every voucher
                if (voucher.getVoucher_id() == ID)
                    return voucher;
            }
        }
        return null;
    }

    public Voucher generateVoucher(String email, String voucherType, float value) {
        Voucher voucher;
        if(available_vouchers == 0) { //Checking if there are any vouchers left
            return null;
        }
        String code = "" + voucherCounter;
        if (voucherType.equals("GiftVoucher")) {    //Generating a GiftVoucher
            voucher = new GiftVoucher(voucherCounter, code, Voucher.VoucherStatusType.UNUSED, VMS_GUI.date, email, campaign_ID, value);
        } else {    //Generating a LoyaltyVoucher
            voucher = new LoyaltyVoucher(voucherCounter, code, Voucher.VoucherStatusType.UNUSED, VMS_GUI.date, email, campaign_ID, value);
        }
        voucherCounter++;
        voucher.setVoucher_date(null);
        sent_vouchers.addVoucher(voucher); //Adding the voucher to collection
        for (User user : vouched_users)
            if (user.getEmail().equals(email)) {
                user.getReceived_vouchers().addVoucher(voucher); //Distributing voucher to user
            }
            available_vouchers--;
            return voucher;
    }

    public void redeemVoucher(String code, LocalDateTime date) {
        for (ArrayMap.Entry entry : sent_vouchers.getEntries()) {
            ArrayList<Voucher> voucherList = (ArrayList<Voucher>) entry.getValue();
            for (Voucher voucher : voucherList) {
                if (voucher.getVoucher_code().equals(code)) {
                    if (date.isAfter(starting_date) && date.isBefore(ending_date)) { //If the voucher is valid
                        if (voucher.getVoucher_status().equals(Voucher.VoucherStatusType.USED)) {
                            System.out.println("VOUCHER ALREADY USED");
                        }
                        else {  //Redeem the voucher if it hasn't been used
                            voucher.setVoucher_status(Voucher.VoucherStatusType.USED);
                            voucher.setVoucher_date(date);
                            entry.setValue(voucherList);
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void addObserver(User user) {
        if(!vouched_users.contains(user))
            vouched_users.add(user);
    }

    @Override
    public void removeObserver(User user) {
        if(vouched_users.contains(user))
            vouched_users.remove(user);
    }

    @Override
    public void notifyAllObservers(Notification notification) {
        for(User user : vouched_users) {
            user.Update(notification); //Notifying every observer
        }
    }

    public Voucher executeStrategy() {
        if(strategy == 'A') {
            type_of_strategy =new A();
            return type_of_strategy.execute(this);
        }
        if(strategy == 'B') {
            type_of_strategy =new B();
           return type_of_strategy.execute(this);
        }
        else {
            type_of_strategy =new C();
            return type_of_strategy.execute(this);
        }
    }

    @Override
    public String toString() {
        return "Campaign ID:"+campaign_ID + "\n"+
                "Name:"+campaign_name + "\n"
                +"Description:" + description + "\n" +
                "Starting Date:"+starting_date +
                "\n"+"Ending Date" + ending_date + "\n"+
                "Status:" + campaign_status +"\n"+
                "Strategy:" + strategy+"\n"+"Vouchers:"+available_vouchers+"\n";
    }
}
