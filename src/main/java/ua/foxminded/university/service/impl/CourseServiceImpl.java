package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.entity.Lecturer;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.service.CourseService;

import java.util.*;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

	private static final String LOG_ERROR_MESSAGE = "Course with id - {} doesn't found";

	private final CourseRepository courseRepository;

	public CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public List<Course> getAllCourses() {
		log.info("Start select all courses.");
		List<Course> list = courseRepository.findAll();
		Collections.sort(list);
		return list;
	}

	@Override
	public List<Course> getCourseById(int courseId) {
		List<Course> list = new ArrayList<>();
		log.info("Start search course by id - {}.", courseId);
		Optional<Course> course = courseRepository.findById((long) courseId);
		if (course.isPresent()) {
			list.add(course.get());
			log.info("Course ID - {} was found", courseId);
		} else {
			log.error(LOG_ERROR_MESSAGE, courseId);
		}
		return list;
	}

	@Override
	public Course getCourseByName(String name){
		Course course = courseRepository.findByName(name.strip()).orElse(null);
		if (course != null){
			return course;
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public List<Lecturer> getCourseLecturers(int courseId) {
		Optional<Course> course = courseRepository.findById((long) courseId);
		List<Lecturer> lecturers = new ArrayList<>();
		if (course.isPresent()) {
			lecturers = courseRepository.findById((long) courseId).get().getLecturers();
		} else {
			log.error(LOG_ERROR_MESSAGE, courseId);
		}
		return lecturers;
	}

	@Override
	public List<Lecture> getCourseLectures(int courseId) {
		Optional<Course> course = courseRepository.findById((long) courseId);
		List<Lecture> lectures = new ArrayList<>();
		if (course.isPresent()) {
			lectures = courseRepository.findById((long) courseId).get().getLectures();
		} else {
			log.error(LOG_ERROR_MESSAGE, courseId);
		}
		Collections.sort(lectures);
		return lectures;
	}

    @Override
    public List<Group> getGroupsOnCourse(int courseId) {
		Optional<Course> courseOptional = courseRepository.findById((long)courseId);
		if (courseOptional.isPresent()) {
			Course course = courseOptional.get();
			if (!course.getGroups().isEmpty()){
				return course.getGroups();
			} else {
				log.error("Entity doesn't contains groups!");
			}
		} else {
			log.error("Such course №{} doesn't exist!", courseId);
		}
		return new LinkedList<>();
	}

    @Override
	public void addNewCourses(List<Course> courses) {
		courseRepository.saveAll(courses);
	}

	@Override
	public Course saveUpdateCourses(Course course) {
		if (course.getId() == null ){
			course.setId(getLastCoursesId() + 1);
		}
		log.info("Course {} saved!", course);
		return courseRepository.save(course);
	}

    @Override
    public void removeCourse(int courseId) {
        courseRepository.deleteById((long) courseId);
    }

    @Override
	public Integer getLastCoursesId() {
		return courseRepository.getLastCourseId();
	}

	@Override
	public void addLecturesForCourse(int courseId, List<Lecture> lectures) {
		Optional<Course> courseOptional = courseRepository.findById((long) courseId);

		if (courseOptional.isPresent()) {
			Course course = courseOptional.get();
			List<Lecture> courseLecture = course.getLectures();
			courseLecture.addAll(lectures);
			course.setLectures(courseLecture);
			courseRepository.save(course);
		} else {
			log.error(LOG_ERROR_MESSAGE, courseId);
		}
	}

	@Override
	public boolean clearCoursesTable() {
		courseRepository.deleteAll();
		return true;
	}
}