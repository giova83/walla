package it.mapler.walla.api;

import it.mapler.walla.api.response.ErrorStatusResponse;
import it.mapler.walla.enumeration.STATUS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/errorManager")
public class ErrorManagerApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorManagerApi.class);

	@RequestMapping(value="/invalidToken", method = RequestMethod.GET)
	private @ResponseBody  ErrorStatusResponse invalidToken(){
		 LOGGER.info("ErrorHandlerApi.ErrorStatusResponse - START");
         ErrorStatusResponse errorStatus = new ErrorStatusResponse();
	     errorStatus.setStatusError(STATUS.ERROR_TOKEN,"Invalid token");
		return errorStatus;

	}

	@RequestMapping(value="/errorCheckToken", method = RequestMethod.GET)
	private @ResponseBody ErrorStatusResponse errorCheckToken(){
		 LOGGER.info("ErrorHandlerApi.ErrorStatusResponse - START");
         ErrorStatusResponse errorStatus = new ErrorStatusResponse();
	     errorStatus.setStatusError(STATUS.ERROR_TOKEN,"Error check token");
		return errorStatus;

	}


}
