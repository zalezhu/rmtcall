/**
 * 
 */
package cn.com.cardinfo.rmtctrl.exe;

/**
 * @author Zale
 *
 */
public class ExecutorDispatcher {
	public static Executor dispatch(String ip,String username,String passwd,String command,String... commandArgs){
		Executor exec = null;
		if("tomcat".equals(command)){
			exec = new TomcatExecutor(ip, username, passwd, commandArgs);
		}
		if(exec ==null){
			exec = new SimpleExecutor(ip, username, passwd, command,commandArgs);
		}
		return exec;
	}
}
