import java.util.ArrayList;
import java.util.List;

public class B implements Strategy {
    public Voucher execute(Campaign c) {
        List<User> vouched_users=c.getObservers();
        int index=0, max=0, contor=0;
        Integer campaign_ID=c.getCampaign_ID();
        for(User user : vouched_users) { //Getting the user with most vouchers amount
            ArrayList<Voucher> vouchers=user.getReceived_vouchers().get(campaign_ID);
            if(vouchers.size() > max) {
                max=vouchers.size();
                index=contor;
            }
            contor++;
        }
        return c.generateVoucher(vouched_users.get(index).getEmail(), "LoyaltyVoucher", 50);
    }
}
