package com.hospital.department;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class DepartmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(DepartmentApplication.class, args);
	}
}

@Component
class BrowserLauncher {
	@EventListener(ApplicationReadyEvent.class)
	public void openBrowser() {
		try {
			Thread.sleep(1500);
			Runtime.getRuntime().exec(
					"rundll32 url.dll,FileProtocolHandler http://localhost:8080/index.html"
			);
		} catch (Exception e) {
			System.out.println("Browser could not open automatically.");
		}
	}
}