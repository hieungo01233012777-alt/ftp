import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Fri Jun 19 17:45:45 GMT+07:00 2026
 */



/**
 * @author hieun
 */
public class homepage1 extends JPanel {
    public homepage1() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        frame1 = new Frame();
        tabbedPane2 = new JTabbedPane();
        panel7 = new JPanel();
        panel8 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        button4 = new JButton();
        label2 = new JLabel();
        radioButton1 = new JRadioButton();
        button5 = new JButton();
        button6 = new JButton();
        scrollPane1 = new JScrollPane();
        tblProcess = new JTable();
        button7 = new JButton();
        button8 = new JButton();
        button9 = new JButton();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //======== frame1 ========
        {
            frame1.setLayout(new BorderLayout());
            frame1.pack();
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        add(frame1, "cell 0 0 39 1");

        //======== tabbedPane2 ========
        {

            //======== panel7 ========
            {
                panel7.setLayout(new BorderLayout());
            }
            tabbedPane2.addTab("Upload/Download", panel7);

            //======== panel8 ========
            {
                panel8.setLayout(new BorderLayout());
            }
            tabbedPane2.addTab("Chia s\u1ebb & Th\u00f4ng b\u00e1o", panel8);
        }
        add(tabbedPane2, "cell 0 1 39 1");

        //---- label1 ----
        label1.setText("th\u01b0 m\u1ee5c l\u00e0m vi\u1ec7c");
        label1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(label1, "cell 0 2 2 1");
        add(textField1, "cell 2 2 14 1");

        //---- button4 ----
        button4.setText("ch\u1ecdn th\u01b0 m\u1ee5c");
        add(button4, "cell 17 2");

        //---- label2 ----
        label2.setText("\u1ea8n danh");
        label2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        add(label2, "cell 21 2 9 1");

        //---- radioButton1 ----
        radioButton1.setText("b\u1eadt");
        radioButton1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(radioButton1, "cell 21 3 2 1");

        //---- button5 ----
        button5.setText("Upload");
        button5.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(button5, "cell 1 4 7 1");

        //---- button6 ----
        button6.setText("Download");
        button6.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        add(button6, "cell 12 4 6 1");

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(tblProcess);
        }
        add(scrollPane1, "cell 1 5 17 2");

        //---- button7 ----
        button7.setText("Pause");
        button7.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        add(button7, "cell 1 10");

        //---- button8 ----
        button8.setText("Resume");
        button8.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        add(button8, "cell 5 10");

        //---- button9 ----
        button9.setText("Global status");
        button9.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        add(button9, "cell 10 10");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private Frame frame1;
    private JTabbedPane tabbedPane2;
    private JPanel panel7;
    private JPanel panel8;
    private JLabel label1;
    private JTextField textField1;
    private JButton button4;
    private JLabel label2;
    private JRadioButton radioButton1;
    private JButton button5;
    private JButton button6;
    private JScrollPane scrollPane1;
    private JTable tblProcess;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
