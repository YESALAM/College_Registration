package com.seatech.alam.collegeregistration;

public class Data {
	private String name;
	private String enroll;
	private String course;
	private String branch;
	private String semester;
	private String session;
	private String dob;

	private String scontact;
	private String receiptNo;
	private String email;
	private String paidAmount;
	private String pcontact;
	private String balAmount;

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	private String submitDate;

	public Data(){}

	public Data(Data fetchData){
		this.name = fetchData.getName();
		this.enroll = fetchData.getEnroll();
		this.course = fetchData.getCourse();
		this.branch = fetchData.getBranch();
		this.semester = fetchData.getSemester();
		this.session = fetchData.getSession();
		this.dob = fetchData.getDob();
	}


	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}

	public String getEnroll() {
		return enroll;
	}
	public void setEnroll(String enroll) {
		this.enroll = enroll;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}

	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getBalAmount() {
		return balAmount;
	}
	public void setBalAmount(String balAmount) {
		this.balAmount = balAmount;
	}

	public String getPcontact() {

		return pcontact;
	}
	public void setPcontact(String pcontact) {
		this.pcontact = pcontact;
	}

	public String getPaidAmount() {

		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getEmail() {

		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getReceiptNo() {

		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getScontact() {
		return scontact;
	}
	public void setScontact(String scontact) {
		this.scontact = scontact;
	}
	
}
