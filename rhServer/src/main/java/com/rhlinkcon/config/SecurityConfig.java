package com.rhlinkcon.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rhlinkcon.security.CustomUserDetailsService;
import com.rhlinkcon.security.JwtAuthenticationEntryPoint;
import com.rhlinkcon.security.JwtAuthenticationFilter;
import com.rhlinkcon.security.UserPrincipalLdap;
import com.rhlinkcon.util.Utils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private Environment env;

	@Value("${ldap.url}")
	private String ldapUrl;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	@Value("${ldap.username}")
	private String ldapSecurityPrincipal;

	@Value("${ldap.password}")
	private String ldapPrincipalPassword;

	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;

	@Value("${ldap.enabled}")
	private String ldapEnabled;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		//DataBase Authentication
		authenticationManagerBuilder
		.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder());

		if (Utils.checkStr(ldapEnabled) && ldapEnabled.equals("true")) {
			
			//LDAP Authentication
			authenticationManagerBuilder
			.ldapAuthentication()
			.userDetailsContextMapper(userDetailsContextMapper())
			.contextSource()
				.url(ldapUrl + ldapBaseDn)
					.managerDn(ldapSecurityPrincipal)
					.managerPassword(ldapPrincipalPassword)
				.and()
					.userSearchFilter("userPrincipalName={0}@FUNPRESP.EXE");
            
		}
	}
	
	@Bean
    public LdapContextSource contextSource () {
        LdapContextSource contextSource= new LdapContextSource();
        contextSource.setUrl(ldapUrl);
        contextSource.setBase(ldapBaseDn);
        contextSource.setUserDn(ldapSecurityPrincipal);
        contextSource.setPassword(ldapPrincipalPassword);
        return contextSource;
    }
	
	@Bean
	public UserDetailsContextMapper userDetailsContextMapper() {
		return new LdapUserDetailsMapper() {
			@Override
			public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
					Collection<? extends GrantedAuthority> authorities) {
				UserDetails details = super.mapUserFromContext(ctx, username, authorities);
				return new UserPrincipalLdap((LdapUserDetails) details, env);
			}
		};
	}
	
	@Bean
	public LdapTemplate ldapTemplate() {
		LdapTemplate ldapTemplate = new LdapTemplate(contextSource());
		ldapTemplate.setIgnorePartialResultException(true);
		return ldapTemplate;
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf()
		.disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authorizeRequests()
		.antMatchers("/",
				"/favicon.ico",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.svg",
				"/**/*.jpg",
				"/**/*.jpeg",
				"/**/*.json",
				"/**/*.woff2",
				"/**/*.woff",
				"/**/*.ttf",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js")
		.permitAll()
		.antMatchers("/api/autenticacao/login")
		.permitAll()
		.antMatchers("/api/publico/simulador/aposentadoria")
		.permitAll()
		.antMatchers(HttpMethod.GET,"/api/publico/**")
		.permitAll()
		.anyRequest()
		.authenticated();

		// Add our custom JWT security filter
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

}
