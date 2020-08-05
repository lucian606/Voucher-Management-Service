import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CampaignEditor {
    private JLabel NameLabel;
    private JTextField newNameText;
    private JLabel newDescriptionLabel;
    private JTextField newDescriptionText;
    private JLabel newStartDateLabel;
    private JTextField newStartingDateText;
    private JLabel newEndingDateLabel;
    private JTextField newEndingDateText;
    private JTextField newNumberOfVouchersText;
    private JLabel newNumberOfVouchersLabel;
    private JTextField campaignIdText;
    private JLabel campaignIdLabel;
    private JButton editCampaignButton;
    private JPanel editPanel;

    public JPanel getEditPanel() {
        return editPanel;
    }

    public CampaignEditor() {
        editCampaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String name=newNameText.getText();
                String description=newDescriptionText.getText();
                LocalDateTime start_date=LocalDateTime.parse(newStartingDateText.getText(),formatter);
                LocalDateTime end_date=LocalDateTime.parse(newEndingDateText.getText(),formatter);
                Integer number_of_vouchers=Integer.valueOf(newNumberOfVouchersText.getText());
                Campaign new_campaign=new Campaign(0,name,description,start_date,end_date,number_of_vouchers,'D');
                VMS.getInstance().updateCampaign(Integer.valueOf(campaignIdText.getText()), new_campaign);
            }
        });
    }
}
