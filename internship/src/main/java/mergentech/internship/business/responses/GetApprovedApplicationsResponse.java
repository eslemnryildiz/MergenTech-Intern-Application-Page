package mergentech.internship.business.responses;

import java.util.List;

public class GetApprovedApplicationsResponse {

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

    private String date;

    private String status;

    // Assuming you want to include lists of related entities as well
    private List<String> periodDates;
    private List<String> fields;
    private List<String> languages;
    private List<String> emails;
}
