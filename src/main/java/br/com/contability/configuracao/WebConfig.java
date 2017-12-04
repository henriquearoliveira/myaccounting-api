package br.com.contability.configuracao;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
	}
}*/

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebConfig implements Filter {
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		final HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE,");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding,"
				+ " Accept-Language, Host, Referer, Connection, User-Agent, authorization,"
				+ " sw-useragent, sw-version");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Credentials", "true");

		if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
			System.err.println("passei...");
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			System.err.println("passei");
			chain.doFilter(req, res);
		}
		
	}
	
	@Override
	public void destroy() {
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
	}
	
}

/*@Configuration
public class WebConfig {
	
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
}*/