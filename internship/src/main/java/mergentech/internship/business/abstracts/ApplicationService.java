package mergentech.internship.business.abstracts;

import mergentech.internship.business.requests.CreateApplicationRequest;
import mergentech.internship.business.responses.GetApplicationInformationsResponse;
import mergentech.internship.business.responses.GetByIdApplicationResponse;
import mergentech.internship.entities.concretes.Application;

import java.util.List;

public interface ApplicationService {

    GetByIdApplicationResponse getById(int id);

    void add(CreateApplicationRequest createApplicationRequest);
    void delete(int id);

    List<GetApplicationInformationsResponse> getApplicationsByStatus(int statusId);

    List<GetApplicationInformationsResponse> getApplicationInformations();
    //

    void updateStatus(int id, int newStatus);





}
