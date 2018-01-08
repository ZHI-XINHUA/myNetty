package zxh.netty.codec.messagepack;

import org.msgpack.annotation.Message;

@Message
public class UserInfo {
	
	public int age;
	
	public String name;
	
	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
	
	 @Override
    public String toString() {
		 String r =  "UserInfo [name=" + name + ", age=" + age+ "]";
		 return r;
    }
	
	

}
