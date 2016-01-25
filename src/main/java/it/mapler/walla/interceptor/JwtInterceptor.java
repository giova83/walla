package it.mapler.walla.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import it.mapler.walla.dao.LoginDao;
import it.mapler.walla.enumeration.STATUS;
import it.mapler.walla.services.LoginService;

public class JwtInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private LoginService loginService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		LOGGER.info("JwtInterceptor.preHandle - START");
		try {
			final String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("wallaBearer ")) {
				throw new Exception("authHeader not found.");
			}

			final String token = authHeader.substring(11); // The part after
															// "Bearer "

			final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
			request.setAttribute("claims", claims);

			if (!loginService.checkLogin(token.trim())) {
				throw new Exception("token not found.");
			}
		} catch (final MalformedJwtException e) {
			LOGGER.error("Invalid token." + e.getMessage(), e);
			response.sendRedirect(request.getContextPath()+"/rest/errorManager/invalidToken");
			return false;

		} catch (final SignatureException e) {
			LOGGER.error("Invalid token." + e.getMessage(), e);
			response.sendRedirect(request.getContextPath()+"/rest/errorManager/invalidToken");
			return false;
		} catch (Exception e) {
			LOGGER.info("Error in validate token." + e.getMessage(), e);
			response.sendRedirect(request.getContextPath()+"/rest/errorManager/errorCheckToken");
			return false;
		} finally {
			LOGGER.info("JwtInterceptor.preHandle  - END");
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		LOGGER.info("JwtInterceptor.preHandle - START");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
