package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.foxminded.university.entity.Group;
import ua.foxminded.university.entity.Lecture;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

	@Query(value = "SELECT max(l.id) FROM Lecture l")
	Integer getLastLectureId();

	List<Lecture> findLectureByDayNumber(int dayNumber);

	List<Lecture> findLecturesByCourseIdAndDayNumber(int courseId, int dayNumber);

	List<Lecture> findLecturesByGroupsAndDayNumber(Group group, int dayNumber);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Lecture WHERE id = :lectureId")
	void removeById(@Param("lectureId") long lectureId);
}
