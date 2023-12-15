package bks2101.kuraga.firstProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController {
    @GetMapping("/auth")
    public String AuthPage() {
        return "auth";
    }
    @GetMapping("/admin/panel/users")
    public String AdminUsersPage() {
        return "adminUsers";
    }
    @GetMapping("/admin/panel/curators")
    public String AdminCuratorsPage() {
        return "adminCurators";
    }
    @GetMapping("/admin/panel/supervisors")
    public String AdminSupervisorsPage() {
        return "adminSupervisors";
    }
    @GetMapping("/ban")
    public String BanPage() {
        return "banned";
    }
    @GetMapping("/curator")
    public String CuratorPanelPage() {
        return "curatorPanel";
    }
    @GetMapping("/forgotPass/{token}/resetPass")
    public String ResetPasswordPage() { return "resetPass"; }
    @GetMapping("/forgotPass")
    public String ForgotPassPage() {
        return "forgotPass";
    }
    @GetMapping("/supervisor")
    public String SupervisorPanelPage() {
        return "supervisorPanel";
    }
}