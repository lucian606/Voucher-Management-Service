import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VoucherGenerator {
    private JPanel voucherGeneratorPanel;
    private JTextField emailField;
    private JTextField typeField;
    private JTextField valueField;
    private JButton addVoucherButton;

    public VoucherGenerator(Integer campaignId) {
        addVoucherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Campaign campaign=VMS.getInstance().getCampaign(campaignId);
                String email=emailField.getText();
                String type=typeField.getText();
                User user=VMS.getInstance().getUserbyEmail(email);
                float value=(float)Integer.valueOf(valueField.getText());
                if(campaign.getAvailable_vouchers() != 0) {
                    campaign.addObserver(user);
                    campaign.generateVoucher(email, type, value);
                }
                else {
                    JOptionPane.showMessageDialog(null, "No more vouchers left");
                }
            }
        });
    }

    public JPanel getVoucherGeneratorPanel() {
        return voucherGeneratorPanel;
    }
}
