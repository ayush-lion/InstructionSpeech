/**
 * 
 */
package com.app.instruction.main;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.app.instruction.panel.InstructionPanel;
import com.app.instruction.panel.exception.InstructionException;

/**
 * @author prashant.joshi
 *
 */
public class TestInstructionComponent extends JFrame {

	private static final long serialVersionUID = 1L;

	// panels
	private InstructionPanel panel;

	//Instruction Panel

	public TestInstructionComponent() {
		try {
			this.getContentPane().setLayout(null);
			this.setResizable(false);
			this.setTitle("Abacus. Lets start learning mind math !!!");
			this.setBounds(10, 10, 1250, 650);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBackground(Color.WHITE);

			/* Create Instruction Panel */
			
			panel = new InstructionPanel();
			panel.setBounds(10, 20, this.getWidth() - 20, this.getHeight() - 180);
		


			/* Add Components to Frame */
			this.getContentPane().add(panel);

			this.setVisible(true);

			// panel.initializeInstructions();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showPanel() {
		this.setVisible(true);
		try {
			panel.initializeInstruction();
		} catch (InstructionException e) {
			e.printStackTrace();
		}
	}
	/*
	 * @param args
	 */
	public static void main(String[] args) {
		TestInstructionComponent ob = new TestInstructionComponent();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ob.showPanel();
			}
		});
	}
}
