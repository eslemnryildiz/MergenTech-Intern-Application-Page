package mergentech.internship.business.concretes;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import mergentech.internship.business.abstracts.ApplicationService;
import mergentech.internship.business.abstracts.EmailService;
import mergentech.internship.business.requests.CreateApplicationRequest;
import mergentech.internship.business.requests.SendEmailRequest;
import mergentech.internship.business.responses.GetApplicationInformationsResponse;
import mergentech.internship.business.responses.GetByIdApplicationResponse;
import mergentech.internship.core.utilities.mappers.ModelMapperService;
import mergentech.internship.dataAccess.abstracts.ApplicationRepository;
import mergentech.internship.entities.concretes.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationManager implements ApplicationService {

    private ApplicationRepository applicationRepository;
    private ModelMapperService modelMapperService;
    private EntityManager entityManager;
    private EmailService emailService;  // EmailService eklendi


    @Override
    public GetByIdApplicationResponse getById(int id) {

        Application application = this.applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // DTO'ya mapleme
        GetByIdApplicationResponse response = this.modelMapperService.forResponse().map(application, GetByIdApplicationResponse.class);

        // Boolean değere göre tarih ayarlama
        if (Boolean.TRUE.equals(application.isDay1())) {
            response.setDay1(true);
            response.setDay2(false);
        } else if (Boolean.TRUE.equals(application.isDay2())) {
            response.setDay1(false);
            response.setDay2(true);
        } else {
            response.setDay1(null);
            response.setDay2(null);
        }

        // Fields, languages, emails gibi ilişkili verileri manuel olarak map etme
        List<String> fields = application.getFieldApplications().stream()
                .map(fieldApplication -> fieldApplication.getField().getName())
                .collect(Collectors.toList());
        response.setFields(fields);

        List<String> languages = application.getLanguageLevels().stream()
                .map(languageLevel -> languageLevel.getLanguage().getLanguageName() + " - " + languageLevel.getLevel().getLevelName())
                .collect(Collectors.toList());
        response.setLanguages(languages);


        return response;
    }


    @Override
    public void add(CreateApplicationRequest createApplicationRequest) {
        // Temel application alanlarını map et
        Application application = this.modelMapperService.forRequest().map(createApplicationRequest, Application.class);

        // LanguageLevels ayarla
        List<LanguageLevel> languageLevels = createApplicationRequest.getLanguageLevels().stream().map(request -> {
            Language language = entityManager.find(Language.class, request.getLanguageId());
            Level level = entityManager.find(Level.class, request.getLevelId());

            LanguageLevel languageLevel = new LanguageLevel();
            languageLevel.setLanguage(language);
            languageLevel.setLevel(level);
            languageLevel.setApplication(application); // ana application'ı ayarla
            return languageLevel;
        }).collect(Collectors.toList());

        // Fields ayarla
        List<FieldApplication> fieldApplications = createApplicationRequest.getFields()
                .stream().map(request -> {
                    Field field = entityManager.find(Field.class, request.getId());

                    FieldApplication fieldApplication = new FieldApplication();
                    fieldApplication.setField(field);
                    fieldApplication.setApplication(application);
                    return fieldApplication;
                }).collect(Collectors.toList());

        application.setFieldApplications(fieldApplications);
        application.setLanguageLevels(languageLevels);

        // Application'ı ve ilişkili entity'leri kaydet
        this.applicationRepository.save(application);
    }


    @Override
    public void delete(int id) {
        this.applicationRepository.deleteById(id);
    }


    @Override
    public List<GetApplicationInformationsResponse> getApplicationsByStatus(int statusId) {
        List<Application> applications = applicationRepository.findApplicationsByStatus(statusId);
        return applications.stream()
                .map(application -> modelMapperService.forResponse().map(application, GetApplicationInformationsResponse.class))
                .collect(Collectors.toList());
    }



    @Override
    public void updateStatus(int id, int newStatusId) { if (newStatusId < 0) {
        throw new IllegalArgumentException("Invalid status ID: " + newStatusId);
    }
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        Status newStatus = entityManager.find(Status.class, newStatusId);
        if (newStatus == null) {
            throw new RuntimeException("Status not found");
        }

        application.setStatus(newStatus);


        applicationRepository.save(application);

        EmailManager.EmailType emailType;
        switch (newStatusId) {

            case 0:  // Onaylanmış statü ID'si
                emailType = EmailManager.EmailType.APPROVAL;
                break;
            case 1:  // Reddedilmiş statü ID'si
                emailType = EmailManager.EmailType.REJECTION;
                break;
            case 2:  // Yedek statü ID'si
                emailType = EmailManager.EmailType.SPARE;
                break;
            default:
                throw new IllegalArgumentException("Invalid status ID: " + newStatusId);
        }

        SendEmailRequest emailRequest = new SendEmailRequest();
        emailRequest.setApplicationId(id);
        emailService.sendEmail(emailRequest, emailType);
    }


    @Override
    public List<GetApplicationInformationsResponse> getApplicationInformations() {
        List<Application> applications = this.applicationRepository.findAll();

        return applications.stream()
                .map(application -> {
                    GetApplicationInformationsResponse response = this.modelMapperService.forResponse()
                            .map(application, GetApplicationInformationsResponse.class);

                    // Fields'ı manuel olarak set edelim
                    List<GetApplicationInformationsResponse.FieldResponse> fieldResponses = application.getFieldApplications().stream()
                            .map(fieldApplication -> new GetApplicationInformationsResponse.FieldResponse(fieldApplication.getField().getId(), fieldApplication.getField().getName()))
                            .collect(Collectors.toList());

                    response.setFields(fieldResponses);

                    return response;
                })
                .collect(Collectors.toList());
    }
    /*public donusturu methodadı (int id){
        return findApplicationsByStatus(id);
    }*/

}
