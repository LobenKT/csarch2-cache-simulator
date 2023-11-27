import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;

public class App {
    private JFrame frame;
    private JTextField memoryBlocksTextField;
    private JTextField inputSequenceTextField;
    private JRadioButton stepByStepRadioButton;
    private JRadioButton finalMemoryRadioButton;
    private JButton simulateButton;
    private Table cacheTable; // Assuming Table class for displaying cache
    private CacheSimulation cacheSimulation;

    public App() {
        initializeUI();
        cacheSimulation = new CacheSimulation();
    }

    private void initializeUI() {
        frame = new JFrame("Cache Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Setup layout and components
        frame.setLayout(new BorderLayout());

        // Top Panel for Inputs
        JPanel inputPanel = new JPanel();
        memoryBlocksTextField = new JTextField(5);
        inputSequenceTextField = new JTextField(20);
        inputPanel.add(new JLabel("Number of Memory Blocks:"));
        inputPanel.add(memoryBlocksTextField);
        inputPanel.add(new JLabel("Input Sequence:"));
        inputPanel.add(inputSequenceTextField);
        
        // Radio buttons for options
        stepByStepRadioButton = new JRadioButton("Step-by-Step Tracing");
        finalMemoryRadioButton = new JRadioButton("Final Memory Snapshot", true);
        ButtonGroup optionGroup = new ButtonGroup();
        optionGroup.add(stepByStepRadioButton);
        optionGroup.add(finalMemoryRadioButton);
        inputPanel.add(stepByStepRadioButton);
        inputPanel.add(finalMemoryRadioButton);

        // Simulate button
        simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runSimulation();
            }
        });
        inputPanel.add(simulateButton);

        // Table for displaying cache
        cacheTable = new Table();
        JPanel tablePanel = cacheTable.panel("Cache Memory", new String[32][3]); // Placeholder data

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void runSimulation() {
        try {
            int numMemoryBlocks = Integer.parseInt(memoryBlocksTextField.getText());
            ArrayList<String> inputSequence = new ArrayList<>(Arrays.asList(inputSequenceTextField.getText().split("\\s+")));
            String[][] simulationResults = cacheSimulation.simulate(numMemoryBlocks, inputSequence, finalMemoryRadioButton.isSelected());

            cacheTable.updateTable(simulationResults); // Update the table with simulation results
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please ensure all inputs are numeric.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error during simulation: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new App();
            }
        });
    }
}
