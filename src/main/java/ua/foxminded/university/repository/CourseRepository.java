package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT max(c.id) FROM Course c")
    Integer getLastCourseId();

    Optional<Course> findByName(String name);
}
