package mergentech.internship.business.concretes;

import lombok.AllArgsConstructor;
import mergentech.internship.business.abstracts.EmailService;
import mergentech.internship.business.requests.SendEmailRequest;
import mergentech.internship.business.responses.GetApprovedApplicationsResponse;
import mergentech.internship.business.responses.GetRejectedApplicationsResponse;
import mergentech.internship.business.responses.GetSpareApplicationsResponse;
import mergentech.internship.core.utilities.mappers.ModelMapperService;
import mergentech.internship.dataAccess.abstracts.ApplicationRepository;
import mergentech.internship.entities.concretes.Application;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmailManager implements EmailService {

    private ModelMapperService modelMapperService;
    private ApplicationRepository applicationRepository;
    private final JavaMailSender mailSender;

    private final String senderMail = "yapayzekaicinactim@gmail.com";
    private final String mailTitle = "Mergen Tech Staj Başvuru Sonucu";

    @Override
    public String sendEmail(SendEmailRequest request, EmailType emailType) {
        Optional<Application> applicantOptional = applicationRepository.findById(request.getApplicationId());

        if (applicantOptional.isPresent()) {
            Application application = applicantOptional.get();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderMail);
            message.setTo(application.getEmail());
            message.setSubject(mailTitle);

            String emailBody = generateEmailBody(application.getNameSurname(), emailType);
            message.setText(emailBody);

            try {
                mailSender.send(message);
                return "Eposta gönderildi";
            } catch (MailException ex) {
                return "Eposta gönderilemedi: " + ex.getMessage();
            }
        } else {
            return "Eposta gönderilemedi, uygulama bulunamadı.";
        }
    }

    private String generateEmailBody(String nameSurname, EmailType emailType) {
        switch (emailType) {
            case APPROVAL:
                return "Sayın " + nameSurname + ",\n" +
                        "\n" +
                        "Mergen Yazılım olarak yapmış olduğunuz staj başvurunuzun onaylandığını memnuniyetle bildirmek isteriz. Başvurunuzu dikkatle değerlendirdik ve şirketimizde staj yapma fırsatını size sunmaktan mutluluk duyuyoruz.\n" +
                        "\n" +
                        "Staj sürecinizle ilgili detaylar ve gerekli bilgiler hakkında kısa süre içerisinde sizi bilgilendireceğiz. Staj başlangıç tarihi, görevleriniz ve diğer ayrıntılar hakkında e-posta yoluyla bilgilendirme yapılacaktır.\n" +
                        "\n" +
                        "Herhangi bir sorunuz olursa bizimle iletişime geçmekten çekinmeyin. Sizi ekibimizde görmek için sabırsızlanıyoruz.\n" +
                        "\n" +
                        "Saygılarımızla,\n" +
                        "\n" +
                        "Mergen Yazılım";

            case REJECTION:
                return "Sayın " + nameSurname + ",\n" +
                        "\n" +
                        "Mergen Yazılım olarak, staj başvurunuzu incelediğimizi ve değerlendirme sürecini tamamladığımızı bildirmek isteriz. Ne yazık ki, bu seferki başvurunuz için olumsuz bir karar vermek durumundayız.\n" +
                        "\n" +
                        "Başvurunuzda gösterdiğiniz ilgi ve çaba için teşekkür ederiz. Ancak, mevcut şartlar ve ihtiyaçlarımız doğrultusunda başka adayları tercih etmek zorunda kaldık.\n" +
                        "\n" +
                        "İleride farklı pozisyonlar veya staj olanakları için tekrar başvuru yapmanızı memnuniyetle karşılayacağımızı belirtmek isteriz. Kariyerinizde başarılar dileriz.\n" +
                        "\n" +
                        "Herhangi bir sorunuz olursa bizimle iletişime geçmekten çekinmeyin.\n" +
                        "\n" +
                        "Saygılarımızla,\n" +
                        "\n" +
                        "Mergen Yazılım";

            case SPARE:
                return "Sayın " + nameSurname + ",\n" +
                        "\n" +
                        "Mergen Yazılım olarak yapmış olduğunuz staj başvurusunu incelediğimizi ve değerlendirme sürecini tamamladığımızı memnuniyetle bildiririz. Başvurunuz çok olumlu değerlendirildi, ancak mevcut kontenjanlarımız dolduğu için bu aşamada size kesin bir yer sunamıyoruz.\n" +
                        "\n" +
                        "Başvurunuzu yedek listeye aldık ve bir boşluk oluşması durumunda size öncelik tanıyacağımızı belirtmek isteriz. Bu tür bir durum oluştuğunda, sizinle en kısa sürede iletişime geçeceğiz.\n" +
                        "\n" +
                        "Başvurunuz ve gösterdiğiniz ilgi için teşekkür eder, ileride farklı fırsatlarda tekrar başvuru yapmanızı memnuniyetle karşılayacağımızı belirtmek isteriz.\n" +
                        "\n" +
                        "Herhangi bir sorunuz olursa bizimle iletişime geçmekten çekinmeyin.\n" +
                        "\n" +
                        "Saygılarımızla,\n" +
                        "\n" +
                        "Mergen Yazılım";

            default:
                return "";
        }
    }

    public enum EmailType {
        APPROVAL,
        REJECTION,
        SPARE
    }
}
