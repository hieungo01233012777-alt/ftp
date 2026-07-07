import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.io.File;

public class ClientApp extends JFrame {

    // ==========================================
    // 1. KHAI BÁO CÁC BIẾN TOÀN CỤC (FIELDS)
    // ==========================================
    private JTabbedPane tabbedPane;
    private JLabel lblWelcome;
    private JButton btnNotif; 
    private JButton btnAnonymous; 
    private JProgressBar storageBar; 
    private JLabel lblStorage; 
    
    private int notifCount = 0; 
    private boolean isAnonymousMode = false; 

    // Các biến lưu trữ thông tin file vừa được chia sẻ để demo real-time
    private String lastSharedFileName = "";
    private String lastSharedSize = "0.0 MB";
    private String lastSharedPerm = "Chỉ đọc";

    // Danh sách tài khoản mẫu tồn tại trên hệ thống (Mock Database)
    private final String[] SYSTEM_USERS = {"admin", "user1", "user2", "giangvien", "sinhvien", "tuan", "lan"};

    // ==========================================
    // 2. HÀM KHỞI TẠO (CONSTRUCTORS)
    // ==========================================
    public ClientApp() {
        this(false); 
    }

    public ClientApp(boolean loginAsAnonymous) {
        this.isAnonymousMode = loginAsAnonymous;
        initFrameSettings();
        initComponents();
        applyAnonymousPrivacy();
    }

    // ==========================================
    // 3. THIẾT LẬP GIAO DIỆN (UI SETUP)
    // ==========================================
    private void initFrameSettings() {
        setTitle("Ứng dụng Quản lý File - Phía Client");
        setSize(920, 640); // Tăng chiều cao để giao diện chứa đủ nút Xem trực tiếp
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void initComponents() {
        setJMenuBar(createMenuBar());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTabbedPane(), BorderLayout.CENTER);
        add(createActionPanel(), BorderLayout.EAST);
        add(createStoragePanel(), BorderLayout.SOUTH); 
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem itemLogout = new JMenuItem("Đăng xuất");
        JMenuItem itemExit = new JMenuItem("Đóng hệ thống");

        itemLogout.addActionListener(e -> {
            this.dispose();
            new ClientApp(false).setVisible(true);
        });
        itemExit.addActionListener(e -> System.exit(0));

        menuHeThong.add(itemLogout);
        menuHeThong.add(itemExit);
        menuBar.add(menuHeThong);
        return menuBar;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        lblWelcome = new JLabel("<html><h2>Xin chào, User (Thư mục cá nhân)</h2></html>", JLabel.LEFT);
        headerPanel.add(lblWelcome, BorderLayout.WEST);
        
        btnNotif = new JButton("Thông báo chia sẻ (0)"); 
        btnNotif.setBackground(Color.LIGHT_GRAY);
        btnNotif.setFocusPainted(false);
        btnNotif.addActionListener(e -> handleShowNotifications());
        headerPanel.add(btnNotif, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JTabbedPane createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Thư mục Cá nhân", createEmptyFileTable());
        tabbedPane.addTab("Được chia sẻ với tôi", createEmptyFileTable());
        tabbedPane.addTab("Thư mục Chung", createEmptyFileTable());
        return tabbedPane;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));
        actionPanel.setBorder(new EmptyBorder(20, 10, 10, 20));

        JButton btnUpload = createActionButton("Tải lên (Upload)");
        JButton btnDownload = createActionButton("Tải xuống (Download)");
        JButton btnView = createActionButton("Xem trực tiếp (View)"); // MỚI THÊM: Nút xem trực tiếp
        btnView.setForeground(new Color(0, 120, 0));
        JButton btnPause = createActionButton("Tạm dừng (Pause)");
        JButton btnResume = createActionButton("Tiếp tục (Resume)");
        JButton btnDelete = createActionButton("Xóa / Hủy (Delete)"); 
        btnDelete.setForeground(Color.RED);
        JButton btnShare = createActionButton("Chia sẻ File/Thư mục");
        
        btnAnonymous = createActionButton("Chế độ: Thường");
        btnAnonymous.setBackground(new Color(230, 230, 230));

        btnUpload.addActionListener(e -> handleUpload());
        btnDownload.addActionListener(e -> handleDownload());
        btnView.addActionListener(e -> handleViewDirectly()); // Gắn sự kiện xem trực tiếp
        btnPause.addActionListener(e -> handlePause());
        btnResume.addActionListener(e -> handleResume());
        btnDelete.addActionListener(e -> handleDelete());
        btnShare.addActionListener(e -> handleShare());
        btnAnonymous.addActionListener(e -> handleToggleAnonymous());

        actionPanel.add(btnUpload);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnDownload);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnView); // Đặt nút Xem ngay dưới nút Download
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(btnPause);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnResume);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        actionPanel.add(btnDelete); 
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(btnShare);
        actionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        actionPanel.add(new JLabel("Ẩn danh nhanh:"));
        actionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        actionPanel.add(btnAnonymous);
        
        return actionPanel;
    }

    private JPanel createStoragePanel() {
        JPanel storagePanel = new JPanel(new BorderLayout(10, 10));
        storagePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        lblStorage = new JLabel("Dung lượng đã dùng: 0 MB / 1 GB");
        storageBar = new JProgressBar(0, 1024); 
        storageBar.setValue(0); 
        storageBar.setStringPainted(true);
        storageBar.setForeground(new Color(50, 150, 250));
        
        storagePanel.add(lblStorage, BorderLayout.WEST);
        storagePanel.add(storageBar, BorderLayout.CENTER);
        return storagePanel;
    }

    // ==========================================
    // 4. XỬ LÝ SỰ KIỆN (EVENT HANDLERS REAL-TIME)
    // ==========================================

    private void handleUpload() {
        if (isAnonymousMode && tabbedPane.getSelectedIndex() != 2) {
            JOptionPane.showMessageDialog(this, "Tài khoản ẩn danh chỉ được phép upload vào Thư mục Chung!");
            return;
        }

        if (tabbedPane.getSelectedIndex() == 1) {
            JTable currentTable = getCurrentTable();
            if (currentTable != null && currentTable.getSelectedRow() != -1) {
                String quyen = (String) currentTable.getValueAt(currentTable.getSelectedRow(), 3);
                if (quyen != null && quyen.contains("Chỉ đọc")) {
                    JOptionPane.showMessageDialog(this, "Thư mục này chỉ có quyền 'Chỉ đọc', bạn không thể tải file lên!");
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Bạn không thể upload trực tiếp vào vùng chia sẻ của người khác khi chưa được cấp quyền!");
                return;
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            DefaultTableModel model = getCurrentTableModel();
            
            if (model != null) {
                String uploader = isAnonymousMode ? "Ẩn danh" : "User (Bạn)";
                String type = isAnonymousMode ? "Anonymous" : "Công khai";
                
                double sizeMB = selectedFile.length() / (1024.0 * 1024.0);
                if (sizeMB < 1.0) {
                    sizeMB = 10.0 + (selectedFile.length() % 50); // Giả lập mức dung lượng xem cho trực quan
                }
                String fileSizeStr = String.format("%.1f MB", sizeMB);

                model.addRow(new Object[]{
                    selectedFile.getName(), fileSizeStr, uploader, type, "Đang tải lên (0%)"
                });

                int rowIndex = model.getRowCount() - 1;
                final int[] progress = {0};
                Timer timer = new Timer(100, null);
                timer.addActionListener(e -> {
                    if (rowIndex >= model.getRowCount()) {
                        ((Timer) e.getSource()).stop();
                        return;
                    }
                    String currentStatus = (String) model.getValueAt(rowIndex, 4);
                    if (currentStatus != null && currentStatus.contains("Tạm dừng")) return;

                    progress[0] += 5;
                    if (progress[0] <= 100) {
                        model.setValueAt("Đang tải lên (" + progress[0] + "%)", rowIndex, 4);
                    }
                    if (progress[0] >= 100) {
                        model.setValueAt("Đã tải lên", rowIndex, 4);
                        updateStorageDisplay();
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.start();
            }
        }
    }

    //Phân quyền tải xuống - Chỉ đọc KHÔNG ĐƯỢC tải về máy
    private void handleDownload() {
        JTable currentTable = getCurrentTable();
        if (currentTable != null) {
            int selectedRow = currentTable.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) currentTable.getModel();

                // Kiểm tra nếu đang ở Tab 1 (Được chia sẻ) và có quyền Chỉ đọc
                if (tabbedPane.getSelectedIndex() == 1) {
                    String quyen = (String) model.getValueAt(selectedRow, 3); // Cột Kiểu/Quyền hạn
                    if (quyen != null && quyen.contains("Chỉ đọc")) {
                        JOptionPane.showMessageDialog(this, 
                                "🚨 [TRUY CẬP BỊ TỪ CHỐI]\nFile này được cấp quyền 'Chỉ đọc'. Bạn không có quyền tải xuống thiết bị!", 
                                "Lỗi Bảo Mật ACL", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                model.setValueAt("Đang tải xuống (0%)", selectedRow, 4);
                final int[] progress = {0};
                Timer timer = new Timer(120, null);
                timer.addActionListener(e -> {
                    if (selectedRow >= model.getRowCount()) {
                        ((Timer) e.getSource()).stop();
                        return;
                    }
                    String currentStatus = (String) model.getValueAt(selectedRow, 4);
                    if (currentStatus != null && currentStatus.contains("Tạm dừng")) return;

                    progress[0] += 10;
                    if (progress[0] <= 100) {
                        model.setValueAt("Đang tải xuống (" + progress[0] + "%)", selectedRow, 4);
                    }
                    if (progress[0] >= 100) {
                        model.setValueAt("Đã tải xuống", selectedRow, 4);
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.start();
                JOptionPane.showMessageDialog(this, "Bắt đầu tải xuống file: " + model.getValueAt(selectedRow, 0));
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một file từ bảng để tải xuống!");
            }
        }
    }

    //Hàm xử lý Xem trực tiếp file (Hỗ trợ cả Chỉ đọc và Toàn quyền)
    private void handleViewDirectly() {
        JTable currentTable = getCurrentTable();
        if (currentTable != null) {
            int selectedRow = currentTable.getSelectedRow();
            if (selectedRow != -1) {
                String fileName = (String) currentTable.getValueAt(selectedRow, 0);
                String quyen = (tabbedPane.getSelectedIndex() == 1) ? (String) currentTable.getValueAt(selectedRow, 3) : "Toàn quyền sở hữu";
                
                JOptionPane.showMessageDialog(this, 
                        "🌐 [XEM TRỰC TIẾP FILE SỬ DỤNG TRÌNH DUYỆT]\n" +
                        "▪️ Tên tài liệu: " + fileName + "\n" +
                        "▪️ Quyền truy cập hiện tại: " + quyen + "\n" +
                        "▪️ Trạng thái: Đang kết xuất luồng dữ liệu xem trực tuyến thành công!", 
                        "Preview Online System", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một file trong bảng để xem trực tiếp!");
            }
        }
    }

    private void handlePause() {
        JTable currentTable = getCurrentTable();
        if (currentTable != null) {
            int selectedRow = currentTable.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) currentTable.getModel();
                String trangThaiHienTai = (String) model.getValueAt(selectedRow, 4); 
                
                if (trangThaiHienTai != null && trangThaiHienTai.contains("Đang")) {
                    String phanTram = trangThaiHienTai.replaceAll("[^0-9%]", "");
                    model.setValueAt("Tạm dừng (" + phanTram + ")", selectedRow, 4);
                } else {
                    JOptionPane.showMessageDialog(this, "Chỉ có thể tạm dừng file đang chạy tiến trình!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một file để tạm dừng!");
            }
        }
    }

    private void handleResume() {
        JTable currentTable = getCurrentTable();
        if (currentTable != null) {
            int selectedRow = currentTable.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) currentTable.getModel();
                String trangThaiHienTai = (String) model.getValueAt(selectedRow, 4); 
                
                if (trangThaiHienTai != null && trangThaiHienTai.contains("Tạm dừng")) {
                    String phanTram = trangThaiHienTai.replaceAll("[^0-9%]", "");
                    if (trangThaiHienTai.contains("xuống") || tabbedPane.getSelectedIndex() == 1) {
                        model.setValueAt("Đang tải xuống (" + phanTram + ")", selectedRow, 4);
                    } else {
                        model.setValueAt("Đang tải lên (" + phanTram + ")", selectedRow, 4);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "File này không ở trạng thái tạm dừng!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một file để tiếp tục tải!");
            }
        }
    }

    private void handleDelete() {
        JTable currentTable = getCurrentTable();
        if (currentTable != null) {
            int selectedRow = currentTable.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel model = (DefaultTableModel) currentTable.getModel();
                String fileName = (String) model.getValueAt(selectedRow, 0);
                
                int choice = JOptionPane.showConfirmDialog(this, 
                        "Bạn có chắc chắn muốn xóa/hủy tiến trình file '" + fileName + "' không?", 
                        "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        
                if (choice == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow); 
                    updateStorageDisplay();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một file trong bảng để xóa!");
            }
        }
    }

    //Kiểm tra tài khoản tồn tại trên hệ thống và truyền chính xác file chia sẻ
    private void handleShare() {
        if (isAnonymousMode) {
            JOptionPane.showMessageDialog(this, "Tài khoản ẩn danh không có quyền chia sẻ file cá nhân!");
            return;
        }

        JTable currentTable = getCurrentTable();
        if (currentTable == null || currentTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một file trong bảng để tiến hành chia sẻ!");
            return;
        }

        String fileName = (String) currentTable.getValueAt(currentTable.getSelectedRow(), 0);
        String fileSize = (String) currentTable.getValueAt(currentTable.getSelectedRow(), 1);

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField txtUser = new JTextField();
        String[] permissions = {"Chỉ đọc (Read-only)", "Toàn quyền (Full control)"}; 
        JComboBox<String> cbPerm = new JComboBox<>(permissions);

        panel.add(new JLabel("Tên người nhận (User):"));
        panel.add(txtUser);
        panel.add(new JLabel("Quyền hạn cấp:"));
        panel.add(cbPerm);

        int result = JOptionPane.showConfirmDialog(this, panel, "Hộp thoại chia sẻ dữ liệu", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String targetUser = txtUser.getText().trim();
            String selectedPerm = (String) cbPerm.getSelectedItem();
            
            if (targetUser.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên tài khoản người nhận!");
                return;
            }

            // KIỂM TRA USER TỒN TẠI TRÊN HỆ THỐNG
            boolean isUserExist = false;
            for (String user : SYSTEM_USERS) {
                if (user.equalsIgnoreCase(targetUser)) {
                    isUserExist = true;
                    break;
                }
            }

            if (!isUserExist) {
                JOptionPane.showMessageDialog(this, 
                        "❌ LỖI: Tài khoản '" + targetUser + "' không tồn tại trên hệ thống!\n" +
                        "(Gợi ý tài khoản demo hợp lệ: admin, user1, user2, giangvien, sinhvien)", 
                        "Chia sẻ thất bại", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nếu user hợp lệ, ghi nhận thông tin file chính xác sang bộ nhớ tạm để mô phỏng nhận thông báo
            lastSharedFileName = fileName;
            lastSharedSize = fileSize;
            lastSharedPerm = selectedPerm;

            JOptionPane.showMessageDialog(this, "Đã gửi yêu cầu chia sẻ '" + fileName + "' tới người dùng '" + targetUser + "' thành công!");
            
            // Giả lập nhận được file phản hồi sau 2 giây
            Timer t = new Timer(2000, event -> {
                notifCount++;
                btnNotif.setText("Thông báo chia sẻ (" + notifCount + ")");
                btnNotif.setBackground(Color.RED); 
                btnNotif.setForeground(Color.WHITE);
                ((Timer)event.getSource()).stop();
            });
            t.start();
        }
    }

    private void handleToggleAnonymous() {
        isAnonymousMode = !isAnonymousMode;
        applyAnonymousPrivacy();
    }

    private void applyAnonymousPrivacy() {
        if (isAnonymousMode) {
            if (btnAnonymous != null) {
                btnAnonymous.setText("Chế độ: Ẩn danh");
                btnAnonymous.setBackground(Color.ORANGE);
            }
            lblWelcome.setText("<html><h2>Xin chào, Anonymous (Chế độ Ẩn danh)</h2></html>");
            
            tabbedPane.setEnabledAt(0, false);
            tabbedPane.setEnabledAt(1, false);
            tabbedPane.setSelectedIndex(2);
            
            storageBar.setVisible(false);
            lblStorage.setText("Dung lượng bộ nhớ: Không giới hạn (Thư mục Chung)");
        } else {
            if (btnAnonymous != null) {
                btnAnonymous.setText("Chế độ: Thường");
                btnAnonymous.setBackground(new Color(230, 230, 230));
            }
            lblWelcome.setText("<html><h2>Xin chào, User (Thư mục cá nhân)</h2></html>");
            
            tabbedPane.setEnabledAt(0, true);
            tabbedPane.setEnabledAt(1, true);
            tabbedPane.setSelectedIndex(0);
            
            updateStorageDisplay();
        }
    }

    private int calculateCurrentStorage() {
        int totalMB = 0;
        totalMB += getStorageFromTab(0);
        totalMB += getStorageFromTab(2);
        return totalMB;
    }

    private int getStorageFromTab(int tabIndex) {
        int tabMB = 0;
        try {
            JPanel panel = (JPanel) tabbedPane.getComponentAt(tabIndex);
            JScrollPane scroll = (JScrollPane) panel.getComponent(0);
            JTable table = (JTable) scroll.getViewport().getView();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            
            for (int i = 0; i < model.getRowCount(); i++) {
                String uploader = (String) model.getValueAt(i, 2);
                String status = (String) model.getValueAt(i, 4);
                if (uploader != null && uploader.contains("Bạn") && status != null && status.equals("Đã tải lên")) {
                    String sizeStr = (String) model.getValueAt(i, 1);
                    if (sizeStr != null && sizeStr.contains("MB")) {
                        double sizeMB = Double.parseDouble(sizeStr.replace("MB", "").trim());
                        tabMB += (int) sizeMB;
                    }
                }
            }
        } catch (Exception e) {}
        return tabMB;
    }

    private void updateStorageDisplay() {
        if (isAnonymousMode) return;
        
        storageBar.setVisible(true);
        int totalStorage = calculateCurrentStorage();
        storageBar.setValue(totalStorage);
        lblStorage.setText("Dung lượng đã dùng: " + totalStorage + " MB / 1 GB");
    }

    //Đổ chính xác file vừa gửi với trạng thái mặc định "Chưa tải"
    private void handleShowNotifications() {
        if (notifCount == 0) {
            JOptionPane.showMessageDialog(this, "Bạn không có thông báo chia sẻ mới nào.");
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Bạn nhận được dữ liệu file chia sẻ mới từ tài khoản hệ thống!\nHệ thống tự động đồng bộ vào mục 'Được chia sẻ với tôi'.");
        
        JPanel panelTab1 = (JPanel) tabbedPane.getComponentAt(1);
        JScrollPane scroll = (JScrollPane) panelTab1.getComponent(0);
        JTable tableTab1 = (JTable) scroll.getViewport().getView();
        DefaultTableModel model = (DefaultTableModel) tableTab1.getModel();
        
        // Nếu trước đó người dùng có thực hiện chia sẻ file, lấy đúng tên file đó để hiển thị
        if (lastSharedFileName != null && !lastSharedFileName.isEmpty()) {
            model.addRow(new Object[]{lastSharedFileName, lastSharedSize, "Người dùng khác", lastSharedPerm, "Chưa tải"});
        } else {
            // Trường hợp click test thông báo mặc định ban đầu khi chưa chọn file chia sẻ cụ thể
            model.addRow(new Object[]{"Báo_Cáo_Bài_Tập_Lớn.docx", "12.5 MB", "Giảng Viên", "Chỉ đọc (Read-only)", "Chưa tải"});
        }
        
        notifCount = 0;
        btnNotif.setText("Thông báo chia sẻ (0)");
        btnNotif.setBackground(Color.LIGHT_GRAY);
        btnNotif.setForeground(Color.BLACK);
        tabbedPane.setSelectedIndex(1); // Tự động bật sang tab 1 cho giảng viên xem kết quả
    }

    // ==========================================
    // 5. CÁC HÀM TIỆN ÍCH (UTILITY METHODS)
    // ==========================================
    private JTable getCurrentTable() {
        JPanel currentPanel = (JPanel) tabbedPane.getSelectedComponent();
        if (currentPanel != null && currentPanel.getComponentCount() > 0) {
            JScrollPane scrollPane = (JScrollPane) currentPanel.getComponent(0);
            return (JTable) scrollPane.getViewport().getView();
        }
        return null;
    }

    private DefaultTableModel getCurrentTableModel() {
        JTable table = getCurrentTable();
        if (table != null) return (DefaultTableModel) table.getModel();
        return null;
    }

    private JPanel createEmptyFileTable() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = {"Tên File", "Kích thước", "Người tải lên", "Kiểu quyền", "Trạng thái"};
        
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        JTable table = new JTable(model);
        table.setRowHeight(25); 
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JButton createActionButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(180, 35));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        return btn;
    }

    // ==========================================
    // 6. MAIN METHOD
    // ==========================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ClientApp().setVisible(true); 
        });
    }
}