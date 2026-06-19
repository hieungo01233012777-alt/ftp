import java.awt.*;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Fri Jun 19 17:43:55 GMT+07:00 2026
 */



/**
 * @author hieun
 */
public class login extends JPanel {
    public login() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        this2 = new JPanel();
        label1 = new JLabel();
        label4 = new JLabel();
        scrollPane2 = new JScrollPane();
        textArea2 = new JTextArea();
        label3 = new JLabel();
        passwordField1 = new JPasswordField();
        label2 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        button2 = new JButton();
        button1 = new JButton();

        //======== this ========
        setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]"));

        //======== this2 ========
        {
            this2.setLayout(new MigLayout(
                "hidemode 3",
                // columns
                "[34,fill]" +
                "[60,fill]" +
                "[fill]" +
                "[fill]" +
                "[86,fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[fill]" +
                "[95,fill]" +
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
                "[]"));

            //---- label1 ----
            label1.setText("\u0110\u0103ng Nh\u1eadp");
            label1.setFont(new Font("Inter", Font.PLAIN, 22));
            this2.add(label1, "cell 5 1");

            //---- label4 ----
            label4.setText("EIMAIL");
            label4.setFont(new Font("Inter", Font.PLAIN, 16));
            this2.add(label4, "cell 2 4");

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(textArea2);
            }
            this2.add(scrollPane2, "cell 4 4 6 1");

            //---- label3 ----
            label3.setText("M\u1eadt kh\u1ea9u");
            label3.setFont(new Font("Inter", Font.PLAIN, 16));
            this2.add(label3, "cell 2 5");
            this2.add(passwordField1, "cell 4 5 7 1");

            //---- label2 ----
            label2.setText("Nh\u1eadp OTP");
            label2.setFont(new Font("Inter", Font.PLAIN, 16));
            this2.add(label2, "cell 2 6");

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(textArea1);
            }
            this2.add(scrollPane1, "cell 4 6 6 1");

            //---- button2 ----
            button2.setText("\u0110\u0103ng nh\u1eadp");
            this2.add(button2, "cell 4 8");

            //---- button1 ----
            button1.setText("\u0110\u0103ng k\u00fd");
            this2.add(button1, "cell 6 8");
        }
        add(this2, "cell 0 0");
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel this2;
    private JLabel label1;
    private JLabel label4;
    private JScrollPane scrollPane2;
    private JTextArea textArea2;
    private JLabel label3;
    private JPasswordField passwordField1;
    private JLabel label2;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JButton button2;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
