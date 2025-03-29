package cn.gking.hupaispring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import cn.gking.hupaispring.core.*;

@Controller
@RequestMapping("/hupai")
public class MyWebController{
    public MyWebController() {
        HupaiSpringApplication.gcThread=new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(6*60*60*1000);
                        ArrayList<Integer> theIndexes = new ArrayList<>();
                        rooms.forEach((integer, gameController) -> {
                            if(System.currentTimeMillis()-gameController.timestamp>6*60*60*1000){
                                theIndexes.add(integer);
                            }
                        });
                        theIndexes.forEach(integer -> {
                            rooms.remove(integer);
                        });
                        theIndexes.clear();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        HupaiSpringApplication.gcThread.start();
    }

    Map<Integer, GameController> rooms=new HashMap<>();
    Random random=new Random();
//    @ResponseBody
//    @GetMapping("/listrooms")
//    public String listRooms(){}
    @ResponseBody
    @GetMapping("/room")
    public String room(@RequestParam int id,@RequestParam(defaultValue = "0") int step){
        GameController gameController = rooms.get(id);
        if(gameController==null)return "";
        System.out.println(gameController.lastChange());
        if(step==0)return gameController.lastChange();
        return gameController.getStep(step);
    }
    @PostMapping("/act")
    public void room(@RequestBody ClientAction action){
        GameController gameController = rooms.get(action.getRoomid());
        System.out.println("Action:"+action);
        if(gameController==null)return;
        else{
            //传参
            System.out.println("SOVLE?");
            gameController.solve(action.getAction(),action.getCards(),action.getReclaim());
        }
    }
    @GetMapping("/join")
    @ResponseBody
    public String join(@RequestParam int id,@RequestParam String name,@RequestParam(defaultValue = "0")int pos){
        GameController gameController = rooms.get(id);
        if(gameController==null)return "{\"status\":500}";
        else{
            Player o = new Player(name);
            if(gameController.players.contains(o)){
                if(pos==0)return "{\"status\":500}";
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
                    return "{\"status\":200}";
                }
            }
            else {
                int[] ints = new int[gameController.config.numberPlayer];
                Iterator<Player> iterator = gameController.players.iterator();
                while (iterator.hasNext()){
                    Player next = iterator.next();
                    ints[next.getPosition()-1]=1;
                }
                int p=0;
                for (int i = 0; i < ints.length; i++) {
                    if(ints[i]==0){
                        p=i+1;
                        break;
                    }
                }
                o.setPosition(p);
                gameController.players.add(o);
                if(gameController.players.size()==gameController.config.numberPlayer){
                    gameController.startGame(gameController1 -> {
                        rooms.remove(gameController1.config.id);
                    });
                }
                return "{\"status\":200,\"pos\":"+p+"}";
            }
        }
    }
    @ResponseBody
    @GetMapping("/createroom")
    public String createroom(@RequestParam int p,@RequestParam int k,@RequestParam int pl,@RequestParam int q){
        int i = random.nextInt(1,1000000);
        GameController gameController = new GameController(new RoomConfig(p,k,q,pl,i));
        rooms.put(i, gameController);
        return new NetworkProtocol().room_init(Flag.STATE_WAITING_CONNECTION).step(i).toString();
    }
}
