package com.example.demo.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService, UserDetailsService {

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminServiceImpl(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void saveAdmin(AdminForm adminForm) {

		Admin admin = new Admin();

		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(passwordEncoder.encode(adminForm.getPassword()));

		adminRepository.save(admin);
	}

	//emailが重複していないかのメソッド
	@Override
	public boolean checkEmail(AdminForm adminForm) {

		return adminRepository.existsByEmail(adminForm.getEmail());
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
