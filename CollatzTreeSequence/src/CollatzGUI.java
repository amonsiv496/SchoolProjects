/**
 * CollatzGUI program implements an application that simply displays
 * and interactive GUI interface that gives the option to generate a 
 * collatz sequence or collatz tree that is going to be saved in a .csv 
 * file. The sequence or tree values are saved in a collection that is a linked list
 * created in Collatz class.
 * The program prompts the user to enter an n value and a limit for the 
 * maximum number of iterations for the sequence or tree. 
 * At the end of the interaction, a label will show a successful or failure run if 
 * the collatz tree includes the n value or if the collatz sequence reaches 1 from n.
 */

import modeImplementaion.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class handles the frame and panels that are generated. It also has
 * the main function that handles the whole application
 */
public class CollatzGUI extends JFrame implements ActionListener, ItemListener{
	
	private JComboBox chainingOptionsBox;
	private JTextField nInput;
	private JSlider limitInput;
	private JLabel status;
	
	// GUI components created with action listeners 
	CollatzGUI(){
		//Layout for choosing chaining mode
		JPanel choice = new JPanel();
		choice.setLayout(new GridLayout(0,2,25,0));
		
		// Choose mode
		choice.add(new JLabel("Choose Chaining Mode!"));
		chainingOptionsBox = new JComboBox();
		chainingOptionsBox.addItem("Forward Chaining");
		chainingOptionsBox.addItem("Backward Chaining");
		choice.add(chainingOptionsBox);
		add(choice);
		
		//panel created for user input
		JPanel input = new JPanel();
		input.setLayout(new GridLayout(5,2,25,5));
		
		input.add(new JLabel("Enter the n value"));
		nInput = new JTextField();
		input.add(nInput);
		
		input.add(new JLabel("Enter the limit"));
		
		limitInput = new JSlider();
		limitInput.setValue(0);
		limitInput.setValue(10);
		limitInput.setValue(20);
		limitInput.setValue(30);
		limitInput.setValue(40);
		limitInput.setValue(50);
		limitInput.setValue(60);
		limitInput.setValue(70);
		limitInput.setValue(80);
		limitInput.setValue(90);
		limitInput.setValue(100);
		limitInput.setMajorTickSpacing(20);
		limitInput.setMinorTickSpacing(5);
		limitInput.setPaintTicks(true);
		limitInput.setPaintLabels(true);
		
		input.add(limitInput);
		
		//execute button with action listener to 
		JButton executeButton = new JButton("Execute!");
		input.add(executeButton);
		executeButton.addActionListener(this);
		
		input.add(new JLabel(""));
		input.add(new JLabel("Status"));
		status = new JLabel("Not executed");
		input.add(status);
		
		//exit button with action listener
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("Done!");
				System.exit(0);
			}
		});
		
		input.add(exit);
		add(input);
		
		//split the frame in two
		add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, choice,input));
		
	}
	
	// main
	public static void main(String args[]){
		
		// 600 x 300 frame created
		CollatzGUI frame = new CollatzGUI(); // create frame
		frame.pack(); // pack frame
		frame.setSize(600,300);
		frame.setTitle("Collatz Sequence/Tree");
		frame.setLocationRelativeTo(null); // center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int chainingMode = (int) chainingOptionsBox.getSelectedIndex();
		int nValueInt = Integer.parseInt(nInput.getText());
		int limitValueInt = limitInput.getValue();
		
		// forward chaining mode
		if (chainingMode == 0){
			if (Collatz.forwardChaining(nValueInt, limitValueInt) == true){
				status.setText("Sucess!");
				status.setFont(new Font("Serif", Font.PLAIN, 20));
				status.setForeground(Color.green);
			}
			else {
				status.setText("Failure!");
				status.setFont(new Font("Serif", Font.PLAIN, 20));
				status.setForeground(Color.RED);
			}
		}
		else {// backward chaining mode
			if (Collatz.backwardChaining(nValueInt, limitValueInt) == true){
				status.setText("Sucess!");
				status.setFont(new Font("Serif", Font.PLAIN, 20));
				status.setForeground(Color.green);
			}
			else {
				status.setText("Failure!");
				status.setFont(new Font("Serif", Font.PLAIN, 20));
				status.setForeground(Color.RED);
			}
		}
	}// end of actionPerformed
}