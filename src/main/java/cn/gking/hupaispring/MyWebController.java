package cn.gking.hupaispring;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import cn.gking.hupaispring.core.*;

@Controller
@RequestMapping("/hupai")
public class MyWebController{
    Map<Integer,Room> rooms=new HashMap<>();
    Random random=new Random();
//    @ResponseBody
//    @GetMapping("/listrooms")
//    public String listRooms(){}
    @ResponseBody
    @GetMapping("/room")
    public String room(@RequestParam int id,@RequestParam(defaultValue = "0") int step){}
    @ResponseBody
    @GetMapping("/createroom")
    public String createroom(@RequestParam int p,@RequestParam int k,@RequestParam int pl,@RequestParam int q){
        int i = random.nextInt(1,1000000);
        Room room = new Room(new RoomConfig(p,k,q,pl));
        rooms.put(i,room);
        return new NetworkProtocol().room_init(Flag.STATE_WAITING_CONNECTION).step(i).toString();
    }
}
