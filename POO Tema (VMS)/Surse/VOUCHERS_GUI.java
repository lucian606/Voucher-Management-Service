import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class VOUCHERS_GUI {
    private JPanel vouchersPanel;
    private JButton displayVouchersButton;
    private JButton generateVoucherButton;
    private JButton redeemVoucherButton;
    private JTextField voucherIdField;

    public VOUCHERS_GUI(Integer campaignId, Integer userId) {
        displayVouchersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame=new JFrame("Vouchers Table");
                String[] columns={"ID","Code","Type","Status","Email","Date","Value"};
                Campaign campaign=VMS.getInstance().getCampaign(campaignId);
                int counter=0;
                ArrayList<Voucher> vouchers=new ArrayList<>();
                for(ArrayMap.Entry entry : campaign.getVouchers().getEntries()) {
                    for(Voucher voucher : (ArrayList<Voucher>)entry.getValue()) {
                        vouchers.add(voucher);
                    }
                }
                Object[][] rows=new Object[vouchers.size()][7];
                for(Voucher voucher : vouchers) {
                    rows[counter][0]=voucher.getVoucher_id();
                    rows[counter][1]=voucher.getVoucher_code();
                    if(voucher instanceof GiftVoucher)
                        rows[counter][2]="GiftVoucher";
                    else
                        rows[counter][2]="LoyaltyVoucher";
                    rows[counter][3]=voucher.getVoucher_status();
                    rows[counter][4]=voucher.getEmail();
                    rows[counter][5]=voucher.getVoucher_date();
                    if(voucher instanceof GiftVoucher)
                        rows[counter][6]=((GiftVoucher) voucher).getAvailable_sum();
                    else if(voucher instanceof LoyaltyVoucher)
                        rows[counter][6]=((LoyaltyVoucher) voucher).getDiscount();
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
        generateVoucherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoucherGenerator generator=new VoucherGenerator(campaignId);
                JFrame generatorFrame=new JFrame("Voucher Generator");
                generatorFrame.setContentPane(generator.getVoucherGeneratorPanel());
                generatorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                generatorFrame.setVisible(true);
                generatorFrame.pack();
            }
        });
        redeemVoucherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code=voucherIdField.getText();
                Integer id=Integer.valueOf(code);
                Campaign campaign=VMS.getInstance().getCampaign(campaignId);
                Voucher voucher=campaign.getVoucher(id);
                if(voucher.getVoucher_status().equals(Voucher.VoucherStatusType.USED))
                    JOptionPane.showMessageDialog(null, "Voucher already used");
                campaign.redeemVoucher(voucher.getVoucher_code(), VMS_GUI.date);
                JOptionPane.showMessageDialog(null,voucher);
            }
        });
    }

    public JPanel getVouchersPanel() {
        return vouchersPanel;
    }
}
