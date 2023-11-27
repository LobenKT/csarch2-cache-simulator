import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        // Creating the Frame
        JFrame frame = new JFrame("8-way BSA + MRU");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1000);

        // Creating the MenuBar and adding components
        //JMenuBar mb = new JMenuBar();

        // Creating the panel at bottom and adding components
        JPanel outerpanel = new JPanel(); 
        JPanel panel = new JPanel(); 
        JPanel bpanel = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel label = new JLabel("Number of Memory Blocks:");
        JTextField tf = new JTextField(5);
        JLabel label2 = new JLabel("Option:");
        JRadioButton j1 = new JRadioButton("Step-by-Step Tracing");
        JRadioButton j2 = new JRadioButton("Final Memory");
        j1.setOpaque(false);
        j2.setOpaque(false);
        ButtonGroup rd = new ButtonGroup();
        rd.add(j1);
        rd.add(j2);
        JButton send = new JButton("Simulate");

        panel.add(label);
        panel.add(tf);
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
        //JTextArea ta = new JTextArea();

        // Adding Components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(BorderLayout.SOUTH, outerpanel);
        
        frame.setVisible(true);
    }
}
