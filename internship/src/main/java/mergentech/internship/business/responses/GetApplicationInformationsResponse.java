package mergentech.internship.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationInformationsResponse {

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
    private boolean day1;
    private boolean day2;

    private StatusResponse status;
    private List<FieldResponse> fields;
    private List<LanguageLevelResponse> languageLevels;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminResponse {
        private int id;
        private String username;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StatusResponse {
        private int id;
        private String statusName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldResponse {
        private int id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LanguageLevelResponse {
        private int languageId;
        private String languageName;
        private int levelId;
        private String levelName;
    }
}
