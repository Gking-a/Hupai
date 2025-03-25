package cn.gking.hupaispring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import cn.gking.hupaispring.core.*;

@Controller
@RequestMapping("/hupai")
public class MyWebController{
    List<Room> rooms=new ArrayList<>();
    @ResponseBody
    @GetMapping("/listrooms")
    public String listRooms(){}
    @ResponseBody
    @GetMapping("/room")
    public String room(@RequestParam String token){}
    @ResponseBody
    @GetMapping("/createroom")
    public String createroom(){
        new Room()
    }
}
