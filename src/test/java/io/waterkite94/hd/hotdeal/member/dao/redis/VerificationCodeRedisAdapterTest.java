package io.waterkite94.hd.hotdeal.member.dao.redis;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import io.waterkite94.hd.hotdeal.IntegrationTestSupport;

class VerificationCodeRedisAdapterTest extends IntegrationTestSupport {

	@Autowired
	private VerificationCodeRedisAdapter verificationCodeRedisAdapter;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@BeforeEach
	void before() {
		redisTemplate.delete(redisTemplate.keys("*"));
	}

	@Test
	@DisplayName(value = "이메일 인증번호를 Redis에 저장합니다.")
	void saveVerificationCode() {
		// given
		String email = "test@example.com";
		String verificationCode = "123456";

		// when
		verificationCodeRedisAdapter.saveVerificationCode(email, verificationCode);

		// then

		Optional<String> findVerificationOptional = verificationCodeRedisAdapter.getVerificationCode(email);

		assertThat(findVerificationOptional).isPresent()
			.get().isEqualTo(verificationCode);
	}
}
