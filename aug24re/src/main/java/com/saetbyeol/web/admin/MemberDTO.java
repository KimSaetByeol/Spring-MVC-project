package com.saetbyeol.web.admin;

public class MemberDTO {
	private int sm_no, sm_grade;
	private String sm_name, sm_id, sm_pw, sm_email, sm_joindate;
	public int getSm_no() {
		return sm_no;
	}
	public void setSm_no(int sm_no) {
		this.sm_no = sm_no;
	}
	public int getSm_grade() {
		return sm_grade;
	}
	public void setSm_grade(int sm_grade) {
		this.sm_grade = sm_grade;
	}
	public String getSm_name() {
		return sm_name;
	}
	public void setSm_name(String sm_name) {
		this.sm_name = sm_name;
	}
	public String getSm_id() {
		return sm_id;
	}
	public void setSm_id(String sm_id) {
		this.sm_id = sm_id;
	}
	public String getSm_pw() {
		return sm_pw;
	}
	public void setSm_pw(String sm_pw) {
		this.sm_pw = sm_pw;
	}
	public String getSm_email() {
		return sm_email;
	}
	public void setSm_email(String sm_email) {
		this.sm_email = sm_email;
	}
	public String getSm_joindate() {
		return sm_joindate;
	}
	public void setSm_joindate(String sm_joindate) {
		this.sm_joindate = sm_joindate;
	}
	
}
