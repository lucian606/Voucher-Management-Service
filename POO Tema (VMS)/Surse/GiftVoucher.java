import java.time.LocalDateTime;

public class GiftVoucher extends Voucher {
    private float available_sum;

    public GiftVoucher(Integer voucher_id, String voucher_code, VoucherStatusType voucher_status, LocalDateTime voucher_date, String email, Integer campaign_ID, float available_sum) {
        super(voucher_id, voucher_code, voucher_status, voucher_date, email, campaign_ID);
        this.available_sum = available_sum;
    }

    public float getAvailable_sum() {
        return available_sum;
    }

    public void setAvailable_sum(float available_sum) {
        this.available_sum = available_sum;
    }

    @Override
    public String toString() {
        return "["+getVoucher_id()+";"+getVoucher_status()+";"+getEmail()+";"+available_sum+";"+getCampaign_ID()+";"+getVoucher_date()+"]";
    }
}
