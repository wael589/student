package com.registrationPlateform.student.controller;

import com.registrationPlateform.student.domain.RegistrationRequest;
import com.registrationPlateform.student.services.StorageFile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.registrationPlateform.student.services.RegistrationService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("api/v1/registration")
public class AppUserController {
    private final RegistrationService registrationService;

    private final StorageFile storageFile;

    public AppUserController(RegistrationService registrationService, StorageFile storageFile) {
        this.registrationService = registrationService;
        this.storageFile = storageFile;
    }

    @RequestMapping(value="/myHome",method = RequestMethod.GET)
    public String getHello(){
        return registrationService.sayHello();
    }

    @RequestMapping(value="/register",method = RequestMethod.POST)
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @RequestMapping(value = "/confirm",method = RequestMethod.GET)
    public String confirmAccount(@RequestParam("token")String token){
        return  registrationService.confirmToken(token);
    }
}
