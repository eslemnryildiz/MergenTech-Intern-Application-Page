package mergentech.internship.entities.concretes;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mergentech.internship.business.abstracts.ApplicationService;


import java.io.Serializable;
import java.util.List;

@Table(name = "applications")
@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
public class Application implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nameSurname")
    private String nameSurname;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "university")
    private String university;

    @Column(name = "department")
    private String department; //bölüm

    @Column(name = "grade")
    private String grade; //sınıf

    @Column(name = "linkedinLink")
    private String linkedinLink;

    @Column(name = "githubLink")
    private String githubLink;

    @Column(name = "cvLink")
    private String cvLink;

    @Column(name = "day1")
    private boolean day1; //1 Temmuz - 29 Temmuz

    @Column(name = "day2")
    private boolean day2;      //5 Ağustos - 2 Eylül

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<LanguageLevel> languageLevels;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<FieldApplication> fieldApplications;
}
