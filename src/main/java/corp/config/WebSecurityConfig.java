package corp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	/**
	 * Admin Role: allows access to /admin/** 
	 * User Role: allows access to /user/**
	 * custom 403: access denied handler
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/home", "/about", "/invoice-viewer-page").permitAll() // if request is for /home or /about->permit
				.antMatchers(HttpMethod.POST,"/api/findInvoice").permitAll()
				.antMatchers(HttpMethod.GET,"/assets/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN") // if request path is /admin/** -> verify it has at least role ADMIN 
				.antMatchers("/user/**").hasAnyRole("USER")
				.anyRequest().authenticated() // any request needs to be authenticated
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll() // allows all users to access /login
				.and()
			.logout()
				.permitAll() // Allows all users to logout
				.and()	
			.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
	
	/**
	 * Creates two users: admin and user
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("rmolerof").password("facturacion2301").roles("USER", "ADMIN")
				.and()
				.withUser("avilcahuaman").password("avilcahuaman0602").roles("USER")
				.and()
				.withUser("ymolero").password("ymolero0603").roles("USER", "ADMIN")
				.and()
				.withUser("pmori").password("pmori0604").roles("USER", "ADMIN")
				.and()
				.withUser("imolero").password("imolero0605").roles("USER", "ADMIN")
				.and()
				.withUser("personal2").password("personal2022").roles("USER")
				.and()
				.withUser("personal1").password("personal2021").roles("USER");
		
	}
}
