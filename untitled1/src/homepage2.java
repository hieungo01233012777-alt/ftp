import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Fri Jun 19 19:51:18 GMT+07:00 2026
 */



/**
 * @author hieun
 */
public class homepage2 extends JPanel {
    public homepage2() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        this2 = new JPanel();
        frame1 = new Frame();
        tabbedPane2 = new JTabbedPane();
        panel7 = new JPanel();
        panel8 = new JPanel();
        label1 = new JLabel();
        label4 = new JLabel();
        scrollPane3 = new JScrollPane();
        textPane1 = new JTextPane();
        textField1 = new JTextField();
        button1 = new JButton();
        label2 = new JLabel();
        comboBox1 = new JComboBox();
        label3 = new JLabel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        button2 = new JButton();
        label5 = new JLabel();
        radioButton3 = new JRadioButton();

        //======== this ========
        setLayout(new BorderLayout());

        //======== this2 ========
        {
            this2.setLayout(new MigLayout(
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
            this2.add(frame1, "cell 0 0 38 1");

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
            this2.add(tabbedPane2, "cell 0 1 38 1");

            //---- label1 ----
            label1.setText("ch\u1ecdn file th\u01b0 m\u1ee5c c\u1ea7n share");
            label1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            this2.add(label1, "cell 1 2 3 3");

            //---- label4 ----
            label4.setText("Th\u00f4ng b\u00e1o m\u1edbi nh\u1eadn");
            label4.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            this2.add(label4, "cell 11 3");

            //======== scrollPane3 ========
            {
                scrollPane3.setViewportView(textPane1);
            }
            this2.add(scrollPane3, "cell 11 4 6 2");
            this2.add(textField1, "cell 1 4 5 2");

            //---- button1 ----
            button1.setText("...");
            this2.add(button1, "cell 6 4 1 2");

            //---- label2 ----
            label2.setText("chia s\u1ebb cho ng\u01b0\u1eddi d\u00f9ng");
            label2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            this2.add(label2, "cell 1 7 3 1");
            this2.add(comboBox1, "cell 1 8 5 1");

            //---- label3 ----
            label3.setText("Quy\u1ec1n truy c\u1eadp");
            label3.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            this2.add(label3, "cell 1 10");

            //---- radioButton1 ----
            radioButton1.setText("ch\u1ec9 xem");
            this2.add(radioButton1, "cell 1 11");

            //---- radioButton2 ----
            radioButton2.setText("To\u00e0n quy\u1ec1n");
            this2.add(radioButton2, "cell 1 12");

            //---- button2 ----
            button2.setText("x\u00e1c nh\u00e2n chia s\u1ebb");
            button2.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            this2.add(button2, "cell 1 13");

            //---- label5 ----
            label5.setText("\u1ea8n danh");
            label5.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            this2.add(label5, "cell 1 14");

            //---- radioButton3 ----
            radioButton3.setText("b\u1eadt");
            radioButton3.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            this2.add(radioButton3, "cell 1 15");
        }
        add(this2, BorderLayout.WEST);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel this2;
    private Frame frame1;
    private JTabbedPane tabbedPane2;
    private JPanel panel7;
    private JPanel panel8;
    private JLabel label1;
    private JLabel label4;
    private JScrollPane scrollPane3;
    private JTextPane textPane1;
    private JTextField textField1;
    private JButton button1;
    private JLabel label2;
    private JComboBox comboBox1;
    private JLabel label3;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JButton button2;
    private JLabel label5;
    private JRadioButton radioButton3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
