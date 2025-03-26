package cn.gking.hupaispring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import cn.gking.hupaispring.core.*;

@Controller
@RequestMapping("/hupai")
public class MyWebController{
    Map<Integer, GameController> rooms=new HashMap<>();
    Random random=new Random();
//    @ResponseBody
//    @GetMapping("/listrooms")
//    public String listRooms(){}
    @ResponseBody
    @GetMapping("/room")
    public String room(@RequestParam int id,@RequestParam(defaultValue = "0") int step){
        return null;
    }
    @GetMapping("/act")
    public void room(@RequestBody ClientAction action){
        GameController gameController = rooms.get(action.getRoomid());
        if(gameController==null)return;
        else{
            //do you parse here
        }
    }
    @GetMapping("/join")
    @ResponseBody
    public String join(@RequestParam int id,@RequestParam String name,@RequestParam(defaultValue = "0")int pos){
        GameController gameController = rooms.get(id);
        if(gameController==null)return "";
        else{
            Player o = new Player(name);
            if(gameController.players.contains(o)){
                if(pos==0)return "";
                else {
                    Iterator<Player> iterator = gameController.players.iterator();
                    Player po=null,pn=null;
                    while (iterator.hasNext()){
                        Player next = iterator.next();
                        if(next.getPosition()==pos)po=next;
                        if(Objects.equals(next.getName(), name))pn=next;
                    }
                    po.setPosition(pn.getPosition());
                    pn.setPosition(pos);
                    gameController.players.get(gameController.players.indexOf(o)).setPosition(pos);
                    return "{\"status\"=200;}";
                }
            }
            else {
                new int[gameController.players.size()]
                while (iterator.hasNext()){
                    Player next = iterator.next();
                    if(next.getPosition()==pos)po=next;
                    if(Objects.equals(next.getName(), name))pn=next;
                }
                gameController.players.add(o);
                return "{\"status\":200;\"pos\":"++"}";
            }
        }
    }
    @ResponseBody
    @GetMapping("/createroom")
    public String createroom(@RequestParam int p,@RequestParam int k,@RequestParam int pl,@RequestParam int q){
        int i = random.nextInt(1,1000000);
        GameController gameController = new GameController(new RoomConfig(p,k,q,pl));
        rooms.put(i, gameController);
        return new NetworkProtocol().room_init(Flag.STATE_WAITING_CONNECTION).step(i).toString();
    }
}
