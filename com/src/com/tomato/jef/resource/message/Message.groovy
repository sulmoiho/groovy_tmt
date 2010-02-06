package com.tomato.jef.resource.message;

/**
 * System Message를 담는 JavaBean
 * @author TomatoSystem
 * @since TmtFrameWork Version 2.0 (jsp/xbuilder)
 * @re-version 2009.12.12 sulmoiho
 */
class Message implements Serializable {
	static final long serialVersionUID = 9125327772409453441L;
	
	private String system;
	private String msgCd;
	private String msgTp;
	private String msg;
	
	/**
	 * 생성자
	 */
	Message() {
		super()
	}

	/**
	 * 생성자
	 */
	Message(String system, String msgCd, String msgTp, String msg) {
		this.system = system
		this.msgCd 	= msgCd
		this.msgTp	= msgTp
		this.msg	= msg
	}
	
	String getMsg() { return msg }
	void setMsg(String msg) { this.msg = msg }

	String getMsgCd() { return msgCd }
	void setMsgCd(String msgCd) { this.msgCd = msgCd }
	
	String getMsgTp() { return msgTp }
	void setMsgTp(String msgTp) { this.msgTp = msgTp }
	
	String getSystem() { return system }
	void setSystem(String system) { this.system = system }
	
	/**
	 * equals 메소드 재정의
	 */
	boolean equals(Object message) {
		boolean result = false
		
		if(message && message instanceof Message){
			Message target = (Message) message
			if((this.system == null && target.system == null) || (this.system && this.system == target.system))
				if((this.msgCd == null && target.msgCd == null) || (this.msgCd && this.msgCd == target.msgCd))
					if((this.msgTp == null && target.msgTp == null) || (this.msgTp && this.msgTp == target.msgTp))
						if((this.msg == null && target.msg == null) || (this.msg && this.msg == target.msg)) result = true
		}
		
		return result
	}
	
	/**
	 * hashCode 메소드 재정의
	 */
	int hashCode() {
		final def prime = 31
		def result = 17
		
		result = prime * result + (this.system 	? this.system.hashCode(): 0)
		result = prime * result + (this.msgCd 	? this.msgCd.hashCode() : 0)
		result = prime * result + (this.msgTp 	? this.msgTp.hashCode() : 0)
		result = prime * result + (this.msg 	? this.msg.hashCode() 	: 0)
		
		return result;
	}
}
