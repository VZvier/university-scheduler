package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.repository.LecturerRepository;
import ua.foxminded.university.service.LecturerService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LecturerServiceImpl implements LecturerService {

	private final LecturerRepository lecturerRepository;

	private final CourseRepository cRepos;

	public LecturerServiceImpl(LecturerRepository lecturerRepository, CourseRepository cRepos) {
		this.lecturerRepository = lecturerRepository;
		this.cRepos = cRepos;
	}

	@Override
	public List<Lecturer> getAllLecturers() {
		log.info("Start get all lecturers from table.");
		List<Lecturer> lecturer = lecturerRepository.findAll();
		Collections.sort(lecturer);
		return lecturer;
	}

	@Override
	public List<Lecturer> getLecturer(int lecturerId) {
		List<Lecturer> lecturer = new ArrayList<>();
		log.info("Start select lecturer with ID - {} from DB.", lecturerId);
		lecturer.add(lecturerRepository.findById((long) lecturerId).orElse(null));
		return lecturer;
	}

	@Override
	public List<Course> getLecturerCourse(int lecturerId) {
		List<Course> course = new ArrayList<>();
		Optional<Lecturer> lecturer = lecturerRepository.findById((long) lecturerId);
		if (lecturer.isPresent()) {
			course.add(lecturer.map(Lecturer::getCourse).get());
			log.info("Lecturer with Id - {} was found.", lecturerId);
		} else {
			log.error("Cannot find any lecturer with ID - {}!", lecturerId);
		}
		return course;
	}

	@Override
	public void saveNewLecturers(List<Lecturer> lecturers) {
		lecturerRepository.saveAll(lecturers);
		log.info("Lecturers was saved to DB - {}.", lecturers.size());
	}

	@Override
	public Lecturer saveUpdateLecturer(Lecturer lecturer){
		if (lecturer.getId() == null) {
			lecturer.setId(getLastLecturerId() + 1);
		}
		log.info("Lecturer {} saved to DB!", lecturer);
		return lecturerRepository.save(lecturer);
	}

	@Override
	public void removeLecturer(int lecturerId) {
		lecturerRepository.deleteById((long) lecturerId);
		log.info("Lecturer was removed from DB - {}.", lecturerId);
	}

	@Override
	public void addLecturerToCourse(int lecturerId, int courseId) {
		Optional<Lecturer> lecturer = lecturerRepository.findById((long) lecturerId);
		Optional<Course> course = cRepos.findById((long) courseId);
		if (lecturer.isPresent() && course.isPresent()) {
			lecturer.get().setCourse(course.get());
			lecturerRepository.save(lecturer.get());
			log.info("Lecturer with Id - {} was added to course - {}.", lecturerId, courseId);
		}
	}

	@Override
	public void removeLecturerFromCourse(int lecturerId) {
		Optional<Lecturer> lecturer = lecturerRepository.findById((long) lecturerId);
		if (lecturer.isPresent()) {
			lecturer.get().setCourse(null);
			lecturerRepository.save(lecturer.get());
			log.info("Lecturer with Id - {} was removed from its course.", lecturerId);
		}
	}

	@Override
	public Integer getLastLecturerId() {
		return lecturerRepository.getLastLecturersId();
	}

    @Override
    public boolean clearLecturerTable() {
		lecturerRepository.deleteAll();
        return true;
    }
}