package bks2101.kuraga.firstProject;

import bks2101.kuraga.firstProject.dto.Journal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstProjectApplication {

	public static void main(String[] args) {
		Journal loadedJournal = Journal.loadFromFile("journal.ser");
		if (loadedJournal == null) {
			Journal myJournal = new Journal();
			myJournal.saveToFile("journal.ser");
		}
		SpringApplication.run(FirstProjectApplication.class, args);
	}

}
