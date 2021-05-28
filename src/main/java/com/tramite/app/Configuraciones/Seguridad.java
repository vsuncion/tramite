package com.tramite.app.Configuraciones;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tramite.app.Servicios.Impl.DetallesUsuario;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class Seguridad extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/*
	@Autowired
	DataSource dataSource;
	*/
	
	@Autowired
	private DetallesUsuario detallesUsuario;
	
	 @Override
	protected void configure(HttpSecurity http) throws Exception { 
		 http.authorizeRequests().antMatchers("/**","/css/**","/js/**","/imagenes/**").permitAll() 
		// .antMatchers("/admin/**").hasAnyRole("ADMIN","USER")
		 .anyRequest().authenticated()
		 .and()
		 	.formLogin().loginPage("/login")
		 	.loginProcessingUrl("/login")
		 	.defaultSuccessUrl("/admin/principal", true) 
		    .permitAll()
		 .and()
		    .logout().permitAll()
		 .and()
		    .exceptionHandling()
		    .accessDeniedPage("/accesodenagado"); 
	}

	

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		
		builder.userDetailsService(detallesUsuario)
		.passwordEncoder(passwordEncoder);
		/*
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("SELECT VUSUARIO AS username,VCLAVE as password,NESTADO as enabled FROM USUARIO WHERE  VUSUARIO=?")
		.authoritiesByUsernameQuery("SELECT \n" + 
				"   T2.VUSUARIO AS username,T3.VNOMBRE AS rol FROM USUARIO_PERFIL T1 \n" + 
				"  INNER JOIN USUARIO T2 ON T1.NUSUARIOFK=T2.NIDUSUARIOPK \n" + 
				"  INNER JOIN PERFIL  T3 ON T1.NPERFILFK=T3.NIDPERFILPK \n" + 
				" WHERE T2.VUSUARIO=?");
		*/
		
		/*
		PasswordEncoder encoder = this.passwordEncoder;
		UserBuilder usuario = User.builder().passwordEncoder(encoder::encode);
		
		builder.inMemoryAuthentication()
		.withUser(usuario.username("admin").password("123456").roles("ADMINISTRADOR"))
		.withUser(usuario.username("vladimir").password("123").roles("ATENCION_PUBLICO"));*/
		
	}
	
	

}
