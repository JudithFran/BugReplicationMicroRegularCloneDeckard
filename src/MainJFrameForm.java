import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainJFrameForm {
    private JComboBox comboBox1;
    private JButton showSystemInfoButton;
    private JButton percentageOfCloneFragmentsButton;
    private JButton extentOfBugReplicationButton;
    private JButton percentageOfReplicatedBugsButton;
    private JButton percentageOfSevereReplicatedButton;
    private JLabel selectSubjectSystemLabel;
    private JLabel showSystemInfoLabel;
    private JPanel panelMain;
    private JLabel title;

    public MainJFrameForm() {
        showSystemInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // TODO add your handling code here:
                String systemName = "";
                systemName = comboBox1.getSelectedItem().toString();
                //System.out.println("System selected: " + systemName);

                if(systemName == "Ctags")
                    showSystemInfoLabel.setText("<html>System's Name: Ctags <br> Programming Language: C <br> Last Revision Number: 774</html>");
                else if(systemName == "Brlcad")
                    showSystemInfoLabel.setText("<html>System's Name: Brlcad <br> Programming Language: C <br> Last Revision Number: 735</html>");
                else if(systemName == "Freecol")
                    showSystemInfoLabel.setText("<html>System's Name: Freecol <br> Programming Language: Java <br> Last Revision Number: 1950</html>");
                else if(systemName == "Carol")
                    showSystemInfoLabel.setText("<html>System's Name: Carol <br> Programming Language: Java <br> Last Revision Number: 1700</html>");
                else if(systemName == "Jabref")
                    showSystemInfoLabel.setText("<html>System's Name: Jabref <br> Programming Language: Java <br> Last Revision Number: 1545</html>");
                else
                    showSystemInfoLabel.setText("<html>Please select a valid subject system!</html>");
            }
        });
        percentageOfCloneFragmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // TODO add your handling code here:
                try {
                    String systemName = "";
                    systemName = comboBox1.getSelectedItem().toString();
                    //System.out.println("System selected in ComboBox: " + systemName);

                    InputParameters ip = new InputParameters();
                    ip.setParameters(systemName);

                    BugReplicationMicroRegularClones brm = new BugReplicationMicroRegularClones();

                    brm.bugReplication();

                    //System.out.println("---------------------------------------Regular Clone Analysis Starts Here--------------------------------------------\n");
                    //brm.bugReplicationR();

                    //System.out.println("-----------------------------------------Micro Clone Analysis Starts Here--------------------------------------------\n");
                    //brm.bugReplicationM();

                }catch(Exception e){
                    System.out.println("error in jButton2ActionPerformed(RQ1): " + e);
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainJFrameForm");
        frame.setContentPane(new MainJFrameForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}