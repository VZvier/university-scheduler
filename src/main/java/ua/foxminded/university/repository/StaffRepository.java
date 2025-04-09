package ua.foxminded.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.university.entity.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query(value = "UPDATE Staff s set s.firstName= :firstName, s.lastName= :lastName, s.position= :position WHERE id= :id")
    boolean update(@Param("id") int id,
                   @Param("firstName") String firstName,
                   @Param("lastName") String lastName,
                   @Param("position") String position);

    @Query(value = "SELECT max(s.id) FROM Staff s")
    Integer getLastStaffId();
}
