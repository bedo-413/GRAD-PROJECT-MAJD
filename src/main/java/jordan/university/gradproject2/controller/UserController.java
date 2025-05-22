package jordan.university.gradproject2.controller;

import jordan.university.gradproject2.enums.Occupation;
import jordan.university.gradproject2.resource.UserResource;
import jordan.university.gradproject2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jordan.university.gradproject2.controller.UserController.USERS_URL;

@RestController
@RequestMapping(USERS_URL)
@Slf4j
public class UserController {
    protected final static String USERS_URL = "/api/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search/by-occupation")
    public List<UserResource> searchByOccupation(
            @RequestParam Occupation occupation,
            @RequestParam String keyword
    ) {
        return userService.getUsersByOccupation(occupation, keyword);
    }

    @GetMapping("/search/by-university-id")
    public List<UserResource> searchByUniversityId(@RequestParam String keyword) {
        return userService.getUsersByUniversityId(keyword);
    }
}
