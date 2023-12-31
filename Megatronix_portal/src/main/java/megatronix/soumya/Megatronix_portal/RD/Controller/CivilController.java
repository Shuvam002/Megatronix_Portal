package megatronix.soumya.Megatronix_portal.RD.Controller;

import megatronix.soumya.Megatronix_portal.RD.Model.CivilModel;
import megatronix.soumya.Megatronix_portal.RD.Repo.CivilRepo;
import megatronix.soumya.Megatronix_portal.RD.Service.CivilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

public class CivilController {

    @Autowired
    private CivilService service;

    @Autowired
    private CivilRepo repo;



    @PostMapping("/rd/civil")
    @Async
    public CompletableFuture<String> register(@RequestBody CivilModel member, Model model) {
        try {
            CompletableFuture<CivilModel> registeredMember = service.CivilMainRd(member);
            model.addAttribute("Rduser", registeredMember.get().getId());
            return CompletableFuture.completedFuture("rd-success");
        } catch (Exception e) {
            // Handle validation errors
            return CompletableFuture.completedFuture("civil");
        }
    }

    @GetMapping("/civilrd/rd_success")
    @Async
    public CompletableFuture<String> rdSuccess(@RequestParam(name = "id") Long userId, Model model) {
        // Fetch the user by ID and display the success page
        CivilModel Rduser = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Use "user" as the attribute name to match the success page template
        model.addAttribute("Rduser", Rduser);
        return CompletableFuture.completedFuture("rd-success");
    }
}
