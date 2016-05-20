package cn.com.cardinfo.rmtctrl.exe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * 
 * @author Zale
 *
 */
public class SimpleExecutor extends AbstractExecutor {
	private static Logger logger = Logger.getLogger(Executor.class);
	private String command;
	private String[] commandArgs;
	@Override
	void doCommand() throws IOException {
		Session sess = getConn().openSession();
		if(commandArgs!=null){
			for(String arg:commandArgs){
				command+=" "+arg;
			}
		}
		sess.execCommand(command);
		InputStream stdout = new StreamGobbler(sess.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			logger.info(line);
		}
	}

	public SimpleExecutor(String ip, String userName, String passwd, String command, String[] commandArgs) {
		super(ip, userName, passwd);
		this.command = command;
		this.commandArgs = commandArgs;
	}

	

}
