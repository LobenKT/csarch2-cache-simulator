import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class App {
    static String[][] default_data = {
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
    public static void main(String[] args) {
        // Creating the Frame
        JFrame frame = new JFrame("8-way BSA + MRU");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);

        // Creating the panel at bottom and adding components
        JPanel outerpanel = new JPanel(); 
        JPanel panel = new JPanel(); 
        JPanel bpanel = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("Number of Memory Blocks:");
        JTextField tf = new JTextField(5);
        tf.setText("5");

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

        panel.add(label);
        panel.add(tf);
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

        outerpanel.add(panel);
        outerpanel.add(bpanel);
        outerpanel.add(panel2);
        outerpanel.setLayout(new BoxLayout(outerpanel, BoxLayout.Y_AXIS));
        outerpanel.setBackground(Color.PINK);

        // Simulation Part
        JPanel simuPanel = new JPanel();
        Table cache = new Table();
        
        simuPanel.add(cache.panel("Cache Memory", default_data));

        // Adding Components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(BorderLayout.SOUTH, outerpanel);
        frame.add(BorderLayout.CENTER, simuPanel);

        JButton resetButton = new JButton("Reset");
        panel2.add(resetButton); // Add the reset button to the panel

        // ActionListener for the reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetCacheData();
                simuPanel.removeAll();
                simuPanel.add(cache.panel("Cache Memory", default_data));
                simuPanel.revalidate();
                simuPanel.repaint();
                // Clearing the input fields
                tf.setText(""); // Clears the "Number of Memory Blocks" field
                tf1.setText(""); // Clears the "Inputs" field
            }
        });
        
        // When simulate is clicked
        send.addActionListener((ActionListener) new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String numBlocksStr = tf.getText().trim();
                String inputsStr = tf1.getText().trim();
        
                // Validate the number of memory blocks field
                if (numBlocksStr.isEmpty() || !isInteger(numBlocksStr)) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid integer for the number of memory blocks.");
                    return; // Stop further processing
                }
        
                int numBlocks = Integer.parseInt(numBlocksStr);
        
                // Validate the inputs field
                if (inputsStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Input field cannot be empty.");
                    return; // Stop further processing
                }
        
                String[] testcase = inputsStr.split("\\s+");
                ArrayList<String> inputarr = new ArrayList<>(Arrays.asList(testcase));
        
                // Additional check to ensure enough inputs are provided
                if (testcase.length < numBlocks) {
                    JOptionPane.showMessageDialog(frame, "The number of inputs provided is less than the specified number of memory blocks.");
                    return; // Stop further processing
                }

                int n = Integer.valueOf(tf.getText());
                String inputValue = tf1.getText().toString();
            //    String[] testcase = inputValue.split("\\s");

            //    ArrayList<String> inputarr = new ArrayList<String>( Arrays.asList(testcase));
                if (testcase.length < n){ //if input are less than
                    for (int i = testcase.length; i < n; i++) {
                        inputarr.add(null);
                    }
                }
                
                if(j1.isSelected()){
                    //Step by Step
                    displayEachStep(default_data, inputarr, n);
                } else if(j2.isSelected()){
                    //Final Snapshot
                    fSnap(default_data, inputarr, n);
                }
            }
        });
        frame.setVisible(true);
    }
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

    private static void resetCacheData() {
        for (int i = 0; i < default_data.length; i++) {
            default_data[i][2] = ""; // Reset the value in the cache
            // If you are using an MRU marker, reset it as well
            // default_data[i][3] = "0"; // Uncomment if MRU marker is used
        }
    }

    public static void displayEachStep(String[][] data, ArrayList<String> inputarr, int n) {
        // Initialize frame and panels
        JFrame frame = new JFrame("8-way BSA + MRU Step-by-Step");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);
        JPanel simuPanel = new JPanel();
        Table cache = new Table();
        // Loop for cache simulation
        for (int i = 0; i < n; i++) {
            int val = Integer.parseInt(inputarr.get(i));
            int k, l;
            // Cache logic (similar to fSnap)
            switch (val % 4) {
                case 0:
                    k = notFull(data, 0, 8);
                    l = (k != -1) ? isThere(data, 0, k, val) : isThere(data, 0, 8, val);
                    processCacheHitMiss(data, k, l, val, 0);
                    break;
                case 1:
                    k = notFull(data, 8, 16);
                    l = (k != -1) ? isThere(data, 8, k, val) : isThere(data, 8, 16, val);
                    processCacheHitMiss(data, k, l, val, 1);
                    break;
                case 2:
                    k = notFull(data, 16, 24);
                    l = (k != -1) ? isThere(data, 16, k, val) : isThere(data, 16, 24, val);
                    processCacheHitMiss(data, k, l, val, 2);
                    break;
                case 3:
                    k = notFull(data, 24, 32);
                    l = (k != -1) ? isThere(data, 24, k, val) : isThere(data, 24, 32, val);
                    processCacheHitMiss(data, k, l, val, 3);
                    break;
            }
            // Update display after each operation
            updateDisplay(simuPanel, cache, data);
            // Optional delay
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Final frame setup
        frame.setLayout(new BorderLayout());
        frame.add(BorderLayout.CENTER, simuPanel);
        frame.setVisible(true);
    }
    private static void processCacheHitMiss(String[][] data, int k, int l, int val, int setNumber) {
        if (l != -1) {
            // Hit: Update MRU
            updateMRU(data, setNumber, l);
        } else {
            // Miss: Place in the next available or MRU position
            int targetIndex = (k != -1) ? k : findMRU(data, setNumber);
            data[targetIndex][2] = Integer.toString(val);
            updateMRU(data, setNumber, targetIndex);
        }
    }
    private static void updateMRU(String[][] data, int setNumber, int mruIndex) {
    // Assuming 'data' array has an MRU marker in a specific column, e.g., column 3
    int start = setNumber * 8; // Assuming each set has 8 blocks
    int end = start + 8;

    // Clear the MRU marker from all blocks in the set
    for (int i = start; i < end; i++) {
        data[i][3] = "0"; // Reset MRU marker
    }

    // Set the MRU marker for the current block
    data[mruIndex][3] = "1"; // Mark this block as MRU
}

private static int findMRU(String[][] data, int setNumber) {
    // Find the block marked as MRU in the specified set
    int start = setNumber * 8; // Assuming each set has 8 blocks
    int end = start + 8;

    for (int i = start; i < end; i++) {
        if ("1".equals(data[i][3])) {
            return i; // Return the index of the MRU block
        }
    }

    // If no block is marked as MRU, return the first block of the set as a default
    return start;
}

    private static void updateDisplay(JPanel simuPanel, Table cache, String[][] data) {
        simuPanel.removeAll();
        simuPanel.add(cache.panel("Cache Memory", data));
        simuPanel.revalidate();
        simuPanel.repaint();
    }


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
            int var = Integer.valueOf(data[i][2]);
            if(var == val){
                return i;
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
