package zxh.netty.heartbeat_schedule;

import java.io.Serializable;
import java.util.HashMap;

public class RequestInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String ip ;
	
	private HashMap<String, Object> cpuPercMap ;
	
	private HashMap<String, Object> memoryMap;
	
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the cpuPercMap
	 */
	public HashMap<String, Object> getCpuPercMap() {
		return cpuPercMap;
	}

	/**
	 * @param cpuPercMap the cpuPercMap to set
	 */
	public void setCpuPercMap(HashMap<String, Object> cpuPercMap) {
		this.cpuPercMap = cpuPercMap;
	}

	/**
	 * @return the memoryMap
	 */
	public HashMap<String, Object> getMemoryMap() {
		return memoryMap;
	}

	/**
	 * @param memoryMap the memoryMap to set
	 */
	public void setMemoryMap(HashMap<String, Object> memoryMap) {
		this.memoryMap = memoryMap;
	}

	
	
	
}
