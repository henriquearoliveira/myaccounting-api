package br.com.contability.configuracao;

/*@Configuration
@EnableWebSecurity*/
public class SecurityConfig /*extends WebSecurityConfigurerAdapter*/ {

	/*@Autowired
	private DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth, AuthenticationManagerBuilder authMaster)
			throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
			.usersByUsernameQuery(
				"SELECT email AS username, senha AS password, ativo AS enabled FROM usuario WHERE email = ?")
			.authoritiesByUsernameQuery(
				"SELECT email AS username, IF(administrador = true, 'ADMINISTRADOR', 'ADMIN') AS role FROM usuario WHERE email = ?");
	
	}

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
				.and().formLogin().loginPage("/login").failureUrl("/login").usernameParameter("username").passwordParameter("password")
				.defaultSuccessUrl("/", true)
				.and().rememberMe()
						.tokenValiditySeconds(1209600)
						.tokenRepository(persistentTokenRepository())
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/login").deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
		
		http.csrf().disable();

	}*/
	
	/* REMEMBER ME DAS PÁGINAS, NÃO FICA DESLOGANDO */
	/*@Bean
	public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }*/

}
