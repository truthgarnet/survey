package admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kong.admin.dto.AdminLoginRequest;
import org.kong.admin.dto.AdminLoginResponse;
import org.kong.admin.dto.AdminUsersResponse;
import org.kong.admin.entity.AdminEntity;
import org.kong.admin.facade.AdminFacade;
import org.kong.admin.mapper.AdminMapper;
import org.kong.admin.service.AdminService;
import org.kong.exception.CustomException;
import org.kong.exception.ErrorCode;
import org.kong.survey.dto.PageDto;
import org.kong.user.entity.UserEntity;
import org.kong.user.mapper.UserMapper;
import org.kong.user.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class AdminFacadeTest {

    @Mock
    private AdminService adminService;

    @Mock
    private UserService userService;

    @Mock
    private AdminMapper adminMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AdminFacade adminFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_success() {
        // given
        AdminLoginRequest request = new AdminLoginRequest("admin", "1234");
        
        AdminEntity entity = new AdminEntity();
        entity.setAdminId("admin");
        entity.setPassword("1234");

        AdminLoginResponse response = new AdminLoginResponse("admin", "관리자");

        when(adminService.findByAdminId(request)).thenReturn(entity);
        when(adminMapper.toAdminLoginResponse(entity)).thenReturn(response);

        // when
        AdminLoginResponse result = adminFacade.login(request);

        // then
        assertThat(result).isEqualTo(response);
        verify(adminService).findByAdminId(request);
        verify(adminMapper).toAdminLoginResponse(entity);
    }

    @Test
    void login_fail_wrong_password() {
        // given
        AdminLoginRequest request = new AdminLoginRequest("admin", "wrongpassword");
        AdminEntity entity = new AdminEntity();
        entity.setAdminId("admin");
        entity.setPassword("correctpassword");

        when(adminService.findByAdminId(request)).thenReturn(entity);

        // when & then
        assertThrows(CustomException.class, () -> adminFacade.login(request));

        verify(adminService).findByAdminId(request);
        verify(adminMapper, never()).toAdminLoginResponse(any());
    }

    @Test
    void getUsers_success() {
        // given
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();

        Page<UserEntity> userPage = new PageImpl<>(List.of(user1, user2));
        PageDto<AdminUsersResponse> expectedResponse = new PageDto<>();

        when(userService.findAll(0, 10)).thenReturn(userPage);
        when(userMapper.toAdminUsersResponse(userPage)).thenReturn(expectedResponse);

        // when
        PageDto<AdminUsersResponse> result = adminFacade.getUsers();

        // then
        assertThat(result).isEqualTo(expectedResponse);
        verify(userService).findAll(0, 10);
        verify(userMapper).toAdminUsersResponse(userPage);
    }
}