package server.filters;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public class AchievementFilter {
    @Nullable
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateFrom;

    @Nullable
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateTo;

    @Nullable
    private Long studentId;

    @Nullable
    private Long groupId;

    @Nullable
    private Long professorId;

    @Nullable
    private Long formOfControlId;

    @Nullable
    private Integer semester;


    @Nullable
    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(@Nullable LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Nullable
    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(@Nullable LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Nullable
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(@Nullable Long studentId) {
        this.studentId = studentId;
    }

    @Nullable
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(@Nullable Long groupId) {
        this.groupId = groupId;
    }

    @Nullable
    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(@Nullable Long professorId) {
        this.professorId = professorId;
    }

    @Nullable
    public Long getFormOfControlId() {
        return formOfControlId;
    }

    public void setFormOfControlId(@Nullable Long formOfControlId) {
        this.formOfControlId = formOfControlId;
    }

    @Nullable
    public Integer getSemester() {
        return semester;
    }

    public void setSemester(@Nullable Integer semester) {
        this.semester = semester;
    }
}
