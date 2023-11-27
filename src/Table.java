import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Table {
    private JTable table;
    private DefaultTableModel model;
    private String[] columnNames; // Now an instance variable

    public Table() {
        // Initialize column names here or through a constructor parameter
        this.columnNames = new String[]{"Block", "Block Set", "Data"};
    }

    public JPanel panel(String title, String[][] data) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(title);
        panel.add(label);

        // Initializing the JTable with a DefaultTableModel
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setSize(200, 800);

        panel.add(new JScrollPane(table)); // Add table to JScrollPane for scroll functionality
        panel.setSize(500, 200);
        panel.setVisible(true);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        return panel;
    }

    public void updateTable(String[][] newData) {
        model.setDataVector(newData, columnNames);
    }
}
