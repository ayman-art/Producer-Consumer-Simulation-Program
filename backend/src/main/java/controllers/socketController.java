package controllers;

import models.SnapShot;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/snap.sendSnap")
    @SendTo("/snapshot/public")
    public SnapShot sendSnap(@Payload SnapShot snap){
        return snap;
    }
}
