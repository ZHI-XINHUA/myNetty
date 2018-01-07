
package zxh.netty.codec.pojo;

import java.io.Serializable;

/**
 * @version 1.0
 */
public class SubscribeReq implements Serializable {

    /**
     * 默认的序列号ID
     */
    private static final long serialVersionUID = 1L;

    private int subReqID;

    private String userName;

    private String productName;

    private String phoneNumber;

    private String address;

    /**
     * @return the subReqID
     */
    public final int getSubReqID() {
	return subReqID;
    }

    /**
     * @param subReqID
     *            the subReqID to set
     */
    public final void setSubReqID(int subReqID) {
	this.subReqID = subReqID;
    }

    /**
     * @return the userName
     */
    public final String getUserName() {
	return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public final void setUserName(String userName) {
	this.userName = userName;
    }

    /**
     * @return the productName
     */
    public final String getProductName() {
	return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public final void setProductName(String productName) {
	this.productName = productName;
    }

    /**
     * @return the phoneNumber
     */
    public final String getPhoneNumber() {
	return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public final void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    /**
     * @return the address
     */
    public final String getAddress() {
	return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public final void setAddress(String address) {
	this.address = address;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "SubscribeReq [subReqID=" + subReqID + ", userName=" + userName
		+ ", productName=" + productName + ", phoneNumber="
		+ phoneNumber + ", address=" + address + "]";
    }
}
