/**
 * 
 */
package cn.com.cardinfo.rmtctrl.exe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @author Zale
 *
 */
public class TomcatExecutor extends AbstractExecutor {
	private static Logger logger = Logger.getLogger(TomcatExecutor.class);
	private String[] command;
	
	public TomcatExecutor(String ip, String userName, String passwd, String... command) {
		super(ip, userName, passwd);
		this.command = command;
	}
	@Override
	void doCommand() throws IOException {
		
		if(!StringUtils.isEmpty(command[0])){
			if("start".equals(command[0])){
				startTomcat();
			}else if("stop".equals(command[0])){
				stopTomcat();
			}else if("restart".equals(command[0])){
				stopTomcat();
				startTomcat();
			}
		}
	}

	private void stopTomcat() throws IOException {
		Session sess = getConn().openSession();
		sess.execCommand("ps -e -o pid -o command |grep tomcat");
		InputStream stdout = new StreamGobbler(sess.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		String pid = null;
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			if (!line.contains("grep tomcat")&&line.contains(command[1])) {
				String[] outs = line.split(" ");
				pid = outs[0];
				logger.debug(pid);
			}
		}
		sess.close();
		int times = 10;
		boolean isKilled = false;
		while (times-- > 0&&pid!=null) {
			sess = getConn().openSession();
			sess.execCommand("kill " + pid);
			stdout = new StreamGobbler(sess.getStdout());
			br = new BufferedReader(new InputStreamReader(stdout));
			String line = br.readLine();
			if (line != null) {
				logger.debug(line);
				isKilled = true;
				break;
			}
			sess.close();
		}
		if (!isKilled&&pid!=null) {
			sess = getConn().openSession();
			sess.execCommand("kill -9 " + pid);
			sess.close();
		}
	}
	private void startTomcat() throws IOException {
		String startCommand ="sh "+ command[1]+"/bin/startup.sh";
		logger.info("start tomcat "+startCommand);
		Session sess = getConn().openSession();
		sess.execCommand(startCommand);
		InputStream stdout = new StreamGobbler(sess.getStdout());
		BufferedReader br = new BufferedReader(new InputStreamReader(stdout));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			logger.info(line);
		}
		sess.close();
	}

}
