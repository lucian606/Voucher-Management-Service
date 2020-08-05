import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUEST_GUI {
    private JButton viewCampaignsButton;
    private JButton viewVouchersButton;
    private JPanel guestPanel;
    private JButton viewNotificationsButton;

    public JPanel getGuestPanel() {
        return guestPanel;
    }

    public GUEST_GUI(Integer user_Id) {
        viewCampaignsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        viewVouchersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame voucherFrame=new JFrame("Vouchers");
                String[] columns={"ID","Code","Status","Email","Date","Voucher Type","Value","Campaign ID"};
                User user=VMS.getInstance().getUser(user_Id);
                int counter=0;
                ArrayList<Voucher> vouchers=new ArrayList<>();
                for(ArrayMap.Entry entry : user.getReceived_vouchers().getEntries()) {
                    for (Voucher voucher : (ArrayList<Voucher>)entry.getValue()) {
                        vouchers.add(voucher);
                    }
                }
                Object[][] rows=new Object[vouchers.size()][8];
                for(Voucher voucher : vouchers) {
                    rows[counter][0]=voucher.getVoucher_id();
                    rows[counter][1]=voucher.getVoucher_code();
                    rows[counter][2]=voucher.getVoucher_status();
                    rows[counter][3]=voucher.getEmail();
                    rows[counter][4]=voucher.getVoucher_date();
                    if(voucher instanceof GiftVoucher) {
                        rows[counter][5] = "GiftVoucher";
                        rows[counter][6] = ((GiftVoucher) voucher).getAvailable_sum();
                    }
                    else if(voucher instanceof LoyaltyVoucher) {
                        rows[counter][5] = "LoyaltyVoucher";
                        rows[counter][6] = ((LoyaltyVoucher) voucher).getDiscount();
                    }
                    rows[counter][7]=voucher.getCampaign_ID();
                    counter++;
                }
                TableModel model=new DefaultTableModel(rows, columns);
                JTable table=new JTable(model);
                JScrollPane jsp=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                voucherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jsp.setVisible(true);
                voucherFrame.add(jsp);
                voucherFrame.setVisible(true);
                voucherFrame.pack();
            }
        });
        viewCampaignsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame=new JFrame("Campaigns Table");
                int n=VMS.getInstance().getCampaigns().size();
                Object[][] rows=new Object[n][8];
                int counter=0;
                String[] columns={"ID","Name","Description","Starting Date","Ending date","Status","Strategy","Vouchers"};
                for(Campaign campaign : VMS.getInstance().getCampaigns()) {
                    rows[counter][0]=campaign.getCampaign_ID();
                    rows[counter][1]=campaign.getCampaign_name();
                    rows[counter][2]=campaign.getDescription();
                    rows[counter][3]=campaign.getStarting_date();
                    rows[counter][4]=campaign.getEnding_date();
                    rows[counter][5]=campaign.getCampaign_status();
                    rows[counter][6]=campaign.getStrategy();
                    rows[counter][7]=campaign.getAvailable_vouchers();
                    counter++;
                }
                TableModel model=new DefaultTableModel(rows, columns);
                JTable table=new JTable(model);
                JScrollPane jsp=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jsp.setVisible(true);
                tableFrame.add(jsp);
                tableFrame.setVisible(true);
                tableFrame.pack();
            }
        });
        viewNotificationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame=new JFrame("Notifications");
                User user=VMS.getInstance().getUser(user_Id);
                int n=user.getReceived_notifications().size();
                Object[][] rows=new Object[n][4];
                int counter=0;
                String[] columns={"Notification Type","Date","Campaign ID","Voucher IDs"};
                for(Notification notification : user.getReceived_notifications()) {
                    rows[counter][0]=notification.getNotification_type();
                    rows[counter][1]=notification.getNotification_date();
                    rows[counter][2]=notification.getCampaign_ID();
                    rows[counter][3]=notification.getCampaign_ID();
                    counter++;
                }
                TableModel model=new DefaultTableModel(rows, columns);
                JTable table=new JTable(model);
                JScrollPane jsp=new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jsp.setVisible(true);
                tableFrame.add(jsp);
                tableFrame.setVisible(true);
                tableFrame.pack();
            }
        });
    }
}
