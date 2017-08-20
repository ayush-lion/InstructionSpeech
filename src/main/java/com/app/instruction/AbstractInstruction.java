package com.app.instruction;

import com.app.instruction.panel.exception.InstructionException;
import com.app.instruction.utils.InstructionLogger;

/**
 * @author prashant.joshi
 *
 */
public abstract class AbstractInstruction {

	/* Holds instruction Customized Integer Parameters */
	
	protected Integer numOftutors;
	
	/* Holds Custom Classes */
	
	protected InstructionLogger logger;
	protected Tutor[] tutor;
	protected Student[] student;
	protected Frame frame;

		
	public Student[] getStudent() {
		return student;
	}

	public void setStudent(Student[] student) {
		this.student = student;
	}

	/**
	 * @return the numOfRods
	 */
	
	public Integer getNumOfRods() {
		return numOftutors;
	}
	
	/**
	 * @param numOfRods the numOfRods to set
	 */
	public void setNumOfRods(Integer numOfRods) {
		this.numOftutors = numOfRods;
	}
	/**
	 * @return the logger
	 */
	public InstructionLogger getLogger() {
		return logger;
	}
	/**
	 * @param logger the logger to set
	 */
	public void setLogger(InstructionLogger logger) {
		this.logger = logger;
	}

	/**
	 * @return the rods
	 */
	public Tutor[] getRods() {
		return tutor;
	}
	/**
	 * @param rods the rods to set
	 */
	public void setRods(Tutor[] rods) {
		this.tutor = rods;
	}
	/**
	 * @return the frame
	 */
	public Frame getFrame() {
		return frame;
	}
	/**
	 * @param frame the frame to set
	 */
	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	/**
	 * @return the beam
	 */
}
