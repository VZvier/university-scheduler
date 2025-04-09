package ua.foxminded.university.service.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.foxminded.university.entity.*;

import java.util.*;

@Slf4j
@Component
public class TestData {


    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Random rand = new Random();

    //    Ready
    public List<String> createFullNames() {
        List<String> fullNames = new LinkedList<>();
        List<String> firstNames = FileReading.getAsList("src/main/resources/ua/foxminded/university/FirstNames.txt");
        List<String> lastNames = FileReading.getAsList("src/main/resources/ua/foxminded/university/LastNames.txt");
        for (String name : firstNames) {
            for (String secondName : lastNames) {
                fullNames.add(name + " " + secondName);
            }
        }
        return fullNames;
    }

    //    Ready
    public List<Lecturer> createLecturers(List<String> fullNames) {
        List<Lecturer> lecturers = new LinkedList<>();
        for (String str : fullNames) {
            String[] fullName = str.split(" ");
            lecturers.add(Lecturer.builder().id(fullNames.indexOf(str) + 1)
                    .firstName(fullName[0])
                    .lastName(fullName[1])
                    .user(User.builder().id((long) fullNames.indexOf(str) + 1)
                            .login(fullName[0].substring(0, 2)
                                    + fullName[1].substring(0, 2)
                                    + (fullNames.indexOf(str) + 1))
                            .encodedPassword(encoder.encode(
                                    fullName[0].substring(0, 2)
                                            + fullName[1].substring(0, 2)
                                            + (fullNames.indexOf(str) + 1)))
                            .role(UserRole.LECTURER).build())
                    .build());
        }
        return lecturers;
    }

    //    Ready
    public List<Student> createStudents(List<String> fullNames) {
        List<Student> students = new LinkedList<>();
        for (String str : fullNames) {
            String[] fullName = str.split(" ");
            students.add(Student.builder().id((long) fullNames.indexOf(str) + 1)
                    .firstName(fullName[0])
                    .lastName(fullName[1])
                    .user(User.builder().id((long) fullNames.indexOf(str) + 157)
                            .login(fullName[0].substring(0, 2)
                                    + fullName[1].substring(0, 2)
                                    + (fullNames.indexOf(str) + 157))
                            .encodedPassword(encoder.encode(fullName[0].substring(0, 2)
                                    + fullName[1].substring(0, 2)
                                    + (fullNames.indexOf(str) + 157)))
                            .role(UserRole.STUDENT).build())
                    .build());
        }
        return students;
    }

    //    Ready
    public List<Staff> createStaff(List<String> fullNames) {
        List<Staff> staff = new LinkedList<>();
        List<String> positions = new LinkedList<>();
        int numberOfPositions = 0;
        int multiplier = 1;
        String position;
        for (String str : fullNames) {
            String[] fullName = str.split(" ");
            position = getStaffPosition((numberOfPositions < 5) ? numberOfPositions : 4);
            positions.add(position);
            if (Collections.frequency(positions, position) >= (numberOfPositions * multiplier)) {
                numberOfPositions++;
                multiplier *= 2;
            }
            staff.add(Staff.builder().id(fullNames.indexOf(str) + 1)
                    .firstName(fullName[0])
                    .lastName(fullName[1])
                    .position(position)
                    .user(
                            User.builder().id((long) fullNames.indexOf(str) + 101)
                                    .login(fullName[0].substring(0, 2)
                                            + fullName[1].substring(0, 2)
                                            + (fullNames.indexOf(str) + 101))
                                    .encodedPassword(encoder.encode(
                                            fullName[0].substring(0, 2)
                                                    + fullName[1].substring(0, 2)
                                                    + (fullNames.indexOf(str) + 101)))
                                    .role(UserRole.STAFF)
                                    .build())
                    .build());
        }

        return staff;
    }

    public List<Staff> createAdmins(List<String> listFullNames) {
        List<Staff> admins = new LinkedList<>();
        admins.add(Staff.builder().id(51)
                .firstName("Harry")
                .lastName("Hacker")
                .position("System Administrator")
                .user(User.builder().id(151L).login("Admin").encodedPassword(encoder.encode("admin")).role(UserRole.ADMIN).build()).build());
        for (String str : listFullNames) {
            String[] fullName = str.split(" ");
            admins.add(
                    Staff.builder().id(152 + listFullNames.indexOf(str))
                            .firstName(fullName[0])
                            .lastName(fullName[1])
                            .position("System Administrator")
                            .user(User.builder().id((long) (152 + listFullNames.indexOf(str)))
                                    .login(fullName[0].substring(0, 2)
                                            + fullName[1].substring(0, 2)
                                            + (152 + listFullNames.indexOf(str)))
                                    .encodedPassword(encoder.encode(
                                            fullName[0].substring(0, 2)
                                                    + fullName[1].substring(0, 2) +
                                                    (152 + listFullNames.indexOf(str))))
                                    .role(UserRole.ADMIN)
                                    .build())
                            .build());
        }
        return admins;
    }

    private String getStaffPosition(int numbOfElement) {
        String[] positions = {"Rector", "Deputy Rector", "Dean", "Deputy Dean", "Head of department"};
        return positions[numbOfElement];
    }

    //    Ready
    public List<Student> separateStudentsIntoGroups(List<Student> listStudent) {
        int minStudents = 15;
        int maxStudents = 31;
        List<Student> students = new LinkedList<>(listStudent);
        Collections.copy(students, listStudent);
        List<Student> studentsWithGroups = new LinkedList<>();
        List<Group> groups = new LinkedList<>();
        while (!students.isEmpty()) {
            Group group = createGroupName(groups);
            groups.add(group);
            group.setId(groups.size());
            int studentsInGroup = rand.nextInt(minStudents, maxStudents);
            if (students.size() < studentsInGroup) {
                studentsInGroup = students.size();
            }
            for (int i = 0; i < studentsInGroup; i++) {
                int randomStudent = rand.nextInt(0, students.size());
                Student student = students.get(randomStudent);
                students.remove(student);
                student.setGroup(group);
                studentsWithGroups.add(student);
            }
        }
        return studentsWithGroups;
    }

    //    Ready
    private Group createGroupName(List<Group> groups) {
        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 2;
        Group group = new Group();
        do {
            String generatedString = rand.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString()
                    .toUpperCase().concat("-" + rand.nextInt(10, 98));
            group.setName(generatedString);
        } while (groups.contains(group));
        return group;
    }

    //    Ready
    public List<Lecturer> separateLecturersToCourses(List<Lecturer> listLecturer, List<Course> listCourses) {
        int minListLim = 0;
        int maxCourseListLim = 24;
        List<Lecturer> lecturers = new LinkedList<>(listLecturer);
        List<Lecturer> lecturersWithCourses = new LinkedList<>();
        Collections.copy(lecturers, listLecturer);
        while (!lecturers.isEmpty()) {
            Lecturer lecturer = lecturers.get(rand.nextInt(minListLim, lecturers.size()));
            int randomCourse = rand.nextInt(minListLim, maxCourseListLim);
            lecturers.remove(lecturer);
            lecturer.setCourse(listCourses.get(randomCourse));
            lecturersWithCourses.add(lecturer);
        }
        return lecturersWithCourses;
    }

    //    Ready
    public List<Course> createCoursesFromFile() {
        List<String> list = FileReading.getAsList("src/main/resources/ua/foxminded/university/Courses.txt");
        List<Course> courses = new LinkedList<>();

        for (String string : list) {
            String[] parameters = string.split("\\|");
            int num = Integer.parseInt(parameters[0]);
            courses.add(new Course(num, parameters[1], parameters[2]));
        }

        return courses;
    }


    public List<Group> getGroupsFromStudentsList(List<Student> students) {
        Group group;
        List<Group> groups = new LinkedList<>();
        for (Student student : students) {
            group = student.getGroup();
            if (!groups.contains(group)) {
                groups.add(group);
            }
        }
        return groups;
    }

    public List<String> selectRandomFullNames(List<String> fullNames, int requiredCount) {
        List<String> selectedFullNames = new LinkedList<>();
        int count = 0;
        while (selectedFullNames.size() < requiredCount) {
            count += rand.nextInt(100, 300);
            if (count >= fullNames.size()) {
                count = 0;
            }
            selectedFullNames.add(fullNames.get(count));
        }
        List<String> result = List.copyOf(selectedFullNames);
        fullNames.removeAll(result);
        return result;
    }


    //    New unchecked methods

    public List<Group> assignGroupsToCourses(List<Group> groups, List<Course> courses) {
        List<Group> copiedGroupsList = new LinkedList<>(groups);
        List<Group> result = new LinkedList<>(groups);
        Collections.copy(copiedGroupsList, groups);
        List<Group> littleGroup;
        while (!copiedGroupsList.isEmpty()) {
            int groupsNumber = (copiedGroupsList.size() > 10)
                    ? rand.nextInt(10, 21) : copiedGroupsList.size();
            littleGroup = new LinkedList<>();
            for (int i = 0; i < groupsNumber; i++) {
                int randomIndex = (copiedGroupsList.size() > 2)?(rand.nextInt(0, copiedGroupsList.size())):0;
                if (copiedGroupsList.size() != 0) {
                    Group group = copiedGroupsList.get(randomIndex);
                    littleGroup.add(group);
                    copiedGroupsList.remove(group);
                } else {
                    i = groupsNumber;
                }
            }
            List<Course> coursesForGroupOfGroups = selectCourses(courses);
            for (Group group : littleGroup) {
                group.setCourses(coursesForGroupOfGroups);
            }
            result.addAll(littleGroup);
        }
        return result;
    }

    public List<Course> selectCourses(List<Course> coursesList) {
        int coursesAmount = rand.nextInt(4, 7);
        List<Course> courses = new LinkedList<>();
        int randomCourseIndex = 0;
        for (int i = 0; i < coursesAmount; i++) {
            randomCourseIndex = rand.nextInt(0, coursesList.size());
            while (courses.contains(coursesList.get(randomCourseIndex))) {
                randomCourseIndex = rand.nextInt(0, coursesList.size());
            }
            courses.add(coursesList.get(randomCourseIndex));
        }
        return courses;
    }

    private void setIdsForEachLectures(List<Lecture> lectures) {
        int id = 1;
        for (Lecture lecture : lectures) {
            lecture.setId(id);
            id++;
        }
    }

    private Lecture createLectureEntity(List<Group> groupList, Course course) {
        Lecture lecture = new Lecture();
        lecture.setCourse(course);
        lecture.setGroups(groupList);
        return setLectureTime(lecture);
    }

    private Lecture setLectureTime(Lecture lecture) {
        int[] dayPair = getDayAndPair(rand.nextInt(0, 20));
        lecture.setDayNumber(dayPair[0]);
        lecture.setPair(dayPair[1]);
        return lecture;
    }


    public static int[] getDayAndPair(int number) {
        int day = number / 4 + 1;
        int pair = number % 4 + 1;
        return new int[]{day, pair};
    }

    private List<List<Group>> getGroupedGroupsForOneLecture(List<Group> aGroups) {
        List<Group> totalGroups = new LinkedList<>();
        totalGroups.addAll(aGroups);
        List<Group> groupsForLecture;
        List<List<Group>> result = new LinkedList<>();
        List<Group> groupsOnOtherLectures = new LinkedList<>();

        while (!totalGroups.isEmpty()) {
            int randomAmount = rand.nextInt(1, 4);
            groupsForLecture = new LinkedList<>();
            for (int i = 1; i <= randomAmount; i++) {
                int randomGroupIndex = rand.nextInt(0, totalGroups.size());
                Group g = totalGroups.get(randomGroupIndex);

                while ((groupsForLecture.contains(g)) && (!totalGroups.isEmpty())) {
                    randomGroupIndex = rand.nextInt(0, totalGroups.size());
                    g = totalGroups.get(randomGroupIndex);
                }
                groupsForLecture.add(g);
                totalGroups.remove(g);
                if (totalGroups.isEmpty())
                    break;
            }
            groupsForLecture.forEach(groupsOnOtherLectures::add);
            result.add(groupsForLecture);
        }
        return result;
    }

    private List<String> getFreeGroupPairs(List<Lecture> lecturesOfGroup) {
        List<String> occupiedGroupPairs = getOccupiedGroupPairs(lecturesOfGroup);
        List<String> allGroupPairsForAWeek = getAllPairsTimeAsStringList();
        List<String> freeGroupPairs = new ArrayList<>();
        allGroupPairsForAWeek.removeAll(occupiedGroupPairs);
        freeGroupPairs = allGroupPairsForAWeek;
        return freeGroupPairs;
    }

    private List<String> getOccupiedGroupPairs(List<Lecture> lectures) {
        List<String> occupiedLectures = new ArrayList<>();
        for (Lecture lecture : lectures) {
            String string = lecture.getDayNumber() + "|" + lecture.getPair();
            if (!occupiedLectures.contains(string)) {
                occupiedLectures.add(string);
            }
        }
        return occupiedLectures;
    }

    private List<String> getAllPairsTimeAsStringList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int[] dayPair = getDayAndPair(i);
            result.add(dayPair[0] + "|" + dayPair[1]);
        }
        return result;
    }

    public List<Lecture> createLecturesByGroups(List<Group> groups) {
        List<Group> copiedGroups = new LinkedList<>(groups);
        Collections.copy(copiedGroups, groups);
        List<Group> groupsWithSameCourses = new LinkedList<>();
        List<Lecture> result = new LinkedList<>();
        while (!copiedGroups.isEmpty()) {
            Group group = copiedGroups.get((copiedGroups.size() > 1) ? rand.nextInt(0, copiedGroups.size() - 1) : 0);
            groupsWithSameCourses = group.getGroupsWithSameCourses(copiedGroups);
            copiedGroups.removeAll(groupsWithSameCourses);
            List<List<Group>> groupedGroups = getGroupedGroupsForOneLecture(groupsWithSameCourses);
            List<Lecture> lectures = createLecturesForEachCourses(groupedGroups, group.getCourses());
            result.addAll(lectures);
        }
        setIdsForEachLectures(result);
        return result;
    }

    private List<Lecture> createLecturesForEachCourses(List<List<Group>> groups, List<Course> courseList) {
        List<Lecture> lectures = new LinkedList<>();
        for (List<Group> groupList : groups) {
            List<String> freeGroupPairs = getFreeGroupPairs(lectures);
            for (Course course : courseList) {
                for (int i = 0; i < rand.nextInt(2, 4); i++) {
                    if (freeGroupPairs.size() > 1) {
                        Lecture lecture = createLectureEntity(groupList, course);

                        freeGroupPairs = getFreeGroupPairs(lectures);
                        String[] str = freeGroupPairs.get(rand.nextInt(freeGroupPairs.size())).split("\\|");
                        int[] dayPair = new int[]{Integer.parseInt(str[0]), Integer.parseInt(str[1])};

                        lecture.setDayNumber(dayPair[0]);
                        lecture.setPair(dayPair[1]);
                        lectures.add(lecture);
                    }
                }
            }
        }
        lectures.sort(Lecture.getDayComparator().thenComparing(Lecture.getPairComparator()));
        return lectures;
    }
}