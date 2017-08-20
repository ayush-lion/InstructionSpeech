/**
 * 
 */
package com.app.instruction.main;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	private JCheckBox doWeNeedFrame;
	private JCheckBox doWeNeedFingers;
	private JTextField attrTxt;
	private JButton loadAbacus;
	private JButton showAbacus;

	public TestInstructionComponent() {
		try {
			this.getContentPane().setLayout(null);
			this.setResizable(false);
			this.setTitle("Abacus. Lets start learning mind math !!!");
			this.setBounds(100, 100, 1050, 690);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setBackground(Color.WHITE);

			/* Create Instruction Panel */
			
			panel = new InstructionPanel();
			panel.setBounds(10, 50, this.getWidth() - 20, this.getHeight() - 180);
		


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
