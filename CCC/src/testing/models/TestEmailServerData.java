package testing.models;

import static org.junit.Assert.*;

import model.email.EmailServerData;

import org.junit.Before;
import org.junit.Test;



public class TestEmailServerData {
	
	int id1;
	int id2;
	String username1;
	String username2;
	String password1;
	String password2;
	String host1;
	String host2;
	Integer port1;
	Integer port2;
	String encryptiontype1;
	String encryptiontype2;
	
	@Before
	public void PrepareTestenviroment(){
		id1 = 1;
		id2 = 2;
		username1 = "username1";
		username2 = "username2";
		password1 = "password1";
		password2 = "password2";
		host1 = "host1";
		host2 = "host2";
		port1 = 111;
		port2 = 222;
		encryptiontype1 = "SSL";
		encryptiontype2 = "TLS";

	}
	
	
	@Test
	public void TestgetUsername(){
		assertEquals(username1, new EmailServerData(username1, password1, host1, port1, encryptiontype1).getUsername());
		assertEquals(username2, new EmailServerData(username2, password2, host2, port2, encryptiontype2).getUsername());
	}
	
	@Test
	public void TestgetPassword(){
		assertEquals(password1, new EmailServerData(username1, password1, host1, port1, encryptiontype1).getPassword());
		assertEquals(password2, new EmailServerData(username2, password2, host2, port2, encryptiontype2).getPassword());
	}
	
	@Test
	public void TestgetHost(){
		assertEquals(host1, new EmailServerData(username1, password1, host1, port1, encryptiontype1).getHost());
		assertEquals(host2, new EmailServerData(username2, password2, host2, port2, encryptiontype2).getHost());
	}
	
	@Test
	public void TestgetPort(){
		assertEquals(port1, new EmailServerData(username1, password1, host1, port1, encryptiontype1).getPort());
		assertEquals(port2, new EmailServerData(username2, password2, host2, port2, encryptiontype2).getPort());
	}
	
	@Test
	public void TestEncryption(){
		assertEquals(encryptiontype1, new EmailServerData(username1, password1, host1, port1, encryptiontype1).getEncyption());
		assertEquals(encryptiontype2, new EmailServerData(username2, password2, host2, port2, encryptiontype2).getEncyption());
	}
	
	
	@Test
	public void TestidSetterGetters(){
		EmailServerData emaildata1 = new EmailServerData(username1, password1, host1, port1, encryptiontype1);
		EmailServerData emaildata2 = new EmailServerData(username2, password2, host2, port2, encryptiontype2);
		emaildata1.setId(1);
		emaildata2.setId(2);
		assertEquals(1, emaildata1.getId());
		assertEquals(2, emaildata2.getId());
		emaildata1.setId(3);
		assertEquals(3, emaildata1.getId());
		assertEquals(2, emaildata2.getId());
	}
	
	@Test
	public void TestToString(){
		assertEquals(username1,  new EmailServerData(username1, password1, host1, port1, encryptiontype1).toString());
		assertEquals(username2, new EmailServerData(username2, password2, host2, port2, encryptiontype2).toString());
	}
}
