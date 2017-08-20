/**
 * 
 */
package com.app.instruction;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.app.instruction.panel.InstructionPanel;
import com.app.instruction.panel.exception.InstructionException;
import com.app.instruction.utils.InstructionAttributesLoader;
import com.app.instruction.utils.InstructionLogger;

/**
 * @author prashant.joshi (198joshi@gmail.com)
 * @version 05-AUG-2017
 */
public class Instruction extends AbstractInstruction {

	private static final Integer DEFAULT_NUMBER_OF_RODS = 4;
	private static Integer DEFAULT_FRAME_VERTICAL_WIDTH = 10;
	private static Integer DEFAULT_FRAME_VERTICAL_HEIGHT = 10;
	private static Integer DEFAULT_ROD_WIDTH = 10;
	private InstructionPanel panel;
	private boolean isPropertiesProvided;

	/* Holds boolean values */
	private boolean doWeNeedToDisplayFrame;

	/**
	 * Default Constructor
	 */

	public Instruction(InstructionPanel panel) throws InstructionException {
		this.panel = panel;
		this.isPropertiesProvided = Boolean.FALSE;
		settingUpAbacusAttributes(null);
	}

	public Instruction(String attributesFileName, InstructionPanel panel) throws InstructionException {
		this.panel = panel;
		this.isPropertiesProvided = Boolean.TRUE;
		settingUpAbacusAttributes(attributesFileName);
	}

	/**
	 * Method is responsible to highlight frame
	 */

	public void highLightFrame(Graphics g) {
		if (canWeDisplayFrame())
			frame.highlight(g);
	}

	/**
	 * Method is responsible to highlight tutor
	 */

	public void highLightTutor(Graphics g) {
		for (Tutor rod : tutor) {
			rod.highlight(g);
		}
	}

	/**
	 * Method is responsible to highlight Rod
	 */

	public void highLightRod(int rodNum, Graphics g) throws InstructionException {
		if (rodNum > numOftutors || rodNum < 1) {
			throw (new InstructionException(" Rod Number should not be increased by ==> " + numOftutors));
		}
		tutor[rodNum - 1].highlight(g);
	}

	/**
	 * Method is responsible to highlight Rod
	 */
	public void highLightBead(int rodNum, int beadNum, Graphics g) throws InstructionException {
		if ((rodNum > numOftutors || rodNum < 1)) {
			throw (new InstructionException(" Rod Number should in between 1 to " + numOftutors));
		}

		if ((beadNum > 5 || beadNum < 1)) {
			throw (new InstructionException(" Bead Number should in between 1 to 5"));
		}
	}

	/**
	 * Method is responsible to draw
	 */
	public void drawAbacus(Graphics g) {
		// Draw Frame
		if (canWeDisplayFrame())
			frame.drawFrame(g);

		// Draw tutor
		for (Tutor rod : tutor) {
			rod.drawRod(g);
		}

		// Draw student
		for (int i = 0; i < numOftutors; i++) {

			student[i].drawStudent(g);
		}
		// Draw SpeechBoxTutor
		for (int i = 0; i < numOftutors; i++) {
			speechboxtutor[i].drawSpeech(g);
		}
		// Draw SpeechBoxStudent
		for (int i = 0; i < numOftutors; i++) {
			speechBoxStudents[i].drawSpeech(g);
		}
				
	}

	/**
	 * Method is responsible to setting up instruction Attributes
	 */
	private void settingUpAbacusAttributes(String attributesFileName) throws InstructionException {
		Map<String, String> attributes = null;
		if (attributesFileName == null) {
			attributes = InstructionAttributesLoader.getAllPropertiesFromResource("instruction.properties");
		} else {
			attributes = InstructionAttributesLoader.getAllPropertiesFromFile(attributesFileName);
		}

		if (attributes == null || attributes.isEmpty()) {
			throw (new InstructionException("Not able to initialize Abacus. Please provide Abacus attributes...."));
		}

		/* Setting up logger */
		
		logger = new InstructionLogger(isTrueOrFalse(attributes, "loggingDebug"),
				isTrueOrFalse(attributes, "loogingWarning"), isTrueOrFalse(attributes, "doWeNeedToDisplayAbacusFrame"));

		/* Print instruction attributes file */
		
		logger.logInfo("attributesFileName => " + attributesFileName);

		/* Print instruction Attributes */
		
		logger.logDebug(attributes);

		/* Setting up instruction Attributes */
		
		numOftutors = attributes.get("numOfRods") != null ? Integer.valueOf(attributes.get("numOfRods"))
				: Instruction.DEFAULT_NUMBER_OF_RODS;
		doWeNeedToDisplayFrame = isTrueOrFalse(attributes, "doWeNeedToDisplayAbacusFrame");

		/* Initialize instruction */
		
		try {
			initializeFrame(attributes);
			initializeRods(attributes);
			initializeStudents(attributes);
			initializeSpeechBoxTutor(attributes);
			initializeSpeechBoxStudent(attributes);
		} catch (IOException e) {
			e.printStackTrace();
			throw (new InstructionException("Unable to initialize Abacus..."));
		}

	}

	/**
	 * Method is responsible to initialize Frame
	 */

	private void initializeFrame(Map<String, String> attributes) throws IOException {
		Boolean doWeNeedToDisplayTotalCount = isTrueOrFalse(attributes, "doWeNeedToDisplayTotalCount");
		Integer frameVerticalWidth = attributes.get("frameVerticalWidth") != null
				? Integer.valueOf(attributes.get("frameVerticalWidth"))
				: Instruction.DEFAULT_FRAME_VERTICAL_WIDTH;
		Integer frameHorizontalWidth = attributes.get("frameHorizontalWidth") != null
				? Integer.valueOf(attributes.get("frameHorizontalWidth"))
				: Instruction.DEFAULT_FRAME_VERTICAL_HEIGHT;

		frame = new Frame();
		frame.setFrameHorizontalWidth(frameHorizontalWidth.intValue());
		frame.setFrameVerticalWidth(frameVerticalWidth.intValue());
		frame.setDoWeNeedToDisplayTotalCount(doWeNeedToDisplayTotalCount.booleanValue());
		frame.setImage(getImage(attributes.get("frameImagePath")));

		frame.setPosX(0);
		frame.setPosY(0);
		frame.setWidth(panel.getWidth());
		frame.setHeight(panel.getHeight());
	}

	/**
	 * Method is responsible to initialize tutor
	 */
	
	private void initializeRods(Map<String, String> attributes) throws IOException {
		Image rodImagePath = getImage(attributes.get("rodImagePath"));
		Boolean doWeNeedToDisplayRodNumbers = isTrueOrFalse(attributes, "doWeNeedToDisplayRodNumbers");
		Integer rodWidth = attributes.get("rodWidth") != null ? Integer.valueOf(attributes.get("rodWidth"))
				: Instruction.DEFAULT_ROD_WIDTH;

		/* Draw tutor */
		
		int minusFrameWidth = frame.getFrameVerticalWidth();
		if (!canWeDisplayFrame()) {
			minusFrameWidth = 0;
		}
		int rodSpace = (panel.getWidth() - minusFrameWidth) / (getNumOfRods() + 1);
		getLogger().logDebug(
				"initializeRods Total Width ==> " + panel.getWidth() + " :: Caclulated Rod Space ==> " + rodSpace);

		tutor = new Tutor[numOftutors];
		for (int i = 0; i < numOftutors; i++) {
			tutor[i] = new Tutor();
			tutor[i].setImage(rodImagePath);
			tutor[i].setPosX(frame.getFrameVerticalWidth()+20);
			tutor[i].setPosY(140);
			tutor[i].setWidth(300);
			tutor[i].setHeight(300);
			tutor[i].setDoWeNeedToDisplayRodNumbers(doWeNeedToDisplayRodNumbers.booleanValue());
			tutor[i].setRodWidth(rodWidth.intValue());
		}
	}

	/**
	 * Method is responsible to initialize student
	 */

	private void initializeStudents(Map<String, String> attributes) throws IOException {
		Image studentImagePath = getImage(attributes.get("studentImagePath"));
		Boolean doWeNeedToDisplayRodNumbers = isTrueOrFalse(attributes, "doWeNeedToDisplayRodNumbers");
		Integer rodWidth = attributes.get("rodWidth") != null ? Integer.valueOf(attributes.get("rodWidth"))
				: Instruction.DEFAULT_ROD_WIDTH;

		/* Draw Rods */
		int minusFrameWidth = frame.getFrameVerticalWidth();
		if (!canWeDisplayFrame()) {
			minusFrameWidth = 0;
		}
		int rodSpace = (panel.getWidth() - minusFrameWidth) / (getNumOfRods() + 1);
		getLogger().logDebug(
				"initializeRods Total Width ==> " + panel.getWidth() + " :: Caclulated Rod Space ==> " + rodSpace);

		student = new Student[numOftutors];
		for (int i = 0; i < numOftutors; i++) {
			student[i] = new Student();
			student[i].setImage(studentImagePath);
			student[i].setPosX(frame.getFrameVerticalWidth() + 920);
			student[i].setPosY(230);
			student[i].setWidth(270);
			student[i].setHeight(200);
			student[i].setDoWeNeedToDisplayRodNumbers(doWeNeedToDisplayRodNumbers.booleanValue());
			student[i].setRodWidth(rodWidth.intValue());
		}
	}
	
	/**
	 * Method is responsible to initialize SpeechBoxTutor
	 */

	private void initializeSpeechBoxTutor(Map<String, String> attributes) throws IOException {
		Image speechBoxTutorImagePath = getImage(attributes.get("speechBoxTutorImagePath"));
		Boolean doWeNeedToDisplayRodNumbers = isTrueOrFalse(attributes, "doWeNeedToDisplayRodNumbers");
		Integer rodWidth = attributes.get("rodWidth") != null ? Integer.valueOf(attributes.get("rodWidth"))
				: Instruction.DEFAULT_ROD_WIDTH;

		/* Draw BOX */
		int minusFrameWidth = frame.getFrameVerticalWidth();
		if (!canWeDisplayFrame()) {
			minusFrameWidth = 0;
		}
		int rodSpace = (panel.getWidth() - minusFrameWidth) / (getNumOfRods() + 1);
		getLogger().logDebug(
				"initializeRods Total Width ==> " + panel.getWidth() + " :: Caclulated Rod Space ==> " + rodSpace);

		speechboxtutor = new SpeechBoxTutor[numOftutors];
		for (int i = 0; i < numOftutors; i++) {
			speechboxtutor[i] = new SpeechBoxTutor();
			speechboxtutor[i].setImage(speechBoxTutorImagePath);
			speechboxtutor[i].setPosX(frame.getFrameVerticalWidth() + 200);
			speechboxtutor[i].setPosY(300);
			speechboxtutor[i].setWidth(700);
			speechboxtutor[i].setHeight(100);
			speechboxtutor[i].setDoWeNeedToDisplayRodNumbers(doWeNeedToDisplayRodNumbers.booleanValue());
			speechboxtutor[i].setRodWidth(rodWidth.intValue());
		}
	}
	
	/**
	 * Method is responsible to initialize SpeechBoxStudent
	 */

	private void initializeSpeechBoxStudent(Map<String, String> attributes) throws IOException {
		Image speechBoxStudentImagePath = getImage(attributes.get("speechBoxStudentImagePath"));
		Boolean doWeNeedToDisplayRodNumbers = isTrueOrFalse(attributes, "doWeNeedToDisplayRodNumbers");
		Integer rodWidth = attributes.get("rodWidth") != null ? Integer.valueOf(attributes.get("rodWidth"))
				: Instruction.DEFAULT_ROD_WIDTH;

		/* Draw BOX */
		int minusFrameWidth = frame.getFrameVerticalWidth();
		if (!canWeDisplayFrame()) {
			minusFrameWidth = 0;
		}
		int rodSpace = (panel.getWidth() - minusFrameWidth) / (getNumOfRods() + 1);
		getLogger().logDebug(
				"initializeRods Total Width ==> " + panel.getWidth() + " :: Caclulated Rod Space ==> " + rodSpace);

		speechBoxStudents = new SpeechBoxStudent[numOftutors];
		for (int i = 0; i < numOftutors; i++) {
			speechBoxStudents[i] = new SpeechBoxStudent();
			speechBoxStudents[i].setImage(speechBoxStudentImagePath);
			speechBoxStudents[i].setPosX(frame.getFrameVerticalWidth() + 320);
			speechBoxStudents[i].setPosY(200);
			speechBoxStudents[i].setWidth(600);
			speechBoxStudents[i].setHeight(100);
			speechBoxStudents[i].setDoWeNeedToDisplayRodNumbers(doWeNeedToDisplayRodNumbers.booleanValue());
			speechBoxStudents[i].setRodWidth(rodWidth.intValue());
		}
	}
	
	
	

	/**
	 * Method is responsible to create the image
	 */
	private Image getImage(String fileName) throws IOException {
		Image image = null;
		if (!isPropertiesProvided) {
			image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(fileName));
		} else {
			image = ImageIO.read(new File(fileName));
		}
		return image;
	}

	/**
	 * Method is responsible to get the boolean property value from properties file
	 * and convert it to Boolean
	 */
	private Boolean isTrueOrFalse(Map<String, String> attributes, String properyName) {
		String val = attributes.get(properyName) != null ? attributes.get(properyName) : "false";
		return val.equalsIgnoreCase("true") ? Boolean.TRUE : Boolean.FALSE;
	}

	/**
	 * @return the frameVerticalWidth
	 */
	public Integer getFrameVerticalWidth() {
		if (!canWeDisplayFrame())
			return 0;
		return frame.getFrameVerticalWidth();
	}

	/**
	 * @param frameVerticalWidth
	 *            the frameVerticalWidth to set
	 */
	public void setFrameVerticalWidth(Integer frameVerticalWidth) {
		frame.setFrameVerticalWidth(frameVerticalWidth);
	}

	/**
	 * @return the frameHorizontalWidth
	 */
	public Integer getFrameHorizontalWidth() {
		if (!canWeDisplayFrame())
			return 0;
		return frame.getFrameHorizontalWidth();
	}

	/**
	 * @param frameHorizontalWidth
	 *            the frameHorizontalWidth to set
	 */
	public void setFrameHorizontalWidth(Integer frameHorizontalWidth) {
		frame.setFrameHorizontalWidth(frameHorizontalWidth);
	}

	/**
	 * @return the doWeNeedToDisplayFrame
	 */
	public boolean canWeDisplayFrame() {
		return doWeNeedToDisplayFrame;
	}

	/**
	 * @param doWeNeedToDisplayFrame
	 *            the doWeNeedToDisplayFrame to set
	 */
	public void setDoWeNeedToDisplayFrame(boolean doWeNeedToDisplayFrame) {
		this.doWeNeedToDisplayFrame = doWeNeedToDisplayFrame;
	}

}
