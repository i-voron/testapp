package ru.testapp.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import ru.testapp.entity.User;

@ParentPackage(value = "default")
@Namespace(value = "/")
public class TestAction extends ActionSupport {
    private String login;
    private String username;
    private String password;

    @Actions( {
            @Action(value = "index",
                    results = {@Result(name = "success", location = "/WEB-INF/jsp/index.jsp")})
    })
    public String execute() {
        login=getPrincipal();
        username=getUser();
        return SUCCESS;
    }
    @Actions( {
            @Action(value = "login",
                    results = {@Result(name = "input", location = "/WEB-INF/jsp/login.jsp")})
    })
    public String input() {
        return INPUT;
    }

    @Actions( {
            @Action(value = "logout",
                    results = {@Result(type = "redirectAction",
                            params = {"actionName","index","suppressEmptyParameters","true"})})
    })
    public String logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(ServletActionContext.getRequest(), ServletActionContext.getResponse(), auth);
        }
        return SUCCESS;
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    private String getUser(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        return user.getName();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
