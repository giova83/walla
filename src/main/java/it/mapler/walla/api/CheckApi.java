package it.mapler.walla.api;

import it.mapler.walla.dao.LoginDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);

	 @RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Status getStatus(){
		 
        LOGGER.info("CheckApi -START-");
		 
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        
        Status status = new Status(String.valueOf(maxMemory), String.valueOf(allocatedMemory), String.valueOf(freeMemory));
        
        return status;
	}
	
	public class Status{
    
		private String maxMemory;
        private  String allocatedMemory; 
        private String freeMemory;
		
        public Status(String maxMemory, String allocatedMemory,
				String freeMemory) {
			
			this.maxMemory = maxMemory;
			this.allocatedMemory = allocatedMemory;
			this.freeMemory = freeMemory;
		}

		public String getMaxMemory() {
			return maxMemory;
		}

		public void setMaxMemory(String maxMemory) {
			this.maxMemory = maxMemory;
		}

		public String getAllocatedMemory() {
			return allocatedMemory;
		}

		public void setAllocatedMemory(String allocatedMemory) {
			this.allocatedMemory = allocatedMemory;
		}

		public String getFreeMemory() {
			return freeMemory;
		}

		public void setFreeMemory(String freeMemory) {
			this.freeMemory = freeMemory;
		}
        
        
        
        
		
	}
	
}
