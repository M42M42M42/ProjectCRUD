package integration.mapper;

import com.m42.custody.entity.UserEntity;
import com.m42.custody.mapper.UserMapper;
import integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserMapperIT extends BaseIntegrationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void should_insert_user_success() {
        UserEntity user = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();

        int rows = userMapper.insertUser(user);
        Long id = user.getId();

        assertThat(rows).isEqualTo(1);
        assertThat(id).isNotNull(); // 测试自动回填主键 id
    }

    @Test
    void should_insert_user_fail_when_email_duplicate() {
        UserEntity user1 = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("test_user_002")
                .password("123456")
                .email("test_user_001@test.com") // 故意重复的 email
                .build();

        userMapper.insertUser(user1);

        assertThatThrownBy(() -> userMapper.insertUser(user2))
                .isInstanceOf(DuplicateKeyException.class);
        List<UserEntity> allUsers = userMapper.selectUserList(new UserEntity());
        assertThat(allUsers).hasSize(1);
        assertThat(allUsers.getFirst().getUsername()).isEqualTo("test_user_001");
    }

    @Test
    void should_select_user_list_by_username_success() {
        UserEntity user1 = UserEntity.builder()
                .username("duplicated_username")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("duplicated_username")
                .password("123456")
                .email("test_user_002@test.com")
                .build();
        UserEntity user3 = UserEntity.builder()
                .username("test_user_003")
                .password("123456")
                .email("test_user_003@test.com")
                .build();
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        userMapper.insertUser(user3);

        UserEntity userToQuery = UserEntity.builder()
                .username("duplicated")
                .build();
        List<UserEntity> result = userMapper.selectUserList(userToQuery);

        assertThat(result).hasSize(2);
    }

    @Test
    void should_select_user_list_by_email_success() {
        UserEntity user1 = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("test_user_002")
                .password("123456")
                .email("test_user_002@test.com")
                .build();
        UserEntity user3 = UserEntity.builder()
                .username("test_user_003")
                .password("123456")
                .email("test_user_003@test.com")
                .build();
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        userMapper.insertUser(user3);

        UserEntity userToQuery = UserEntity.builder()
                .email("test_user_003@test.com")
                .build();
        List<UserEntity> result = userMapper.selectUserList(userToQuery);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getUsername()).isEqualTo("test_user_003");
    }

    @Test
    void should_delete_batch_success() {
        UserEntity user1 = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        UserEntity user2 = UserEntity.builder()
                .username("test_user_002")
                .password("123456")
                .email("test_user_002@test.com")
                .build();
        UserEntity user3 = UserEntity.builder()
                .username("test_user_003")
                .password("123456")
                .email("test_user_003@test.com")
                .build();
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        userMapper.insertUser(user3);

        List<Long> idList = new ArrayList<>();
        idList.add(user1.getId());
        idList.add(user2.getId());
        idList.add(user3.getId());

        int rows = userMapper.deleteBatch(idList);
        assertThat(rows).isEqualTo(3);

        List<UserEntity> result1 = userMapper.selectUserList(user1);
        List<UserEntity> result2 = userMapper.selectUserList(user2);
        List<UserEntity> result3 = userMapper.selectUserList(user3);
        assertThat(result1).isEmpty();
        assertThat(result2).isEmpty();
        assertThat(result3).isEmpty();
    }

    @Test
    void test_update_username_success() {
        UserEntity user = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        userMapper.insertUser(user);

        user.setUsername("username_changed");
        int rows = userMapper.updateUserSelective(user);

        UserEntity queryParam = UserEntity.builder()
                .id(user.getId())
                .build();
        List<UserEntity> result = userMapper.selectUserList(queryParam);
        assertThat(rows) .isEqualTo(1);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getUsername()).isEqualTo("username_changed");
        assertThat(result.getFirst().getPassword()).isEqualTo("123456");
        assertThat(result.getFirst().getEmail()).isEqualTo("test_user_001@test.com");
    }

    @Test
    void test_update_password_success() {
        UserEntity user = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        userMapper.insertUser(user);

        user.setPassword("password_changed");
        int rows = userMapper.updateUserSelective(user);

        UserEntity queryParam = UserEntity.builder()
                .id(user.getId())
                .build();
        List<UserEntity> result = userMapper.selectUserList(queryParam);
        assertThat(rows) .isEqualTo(1);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getUsername()).isEqualTo("test_user_001");
        assertThat(result.getFirst().getPassword()).isEqualTo("password_changed");
        assertThat(result.getFirst().getEmail()).isEqualTo("test_user_001@test.com");
    }

    @Test
    void test_update_email_success() {
        UserEntity user = UserEntity.builder()
                .username("test_user_001")
                .password("123456")
                .email("test_user_001@test.com")
                .build();
        userMapper.insertUser(user);

        user.setEmail("email_changed@test.com");
        int rows = userMapper.updateUserSelective(user);

        UserEntity queryParam = UserEntity.builder()
                .id(user.getId())
                .build();
        List<UserEntity> result = userMapper.selectUserList(queryParam);
        assertThat(rows) .isEqualTo(1);
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getUsername()).isEqualTo("test_user_001");
        assertThat(result.getFirst().getPassword()).isEqualTo("123456");
        assertThat(result.getFirst().getEmail()).isEqualTo("email_changed@test.com");
    }
}
