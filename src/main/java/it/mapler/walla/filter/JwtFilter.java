package it.mapler.walla.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import it.mapler.walla.services.LoginService;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

public class JwtFilter extends GenericFilterBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
	
	@Autowired
	 private LoginService loginService;

    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
    	LOGGER.info("JwtFilter.doFilter - START");
    	HttpServletRequest request = null; 
    	try {
    	
        request = (HttpServletRequest) req;

        final String authHeader = request.getHeader("Authorization");
        
        if(StringUtils.isEmpty(authHeader)){
        	throw new Exception("authHeader not found.");
        }
        
        if (authHeader == null || !authHeader.startsWith("wallaBearer ")) {
            throw new Exception("Missing or invalid Authorization header.");
        }

        final String token = authHeader.substring(11); // The part after "Bearer "

        if(!loginService.checkLogin(token)){
        	String requestURI = request.getRequestURI();
        	String newURI = StringUtils.removeEnd(requestURI, "/rest");
            newURI = newURI + "/redirect";
            req.getRequestDispatcher(newURI).forward(req, res);
        }
        	
         final Claims claims = Jwts.parser().setSigningKey("secretkey")
                .parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
        }catch (final SignatureException e) {
        	LOGGER.info("Invalid token.");
        	String requestURI = request.getRequestURI();
        	String newURI = StringUtils.removeEnd(requestURI, "/rest");
            newURI = newURI + "/redirect";
            req.getRequestDispatcher(newURI).forward(req, res);
        }catch (Exception e) {
        	LOGGER.info("Error in validate token.");
        	String requestURI = request.getRequestURI();
        	String newURI = StringUtils.substringBefore(requestURI, "/rest");
            newURI = newURI + "/rest/redirect";
            req.getRequestDispatcher(newURI).forward(req, res);
		}finally{
			LOGGER.info("JwtFilter.doFilter - END");
		}

        chain.doFilter(req, res);
    }
    
    

}
