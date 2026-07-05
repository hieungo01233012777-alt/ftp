import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import javax.swing.border.EmptyBorder;

public class AppLogin extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public AppLogin() {
        setTitle("Hệ thống Đăng nhập");
        setSize(400, 700); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Thêm các trang vào CardLayout (Đã xóa bỏ trang HOME thừa)
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createEmailPanel(), "EMAIL");
        mainPanel.add(createPhonePanel(), "PHONE");
        mainPanel.add(createRegisterPanel(), "REGISTER");
        mainPanel.add(createForgotPanel(), "FORGOT");

        add(mainPanel);
    }

    // 1. Trang Đăng nhập chính (ĐÃ THÊM LOGIC CHUYỂN TRANG USER/ADMIN)
    private JPanel createLoginPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("ĐĂNG NHẬP", JLabel.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField txtUser = createStyledTextField("Tên đăng nhập");
        JPasswordField txtPass = createStyledPasswordField("Mật khẩu");

        // Nút Đăng nhập giờ tự xử lý logic thay vì dùng createStyledButton chuyển tab
        JButton btnLogin = new JButton("Đăng nhập");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(300, 40));
        
        btnLogin.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String password = new String(txtPass.getPassword());

            // Kiểm tra rỗng
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // MÔ PHỎNG KIỂM TRA DATABASE
            if (username.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Đăng nhập quyền Admin thành công!");
                this.dispose(); // Đóng cửa sổ Đăng nhập
                new ServerAdminApp().setVisible(true); // Mở cửa sổ Admin
            } 
            else if (username.equals("user") && password.equals("user")) {
                JOptionPane.showMessageDialog(this, "Đăng nhập quyền User thành công!");
                this.dispose(); // Đóng cửa sổ Đăng nhập
                new ClientApp().setVisible(true); // Mở cửa sổ Client (User)
            } 
            else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!\n(Gợi ý: dùng admin/admin hoặc user/user)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnEmail = createStyledButton("Đăng nhập bằng Email", "EMAIL");
        JButton btnPhone = createStyledButton("Đăng nhập bằng SĐT", "PHONE");
        JButton btnRegister = createStyledButton("Chưa có tài khoản? Đăng ký", "REGISTER");
        JButton btnForgot = createStyledButton("Quên mật khẩu?", "FORGOT"); 

        p.add(title); p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(txtUser); p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(txtPass); p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(btnLogin); p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(btnEmail); p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(btnPhone); p.add(Box.createRigidArea(new Dimension(0, 20))); 
        p.add(btnRegister); p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(btnForgot);

        return p;
    }

    // 2. Trang Đăng nhập Email
    private JPanel createEmailPanel() {
        JPanel p = createBasePanel("ĐĂNG NHẬP BẰNG EMAIL");
        p.add(createStyledTextField("Nhập Email của bạn"));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Gửi mã OTP", null));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledTextField("Nhập mã OTP")); 
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton btnLoginMail = new JButton("Đăng nhập (Demo User)");
        btnLoginMail.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLoginMail.setMaximumSize(new Dimension(300, 40));
        btnLoginMail.addActionListener(e -> {
             this.dispose();
             new ClientApp().setVisible(true);
        });
        p.add(btnLoginMail);
        
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Quay lại", "LOGIN"));
        return p;
    }

    // 3. Trang Đăng nhập SĐT
    private JPanel createPhonePanel() {
        JPanel p = createBasePanel("ĐĂNG NHẬP BẰNG SĐT");
        p.add(createStyledTextField("Nhập số điện thoại"));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Gửi mã OTP", null));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledTextField("Nhập mã OTP")); 
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton btnLoginPhone = new JButton("Đăng nhập (Demo User)");
        btnLoginPhone.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLoginPhone.setMaximumSize(new Dimension(300, 40));
        btnLoginPhone.addActionListener(e -> {
             this.dispose();
             new ClientApp().setVisible(true);
        });
        p.add(btnLoginPhone);

        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Quay lại", "LOGIN"));
        return p;
    }

    // 4. Trang Quên mật khẩu
    private JPanel createForgotPanel() {
        JPanel p = createBasePanel("QUÊN MẬT KHẨU");
        p.add(createStyledTextField("Nhập Email hoặc SĐT"));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Gửi mã OTP", null));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledTextField("Nhập mã OTP")); 
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(createStyledButton("Xác nhận đổi MK", "LOGIN")); 
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Quay lại", "LOGIN"));
        return p;
    }

    // 5. Trang Đăng ký
    private JPanel createRegisterPanel() {
        JPanel p = createBasePanel("ĐĂNG KÝ TÀI KHOẢN");
        
        p.add(createStyledTextField("Họ và tên"));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createDOBPanel());
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createGenderPanel());
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledTextField("Email hoặc SĐT"));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Nhận mã xác nhận", null));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledTextField("Nhập mã OTP"));
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        p.add(createStyledButton("Hoàn tất Đăng ký", "LOGIN"));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(createStyledButton("Quay lại", "LOGIN"));
        return p;
    }

    // --- CÁC HÀM HỖ TRỢ ---

    private JPanel createBasePanel(String titleText) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        JLabel title = new JLabel(titleText, JLabel.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        p.add(title);
        p.add(Box.createRigidArea(new Dimension(0, 20)));
        return p;
    }

    private JTextField createStyledTextField(String title) {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(300, 40)); 
        tf.setBorder(BorderFactory.createTitledBorder(title));
        return tf;
    }

    private JPasswordField createStyledPasswordField(String title) {
        JPasswordField pf = new JPasswordField();
        pf.setMaximumSize(new Dimension(300, 40)); 
        pf.setBorder(BorderFactory.createTitledBorder(title));
        return pf;
    }

    private JPanel createDOBPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS)); 
        p.setMaximumSize(new Dimension(300, 50));
        p.setBorder(BorderFactory.createTitledBorder("Ngày sinh"));
        p.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) days[i - 1] = String.format("%02d", i);

        String[] months = new String[12];
        for (int i = 1; i <= 12; i++) months[i - 1] = String.format("%02d", i);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[100]; 
        for (int i = 0; i < 100; i++) years[i] = String.valueOf(currentYear - i);

        p.add(new JLabel(" Ngày: ")); p.add(new JComboBox<>(days));
        p.add(new JLabel(" Tháng: ")); p.add(new JComboBox<>(months));
        p.add(new JLabel(" Năm: ")); p.add(new JComboBox<>(years));

        return p;
    }

    private JPanel createGenderPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setMaximumSize(new Dimension(300, 50));
        p.setBorder(BorderFactory.createTitledBorder("Giới tính"));
        p.setAlignmentX(Component.CENTER_ALIGNMENT); 

        JRadioButton rbMale = new JRadioButton("Nam");
        JRadioButton rbFemale = new JRadioButton("Nữ");

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbMale);
        bg.add(rbFemale);
        rbMale.setSelected(true); 

        p.add(rbMale);
        p.add(rbFemale);

        return p;
    }

    private JButton createStyledButton(String text, String targetPage) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 40)); 
        if (targetPage != null) {
            btn.addActionListener(e -> cardLayout.show(mainPanel, targetPage));
        }
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AppLogin().setVisible(true));
    }
}
