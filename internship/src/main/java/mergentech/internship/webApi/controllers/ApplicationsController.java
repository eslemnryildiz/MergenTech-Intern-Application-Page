package mergentech.internship.webApi.controllers;

import lombok.AllArgsConstructor;
import mergentech.internship.business.abstracts.ApplicationService;
import mergentech.internship.business.requests.CreateApplicationRequest;
import mergentech.internship.business.requests.StatusRequest;
import mergentech.internship.business.responses.GetApplicationInformationsResponse;
import mergentech.internship.business.responses.GetByIdApplicationResponse;
import mergentech.internship.dataAccess.abstracts.ApplicationRepository;
import mergentech.internship.entities.concretes.Application;
import mergentech.internship.restpostparameter.applicationRestPostParameters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/applications")
public class ApplicationsController {

    private ApplicationService applicationService;

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void add(@RequestBody CreateApplicationRequest createApplicationRequest){
        this.applicationService.add(createApplicationRequest);
    }

    @PostMapping("/getById")
    public GetByIdApplicationResponse getById(@RequestBody int id){
        return applicationService.getById(id);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody int id){
        this.applicationService.delete(id);
    }


    @PostMapping("/status")
    public List<GetApplicationInformationsResponse> getApplicationsByStatus(@RequestBody int statusId) {
        return applicationService.getApplicationsByStatus(statusId);
    }

    @PutMapping("/updateStatus")
    public void updateStatus(@RequestBody applicationRestPostParameters parameter) {
        applicationService.updateStatus(parameter.getId(), parameter.getStatus());
    }

    @GetMapping("/informations")
    public List<GetApplicationInformationsResponse> getApplicationInformations() {
        return applicationService.getApplicationInformations();
    }

}
