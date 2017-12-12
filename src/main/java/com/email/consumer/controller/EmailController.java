package com.email.consumer.controller;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.email.consumer.bean.EmailEntity;

@Controller
public class EmailController {

	
	//static String fileName = "population.json";
	static Logger logger = Logger.getLogger(EmailController.class);
	//static List<String> methodsList = new ArrayList<>(Arrays.asList("create_instance_"));
	
	static final String create_instance = "create_instance_";
	static final String header1 = "request_header1_";
	static final String header2 = "request_header2_";
	static final String header3 = "request_header3_";
	static final String header4 = "request_header4_";
	static final String name = "name";
	static final String value = "value";
	static final String create_instance_request = "create_instance_request";
	static final String create_instance_endpoint = "create_instance_endpoint";
	static final String fileName = "application.properties";
	static Properties env = new Properties();
	
	static {
		
		try {
			env.load(EmailController.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        X509TrustManager tm = new X509TrustManager() {

	            public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
	            }

	            public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
	            }

	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };
	        ctx.init(null, new TrustManager[]{tm}, null);
	        SSLContext.setDefault(ctx);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
		
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
	    return "mailer";
	}
	
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail (@RequestParam String from, @RequestParam String to, @RequestParam String content, 
			@RequestParam String appId, Model model) {
		logger.info("Service invoked for "+appId+" at "+System.currentTimeMillis());
		EmailEntity emailIns =new EmailEntity();
		ResponseEntity<String> createInstanceResponseEntity = null;
		// validate request
		if (validateRequest(emailIns, from, to, content, appId)) {
			// use broker to create, bind email service and send mail
			createInstanceResponseEntity = createServiceInstance(emailIns);
			logger.info(createInstanceResponseEntity.getStatusCode());
			model.addAttribute("httpStatus", HttpStatus.ACCEPTED.toString());
			model.addAttribute("response", createInstanceResponseEntity.getBody());
			
		}
		else {
			logger.info("Bad Request for "+appId+" at "+System.currentTimeMillis());
			model.addAttribute("httpStatus", HttpStatus.BAD_REQUEST.toString());
			model.addAttribute("response", "Bad Request");
		}
		logger.info("Request served for "+appId+" at "+System.currentTimeMillis());

		return "status";
		
	}

	

	private ResponseEntity<String> createServiceInstance(EmailEntity emailIns) {
		// TODO Auto-generated method stub
		logger.info("Service instance is getting created for "+emailIns.getAppId()+ " at "+System.currentTimeMillis());
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(env.getProperty(header1+name), env.getProperty(header1+value));
		headers.add(env.getProperty(header2+name), env.getProperty(header2+value));
		headers.add(env.getProperty(header3+name), env.getProperty(header3+value));
		headers.add(env.getProperty(header4+name), env.getProperty(header4+value));
		HttpEntity<String> requestEntity = new HttpEntity<String>(env.getProperty(create_instance_request), headers);
		
		ResponseEntity<String> responseEntity =
	            rest.exchange(env.getProperty(create_instance_endpoint), HttpMethod.PUT, requestEntity, String.class);
		logger.info("method ends at "+System.currentTimeMillis());
		return responseEntity;
	}

 

	private boolean validateRequest(EmailEntity emailEntity, String from, String to, String content, String appId) {
		// TODO Auto-generated method stub
		emailEntity.setAppId(appId);
		emailEntity.setContent(content);
		emailEntity.setFrom(from);
		emailEntity.setTo(to);
		emailEntity.setTimestamp((new Long(System.currentTimeMillis())).toString());
		
		return true;
		
	}

	
}
