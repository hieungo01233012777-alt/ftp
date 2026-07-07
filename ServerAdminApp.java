import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerAdminApp extends JFrame {

    // --- Các thành phần UI toàn cục ---
    private DefaultTableModel userTableModel;
    private JTable userTable;
    
    private JTextField txtMaxUpload;
    private JTextField txtMaxDownload;
    private JTextField txtDefaultQuota;
    private JTextArea serverLogArea;

    public ServerAdminApp() {
        setTitle("Bảng Điều Khiển Server - Phía Admin");
        setSize(1150, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- MENU BAR 
        JMenuBar menuBar = new JMenuBar();
        JMenu menuHeThong = new JMenu("Hệ thống");
        
        JMenuItem itemLogout = new JMenuItem("Đăng xuất");
        itemLogout.addActionListener(e -> handleLogout());
        
        JMenuItem itemExit = new JMenuItem("Đóng hệ thống");
        itemExit.addActionListener(e -> System.exit(0));
        
        menuHeThong.add(itemLogout);
        menuHeThong.addSeparator(); // Đường kẻ ngang phân cách
        menuHeThong.add(itemExit);
        menuBar.add(menuHeThong);
        setJMenuBar(menuBar);

        // --- HEADER ---
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        headerPanel.add(new JLabel("<html><h2 style='color:#2c3e50;'>HỆ THỐNG QUẢN TRỊ FILE SERVER</h2></html>"));
        add(headerPanel, BorderLayout.NORTH);

        // --- TABS (MAIN CONTENT) ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Giám sát & Phân quyền User", createUserManagementPanel());
        tabbedPane.addTab("Cấu hình Chung", createSystemConfigPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // --- SERVER LOG (FOOTER) ---
        add(createServerLogPanel(), BorderLayout.SOUTH);
        
        logSystem("Server Admin Dashboard đã khởi động. Sẵn sàng lắng nghe kết nối.");
    }

    // ==========================================
    // XỬ LÝ ĐĂNG XUẤT
    // ==========================================
    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn đăng xuất?", 
                "Xác nhận đăng xuất", 
                JOptionPane.YES_NO_OPTION);
                
        if (confirm == JOptionPane.YES_OPTION) {
            logSystem("Admin đang đăng xuất khỏi hệ thống...");
            this.dispose(); // Đóng cửa sổ Server hiện tại
            
            // Gọi lại trang đăng nhập 
            SwingUtilities.invokeLater(() -> {
                new AppLogin().setVisible(true);
            });
        }
    }

    // ==========================================
    // TAB 1: QUẢN LÝ USER VÀ QUYỀN HẠN
    // ==========================================
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Khởi tạo bảng danh sách user
        String[] columns = {
            "Username", "Mạng", "Trạng thái TK", "Dung lượng (Dùng/Tối đa)", 
            "Upload", "Download", "Chia sẻ", "Anonymous", "Thư mục truy cập"
        };
        
        // Dữ liệu mẫu ban đầu
        Object[][] mockData = {
            {"user_01", "Online", "Bình thường", "450 / 500 MB", true, true, true, false, "/public, /users/user_01"},
            {"user_02", "Offline", "Bị chặn", "100 / 1024 MB", false, false, false, false, "/public"},
            {"guest_99", "Online", "Bình thường", "10 / 50 MB", false, false, false, true, "/public/temp"}
        }; 

        userTableModel = new DefaultTableModel(mockData, columns) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Các cột quyền (Upload, Download, Chia sẻ, Anon) hiển thị dạng Checkbox
                if (columnIndex >= 4 && columnIndex <= 7) {
                    return Boolean.class; 
                }
                return String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Chỉ xem, thao tác qua nút bấm
            }
        };

        userTable = new JTable(userTableModel);
        userTable.setRowHeight(28);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Đổi màu chữ cho dễ nhìn trạng thái
        DefaultTableCellRenderer statusRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String str = value.toString();
                    if (str.equals("Online")) c.setForeground(new Color(0, 153, 51)); // Xanh lá
                    else if (str.equals("Offline")) c.setForeground(Color.GRAY);
                    else if (str.equals("Bị chặn")) c.setForeground(Color.RED);
                    else if (str.equals("Bình thường")) c.setForeground(Color.BLACK);
                    else c.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                }
                return c;
            }
        };
        userTable.getColumnModel().getColumn(1).setCellRenderer(statusRenderer); 
        userTable.getColumnModel().getColumn(2).setCellRenderer(statusRenderer); 
        
        // Căn chỉnh độ rộng các cột chứa văn bản dài
        userTable.getColumnModel().getColumn(3).setPreferredWidth(160); 
        userTable.getColumnModel().getColumn(8).setPreferredWidth(180); 

        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // 2. Các nút Action (Xếp 2 dòng)
        JPanel actionPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));
        
        JButton btnToggleBlock = new JButton("Chặn/Mở khóa TK");
        JButton btnSetQuota = new JButton("Sửa Dung Lượng Tối Đa");
        JButton btnAssignDir = new JButton("Gán Thư Mục");
        JButton btnToggleShare = new JButton("Bật/Tắt Chia sẻ");

        JButton btnToggleUpload = new JButton("Bật/Tắt Upload");
        JButton btnToggleDownload = new JButton("Bật/Tắt Download");
        JButton btnToggleAnon = new JButton("Bật/Tắt Anonymous");
        
        // Gắn sự kiện (Listeners)
        btnToggleBlock.addActionListener(e -> toggleUserStatus());
        btnSetQuota.addActionListener(e -> handleSetUserSpecificQuota());
        btnAssignDir.addActionListener(e -> handleAssignDirectory());
        btnToggleShare.addActionListener(e -> togglePermission(6, "Chia sẻ"));
        
        btnToggleUpload.addActionListener(e -> togglePermission(4, "Upload"));
        btnToggleDownload.addActionListener(e -> togglePermission(5, "Download"));
        btnToggleAnon.addActionListener(e -> togglePermission(7, "Anonymous"));

        // Dòng 1
        actionPanel.add(btnToggleBlock);
        actionPanel.add(btnToggleUpload);
        actionPanel.add(btnToggleDownload);
        actionPanel.add(btnToggleShare);
        
        // Dòng 2
        actionPanel.add(btnSetQuota);
        actionPanel.add(btnAssignDir);
        actionPanel.add(btnToggleAnon);
        actionPanel.add(new JLabel("")); // Căn chỗ trống

        panel.add(actionPanel, BorderLayout.SOUTH);
        return panel;
    }

    // ==========================================
    // CÁC HÀM XỬ LÝ LOGIC TAB 1
    // ==========================================
    private void toggleUserStatus() {
        int row = userTable.getSelectedRow();
        if (row != -1) {
            String currentStatus = (String) userTableModel.getValueAt(row, 2);
            String newStatus = currentStatus.equals("Bình thường") ? "Bị chặn" : "Bình thường";
            String username = (String) userTableModel.getValueAt(row, 0);
            
            userTableModel.setValueAt(newStatus, row, 2);
            logSystem("Tài khoản [" + username + "] đã chuyển sang trạng thái: " + newStatus);
            
            if (newStatus.equals("Bị chặn")) {
                // Thu hồi hết quyền khi bị chặn
                userTableModel.setValueAt(false, row, 4); 
                userTableModel.setValueAt(false, row, 5); 
                userTableModel.setValueAt(false, row, 6); 
                userTableModel.setValueAt(false, row, 7); 
                logSystem("Hệ thống tự động vô hiệu hóa toàn bộ quyền của [" + username + "] do bị chặn.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 User trên bảng để thao tác!");
        }
    }

    private void togglePermission(int columnIndex, String actionName) {
        int row = userTable.getSelectedRow();
        if (row != -1) {
            String accStatus = (String) userTableModel.getValueAt(row, 2);
            if (accStatus.equals("Bị chặn")) {
                JOptionPane.showMessageDialog(this, "Không thể cấp quyền! Tài khoản này đang Bị chặn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean current = (boolean) userTableModel.getValueAt(row, columnIndex);
            userTableModel.setValueAt(!current, row, columnIndex);
            String username = (String) userTableModel.getValueAt(row, 0);
            
            String status = !current ? "CẤP" : "THU HỒI";
            logSystem("Đã " + status + " quyền [" + actionName + "] cho tài khoản [" + username + "]");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 User trên bảng để thao tác!");
        }
    }

    private void handleSetUserSpecificQuota() {
        int row = userTable.getSelectedRow();
        if (row != -1) {
            String username = (String) userTableModel.getValueAt(row, 0);
            String currentQuotaStr = (String) userTableModel.getValueAt(row, 3); 
            
            // Tách chuỗi lấy dung lượng tối đa hiện tại
            String[] parts = currentQuotaStr.split(" / ");
            String usedSpace = parts[0]; 
            String maxSpace = parts.length > 1 ? parts[1].replace(" MB", "").trim() : "";
            
            String newMax = JOptionPane.showInputDialog(this, 
                    "Nhập dung lượng lưu trữ TỐI ĐA mới (MB) cho user [" + username + "]:", maxSpace);
            
            if (newMax != null && !newMax.trim().isEmpty()) {
                try {
                    int quota = Integer.parseInt(newMax.trim());
                    String newQuotaStr = usedSpace + " / " + quota + " MB";
                    userTableModel.setValueAt(newQuotaStr, row, 3);
                    logSystem("Cập nhật Quota riêng cho [" + username + "] thành " + quota + " MB.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 User để sửa dung lượng lưu trữ!");
        }
    }

    private void handleAssignDirectory() {
        int row = userTable.getSelectedRow();
        if (row != -1) {
            String username = (String) userTableModel.getValueAt(row, 0);
            String currentDirs = (String) userTableModel.getValueAt(row, 8); 
            
            String newDirs = JOptionPane.showInputDialog(this, 
                    "Nhập các thư mục được phép truy cập (cách nhau bằng dấu phẩy) cho [" + username + "]:", currentDirs);
            
            if (newDirs != null && !newDirs.trim().isEmpty()) {
                userTableModel.setValueAt(newDirs, row, 8);
                logSystem("Đã đổi quyền thư mục của [" + username + "] thành: " + newDirs);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 User để phân quyền thư mục!");
        }
    }

    // ==========================================
    // TAB 2: CẤU HÌNH HỆ THỐNG CHUNG
    // ==========================================
    private JPanel createSystemConfigPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        txtMaxUpload = new JTextField("50", 15);
        txtMaxDownload = new JTextField("500", 15);
        txtDefaultQuota = new JTextField("1024", 15); 

        panel.add(createConfigRow("Kích thước File TỐI ĐA 1 lần Upload (MB):", txtMaxUpload));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(createConfigRow("Kích thước File TỐI ĐA 1 lần Download (MB):", txtMaxDownload));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(createConfigRow("Dung lượng lưu trữ MẶC ĐỊNH cho user mới (MB):", txtDefaultQuota));
        
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        
        JButton btnSave = new JButton("Lưu Cấu Hình Hệ Thống");
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.addActionListener(e -> {
            try {
                int upload = Integer.parseInt(txtMaxUpload.getText());
                int download = Integer.parseInt(txtMaxDownload.getText());
                int defaultQuota = Integer.parseInt(txtDefaultQuota.getText());
                
                logSystem("Đã lưu cấu hình chung: Max Upload = " + upload + "MB, Max Download = " + download + "MB, Quota Mặc định = " + defaultQuota + "MB.");
                JOptionPane.showMessageDialog(this, "Lưu cấu hình hệ thống thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi! Vui lòng chỉ nhập số nguyên cho các ô cấu hình.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnSave);

        return panel;
    }

    private JPanel createConfigRow(String labelText, JTextField txtField) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row.setMaximumSize(new Dimension(700, 40));
        JLabel lbl = new JLabel(labelText);
        lbl.setPreferredSize(new Dimension(350, 30)); 
        row.add(lbl);
        row.add(txtField);
        return row;
    }

    // ==========================================
    // PANEL: SERVER LOG TẠI FOOTER
    // ==========================================
    private JPanel createServerLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Nhật ký hệ thống (Server Logs)"));
        panel.setPreferredSize(new Dimension(1100, 180));

        serverLogArea = new JTextArea();
        serverLogArea.setEditable(false);
        serverLogArea.setBackground(new Color(40, 44, 52)); // Màu nền tối
        serverLogArea.setForeground(new Color(152, 195, 121)); // Màu chữ xanh lá
        serverLogArea.setFont(new Font("Consolas", Font.PLAIN, 13));

        panel.add(new JScrollPane(serverLogArea), BorderLayout.CENTER);
        return panel;
    }

    private void logSystem(String message) {
        String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        serverLogArea.append("[" + timestamp + "] " + message + "\n");
        serverLogArea.setCaretPosition(serverLogArea.getDocument().getLength());
    }

    // ==========================================
    // MAIN METHOD
    // ==========================================
    public static void main(String[] args) {
        try {
            // Áp dụng LookAndFeel hệ điều hành cho đẹp
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new ServerAdminApp().setVisible(true));
    }
}