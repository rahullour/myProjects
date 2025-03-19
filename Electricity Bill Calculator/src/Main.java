//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Main {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";
    static final String USER = "root";
    static final String PASS = "admin";
    static int id_old_val;
    static float main_meter_units;
    static float User1_home_meter_units;
    static float new_main_meter_units;
    static float bill_amt_input;
    static float User1_home_meter_new_units_input;
    static float new_water_units_input;
    static float water_units_User1_percentage_input;
    static float total_bill_units;
    static float per_unit_amt;
    static float User1_home_units;
    static float total_water_units;
    static float User1_water_units;
    static float User2_water_units;
    static float User2_home_units;
    static float total_User1_units;
    static float total_User2_units;
    static float payable_User1_amt;
    static float payable_User2_amt;

    public Main() {
    }

    public static void main(String[] args) {
        final JFrame f = new JFrame();
        JButton calculate_button = new JButton("CALCULATE BILL");
        JButton update_button = new JButton("ADD NEW BILL RECORD");
        JButton show_last_records = new JButton("SHOW LAST RECORDS");
        final JTextField nbu = new JTextField();
        final JTextField bill_amt = new JTextField();
        final JTextField User1_home_meter_new_units = new JTextField();
        final JTextField new_water_units = new JTextField();
        final JTextField new_water_units_User1_percentage = new JTextField();
        JLabel nbu_label = new JLabel("Main Meter New Units  :");
        JLabel nb_amt_label = new JLabel("New Bill Total Amount  :");
        JLabel User1_home_meter_new_units_label = new JLabel("User1_ Home Meter New Units  :");
        JLabel new_water_units_label = new JLabel("Total Water Units Consumed  :");
        JLabel new_water_units_User1_percentage_label = new JLabel("Water Units User1_ Share Percentage :");
        nbu_label.setBounds(195, 50, 250, 25);
        nb_amt_label.setBounds(195, 75, 250, 25);
        User1_home_meter_new_units_label.setBounds(166, 100, 250, 25);
        new_water_units_label.setBounds(156, 125, 250, 25);
        new_water_units_User1_percentage_label.setBounds(127, 150, 250, 25);
        nbu.setBounds(350, 50, 150, 25);
        bill_amt.setBounds(350, 75, 150, 25);
        User1_home_meter_new_units.setBounds(350, 100, 150, 25);
        new_water_units.setBounds(350, 125, 150, 25);
        new_water_units_User1_percentage.setBounds(350, 150, 150, 25);
        calculate_button.setBounds(200, 250, 200, 50);
        calculate_button.setBackground(Color.GREEN);
        calculate_button.setForeground(Color.BLACK);
        show_last_records.setBounds(200, 350, 200, 50);
        show_last_records.setBackground(Color.BLUE);
        show_last_records.setForeground(Color.WHITE);
        update_button.setBounds(200, 450, 200, 50);
        update_button.setBackground(Color.RED);
        update_button.setForeground(Color.WHITE);
        f.setLayout((LayoutManager)null);
        f.add(new_water_units_User1_percentage);
        f.add(nbu);
        f.add(bill_amt);
        f.add(new_water_units);
        f.add(User1_home_meter_new_units);
        f.add(new_water_units_label);
        f.add(new_water_units_User1_percentage_label);
        f.add(User1_home_meter_new_units_label);
        f.add(nbu_label);
        f.add(nb_amt_label);
        f.add(calculate_button);
        f.add(update_button);
        f.add(show_last_records);
        f.setTitle("Electricity Bill Calculator");
        f.setResizable(false);
        f.setBounds(475, 100, 600, 600);
        f.setVisible(true);
        f.setDefaultCloseOperation(3);
        Connection conn = null;
        Statement stmt = null;

        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "admin");
        } catch (Exception var25) {
            System.out.println(var25);
            System.out.println("Connection Failed!");
        }

        try {
            System.out.println("Creating Statement...");
            stmt = conn.createStatement();
        } catch (SQLException var24) {
            System.out.println("Statement Creation Failed!");
        }

        String sql;
        try {
            sql = "CREATE DATABASE ElectricityBill";
            stmt.executeUpdate(sql);
            System.out.println("Database Created Successfully...");
        } catch (SQLException var23) {
            System.out.println("Database Creation Failed!");
        }

        try {
            sql = "use ElectricityBill;";
            stmt.executeUpdate(sql);
            System.out.println("Database In Use");
        } catch (SQLException var22) {
            System.out.println("Database Selection Failed!");
        }

        try {
            sql = "CREATE TABLE BillRecord(\n  id int(5)  NOT NULL UNIQUE,\n  main_meter_units float(20,4) PRIMARY KEY NOT NULL,\n  total_bill_units float(15, 4),\n  total_bill_amount float(15, 4),\n  per_unit_amount float(5, 3),\n  User1_home_meter_units float(20, 4),\n  User1_bill_units float(15, 4),\n  User2_bill_units float(15, 4),\n  water_units float(10, 4),\n  User1_water_units_percentage float(5, 3),\n  User2_water_units_percentage float(5, 3),\n  User1_water_units float(10, 4),\n  User2_water_units float(10, 4),\n  total_payable_User1_units float(10,4),\n  total_payable_User2_units float(10,4),\n  User1_bill_amount float(15,4),\n  User2_bill_amount float(15,4)\n);\n";
            stmt.executeUpdate(sql);
            System.out.println("Table Created Successfully...");
        } catch (SQLException var21) {
            System.out.println("Table Creation Failed!");
        }

        try {
            sql = "select id,main_meter_units,User1_home_meter_units from BillRecord ORDER BY id DESC LIMIT 1;";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            id_old_val = rs.getInt("id");
            main_meter_units = rs.getFloat("main_meter_units");
            User1_home_meter_units = rs.getFloat("User1_home_meter_units");
            System.out.println("Data Selection Successfull!");
            conn.close();
        } catch (SQLException var20) {
            System.out.println(var20);
        }

        System.out.println("Goodbye!");
        calculate_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.new_main_meter_units = Float.parseFloat(nbu.getText());
                    Main.bill_amt_input = Float.parseFloat(bill_amt.getText());
                    Main.User1_home_meter_new_units_input = Float.parseFloat(User1_home_meter_new_units.getText());
                    Main.new_water_units_input = Float.parseFloat(new_water_units.getText());
                    Main.water_units_User1_percentage_input = Float.parseFloat(new_water_units_User1_percentage.getText());
                    if (Main.new_main_meter_units >= Main.main_meter_units) {
                        Main.total_bill_units = Main.new_main_meter_units - Main.main_meter_units;
                        Main.per_unit_amt = Main.bill_amt_input / Main.total_bill_units;
                        Main.User1_home_units = Main.User1_home_meter_new_units_input - Main.User1_home_meter_units;
                        Main.total_water_units = Main.new_water_units_input;
                        Main.User1_water_units = Main.total_water_units / 100.0F * Main.water_units_User1_percentage_input;
                        Main.User2_water_units = Main.total_water_units - Main.User1_water_units;
                        Main.User2_home_units = Main.total_bill_units - Main.total_water_units - Main.User1_home_units;
                        Main.total_User1_units = Main.User1_water_units + Main.User1_home_units;
                        Main.total_User2_units = Main.total_bill_units - Main.total_User1_units;
                        Main.payable_User1_amt = Main.total_User1_units * Main.per_unit_amt;
                        Main.payable_User2_amt = Main.total_User2_units * Main.per_unit_amt;
                        JOptionPane.showMessageDialog(f, "                                                 Electricity Cost = " + Main.per_unit_amt + " Rs./Unit. \n\n------------------------------------------------------------------------------------------------------------------\n\nUser1_ Parkash Bill Units(Not Water) = " + Main.User1_home_units + "\nUser1_ Parkash Water Units = " + Main.User1_water_units + "\nUser1_ Parkash Total Bill Units = " + Main.total_User1_units + "\nUser1_ Parkash Bill To Be Paid = " + Main.payable_User1_amt + " Rs. \n\n------------------------------------------------------------------------------------------------------------------\n\nUser2 Bill Units(Not Water) = " + Main.User2_home_units + "\nUser2 Water Units = " + Main.User2_water_units + "\nUser2 Total Bill Units = " + Main.total_User2_units + "\nUser2 Bill To Be Paid = " + Main.payable_User2_amt + " Rs. ", "Bill Amount Shares :)", -1);
                    } else {
                        JOptionPane.showMessageDialog(f, "Main meter units consumption cannot be less than the last consumed units !", "Error!", 0);
                    }
                } catch (Exception var3) {
                    JOptionPane.showMessageDialog(f, "Please Enter The Values First Or Check Their Format !", "Error!", 0);
                }

            }
        });
        update_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JOptionPane();

                JOptionPane dialog;
                try {
                    Main.new_main_meter_units = Float.parseFloat(nbu.getText());
                    Main.bill_amt_input = Float.parseFloat(bill_amt.getText());
                    Main.User1_home_meter_new_units_input = Float.parseFloat(User1_home_meter_new_units.getText());
                    Main.new_water_units_input = Float.parseFloat(new_water_units.getText());
                    Main.water_units_User1_percentage_input = Float.parseFloat(new_water_units_User1_percentage.getText());
                    String[] options = new String[]{"NO", "YES"};
                    int selectedOption = JOptionPane.showOptionDialog(f, "Are You Sure You Want To Update Bill Record With Above Input Values ! ", "COFIRMATION TO PROCEED !", -1, 1, (Icon)null, options, options[0]);
                    if (1 == selectedOption) {
                        Connection conn = null;
                        Statement stmt = null;

                        try {
                            System.out.println("Connecting to database...");
                            conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "admin");
                        } catch (Exception var12) {
                            System.out.println(var12);
                            System.out.println("Connection Failed!");
                        }

                        try {
                            System.out.println("Creating Statement...");
                            stmt = conn.createStatement();
                        } catch (SQLException var11) {
                            System.out.println("Statement Creation Failed!");
                        }

                        String sql;
                        try {
                            sql = "use ElectricityBill;";
                            stmt.executeUpdate(sql);
                            System.out.println("Database In Use");
                        } catch (SQLException var10) {
                            System.out.println("Database Selection Failed!");
                        }

                        try {
                            sql = "INSERT INTO BillRecord(main_meter_units , total_bill_units , total_bill_amount , per_unit_amount , User1_home_meter_units , User1_bill_units , User2_bill_units , water_units , User1_water_units_percentage , User2_water_units_percentage , User1_water_units , User2_water_units , total_payable_User1_units ,total_payable_User2_units , User1_bill_amount , User2_bill_amount ,id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                            PreparedStatement statement = conn.prepareStatement(sql);
                            statement.setFloat(1, Main.new_main_meter_units);
                            statement.setFloat(2, Main.total_bill_units);
                            statement.setFloat(3, Main.bill_amt_input);
                            statement.setFloat(4, Main.per_unit_amt);
                            statement.setFloat(5, Main.User1_home_meter_new_units_input);
                            statement.setFloat(6, Main.User1_home_units);
                            statement.setFloat(7, Main.User2_home_units);
                            statement.setFloat(8, Main.total_water_units);
                            statement.setFloat(9, Main.water_units_User1_percentage_input);
                            statement.setFloat(10, 100.0F - Main.water_units_User1_percentage_input);
                            statement.setFloat(11, Main.User1_water_units);
                            statement.setFloat(12, Main.User2_water_units);
                            statement.setFloat(13, Main.total_User1_units);
                            statement.setFloat(14, Main.total_User2_units);
                            statement.setFloat(15, Main.payable_User1_amt);
                            statement.setFloat(16, Main.payable_User2_amt);
                            statement.setInt(17, Main.id_old_val + 1);
                            statement.executeUpdate();
                            System.out.println("Data Insertion Successful");
                            dialog = new JOptionPane();
                            dialog.showMessageDialog(f, "New Bill Record Updated !", "BILL RECORD UPDATE STATUS", -1);
                            stmt.close();
                            System.out.println("Show last records - Data Selection Successfull!");
                        } catch (SQLException var9) {
                            System.out.println("Data Insertion Failed! " + var9);
                            dialog = new JOptionPane();
                            dialog.showMessageDialog(f, "Update Failed !", "BILL RECORD UPDATE STATUS", 0);
                        }
                    }
                } catch (Exception var13) {
                    System.out.println("Data Insertion Failed! " + var13);
                    dialog = new JOptionPane();
                    JOptionPane.showMessageDialog(f, "Please Enter The Values First Or Check Their Format !", "Error !", 0);
                }

            }
        });
        show_last_records.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Connection conn = null;
                Statement stmt = null;

                try {
                    System.out.println("Connecting to database...");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "admin");
                } catch (Exception var20) {
                    System.out.println(var20);
                    System.out.println("Connection Failed!");
                }

                try {
                    System.out.println("Creating Statement...");
                    stmt = conn.createStatement();
                } catch (SQLException var19) {
                    System.out.println("Statement Creation Failed!");
                }

                String sql;
                try {
                    sql = "use ElectricityBill;";
                    stmt.executeUpdate(sql);
                    System.out.println("Database In Use");
                } catch (SQLException var18) {
                    System.out.println("Database Selection Failed!");
                }

                try {
                    sql = "select * from BillRecord;";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    ResultSet rs = statement.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int numberOfColumns = rsmd.getColumnCount();
                    sql = "select count(*) AS count from billrecord;";
                    statement = conn.prepareStatement(sql);
                    ResultSet rs_rowcount = statement.executeQuery();
                    rs_rowcount.next();
                    int numberOfRows = rs_rowcount.getInt("count");
                    ++numberOfRows;
                    String[] column_name = new String[numberOfColumns];
                    String[][] rows_data = new String[numberOfRows][numberOfColumns];
                    int j = 0;

                    int k;
                    for(k = 1; k <= numberOfColumns; ++j) {
                        String columnName = rsmd.getColumnName(k);
                        column_name[j] = "";
                        rows_data[0][j] = columnName.toUpperCase(Locale.ROOT);
                        ++k;
                    }

                    j = 1;
                    k = 0;

                    while(rs.next()) {
                        for(int i = 1; i <= numberOfColumns; ++i) {
                            rows_data[j][k] = rs.getString(i);
                            ++k;
                            if (i == numberOfColumns) {
                                ++j;
                                k = 0;
                            }
                        }
                    }

                    JTable jt = new JTable(rows_data, column_name);
                    jt.setPreferredSize(new Dimension(4600, 1700));
                    jt.setAutoResizeMode(0);
                    JScrollPane sp = new JScrollPane(jt);
                    sp.setPreferredSize(new Dimension(1250, 700));
                    JOptionPane dialog = new JOptionPane();
                    jt.setTableHeader((JTableHeader)null);
                    jt.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            c.setBackground(row == 0 ? Color.BLACK : Color.WHITE);
                            c.setForeground(row == 0 ? Color.WHITE : Color.BLACK);
                            if (row == 0) {
                                c.setFont(new Font("Monospaced", 1, 15));
                            } else {
                                c.setFont(new Font("default", 0, 15));
                            }

                            return c;
                        }
                    });
                    JOptionPane.showMessageDialog(f, sp, "OLD BILLS RECORD", -1);
                    stmt.close();
                    System.out.println("Show last records - Data Selection Successfull!");
                } catch (SQLException var21) {
                    System.out.println("Show last records - Data Selection Failed!");
                    System.err.print("SQLException: ");
                    System.err.println(var21.getMessage());
                }

            }
        });
    }
}
