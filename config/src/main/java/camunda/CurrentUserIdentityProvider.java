package camunda;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrentUserIdentityProvider {

    private static final ThreadLocal<UserIdentity> identityThreadLocal = new InheritableThreadLocal<>();

    public List<String> groupNames(UserIdentity userIdentity) {
        return userIdentity.getGroups().stream().map(UserGroup::getName).collect(Collectors.toList());
    }

    public void setCurrentIdentity() {
        UserIdentity userIdentity = new UserIdentity();
        List<UserGroup> groups = new ArrayList<>();
        UserGroup group = new UserGroup();
        group.setName("CREATOR");
        groups.add(group);
        userIdentity.setGroups(groups);
        identityThreadLocal.set(userIdentity);
    }

    public UserIdentity currentIdentity() {
        return identityThreadLocal.get();
    }

}
