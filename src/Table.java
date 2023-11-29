import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class Table {
    JTable j;

    // Constructor
    JPanel panel(String title, String[][] data) {
        // Frame initialization
        JPanel f = new JPanel();
        JLabel label2 = new JLabel(title);
        f.add(label2);

        // Column Names
        String[] columnNames = {"Block Set", "Block", "Data"};

        // Initializing the JTable with a custom table model
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells not editable
            }
        };
        j = new JTable(tableModel);

        // Center align cell text
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        j.setDefaultRenderer(Object.class, centerRenderer);

        // Bold column headers
        JTableHeader header = j.getTableHeader();
        header.setDefaultRenderer(new BoldHeaderRenderer(j));

        // Applying color scheme for each row
        j.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); // Alternate colors
                setHorizontalAlignment(CENTER); // Center text
                return c;
            }
        });

        j.setSize(200, 800);
        f.add(j.getTableHeader(), BorderLayout.NORTH);
        f.add(j, BorderLayout.CENTER);
        f.setSize(500, 200);
        f.setVisible(true);
        f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));

        return f;
    }

    // Custom renderer for bold header
    private static class BoldHeaderRenderer extends DefaultTableCellRenderer {
        public BoldHeaderRenderer(JTable table) {
            JTableHeader header = table.getTableHeader();
            setHorizontalAlignment(CENTER);
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont().deriveFont(Font.BOLD));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setHorizontalAlignment(CENTER);
            return label;
        }
    }
}
