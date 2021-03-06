package guan.suns.service;

import guan.suns.exception.*;
import guan.suns.model.CourseSelectionPDM;
import guan.suns.model.StudentPDM;
import guan.suns.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by lenovo on 2016/5/9.
 */

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseService courseService;

    @Override
    public boolean createStudent(StudentPDM student) throws UserExistedException, UserInfoErrorException {

        if(student == null
                || student.getPassword() == null
                || student.getPassword().equals("")
                || student.getName() == null
                || student.getName().equals("")
                || student.getStudentID() == null
                || student.getStudentID().length() != 10
                || student.getEnrolledAge() < 10
                || student.getEnrolledAge() > 50
                )
            throw new UserInfoErrorException();

        StudentPDM newStudent = studentRepository.findOne(student.getStudentID());
        if(newStudent != null) throw new UserExistedException();

        studentRepository.save(student);

        return true;
    }

    @Override
    public boolean deleteStudent(StudentPDM student) throws UserNotFoundException {

        if(student == null
                || student.getStudentID() == null
                || student.getStudentID().length() != 10
                )
            return false;

        StudentPDM deleteStudent = studentRepository.findOne(student.getStudentID());
        if(deleteStudent == null) throw new UserNotFoundException();

        for(CourseSelectionPDM course : deleteStudent.getSelectedCourses()){
            if(course!=null){
                try {
                    courseService.dropCourse(course);
                }
                catch (CourseNotSelectedException courseNotSelectedException){
                    courseNotSelectedException.printStackTrace();
                }
                catch (CourseSelectionInfoError courseSelectionInfoErrorException){
                    courseSelectionInfoErrorException.printStackTrace();
                }
            }
        }

        studentRepository.delete(deleteStudent);

        return true;
    }

    @Override
    public StudentPDM loginStudent(StudentPDM student) throws UserNotFoundException, PasswordErrorException {

        if(student == null
                || student.getStudentID() == null
                || student.getStudentID().length() != 10
                || student.getPassword()==null
                || student.getPassword().isEmpty()
                ){
            throw new UserNotFoundException();
        }

        StudentPDM getStudent = studentRepository.findOne(student.getStudentID());

        if(getStudent==null){
            throw new UserNotFoundException();
        }
        if(!getStudent.getPassword().equals(student.getPassword())){
            throw new PasswordErrorException();
        }

        StudentPDM returnStudent = new StudentPDM(getStudent.getStudentID(),getStudent.getPassword(),getStudent.getName(),getStudent.getGender(),getStudent.getClassName(),getStudent.getDepartment(),getStudent.getEnrolledAge(),getStudent.getEnrolledTime());

        return returnStudent;
    }

    @Override
    public StudentPDM getStudentDetail(StudentPDM student) throws UserNotFoundException, UserInfoErrorException {

        if(student == null
                || student.getStudentID() == null
                || student.getStudentID().length() != 10
                ){
            throw new UserInfoErrorException();
        }

        StudentPDM getStudent = studentRepository.findOne(student.getStudentID());

        if(getStudent==null){
            throw new UserNotFoundException();
        }

        StudentPDM returnStudent = new StudentPDM(getStudent.getStudentID(),getStudent.getPassword(),getStudent.getName(),getStudent.getGender(),getStudent.getClassName(),getStudent.getDepartment(),getStudent.getEnrolledAge(),getStudent.getEnrolledTime());
        returnStudent.setSelectedCourses(getStudent.getSelectedCourses());

        return returnStudent;
    }

    @Override
    public boolean updateStudent(StudentPDM student) throws UserNotFoundException, UserInfoErrorException {
        if(student == null
                || student.getPassword() == null
                || student.getPassword().equals("")
                || student.getName() == null
                || student.getName().equals("")
                || student.getStudentID() == null
                || student.getStudentID().length() != 10
                || student.getEnrolledAge() < 10
                || student.getEnrolledAge() > 50
                )
            throw new UserInfoErrorException();

        StudentPDM newStudent = studentRepository.findOne(student.getStudentID());
        if(newStudent == null) throw new UserNotFoundException();

        newStudent.setClassName(student.getClassName());
        newStudent.setEnrolledAge(student.getEnrolledAge());
        newStudent.setGender(student.getGender());
        newStudent.setEnrolledTime(student.getEnrolledTime());
        newStudent.setDepartment(student.getDepartment());
        newStudent.setPassword(student.getPassword());
        newStudent.setName(student.getName());

        studentRepository.save(newStudent);

        return true;
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByName(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getName()==null
                )
            throw new QueryInfoError();

        return studentRepository.findByName(student.getName());
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByDepartment(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getDepartment()==null
                )
            throw new QueryInfoError();

        return studentRepository.findByDepartment(student.getDepartment());
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByClassName(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getClassName()==null
                )
            throw new QueryInfoError();

        return studentRepository.findByClassName(student.getClassName());
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByNameAndDepartment(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getName()==null
                || student.getDepartment() == null
                )
            throw new QueryInfoError();

        return studentRepository.findByNameAndDepartment(student.getName(),student.getDepartment());
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByNameAndClassName(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getName()==null
                || student.getClassName() == null
                )
            throw new QueryInfoError();

        return studentRepository.findByNameAndClassName(student.getName(),student.getClassName());
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByClassNameAndDepartment(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getDepartment() == null
                || student.getClassName() == null
                )
            throw new QueryInfoError();

        return studentRepository.findByClassNameAndDepartment(student.getClassName(),student.getDepartment());
    }

    @Override
    public ArrayList<StudentPDM> getStudentDetailByNameAndClassNameAndDepartment(StudentPDM student) throws QueryInfoError {
        if(student == null
                || student.getDepartment() == null
                || student.getClassName() == null
                || student.getName() == null
                )
            throw new QueryInfoError();

        return studentRepository.findByNameAndClassNameAndDepartment(student.getName(),student.getClassName(),student.getDepartment());
    }

    @Override
    public ArrayList<StudentPDM> getAllStudentsDetail() {
        ArrayList<StudentPDM> students = new ArrayList<>(studentRepository.findAll());
        return students;
    }
}
