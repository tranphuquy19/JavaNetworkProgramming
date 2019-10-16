package tk.doraneko.test.threelayerpattern.gui;

import tk.doraneko.test.threelayerpattern.bus.UserBUS;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author tranphuquy19@gmail.com
 * @since 16/10/2019
 */
public class UserGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserGUI frame = new UserGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UserGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 519, 387);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Database Programing");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
        lblNewLabel.setBounds(10, 0, 483, 25);
        contentPane.add(lblNewLabel);

        JLabel lblDatabaseConnectionString = new JLabel("Input Information");
        lblDatabaseConnectionString.setFont(new Font("Dialog", Font.PLAIN, 12));
        lblDatabaseConnectionString.setBounds(10, 47, 183, 14);
        contentPane.add(lblDatabaseConnectionString);

        JLabel lblSearch = new JLabel("SQL Command");
        lblSearch.setFont(new Font("Dialog", Font.PLAIN, 12));
        lblSearch.setBounds(10, 73, 167, 14);
        contentPane.add(lblSearch);

        table = new JTable();
        table.setBounds(10, 107, 483, 164);
        contentPane.add(table);

        textField = new JTextField();
        textField.setBounds(195, 45, 234, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(195, 71, 234, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnQuery = new JButton("SUBMIT");
        btnQuery.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        btnQuery.setBounds(72, 292, 89, 23);
        contentPane.add(btnQuery);

        JButton btnReset = new JButton("RESET");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        btnReset.setBounds(195, 292, 89, 23);
        contentPane.add(btnReset);

        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        btnExit.setBounds(315, 291, 89, 23);
        contentPane.add(btnExit);
    }
}

