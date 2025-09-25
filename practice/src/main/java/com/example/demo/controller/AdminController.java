package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Contact;
import com.example.demo.form.AdminForm;
import com.example.demo.form.ContactForm;
import com.example.demo.service.AdminService;
import com.example.demo.service.ContactService;

@Controller
public class AdminController {

	
	@Autowired
	AdminService adminService;
	@Autowired
	ContactService contactService;
	
	
	//管理者の登録画面表示
	@GetMapping("/admin/signup")
	public String signupView(Model model) {
		
		model.addAttribute("adminForm", new AdminForm());
		
		return "admin/signup";
	}
	
	//管理者の登録
	@PostMapping("/admin/signup")
	public String signup(@Validated @ModelAttribute("adminForm") AdminForm adminForm, BindingResult errorResult, Model model) {
		
		if (errorResult.hasErrors()) {
			return "admin/signup";
		}
		
		if(adminService.checkEmail(adminForm)) {
			
			model.addAttribute("emailError", "メールアドレスが重複しています");
			return "admin/signup";
		}else {
		
			adminService.saveAdmin(adminForm);
			return "redirect:/admin/signin";
		}
	}
	
	//ログイン画面の表示
	@GetMapping("/admin/signin")
	public String signinView(Model model) {
		
		model.addAttribute("adminForm", new AdminForm());
		return "admin/signin";
	}
	
	/*
	@GetMapping("/admin/signout")
	public String signout(Model model) {
		return "admin/signout";
	}
	*/
	
	//一覧表示のget
	@GetMapping("/admin/contacts")
	public String contactList(Model model) {
		
		List<Contact> contacts = contactService.findAllContacts();
		model.addAttribute("contactList", contacts);
		
		
		//一覧画面を表示
		return "admin/contacts";
	}
	
	//一覧画面からidごとの詳細画面へのget
	@GetMapping("/admin/contacts/{id}")
	public String contactDetail(@PathVariable("id") Long id, Model model){
		
		Contact contact = contactService.findId(id);
		model.addAttribute("contact", contact);
	
		//詳細画面の表示
		return "admin/contactDetail";
	}
	
	
	//詳細画面からidごとの編集画面へのget
	@GetMapping("/admin/contacts/{id}/edit")
	public String contactEdit(@PathVariable("id") Long id, Model model){
		
		Contact contact = contactService.findId(id);
		ContactForm contactForm = new ContactForm();
		contactForm = contactService.getOnContact(contact.getId());
		model.addAttribute("contact", contact);
		model.addAttribute("contactForm", contactForm);
		
		//編集画面の表示
		return "admin/contactEdit";
	}
	
	//編集画面の更新ボタン受け付け
	@PostMapping("/admin/contactEdit")
	public String contactList(@Validated @ModelAttribute("contactForm") ContactForm contactForm,
			BindingResult errorResult, HttpServletRequest request) {
		
		if(errorResult.hasErrors()) {
			//エラーの場合、一覧画面に戻る
			return "redirect:/admin/contactEdit";
		}
		
		contactService.saveContact(contactForm);
		
		//入力が正常の場合、下のgetにリダイレクトする
		return "redirect:/admin/contactEdit";
	}
	
	//編集画面のpostから受け取り
	@GetMapping("/admin/contactEdit")
	public String confirm(Model model) {

		return "redirect:/admin/contacts";
	}
	
	
	//編集画面から削除ボタン受け付け
	@GetMapping("/admin/contactsDelete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		
		contactService.deleteId(id);
		return "redirect:/admin/contacts";
	}
	
}
