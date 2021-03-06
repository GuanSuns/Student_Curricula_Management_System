package guan.suns.response;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/6/5.
 */
public class StudentsStatisticsResponse {

    private Integer status;
    private String info;

    private Float avg;
    private ArrayList<StudentDetailResponse> students;

    public StudentsStatisticsResponse() {
    }

    public StudentsStatisticsResponse(Integer status, String info, Float avg, ArrayList<StudentDetailResponse> students) {
        this.status = status;
        this.info = info;
        this.avg = avg;
        this.students = students;
    }

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<StudentDetailResponse> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentDetailResponse> students) {
        this.students = students;
    }
}
