package WebPatterns.business;

import lombok.*;
/**
 * @author Julie
 *
 */
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
@NoArgsConstructor
public class User {

    //private int userid;
    @EqualsAndHashCode.Include
    private String username;
    @ToString.Exclude
    private String password;
    private String firstname;
    private String lastname;
    //@NonNull
    private String email;
}
