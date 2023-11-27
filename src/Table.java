// Packages to import
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Table {
	JTable j;

	// Constructor
	JPanel panel(String title)
	{
		// Frame initialization
		JPanel f = new JPanel();
        JLabel label2 = new JLabel(title);
        f.add(label2);

		// Data to be displayed in the JTable
		String[][] data = {
			{ "0", "0", "" },
			{ "0", "1", "" },
            { "0", "2", "" },
            { "0", "3", "" },
            { "0", "4", "" },
            { "0", "5", "" },
            { "0", "6", "" },
            { "0", "7", "" },
            { "1", "0", "" },
			{ "1", "1", "" },
            { "1", "2", "" },
            { "1", "3", "" },
            { "1", "4", "" },
            { "1", "5", "" },
            { "1", "6", "" },
            { "1", "7", "" },
            { "2", "0", "" },
			{ "2", "1", "" },
            { "2", "2", "" },
            { "2", "3", "" },
            { "2", "4", "" },
            { "2", "5", "" },
            { "2", "6", "" },
            { "2", "7", "" },
            { "3", "0", "" },
			{ "3", "1", "" },
            { "3", "2", "" },
            { "3", "3", "" },
            { "3", "4", "" },
            { "3", "5", "" },
            { "3", "6", "" },
            { "3", "7", "" },
		};

		// Column Names
		String[] columnNames = { "Block", "Block Set", "Data" };

		// Initializing the JTable
		j = new JTable(data, columnNames);
		j.setSize(200, 300);


		f.add(j);
		// Frame Size
		f.setSize(500, 200);
		// Frame Visible = true
		f.setVisible(true);
        f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));

        return f;
	}
}
