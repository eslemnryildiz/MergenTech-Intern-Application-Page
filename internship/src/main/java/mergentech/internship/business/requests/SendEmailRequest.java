package mergentech.internship.business.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mergentech.internship.entities.concretes.Application;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendEmailRequest {

    @NotNull
    private int applicationId;
}
