package ua.foxminded.university.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Course;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;
import ua.foxminded.university.repository.GroupRepository;
import ua.foxminded.university.repository.LectureRepository;
import ua.foxminded.university.service.GroupService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;

	private final LectureRepository lectureRepository;

	public GroupServiceImpl(GroupRepository groupRepository, LectureRepository lectureRepository) {
		this.groupRepository = groupRepository;
		this.lectureRepository = lectureRepository;
	}

	@Override
	public List<Group> getAllGroups() {
		log.info("Start select all groups from DB.");
		return groupRepository.findAll();
	}

	@Override
	public List<Group> getGroup(int groupId) {
		List<Group> group = new ArrayList<>();
		log.info("Start search group by ID - {}", groupId);
		group.add(groupRepository.findById((long) groupId).orElse(null));
		return group;
	}

	@Override
	@Transactional
	public List<Lecture> getGroupLectures(int groupId) {
		log.info("Start to get lectures of group with ID - {}", groupId);
		List<Lecture> lectures = new LinkedList<>();
		if (groupRepository.findById((long) groupId).isPresent()) {
			lectures = groupRepository.findById((long) groupId).map(Group::getLectures).get();
			lectures.sort(Lecture.getDayComparator().thenComparing(Lecture.getPairComparator()));
		}
		return lectures;
	}

	@Override
	@Transactional
	public List<Course> getGroupCourses(int groupId) {
		List<Course> groupCourses = new LinkedList<>();
		if (groupRepository.findById((long) groupId).isPresent()) {
			groupCourses = groupRepository.findById((long) groupId).map(Group::getCourses).get();
		}
		return groupCourses;
	}

	@Override
	public void addNewGroups(List<Group> groups) {
		groupRepository.saveAll(groups);
		log.info("New group was saved. {}", groups.size());
	}

	@Override
	public void removeGroupFromLecture(int groupId, int lectureId) {
		log.info("Start add lecture №{} for group with ID - {}", lectureId, groupId);
		Optional<Group> groupOptional = groupRepository.findById((long) groupId);
		Optional<Lecture> lectures = lectureRepository.findById((long) lectureId);

		if (groupOptional.isPresent() && lectures.isPresent()) {
			Group group = groupOptional.get();
			group.getLectures().remove(lectures.get());
			groupRepository.save(group);
			log.info("Lecture №{} added to group with ID - {}", lectureId, groupId);
		} else {
			log.error("Cannot remove group {} from lecture {}, group or lecture not found", groupId, lectureId);
		}
	}

	@Override
	public void removeAllGroupCourses(int groupId) {
		if (groupRepository.findById((long) groupId).isPresent()) {
			Group group = groupRepository.findById((long) groupId).get();
			group.setCourses(new LinkedList<>());
			saveUpdateGroup(group);
		} else {
			log.error("No such group found!");
		}
	}

	@Override
	@Transactional
	public void removeGroupFromCourse(int groupId, int courseId) {
		log.info("Start remove group №{} from course №{}", groupId, courseId);
		if (groupRepository.findById((long) groupId).isPresent()) {
			Group group = groupRepository.findById((long) groupId).get();
			if (group.getCourses() != null && !group.getCourses().isEmpty()) {
				List<Course> groupCourses = group.getCourses();
				int coursesAmountBefore = groupCourses.size();
				groupCourses.removeIf(course -> course.getId() == courseId);
				if (groupCourses.size() == coursesAmountBefore) {
					log.error("Can not unsign group №{} from course №{}", groupId, courseId);
				}
				group.setCourses(groupCourses);
			} else {
				log.error("Group does not contains courses!");
			}
		} else {
			log.error("No such group found!");
		}
	}

	@Override
	public boolean clearGroupsTable() {
		groupRepository.deleteAll();
		return true;
	}

	@Override
	public void saveUpdateGroup(Group group) {
		if (group.getId() == 0) {
			group.setId(groupRepository.getLastGroupId());
		}
		log.info("Save/update group {}", group);
		groupRepository.save(group);
	}

	@Override
	@Transactional
	public void removeGroup(Integer groupId) {
		log.info("Remove group №{}", groupId);
		if (groupRepository.findById((long) groupId).isPresent()) {
			Group group = groupRepository.findById((long) groupId).get();
			groupRepository.deleteById((long) groupId);
		} else {
			log.error("No such group found!");
		}
	}

	@Override
	public Integer getLastGroupId(){
		return groupRepository.getLastGroupId();
	}
}