package kr.co.dhflour.emaillist.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.dhflour.emaillist.dao.EmailListDao;
import kr.co.dhflour.emaillist.vo.EmailListVo;

@WebServlet("/el")
public class EmailListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		RequestDispatcher rd = null;
		String action = request.getParameter("a");
		if (action == null) {
			action = "list";
		}
		
		switch(action) {
		case "add":
			String firstName = request.getParameter("fn");
			String lastName = request.getParameter("ln");
			String email = request.getParameter("email");
			
			
			EmailListVo vo = new EmailListVo();
			vo.setFirstName(firstName);
			vo.setLastName(lastName);
			vo.setEmail(email);
			
			boolean result = new EmailListDao().InsertVo(vo);
			if(result) {
				response.sendRedirect("/emaillist2/el");
			}
			break;
		case "form":
			rd = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
			rd.forward(request, response);
			break;
		default:
			/*list 처리(예외상황에 대비하기 위해 첫째 페이지를 default에 넣는다.)*/
			EmailListDao dao = new EmailListDao();
			List<EmailListVo> list = dao.fetchList();
			
			//request의 연장을 하는 코드라고 이해.
			request.setAttribute("list", list); //list 라는 이름으로 object를 던져줌.
			rd = request.getRequestDispatcher("/WEB-INF/views/list.jsp"); //분기만 받아옴?
			rd.forward(request, response); //포워딩(servlet에서 jsp로 이동시키는 것)
			break;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}

}
