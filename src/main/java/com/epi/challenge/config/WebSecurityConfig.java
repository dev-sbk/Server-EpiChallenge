package com.epi.challenge.config;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private void globalConfigure(AuthenticationManagerBuilder auth, DataSource dataSource) {
		try {
			auth.jdbcAuthentication().dataSource(dataSource)
					.usersByUsernameQuery(
							"SELECT login as principale,password  as credentials,actived FROM clients WHERE login=?")
					.passwordEncoder(new Md5PasswordEncoder())
					.authoritiesByUsernameQuery(
							"select u.login  as principale,r.role as role from clients u, roles r where u.role_id = r.id and u.login =?")
					.rolePrefix("ROLE_");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] permissions = new String[] {"/plugins/**","/dist/**","/register/**","/facebook/**","/css/**", "/js/**", "/img**","/fonts/**"};
		http.csrf().disable().authorizeRequests().antMatchers(permissions).permitAll().anyRequest().authenticated()
				.and().formLogin().loginPage("/login").defaultSuccessUrl("/index").failureUrl("/login?error")
				.permitAll().and().logout().invalidateHttpSession(true).logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling().accessDeniedPage("/403");
	}
}
