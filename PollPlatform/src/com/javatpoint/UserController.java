package com.javatpoint;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;   
import org.springframework.web.bind.annotation.RequestMapping;    
import org.springframework.web.servlet.ModelAndView;

import com.database.*; 
/**
 * This is the controller class.
 * It is responsible for directing web pages.
 * @author LuyiYang
 *
 */
@Controller  
public class UserController{  
	PollPlatformDatabase db;

	public UserController() throws ClassNotFoundException, SQLException{	
		db = DerbyDatabase.getInstance();
	}
	
	@RequestMapping("/SignInForm")  
	public ModelAndView signInForm(HttpServletRequest request,HttpServletResponse res){
		String message = request.getParameter("message");
		return new ModelAndView("SignInForm","message",message);  
	}

	@RequestMapping("/SignUpForm")  
	public ModelAndView signUpForm(){  
		return new ModelAndView("SignUpForm"); 
	} 

	@RequestMapping("/UserHomePage")  
	public ModelAndView userHomePage(HttpServletRequest request,HttpServletResponse res) {  
		String message = request.getParameter("message");
		String name = request.getParameter("name");
		User u = db.getUser(name);
		List<Poll> pollList = db.getRecommendations(name, 5);
		return new ModelAndView("UserHomePage", "user", u).addObject("message", message).addObject("pollList", pollList);  
	} 

	@RequestMapping("/SeeMyPolls")  
	public ModelAndView seeMyPollsPage(HttpServletRequest request,HttpServletResponse res){ 
		String name = request.getParameter("name");

		return new ModelAndView("SeeMyPolls", "pollList", db.getPollsForUser(name)).addObject("name", name);
	}

	@RequestMapping("/SeeAvailablePolls")  
	public ModelAndView seeAvailablePollsPage(HttpServletRequest request,HttpServletResponse res){
		String name = request.getParameter("name");
		String tag = request.getParameter("tag");
		List<Poll> list = null;
		if(tag == null){
			list = db.getAllPolls();
		}else{
			list = db.getTaggedPolls(tag);
		}
		return new ModelAndView("SeeAvailablePolls", "pollList", list).addObject("name", name); 
	}

	@RequestMapping("/CreateANewPoll")  
	public ModelAndView createANewPollPage(HttpServletRequest request,HttpServletResponse res){ 
		String name = request.getParameter("name");
		return new ModelAndView("CreateANewPoll", "name", name); 
	}
	
	@RequestMapping("/poll")  
	public ModelAndView pollPage(HttpServletRequest request,HttpServletResponse res) {  
		int pid = Integer.parseInt(request.getParameter("pollId"));
		String name = request.getParameter("name");
		Poll p = db.getPoll(pid);
		return new ModelAndView("poll", "poll", p).addObject("name", name);  
	} 
	
	@RequestMapping("/pollResults")  
	public ModelAndView pollResults(HttpServletRequest request,HttpServletResponse res) {  
		int pid = Integer.parseInt(request.getParameter("pollId"));
		String name = request.getParameter("name");
		Poll p = db.getPoll(pid);
		int numberOfPeopleVoted = 0;
		Double total = 0.0;
		for(int i = 0; i<p.getPollResults().length; i++){
			total += p.getPollResults()[i]*(i+1);
			numberOfPeopleVoted += p.getPollResults()[i];
		}
		Double average = total/numberOfPeopleVoted;
		return new ModelAndView("pollResults", "poll", p).addObject("name", name).addObject("average", average).addObject("numberOfPeopleVoted", numberOfPeopleVoted);  
	} 

	@RequestMapping("/signup")  
	public ModelAndView signUp(HttpServletRequest request,HttpServletResponse res){

		String name=request.getParameter("name");  
		String password=request.getParameter("password"); 

		User user = new User(name, password);

		if(db.addUser(user) != -1){
			return new ModelAndView("redirect:/UserHomePage", "name", name);
		}else{
			String message = "Username is already in use. Please try again.";
			return new ModelAndView("SignUpForm", "message", message);
		}
	}  

	@RequestMapping("/signin")  
	public ModelAndView signIn(HttpServletRequest request,HttpServletResponse res){  

		String name=request.getParameter("name");  
		String password=request.getParameter("password"); 
		User u = db.getUser(name);
		if (u != null) {
			if(u.getPassword().equals(password)){
				return new ModelAndView("redirect:/UserHomePage","name",name);
			}else{
				String message = "Password is not correct. Please try again.";
				return new ModelAndView("redirect:/SignInForm", "message", message);
			}
		}else{
			String message = "Username is not correct. Please try again.";
			return new ModelAndView("SignInForm", "message", message);
		}
	} 

	@RequestMapping("/createpoll")  
	public ModelAndView createANewPoll(HttpServletRequest request,HttpServletResponse res){ 
		String poster = request.getParameter("name");
		User u = db.getUser(poster);
		String pollTitle = request.getParameter("pollTitle");
		String pollContent = request.getParameter("content");
		String polltag = request.getParameter("tag");
		if(pollTitle.length() == 0 || pollContent.length() == 0){
			String message1 = null;
			String message2 = null;
			if(pollTitle.length() == 0){
				message1 = "Poll title can't be empty.";
			}
			if(pollContent.length() == 0){
				message2 = "Poll content can't be empty.";
			}	
			return new ModelAndView("CreateANewPoll", "name", poster).addObject("message1", message1).addObject("message2", message2); 
		}

		if(polltag == null){
			polltag = "others";
		}
		Poll p = new Poll();
		p.setPoster(poster);
		p.setPollTitle(pollTitle);
		p.setPollContent(pollContent);
		p.setTag(polltag);
		db.postAPoll(poster,  p);		
		List<Poll> pollList = db.getRecommendations(poster, 5);
		return new ModelAndView("UserHomePage", "user", u).addObject("pollList", pollList); 
	}

	@RequestMapping("/submitRating")  
	public ModelAndView submitRating(HttpServletRequest request,HttpServletResponse res) {  
		String message = "You have successfully submitted a poll.";
		int rating = Integer.parseInt(request.getParameter("rating"));
		String name = request.getParameter("user");
		int pid = Integer.parseInt(request.getParameter("pollId"));
		int result = db.fillPoll(name, pid, rating);
		User u = db.getUser(name);

		if(result == -1){
			message = "You have voted this poll. Try others!";
		}

		List<Poll> pollList = db.getRecommendations(name, 5);
		return new ModelAndView("UserHomePage","message", message).addObject("user", u).addObject("pollList", pollList);  
	}

	@RequestMapping("/category")  
	public ModelAndView seeCategory(HttpServletRequest request,HttpServletResponse res) {  

		String name = request.getParameter("name");
		String tag = request.getParameter("tag");
		List<Poll> list = null;
		if(tag == null || tag.equalsIgnoreCase("all")){
			list = db.getAllPolls();
		}
		else if(tag.equals("most recent")){
			list = db.getRecentPolls(10);
		}
		else{
			list = db.getTaggedPolls(tag);
		}
		return new ModelAndView("SeeAvailablePolls", "pollList", list).addObject("name", name);  
	}

}  