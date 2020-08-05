import java.util.ArrayList;
import java.util.List;

public class VMS {
    private List<Campaign> campaigns;
    private List<User> users;
    private static VMS single_instance=null; //Used for singleton pattern

    private VMS() {
        campaigns=new ArrayList<>();
        users=new ArrayList<>();
    }

    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public List<User> getUsers() {
        return users;
    }

    public Campaign getCampaign(Integer id) {
        for (Campaign campaign : campaigns) {
            if (campaign.getCampaign_ID() == id)
                return campaign;
        }
        return null;
    }

    public void addCampaign(Campaign campaign) {
        if(!campaigns.contains(campaign))
            campaigns.add(campaign);
    }

    public void cancelCampaign(Integer id) {
        Campaign campaign=getCampaign(id);
        campaigns.remove(campaign);
        if(campaign.getCampaign_status().equals(Campaign.CampaignStatusType.NEW) || campaign.getCampaign_status().equals(Campaign.CampaignStatusType.STARTED))
            campaign.setCampaign_status(Campaign.CampaignStatusType.CANCELLED);
        addCampaign(campaign);
        Notification notification=new Notification(Notification.NotificationType.CANCEL, VMS_GUI.date, campaign.getCampaign_ID());
        campaign.notifyAllObservers(notification); //We notify every user of the campaign
    }

    public void addUser(User user) {
        if(!users.contains(user))
            users.add(user);
    }

    public User getUser(Integer id) {
        for(User user : users)
            if(user.getUser_ID() == id) {
                return user;
            }
        return null;
    }

    public User getUserbyEmail(String email) {
        for(User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public void updateCampaign(Integer id, Campaign campaign) {
        Campaign old_campaign=getCampaign(id);
        if(old_campaign.getCampaign_status().equals(Campaign.CampaignStatusType.NEW)) {
            campaigns.remove(old_campaign);
            old_campaign.setCampaign_name(campaign.getCampaign_name());
            old_campaign.setDescription(campaign.getDescription());
            old_campaign.setStarting_date(campaign.getStarting_date());
            old_campaign.setEnding_date(campaign.getEnding_date());
            if(campaign.getNumber_of_vouchers() >= old_campaign.getNumber_of_vouchers()-old_campaign.getAvailable_vouchers())
                old_campaign.setNumber_of_vouchers(campaign.getNumber_of_vouchers());
            Notification notification=new Notification(Notification.NotificationType.EDIT, VMS_GUI.date, old_campaign.getCampaign_ID());
            campaigns.add(old_campaign);
            old_campaign.notifyAllObservers(notification); //Notifying the users
        }
        else if(old_campaign.getCampaign_status().equals(Campaign.CampaignStatusType.STARTED)) {
            campaigns.remove(old_campaign);
            old_campaign.setEnding_date(campaign.getEnding_date());
            if(campaign.getNumber_of_vouchers() >= old_campaign.getNumber_of_vouchers()-old_campaign.getAvailable_vouchers())
                old_campaign.setNumber_of_vouchers(campaign.getNumber_of_vouchers());
            old_campaign.setAvailable_vouchers(old_campaign.getNumber_of_vouchers());
            Notification notification=new Notification(Notification.NotificationType.EDIT, VMS_GUI.date, old_campaign.getCampaign_ID());
            campaigns.add(old_campaign);
            old_campaign.notifyAllObservers(notification); //Notifying the users
        }
    }

    public Voucher execute(Campaign campaign) {
        return campaign.executeStrategy();
    }

    public static VMS getInstance() {
        if(single_instance == null)
            single_instance=new VMS();
        return single_instance;
    }
}
