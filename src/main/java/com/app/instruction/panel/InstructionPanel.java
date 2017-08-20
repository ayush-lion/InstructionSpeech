/**
 * 
 */
package com.app.instruction.panel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.app.instruction.Instruction;
import com.app.instruction.panel.exception.InstructionException;

/**
 * Class is responsible to create and handle Abacus Operations
 * 
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 04-Aug-2017
 */
public class InstructionPanel extends JPanel {
	
	private static final long serialVersionUID = -2312019179090620771L;
	private Instruction abacus;
	private boolean doWeNeedToHighlightFrame = Boolean.FALSE;
	private boolean doWeNeedToHighlightRods = Boolean.FALSE;
	private boolean doWeNeedToHighlightSpecificRods = Boolean.FALSE;
	private boolean doWeNeedToHighlightBeads = Boolean.FALSE;
	private boolean doWeNeedToHighlightLowerBeads = Boolean.FALSE;
	private boolean doWeNeedToHighlightUpperBeads = Boolean.FALSE;
	private boolean doWeNeedToHighlightSpecificBeads = Boolean.FALSE;
	private boolean doWeNeedToHighlightDivider = Boolean.FALSE;
	
	private String abacusAttributesFileName = null;
	private int rodNumber;
	private int beadNumber;
	
	public InstructionPanel(String abacusAttributesFileName)  {
		this.abacusAttributesFileName = abacusAttributesFileName;
	}
	
	public InstructionPanel() {}
	
	/**
	 * Method is responsible to initialize Instruction
	 */
	
	public void initializeInstruction() throws InstructionException {
		if(abacusAttributesFileName != null) {
			abacus = new Instruction(abacusAttributesFileName, this);
		} else {
			abacus = new Instruction(this);
		}
		this.repaint();
	}
	
	
	/**
	 * Method is responsible to hightlight Frame
	 */
	public void highlightFrame() {
		switchBoolean("doWeNeedToHighlightFrame");
		this.repaint();
	}
	
	/**
	 * Method is responsible to hightlight Frame
	 */
	private void highlightFrame(Graphics g) {
		if(getAbacus().canWeDisplayFrame()) {
			getAbacus().getFrame().highlight(g);
		}
	}
	
	/**
	 * Method is responsible to tutor rods
	 */
	public void highlighttutors() {
		switchBoolean("doWeNeedToHighlightRods");
		this.repaint();
	}
	
	/**
	 * Method is responsible to hightlight Rods
	 */
	private void highlightRods(Graphics g) {
		for(int i = 0; i < getAbacus().getNumOfRods(); i++) {
			getAbacus().getRods()[i].highlight(g);
		}
	}
	
	/**
	 * Method is responsible to highlight Specific rod
	 */
	public void highlightSpecificRod(int rodNum) {
		switchBoolean("doWeNeedToHighlightSpecificRods");
		this.rodNumber = rodNum;
		this.repaint();
	}
	
	/**
	 * Method is responsible to hightlight Specific Rod
	 */
	private void highlightSpecificRod(Graphics g) {
		if(rodNumber < 1 || rodNumber > getAbacus().getNumOfRods()) {
			return;
		}
		getAbacus().getRods()[getAbacus().getNumOfRods() - rodNumber].highlight(g);
	}
	
	/**
	 * Method is responsible to highlight Lower Beads
	 */
	public void highlightLowerBeads() {
		switchBoolean("doWeNeedToHighlightLowerBeads");
		this.repaint();
	}
	
	
	/**
	 * Method is responsible to highlight Upper Beads
	 */
	public void highlightUpperBeads() {
		switchBoolean("doWeNeedToHighlightUpperBeads");
		this.repaint();
	}
	
	/**
	 * Method is responsible to highlight rods
	 */
	public void highlightBeads() {
		switchBoolean("doWeNeedToHighlightBeads");
		this.repaint();
	}
	
	/**
	 * Method is responsible to highlight Specific Bead
	 */
	public void highlightSpecificBead(int rodNum, int beadNum) {
		switchBoolean("doWeNeedToHighlightSpecificBeads");
		this.rodNumber = rodNum;
		this.beadNumber = beadNum;
		this.repaint();
	}
	
	/**
	 * Method is responsible to highlight rods
	 */
	public void highlightDivider() {
		switchBoolean("doWeNeedToHighlightDivider");
		this.repaint();
	}
	
	/**
	 * Method is responsible to highlight rods
	 */
	public void hideFrame(boolean isOnorOff) {
		resetAllBooleans();
		getAbacus().setDoWeNeedToDisplayFrame(isOnorOff);
		this.repaint();
	}
	
	
	/**
	 * Method is responsible to highlight rods
	 */
	public void showAbacus() {
		resetAllBooleans();
		//resetAbacus();
		this.repaint();
	}
	
	/**
	 * Switch boolean to TRUE
	 */
	private void switchBoolean(String booleanName) {
		resetAllBooleans();
		switch (booleanName) {
			case "doWeNeedToHighlightFrame" :
				doWeNeedToHighlightFrame = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightRods" :
				doWeNeedToHighlightRods = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightSpecificRods" :
				doWeNeedToHighlightSpecificRods = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightBeads" :
				doWeNeedToHighlightBeads = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightLowerBeads" :
				doWeNeedToHighlightLowerBeads = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightUpperBeads" :
				doWeNeedToHighlightUpperBeads = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightSpecificBeads" :
				doWeNeedToHighlightSpecificBeads = Boolean.TRUE;
				break;
			case "doWeNeedToHighlightDivider" :
				doWeNeedToHighlightDivider = Boolean.TRUE;
				break;
		}
	}
	
	/**
	 * Method is responsible to reset all booleans
	 */
	public void resetAllBooleans() {
		doWeNeedToHighlightFrame = Boolean.FALSE;
		doWeNeedToHighlightRods = Boolean.FALSE;
		doWeNeedToHighlightBeads = Boolean.FALSE;
		doWeNeedToHighlightLowerBeads = Boolean.FALSE;
		doWeNeedToHighlightUpperBeads = Boolean.FALSE;
		doWeNeedToHighlightSpecificBeads = Boolean.FALSE;
		doWeNeedToHighlightSpecificRods = Boolean.FALSE;
		doWeNeedToHighlightDivider = Boolean.FALSE;
	}
	
	
	@Override
	public void paint(Graphics g) {
		// Draw complete Abacus
		if(getAbacus() != null)
			getAbacus().drawAbacus(g);
		
		
		// High light Frame
		if(doWeNeedToHighlightFrame) {
			highlightFrame(g);
		} 
		
		// High light Rods
		if(doWeNeedToHighlightRods) {
			highlightRods(g);
		}
		
		// High light Specific Rod
		if(doWeNeedToHighlightSpecificRods) {
			highlightSpecificRod(g);
		}
	}

	/**
	 * @return the abacus
	 */
	public Instruction getAbacus() {
		return abacus;
	}
	
	public InstructionPanel getCurrentInstance() {
		return this;
	}
	
	/**
	 * @param abacusAttributesFileName the abacusAttributesFileName to set
	 */
	public void setAbacusAttributesFileName(String abacusAttributesFileName) {
		this.abacusAttributesFileName = abacusAttributesFileName;
	}
}
