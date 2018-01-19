package zxh.netty.ssl.twoway;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public final class SecureChatSslContextFactory {
	
	 private static final String PROTOCOL = "TLS";
	
	private static SSLContext SERVER_CONTEXT;//服务器安全套接字协议
	
    private static SSLContext CLIENT_CONTEXT;//客户端安全套接字协议
    
    
	public static SSLContext getServerContext(String pkPath,String caPath){
		if(SERVER_CONTEXT!=null) return SERVER_CONTEXT;
		InputStream in =null;
		InputStream tIN = null;
		
		try{
			KeyManagerFactory kmf = null;
			if(pkPath!=null){
				//实例化KeyStore对象，JKS文件是使用keytool生成的keystore文件
				KeyStore ks = KeyStore.getInstance("JKS");
				in = new FileInputStream(pkPath);
				ks.load(in, "sNetty".toCharArray());
				
				kmf = KeyManagerFactory.getInstance("SunX509");
				kmf.init(ks, "sNetty".toCharArray());
			}
			
			TrustManagerFactory tf = null;
			if (caPath != null) {
			    KeyStore tks = KeyStore.getInstance("JKS");
			    tIN = new FileInputStream(caPath);
			    tks.load(tIN, "sNetty".toCharArray());
			    // tks.load(tIN, "123456".toCharArray());
			    tf = TrustManagerFactory.getInstance("SunX509");
			    tf.init(tks);
			}
			
			SERVER_CONTEXT= SSLContext.getInstance(PROTOCOL);
			SERVER_CONTEXT.init(kmf.getKeyManagers(),tf.getTrustManagers(), null);
			
		}catch(Exception e){
			throw new Error("Failed to initialize the server-side SSLContext", e);
		}finally{
			if(in !=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
			
			if (tIN != null){
			    try {
			    	tIN.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
				tIN = null;
		   }
		}	

		return SERVER_CONTEXT;
	 }
	
	
	 public static SSLContext getClientContext(String pkPath,String caPath){
		 if(CLIENT_CONTEXT!=null) return CLIENT_CONTEXT;
		 
		 InputStream in = null;
		 InputStream tIN = null;
		 try{
			KeyManagerFactory kmf = null;
			if (pkPath != null) {
			    KeyStore ks = KeyStore.getInstance("JKS");
			    in = new FileInputStream(pkPath);
			    ks.load(in, "cNetty".toCharArray());
			    // ks.load(in, "123456".toCharArray());
			    kmf = KeyManagerFactory.getInstance("SunX509");
			    kmf.init(ks, "cNetty".toCharArray());
			    // kmf.init(ks, "123456".toCharArray());
			}
				
			TrustManagerFactory tf = null;
			if (caPath != null) {
			    KeyStore tks = KeyStore.getInstance("JKS");
			    tIN = new FileInputStream(caPath);
			    tks.load(tIN, "cNetty".toCharArray());
			    tf = TrustManagerFactory.getInstance("SunX509");
			    tf.init(tks);
			}
			 
			 CLIENT_CONTEXT = SSLContext.getInstance(PROTOCOL);
			 CLIENT_CONTEXT.init(kmf.getKeyManagers(),tf.getTrustManagers(), null);
			 
		 }catch(Exception e){
			 throw new Error("Failed to initialize the client-side SSLContext");
		 }finally{
			 if(in !=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					in = null;
				}
				
				if (tIN != null){
				    try {
				    	tIN.close();
				    } catch (IOException e) {
				    	e.printStackTrace();
				    }
					tIN = null;
			   }
		 }
		 
		 return CLIENT_CONTEXT;
	 }

}
