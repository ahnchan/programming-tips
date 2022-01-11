package com.ahnchan.jsondiff;

import com.ahnchan.jsondiff.vo.Audit;
import com.ahnchan.jsondiff.vo.User;
import com.ahnchan.jsondiff.vo.UserHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JsondiffApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsondiffApplication.class, args);



		// Sample data
		List<UserHistory> firstHistory = new ArrayList<UserHistory>();
		firstHistory.add(new UserHistory(Timestamp.valueOf("2021-10-15 00:00:00"), 180.1, 80.0, 27.5));
		firstHistory.add(new UserHistory(Timestamp.valueOf("2021-09-15 00:00:00"), 180.1, 81.0, 28.5));
		firstHistory.add(new UserHistory(Timestamp.valueOf("2021-08-15 00:00:00"), 180.1, 82.0, 29.5));

		User first = new User("gildong", 30, 'm', 180.1, firstHistory);

		List<UserHistory> secondHistory = new ArrayList<UserHistory>();
		secondHistory.add(new UserHistory(Timestamp.valueOf("2021-10-15 00:00:00"), 180.1, 80.0, 27.5));
		secondHistory.add(new UserHistory(Timestamp.valueOf("2021-09-15 00:00:00"), 180.1, 81.0, 28.5));
		secondHistory.add(new UserHistory(Timestamp.valueOf("2021-08-15 00:00:00"), 180.1, 82.0, 29.5));
		User second = new User("gildong", 31, 'm', 180.2, secondHistory);

		List<Audit> diffs = new ArrayList<Audit>();
		JSONDiff jsonDiff = new JSONDiff();

		try {
			diffs = jsonDiff.diffJSON(first, second);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("diff:"+ diffs);
	}

}
