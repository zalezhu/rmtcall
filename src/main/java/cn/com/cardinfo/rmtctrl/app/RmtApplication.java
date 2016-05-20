/**
 * 
 */
package cn.com.cardinfo.rmtctrl.app;

import static cn.com.cardinfo.rmtctrl.util.ValidationUtil.isIPv4;

import org.apache.log4j.Logger;

import cn.com.cardinfo.rmtctrl.exe.Executor;
import cn.com.cardinfo.rmtctrl.exe.ExecutorDispatcher;

/**
 * @author Zale
 *
 */
public class RmtApplication {
	private static Logger logger = Logger.getLogger(RmtApplication.class);
	public static void main(String[] args) {
		String remoteIp = null;
		String remoteUserName = null;
		String remotePasswd = null;
		String command = null;
		if(args.length>0){
			remoteIp = args[0];
		}
		if(args.length>1){
			remoteUserName = args[1];
		}
		if(args.length>2){
			remotePasswd = args[2];
		}
		if(args.length>3){
			command = args[3];
		}
		String[] commandArgs = null;
		if(args.length>4){
			commandArgs = new String[args.length-4];
			System.arraycopy(args, 4, commandArgs, 0, commandArgs.length);
		}
		if(remoteIp==null||remoteIp.contains("help")){
			logger.info("normal useful : java -jar rmt.jar {hostIp} {loginName} {loginPasswd} {command} {commandArgs}");
			logger.info("tomcat useful : java -jar rmt.jar {hostIp} {loginName} {loginPasswd} tomcat {stop|start|restart} {tomcatpath}");
			return ;
		}
		if(!isIPv4(remoteIp)){
			logger.error("wrong ip format");
			return;
		}
		if(args.length<3){
			logger.error("miss parameters");
			return;
		}
		Executor exec = ExecutorDispatcher.dispatch(remoteIp, remoteUserName, remotePasswd, command, commandArgs);
		try {
			exec.execute();
		} catch (Exception e) {
			logger.error("execute failed",e);
		}
		
	}
}
