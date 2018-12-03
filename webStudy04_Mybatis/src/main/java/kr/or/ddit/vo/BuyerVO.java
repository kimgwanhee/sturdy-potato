package kr.or.ddit.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

/**
 * 자바빈 : Java beans 규약에 따라 재사용 가능한 객체들.
 * (Valude Object (VO), Data Transfer Object)
 * 1. 값을 지정할 프로퍼티
 * 2. 캡슐화
 * 3. 캡슐화된 프로퍼티에 접근할 인터페이스 메소드 제공(getter/setter)
 * 	get[set]변수명의 첫문자만 대문자로 한 카멜 표기법
 * 4. 상태 비교 방법 제공(equals)
 * 5. 상태 확인 방법 제공(toString)
 * 6. 직렬화 가능(전송이나 저장이 가능)
 * 
 */
@Alias("buyerVO")
public class BuyerVO implements Serializable{

	private String buyer_id;
	private String buyer_name;
	private String buyer_lgu;
	private String buyer_bank;
	private String buyer_bankno;
	private String buyer_bankname;
	private String buyer_zip;
	private String buyer_add1;
	private String buyer_add2;
	private String buyer_comtel;
	private String buyer_fax;
	private String buyer_mail;
	private String buyer_charger;
	private String buyer_telext;
	
	@Override
	public String toString() {
		return "BuyerVO [buyer_id=" + buyer_id + ", buyer_name=" + buyer_name + ", buyer_lgu=" + buyer_lgu
				+ ", buyer_bank=" + buyer_bank + ", buyer_bankno=" + buyer_bankno + ", buyer_bankname=" + buyer_bankname
				+ ", buyer_zip=" + buyer_zip + ", buyer_add1=" + buyer_add1 + ", buyer_add2=" + buyer_add2
				+ ", buyer_comtel=" + buyer_comtel + ", buyer_fax=" + buyer_fax + ", buyer_mail=" + buyer_mail
				+ ", buyer_charger=" + buyer_charger + ", buyer_telext=" + buyer_telext + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyer_add1 == null) ? 0 : buyer_add1.hashCode());
		result = prime * result + ((buyer_add2 == null) ? 0 : buyer_add2.hashCode());
		result = prime * result + ((buyer_bank == null) ? 0 : buyer_bank.hashCode());
		result = prime * result + ((buyer_bankname == null) ? 0 : buyer_bankname.hashCode());
		result = prime * result + ((buyer_bankno == null) ? 0 : buyer_bankno.hashCode());
		result = prime * result + ((buyer_charger == null) ? 0 : buyer_charger.hashCode());
		result = prime * result + ((buyer_comtel == null) ? 0 : buyer_comtel.hashCode());
		result = prime * result + ((buyer_fax == null) ? 0 : buyer_fax.hashCode());
		result = prime * result + ((buyer_id == null) ? 0 : buyer_id.hashCode());
		result = prime * result + ((buyer_lgu == null) ? 0 : buyer_lgu.hashCode());
		result = prime * result + ((buyer_mail == null) ? 0 : buyer_mail.hashCode());
		result = prime * result + ((buyer_name == null) ? 0 : buyer_name.hashCode());
		result = prime * result + ((buyer_telext == null) ? 0 : buyer_telext.hashCode());
		result = prime * result + ((buyer_zip == null) ? 0 : buyer_zip.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuyerVO other = (BuyerVO) obj;
		if (buyer_add1 == null) {
			if (other.buyer_add1 != null)
				return false;
		} else if (!buyer_add1.equals(other.buyer_add1))
			return false;
		if (buyer_add2 == null) {
			if (other.buyer_add2 != null)
				return false;
		} else if (!buyer_add2.equals(other.buyer_add2))
			return false;
		if (buyer_bank == null) {
			if (other.buyer_bank != null)
				return false;
		} else if (!buyer_bank.equals(other.buyer_bank))
			return false;
		if (buyer_bankname == null) {
			if (other.buyer_bankname != null)
				return false;
		} else if (!buyer_bankname.equals(other.buyer_bankname))
			return false;
		if (buyer_bankno == null) {
			if (other.buyer_bankno != null)
				return false;
		} else if (!buyer_bankno.equals(other.buyer_bankno))
			return false;
		if (buyer_charger == null) {
			if (other.buyer_charger != null)
				return false;
		} else if (!buyer_charger.equals(other.buyer_charger))
			return false;
		if (buyer_comtel == null) {
			if (other.buyer_comtel != null)
				return false;
		} else if (!buyer_comtel.equals(other.buyer_comtel))
			return false;
		if (buyer_fax == null) {
			if (other.buyer_fax != null)
				return false;
		} else if (!buyer_fax.equals(other.buyer_fax))
			return false;
		if (buyer_id == null) {
			if (other.buyer_id != null)
				return false;
		} else if (!buyer_id.equals(other.buyer_id))
			return false;
		if (buyer_lgu == null) {
			if (other.buyer_lgu != null)
				return false;
		} else if (!buyer_lgu.equals(other.buyer_lgu))
			return false;
		if (buyer_mail == null) {
			if (other.buyer_mail != null)
				return false;
		} else if (!buyer_mail.equals(other.buyer_mail))
			return false;
		if (buyer_name == null) {
			if (other.buyer_name != null)
				return false;
		} else if (!buyer_name.equals(other.buyer_name))
			return false;
		if (buyer_telext == null) {
			if (other.buyer_telext != null)
				return false;
		} else if (!buyer_telext.equals(other.buyer_telext))
			return false;
		if (buyer_zip == null) {
			if (other.buyer_zip != null)
				return false;
		} else if (!buyer_zip.equals(other.buyer_zip))
			return false;
		return true;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getBuyer_name() {
		return buyer_name;
	}
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	public String getBuyer_lgu() {
		return buyer_lgu;
	}
	public void setBuyer_lgu(String buyer_lgu) {
		this.buyer_lgu = buyer_lgu;
	}
	public String getBuyer_bank() {
		return buyer_bank;
	}
	public void setBuyer_bank(String buyer_bank) {
		this.buyer_bank = buyer_bank;
	}
	public String getBuyer_bankno() {
		return buyer_bankno;
	}
	public void setBuyer_bankno(String buyer_bankno) {
		this.buyer_bankno = buyer_bankno;
	}
	public String getBuyer_bankname() {
		return buyer_bankname;
	}
	public void setBuyer_bankname(String buyer_bankname) {
		this.buyer_bankname = buyer_bankname;
	}
	public String getBuyer_zip() {
		return buyer_zip;
	}
	public void setBuyer_zip(String buyer_zip) {
		this.buyer_zip = buyer_zip;
	}
	public String getBuyer_add1() {
		return buyer_add1;
	}
	public void setBuyer_add1(String buyer_add1) {
		this.buyer_add1 = buyer_add1;
	}
	public String getBuyer_add2() {
		return buyer_add2;
	}
	public void setBuyer_add2(String buyer_add2) {
		this.buyer_add2 = buyer_add2;
	}
	public String getBuyer_comtel() {
		return buyer_comtel;
	}
	public void setBuyer_comtel(String buyer_comtel) {
		this.buyer_comtel = buyer_comtel;
	}
	public String getBuyer_fax() {
		return buyer_fax;
	}
	public void setBuyer_fax(String buyer_fax) {
		this.buyer_fax = buyer_fax;
	}
	public String getBuyer_mail() {
		return buyer_mail;
	}
	public void setBuyer_mail(String buyer_mail) {
		this.buyer_mail = buyer_mail;
	}
	public String getBuyer_charger() {
		return buyer_charger;
	}
	public void setBuyer_charger(String buyer_charger) {
		this.buyer_charger = buyer_charger;
	}
	public String getBuyer_telext() {
		return buyer_telext;
	}
	public void setBuyer_telext(String buyer_telext) {
		this.buyer_telext = buyer_telext;
	}
	
	
}
