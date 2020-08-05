import java.time.LocalDateTime;

public abstract class Voucher {
    private int voucher_id;
    private String voucher_code;
    enum VoucherStatusType {
        USED,
        UNUSED
    }
    private VoucherStatusType voucher_status;
    private LocalDateTime voucher_date;
    private String email;
    private Integer campaign_ID;

    public Voucher(Integer voucher_id, String voucher_code, VoucherStatusType voucher_status, LocalDateTime voucher_date, String email, Integer campaign_ID) {
        this.voucher_id = voucher_id;
        this.voucher_code = voucher_code;
        this.voucher_status = voucher_status;
        this.voucher_date = voucher_date;
        this.email = email;
        this.campaign_ID = campaign_ID;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public VoucherStatusType getVoucher_status() {
        return voucher_status;
    }

    public void setVoucher_status(VoucherStatusType voucher_status) {
        this.voucher_status = voucher_status;
    }

    public LocalDateTime getVoucher_date() {
        return voucher_date;
    }

    public void setVoucher_date(LocalDateTime voucher_date) {
        this.voucher_date = voucher_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCampaign_ID() {
        return campaign_ID;
    }

    public void setCampaign_ID(Integer campaign_ID) {
        this.campaign_ID = campaign_ID;
    }

}
