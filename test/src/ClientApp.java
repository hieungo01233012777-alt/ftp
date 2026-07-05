import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ClientApp extends JFrame {
    public ClientApp() {
        setTitle("Ứng dụng Quản lý File - Phía Client");
        setSize(850, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PHẦN HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Tên user (Sau này bạn set text lại khi đăng nhập thành công)
        JLabel lblWelcome = new JLabel("<html><h2>Xin chào, [Tên_User]</h2></html>", JLabel.LEFT);
        headerPanel.add(lblWelcome, BorderLayout.WEST);
        
        // Nút thông báo
        JButton btnNotif = new JButton("Thông báo chia sẻ (0)"); 
        btnNotif.setBackground(Color.LIGHT_GRAY);
        headerPanel.add(btnNotif, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);

        // --- PHẦN DANH SÁCH FILE (TABS) ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Thư mục Cá nhân", createEmptyFileTable());
        tabbedPane.addTab("Được chia sẻ với tôi", createEmptyFileTable());
        tabbedPane.addTab("Thư mục Chung (Anonymous)", createEmptyFileTable());
        add(tabbedPane, BorderLayout.CENTER);

        // --- PHẦN CÁC NÚT CHỨC NĂNG ---
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(new EmptyBorder(30, 10, 10, 20));

        actionPanel.add(createActionButton("Tải lên (Upload)"));
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(createActionButton("Tải xuống (Download)"));
        actionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        actionPanel.add(createActionButton("Tạm dừng (Pause)"));
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(createActionButton("Tiếp tục (Resume)"));
        actionPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        
        actionPanel.add(createActionButton("Chia sẻ File/Thư mục"));

        add(actionPanel, BorderLayout.EAST);
    }

    // Tạo bảng trống để hứng dữ liệu thật
    private JPanel createEmptyFileTable() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"Tên File/Thư mục", "Kích thước", "Tiến trình", "Trạng thái", "Quyền"};
        
        // Dữ liệu rỗng
        Object[][] emptyData = {}; 
        
        DefaultTableModel model = new DefaultTableModel(emptyData, columnNames);
        JTable table = new JTable(model);
        table.setRowHeight(25); // Chỉnh độ cao hàng cho dễ nhìn
        
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 35));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientApp().setVisible(true));
    }
}