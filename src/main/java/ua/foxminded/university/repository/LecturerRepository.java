package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

	@Query(value = "SELECT max(lr.id) FROM Lecturer lr")
	Integer getLastLecturersId();
}
