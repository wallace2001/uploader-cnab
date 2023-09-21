package com.wallace.uploadcnab.service;

import com.wallace.uploadcnab.domain.User;
import com.wallace.uploadcnab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override @Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " dont founded."));
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
			AuthorityUtils.createAuthorityList(user.getRole())
		);
	}

	@Transactional(readOnly = false)
	public User save(User user) {
		String crypt = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(crypt);

		return repository.save(user);
	}

	@Transactional(readOnly = true)
	public Optional<User> findEmail(String email) {

		return repository.findByEmail(email);
	}
}
