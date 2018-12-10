package kr.or.ddit.vo;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Alias("boardVO")
public class BoardVO implements Serializable{
	
	private Long bo_no;
	private String bo_writer;
	private String bo_pass;
	private String bo_ip;
	private String bo_mail;
	private String bo_title;
	private String bo_content;
	private String bo_date;
	private Long bo_hit;
	private Long bo_rcmd;
	
	//board와 1:N관계 has~many관계
	private List<PdsVO> pdsList;
	private List<ReplyVO> replyList;
	private Long[] delFiles;

	private List<FileItem> itemList;
	
	public void setItemList(List<FileItem> fileitems) {
		this.itemList = fileitems;
		List<PdsVO> pdsList = new ArrayList<>();
		for (FileItem fileItem : fileitems) {
			if (fileItem != null) {
				pdsList.add(new PdsVO(fileItem));//객체는 주소를 참조해서 상관x
			}
			this.pdsList = pdsList;
		}
	}
}
