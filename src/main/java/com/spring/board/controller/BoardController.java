package com.spring.board.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.HomeController;
import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.common.CommonUtil;

@Controller
public class BoardController {
	
	@Autowired 
	boardService boardService;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	@RequestMapping(value = "/board/boardList.do" , method = RequestMethod.GET )  // Yi: removed (  )
//	public String boardRead(Locale locale, Model model, PageVo pageVo) throws Exception{
//		
//		List<BoardVo> boardList = new ArrayList<BoardVo>();
//		
//		int page = 1;
//		int totalCnt = 0;
//		
//		if(pageVo.getPageNo() == 0){
//			pageVo.setPageNo(page);
//		}
//		
//		boardList = boardService.SelectBoardList(pageVo);
//		totalCnt = boardService.selectBoardCnt();
//		
//		
//		model.addAttribute("boardList", boardList);
//		model.addAttribute("totalCnt", totalCnt);
//		model.addAttribute("pageNo", page);
//		
//		return "board/boardList";
//	}
	
	
	// 목록 페이지 이동 
	@RequestMapping(value = "/board/boardList.do" , method = RequestMethod.GET )  // Yi: removed (  )
	public String boardList(Locale locale, Model model, PageVo pageVo, 
				HttpServletRequest request) throws Exception{
//		
		
		String [] codeId = request.getParameterValues("codeId"); 
		if (codeId != null) {
			for (int i=0; i < codeId.length; i++) {
				System.out.println("String[]codeId: " + codeId[i].toString());
			}
		}
		System.out.println(Arrays.toString(codeId));
		
		
		
		List<BoardVo> boardList = new ArrayList<BoardVo>(); 
		List<ComCodeVo> selectKindList = new ArrayList<ComCodeVo>();
		
				
		int page = 1;
//		int limit = 10; 
		int totalCnt = 0; 

//		if(request.getParameter("page")!= null) {
//			page = Integer.parseInt(request.getParameter("page"));
//		}
		 
	
		
//		int maxPage = (totalCnt + limit - 1)/limit;
//		int startPage = ((page-1)/10) * 10 + 1; 
//		
//		int endPage = startPage + 10 - 1;
//		if (endPage > maxPage) {
//			endPage = maxPage;
//		}
//		if (endPage < page) {
//			page = endPage; 
//		}
	
		if(pageVo.getPageNo() == 0){
			pageVo.setPageNo(page);
		}
		
		boardList = boardService.SelectBoardList(pageVo);
		totalCnt = boardService.selectBoardCnt();
		selectKindList = boardService.selectKindList(); 
		
//		model.addAttribute("page", page);
//		model.addAttribute("maxPage", maxPage);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage); 
		model.addAttribute("pageNo", page); 
		model.addAttribute("boardList", boardList); 
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("selectKindList", selectKindList);
		
		return "board/boardList";
	}
	
	
	// 게시판 - 상세보기 
	
	@RequestMapping(value = "/board/{boardType}/{boardNum}/boardView.do", method = RequestMethod.GET)
	public String boardView(Locale locale, Model model
			,@PathVariable("boardType")String boardType
			,@PathVariable("boardNum")int boardNum) throws Exception{
		
		BoardVo boardVo = new BoardVo();
		
		boardVo = boardService.selectBoard(boardType,boardNum);
		
		model.addAttribute("boardType", boardType);
		model.addAttribute("boardNum", boardNum);
		model.addAttribute("board", boardVo);
		
		return "board/boardView";
	}
	
	// 글 작성 클릭시 글 작성 페이지로 이동 
	@RequestMapping(value = "/board/boardWrite.do", method = RequestMethod.GET)
	public String boardWrite(Locale locale, Model model) throws Exception{
		return "board/boardWrite";
	}
	// 게시판 - 글 작성 처리 
	@RequestMapping(value = "/board/boardWriteAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardWriteAction(Locale locale, BoardVo boardVo) throws Exception{
		
		HashMap<String, String> result = new HashMap<String, String>();
		CommonUtil commonUtil = new CommonUtil();
		
		// 전체 게시글 개수를 얻어와 resultCnt에 저장 
		int resultCnt = boardService.boardInsert(boardVo);
		
		result.put("success", (resultCnt > 0)?"Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	// 게시판 - 삭제 처리 
	@RequestMapping(value="/board/boardDelete.do", method = RequestMethod.POST)
	@ResponseBody // you should add this when you're returning callbackMsg/json data 
	public String boardDelete(Model model, BoardVo boardVo) throws Exception {

		HashMap<String, String> result = new HashMap<String, String>(); 
		CommonUtil commonUtil = new CommonUtil(); 
			
		int resultCnt = boardService.boardDelete(boardVo);
			
		result.put("success", (resultCnt > 0)?"Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString("", result); 
		System.out.println("callbackMsg::"+callbackMsg);
			
		boardService.boardDelete(boardVo);
		return callbackMsg; // this is not used to return url location. Instead, it's used for returning json data. 
	}
		
	// 게시판 - 수정 처리 
	@RequestMapping(value = "/board/boardUpdateAction.do", method = RequestMethod.POST) // Yi: not sure, post or get
	@ResponseBody
	public String boardUpdateAction(Locale locale, BoardVo boardVo) throws Exception {

		HashMap<String, String> result = new HashMap<String, String>();
		CommonUtil commonUtil = new CommonUtil();

		int resultCnt = boardService.boardUpdate(boardVo);

		result.put("success", (resultCnt > 0) ? "Y" : "N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ", result);
		System.out.println("callbackMsg::" + callbackMsg);

		//boardService.boardUpdate(boardVo);
		return callbackMsg; // 다른 방법 'return "redirect:/board/boardList"' doesn't work because it's not for returning url location;
	}
	
	// 게시판 - 수정 뷰 페이지 이동
	@RequestMapping(value="/board/{boardType}/{boardNum}/boardUpdate.do", method = RequestMethod.GET)
	public String boardUpdate(Locale locale, Model model
			,@PathVariable("boardType")String boardType
			,@PathVariable("boardNum") int boardNum) throws Exception{
	
		BoardVo boardVo = new BoardVo();
		
		boardVo = boardService.selectBoard(boardType, boardNum);
		
		model.addAttribute("boardType", boardType);
		model.addAttribute("boardNum", boardNum); 
		model.addAttribute("board", boardVo);
		
		System.out.println("The troublesome boardUpdate - controller is finally working"); 
		
		return "board/boardUpdate"; 
	}
	
	// login page 
	@RequestMapping(value = "/board/boardLogin.do", method = RequestMethod.GET)
	public String boardLogin(Locale locale, Model model) throws Exception{
		return "board/boardLogin";
	}
	
	// registration page
	@RequestMapping(value = "/board/boardRegister.do", method = RequestMethod.GET)
	public String boardRegister(Locale locale, Model model) throws Exception{
		return "board/boardRegister";
	}

	@RequestMapping(value = "/board/boardRead.do", method = RequestMethod.GET)
	public String boardRead(Locale locale, Model model) throws Exception{
		return "board/boardReadr";
	}
}
