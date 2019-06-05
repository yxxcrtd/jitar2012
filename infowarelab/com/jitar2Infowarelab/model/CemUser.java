package com.jitar2Infowarelab.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/*
 * 		<address>地址</address>
		<cellphone>13500000015</cellphone>
		<city>address_China_Zhejiang_Hangzhou</city>
		<company>gongsi</company>
		<country>address_China</country>
		<deptId>e10aa6eafd1546728ef4d703c0b7efab</deptId>
		<duty>职务</duty>
		<email>3rd015@test.com</email>
		<enabled>true</enabled>
		<fax>f123456</fax>
		<firstName>名</firstName>
		<forceCreate>true</forceCreate>
		<gender>1</gender>
		<lastName>姓</lastName>
		<nickname>3rd015</nickname>
		<officePhone>11111112</officePhone>
		<otherEmail>3rd015_other@test.com</otherEmail>
		<otherInfo>其它信息</otherInfo>
		<otherPhone>87654321</otherPhone>
		<password>123456</password>
		<postcode>123456</postcode>
		<province>address_China_Zhejiang</province>
		<reportTo>2</reportTo>
		<userName>3rd015</userName>
		<userType>2</userType>
 */
/**
 * 视频会议的用户对象
 * @author dell
 *
 */
@XStreamAlias("user")
public class CemUser {
		@XStreamAsAttribute
		private int id = 0;
		@XStreamAsAttribute
		private String activeDate;
		@XStreamAsAttribute
		private String address = "";
		@XStreamAsAttribute
		private String cellphone = "";
		@XStreamAsAttribute
		private String city = "";
		@XStreamAsAttribute
		private String company = "";
		@XStreamAsAttribute
		private String country = "";
		@XStreamAsAttribute
		private String deptId = "";
		@XStreamAsAttribute
		private boolean easycallUsed = true;
		@XStreamAsAttribute
		private boolean eimUsed = false;
		@XStreamAsAttribute
		private String duty = "";
		@XStreamAsAttribute
		private String email = "";
		@XStreamAsAttribute
		private boolean enabled = true;
		@XStreamAsAttribute
		private String fax = "";
		@XStreamAsAttribute
		private String firstName = "";
		private boolean forceCreate = true;		//默认true
		@XStreamAsAttribute
		private int gender = 1;					//默认1
		@XStreamAsAttribute
		private String lastName = "";
		@XStreamAsAttribute
		private String nickname = "";
		@XStreamAsAttribute
		private String officePhone = "";
		@XStreamAsAttribute
		private String otherEmail = "";
		@XStreamAsAttribute
		private String otherInfo = "";
		@XStreamAsAttribute
		private String otherPhone = "";
		@XStreamAsAttribute
		private String password = "";
		@XStreamAsAttribute
		private String postcode = "";
		@XStreamAsAttribute
		private String province = "";
		
		private int reportTo = 2;				//默认 2
		
		@XStreamAsAttribute
		private int siteId = 0;
		@XStreamAsAttribute
		private String userName = "";
		@XStreamAsAttribute
		private int userType = 2;  				//默认 2
		@XStreamAsAttribute
		private String extension;
		@XStreamAsAttribute
		private String inActiveDate;
		
		
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCellphone() {
			return cellphone;
		}
		public void setCellphone(String cellphone) {
			this.cellphone = cellphone;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getDeptId() {
			return deptId;
		}
		public void setDeptId(String deptId) {
			this.deptId = deptId;
		}
		public String getDuty() {
			return duty;
		}
		public void setDuty(String duty) {
			this.duty = duty;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean getEnabled() {
			return enabled;
		}
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}
		public String getFax() {
			return fax;
		}
		public void setFax(String fax) {
			this.fax = fax;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public boolean getForceCreate() {
			return forceCreate;
		}
		public void setForceCreate(boolean forceCreate) {
			this.forceCreate = forceCreate;
		}
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getOfficePhone() {
			return officePhone;
		}
		public void setOfficePhone(String officePhone) {
			this.officePhone = officePhone;
		}
		public String getOtherEmail() {
			return otherEmail;
		}
		public void setOtherEmail(String otherEmail) {
			this.otherEmail = otherEmail;
		}
		public String getOtherInfo() {
			return otherInfo;
		}
		public void setOtherInfo(String otherInfo) {
			this.otherInfo = otherInfo;
		}
		public String getOtherPhone() {
			return otherPhone;
		}
		public void setOtherPhone(String otherPhone) {
			this.otherPhone = otherPhone;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getPostcode() {
			return postcode;
		}
		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public int getReportTo() {
			return reportTo;
		}
		public void setReportTo(int reportTo) {
			this.reportTo = reportTo;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public int getUserType() {
			return userType;
		}
		public void setUserType(int userType) {
			this.userType = userType;
		}
		public String getActiveDate() {
			return activeDate;
		}
		public void setActiveDate(String activeDate) {
			this.activeDate = activeDate;
		}
		public boolean isEasycallUsed() {
			return easycallUsed;
		}
		public void setEasycallUsed(boolean easycallUsed) {
			this.easycallUsed = easycallUsed;
		}
		public boolean isEimUsed() {
			return eimUsed;
		}
		public void setEimUsed(boolean eimUsed) {
			this.eimUsed = eimUsed;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getSiteId() {
			return siteId;
		}
		public void setSiteId(int siteId) {
			this.siteId = siteId;
		}
		public String getExtension() {
			return extension;
		}
		public void setExtension(String extension) {
			this.extension = extension;
		}
		public String getInActiveDate() {
			return inActiveDate;
		}
		public void setInActiveDate(String inActiveDate) {
			this.inActiveDate = inActiveDate;
		}
		
		
}
