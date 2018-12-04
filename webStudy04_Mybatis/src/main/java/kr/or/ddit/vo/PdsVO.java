package kr.or.ddit.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Alias("pdsVO")
public class PdsVO implements Serializable{ 
	private Long pds_no;
	private Long bo_no;
	private String pds_filename;
	private String pds_savename;
	private String pds_mime;
	private Long pds_size;
	private String pds_fancysize;
}
