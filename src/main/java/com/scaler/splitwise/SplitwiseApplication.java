package com.scaler.splitwise;

import com.scaler.splitwise.commands.Command;
import com.scaler.splitwise.commands.CommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@EnableJpaAuditing
@SpringBootApplication
public class SplitwiseApplication implements CommandLineRunner {
	private CommandExecutor commandExecutor;
	private Scanner scanner = new Scanner(System.in);

	@Autowired
	public SplitwiseApplication(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}

	@Override
	public void run(String... args) throws Exception {
		while(true) {
			String inp = scanner.next();
			commandExecutor.execute(inp);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);
	}

}
