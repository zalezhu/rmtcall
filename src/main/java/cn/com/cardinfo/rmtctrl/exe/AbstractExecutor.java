package cn.com.cardinfo.rmtctrl.exe;

import java.io.IOException;

import ch.ethz.ssh2.Connection;

public abstract class AbstractExecutor implements Executor {
	private String ip;
	private String userName;
	private String passwd;
	private Connection conn;
	
	public AbstractExecutor(String ip, String userName, String passwd) {
		super();
		this.ip = ip;
		this.userName = userName;
		this.passwd = passwd;
	}

	public Connection getConn() {
		return conn;
	}

	public void execute() throws Exception {
		getConnection();
		doCommand();
		close();
	}

	private void getConnection() throws IOException {
		conn = new Connection(ip);  
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(userName, passwd);  
		if(!isAuthenticated){
			throw new RuntimeException("connect failed");
		}
	}

	abstract void doCommand() throws IOException;
	
	private void close(){
		conn.close();
	}

}
