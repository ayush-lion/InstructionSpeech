/**
 * 
 */
package com.app.instruction.panel.exception;

/**
 * @author prashant.joshi
 *
 */
public class InstructionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String customizedMessage;

	public InstructionException(String customizedMessage) {
		this.customizedMessage = customizedMessage;
	}
	
	/**
	 * @return the customizedMessage
	 */
	public String getCustomizedMessage() {
		return customizedMessage;
	}

	/**
	 * @param customizedMessage the customizedMessage to set
	 */
	public void setCustomizedMessage(String customizedMessage) {
		this.customizedMessage = customizedMessage;
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("Exception Message ==> ").append(getMessage()).append("\n");
		buf.append("Customized Exception ==> ").append(getCustomizedMessage());
		return buf.toString();
	}
}
