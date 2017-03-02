package secret.santa.mailer;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.ArrayUtils;

import secret.santa.mailer.Mailer.MailerAddress;

public class Main
{
    private static final Mailer MAILER = Mailer.Builder.with().host("mspxrly01.mspx.app").port("25").build();
    
    public static void main(String [] args) throws Exception {
        if (ArrayUtils.isEmpty(args)) {
            System.out.println("Expected Args: [CONFIRM|ALLOCATE|REMIND|TODAY]");
            System.exit(0);
        }
            
        new Main().go(args[0]);
    }
    
    private void go(final String action) throws Exception {
        switch (action){
            case "CONFIRM":
                processConfirmation(ListUtils.union(secretSanta5, secretSanta10));
                break;
            case "ALLOCATE":
                processAllocation(secretSanta5, "£5");
                processAllocation(secretSanta10, "£10");
                break;
            case "REMIND":
                processReminder(ListUtils.union(secretSanta5, secretSanta10));
                break;
            case "TODAY":
                processToday(ListUtils.union(secretSanta5, secretSanta10));
                break;
            default:
                System.out.println("UNKNOWN_ACTION=[" + action + "]");
        }
    }

    private void processToday(final List<UserConfig> users) {
        users.forEach(u -> {
            MAILER.compose().from(MailerAddress.email("slh").alias("Santa's Little Helper"))
                            .to(MailerAddress.email(u.getEmail()))
                            .subject("Dev/Test Christmas Lunch - Secret Santa - TODAY")
                            .body(emailToday.writeString(FluentMap.generic().keyValue("user", u)))
                            .mimeSubType("html")
                            .send();
        });
    }
    
    private void processConfirmation(final List<UserConfig> users) {
        users.forEach(u -> {
            MAILER.compose().from(MailerAddress.email("slh").alias("Santa's Little Helper"))
                            .to(MailerAddress.email(u.getEmail()))
                            .subject("Dev/Test Christmas Lunch - Secret Santa - Confirmation")
                            .body(emailConfirmation.writeString(FluentMap.generic().keyValue("user", u)))
                            .mimeSubType("html")
                            .send();
        });
    }
    
    private void processAllocation(final List<UserConfig> users, final String spendingLimit) {
        Collections.shuffle(users);
        
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setNominee(users.get(i == users.size() - 1 ? 0 : i + 1)); 
        }
        
        users.forEach(u -> {
            MAILER.compose().from(MailerAddress.email("slh").alias("Santa's Little Helper"))
                            .to(MailerAddress.email(u.getEmail()))
                            .subject("Dev/Test Christmas Lunch - Secret Santa - Allocation")
                            .body(emailAllocation.writeString(FluentMap.generic().keyValue("user", u).keyValue("spendingLimit", spendingLimit)))
                            .mimeSubType("html")
                            .send();
        });
    }
    
    private void processReminder(final List<UserConfig> users) {
        users.forEach(u -> {
            MAILER.compose().from(MailerAddress.email("slh").alias("Santa's Little Helper"))
                            .to(MailerAddress.email(u.getEmail()))
                            .subject("Dev/Test Christmas Lunch - Secret Santa - Reminder")
                            .body(emailReminder.writeString(FluentMap.generic().keyValue("user", u)))
                            .mimeSubType("html")
                            .send();
        });
    }
    
    private final FluentFreemarker emailTest = FluentFreemarker.Builder.with().template("/email_test.html").build();
    private final FluentFreemarker emailConfirmation = FluentFreemarker.Builder.with().template("/email_secret_santa_confirmation.html").build();
    private final FluentFreemarker emailAllocation = FluentFreemarker.Builder.with().template("/email_secret_santa_allocation.html").build();
    private final FluentFreemarker emailReminder = FluentFreemarker.Builder.with().template("/email_secret_santa_reminder.html").build();
    private final FluentFreemarker emailToday = FluentFreemarker.Builder.with().template("/email_secret_santa_today.html").build();

    private final List<UserConfig> test1 = FluentList.<UserConfig>with()
            .item(UserConfig.with().setFirstName("A").setLastName("B").setEmail("A.B@test.com"));

    private final List<UserConfig> test2 = FluentList.<UserConfig>with()
            .item(UserConfig.with().setFirstName("C").setLastName("D").setEmail("C.D@test.com"));
    
    private final List<UserConfig> secretSanta5 = FluentList.<UserConfig>with()
            .item(UserConfig.with().setFirstName("U").setLastName("V").setEmail("U.V@test.com"));
    
    private final List<UserConfig> secretSanta10 = FluentList.<UserConfig>with()
            .item(UserConfig.with().setFirstName("W").setLastName("X").setEmail("W.X@test.com"));
    
    private final List<UserConfig> secretSantaOut = FluentList.<UserConfig>with()
            .item(UserConfig.with().setFirstName("Y").setLastName("Z").setEmail("Y.Z@test.com"));

}
