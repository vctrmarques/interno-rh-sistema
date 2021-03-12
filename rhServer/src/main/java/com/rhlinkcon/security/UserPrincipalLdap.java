package com.rhlinkcon.security;

import java.util.Collection;

import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

public class UserPrincipalLdap implements LdapUserDetails{
	

	private LdapUserDetails details;
	private Environment env;

	public UserPrincipalLdap(LdapUserDetails details, Environment env) {
	    this.details = details;
	    this.env = env;
	}

	public boolean isEnabled() {
	    return details.isEnabled() && getUsername().equals(env.getRequiredProperty("ldap.username"));
	}

	public String getDn() {
	    return details.getDn();
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return details.getAuthorities();
	}

	public String getPassword() {
	    return details.getPassword();
	}

	public String getUsername() {
	    return details.getUsername();
	}

	public boolean isAccountNonExpired() {
	    return details.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
	    return details.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
	    return details.isCredentialsNonExpired();
	}

	@Override
	public void eraseCredentials() {
		// TODO Auto-generated method stub
		
	}
}
