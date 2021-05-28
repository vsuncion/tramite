package com.tramite.app;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tramite.app.Entidades.Perfiles;

@SpringBootApplication
public class TramiteApplication implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BCryptPasswordEncoder encriptar;

	public static void main(String[] args) {
		SpringApplication.run(TramiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		logger.info("================ CARGA INICIAL ============");
		String sql ="SELECT * FROM PERFIL";
		List<Perfiles> perfiles= jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Perfiles.class));
		perfiles.forEach(System.out :: println);
		
		String clave = "12345";
		
		for(int i=0;i<5;i++) {
			String clave_encriptada = encriptar.encode(clave);
			logger.info(i+"= "+clave_encriptada);
		}
	}

}
