package com.abin.RedisDemo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.logging.Logger;

@SpringBootApplication
@EnableCaching
public class RedisDemoApplication implements CommandLineRunner {

	private final Logger LOG = (Logger) LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	@Autowired
	public RedisDemoApplication(UserRepository userRepository){
		this.userRepository=userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("saving users Current user count is {}.", userRepository.count());
		User shubham = new User("Shubham", 2000);
		User pankaj = new User("pankaj", 29000);
		User lewis = new User("lewis", 550);

		userRepository.save(shubham);
		userRepository.save(pankaj);
		userRepository.save(lewis);

		LOG.info("Done saving. user count is {}.", userRepository.findAll());



	}
}
