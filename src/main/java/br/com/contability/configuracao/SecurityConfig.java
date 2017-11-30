package br.com.contability.configuracao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.contability.comum.ShaPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth/*, AuthenticationManagerBuilder authMaster*/)
			throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
			.usersByUsernameQuery(
				"SELECT email AS username, senha AS password, ativo AS enabled FROM usuario WHERE email = ?")
			.authoritiesByUsernameQuery(
				"SELECT email AS username, IF(administrador = true, 'ADMINISTRADOR', 'ADMIN') AS role FROM usuario WHERE email = ?");
	
	}
	 /* auth.inMemoryAuthentication()
	 .withUser("joao").password("joao").roles("CADASTRAR_VINHO").and()
	 .withUser("maria").password("maria").roles("CADASTRAR_VINHO",
	 "LISTAR_VINHO");
	 
	 authMaster.jdbcAuthentication().dataSource(dataSource)
	 .usersByUsernameQuery(
	 "SELECT email AS username, senha_master AS password, ativo AS enabled FROM usuario WHERE email = ?"
	 ) .authoritiesByUsernameQuery(
	 "SELECT email AS username, IF(administrador = true, 'ADMINISTRADOR', 'ADMIN') AS role FROM usuario WHERE email = ?"
	 );
	 
	 
	 client.jdbcAuthentication().dataSource(dataSource) .usersByUsernameQuery(
	 "SELECT email AS username, senha AS password, (SELECT TRUE) AS enable FROM usuario WHERE email = ?"
	 ) .authoritiesByUsernameQuery(
	 "SELECT email AS username, (SELECT 'ADMINISTRADOR') AS role FROM usuario WHERE email = ?"
	 );
	 
	 clientMaster.jdbcAuthentication().dataSource(dataSource)
	 .usersByUsernameQuery(
	 "SELECT email AS username, senha_master AS password, ativo AS enabled FROM usuario WHERE email = ?"
	 ) .authoritiesByUsernameQuery(
	 "SELECT email AS username, (SELECT 'ADMINISTRADOR') AS role FROM usuario WHERE email = ?"
	 );
	 
	 
	 }
	 

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("joao")
				.password(
						"0cc357ec192ad6a4432707492649ced1485ea760ebfbcb31674d1753f2c914aa9d9609aef9130856c93485628b510dbf37804443023a11da438e58411465a537")
				.roles("USER");

	}
	*/

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/layout/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder passwordEncoder = new ShaPasswordEncoder();
		return passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/login/**", "/teste/**", "/cadastrar/**", "/esqueceusenha/**", "/alterasenha/**").permitAll().anyRequest().authenticated()
				.and().httpBasic()
				.and().formLogin().loginPage("/login")/*.failureUrl("/login")*/.usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.and().rememberMe()
						.tokenValiditySeconds(1209600)
						.tokenRepository(persistentTokenRepository())
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login").deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
		
		http.csrf().disable();

		/*
		 * http.logout().logoutUrl("/logout").invalidateHttpSession(true).
		 * clearAuthentication(true);
		 * 
		 * http.logout().logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout"));
		 * 
		 * http.logout().logoutUrl("/logout").clearAuthentication(true).
		 * logoutSuccessUrl("/login");
		 * 
		 * 
		 * http .authorizeRequests() .antMatchers("/usuario").hasRole("USER")
		 * .anyRequest().authenticated() .and() .formLogin()
		 * .loginPage("/login") .permitAll() .and() .logout()
		 * .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		 * 
		 * 
		 * http.csrf().disable(); começo teste
		 * 
		 * 
		 * http.authorizeRequests()
		 * .antMatchers("/usuario").access("hasRole('ROLE_USER')") .and()
		 * .formLogin().loginPage("/login") .defaultSuccessUrl("/usuario")
		 * .failureUrl("/login?error")
		 * .usernameParameter("username").passwordParameter("password") .and()
		 * .logout().logoutSuccessUrl("/login");
		 * 
		 * final teste
		 * 
		 * http.authorizeRequests().antMatchers("/usuario/**").permitAll().
		 * anyRequest().authenticated(); //.and().httpBasic();
		 * 
		 * http.authorizeRequests().antMatchers("/login/**").permitAll().and().
		 * httpBasic();
		 * 
		 * http .authorizeRequests()
		 * .antMatchers("/usuario").hasRole("USER").anyRequest().authenticated()
		 * ;
		 * 
		 * http.logout() .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout")) .logoutSuccessUrl("/login");
		 * 
		 * http .authorizeRequests()
		 * .antMatchers("/usuario").hasRole("USER").anyRequest().authenticated()
		 * http .authorizeRequests().antMatchers("/usuario",
		 * "/login").permitAll().anyRequest().authenticated().and().httpBasic()
		 * .and() .formLogin() .loginPage("/login") .permitAll() .and()
		 * .logout() .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout"));
		 */

	}
	
	/* REMEMBER ME DAS PÁGINAS, NÃO FICA DESLOGANDO */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

}
