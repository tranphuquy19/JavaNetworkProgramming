package tk.doraneko.test.swingtest;

import tk.doraneko.test.threelayerpattern.Default;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

/*
 * @author tranphuquy19@gmail.com
 * @since 10/16/2019
 */
public class DbPrograming extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField txtConnectionString;
    private JTextField txtSqlCommand;

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

        table = new JTable();
        table.setBounds(10, 107, 483, 164);
        contentPane.add(table);

        txtConnectionString = new JTextField();
        txtConnectionString.setBounds(195, 45, 234, 20);
        contentPane.add(txtConnectionString);
        txtConnectionString.setColumns(10);

        txtSqlCommand = new JTextField();
        txtSqlCommand.setBounds(195, 71, 234, 20);
        contentPane.add(txtSqlCommand);
        txtSqlCommand.setColumns(10);

        JButton btnSubmit = new JButton("SUBMIT");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SqlHelper sqlHelper = new SqlHelper();

                String connectionString = txtConnectionString.getText();
                String sql = txtSqlCommand.getText();

                if (sql.isEmpty() || connectionString.isEmpty()) return;
                sqlHelper.setConnectionString(connectionString);
                sqlHelper.setSql(sql);

                Object[] titles = sqlHelper.getTiles();
                Object[][] rows = sqlHelper.getRows();
                DefaultTableModel dt = new DefaultTableModel(rows, titles);
                table.setModel(dt);

                table.updateUI();
            }
        });
        btnSubmit.setBounds(72, 292, 89, 23);
        contentPane.add(btnSubmit);

        JButton btnReset = new JButton("RESET");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                txtConnectionString.setText("");
                txtSqlCommand.setText("");
                DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
                defaultTableModel.setRowCount(0);
                table.setModel(defaultTableModel);
                table.updateUI();
            }
        });
        btnReset.setBounds(195, 292, 89, 23);
        contentPane.add(btnReset);

        JButton btnExit = new JButton("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        btnExit.setBounds(315, 291, 89, 23);
        contentPane.add(btnExit);
    }
}

