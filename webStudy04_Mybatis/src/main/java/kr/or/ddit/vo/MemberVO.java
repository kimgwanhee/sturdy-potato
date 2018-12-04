package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.apache.commons.codec.binary.Base64;
import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Alias("memberVO")
@NoArgsConstructor
@Data
@ToString(exclude= {"mem_img"})
@EqualsAndHashCode(of= {"mem_id", "mem_regno1", "mem_regno2"})
public class MemberVO implements Serializable{
	public MemberVO(String mem_id, String mem_pass) {//이런형태의 생성자를 넣으려면 위의 기본생성자도 넣어야함
		super();
		this.mem_id = mem_id;
		this.mem_pass = mem_pass;
	}
	
	
	private String mem_id;
	private String mem_pass;
	private String mem_name;
	private String mem_regno1;
	private String mem_regno2;
	private String mem_bir;
	private String mem_zip;
	private String mem_add1;
	private String mem_add2;
	private String mem_hometel;
	private String mem_comtel;
	private String mem_hp;
	private String mem_mail;
	private String mem_job;
	private String mem_like;
	private String mem_memorial;
	private String mem_memorialday;
	private Long mem_mileage;
	private String mem_delete;
	private String mem_auth;
	//구매상품목록
	private List<ProdVO> prodList;
	
	private byte[] mem_img;//2진 //tostring하면 콘솔 용량 다 잡아먹어서 제외
	
	public String getMem_imgBase64(){
		if(mem_img==null) {
			return null;
		}else {
			return Base64.encodeBase64String(mem_img);
		}
	}
	
	public String getAddress(){
		return Objects.toString(mem_add1, "")+ " "+ Objects.toString(mem_add2, "");//널일수도 있으므로 null value를 미리 셋팅
	}
	
	public String getAddressTest() {
		return "테스트";
	}
	
	
}