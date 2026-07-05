import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class ServerAdminApp extends JFrame {
    public ServerAdminApp() {
        setTitle("Bảng Điều Khiển Server - Phía Admin");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PHẦN HEADER ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(new JLabel("<html><h2>HỆ THỐNG QUẢN TRỊ SERVER</h2></html>"));
        add(headerPanel, BorderLayout.NORTH);

        // --- PHẦN TABS ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Quản lý Người dùng & Quyền", createUserManagementPanel());
        tabbedPane.addTab("Cấu hình Dung lượng", createSystemConfigPanel());
        add(tabbedPane, BorderLayout.CENTER);
    }

    // Tab 1: Quản lý User (Bảng trống để load danh sách user từ DB)
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Bảng danh sách user rỗng
        String[] columns = {"Username", "Quyền Upload", "Quyền Download", "Quyền Anonymous", "Dung lượng đã dùng"};
        Object[][] emptyData = {}; 
        
        DefaultTableModel model = new DefaultTableModel(emptyData, columns);
        JTable userTable = new JTable(model);
        userTable.setRowHeight(25);
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Các nút hành động quản lý Server
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        actionPanel.add(new JButton("Cho phép / Block Upload"));
        actionPanel.add(new JButton("Cho phép / Block Download"));
        actionPanel.add(new JButton("Cho phép / Block Anonymous"));
        actionPanel.add(new JButton("Gán quyền vào Thư mục"));
        
        panel.add(actionPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Tab 2: Cấu hình hệ thống (Các ô nhập trống chờ config)
    private JPanel createSystemConfigPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 50, 30, 50));

        panel.add(createConfigRow("Dung lượng lưu trữ tối đa mỗi User (MB):"));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createConfigRow("Kích thước File TỐI ĐA khi Upload (MB):"));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createConfigRow("Kích thước File TỐI ĐA khi Download (MB):"));
        
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JButton btnSave = new JButton("Lưu Cấu Hình Mới");
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setMaximumSize(new Dimension(200, 40));
        panel.add(btnSave);

        return panel;
    }

    // Hàm hỗ trợ tạo dòng nhập liệu cấu hình
    private JPanel createConfigRow(String labelText) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setMaximumSize(new Dimension(600, 40));
        
        JLabel lbl = new JLabel(labelText);
        lbl.setPreferredSize(new Dimension(300, 30)); // Cố định chiều rộng nhãn cho thẳng hàng
        row.add(lbl);
        
        JTextField txt = new JTextField("", 15); // Khởi tạo rỗng
        row.add(txt);
        
        return row;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServerAdminApp().setVisible(true));
    }
}