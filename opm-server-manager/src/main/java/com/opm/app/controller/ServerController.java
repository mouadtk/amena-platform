package com.opm.app.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opm.app.business.InstallService;
import com.opm.app.model.enumeration.InstallationStatus;
import com.opm.app.model.enumeration.ServerState;
import com.opm.app.model.server.Domain;
import com.opm.app.model.server.Server;
import com.opm.app.model.server.ServerProvider;
import com.opm.app.service.DomainService;
import com.opm.app.service.ServerProviderService;
import com.opm.app.service.ServerService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/Server")
public class ServerController {

	@Autowired
	InstallService installservice;
	
	@Autowired
	private ServerProviderService providerService;
	
	@Autowired
	DomainService domainService;
	
	@Autowired
	ServerService serverService;

	@RequestMapping(value = "/Add")
	public String home(Model model){
		List<ServerProvider> providers= providerService.getAllProviders();
		model.addAttribute("providers", providers);	
		return "/server/add.servers";
	}

	@RequestMapping(value = {"/list","/All"})		
	public ModelAndView list() {
		List<Server> servers = serverService.getALLServers();
		ModelAndView mv = new ModelAndView("/server/list.servers");
		mv.addObject("servers", servers);
		return  mv;
	}

	@RequestMapping(value = "/Save", method = RequestMethod.POST)
	public String saveOneServer(Model model, HttpServletRequest req , RedirectAttributes att) {
		
		try {
			String mainip= req.getParameter("main_ip");
			if(serverService.findByMainIp(mainip)!=null){
				att.addFlashAttribute("warningMsg", "server with this main ip : "+mainip+" already exist!");
				return "redirect:/Server/Add"; 
			}
			List<String> ips_tmp= Arrays.asList(req.getParameter("extraips").split(","));
			if(ips_tmp== null || ips_tmp.size()==0){
				att.addFlashAttribute("warningMsg", "Extra Ips field is requied!");
				return "redirect:/Server/Add"; 
			}
			Server srv=serverService.saveOrUpdate(req);
			if(srv==null){
				att.addFlashAttribute("errorMsg", "can not add this server plz try aggain");
				return "redirect:/Server/Add";
			}
			List<Domain> domains= domainService.getFreeDomains(ips_tmp.size()/4 + Math.min(ips_tmp.size()%4,1), 1);
			if(domains==null)
				att.addFlashAttribute("warningMsg", "no domains for this server");
			else{ 
				srv.setDomainProvider(domains.get(0).getProvider());
				serverService.saveOrUpdate(srv);
			}
			serverService.subDomainGenerator(domains,ips_tmp, srv);
			att.addFlashAttribute("successMsg", "Server Added!");
			return "redirect:/Server/list";
		} catch (Exception e) {
			e.printStackTrace();
			att.addFlashAttribute("errorMsg",e.getClass().getSimpleName());
			return "redirect:/Server/Add";
		}

	}
		
	@RequestMapping(value="/UploadFile", method= RequestMethod.POST)
	public String uploadServerFile(@RequestParam("file") MultipartFile file, Model model , RedirectAttributes att){
		if (!file.isEmpty()) {
			try {
				InputStream in= file.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line=br.readLine())!=null){
					String[] tmp= line.trim().split(",");
					if(tmp.length==5){
						ServerProvider provider= providerService.findName(tmp[0].trim());
						String mainip= tmp[1];
						if(serverService.findByMainIp(mainip)!=null){
							att.addFlashAttribute("warningMsg", "server with this main ip : "+mainip+" already exist!");
							continue;
						}
						List<String> ips_tmp= Arrays.asList(tmp[4].trim().split("\\|"));
						if(ips_tmp== null || ips_tmp.size()==0){
							att.addFlashAttribute("warningMsg", "Extra Ips field is requied!");
						}
						Server srv= serverService.findByMainIp(mainip);
						if(srv!=null){
							att.addFlashAttribute("warningMsg", "Some server already persisted!");
							continue;
						}
						srv=new  Server();
						srv.setProvider(provider);
						srv.setMainip(mainip);
						srv.setPassword(tmp[3]);
						srv.setInstallState(InstallationStatus.WAITING);
						srv.setActif(true);
						srv.setState(ServerState.New);
						try {
							srv.setPortssh(Integer.valueOf(tmp[2]));
						} catch (Exception e) {
							srv.setPortssh(22);
						}
						if(!serverService.saveOrUpdate(srv)){
							att.addFlashAttribute("errorMsg", "can not add this server plz try aggain");
							continue;
						}
						List<Domain> domains= domainService.getFreeDomains(ips_tmp.size()/4 + Math.min(ips_tmp.size()%4,1), 1);
						if(domains==null)
							att.addFlashAttribute("warningMsg", "no domains for this server");
						else{ 
							srv.setDomainProvider(domains.get(0).getProvider());
							serverService.saveOrUpdate(srv);
						}
						serverService.subDomainGenerator(domains,ips_tmp, srv);
						att.addFlashAttribute("successMsg", "Server Added!");
						
					}else{
						att.addFlashAttribute("warningMsg", "invalide lines");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				att.addFlashAttribute("errorMsg", "Error while reading the file ! "+e.getClass().getSimpleName());
				return "redirect:/Server/Add";
			}
		}else {
			att.addFlashAttribute("warningMsg", "File is Empty !");
			return "redirect:/Server/Add";
		}
		return "redirect:/Server/All";
	}
	
	@RequestMapping(value="/Launch", method= RequestMethod.POST)
	@ResponseBody
	public String launchInstallation(@RequestParam("idServer") Long id){
		try {
			//lazy mode false
			Server server= serverService.findById(id, false);
			if(server==null){
				return "Error: can not found this server";
			}
			if(server.getDomainProvider()==null){
				return "Error: no domain affected to this server";
			}
			server.setInstallState(InstallationStatus.INPROCESS);
			serverService.saveOrUpdate(server);
			try {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						System.out.println("Thread running");
						Map<String,String> map=installservice.mainInstall(server);
						if(map.containsKey("error")){
							server.setInstallState(InstallationStatus.FAILED);
						}else
							server.setInstallState(InstallationStatus.DONE);
						serverService.saveOrUpdate(server);
						System.out.println("Thread done");
					}
				});
				t.start();
			} catch (Exception e) {
				server.setInstallState(InstallationStatus.FAILED);
				serverService.saveOrUpdate(server);
				return "Error: "+e.getClass().getSimpleName();
			}
			return "Installation service is running";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: "+e.getClass().getSimpleName();
		}
	}

	@RequestMapping(value="/CheckInstallation", method= RequestMethod.POST)
	@ResponseBody
	public Map<Long, String> checkInstallationStatus(@RequestParam("ids[]")List<Long> ids){
		Map<Long, String> response= new HashMap<>();
		try {
			response = serverService.checkServersProcess(ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	@RequestMapping("/Details/{id}")
	public String serervDetailsIndex(Model model, @PathVariable("id")Long id){
		Server server;
		try {
			server= serverService.findById(id, false);
			System.err.println("main server : "+server.getMainip());
		} catch (Exception e) {
			e.printStackTrace();
			server= new Server();
		}
		model.addAttribute("server",server);
		return "/server/details";
	}
	
	@RequestMapping("/GlobalInstallation")
	public String globallInstall(Model model, RedirectAttributes att){
		String warningMsg="";
		String errorMsg="";
		int countSuccess=0;
		try {
			List<Server> servers= serverService.getNewServerOrFailedInstalltion();
			if(servers.isEmpty()){
				warningMsg+=" All servers already installed";
			}
			for (Server server : servers) {
				
				if(server.getDomainProvider()==null){
					errorMsg+="Error: no domain affected to this server("+server.getMainip()+")<br>";
					continue;
				}
				server.setInstallState(InstallationStatus.INPROCESS);
				serverService.saveOrUpdate(server);
				try {
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							System.out.println("Thread running");
							Map<String,String> map=installservice.mainInstall(server);
							if(map.containsKey("error")){
								server.setInstallState(InstallationStatus.FAILED);
							}else
								server.setInstallState(InstallationStatus.DONE);
							serverService.saveOrUpdate(server);
							System.out.println("Thread done");
						}
					});
					t.start();
					countSuccess++;
				} catch (Exception e) {
					server.setInstallState(InstallationStatus.FAILED);
					serverService.saveOrUpdate(server);
					errorMsg+="Error: "+e.getClass().getSimpleName()+"<br>";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!warningMsg.isEmpty())
			att.addFlashAttribute("warningMsg",warningMsg);
		if(!errorMsg.isEmpty())
			att.addFlashAttribute("errorMsg",errorMsg);
		if(countSuccess>0){
			att.addFlashAttribute("successMsg","Installation running for "+countSuccess+" servers");
		}
		return "redirect:/Server/All";
	}	
}
