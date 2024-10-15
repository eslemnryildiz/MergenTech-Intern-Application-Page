package mergentech.internship.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mergentech.internship.business.responses.GetApplicationInformationsResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationRequest {

    @NotNull
    @NotBlank
    private String nameSurname;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String telephone;

    @NotNull
    @NotBlank
    private String university;

    @NotNull
    @NotBlank
    private String department;

    @NotNull
    @NotBlank
    private String grade;

    @NotNull
    @NotBlank
    private String linkedinLink;

    @NotNull
    @NotBlank
    private String githubLink;

    @NotNull
    @NotBlank
    private String cvLink;

    @NotNull
    @NotBlank
    private boolean day1;

    @NotNull
    @NotBlank
    private boolean day2;

    @NotNull
    @NotBlank
    private List<FieldRequest> fields;

    @NotNull
    @NotBlank
    private List<LanguageLevelRequest> languageLevels;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldRequest {
        private int id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LanguageLevelRequest {
        private int languageId;
        private int levelId;
    }

}
