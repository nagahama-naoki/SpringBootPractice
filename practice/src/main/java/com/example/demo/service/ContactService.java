package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;

public interface ContactService {
	
	public ContactForm getOnContact(long id);
	
	void saveContact (ContactForm contactForm);

	List<Contact> findAllContacts();
	
	Contact findId(long id);
	
	void deleteId(long id);
}