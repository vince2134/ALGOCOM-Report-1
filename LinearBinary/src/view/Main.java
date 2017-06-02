package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by avggo on 6/2/2017.
 */
public class Main {

    private JComboBox inputSizeComboBox;
    private JLabel inputSizeLabel;
    private JLabel searchTypeLabel;
    private JComboBox searchTypeComboBox;
    private JComboBox caseComboBox;
    private JLabel executionTimeLabel;
    private JTextField executionTimesTextField;
    private JButton runButton;
    private JLabel bestTimeLabel;
    private JLabel bestLinearTimeValueLabel;
    private JLabel averageTimeLabel;
    private JLabel averageLinearTimeValueLabel;
    private JLabel worstTimeLabel;
    private JPanel panelMain;
    private JLabel worstLinearTimeValue;
    private JLabel bestBinaryTimeValueLabel;
    private JLabel averageBinaryTimeValueLabel;
    private JLabel worstBinaryTimeValue;
    Integer searchValue;
    Long bestTime, averageTime, worstTime;


    public Main() {
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, inputSizeComboBox.getSelectedItem().toString());
                run();
            }
        });
    }

    int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public void run(){
        bestTime = null;
        averageTime = null;
        worstTime = null;

        Boolean found = false;
        ArrayList<Integer> currentInput = new ArrayList<>();

        //INITIALIZE INPUT ARRAY BASED ON INPUT SIZE
        for(int i = 1; i <= Integer.parseInt(inputSizeComboBox.getSelectedItem().toString()); i++){
            currentInput.add(i);
        }

        for(int j = 1; j <= Integer.parseInt(executionTimesTextField.getText().toString()); j++) {
            found = false;


                if (caseComboBox.getSelectedItem().toString().equals("BEST CASE")) {
                    if(searchTypeComboBox.getSelectedItem().toString().equals("Linear"))
                        searchValue = 1;
                    else
                        searchValue = currentInput.get((currentInput.size() - 1) / 2);
                }
                else if (caseComboBox.getSelectedItem().toString().equals("AVERAGE CASE")) {
                    if (j < Integer.parseInt(executionTimesTextField.getText().toString()) / 2)
                        searchValue = randomWithRange(1, currentInput.size());
                    else
                        searchValue = -1;
                } else
                    searchValue = -1;

                long startTime, endTime;

                if(searchTypeComboBox.getSelectedItem().toString().equals("Linear")) {
                    startTime = System.nanoTime();
                    for (int i = 0; !found && i < currentInput.size(); i++) {
                        if (currentInput.get(i) == searchValue)
                            found = true;
                    }
                    endTime = System.nanoTime();
                }
                else{
                    startTime = System.nanoTime();

                    int low = 0, high = currentInput.size() -1;

                    while(low <= high && !found){
                        int mid = low + (high - low) / 2;
                        if(searchValue < currentInput.get(mid))
                            high = mid - 1;
                        else if(searchValue > currentInput.get(mid))
                            low = mid + 1;
                        else
                            found = true;
                    }

                    endTime = System.nanoTime();
                }

                long duration = endTime - startTime;

                if(bestTime == null)
                    bestTime = duration;
                else if(duration < bestTime && duration != 0)
                    bestTime = duration;

                if(averageTime == null)
                    averageTime = duration;
                else {
                    averageTime = averageTime + duration;
                }

                if(worstTime == null)
                    worstTime = duration;
                else if(duration > worstTime)
                    worstTime = duration;
        }

        averageTime = averageTime / Integer.parseInt(executionTimesTextField.getText().toString());

        if(searchTypeComboBox.getSelectedItem().toString().equals("Linear")) {
            bestLinearTimeValueLabel.setText(bestTime.toString());
            averageLinearTimeValueLabel.setText(averageTime.toString());
            worstLinearTimeValue.setText(worstTime.toString());
        }
        else{
            bestBinaryTimeValueLabel.setText(bestTime.toString());
            averageBinaryTimeValueLabel.setText(averageTime.toString());
            worstBinaryTimeValue.setText(worstTime.toString());
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("ALGOCOM Report 1");
        jFrame.setContentPane(new Main().panelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
