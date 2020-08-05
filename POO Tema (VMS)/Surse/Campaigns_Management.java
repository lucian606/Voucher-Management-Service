import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Campaigns_Management {
    private JButton displayCampaignsButton;
    private JButton addCampaignButton;
    private JButton editCampaignButton;
    private JButton closeCampaignButton;
    private JPanel CampaignsPanel;
    private JButton viewCampaignButton;
    private JTextField campaignIdField;

    public Campaigns_Management() {
        displayCampaignsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CampaignDisplayer displayer=new CampaignDisplayer();
                JFrame displayFrame=new JFrame("Campaigns Display");
                displayFrame.setContentPane(displayer.getDisplayPanel());
                displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                displayFrame.pack();
                displayFrame.setVisible(true);
            }
        });
        editCampaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CampaignEditor editor=new CampaignEditor();
                JFrame editFrame=new JFrame("Campaign Editor");
                editFrame.setContentPane(editor.getEditPanel());
                editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editFrame.setVisible(true);
                editFrame.pack();
            }
        });
        addCampaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Campaign_Creator creator=new Campaign_Creator();
                JFrame creatorFrame=new JFrame("Campaign Creator");
                creatorFrame.setContentPane(creator.getCampaignCreatorPanel());
                creatorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                creatorFrame.setVisible(true);
                creatorFrame.pack();
            }
        });
        viewCampaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id=Integer.valueOf(campaignIdField.getText());
                String info=VMS.getInstance().getCampaign(id).toString();
                JOptionPane.showMessageDialog(null, info);
            }
        });
        closeCampaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer id=Integer.valueOf(campaignIdField.getText());
                VMS.getInstance().cancelCampaign(id);
            }
        });
    }

    public JPanel getCampaignsPanel() {
        return CampaignsPanel;
    }
}
