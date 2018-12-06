/**
 * 게시글 상세조회의 덧글 처리
 */

function replyListMaker(resp) {
		if (resp.error) {
			if (resp.error ) {
				alert(resp.message);
			}
		} else {	//등록성공
			var html = "";
			if (resp.dataList) {
				$.each(resp.dataList, function(idx, reply) {
					html += "<tr id='TR_"+reply.rep_no+"'>";
					html += "<td>" + reply.rep_no + "</td>";
					html += "<td>" + reply.bo_no + "</td>";
					html += "<td>" + reply.rep_writer + "</td>";
					html += "<td>" + reply.rep_ip + "</td>";
					html += "<td>" + reply.rep_pass + "</td>";
					html += "<td>" + reply.rep_content + "</td>";
					html += "<td>" + reply.rep_date + "&nbsp;<span data-toggle='modal'class='replyDelBtn' > [삭제] </span></td>";
					html += "</tr>";
				});
			} else {
				html += "<tr><td colspan='7'>데이터없음</td></tr>";
			}
			pagingArea.html(resp.pagingHTML);
			listBody.html(html);
			//여기서 리셋해줘야함 
//			replyForm[0].reset();//[0]이렇게 첨자로 접근해야됨?
		}
	}

function pagingReply(page, bo_no) {//여기선 덧글에 대해서만 페이징처리할곳
		$.ajax({//주소(URL), 메서드, 파라미터(데이타), 내가응답데이타는 어떤형식으로 받을수있는지(html json..등의 데이타타입)가 필요..
			
			url : $.getContextPath()+"/reply/replyList.do",
			data : {
				bo_no:bo_no, //어떤글이라는 정보는 여기서 끝
				page : page
			//위랑 두개의 데이터를 가지고 요청발생
			},
			dataType : "json",
			success : replyListMaker,
			error : function(resp) {//에러도 하나의 응답이니깐 resp로..
				console.log(resp.status);
			}
		});
	}
	
	

	$(function() {
		pagingArea = $("#pagingArea");
		listBody = $("#listBody");
		var replyForm = $("[name='replyForm']");
		var delModal = $("#replyDeleteModal");
		

		listBody.on("click", ".replyDelBtn", function(){//동적이게 처리할땐 반드시 이런 코드가 나와야함 ..ㅠ
			var trId = $(this).closest("tr").prop("id");//this는 스판태그 부모는 td 그 부모는 tr//prop로 아이디 뽑아냄
			var rep_no = trId.substring(trId.indexOf("_")+1);
			delModal.find("#rep_no").val(rep_no);
			delModal.modal("show");
		});
		
		$("#modalBtn").on("click", function(){
			var action = replyForm.attr("action");
			//글번호 등등 셋팅한거 받아서 보내야함
			replyForm.attr("action", $.getContextPath()+"/reply/replyDelete.do");//그럼 이제 insert가 아니라 delete를 탈것
			//
			var rep_no = delModal.find("#rep_no").val();//히든태그의 val를 꺼내면 rep_no이다
			var rep_pass = delModal.find("#rep_pass").val();
			replyForm.find("[name='rep_no']").val(rep_no);
			replyForm.find("[name='rep_pass']").val(rep_pass);
			replyForm.submit();
			replyForm.attr("action", action)//이제 원래대로 다시 돌려두기
			replyForm[0].reset();
			$("#modalForm")[0].reset();
			delModal.modal("hide");
		});
		
		
		replyForm.ajaxForm({//ajaxForm 동기요청을 비동기로바꿈
			dataType : 'json',
			success :replyListMaker,
			error:function(resp){
				alert(resp.status);	
			}
		});
	});

