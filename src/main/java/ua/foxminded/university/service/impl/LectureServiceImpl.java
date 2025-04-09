package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.repository.CourseRepository;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.repository.LectureRepository;
import ua.foxminded.university.service.LectureService;

import java.util.*;

@Slf4j
@Service
public class LectureServiceImpl implements LectureService {

	private static final String LOG_START_SEARCH = "Start search lecture with id - {} from table.";
	private static final String LOG_ERROR = "Lecture with ID - {} doesn't found";

	private final LectureRepository lectureRepository;

	private final GroupRepository groupRepository;
	private final CourseRepository courseRepository;

	public LectureServiceImpl(LectureRepository lectureRepository, GroupRepository groupRepository, CourseRepository courseRepository) {
		this.lectureRepository = lectureRepository;
		this.groupRepository = groupRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public List<Lecture> getAllLectures() {
		log.info("Start search all lectures from table.");
		List<Lecture> lectures = lectureRepository.findAll();
		Collections.sort(lectures);
		return lectures;
	}

	@Override
	public List<Lecture> getLecture(int lectureId) {
		List<Lecture> lecture = new ArrayList<>();
		log.info(LOG_START_SEARCH, lectureId);
		Optional<Lecture> lectureOpt = lectureRepository.findById((long) lectureId);
		if (lectureOpt.isPresent()) {
			lecture.add(lectureOpt.get());
			return lecture;
		} else {
			log.error(LOG_ERROR, lectureId);
		}
		return lecture;
	}

	@Override
	public List<Course> getLectureCourse(int lectureId) {
		List<Course> courseOfLecture = new ArrayList<>();
		log.info(LOG_START_SEARCH, lectureId);
		Optional<Lecture> lectureOpt = lectureRepository.findById((long) lectureId);
		if (lectureOpt.isPresent()) {
			courseOfLecture.add(lectureOpt.get().getCourse());
			return courseOfLecture;
		} else {
			log.error(LOG_ERROR, lectureId);
		}
		return courseOfLecture;
	}

	@Override
	public List<Group> getLectureGroups(int lectureId) {
		List<Group> groupsOfLecture = new ArrayList<>();
		log.info(LOG_START_SEARCH, lectureId);
		Optional<Lecture> lectureOpt = lectureRepository.findById((long) lectureId);
		if (lectureOpt.isPresent()) {
			groupsOfLecture.addAll(lectureOpt.get().getGroups());
			return groupsOfLecture;
		} else {
			log.error(LOG_ERROR, lectureId);
		}
		return groupsOfLecture;
	}


	@Override
	public void addNewLectures(List<Lecture> lectures){
		log.info("Start save lecture list: {} lectures.", lectures.size());
		lectureRepository.saveAll(lectures);
	}

	@Override
	public List<Lecture> saveUpdateLecture(Lecture lecture){
		if (lecture.getId() == null){
			lecture.setId(getLastLectureId() + 1);
		}
		lectureRepository.save(lecture);
		return lectureRepository.findAll();
	}

	@Override
	public void removeLecture(int lectureId) {
		log.info("Start remove lecture №{} from list.", lectureId);
		Optional<Lecture> optionalLecture = lectureRepository.findById((long)lectureId);
		if (optionalLecture.isPresent()){
			Lecture lecture = optionalLecture.get();
			lecture.setGroups(new LinkedList<>());
			lectureRepository.save(lecture);
			lectureRepository.removeById(lectureId);
		}
	}

	@Override
	public List<Lecture> addGroupToLecture(int groupId, int lectureId) {
		Optional<Lecture> lectureOptional = lectureRepository.findById((long) lectureId);
		Optional<Group> groupOptional = groupRepository.findById((long) groupId);
		if (lectureOptional.isPresent() && groupOptional.isPresent()){
			Lecture lecture = lectureOptional.get();
			List<Group> groups = lecture.getGroups();
			groups.add(groupOptional.get());
			lecture.setGroups(groups);
			lectureRepository.save(lecture);
		} else {
			log.error("Is present lecture - {}, group - {}", lectureOptional.isPresent(), groupOptional.isPresent());
			throw new NoSuchElementException();
		}
		return lectureRepository.findAll();
	}

	@Override
	public List<Lecture> removeGroupFromLecture(int groupId, int lectureId) {
		log.info(LOG_START_SEARCH, lectureId);
		Optional<Lecture> lectureOpt = lectureRepository.findById((long) lectureId);
		Optional<Group> groupOpt = groupRepository.findById((long) groupId);
		if (lectureOpt.isPresent() && groupOpt.isPresent()) {
			Lecture lecture = lectureOpt.get();
			List<Group> lectureGroups = lecture.getGroups();
			lectureGroups.remove(groupOpt.get());
			lecture.setGroups(lectureGroups);
			lectureRepository.save(lecture);
		} else {
			log.error(LOG_ERROR, lectureId);
		}
		return lectureRepository.findAll();
	}

	@Override
	public List<Lecture> addOrReplaceCourseToLecture(int courseId, int lectureId) {
		Optional<Course> courseOptional = courseRepository.findById((long) courseId);
		Optional<Lecture> lectureOptional = lectureRepository.findById((long) lectureId);
		if (courseOptional.isPresent()) {
			Lecture lecture = lectureOptional.get();
			lecture.setCourse(courseOptional.get());
			lectureRepository.save(lecture);
		} else {
			throw new NoSuchElementException();
		}
		return lectureRepository.findAll();
	}

	@Override
    public boolean clearLectureTable() {
		lectureRepository.deleteAll();
        return true;
    }

	@Override
	public Integer getLastLectureId(){
		return lectureRepository.getLastLectureId();
	}

	@Override
	public boolean removeAllGroupsFromAllLecturesAndClearTable() {
		List<Lecture> lectures = lectureRepository.findAll();
		lectures.forEach(lecture -> {
			List<Group> groupList = new LinkedList<>();
			lecture.setGroups(groupList);
		});
		lectureRepository.saveAll(lectures);
		lectureRepository.deleteAll();

		return lectureRepository.findAll().isEmpty();
	}


}