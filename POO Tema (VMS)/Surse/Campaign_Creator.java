import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Campaign_Creator {
    private JPanel campaignCreatorPanel;
    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField startDateField;
    private JTextField endingDateField;
    private JTextField vouchersField;
    private JTextField stratField;
    private JButton addCampaignButton;

    public Campaign_Creator() {
        addCampaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime date=VMS_GUI.date;
                Integer id=Integer.valueOf(idField.getText());
                String name=nameField.getText();
                String description=descriptionField.getText();
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime start_date=LocalDateTime.parse(startDateField.getText(),formatter);
                LocalDateTime end_date=LocalDateTime.parse(endingDateField.getText(),formatter);
                Integer number_of_vouchers=Integer.valueOf(vouchersField.getText());
                char strat=stratField.getText().charAt(0);
                Campaign new_campaign=new Campaign(id,name,description,start_date,end_date,number_of_vouchers,strat);
                if(date.isEqual(start_date) || date.isAfter(start_date))
                    new_campaign.setCampaign_status(Campaign.CampaignStatusType.STARTED);
                if(date.isEqual(end_date) || date.isAfter(end_date))
                    new_campaign.setCampaign_status(Campaign.CampaignStatusType.EXPIRED);
                try {
                    if (!VMS.getInstance().getCampaign(id).equals(null)) {
                        JOptionPane.showMessageDialog(null, "ERROR: CAMPAIGN EXISTS ALREADY");
                    } else {
                        VMS.getInstance().addCampaign(new_campaign);    //We never get here
                    }
                }
                catch(Exception exc) {
                    VMS.getInstance().addCampaign(new_campaign);
                }
            }
        });
    }

    public JPanel getCampaignCreatorPanel() {
        return campaignCreatorPanel;
    }
}
