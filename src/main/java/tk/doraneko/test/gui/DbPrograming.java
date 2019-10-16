package tk.doraneko.test.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;

/*
 * @author tranphuquy19@gmail.com
 * @since 10/16/2019
 */
public class DbPrograming extends JFrame {

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
                    DbPrograming frame = new DbPrograming();
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
    public DbPrograming() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 519, 387);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Database Programing");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel.setBounds(10, 11, 483, 14);
        contentPane.add(lblNewLabel);

        JLabel lblDatabaseConnectionString = new JLabel("Database Connection String");
        lblDatabaseConnectionString.setBounds(10, 47, 183, 14);
        contentPane.add(lblDatabaseConnectionString);

        JLabel lblSearch = new JLabel("Search");
        lblSearch.setBounds(10, 82, 46, 14);
        contentPane.add(lblSearch);

        table = new JTable();
        table.setBounds(10, 107, 483, 164);
        contentPane.add(table);

        textField = new JTextField();
        textField.setBounds(171, 44, 234, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(171, 79, 234, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnQuery = new JButton("QUERY");
        btnQuery.setBounds(104, 292, 89, 23);
        contentPane.add(btnQuery);

        JButton btnReset = new JButton("RESET");
        btnReset.setBounds(287, 292, 89, 23);
        contentPane.add(btnReset);
    }
}

