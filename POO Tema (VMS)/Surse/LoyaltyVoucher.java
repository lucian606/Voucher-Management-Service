import java.time.LocalDateTime;

public class LoyaltyVoucher extends Voucher {
    private float discount;

    public LoyaltyVoucher(Integer voucher_id, String voucher_code, VoucherStatusType voucher_status, LocalDateTime voucher_date, String email, Integer campaign_ID, float discount) {
        super(voucher_id, voucher_code, voucher_status, voucher_date, email, campaign_ID);
        this.discount = discount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "["+getVoucher_id()+";"+getVoucher_status()+";"+getEmail()+";"+discount+";"+getCampaign_ID()+";"+getVoucher_date()+"]";
    }
}
