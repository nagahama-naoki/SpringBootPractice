package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.form.AdminForm;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public void saveAdmin(AdminForm adminForm) {
		
		Admin admin = new Admin();
		
		admin.setLastName(adminForm.getLastName());
		admin.setFirstName(adminForm.getFirstName());
		admin.setEmail(adminForm.getEmail());
		admin.setPassword(adminForm.getPassword());
		
		adminRepository.save(admin);
	}
	
	//emailが重複していないかのメソッド
	@Override
	public boolean checkEmail(AdminForm adminForm) {
		
		return adminRepository.existsByEmail(adminForm.getEmail());
	}
	
	@Override
	public boolean authenticate(String email, String password) {
		
		Admin admin = adminRepository.findByEmail(email);
		
		if(admin == null) {
			return false;
		}
		
		return password.equals(admin.getPassword());
	}
}
