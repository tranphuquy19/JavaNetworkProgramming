package tk.doraneko.test.swingtest;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;

/*
 * @author tranphuquy19@gmail.com
 * @since 10/16/2019
 */
public class DbPrograming extends JFrame {

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


        textField = new JTextField();
        textField.setBounds(195, 45, 234, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(195, 71, 234, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        JButton btnSubmit = new JButton("SUBMIT");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                table = new JTable();
                table.setBounds(10, 107, 483, 164);

                SqlHelper sqlHelper = new SqlHelper();
                Object[] titles = sqlHelper.getTiles("select * from user_tb");
                Object[][] rows = sqlHelper.getRows("select * from user_tb");
                DefaultTableModel dt = new DefaultTableModel(rows, titles);
                table.setModel(dt);
                table.setFillsViewportHeight(true);
                rootPane.add(new JScrollPane(table));
                rootPane.add(table);
                table.updateUI();
            }
        });
        btnSubmit.setBounds(72, 292, 89, 23);
        contentPane.add(btnSubmit);

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

