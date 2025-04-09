package ua.foxminded.university.test.staff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.foxminded.university.entity.*;
import ua.foxminded.university.repository.StaffRepository;
import ua.foxminded.university.service.CourseService;
import ua.foxminded.university.service.GroupService;
import ua.foxminded.university.service.LectureService;
import ua.foxminded.university.service.LecturerService;
import ua.foxminded.university.service.data.UserRole;
import ua.foxminded.university.service.impl.StaffServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;
    @Mock
    private CourseService courseService;
    @Mock
    private GroupService groupService;
    @Mock
    private LecturerService lecturerService;
    @Mock
    private LectureService lectureService;

    @InjectMocks
    private StaffServiceImpl staffService;

    @Test
    void testGetAllStaff() {
        List<Staff> expectedResult = getStaff();

        when(staffRepository.findAll()).thenReturn(expectedResult);
        List<Staff> actualResult = staffService.getAllStaff();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetStaffById() {
        List<Staff> expectedResult = List.of(getStaff().get(0));

        when(staffRepository.findById(1)).thenReturn(Optional.of(getStaff().get(0)));
        List<Staff> actualResult = staffService.getStaffById(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUpdateStaff() {
        boolean expectedResult = true;

        when(staffRepository.update(1,"test","test","test")).thenReturn(expectedResult);
        boolean actualResult = staffService.updateStaff(
                new Staff(1,"test","test","test", null));

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveNewStaff() {
        List<Staff> staff = getStaff();
        boolean expectedResult = true;

        when(staffRepository.saveAll(staff)).thenReturn(staff);
        boolean actualResult = staffService.saveNewStaff(staff);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testRemoveStaff() {
        boolean expectedResult = true;

        doNothing().when(staffRepository).deleteById(1);
        boolean actualResult = staffService.removeStaff(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetLastStaffId() {
        int expectedResult = 1;

        when(staffRepository.getLastStaffId()).thenReturn(1);
        int actualResult = staffService.getLastStaffId();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testClearStaffTable() {
        boolean expectedResult = true;

        doNothing().when(staffRepository).deleteAll();
        boolean actualResult = staffService.clearStaffTable();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testAssignLecturerToCourse() {
        List<Course> expectedResult = getCourses();

        doNothing().when(lecturerService).addLecturerToCourse(1, 1);
        when(courseService.getAllCourses()).thenReturn(getCourses());
        List<Course> actualResult = staffService.assignLecturerToCourse(1, 1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testAssignLectureByIdToCourse() {
        List<Course> expectedResult = getCourses();

        when(lectureService.addOrReplaceCourseToLecture(1, 1)).thenReturn(List.of(new Lecture()));
        when(courseService.getAllCourses()).thenReturn(expectedResult);
        List<Course> actualResult = staffService.assignLectureByIdToCourse(1, 1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testAssignGroupToCourse() {
        List<Course> expectedResult = getCourses();

        when(groupService.getGroup(1)).thenReturn(List.of(getGroups().get(0)));
        when(courseService.getCourseById(1)).thenReturn(List.of(getCourses().get(0)));
        doNothing().when(groupService).saveUpdateGroup(getGroups().get(0));
        when(courseService.getAllCourses()).thenReturn(expectedResult);
        List<Course> actualResult = staffService.assignGroupToCourse(1, 1);

        assertEquals(expectedResult, actualResult);


    }

    @Test
    void testUnAssignLecturerFromCourse() {
        List<Course> expectedResult = getCourses();

        doNothing().when(lecturerService).removeLecturerFromCourse(1);
        when(courseService.getAllCourses()).thenReturn(expectedResult);
        List<Course> actualResult = staffService.unsignLecturerFromCourse(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUnAssignGroupFromCourse() {
        List<Course> expectedResult = getCourses();

        when(courseService.getAllCourses()).thenReturn(expectedResult);
        List<Course> actualResult = staffService.unsignLecturerFromCourse(1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testAssignGroupToLecture() {
        List<Lecture> expectedResult = List.of(getOneLecture());

        when(lectureService.addGroupToLecture(1, 1)).thenReturn(List.of(getOneLecture()));
        when(lectureService.getAllLectures()).thenReturn(List.of(getOneLecture()));
        List<Lecture> actualResult = staffService.assignGroupToLecture(1, 1);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testUnAssignGroupFromLecture() {
        List<Lecture> expectedResult = List.of(getOneLecture());

        when(lectureService.getAllLectures()).thenReturn(expectedResult);
        List<Lecture> actualResult = staffService.assignGroupToLecture(1, 1);

        assertEquals(expectedResult, actualResult);
    }


//  Data for tests
    private List<Course> getCourses() {
        List<Course> courses = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            courses.add(Course.builder().id(i).name("TestCourse" + i).description("TestDescription" + i).groups(new LinkedList<>()).build());
        }
        return courses;
    }

    private List<Group> getGroups() {
        List<Group> groups = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            groups.add(Group.builder().id(i).name("TT-" + i + i).courses(new LinkedList<>()).build());
        }
        return groups;
    }

    private Lecture getOneLecture() {
        Lecture lecture = new Lecture();
        lecture.setId(1);
        lecture.setDayNumber(1);
        lecture.setPair(1);
        lecture.setCourse(getCourses().get(1));
        lecture.setGroups(getGroups());
        return lecture;
    }

    private List<Staff> getStaff(){
        List<Staff> staff = new LinkedList<>();
        for (int i = 1; i < 5; i++){
            staff.add(new Staff(i, "TestStaff"+i, "TestStaff"+i, "TestStaffPosition",
                    User.builder().id((long)i).login("TS" + i).password("TS" + i).role(UserRole.STAFF).build()));
        }
        return staff;
    }
}