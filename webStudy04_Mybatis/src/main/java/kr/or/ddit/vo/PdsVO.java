package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Alias("pdsVO")
public class PdsVO implements Serializable{ 
	
	public PdsVO(FileItem item) {
		super();
		this.item = item;
		String savename = UUID.randomUUID().toString();
		setPds_savename(savename);
		setPds_filename(item.getName());
		setPds_mime(item.getContentType());
		setPds_size(item.getSize());
		setPds_fancysize(FileUtils.byteCountToDisplaySize(item.getSize()));
		
		
	}

	private Long pds_no;
	private Long bo_no;
	private String pds_filename;
	private String pds_savename;
	private String pds_mime;
	private Long pds_size;
	private String pds_fancysize;
	
	private FileItem item;
}
