package mergentech.internship.dataAccess.abstracts;

import mergentech.internship.business.responses.GetApplicationInformationsResponse;
import mergentech.internship.entities.concretes.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    @Query("SELECT a FROM Application a WHERE a.status.id = :statusId")
    List<Application> findApplicationsByStatus(@Param("statusId") int statusId);
}
