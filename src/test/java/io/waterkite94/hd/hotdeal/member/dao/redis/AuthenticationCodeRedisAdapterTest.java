package io.waterkite94.hd.hotdeal.member.dao.redis;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;

class AuthenticationCodeRedisAdapterTest extends IntegrationTestSupport {

	@Autowired
	private AuthenticationCodeRedisAdapter authenticationCodeRedisAdapter;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@BeforeEach
	void before() {
		redisTemplate.delete(redisTemplate.keys("*"));
	}

	@Test
	@DisplayName(value = "이메일 인증번호를 Redis에 저장합니다.")
	void saveAuthenticationCode() {
		// given
		String email = "test@example.com";
		String authenticationCode = "123456";

		// when
		authenticationCodeRedisAdapter.saveAuthenticationCode(email, authenticationCode);

		// then

		String findAuthenticationCode = authenticationCodeRedisAdapter.getAuthenticationCode(email);

		assertThat(findAuthenticationCode).isEqualTo(authenticationCode);
	}
}
