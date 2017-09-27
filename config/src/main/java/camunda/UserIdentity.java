package camunda;

import lombok.Data;

import java.util.List;

@Data
public class UserIdentity {

    List<UserGroup> groups;
}
