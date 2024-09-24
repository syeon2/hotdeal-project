package io.waterkite94.hd.hotdeal.support.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.MemberRepository;
import io.waterkite94.hd.hotdeal.member.api.infrastructure.persistence.entity.MemberEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<MemberEntity> findMember = memberRepository.findByEmail(email);

		if (findMember.isEmpty()) {
			throw new UsernameNotFoundException(email);
		}

		MemberEntity member = findMember.get(0);

		return new User(member.getEmail(), member.getPassword(), true, true, true, true, new ArrayList<>());
	}

	public MemberEntity findMemberByEmail(String email) {
		return memberRepository.findByEmail(email).get(0);
	}
}
