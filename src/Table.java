// Packages to import
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Table {
	JTable j;

	// Constructor
	JPanel panel(String title, String[][] data)
	{
		// Frame initialization
		JPanel f = new JPanel();
        JLabel label2 = new JLabel(title);
        f.add(label2);

		// Column Names
		String[] columnNames = { "Block", "Block Set", "Data" };

		// Initializing the JTable
		j = new JTable(data, columnNames);
		j.setSize(200, 800);


		f.add(j);
		// Frame Size
		f.setSize(500, 200);
		// Frame Visible = true
		f.setVisible(true);
        f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));

        return f;
	}
}
