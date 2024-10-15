package mergentech.internship.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdApplicationResponse {


    private int id;
    private String nameSurname;
    private String email;
    private String telephone;
    private String university;
    private String department;
    private String grade;
    private String linkedinLink;
    private String githubLink;
    private String cvLink;
    private Boolean day1;
    private Boolean day2;
    private String status;

    // Adding lists for related entities
    private List<String> fields;
    private List<String> languages;
}
