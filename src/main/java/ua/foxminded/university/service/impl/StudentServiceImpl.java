package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Student;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.repository.StudentRepository;
import ua.foxminded.university.service.StudentService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

	private final GroupRepository gRepos;

	public StudentServiceImpl(StudentRepository studentRepository, GroupRepository gRepos) {
		this.studentRepository = studentRepository;
		this.gRepos = gRepos;
	}

	@Override
	public List<Student> getAllStudents() {
		log.info("Start get list of students.");
		List<Student> students = studentRepository.findAll();
		Collections.sort(students);
		return students;
	}

	@Override
	public List<Student> getStudent(int studentId) {
		List<Student> student = new ArrayList<>();
		log.info("Start get students with id №{} from database as list.", studentId);
		studentRepository.findById((long) studentId).ifPresent(student::add);
		return student;
	}

	@Override
	public List<Student> getStudentsInGroup(int groupId) {
		log.info("Start get students assigned to group with id №{}.", groupId);
		return studentRepository.getStudentsByGroupId(groupId);
	}

	@Override
	public List<Group> getStudentGroup(int studentId) {
		log.info("Start get group of student with id №{}.", studentId);
		List<Group> group = new ArrayList<>();
		studentRepository.findById((long) studentId).ifPresent(student -> group.add(student.getGroup()));
		return group;
	}

	@Override
	public void addStudentToGroup(int studentId, int groupId) {
		log.info("Start add student with id №{} to group with id №{}.", studentId, groupId);
		Optional<Student> student = studentRepository.findById((long) studentId);
		Optional<Group> group = gRepos.findById((long) groupId);
		if (student.isPresent() && group.isPresent()) {
			student.get().setGroup(group.get());
			studentRepository.save(student.get());
			log.info("Save of student - {}, with group - {} is successfully.", studentId, groupId);
		} else {
			log.error("Cannot find Student №{} or group №{} in database!", studentId, groupId);
		}
	}

	@Override
	public void removeStudentFromGroup(int studentId){
		log.info("Start remove student with id №{} from his group.", studentId);
		Optional<Student> studentOptional = studentRepository.findById((long) studentId);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			student.setGroup(null);
			studentRepository.save(student);
		} else {
			log.error("No such student -{} found!", studentId);
		}
	}

	@Override
	public void saveNewListOfStudents(List<Student> students) {
		log.info("Start add new student list with size - {} to database.", students.size());
		studentRepository.saveAll(students);
		log.info("Student list saved successfully");
	}

	@Override
	public Student saveUpdateStudent(Student student){
		if (student.getId() == null){
			student.setId(getLastStudentId() + 1);
		}
		log.info("Student {} saved to DB!", student);
		return studentRepository.save(student);
	}

	@Override
	public void removeStudent(int studentId) {
		log.info("Start remove student with id №{} from database.", studentId);
		removeStudentFromGroup(studentId);
		studentRepository.deleteById((long) studentId);
	}

	@Override
	public long getLastStudentId() {
		return studentRepository.getLastStudentsId();
	}

    @Override
    public boolean clearStudentsTable() {
        studentRepository.deleteAll();
		return true;
    }
}