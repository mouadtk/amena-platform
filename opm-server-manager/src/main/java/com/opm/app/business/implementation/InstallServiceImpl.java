/**
 * 
 */
package com.opm.app.business.implementation;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.opm.app.business.InstallService;
import com.opm.app.model.server.InstallationLog;
import com.opm.app.model.server.Server;
import com.opm.app.model.server.ServerIP;
import com.opm.app.service.InstallationLogService;
import com.opm.app.service.ServerService;

/**
 * @author Mouad-tk
 * 25 nov. 2016
 */
@Service
@PropertySource(value = { "classpath:application.properties" })
public class InstallServiceImpl implements InstallService{
	
	@Value("config_resources_path")
	String INSTALLRESOURCE;

	@Value("config_destination_path")
	String RESOURCEDESTINATION ;
	
	@Autowired
	ServerService serverservice;
	
	@Autowired
	InstallationLogService logService;

	@Override
	public  Map<String, String> copyInstallFiles(Session session, String destinationFile) {
		
		Map<String, String> logger = new HashMap<>();
		if (session == null || destinationFile.isEmpty()){
			logger.put("error", session == null ? " session is lost" : " destinationFile is empty");
			return logger;
		}
		try	{
			ChannelSftp sftpChan = (ChannelSftp) session.openChannel("sftp");
			sftpChan.connect();
			System.out.println("INSTALLRESOURCE   ::::  "+INSTALLRESOURCE);
		   /***
			* need change
			**/
			INSTALLRESOURCE="configuration.zip";
			Resource resource = new ClassPathResource("/"+INSTALLRESOURCE);
			sftpChan.put(resource.getFile().getAbsolutePath(), destinationFile);/** lfcha7A hia progress bar SFTPProgressMonitor*/

			sftpChan.disconnect();
		} catch (Exception e) {
			logger.put("error", e.getMessage());
			e.printStackTrace();
		}
		return logger;
	}

	@SuppressWarnings("unused")
	@Override
	public  Map<String, String> mainInstall(Server server) {
		Map<String, String> logger = new HashMap<String, String>();
		Session session = null;
		/**
		 * connections
		 **/
		System.out.println("start connection");
		try {
			try {
				session = sshConnect(server);
			} catch (Exception e) {
				System.out.println("connection failed");
				logger.put("error",e.getClass().getSimpleName());
				try {session.disconnect();} catch (Exception e2) {}
				session=null;
			}
			
			/**
			 * copy installation files       *********  OK
			 **/
			if(!saveLogForService("Connection", logger, server))
				return logger;
			System.out.println("File Transert start");
			RESOURCEDESTINATION="/home/configuration.zip";			
			execCommand(session, "rm -f "+RESOURCEDESTINATION);
			if(!saveLogForService("File Transfer", logger=copyInstallFiles(session, RESOURCEDESTINATION), server))
				return logger;
			System.out.println("Transfer Done");
			/**
			 * step 1
			 * update and install basic dependencies *********  OK
			 */ 
			System.out.println("Start update");
			execCommand(session, "yum update -y");
			System.out.println("update done");
			if(!saveLogForService("System Update", logger=execCommand(session, "yum clean all ; yum update -y"), server))
				return logger;
			/**
			 * step 2
			 */
			System.out.println("Start dependecies");
			if(!saveLogForService("Dependencies", logger=execCommand(session," yum install rpm grep gawk nano perl chkconfig openssh-clients wget php php-mysql zip unzip telnet gcc make cmake rsync bind-utils net-tools -y"), server))
				return logger;
			System.out.println("dependecies done");
			/**
			 * unzip the config file; -o to override files if exists -d define  *********  OK
			 * destination folder or file
			 */
			/***
			 * step 3
			 */
			System.out.println("Start unzip");
			if(!saveLogForService("Unzip Ressources Files", logger=execCommand(session, "unzip -o " + RESOURCEDESTINATION + " -d /home/"), server))
				return logger;
			System.out.println("unzip done");
			/**
			 * launch install.sh httpd + configuration de mysql si neccessaire
			 */
			/**
			 * step 4a
			 */
			System.out.println("Start httpd");
			if(!saveLogForService("Httpd and PHP", logger=execCommand(session,
					"chmod 755 /home/configuration/httpd/install.sh ;  sh  /home/configuration/httpd/install.sh"), server))
				return logger;
			System.out.println("httpd done");
			/**
			 * Network configuration   *********  OK
			 */
			/***
			 * step 5
			 */
			try{	
				execCommand(session, "chmod 755 /home/configuration/networkconf.sh; echo > /home/configuration/ips.txt");
				execCommand(session, "rm -f /home/configuration/ips.txt");
				for (ServerIP ip : server.getIpserver()) {
					execCommand(session, "echo " + ip.getIp().trim() + ">> /home/configuration/ips.txt");
				}
			}catch(Exception e){
				System.err.println(e.getMessage());
				return logger;
			}

			if(!saveLogForService("Network Configuration", logger=execCommand(session, "sh  /home/configuration/networkconf.sh " + server.getMainip()), server))
				return logger;
			/**
			 * Generation des cle ssh
			 */
			/**
			 * step 6
			 */
			if(!saveLogForService("DKIM", logger=execCommand(session, "chmod 755 /home/configuration/dkim-gen.sh"), server))
				return logger;

			for (ServerIP ip : server.getIpserver()) {
				if (ip.getDomain().split("\\.").length == 2) {
					try{
						logger = execCommand(session, "sh  /home/configuration/dkim-gen.sh " + ip.getDomain());
					}catch(Exception e){
						e.printStackTrace();

					}
					if (logger.containsKey("error") && !logger.get("error").isEmpty()) {
						System.err.println("error  " + logger.get("error"));
						return logger;
					}
				}
				else {
					System.err.println(" SSH KEY "+ip.getDomain());
				}
			}

			/**
			 * launch pmta installation
			 */
			/***
			 * step8
			 */
			execCommand(session, "echo >> /home/configuration/pmta/ip_domain.txt");
			for (ServerIP ip : server.getIpserver()) {
				String toAppend=ip.getIp()+";"+ip.getDomain().trim();
				execCommand(session, "echo '"+toAppend+"' >> /home/configuration/pmta/ip_domain.txt");
			}
			
			System.out.println("PMTA START ");
			if(!saveLogForService("PMTA", logger=execCommand(session,
					"chmod +x /home/configuration/pmta/install.sh ;  sh  /home/configuration/pmta/install.sh"), server))
				return logger;
			
			/**
			 * step 9
			 * start iptables
			 */
			if (false){
				try{
					logger = execCommand(session,"chmod 755 /home/configuration/iptables.sh ; sh /home/configuration/iptables.sh");
				}catch(Exception e){
					System.err.println(e.getMessage());
					return logger;
				}
				try{
					logger = execCommand(session,"service iptables restart");
				}catch(Exception e){
					System.err.println(e.getMessage());
					return logger;
				}
			}
			execCommand(session,"rm -rf /home/configuration* ");
			session.disconnect();
			
		} catch (Exception e) {
			logger.put("error", e.getMessage());
		}
		finally {
			try {
				session.disconnect();
			} catch (Exception e2) {}
		}
		return logger;
	}

	/***
	 * 
	 * @param session
	 * @param command
	 * @return logger (error case ) ? [error: 'error message'] : empty map  
	 * 
	 */
	@Override
	public  java.util.Map<String, String> execCommand(Session session, String command) {
		Map<String, String> logger = new HashMap<String, String>();
		if (session == null || command.isEmpty()) {
			logger.put("error", session == null ? " session is lost" : " command is empty");
			return logger;
		}
		try {
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);
			channel.setInputStream(null);
			ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();
			((ChannelExec) channel).setErrStream(errorOutput);
			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				
				
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					if (in.available() > 0)
						continue;
					int exit_status = channel.getExitStatus();
					System.out.println("exit-status: " + exit_status);
					if (exit_status != 0) {
						byte[] errors = errorOutput.toByteArray();
						System.err.println("errs: " + new String(errors, StandardCharsets.UTF_8));
						logger.put("error", new String(errors, StandardCharsets.UTF_8));
					}
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (Exception ee) {
					logger.put("error", ee.getMessage());
					return logger;
				}
			}
			channel.disconnect();
		} catch (Exception e) {
			logger.put("error", e.getMessage());
			e.printStackTrace();
		}
		return logger;
	}
	
	
	@Override
	public Session sshConnect(Server server) throws JSchException {
		JSch jsch=new JSch();
		try{
			Session session=jsch.getSession("root", server.getMainip(), server.getPortssh());
			session.setPassword(server.getPassword());
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect(30000);
			return session;
		}catch(JSchException jse){
			jse.printStackTrace();
			throw jse;
		}
	}

	/***
	 * 
	 * @param serviceName
	 * @param map
	 * @param server
	 * @return
	 */
	public boolean saveLogForService(String serviceName, Map<String, String> map, Server server){
		
		InstallationLog log= new InstallationLog();
		log.setServer(server);
		log.setServiceName(serviceName);
		log.setActif(true);
		try {
			if(map.containsKey("error")){
				log.setInstalled(false);
				log.setComment(map.get("error"));
				logService.saveOrupdate(log);
				return false;
			}else{
				log.setComment("");
				log.setInstalled(true);
				logService.saveOrupdate(log);
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	};
	
}

class test {
	public static void main(String[] arg) {

		Server server = new Server();
		//
		server.setMainip("67.227.24.119");
		server.setPassword("Aq8@f1n@$");
		server.setPortssh(22);

		ServerIP ip1 = new ServerIP();
		ip1.setIp("67.227.24.119");
		ip1.setDomain("labopm.com");

		ServerIP ip2 = new ServerIP();
		ip2.setIp("192.168.9.244");
		ip2.setDomain("sub2.labopm.com");

		ServerIP ip3 = new ServerIP();
		ip3.setIp("192.168.9.252");
		ip3.setDomain("sub3.labopm.com");

		Set<ServerIP> ips = new HashSet<>();
		ips.add(ip1);
		ips.add(ip2);
		ips.add(ip3);
		server.setIpserver(ips);
		try {
			JSch jsch = new JSch();

			Session session = jsch.getSession("root", server.getMainip(), 22);
			session.setPassword(server.getPassword());

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect();
			//mainInstall(server, session);
			session.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}