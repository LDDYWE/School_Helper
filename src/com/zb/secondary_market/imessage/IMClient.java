package com.zb.secondary_market.imessage;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
/**
 * Create a instant message client to connect to the server<br><br>
 * <strong>public methods:<br>
 * {@code register()}<br>
 * {@code login()}<br>
 * {@code changePassword()}<br>
 * {@code logout()}<br>
 * {@code sendMessage()}<br>
 * {@code getUsername()}<br>
 * {@code setIMessageListener()}<br><br>
 * <stong>public interface: </strong>{@link IMessageListener}
 * @author JohnDannl
 *
 */
public class IMClient {
	public static String TAG="IMessageClient";	
	public static String serverName="im.ustc.edu.cn";
	public static String hostName="202.38.73.228",resource="android";	
	private String username="",password="";
	private AbstractXMPPConnection conn=null;
	private AccountManager accountManager=null;
	private ChatManager chatManager=null;
	private IMessageListener msgListener=null;
	private Map<String,Chat> bud2chat=new HashMap<String,Chat>();
	
	/**
	 * Both username and password could not be empty,username must be global unique
	 * @param username
	 * @param password
	 */
	public IMClient(String username,String password){
		this.username=username;
		this.password=password;
	}	
	private XMPPTCPConnectionConfiguration init_config() 
			throws NoSuchAlgorithmException, KeyManagementException{
		SSLContext sslcontext =SSLContext.getInstance("TLS");
		X509TrustManager tm = new X509TrustManager() {
            
			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}			
        };
        sslcontext.init(null, new TrustManager[] {tm}, null); 
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
      		  .setUsernameAndPassword(this.username, this.password)
      		  .setServiceName(serverName)
      		  .setHost(hostName)		
      		  .setCustomSSLContext(sslcontext)	// unable to find valid certification path to requested target
      		  .setPort(5222)
      		  .setResource(resource) 	// you can specify the login platform:windows/android/...
      		  .build();		//.setSecurityMode(SecurityMode.ifpossible)
        return config;
	}
	private void init_chatmanager(){
		chatManager = ChatManager.getInstanceFor(conn);
		chatManager.addChatListener(new ChatManagerListener(){

			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				// TODO Auto-generated method stub
				String par_name=chat.getParticipant().split("@",2)[0];
				bud2chat.put(par_name, chat);
				//Log.d(TAG, "passive:new chat from:"+":"+chat.getParticipant()+chat.getThreadID());
				chat.addMessageListener(new ChatMessageListener() {
					
					@Override
					public void processMessage(Chat chat, Message message) {
						// TODO Auto-generated method stub
						String par_name=chat.getParticipant().split("@",2)[0];
						//Log.d(TAG, "passive:"+message.getBody());
						msgListener.onMessageReceive(par_name, message.getBody());
					}
					
				});					
			}
			
		});
	}
	private Chat createChat(String participant){
		Chat newChat= chatManager.createChat(participant+"@"+serverName, new ChatMessageListener(){

			@Override
			public void processMessage(Chat chat, Message message) {
				// TODO Auto-generated method stub
				/*String par_name=chat.getParticipant().split("@",2)[0];
				Log.d(TAG, "positive:"+message.getBody());
				bud2chat.put(par_name, chat);
				msgListener.onMessageReceive(par_name, message.getBody());*/
			}
			
		});
		//Log.d(TAG, newChat.getThreadID());
		return newChat;
	}
	
	/**
	 * May block,should not be run in UI thread.If the user name has been registered, 
	 * a XMPPException "XMPPError: conflict - cancel" will be thrown
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws SmackException
	 * @throws IOException
	 * @throws XMPPException
	 * @author JohnDannl
	 */
	public void register() throws KeyManagementException,
	NoSuchAlgorithmException, SmackException, IOException, XMPPException{		
		XMPPTCPConnectionConfiguration config=init_config();
		//SmackConfiguration.DEBUG = true;	// open debug model		
		conn = new XMPPTCPConnection(config);
		conn.connect();
		accountManager=AccountManager.getInstance(conn);
		accountManager.createAccount(this.username,this.password);		
		conn.disconnect();
		accountManager=null;
		conn=null;
	}
	
	
	/**
	 * May block,should not be run in UI thread.If the user and password are not compatible,
	 * a XMPPException -- "SASLError using DIGEST-MD5: not-authorized" will be thrown
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws SmackException
	 * @throws IOException
	 * @throws XMPPException
	 * @author JohnDannl
	 */
	public void login() throws KeyManagementException, 
	NoSuchAlgorithmException, SmackException, IOException, XMPPException{
		XMPPTCPConnectionConfiguration config=init_config();
		if(conn!=null || accountManager!=null){
			conn.disconnect();
			accountManager=null;
			conn=null;
		}
		conn = new XMPPTCPConnection(config);
		//conn=new XMPPTCPConnection(username, password, serverName);
		conn.connect();
		accountManager=AccountManager.getInstance(conn);
		conn.login();
		init_chatmanager();
	}
	/**
	 * May block,should not be run in UI thread.
	 * You should login first before you change an acount's password
	 * @param newPassword
	 * @throws NoResponseException
	 * @throws XMPPErrorException
	 * @throws NotConnectedException
	 * @author JohnDannl
	 */
	public void changePassword(String newPassword) throws NoResponseException, 
	XMPPErrorException, NotConnectedException{
		if(conn!=null && accountManager!=null){
			accountManager.changePassword(newPassword);
		}
	}
	/**
	 * May block,should not be run in UI thread.
	 * Send a message to a participant,the participant can be on/off-line and case ignored.
	 * @param participant
	 * @param message
	 * @throws NotConnectedException
	 * @author JohnDannl
	 */
	public void sendMessage(String participant,String message) throws NotConnectedException{
		Chat aChat;
		participant=participant.toLowerCase();
		/*for(String name:bud2chat.keySet())
			Log.d(TAG, name);*/
		if(bud2chat.containsKey(participant))aChat=bud2chat.get(participant);
		else {
			aChat=createChat(participant);
			bud2chat.put(participant, aChat);
		}
		aChat.sendMessage(message);
	}
	public String getUsername(){return username;}
	/**
	 * Go off-line
	 */
	public void Logout(){
		if(conn!=null)conn.disconnect();
	}
	/**
	 * The interface to process message receiving
	 * @author JohnDannl
	 *
	 */
	public interface IMessageListener{
		/**
		 * Receiving a message(msg) from someone(name)
		 * @param name
		 * @param msg
		 */
		public void onMessageReceive(String name, String msg);		
	}
	public void setIMessageListener(IMessageListener listener){
		if(listener!=null)this.msgListener=listener;
	}
}
