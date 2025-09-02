package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.form.ContactForm;
import com.example.demo.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService{
	
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public ContactForm getOnContact(long id) {
		Contact contact = contactRepository.findById(id).orElse(null);
		
		ContactForm contactForm = new ContactForm();
		contactForm.setId(contact.getId());
		contactForm.setLastName(contact.getLastName());
		contactForm.setFirstName(contact.getFirstName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhone(contact.getPhone());
		contactForm.setZipCode(contact.getZipCode());
		contactForm.setAddress(contact.getAddress());
		contactForm.setBuildingName(contact.getBuildingName());
		contactForm.setContactType(contact.getContactType());
		contactForm.setBody(contact.getBody());
		
		return contactForm;
	}
	
	@Override
	public void saveContact(ContactForm contactForm) {
		Contact contact = new Contact();
		
		contact.setId(contactForm.getId());
		contact.setLastName(contactForm.getLastName());
		contact.setFirstName(contactForm.getFirstName());
		contact.setEmail(contactForm.getEmail());
		contact.setPhone(contactForm.getPhone());
		contact.setZipCode(contactForm.getZipCode());
		contact.setAddress(contactForm.getAddress());
		contact.setBuildingName(contactForm.getBuildingName());
		contact.setContactType(contactForm.getContactType());
		contact.setBody(contactForm.getBody());
		
		contactRepository.save(contact);
	}
	
	@Override
	public List<Contact> findAllContacts() {
		return contactRepository.findAll();
	}
	
	@Override
	public Contact findId(long id) {
		return contactRepository.findById(id).orElse(null);
	}
	
	@Override
	public void deleteId(long id) {
		contactRepository.deleteById(id);
	}
	
}
