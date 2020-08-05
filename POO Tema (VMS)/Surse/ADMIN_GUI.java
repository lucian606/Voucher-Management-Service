import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ADMIN_GUI {
    private JButton vouchersButton;
    private JPanel adminPanel;
    private JButton Campaigns_Button;
    private JTextField campaignIDTextField;
    private JLabel campaignLabel;
    private Integer campaign_Id;
    private Integer user_Id;

    public JPanel getAdminPanel() {
        return adminPanel;
    }

    public ADMIN_GUI(Integer id) {
        user_Id=id;
        Campaigns_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame camapaignsFrame=new JFrame("Campaigns");
                camapaignsFrame.setContentPane(new Campaigns_Management().getCampaignsPanel());
                camapaignsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                camapaignsFrame.pack();
                camapaignsFrame.setVisible(true);
            }
        });
        vouchersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campaign_Id=Integer.valueOf(campaignIDTextField.getText());
                JFrame vouchersFrame=new JFrame("Vouchers of campaign "+campaign_Id);
                vouchersFrame.setContentPane(new VOUCHERS_GUI(campaign_Id, user_Id).getVouchersPanel());
                vouchersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                vouchersFrame.pack();
                vouchersFrame.setVisible(true);
            }
        });
    }
}
