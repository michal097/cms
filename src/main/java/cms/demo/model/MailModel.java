package cms.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;

@Entity
@Data
@NoArgsConstructor
public class MailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mailId;
    @Email
    private String mailAdress;

    public MailModel(String mailAdress){
        this.setMailAdress(mailAdress);
    }
    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress.trim();
    }
}
