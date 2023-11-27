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

        // Creating the MenuBar and adding components
        //JMenuBar mb = new JMenuBar();

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
        frame.add(BorderLayout.NORTH, simuPanel);
        
        // When simulate is clicked
        send.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.valueOf(tf.getText());
                String inputValue = tf1.getText().toString();
                String[] testcase = inputValue.split("\\s");
                //System.out.println(testcase.length);
                //System.out.println("n: " + n);
                //System.out.println("User input: " + inputValue);
                //System.out.println(testcase.length);
                ArrayList<String> inputarr = new ArrayList<String>( Arrays.asList(testcase));
                if (testcase.length < n*2){ //if input are less than
                    for (int i = testcase.length; i < n*2; i++) {
                        inputarr.add(null);
                    }
                }
                //for (int i = 0; i < n*2; i++) {
                //    System.out.println(inputarr.get(i));
                //} 
                if(j1.isSelected()){
                    //Step-by-Step Tracing

                } else if(j2.isSelected()){
                    //Final Snapshot
                    fSnap(default_data, inputarr, n);
                }
            }
        });
        frame.setVisible(true);
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
            int var = Integer.parseInt(data[i][2]);
            if(var == val){
                return i;
            }
        }
        return -1;
    }
    
    public static void fSnap(String[][] data, ArrayList<String> inputarr, int n) {
        int set0, set1, set2, set3;
            set0 = 0;
            set1 = 0;
            set2 = 0;
            set3 = 0;
            int k, l;

        for (int i = 0; i < n*2; i++) {
            int val = Integer.valueOf(inputarr.get(i));
            

            switch (val % 4) {
                case 0:
                    k = notFull(data, 0, 8);
                    l = isThere(data, 0, k, val);
                    //hit
                    if(l != -1){
                        set0 = l;
                    } else {
                        //miss
                        if (k != -1) {
                            data[k][2] = inputarr.get(i);
                            set0 = k;
                        } else {
                            data[set0][2] = inputarr.get(i);
                        }
                    }
                    System.out.println("Val:"+val%4 + " K: " + k + " L: " + l);
                    break;

                case 1:
                    k = notFull(data, 8, 16);
                    l = isThere(data, 8, k, val);
                    //hit
                    if(l != -1){
                        set1 = l;
                    } else {
                        //miss
                        if (k != -1) {
                            data[k][2] = inputarr.get(i);
                            set1 = k;
                        } else {
                            data[set1][2] = inputarr.get(i);
                        }
                    }
                    System.out.println("Val:"+val%4 + " K: " + k + " L: " + l);
                    break;
                    
                case 2:
                    k = notFull(data, 16, 24);
                    l = isThere(data, 16, k, val);
                    //hit
                    if(l != -1){
                        set2 = l;
                    } else {
                        //miss
                        if (k != -1 && k > 8) {
                            data[k][2] = inputarr.get(i);
                            set2 = k;
                        } else {
                            data[set2][2] = inputarr.get(i);
                        }
                    }
                    System.out.println("Val:"+val%4 + " K: " + k + " L: " + l);
                    break;
                    
                case 3:
                    k = notFull(data, 24, 32);
                    l = isThere(data, 24, k, val);
                    //hit
                    if(l != -1){
                        set3 = l;
                    } else {
                        //miss
                        if (k != -1) {
                            data[k][2] = inputarr.get(i);
                            set3 = k;
                        } else {
                            data[set3][2] = inputarr.get(i);
                        }
                    }
                    System.out.println("Val:"+val%4 + " K: " + k + " L: " + l);
                    break;
            }

            
        }

        // new frame again
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
        
        simuPanel.add(cache.panel("Cache Memory", data));

        // Adding Components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(BorderLayout.SOUTH, outerpanel);
        frame.add(BorderLayout.NORTH, simuPanel);
        
        // When simulate is clicked
        send.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = Integer.valueOf(tf.getText());
                String inputValue = tf1.getText().toString();
                String[] testcase = inputValue.split("\\s");
                ArrayList<String> inputarr = new ArrayList<String>( Arrays.asList(testcase));
                if (testcase.length < n*2){ //if input are less than
                    for (int i = testcase.length; i < n*2; i++) {
                        inputarr.add(null);
                    }
                }
                
                if(j1.isSelected()){
                    //Step-by-Step Tracing

                } else if(j2.isSelected()){
                    //Final Snapshot
                    fSnap(default_data, inputarr, n);
                }
            }
        });
        frame.setVisible(true);
    }

}
