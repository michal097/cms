package cms.demo.repos;

import cms.demo.model.MailModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MailRepo extends CrudRepository<MailModel,Long> {
    List<MailModel> findAll();
}
