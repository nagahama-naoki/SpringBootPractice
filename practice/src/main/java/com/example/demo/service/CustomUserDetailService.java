package com.example.demo.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private AdminRepository adminRepository;
	
	public CustomUserDetailService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<Admin> optionalAdmin = adminRepository.findByEmail(email);

		Admin admin = optionalAdmin.orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + email));

		return new org.springframework.security.core.userdetails.User(
				admin.getEmail(),
				admin.getPassword(), // パスワードはハッシュ化されている必要があります
				Collections.emptyList() // 権限リスト（必要に応じて実装）
		);
	}
}
