import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class App {
    private JFrame frame;
    private JTable cacheTable;
    private String[][] default_data;
    private String[][] data;
    private JPanel simuPanel;
    private int currentStep = 0;
    private ArrayList<String> inputarr;
    private int[] mruBlock; // Initialize this array in the constructor
    private Timer stepTimer;
    private JTextArea textArea = new JTextArea(20, 50); // 20 rows and 50 columns
    private ArrayList<String> info = new ArrayList<>();
    private int hitcount = 0;
    private int misscount = 0;
    private int set0, set1, set2, set3;
    private int k, l;



    public App() {
        set0 = 0;
        set1 = 0;
        set2 = 0;
        set3 = 0;
        default_data = new String[][] {
            // Initialize your default data here...
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
        data = default_data;
        mruBlock = new int[4]; // Assuming 4 sets, initialize with default values
        Arrays.fill(mruBlock, 7); // Initialize each set's MRU to point to the last block (block 7)
        inputarr = new ArrayList<>();
        stepTimer = new Timer(250, e -> processNextStep()); // Delay of 1000 ms (1 second)
        initializeUI();
    }

        private void initializeUI() {
            frame = new JFrame("8-way BSA + MRU");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1500, 800);

            // Create a JPanel for the title
            JPanel titlePanel = new JPanel();
            JLabel titleLabel = new JLabel("8-way BSA + MRU");
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 24)); // Set the font and size
            titlePanel.add(titleLabel);


            JPanel outerPanel = new JPanel();
            // Initialize other panels and components...
            // Add them to outerPanel...
            JPanel panel = new JPanel(); 
            JPanel bpanel = new JPanel();
            JPanel panel2 = new JPanel();

            JLabel label1 = new JLabel("Inputs:");
            JTextField tf1 = new JTextField(30);
            JLabel label2 = new JLabel("Option:");
            JRadioButton j1 = new JRadioButton("Step-by-Step Tracing");
            JRadioButton j2 = new JRadioButton("Final Memory Snapshot", true);
            j1.setOpaque(false);
            j2.setOpaque(false);
            ButtonGroup rd = new ButtonGroup();
            rd.add(j1);
            rd.add(j2);
            JButton send = new JButton("Simulate");

            panel.add(label1);
            panel.add(tf1);
            panel.setSize(1500, 100);

            bpanel.add(label2);
            bpanel.add(j1);
            bpanel.add(j2);
            bpanel.setSize(1500, 100);

            panel2.add(send);
            panel2.setSize(1500, 100);

            panel.setBackground(Color.LIGHT_GRAY);
            bpanel.setBackground(Color.LIGHT_GRAY);
            panel2.setBackground(Color.LIGHT_GRAY);

            outerPanel.add(panel);
            outerPanel.add(bpanel);
            outerPanel.add(panel2);
            outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
            outerPanel.setBackground(Color.LIGHT_GRAY);

            JButton reset = new JButton("Reset");

            // Add reset button to the panel
            panel2.add(reset);

            // Create a JScrollPane for the text area
             JScrollPane textScrollPane = new JScrollPane(textArea);
            textScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

   

            // Create a panel for the text area
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BorderLayout()); // Use BorderLayout to occupy the entire panel

            // Add the text area to the text panel
            textPanel.add(textScrollPane, BorderLayout.CENTER);

            // Create a JSplitPane to split the space between text area and table
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(textPanel); // Add the text area to the left
            splitPane.setRightComponent(simuPanel); // Add the table to the right

            // Add the splitPane to the frame
            frame.add(splitPane, BorderLayout.CENTER);

            

            // Define ActionListener for the reset button
            reset.addActionListener(e -> {
                // Reset the text field, data arrays, counters, etc.
                tf1.setText("");
                data = default_data.clone();
                hitcount = 0;
                misscount = 0;
                currentStep = 0;
                inputarr.clear();

                // Set the third column of the table to empty
                for (int i = 0; i < data.length; i++) {
                    data[i][2] = ""; // Assuming the third column contains empty strings
                }

                displayCacheState(default_data); // Update the table display
                // Any other resets as required
            });



            simuPanel = new JPanel();

            // Button action listener
            send.addActionListener(e -> {
                
                String inputValue = tf1.getText();

                //String inputValue = tf1.getText();
                if (!validateInput(inputValue)) {
                    return; // Stop processing if input is invalid
                }

                String[] testcase = inputValue.split("\\s");
                int n = testcase.length;

                inputarr.clear();
                inputarr.addAll(Arrays.asList(testcase));
                while (inputarr.size() < n) {
                    inputarr.add(null);
                }

                if (j1.isSelected()) {
                    // Step-by-Step Tracing
                    currentStep = 0;
                    processStep();
                } else if (j2.isSelected()) {
                    // Final Snapshot
                    fSnap(default_data, inputarr, n);
                }
            });

            initializeCacheTable();
            //Dimension size = new Dimension(500, 800); 
            //simuPanel.setPreferredSize(size);
            //setupCellRenderer();
            simuPanel.setBorder(BorderFactory.createEmptyBorder(10, 600, 0, 600));
            frame.add(titlePanel, BorderLayout.NORTH); // Add the title panel to the top
            frame.add(simuPanel, BorderLayout.CENTER);
            frame.add(outerPanel, BorderLayout.SOUTH);
            //frame.add(right, BorderLayout.WEST);
            //frame.add(left, BorderLayout.EAST);
            frame.setVisible(true);
        }

        private boolean validateInput(String input) {
            // Example validation: input should not be empty and should be numeric
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Input is empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!input.matches("^[0-9\\s]+$")) {
                JOptionPane.showMessageDialog(frame, "Input should contain only integers and spaces", "Input Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            // Additional validation rules can be added here
            return true;
        }

        private void initializeCacheTable() {
        DefaultTableModel tableModel = new DefaultTableModel(data, new String[]{"Set", "Block", "Value"}) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells not editable
            }
        };

        cacheTable = new JTable(tableModel);

        // Center align cell text and apply color scheme
        cacheTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE); // Alternate row colors
                setHorizontalAlignment(CENTER); // Center text
                return c;
            }
        });

        // Bold column headers
        JTableHeader header = cacheTable.getTableHeader();
        header.setDefaultRenderer(new BoldHeaderRenderer());
        cacheTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        cacheTable.setSize(200, 800);

        

        //JScrollPane scrollPane = new JScrollPane(cacheTable);
        simuPanel.add(header, BorderLayout.NORTH);
        simuPanel.add(cacheTable, BorderLayout.CENTER);
        
        simuPanel.setSize(500, 200);
        simuPanel.setLayout(new BoxLayout(simuPanel, BoxLayout.PAGE_AXIS));
    }

     // Custom renderer for bold header
     private static class BoldHeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (comp instanceof JLabel) {
                ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
                comp.setFont(comp.getFont().deriveFont(Font.BOLD));
                comp.setBackground(Color.GRAY);  // Set the background color to grey
                comp.setForeground(Color.WHITE); // Optional: Set the text color to white for better readability
            }
            return comp;
        }
    }
    
    private void displayCacheState(String[][] cacheData) {
        DefaultTableModel tableModel = (DefaultTableModel) cacheTable.getModel();
        tableModel.setDataVector(cacheData, new String[]{"Set", "Block", "Value"});
    }

    private void processStep() {
        if (currentStep < inputarr.size()) {
            String input = inputarr.get(currentStep);
            if (input != null && !input.isEmpty()) {
                int val = Integer.parseInt(input);


                switch (val % 4) {
                    case 0:
                        k = notFull(data, 0, 8);
                        if(k != -1)
                            l = isThere(data, 0, k, val);
                        else
                            l = isThere(data, 0, 8, val);
                        //hit
                        if(l != -1){
                            set0 = l;
                            hitcount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set0][1]) + " | Status: Hit |");
                        } else {
                            //miss
                            if (k != -1) {
                                data[k][2] = inputarr.get(currentStep);
                                set0 = k;
                            } else {
                                data[set0][2] = inputarr.get(currentStep);
                            }
                            misscount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set0][1]) + " | Status: Miss |");
                        }
                        System.out.println(currentStep +" Val:"+val%4 + " K: " + k + " L: " + l);

                        break;
    
                    case 1:
                        k = notFull(data, 8, 16);
                        if(k != -1)
                            l = isThere(data, 8, k, val);
                        else
                            l = isThere(data, 8, 16, val);
                        //hit
                        if(l != -1){
                            set1 = l;
                            hitcount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set1][1]) + " | Status: Hit |");
                        } else {
                            //miss
                            if (k != -1) {
                                data[k][2] = inputarr.get(currentStep);
                                set1 = k;
                            } else {
                                data[set1][2] = inputarr.get(currentStep);
                            }
                            misscount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set1][1]) + " | Status: Miss |");
                        }
                        System.out.println(currentStep + " Val:"+val%4 + " K: " + k + " L: " + l);
                        break;
                        
                    case 2:
                        k = notFull(data, 16, 24);
                        if(k != -1)
                            l = isThere(data, 16, k, val);
                        else
                            l = isThere(data, 16, 24, val);
                        //hit
                        if(l != -1){
                            set2 = l;
                            hitcount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set2][1]) + " | Status: Hit |");
                        } else {
                            //miss
                            if (k != -1 && k > 8) {
                                data[k][2] = inputarr.get(currentStep);
                                set2 = k;
                            } else {
                                data[set2][2] = inputarr.get(currentStep);
                            }
                            misscount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set2][1]) + " | Status: Miss |");
                        }
                        System.out.println(currentStep + " Val:"+val%4 + " K: " + k + " L: " + l);
                        break;
                        
                    case 3:
                        k = notFull(data, 24, 32);
                        if(k != -1)
                            l = isThere(data, 24, k, val);
                        else
                            l = isThere(data, 24, 32, val);
                        //hit
                        if(l != -1){
                            set3 = l;
                            hitcount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set3][1]) + " | Status: Hit |");
                        } else {
                            //miss
                            if (k != -1) {
                                data[k][2] = inputarr.get(currentStep);
                                set3 = k;
                            } else {
                                data[set3][2] = inputarr.get(currentStep);
                            }
                            misscount++;
                            info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set3][1]) + " | Status: Miss |");
                        }
                        System.out.println(currentStep + " Val:"+val%4 + " K: " + k + " L: " + l);
                        break;
                }
    
                // Update and display the cache state
                displayCacheState(data);
            }
    
            // Increment step for the next iteration
            currentStep++;
            if (currentStep < inputarr.size()) {
                stepTimer.setRepeats(false); // Ensure the Timer only runs once for each step
                stepTimer.start(); // Start the Timer to process the next step after a delay
            } else {
                JOptionPane.showMessageDialog(frame, "Step-by-Step Tracing Completed");
                int n = inputarr.size();
                float hitrate = (float) hitcount/(n);
                float missrate = (float) misscount/(n);
                float average_time = (hitrate) + (missrate*326);
                float total_time = (hitcount) + (misscount*641);

                // new frame again
                // Creating the Frame
                JFrame frame = new JFrame("8-way BSA + MRU");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1500, 800);

                // Simulation Part
                JPanel simuPanel = new JPanel();
                Table cache = new Table();

                JPanel textpanel = new JPanel();
                JLabel memory_accesstime_label = new JLabel("Memory Access Time: " + String.valueOf(n));
                JLabel hit_label = new JLabel("Hit Count: " + String.valueOf(hitcount));
                JLabel miss_label = new JLabel("Miss Time: " + String.valueOf(misscount));
                JLabel hitrate_label = new JLabel("Hit Rate: " + String.valueOf(hitrate*100) + "%");
                JLabel missrate_label = new JLabel("Miss Rate: " + String.valueOf(missrate*100) + "%");
                JLabel avetime_label = new JLabel("Average Time: " + String.valueOf(average_time) + "ns");
                JLabel totaltime_label = new JLabel("Total Time: " + String.valueOf(total_time) + "ns");
                memory_accesstime_label.setFont(new Font("Verdana",1,20));
                hit_label.setFont(new Font("Verdana",1,20));
                miss_label.setFont(new Font("Verdana",1,20));
                hitrate_label.setFont(new Font("Verdana",1,20));
                missrate_label.setFont(new Font("Verdana",1,20));
                avetime_label.setFont(new Font("Verdana",1,20));
                totaltime_label.setFont(new Font("Verdana",1,20));
                textpanel.add(memory_accesstime_label);
                textpanel.add(hit_label);
                textpanel.add(miss_label);
                textpanel.add(hitrate_label);
                textpanel.add(missrate_label);
                textpanel.add(avetime_label);
                textpanel.add(totaltime_label);

                JButton textlog = new JButton("Generate Text Log");
                textpanel.add(textlog);

                textpanel.setLayout(new BoxLayout(textpanel, BoxLayout.Y_AXIS));
                
                simuPanel.add(cache.panel("Cache Memory", data));
                simuPanel.add(textpanel);


                // Adding Components to the frame
                frame.setLayout(new BorderLayout());
                frame.add(BorderLayout.NORTH, simuPanel);
                
                // When simulate is clicked
                textlog.addActionListener((ActionListener) new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //insert code
                        WriteToFile file = new WriteToFile();
                        file.generate(info);
                    }
                });
                frame.setVisible(true);
            }
        }
    }
    
    
    
    
    private void processNextStep() {
        if (currentStep < inputarr.size()) {
            processStep();
        }
    }
    


    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }


    // Other methods (like notFull, isThere, fSnap)...
     public static int notFull(String[][] data, int start, int end){
        for(int i = start; i < end; i++){
            if(data[i][2].equals("")){
                return i;
            }
        }
        return -1;
    }

    public static int isThere(String[][] data, int start, int end, int val){
        for(int i = start; i < end; i++){
            if (!data[i][2].isEmpty()) {
                int var = Integer.valueOf(data[i][2]);
                if(var == val){
                    return i;
                }
            }
        }
        return -1;
    }
    
    
    public static void fSnap(String[][] data, ArrayList<String> inputarr, int n) {
        ArrayList<String> info = new ArrayList<>();
        int hitcount = 0;
        int misscount = 0;
        int set0, set1, set2, set3;
            set0 = 0;
            set1 = 0;
            set2 = 0;
            set3 = 0;
            int k, l;

        for (int i = 0; i < n; i++) {
            int val = Integer.valueOf(inputarr.get(i));
            

            switch (val % 4) {
                case 0:
                    k = notFull(data, 0, 8);
                    if(k != -1)
                        l = isThere(data, 0, k, val);
                    else
                        l = isThere(data, 0, 8, val);
                    //hit
                    if(l != -1){
                        set0 = l;
                        hitcount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set0][1]) + " | Status: Hit |");
                    } else {
                        //miss
                        if (k != -1) {
                            data[k][2] = inputarr.get(i);
                            set0 = k;
                        } else {
                            data[set0][2] = inputarr.get(i);
                        }
                        misscount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set0][1]) + " | Status: Miss |");
                    }
                    System.out.println(i +" Val:"+val%4 + " K: " + k + " L: " + l);
                    break;

                case 1:
                    k = notFull(data, 8, 16);
                    if(k != -1)
                        l = isThere(data, 8, k, val);
                    else
                        l = isThere(data, 8, 16, val);
                    //hit
                    if(l != -1){
                        set1 = l;
                        hitcount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set1][1]) + " | Status: Hit |");
                    } else {
                        //miss
                        if (k != -1) {
                            data[k][2] = inputarr.get(i);
                            set1 = k;
                        } else {
                            data[set1][2] = inputarr.get(i);
                        }
                        misscount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set1][1]) + " | Status: Miss |");
                    }
                    System.out.println(i + " Val:"+val%4 + " K: " + k + " L: " + l);
                    break;
                    
                case 2:
                    k = notFull(data, 16, 24);
                    if(k != -1)
                        l = isThere(data, 16, k, val);
                    else
                        l = isThere(data, 16, 24, val);
                    //hit
                    if(l != -1){
                        set2 = l;
                        hitcount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set2][1]) + " | Status: Hit |");
                    } else {
                        //miss
                        if (k != -1 && k > 8) {
                            data[k][2] = inputarr.get(i);
                            set2 = k;
                        } else {
                            data[set2][2] = inputarr.get(i);
                        }
                        misscount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set2][1]) + " | Status: Miss |");
                    }
                    System.out.println(i + " Val:"+val%4 + " K: " + k + " L: " + l);
                    break;
                    
                case 3:
                    k = notFull(data, 24, 32);
                    if(k != -1)
                        l = isThere(data, 24, k, val);
                    else
                        l = isThere(data, 24, 32, val);
                    //hit
                    if(l != -1){
                        set3 = l;
                        hitcount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set3][1]) + " | Status: Hit |");
                    } else {
                        //miss
                        if (k != -1) {
                            data[k][2] = inputarr.get(i);
                            set3 = k;
                        } else {
                            data[set3][2] = inputarr.get(i);
                        }
                        misscount++;
                        info.add(String.valueOf(val) + " | Set " + String.valueOf(val%4) + " | Block " + String.valueOf(data[set3][1]) + " | Status: Miss |");
                    }
                    System.out.println(i+ " Val:"+val%4 + " K: " + k + " L: " + l);
                    break;
            }

            
        }

        float hitrate = (float) hitcount/(n);
        float missrate = (float) misscount/(n);
        float average_time = (hitrate) + (missrate*326);
        float total_time = (hitcount) + (misscount*641);

        // new frame again
        // Creating the Frame
        JFrame frame = new JFrame("8-way BSA + MRU");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);

        // Simulation Part
        JPanel simuPanel = new JPanel();
        Table cache = new Table();

        JPanel textpanel = new JPanel();
        JLabel memory_accesstime_label = new JLabel("Memory Access Time: " + String.valueOf(n));
        JLabel hit_label = new JLabel("Hit Count: " + String.valueOf(hitcount));
        JLabel miss_label = new JLabel("Miss Time: " + String.valueOf(misscount));
        JLabel hitrate_label = new JLabel("Hit Rate: " + String.valueOf(hitrate*100) + "%");
        JLabel missrate_label = new JLabel("Miss Rate: " + String.valueOf(missrate*100) + "%");
        JLabel avetime_label = new JLabel("Average Time: " + String.valueOf(average_time) + "ns");
        JLabel totaltime_label = new JLabel("Total Time: " + String.valueOf(total_time) + "ns");
        memory_accesstime_label.setFont(new Font("Verdana",1,20));
        hit_label.setFont(new Font("Verdana",1,20));
        miss_label.setFont(new Font("Verdana",1,20));
        hitrate_label.setFont(new Font("Verdana",1,20));
        missrate_label.setFont(new Font("Verdana",1,20));
        avetime_label.setFont(new Font("Verdana",1,20));
        totaltime_label.setFont(new Font("Verdana",1,20));
        textpanel.add(memory_accesstime_label);
        textpanel.add(hit_label);
        textpanel.add(miss_label);
        textpanel.add(hitrate_label);
        textpanel.add(missrate_label);
        textpanel.add(avetime_label);
        textpanel.add(totaltime_label);

        JButton textlog = new JButton("Generate Text Log");
        textpanel.add(textlog);

        textpanel.setLayout(new BoxLayout(textpanel, BoxLayout.Y_AXIS));
        
        simuPanel.add(cache.panel("Cache Memory", data));
        simuPanel.add(textpanel);


        // Adding Components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(BorderLayout.NORTH, simuPanel);
        
        // When simulate is clicked
        textlog.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //insert code
                WriteToFile file = new WriteToFile();
                file.generate(info);
            }
        });
        frame.setVisible(true);
    }
}
