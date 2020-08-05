import java.util.List;

public class A implements Strategy {
    public Voucher execute(Campaign c) {
        List<User> vouched_users=c.getObservers();
        int random=(int)Math.floor(Math.random()*Math.floor(vouched_users.size())); //Random user
        Voucher voucher=c.generateVoucher(vouched_users.get(random).getEmail(), "GiftVoucher", 100);
        return voucher;
    }
}
