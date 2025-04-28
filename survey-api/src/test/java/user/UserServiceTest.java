package user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kong.user.entity.UserEntity;
import org.kong.user.repository.UserRepository;
import org.kong.user.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("존재하지 않는 사용자에 접근")
    public void getUser_Exception() {
        // given
        when(userRepository.findByUserId(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.findUserById(1));

        // then
        assertThat(exception.getMessage()).isEqualTo("사용자를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("정상 접근 - 사용자 정보 조회")
    public void getUser_Success() {
        // given
        String userName = "사용자1";
        String userNickName = "사용자1";

        UserEntity mockitoUser = new UserEntity(userName, userNickName, "USER");
        when(userRepository.findByUserId(1)).thenReturn(Optional.of(mockitoUser));

        // when
        UserEntity result = userService.findUserById(1);
        //then
        assertThat(mockitoUser).isNotNull();
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findByUserId(1);


    }
}
