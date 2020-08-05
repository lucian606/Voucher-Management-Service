import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CampaignDisplayer {

    private JPanel displayPanel;
    private JButton displayByDateButton;
    private JButton displayByNameButton;
    private JButton unsortedDisplayButton;
    private JCheckBox reverseNameSortCheckBox;
    private JCheckBox reverseDateSortCheckBox;

    public JPanel getDisplayPanel() {
        return displayPanel;
    }

    public CampaignDisplayer() {
        unsortedDisplayButton.addActionListener(new ActionListener() {
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
        displayByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame=new JFrame("Campaigns Table");
                int n=VMS.getInstance().getCampaigns().size();
                ArrayList<Campaign> campaigns=(ArrayList<Campaign>) VMS.getInstance().getCampaigns();
                ArrayList<Campaign> old_list=(ArrayList<Campaign>) campaigns.clone();
                Comparator<Campaign> compareByName=(Campaign c1, Campaign c2)->c1.getCampaign_name().compareTo(c2.getCampaign_name());
                if(reverseNameSortCheckBox.isSelected())
                    Collections.sort(campaigns, compareByName.reversed());
                else
                    Collections.sort(campaigns, compareByName);
                Object[][] rows=new Object[n][8];
                int counter=0;
                String[] columns={"ID","Name","Description","Starting Date","Ending date","Status","Strategy","Vouchers"};
                for(Campaign campaign : campaigns) {
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
                VMS.getInstance().setCampaigns(old_list);
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
        displayByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tableFrame=new JFrame("Campaigns Table");
                int n=VMS.getInstance().getCampaigns().size();
                ArrayList<Campaign> campaigns=(ArrayList<Campaign>) VMS.getInstance().getCampaigns();
                ArrayList<Campaign> old_list=(ArrayList<Campaign>) campaigns.clone();
                Comparator<Campaign> compareByDate=(Campaign c1, Campaign c2)->c1.getStarting_date().compareTo(c2.getStarting_date());
                if(reverseDateSortCheckBox.isSelected())
                    Collections.sort(campaigns, compareByDate.reversed());
                else
                    Collections.sort(campaigns, compareByDate);
                Object[][] rows=new Object[n][8];
                int counter=0;
                String[] columns={"ID","Name","Description","Starting Date","Ending date","Status","Strategy","Vouchers"};
                for(Campaign campaign : campaigns) {
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
                VMS.getInstance().setCampaigns(old_list);
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
