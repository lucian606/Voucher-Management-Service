import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Notification {
    public enum NotificationType {
        EDIT,
        CANCEL
    }
    private NotificationType notification_type;
    private LocalDateTime notification_date;
    private Integer campaign_ID;
    private List<Integer> voucher_ids;

    public Notification(NotificationType notification_type, LocalDateTime notification_date, Integer campaign_ID) {
        this.notification_type = notification_type;
        this.notification_date = notification_date;
        this.campaign_ID = campaign_ID;
    }

    public NotificationType getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(NotificationType notification_type) {
        this.notification_type = notification_type;
    }

    public LocalDateTime getNotification_date() {
        return notification_date;
    }

    public void setNotification_date(LocalDateTime notification_date) {
        this.notification_date = notification_date;
    }

    public Integer getCampaign_ID() {
        return campaign_ID;
    }

    public void setCampaign_ID(Integer campaign_ID) {
        this.campaign_ID = campaign_ID;
    }

    public List<Integer> getVoucher_ids() {
        return voucher_ids;
    }

    public void setVoucher_ids(List<Integer> voucher_ids) {
        this.voucher_ids = voucher_ids;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return  campaign_ID+";"+voucher_ids+";"+notification_date.format(formatter)+";"+notification_type;
    }
}
