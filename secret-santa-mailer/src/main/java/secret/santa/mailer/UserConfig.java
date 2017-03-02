package secret.santa.mailer;

import lombok.Data;
import lombok.experimental.Accessors;

@Data(staticConstructor = "with")
@Accessors(chain = true)
public class UserConfig
{
    private String firstName;
    private String lastName;
    private String email;
    
    private UserConfig nominee;
    
    @Override
    public String toString() {
        return "UserConfig=[firstName=[" + firstName + "], lastName=[" + lastName + "], email=[" + email + "], nominee=[" + (nominee != null ? nominee.firstName + " " + nominee.lastName : "None") +  "]]";
    }
}
