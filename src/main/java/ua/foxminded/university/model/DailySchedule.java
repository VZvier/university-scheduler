package ua.foxminded.university.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.university.entity.Lecture;

import java.time.DayOfWeek;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailySchedule {

	private DayOfWeek dayOfWeek;

	private List<Lecture> lectures;
}