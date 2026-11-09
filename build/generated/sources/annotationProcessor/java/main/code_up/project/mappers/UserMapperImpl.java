package code_up.project.mappers;

import code_up.project.dtos.UserDto.UserRegisterRequest;
import code_up.project.dtos.UserDto.UserRegisterResponse;
import code_up.project.entity.User;
import code_up.project.shared.enums.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-02T16:03:22-0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-java-compiler-worker-9.8.1.jar, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserRegisterRequest toDto(User user) {
        if ( user == null ) {
            return null;
        }

        String username = null;
        String email = null;
        String password = null;
        Role roles = null;

        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        roles = user.getRoles();

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest( username, email, password, roles );

        return userRegisterRequest;
    }

    @Override
    public User toEntity(UserRegisterRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.username() );
        user.setEmail( dto.email() );
        user.setPassword( dto.password() );
        user.setRoles( dto.roles() );

        return user;
    }

    @Override
    public UserRegisterResponse toResponse(User dto) {
        if ( dto == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        String email = null;
        String password = null;
        Role roles = null;

        id = dto.getId();
        username = dto.getUsername();
        email = dto.getEmail();
        password = dto.getPassword();
        roles = dto.getRoles();

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse( id, username, email, password, roles );

        return userRegisterResponse;
    }

    @Override
    public List<UserRegisterResponse> toResponse(List<User> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<UserRegisterResponse> list = new ArrayList<UserRegisterResponse>( dtos.size() );
        for ( User user : dtos ) {
            list.add( toResponse( user ) );
        }

        return list;
    }
}
