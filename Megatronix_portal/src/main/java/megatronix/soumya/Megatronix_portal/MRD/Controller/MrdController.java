package megatronix.soumya.Megatronix_portal.MRD.Controller;

import megatronix.soumya.Megatronix_portal.MRD.Model.MrdModel;
import megatronix.soumya.Megatronix_portal.MRD.Repo.MrdRepo;
import megatronix.soumya.Megatronix_portal.MRD.Service.MrdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MrdController {
    @Autowired
    private MrdService service;

    @Autowired
    private MrdRepo mrdRepository;

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        // Use "user" as the attribute name to match the form in registration.html
        model.addAttribute("user", new MrdModel());
        return "registration";
    }

    @GetMapping("/registration-success")
    public String registrationSuccess(@RequestParam(name = "id") Long userId, Model model) {
        // Fetch the user by ID and display the success page
        MrdModel user = mrdRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Use "user" as the attribute name to match the success page template
        model.addAttribute("user", user);
        return "registration-success";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") MrdModel member, Model model) {
        try {
            MrdModel registeredMember = service.registerMember(member);
            model.addAttribute("uniqueId", registeredMember.getId());
            return "registration-success";
        } catch (Exception e) {
            // Handle validation errors
            return "registration";
        }
    }
}
