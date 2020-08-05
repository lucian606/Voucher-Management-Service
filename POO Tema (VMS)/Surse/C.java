import java.util.ArrayList;
import java.util.List;

public class C implements Strategy {
    public Voucher execute(Campaign c) {
        List<User> vouched_users=c.getObservers();
        Integer campaign_ID=c.getCampaign_ID();
        int index=0, contor=0;
        int min=vouched_users.get(0).getReceived_vouchers().get(campaign_ID).size();
        for(User user : vouched_users) { //Getting the user with the least vouchers amount
            ArrayList<Voucher> vouchers=user.getReceived_vouchers().get(campaign_ID);
            if(vouchers.size() < min) {
                min=vouchers.size();
                index=contor;
            }
            contor++;
        }
        return c.generateVoucher(vouched_users.get(index).getEmail(), "GiftVoucher", 100);
    }
}
