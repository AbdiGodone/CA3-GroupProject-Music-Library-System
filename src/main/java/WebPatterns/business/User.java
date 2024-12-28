package WebPatterns.business;
/**
 *
 * @author Julie
 */
import lombok.*;
// Add getter methods
@Getter
// Add a toString method
@ToString
// Add equals and hashcode methods - only include the specifically noted variables
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
// Add the ability to build object with any components in any order
@Builder
// Add an all-args constructor
@AllArgsConstructor

public class User {

    private int userid;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String firstname;
    private String lastname;
    @NonNull
    private String email;
}
